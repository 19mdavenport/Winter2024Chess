package clientTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;


public class ServerFacadeTests {
    int lol = 1;
    @TestFactory
    public Stream<DynamicTest> lol() {
        return Stream.generate(() -> DynamicTest.dynamicTest("lol" + lol, () -> lol++)).limit(100);
    }

}
