package parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

class JsonParserTest {
    Parser parser = new JsonParser();
    RealItem pen = new RealItem();
    RealItem pencil = new RealItem();
    VirtualItem book = new VirtualItem();
    VirtualItem film = new VirtualItem();
    Cart expectedCart;
    Cart actualCart;
    File file;

    @BeforeEach
    void createCartAndJSONFile() {
        expectedCart = new Cart("olga-cart");
        file = new File("src/main/resources/olga-cart.json");
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
        pen.setPrice(0);
        pen.setWeight(0.01);

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
    public void testNoSuchFileException() {
        File nonexistentFile = new File("src/main/resources/test-cart.json");
        Throwable exception = assertThrows(NoSuchFileException.class, () -> parser.readFromFile(nonexistentFile));
        assertEquals("File src\\main\\resources\\test-cart.json.json not found!", exception.getMessage());
    }


    @AfterEach
    void deleteJSONFileCart() {
        file.delete();
        expectedCart = null;
    }

}