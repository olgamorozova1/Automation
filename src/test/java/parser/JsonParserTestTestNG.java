package parser;

import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import testGroups.testGroups;

import java.io.*;

public class JsonParserTestTestNG {
    private Parser parser = new JsonParser();
    private RealItem pen = new RealItem();
    private VirtualItem book = new VirtualItem();
    private Cart expectedCart;
    private Cart actualCart;
    private File file;

    @BeforeMethod(groups = {testGroups.GROUP1})
    void createCartAndJSONFile() {
        expectedCart = new Cart("olga-cart");
        file = new File("src/main/resources/olga-cart.json");
    }

    @Test(groups = {testGroups.GROUP1})
    void writeToFileTest() {
        pen.setName("Pen");
        pen.setPrice(10.12);
        pen.setWeight(0.05);
        book.setSizeOnDisk(100);
        book.setName("Book");
        book.setPrice(20.50);

        expectedCart.addRealItem(pen);
        expectedCart.addVirtualItem(book);
        parser.writeToFile(expectedCart);
        try {
            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            actualCart = gson.fromJson(reader, Cart.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(actualCart.getCartName(), expectedCart.getCartName());
        softAssertion.assertEquals(actualCart.getTotalPrice(), expectedCart.getTotalPrice());
        softAssertion.assertAll();
    }

    @Test(groups = {testGroups.GROUP1})
    void readFromFileTest() {
        pen.setName("Pen");
        pen.setPrice(10.00);
        pen.setWeight(0.50);
        book.setSizeOnDisk(100);
        book.setName("Book");
        book.setPrice(20.50);

        expectedCart.addRealItem(pen);
        expectedCart.addVirtualItem(book);
        try {
            Gson gson = new Gson();
            Writer writer = new FileWriter(file);
            gson.toJson(expectedCart, writer);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        actualCart = parser.readFromFile(file);
        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(actualCart.getCartName(), expectedCart.getCartName());
        softAssertion.assertEquals(actualCart.getTotalPrice(), expectedCart.getTotalPrice());
        softAssertion.assertAll();
    }

    @Test(enabled = false, groups = {testGroups.GROUP2})
    public void testExceptionMessage() {
        String path = "src/main/resources/test-cart.json";
        Throwable exception = Assert.expectThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
        Assert.assertEquals(("File " + path + ".json not found!"), exception.getMessage());
    }

    @Test(groups = {testGroups.GROUP2})
    @Parameters({"path"})
    public void NoSuchFileExceptionTest(String path) {
        Assert.assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
    }

    @AfterMethod(groups = {testGroups.GROUP1})
    void deleteJSONFileCart() {
        file.delete();
        expectedCart = null;
    }
}
