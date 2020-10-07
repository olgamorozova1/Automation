package shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertLenientEquals;

class CartTest {
    private RealItem pen = new RealItem();
    private VirtualItem book = new VirtualItem();
    private Cart testCart;
    private Cart expectedCart;
    private double actualResult;
    private double expectedResult;
    private final double tax = 0.2;

    @BeforeEach
    void createCart() {
        testCart = new Cart("test-cart");
        actualResult = 0.00;
    }

    @Test
    void getTotalPriceTestForAllItems() {
        pen.setPrice(111.99);
        book.setPrice(22.26);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        expectedResult = (pen.getPrice() + book.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        assertEquals(expectedResult, actualResult, 0.001);
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

    @Test
    void deleteRealItemAndCheckTotalPriceTest() {
        pen.setPrice(111.11);
        book.setPrice(222.22);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        testCart.deleteRealItem(pen);
        expectedResult = (book.getPrice() + pen.getPrice() - pen.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test
    void deleteVirtualItemAndCheckTotalPriceTest() {
        pen.setPrice(111.11);
        book.setPrice(222.22);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        testCart.deleteVirtualItem(book);
        expectedResult = (book.getPrice() + pen.getPrice() - book.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        assertEquals(expectedResult, actualResult, 0.001);
    }

    @AfterEach
    void deleteCart() {
        testCart = null;
    }
}