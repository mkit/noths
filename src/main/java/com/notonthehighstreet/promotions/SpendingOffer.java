package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.Set;

import static com.notonthehighstreet.promotions.PromotionUtils.applyAllPromotionsAndGetPriceInPennies;

/*
 * In this implementation I focused on design therefore null validation and logging functionality I decided to skip.
 */

/**
 * This offer applies a discount percentage (discountPercentage) whenever the total spend on items is more the spending
 * threshold in pennies (spendingThresholdInPennies). The discount is combined with other offers in such a way that
 * other offers are applied first.
 */
public class SpendingOffer implements PromotionalRule {

    private final Integer spendingThresholdInPennies;
    private final Double discountPercentageFactor;

    public SpendingOffer(Integer spendingThresholdInPennies, Integer discountPercentage) {
        this.spendingThresholdInPennies = spendingThresholdInPennies;
        this.discountPercentageFactor = 1 - (discountPercentage / 100.0);
    }

    @Override
    public boolean isApplicable(Set<Item> items, Set<PromotionalRule> otherPromotionalRules) {
        return items.stream().mapToInt(Item::getPrice).sum() >= spendingThresholdInPennies;
    }

    @Override
    public Integer getItemPriceInPenniesAfterDiscount(Item item, Set<PromotionalRule> otherApplicableRules) {
        Integer priceInPenniesAfterOtherReductions = applyAllPromotionsAndGetPriceInPennies(item, otherApplicableRules);
        return ((Long) Math.round(priceInPenniesAfterOtherReductions * discountPercentageFactor)).intValue();
    }
}
