package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.HashSet;
import java.util.Set;

public class PromotionUtils {

    /**
     * Calculates the final item price in pennies after applying or promotional rules.
     *
     * @param item             Item onto which we apply the promotional rules.
     * @param promotionalRules Promotional rules to be applies.
     * @return Price of the item after all reductions.
     */
    public static Integer applyAllPromotionsAndGetPriceInPennies(Item item, Set<PromotionalRule> promotionalRules) {
        if (promotionalRules.isEmpty()) {
            return item.getPrice();
        } else {
            PromotionalRule promotionalRule = promotionalRules.iterator().next();
            Set<PromotionalRule> remainingPromotions = new HashSet<>(promotionalRules);
            remainingPromotions.remove(promotionalRule);
            Integer reducedPrice = promotionalRule.getItemPriceInPenniesAfterDiscount(item, remainingPromotions);
            Item reducedPriceItem = new Item(item.getProductCode(), item.getName(), reducedPrice);
            return Math.min(applyAllPromotionsAndGetPriceInPennies(reducedPriceItem, remainingPromotions), reducedPrice);
        }
    }
}
