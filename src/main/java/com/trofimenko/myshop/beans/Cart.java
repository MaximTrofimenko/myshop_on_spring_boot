package com.trofimenko.myshop.beans;

import com.trofimenko.myshop.persistence.entities.CartRecord;
import com.trofimenko.myshop.persistence.entities.Product;
import com.trofimenko.myshop.services.soap.PaymentService;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.paymentservice.Payment;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {
    private final PaymentService paymentService;

    private static final long serialVersionUID = 1L;

    private List<CartRecord> cartRecords;
    private List<Payment> payments;
    private Double price;
    private Payment payment;

    @PostConstruct
    public void init() {
        cartRecords = new ArrayList<>();
        payments = paymentService.getPayments("Russia");
    }

    public void clear() {
        cartRecords.clear();
        recalculatePrice();
    }

    public void add(Product product) {
        //перебираем записи в корзине
        for (CartRecord cartRecord : cartRecords) {
            //если находим такой же продукт в корзине
            if (cartRecord.getProduct().getId().equals(product.getId())) {
                //то увеличеваем количество на один
                cartRecord.setQuantity(cartRecord.getQuantity() + 1);
                //пересчитываем цену этих одинаковых продуктов
                cartRecord.setPrice(cartRecord.getQuantity() * cartRecord.getProduct().getPrice());
                recalculatePrice();
                return;
            }
        }
        cartRecords.add(new CartRecord(product));
        recalculatePrice();
    }

    //тут все просто, удаляем запись по id продукта
    public void removeByProductId(UUID productId) {
        for (int i = 0; i < cartRecords.size(); i++) {
            if (cartRecords.get(i).getProduct().getId().equals(productId)) {
                cartRecords.remove(i);
                recalculatePrice();
                return;
            }
        }
    }

    private void recalculatePrice() {
        price = 0.0;
        //получаем цену в каждой записи в листе карт рекордов, вытягиваем из них цену и суммируем
        cartRecords.forEach(cartRecord -> price = price + cartRecord.getPrice());
    }

}