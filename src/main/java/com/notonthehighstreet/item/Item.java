package com.notonthehighstreet.item;

public class Item {

    private final Integer productCode;
    private final String name;
    private final Integer priceInPennies;

    public Item(Integer productCode, String name, Integer priceInPennies) {
        this.productCode = productCode;
        this.name = name;
        this.priceInPennies = priceInPennies;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return priceInPennies;
    }

    public Item copy() {
        return new Item(productCode, name, priceInPennies);
    }
}
