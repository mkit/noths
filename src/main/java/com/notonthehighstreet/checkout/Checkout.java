package com.notonthehighstreet.checkout;

import com.notonthehighstreet.item.Item;
import com.notonthehighstreet.promotions.PromotionalRule;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.notonthehighstreet.promotions.PromotionUtils.applyAllPromotionsAndGetPriceInPennies;

public class Checkout {

    private final Set<PromotionalRule> promotionalRules;
    private final Set<Item> scannedItems = new HashSet<>();

    public Checkout(Set<PromotionalRule> promotionalRules) {
        this.promotionalRules = promotionalRules;
    }

    public synchronized void scan(Item item) {
        scannedItems.add(item.copy());
    }

    public synchronized Double total() {
        if (scannedItems.isEmpty()) {
            return 0.0;
        }
        if (promotionalRules.isEmpty()) {
            return scannedItems.stream().mapToInt(Item::getPrice).sum() / 100.0;
        }
        Set<PromotionalRule> applicableRules = promotionalRules
                .stream()
                .filter(promotionalRule -> {
                    Set<PromotionalRule> otherPromotionalRules = new HashSet<>(promotionalRules);
                    otherPromotionalRules.remove(promotionalRule);
                    return promotionalRule.isApplicable(scannedItems, otherPromotionalRules);
                }).collect(Collectors.toSet());
        double discountedTotalInPennies = scannedItems
                .stream()
                .mapToInt(item -> applyAllPromotionsAndGetPriceInPennies(item, applicableRules))
                .sum();
        return Math.max(0.0, discountedTotalInPennies / 100.0);
    }
}
