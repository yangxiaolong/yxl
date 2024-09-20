package com.lego.yxl.command;

import com.lego.yxl.annotation.LazyLoadBy;
import com.lego.yxl.repository.address.Address;
import com.lego.yxl.repository.product.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreateOrderContext {
    private final CreateOrderCommand command;

    @LazyLoadBy("#{@addressRepository.getById(command.userAddress)}")
    private Address address;

    @LazyLoadBy("#{@productRepository.getByIds(${productIds})}")
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
