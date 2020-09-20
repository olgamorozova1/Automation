package shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertLenientEquals;

class CartTest {

    public RealItem pen = new RealItem();
    public VirtualItem book = new VirtualItem();
    public Cart testCart;
    public Cart expectedCart;
    public double actualResult;


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

    @Test
    void deleteRealItemTest() {
        // expected cart is cart which does not contain any item
        expectedCart = new Cart("test-cart");
        //We add and delete Real item in Test cart
        testCart.addRealItem(pen);
        testCart.deleteRealItem(pen);
        //When Real Item is deleted from Cart it does not contain any item so it should be equal to Expected cart
        assertLenientEquals(expectedCart, testCart);
    }

    @Test
    void deleteVirtualItemTest() {
        expectedCart = new Cart("test-cart");
        testCart.addVirtualItem(book);
        testCart.deleteVirtualItem(book);
        assertLenientEquals(expectedCart, testCart);
    }

    @AfterEach
    void deleteCart() {
        testCart = null;
    }

}