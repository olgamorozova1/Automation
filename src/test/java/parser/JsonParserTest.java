package parser;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertLenientEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

class JsonParserTest {
    public Parser parser = new JsonParser();
    public RealItem pen = new RealItem();
    public RealItem pencil = new RealItem();
    public VirtualItem book = new VirtualItem();
    public VirtualItem film = new VirtualItem();
    public Cart expectedCart;
    public Cart actualCart;
    public File file;

    @BeforeEach
    void createCartAndJSONFile() {
        expectedCart = new Cart("olga-cart");
        file = new File("src/main/resources/olga-cart.json");
    }

    @Test
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
        assertLenientEquals(expectedCart, actualCart);
    }

    @Test
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
        assertLenientEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithOneRealItemAndOneVirtualItem() {

        pen.setName("Pen");
        pen.setPrice(10.12);
        pen.setWeight(0.05);

        book.setSizeOnDisk(100);
        book.setName("Book");
        book.setPrice(20.50);

        expectedCart.addRealItem(pen);
        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createSeveralRealAndVirtualItemsInOneCart() {

        pen.setName("Pen");
        pen.setPrice(10.00);
        pen.setWeight(0.05);

        pencil.setName("Pencil");
        pencil.setPrice(20.00);
        pencil.setWeight(0.07);

        book.setSizeOnDisk(100);
        book.setName("Book");
        book.setPrice(20.50);

        film.setSizeOnDisk(500);
        film.setName("Film");
        film.setPrice(45.37);

        expectedCart.addRealItem(pen);
        expectedCart.addRealItem(pencil);
        expectedCart.addVirtualItem(book);
        expectedCart.addVirtualItem(film);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithEmptyNameOfRealItem() {

        pen.setName("");
        pen.setPrice(0);
        pen.setWeight(0.01);

        expectedCart.addRealItem(pen);

        parser.writeToFile(expectedCart);
        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithZeroPriceOfRealItem() {

        pen.setName("Pen");
        pen.setPrice(0);
        pen.setWeight(0.01);

        expectedCart.addRealItem(pen);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithNegativePriceOfRealItem() {
        pen.setName("Pen");
        pen.setPrice(-0.01);
        pen.setWeight(0.01);

        expectedCart.addRealItem(pen);
        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithZeroWeightOfRealItem() {
        pen.setName("Pen");
        pen.setPrice(0.1);
        pen.setWeight(0.00);

        expectedCart.addRealItem(pen);
        parser.writeToFile(expectedCart);


        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithNegativeWeightOfRealItem() {
        pen.setName("Pen");
        pen.setPrice(0.01);
        pen.setWeight(-0.05);

        expectedCart.addRealItem(pen);
        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithEmptyNameOfVirtualItem() {
        book.setSizeOnDisk(10);
        book.setName("");
        book.setPrice(10);

        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithZeroPriceOfVirtualItem() {

        book.setSizeOnDisk(0.01);
        book.setName("Book");
        book.setPrice(0);

        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithNegativePriceOfVirtualItem() {

        book.setSizeOnDisk(0.01);
        book.setName("Book");
        book.setPrice(-0.01);

        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithZeroSizeOnDiskOfVirtualItem() {
        book.setSizeOnDisk(0);
        book.setName("Book");
        book.setPrice(0.01);

        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createdCartWithNegativeSizeOnDiskOfVirtualItem() {
        book.setSizeOnDisk(-0.01);
        book.setName("Book");
        book.setPrice(0.01);

        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);

        assertReflectionEquals(expectedCart, actualCart);
    }

    @Test
    void createCartWithoutItems() {
        parser.writeToFile(expectedCart);
        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);
    }

    @Disabled
    @Test
    void readFromFileWithoutName() {
        Cart expectedCart = new Cart("");
        file = new File("src/main/resources/.json");

        pen.setName("Pen");
        pen.setPrice(10.12);
        pen.setWeight(0.05);

        book.setSizeOnDisk(100);
        book.setName("Book");
        book.setPrice(20.50);

        expectedCart.addRealItem(pen);
        expectedCart.addVirtualItem(book);

        parser.writeToFile(expectedCart);

        actualCart = parser.readFromFile(file);
        assertReflectionEquals(expectedCart, actualCart);

    }

    @Test
    public void testExceptionMessage() {
        String path = "src\\main\\resources\\test-cart.json";
        Throwable exception = assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
        assertEquals(("File " + path + ".json not found!"), exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = { "src\\main\\resources\\test-cart.json", "src\\main\\resources\\eugen-cart.txt", "eugen-cart.json", "", "src\\main\\resources\\eugen-cart" })
    public void NoSuchFileExceptionTest(String path) {
        Assertions.assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
    }

    @AfterEach
    void deleteJSONFileCart() {
        file.delete();
        expectedCart = null;
    }

}