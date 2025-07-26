package com.lego.yxl.wide.controller;

import com.lego.yxl.wide.es.WideOrder;
import com.lego.yxl.wide.WideOrderService;
import com.lego.yxl.wide.es.WideOrderESDao;
import com.lego.yxl.wide.jpa.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/wide")
public class WideController {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private WideOrderESDao wideOrderDao;

    @Autowired
    private WideOrderService wideOrderService;

    private Order order;
    private User user;
    private Address address;
    private Product product;

    void setUp() {
        this.user = new User();
        this.user.setName("测试");
        this.userDao.save(this.user);

        this.address = new Address();
        this.address.setDetail("详细地址");
        this.address.setUserId(this.user.getId());
        this.addressDao.save(this.address);

        this.product = new Product();
        this.product.setName("商品名称");
        this.product.setPrice(100);
        this.productDao.save(this.product);

        this.order = new Order();
        this.order.setUserId(this.user.getId());
        this.order.setAddressId(this.address.getId());
        this.order.setProductId(this.product.getId());
        this.order.setDescr("我的订单");
        this.orderDao.save(this.order);

        this.wideOrderService.index(this.order.getId());
    }

    @GetMapping("/index")
    public void index() {
        setUp();

        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(order.getAddressId(), wideOrder.getAddressId());
        Assertions.assertEquals(order.getProductId(), wideOrder.getProductId());
        Assertions.assertEquals(order.getUserId(), wideOrder.getUserId());
        Assertions.assertEquals(order.getDescr(), wideOrder.getOrderDescr());

        Assertions.assertEquals(user.getName(), wideOrder.getUserName());

        Assertions.assertEquals(address.getDetail(), wideOrder.getAddressDetail());

        Assertions.assertEquals(product.getName(), wideOrder.getProductName());
        Assertions.assertEquals(product.getPrice(), wideOrder.getProductPrice());
    }

    @GetMapping("/updateOrder")
    public void updateOrder() {
        setUp();

        this.order.setDescr("订单详情");
        this.orderDao.save(this.order);

        this.wideOrderService.updateOrder(this.order.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(order.getDescr(), wideOrder.getOrderDescr());
    }

    @GetMapping("/updateUser")
    public void updateUser() {
        setUp();

        this.user.setName("新姓名");
        this.userDao.save(this.user);

        this.wideOrderService.updateUser(this.user.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(user.getName(), wideOrder.getUserName());
    }

    @GetMapping("/updateAddress")
    public void updateAddress() {
        setUp();

        this.address.setDetail("新详细地址");
        this.addressDao.save(this.address);

        this.wideOrderService.updateAddress(this.address.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(address.getDetail(), wideOrder.getAddressDetail());
    }

    @GetMapping("/updateProduct")
    public void updateProduct() {
        setUp();

        this.product.setName("新商品");
        this.product.setPrice(200);
        this.productDao.save(this.product);

        this.wideOrderService.updateProduct(this.product.getId());
        Optional<WideOrder> optional = wideOrderDao.findById(this.order.getId());

        Assertions.assertTrue(optional.isPresent());
        WideOrder wideOrder = optional.get();
        Assertions.assertEquals(order.getId(), wideOrder.getId());
        Assertions.assertEquals(product.getName(), wideOrder.getProductName());
        Assertions.assertEquals(product.getPrice(), wideOrder.getProductPrice());
    }

}
