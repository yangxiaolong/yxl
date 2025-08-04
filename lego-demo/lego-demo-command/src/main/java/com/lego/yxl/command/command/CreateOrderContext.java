package com.lego.yxl.command.command;

import com.lego.yxl.core.loader.annotation.LazyLoadBy;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateOrderContext {
    private final CreateOrderCommand command;

    @LazyLoadBy("#{@addressRepository.getById(command.userAddress)}")
    private Address address;

    @LazyLoadBy("#{@productRepository.getByIds(productIds)}")
    private List<Product> products;

    public List<Long> getProductIds() {
        return command.getProducts().stream()
                .map(ProductForBuy::getProductId)
                .collect(Collectors.toList());
    }

    public CreateOrderContext(CreateOrderCommand command) {
        this.command = command;
    }
}
