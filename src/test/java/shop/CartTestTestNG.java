package shop;

import org.testng.Assert;
import org.testng.annotations.*;
import Constants.testGroups;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class CartTestTestNG {
    private RealItem pen = new RealItem();
    private VirtualItem book = new VirtualItem();
    private Cart testCart;
    private Cart expectedCart;
    private double actualResult;
    private double expectedResult;
    private final double tax = 0.2;

    @BeforeMethod(groups = {testGroups.CHECKIN})
    void createCart() {
        testCart = new Cart("test-cart");
        actualResult = 0.00;
    }

    @Test(groups = {testGroups.CHECKIN})
    void getTotalPriceTestForAllItems() {
        pen.setPrice(111.99);
        book.setPrice(22.26);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        expectedResult = (pen.getPrice() + book.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        Assert.assertEquals(expectedResult, actualResult, 0.001);
    }

    @Test(groups = {testGroups.FUNCTIONAL})
    void deleteRealItemTest() {
        expectedCart = new Cart("test-cart");
        pen.setPrice(111.11);
        pen.setWeight(0.52);
        pen.setName("pen");
        testCart.addRealItem(pen);
        testCart.deleteRealItem(pen);
        assertReflectionEquals(expectedCart, testCart);
    }

    @Test(groups = {testGroups.FUNCTIONAL})
    void deleteVirtualItemTest() {
        expectedCart = new Cart("test-cart");

        book.setPrice(222.22);
        book.setSizeOnDisk(520);
        book.setName("book");

        testCart.addVirtualItem(book);
        testCart.deleteVirtualItem(book);
        assertReflectionEquals(expectedCart, testCart);
    }

    @Test(groups = {testGroups.FUNCTIONAL})
    void deleteRealItemAndCheckTotalPriceTest() {
        pen.setPrice(111.11);
        book.setPrice(222.22);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        testCart.deleteRealItem(pen);
        expectedResult = (book.getPrice() + pen.getPrice() - pen.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        Assert.assertEquals(actualResult, expectedResult, 0.001);
    }

    @Test(groups = {testGroups.FUNCTIONAL})
    void deleteVirtualItemAndCheckTotalPriceTest() {
        pen.setPrice(111.11);
        book.setPrice(222.22);

        testCart.addRealItem(pen);
        testCart.addVirtualItem(book);
        testCart.deleteVirtualItem(book);
        expectedResult = (book.getPrice() + pen.getPrice() - book.getPrice()) * (1 + tax);
        actualResult = testCart.getTotalPrice();
        Assert.assertEquals(actualResult, expectedResult, 0.001);
    }

    @AfterMethod(groups = {testGroups.CHECKIN})
    void deleteCart() {
        testCart = null;
    }
}
