package com.lego.yxl.command.command;

import com.lego.yxl.core.command.AggNotFoundException;
import com.lego.yxl.core.loader.lazyloadproxyfactory.LazyLoadProxyFactory;
import com.lego.yxl.core.validator.ValidateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class OrderCommandApplicationServiceImpl implements OrderCommandApplicationService {
    @Autowired
    private OrderRepositoryForCommand orderRepositoryForCommand;

    @Autowired
    private LazyLoadProxyFactory lazyLoadProxyFactory;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ValidateService validateService;

    @Override
    public Order create(CreateOrderCommand command) {
        CreateOrderContext context = new CreateOrderContext(command);
        CreateOrderContext contextProxy = this.lazyLoadProxyFactory.createProxyFor(context);

        validateService.validateBusiness(contextProxy);

        Order order = Order.create(contextProxy);

        this.orderRepositoryForCommand.save(order);
        order.consumeAndClearEvent(event -> eventPublisher.publishEvent(event));
        return order;
    }

    @Override
    public void paySuccess(PayByIdSuccessCommand command) {
        Order order = this.orderRepositoryForCommand.findById(command.getOrderId())
                .orElseThrow(() -> new AggNotFoundException(command.getOrderId()));
        order.paySuccess(command);
        this.orderRepositoryForCommand.save(order);
        order.consumeAndClearEvent(event -> eventPublisher.publishEvent(event));
    }

    @Override
    public Order syncByOrderId(SyncOrderByIdCommand command) {
        Optional<Order> orderOpt = this.orderRepositoryForCommand.findById(command.getKey());
        SyncOrderByIdContext context = this.lazyLoadProxyFactory.createProxyFor(new SyncOrderByIdContext(command));
        this.validateService.validateRule(context);

        Order order = orderOpt.orElseGet(() -> Order.createForSync(context));

        order.applySync(context);
        order.consumeAndClearEvent(event -> eventPublisher.publishEvent(event));
        this.orderRepositoryForCommand.save(order);
        return order;
    }

    @Override
    public void cancel(Long orderId) {
        Order order = this.orderRepositoryForCommand.findById(orderId)
                .orElseThrow(() -> new AggNotFoundException(orderId));
        order.cancel();
        this.orderRepositoryForCommand.save(order);
    }
}
