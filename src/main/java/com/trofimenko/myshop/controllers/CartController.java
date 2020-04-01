package com.trofimenko.myshop.controllers;

import com.trofimenko.myshop.beans.Cart;
import com.trofimenko.myshop.services.ProductService;
import com.trofimenko.myshop.services.soap.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.paymentservice.Payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;
    private final ProductService productService;
    private final PaymentService paymentService;

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable UUID id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        cart.add(productService.findById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable UUID id){
        cart.removeByProductId(id);
        return "redirect:/cart";
    }

    @GetMapping
    public String showCart(Model model) {
        List<Payment> payments = paymentService.getPayments("USA");
        model.addAttribute("cart", cart);
        model.addAttribute("payments", payments);
        return "cart";
    }
}

