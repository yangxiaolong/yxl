package com.lego.yxl.command.controller;

import com.google.common.collect.Lists;
import com.lego.yxl.command.command.CreateOrderCommand;
import com.lego.yxl.command.command.ProductForBuy;
import com.lego.yxl.command.command.SyncOrderByIdCommand;

import java.util.List;

public class CommandCreateUtil {

    public static CreateOrderCommand getCreateOrderCommand() {
        CreateOrderCommand command = new CreateOrderCommand();
        command.setUserId(100L);
        command.setUserAddress(10000L);
        List<ProductForBuy> products = Lists.newArrayList();
        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1000L);
            product.setAmount(1);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1100L);
            product.setAmount(2);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1200L);
            product.setAmount(3);
            products.add(product);
        }

        command.setProducts(products);
        return command;
    }

    public static SyncOrderByIdCommand getSyncOrderCommand(Long orderId) {
        SyncOrderByIdCommand command = new SyncOrderByIdCommand(orderId);
        command.setUserId(100L);
        command.setUserAddress(10000L);
        List<ProductForBuy> products = Lists.newArrayList();
        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1000L);
            product.setAmount(1);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1100L);
            product.setAmount(2);
            products.add(product);
        }

        {
            ProductForBuy product = new ProductForBuy();
            product.setProductId(1200L);
            product.setAmount(3);
            products.add(product);
        }

        command.setProducts(products);
        return command;
    }
}
