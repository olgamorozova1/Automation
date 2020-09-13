package shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualItemTest {

    VirtualItem virtualItem;

    @BeforeEach
    void setUp() {
        virtualItem = new VirtualItem();
    }

    @Test
    void setSizeOnDisk() {
        virtualItem.setSizeOnDisk(1.11);
        assertEquals(1.11, virtualItem.getSizeOnDisk());
    }

    @AfterEach
    void tearDown() {
        virtualItem = null;
    }


}