package server;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.mysql.MySqlDataAccess;
import handler.*;
import service.BadRequestException;
import service.ChessServerException;
import service.RequestItemTakenException;
import service.UnauthorizedException;
import spark.Spark;
import websocket.WebSocketHandler;

import java.net.HttpURLConnection;

public class Server {
    LOLOLOLOLOLOLOLOLOLOLOLOLOL

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        DataAccess dataAccess;

        try {
            dataAccess = new MySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        WebSocketHandler ws = WebSocketHandler.getInstance();
        ws.setDataAccess(dataAccess);

        Spark.webSocket("/connect", ws);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(dataAccess));

        Spark.path("/session", () -> {
            Spark.post("", new LoginHandler(dataAccess));
            Spark.delete("", new LogoutHandler(dataAccess));
        });

        Spark.path("/game", () -> {
            Spark.get("", new ListGamesHandler(dataAccess));
            Spark.post("", new CreateGameHandler(dataAccess));
            Spark.put("", new JoinGameHandler(dataAccess));
        });

        Spark.delete("/db", new ClearHandler(dataAccess));

        Spark.exception(BadRequestException.class, new ChessServerExceptionHandler<>(HttpURLConnection.HTTP_BAD_REQUEST));
        Spark.exception(UnauthorizedException.class, new ChessServerExceptionHandler<>(HttpURLConnection.HTTP_UNAUTHORIZED));
        Spark.exception(RequestItemTakenException.class, new ChessServerExceptionHandler<>(HttpURLConnection.HTTP_FORBIDDEN));
        Spark.exception(ChessServerException.class, new ChessServerExceptionHandler<>(HttpURLConnection.HTTP_INTERNAL_ERROR));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
    }
}
