package com.lego.yxl.validator.controller;

import com.lego.yxl.core.ValidateException;
import com.lego.yxl.context.CreateOrderCmd;
import com.lego.yxl.repository.product.Product;
import com.lego.yxl.repository.stock.Stock;
import com.lego.yxl.repository.user.User;
import com.lego.yxl.validator.service.CreateOrderContext;
import com.lego.yxl.validator.service.DomainValidateService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validator/domain")
public class DomainValidateServiceController {

    @Autowired
    private DomainValidateService domainValidateService;

    @RequestMapping("/createOrder_error1")
    public void createOrder_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                this.domainValidateService.createOrder((CreateOrderContext) null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrder_error2")
    public void createOrder_error2() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                CreateOrderContext context = new CreateOrderContext();
                this.domainValidateService.createOrder(context);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrder_error3")
    public void createOrder_error3() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                CreateOrderContext context = new CreateOrderContext();
                context.setUser(User.builder()
                        .build());
                this.domainValidateService.createOrder(context);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrder_error4")
    public void createOrder_error4() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                CreateOrderContext context = new CreateOrderContext();
                context.setUser(User.builder()
                        .build());
                context.setProduct(Product.builder()
                        .build());
                this.domainValidateService.createOrder(context);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }

        });
    }

    @RequestMapping("/createOrder_error5")
    public void createOrder_error5() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                CreateOrderContext context = new CreateOrderContext();
                context.setUser(User.builder()
                        .build());
                context.setProduct(Product.builder()
                        .build());
                context.setStock(Stock.builder()
                        .count(0)
                        .build());
                context.setCount(1);
                this.domainValidateService.createOrder(context);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrder")
    public void createOrder() {
        CreateOrderContext context = new CreateOrderContext();
        context.setUser(User.builder()
                .build());
        context.setProduct(Product.builder()
                .build());
        context.setStock(Stock.builder()
                .count(10)
                .build());
        context.setCount(1);
        this.domainValidateService.createOrder(context);
    }

    @RequestMapping("/createOrderCmd_error1")
    public void createOrderCmd_error1() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            try {
                this.domainValidateService.createOrder((CreateOrderCmd) null);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrderCmd_error2")
    public void createOrderCmd_error2() {
        Assertions.assertThrows(ValidateException.class, () -> {
            try {
                CreateOrderCmd cmd = new CreateOrderCmd();
                cmd.setCount(10000);
                cmd.setProductId(100L);
                cmd.setUserId(100L);
                this.domainValidateService.createOrder(cmd);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @RequestMapping("/createOrderCmd")
    public void createOrderCmd() {
        CreateOrderCmd cmd = new CreateOrderCmd();
        cmd.setCount(1);
        cmd.setProductId(100L);
        cmd.setUserId(100L);
        this.domainValidateService.createOrder(cmd);
    }

}
