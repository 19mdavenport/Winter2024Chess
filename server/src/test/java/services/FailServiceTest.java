package services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    @Test
    public void meh9() {
        Assertions.fail();
    }

    @Test
    public void meh10() {
        Assertions.assertTimeout(Duration.ZERO, () -> Thread.sleep(1));
    }

    @Test
    public void meh11() {
        Assertions.assertSame("hi", "hello");
    }

    @Test
    public void meh12() {
        Assertions.assertNotSame("hi", "hi");
    }

    @Test
    public void meh13() {
        Assertions.assertIterableEquals(List.of(1), List.of(2));
    }

    @Test
    public void meh14() {
        Assertions.assertArrayEquals(new int[]{7}, new int[]{7, 9});
    }

    @Test
    public void meh15() {
        Assertions.assertInstanceOf(Integer.class, "hi");
    }

    @Test
    public void meh16() {
        Assumptions.abort();
    }

    @Test
    public void meh17() {
        Assumptions.assumeTrue(false);
    }

    @Test
    public void meh18() {
        Assumptions.assumeFalse(true);
    }
}
