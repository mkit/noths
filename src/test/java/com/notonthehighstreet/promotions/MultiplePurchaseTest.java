package com.notonthehighstreet.promotions;

import com.notonthehighstreet.item.Item;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class MultiplePurchaseTest {

    private final Integer reducedPriceInPennies = 730;
    private final PromotionalRule ruleUnderTest = new MultiplePurchaseOffer(1, 2, reducedPriceInPennies);

    @Test
    public void testIsApplicableReturnsTrue() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);
        Item testItemB = new Item(1, "Test item B", 2800);

        Set<Item> items = new HashSet<>();
        items.add(testItemA);
        items.add(testItemB);

        // when and then
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

        // when and then
        boolean result = ruleUnderTest.isApplicable(items, Collections.singleton(ruleUnderTest));

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testIsApplicableReturnsFalseWhenEmptyItems() {
        // given

        // when and then
        boolean result = ruleUnderTest.isApplicable(Collections.emptySet(), Collections.singleton(ruleUnderTest));

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void testDiscountedPrice() {
        // given
        Item testItemA = new Item(1, "Test item A", 2400);

        // when
        Integer result = ruleUnderTest.getItemPriceInPenniesAfterDiscount(testItemA, Collections.emptySet());

        // then
        Assert.assertEquals(reducedPriceInPennies, result);
    }
}
