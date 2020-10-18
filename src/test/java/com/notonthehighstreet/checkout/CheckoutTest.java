package com.notonthehighstreet.checkout;

import com.notonthehighstreet.item.Item;
import com.notonthehighstreet.promotions.MultiplePurchaseOffer;
import com.notonthehighstreet.promotions.PromotionalRule;
import com.notonthehighstreet.promotions.SpendingOffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest {

    private Checkout checkoutWithoutPromotions;
    private Checkout checkoutWithPromotions;

    private final Integer spendingThresholdInPennies = 6000;
    private final Integer discountPercentage = 10;
    private final Integer productCode = 1;
    private final Integer thresholdNumber = 2;
    private final Integer reducedPriceInPennies = 800;


    @Before
    public void setUp() {
        checkoutWithoutPromotions = new Checkout(Collections.emptySet());
        Set<PromotionalRule> promotionalRules = new HashSet<>();
        promotionalRules.add(new SpendingOffer(spendingThresholdInPennies, discountPercentage));
        promotionalRules.add(new MultiplePurchaseOffer(productCode, thresholdNumber, reducedPriceInPennies));
        checkoutWithPromotions = new Checkout(promotionalRules);
    }

    @Test
    public void testCorrectTotalWithoutPromotions() {
        // given
        Item testItemA = new Item(productCode, "Test item A", 2400);
        Item testItemB = new Item(productCode, "Test item B", 2800);

        // when
        checkoutWithoutPromotions.scan(testItemA);
        checkoutWithoutPromotions.scan(testItemB);

        // then
        Assert.assertEquals((Double) 52.0, checkoutWithoutPromotions.total());
    }

    @Test
    public void testItemCanBeScannedTwice() {
        // given
        Item testItemA = new Item(productCode, "Test item A", 2400);

        // when
        checkoutWithoutPromotions.scan(testItemA);
        checkoutWithoutPromotions.scan(testItemA);

        // then
        Assert.assertEquals((Double) 48.0, checkoutWithoutPromotions.total());
    }

    @Test
    public void testCorrectTotalWithSomePromotions() {
        // given
        Item testItemA = new Item(productCode, "Test item A", 2400);
        Item testItemB = new Item(productCode, "Test item B", 2800);

        // when
        checkoutWithPromotions.scan(testItemA);
        checkoutWithPromotions.scan(testItemB);

        // then
        Assert.assertEquals((Double) (reducedPriceInPennies * 2 / 100.0), checkoutWithPromotions.total());
    }

    @Test
    public void testCorrectTotalWithAllPromotions() {
        // given
        Item testItemA = new Item(productCode, "Test item A", 2400);
        Item testItemB = new Item(productCode, "Test item B", 2800);
        Item testItemC = new Item(productCode, "Test item C", 2800);

        // when
        checkoutWithPromotions.scan(testItemA);
        checkoutWithPromotions.scan(testItemB);
        checkoutWithPromotions.scan(testItemC);

        // then
        Assert.assertEquals((Double) (reducedPriceInPennies * 3 * 0.9 / 100.0), checkoutWithPromotions.total());
    }

    @Test
    public void testCorrectTotalWithAllPromotionsAndOneNoDiscountedItem() {
        // given
        int regularItemPriceInPennies = 40;
        Item testItemA = new Item(productCode, "Test item A", 2400);
        Item testItemB = new Item(productCode, "Test item B", 2800);
        Item testItemC = new Item(productCode, "Test item C", 2800);
        Item testItemD = new Item(2, "Test item D", regularItemPriceInPennies);

        // when
        checkoutWithPromotions.scan(testItemA);
        checkoutWithPromotions.scan(testItemB);
        checkoutWithPromotions.scan(testItemC);
        checkoutWithPromotions.scan(testItemD);

        // then
        Assert.assertEquals(
                (Double) (((reducedPriceInPennies * 3 + regularItemPriceInPennies) * 0.9 / 100.0)),
                checkoutWithPromotions.total());
    }
}
