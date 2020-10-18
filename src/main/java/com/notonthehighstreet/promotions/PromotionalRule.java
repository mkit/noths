package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.Set;

/**
 * Encapsulates logic for offer applicability and its calculation.
 */
public interface PromotionalRule {

    /**
     * Checks whether this offer is applicable in the context of the given items and other promotional rules.
     *
     * @param items                 Items context. When used with checkout these are the items currently in the checkout.
     * @param otherPromotionalRules Other promotions context.
     *                              When used within checkout these are other promotions registered with the checkout.
     * @return True if this rule is applicable to any of the given items. False otherwise.
     */
    boolean isApplicable(Set<Item> items, Set<PromotionalRule> otherPromotionalRules);

    /**
     * Calculates the discounted price for the given item under this promotion.
     *
     * @param item                 Item for which the discounted price needs to be calculated.
     * @param otherApplicableRules Other applicable (i.e. isApplicable == true) rules in the context of the checkout.
     * @return discounted price (in pennies) under this promotional rule and within the other applicable rules context.
     */
    Integer getItemPriceInPenniesAfterDiscount(Item item, Set<PromotionalRule> otherApplicableRules);
}
