package shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    RealItem pen = new RealItem();
    VirtualItem book = new VirtualItem();
    Cart testCart;
    double actualResult;


    @BeforeEach
    void createCart() {
        testCart = new Cart("test-cart");
        actualResult = 0.00;
    }


    @Test
    void getTotalPriceTestForRealItem() {
        pen.setPrice(111.11);
        testCart.addRealItem(pen);
        actualResult = testCart.getTotalPrice();
        assertEquals(133.33, Math.round(actualResult * 100) / 100.0d);
    }

    @Test
    void getTotalPriceTestForVirtualItem() {

        book.setPrice(33.3);
        testCart.addVirtualItem(book);
        actualResult = testCart.getTotalPrice();
        assertEquals(39.96, Math.round(actualResult * 100) / 100.0d);
    }

    @Test
    void getTotalPriceTestForAllItems() {

        pen.setPrice(10.00);
        book.setPrice(20.00);
        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        actualResult = testCart.getTotalPrice();
        assertEquals(36.00, Math.round(actualResult * 100) / 100.0d);
    }

    @AfterEach
    void deleteCart() {
        testCart = null;
    }

}