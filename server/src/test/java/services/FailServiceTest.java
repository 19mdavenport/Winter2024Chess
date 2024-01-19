package services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FailServiceTest {
    @Test
    public void meh1() {
        Assertions.assertTrue(false);
    }

    @Test
    public void meh2() {
        Assertions.assertFalse(true);
    }

    @Test
    public void meh3() {
        Assertions.assertEquals(5, 2+2);
    }

    @Test
    public void meh4() {
        Assertions.assertNotEquals(4, 2+2);
    }

    @Test
    public void meh5() {
        Assertions.assertNull("hi");
    }

    @Test
    public void meh6() {
        Assertions.assertNotNull(null);
    }

    @Test
    public void meh7() {
        Assertions.assertDoesNotThrow(() -> {throw new RuntimeException();});
    }

    @Test
    public void meh8() {
        Assertions.assertThrows(Exception.class, ()->{});
    }
}
