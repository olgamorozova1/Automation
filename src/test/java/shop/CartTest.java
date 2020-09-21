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
    public final double tax = 0.2;


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
        actualResult = testCart.getTotalPrice();
        assertEquals(pen.getPrice()+pen.getPrice()*tax+book.getPrice()+book.getPrice()*tax, testCart.getTotalPrice(), 0.001);
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