package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.Set;

/*
 * In this implementation I focused on design therefore null validation and logging functionality I decided to skip.
 */
public class MultiplePurchaseOffer implements PromotionalRule {

    private final Integer productCode;
    private final Integer thresholdNumber;
    private final Integer reducedPriceInPennies;

    public MultiplePurchaseOffer(Integer productCode, Integer thresholdNumber, Integer reducedPriceInPennies) {
        this.productCode = productCode;
        this.thresholdNumber = thresholdNumber;
        this.reducedPriceInPennies = reducedPriceInPennies;
    }

    @Override
    public boolean isApplicable(Set<Item> items, Set<PromotionalRule> otherPromotionalRules) {
        return items.stream().filter(item -> item.getProductCode().equals(productCode)).count() >= thresholdNumber;
    }

    @Override
    public Integer getItemPriceInPenniesAfterDiscount(Item item, Set<PromotionalRule> otherApplicableRules) {
        if (item.getProductCode().equals(productCode)) {
            return reducedPriceInPennies;
        } else {
            return item.getPrice();
        }
    }
}
