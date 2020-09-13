package shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RealItemTest {
    RealItem realItem;

    @BeforeEach
    void setUp() {
        realItem = new RealItem();
    }

    @Test
    void setWeight_isObjectCreated_True() {

        realItem.setWeight(1.11);
        assertEquals(1.11, realItem.getWeight());
    }


    @AfterEach
    void tearDown() {
        realItem = null;
    }
}