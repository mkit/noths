package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class SpendingOfferTest {

    private final Integer discountPercentage = 10;
    private final Double discountPercentageFactor = 1 - (discountPercentage/100.0);
    private final Integer spendingThreshold = 5000;
    private final PromotionalRule ruleUnderTest = new SpendingOffer(spendingThreshold, discountPercentage);

    @Test
    public void testIsApplicableReturnsTrue() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);
        Item testItemB = new Item(1, "Test item B", 2800);

        Set<Item> items = new HashSet<>();
        items.add(testItemA);
        items.add(testItemB);

        // when
        boolean result = ruleUnderTest.isApplicable(items, Collections.singleton(ruleUnderTest));

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void testIsApplicableReturnsFalse() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);

        Set<Item> items = new HashSet<>();
        items.add(testItemA);

        // when
        boolean result = ruleUnderTest.isApplicable(items, Collections.singleton(ruleUnderTest));

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testIsApplicableReturnsFalseWhenEmptyItems() {
        // given

        // when
        boolean result = ruleUnderTest.isApplicable(Collections.emptySet(), Collections.singleton(ruleUnderTest));

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testDiscountedPrice() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);

        // when
        int result = ruleUnderTest.getItemPriceInPenniesAfterDiscount(testItemA, Collections.emptySet());

        // then
        Assert.assertEquals(Math.round(testItemA.getPrice() * discountPercentageFactor), result);
    }

    @Test
    public void testDiscountedPriceWithOtherOffers() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);
        Integer reducedPriceInPennies = 700;

        // when
        int result = ruleUnderTest.getItemPriceInPenniesAfterDiscount(
                testItemA,
                Collections.singleton(new MultiplePurchaseOffer(1, 2, reducedPriceInPennies)));

        // then
        Assert.assertEquals(Math.round(reducedPriceInPennies * discountPercentageFactor), result);
    }
}
