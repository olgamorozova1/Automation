package parser;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;


public class ExceptionTest {
    private Parser parser = new JsonParser();

    @Test(dataProvider = "path")
    public void NoSuchFileExceptionTest(String path) {
        Assert.assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path)));
    }

    @DataProvider(name = "path")
    public Object[][] dataProvider() {
        return new Object[][]{
                {"src/main/resources/eugen-cart.txt"},
                {"eugen-cart.json"},
                {""},
                {"src/main/resources/eugen-cart"},
                {"src/main/resources/test-cart.json"}
        };
    }
}
