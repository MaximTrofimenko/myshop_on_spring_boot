package com.trofimenko.myshop.controllers;

import com.trofimenko.myshop.beans.Cart;
import com.trofimenko.myshop.persistence.entities.Shopuser;
import com.trofimenko.myshop.services.ProductService;
import com.trofimenko.myshop.services.ReviewService;
import com.trofimenko.myshop.services.ShopuserService;
import com.trofimenko.myshop.utils.CaptchaGenerator;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final ProductService productService;
    private final ShopuserService shopuserService;
    private final CaptchaGenerator captchaGenerator;
    private final ReviewService reviewService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }


    /*
    cookie - небольшой объем информации, отправленный сервлетом в веб-браузер, сохраненный браузером, а затем отправленный обратно на сервер.
    Значение файла cookie может однозначно идентифицировать клиента, поэтому файлы cookie обычно используются для управления сеансами.
    Required - необходимый, требуемый, обязательный
    */

    @GetMapping("/admin")
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        if (data != null) {
            System.out.println(data);
        }

        model.addAttribute("products", productService.findAll(null));

        return "admin";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        Shopuser shopuser = shopuserService.findByPhone(principal.getName());

        model.addAttribute("shopuser", shopuser);
        model.addAttribute("reviews",reviewService.getReviewsByShopuser(shopuser));

        return "profile";
    }

    //метод просто выводит капчу,обработка правильности в другом месте
    @GetMapping(value = "/captcha",produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getCaptcha(HttpSession session) throws IOException {
            BufferedImage bufferedImage = captchaGenerator.getCaptchaImage();
            session.setAttribute("captchaCode",captchaGenerator.getCaptchaString());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
    }



}