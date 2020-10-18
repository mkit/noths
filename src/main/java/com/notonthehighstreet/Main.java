package com.notonthehighstreet;

import com.notonthehighstreet.checkout.Checkout;
import com.notonthehighstreet.item.Item;
import com.notonthehighstreet.promotions.MultiplePurchaseOffer;
import com.notonthehighstreet.promotions.PromotionalRule;
import com.notonthehighstreet.promotions.SpendingOffer;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Item cardHolder = new Item(1, "Travel Card Holder", 925);
        Item personaliseCufflinks = new Item(2, "Personalised Cufflinks", 4500);
        Item kidsTShirt = new Item(3, "Kids T-Shirt", 1995);

        Set<PromotionalRule> promotionalRules = new HashSet<>();
        promotionalRules.add(new MultiplePurchaseOffer(1, 2, 850));
        promotionalRules.add(new SpendingOffer(6000, 10));

        // Scenario 1
        Checkout co = new Checkout(promotionalRules);
        co.scan(cardHolder);
        co.scan(personaliseCufflinks);
        co.scan(kidsTShirt);

        /*
         * I am rounding the numbers using mathematical rounding (not ceil or floor).
         * Therefore, the results are 1 penny different. I thought that is correct.
         */
        System.out.println("Scenario 1 total: " + co.total());

        // Scenario 2
        co = new Checkout(promotionalRules);
        co.scan(cardHolder);
        co.scan(kidsTShirt);
        co.scan(cardHolder);

        System.out.println("Scenario 2 total: " + co.total());

        // Scenario 3
        co = new Checkout(promotionalRules);
        co.scan(cardHolder);
        co.scan(personaliseCufflinks);
        co.scan(cardHolder);
        co.scan(kidsTShirt);

        System.out.println("Scenario 3 total: " + co.total());
    }
}
