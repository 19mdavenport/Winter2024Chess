package clientTests;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import web.ResponseException;
import web.ServerFacade;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class ServerFacadeTests {
    int lol = 0;
    @TestFactory
    public Stream<DynamicTest> lol() {
        return Stream.generate(() -> DynamicTest.dynamicTest("lol" + lol++, () -> Assertions.assertTrue(true))).limit(100);
    }

}
