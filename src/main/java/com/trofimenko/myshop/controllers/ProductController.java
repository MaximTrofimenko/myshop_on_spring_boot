package com.trofimenko.myshop.controllers;

import com.trofimenko.myshop.exceptions.ProductNotFoundException;
import com.trofimenko.myshop.exceptions.WrongCaptchaCodeException;
import com.trofimenko.myshop.persistence.entities.*;
import com.trofimenko.myshop.persistence.pojo.ProductPojo;
import com.trofimenko.myshop.persistence.pojo.ReviewPojo;
import com.trofimenko.myshop.services.ImageService;
import com.trofimenko.myshop.services.ProductService;
import com.trofimenko.myshop.services.ReviewService;
import com.trofimenko.myshop.services.ShopuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final AmqpTemplate amqpTemplate;
    private final ImageService imageService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ShopuserService shopuserService;


    @GetMapping(value = "/{id}")
    public String getOneProduct(Model model,@PathVariable String id) throws Exception{

        Product product = productService.findById(UUID.fromString(id));
        List<Review> reviews = reviewService.getReviewsByProduct(product).orElse(new ArrayList<>());

        model.addAttribute("product",product);
        model.addAttribute("reviews", reviews);

        return "product";
    }



    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = imageService.loadFileAsResource(id);
        if (bufferedImage != null) {
            ImageIO.write(bufferedImage,"png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } else {
            return new byte[0];
        }
    }




    @PostMapping
    public String addOne(@RequestParam("image") MultipartFile image, ProductPojo productPojo) throws IOException {
        Image img = imageService.uploadImage(image, productPojo.getTitle());
        return productService.save(productPojo, img);
    }


    @PostMapping("/reviews")
    public String addReview(ReviewPojo reviewPojo, HttpSession session, Principal principal) throws ProductNotFoundException {

        if(reviewPojo.getCaptchaCode().equals(session.getAttribute("captchaCode"))) {

            Product product = productService.findById(reviewPojo.getProductId());
            Shopuser shopuser = shopuserService.findByPhone(principal.getName());

            Review review = Review.builder()
                    .commentary(reviewPojo.getCommentary())
                    .product(product)
                    .shopuser(shopuser)
                    .approved(shopuser.getRoles().stream().anyMatch(x -> x.getName().equals("ROLE_ADMIN")))
                    .build();

            /*
            отправляем сообщение для RabbitMQ в тот момент когда оставляем отзыв о товаре
             */
            amqpTemplate.convertAndSend("myshop.exchange","my.shop","User " + shopuser.getPhone() + "has left the review!");

            reviewService.save(review);

            return "redirect:/products/" + product.getId();
        }else
            throw new WrongCaptchaCodeException("Wrong captcha");
    }
}
