package no.kristiania.yatzygame.games;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpServer;
import no.kristiania.yatzygame.category.CategoryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GamesController implements HttpController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final GamesDao gameDao;

    public GamesController(GamesDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public void handle(String requestAction, String requestPath, Map<String, String> query,
                       String requestBody, OutputStream outputStream)
            throws IOException {
        try {
            if (requestAction.equals("POST")) {
                query = HttpServer.parseQueryString(requestBody);
                Game game = new Game();
                String dicelist = query.get("diceList");
                //Regex used to allow input with comma and whitespace
                List<Integer> lst = new ArrayList<>();
                if (dicelist != null) {
                    lst = Arrays.stream(dicelist.split("[\\\\s, ]+")).map(Integer::parseInt).collect(Collectors.toList());
                }
                if (dicelist != null) {
                    String[] splited = (query.get("playerFirstName").split("\\s+"));
                    game.setPlayerFirstName(splited[0]);
                    game.setPlayerLastName(splited[1]);
                    Game existingPlayer =  gameDao.getExistingGamePlayer(game.getPlayerFirstName(), game.getPlayerLastName());
                    String fetchCategory = query.get("categoryName");
                    game.setCategory(query.get("categoryName"));
                    game.setDiceSequence(query.get("diceList"));
                    game.setGameDate(query.get("gameDate"));
                    game.setGameName(query.get("gameName"));

                    if (!lst.isEmpty()) {
                        game.setScore(game.calculateScore(fetchCategory, lst));
                    }
                    gameDao.updateGameDetails(existingPlayer.getId(),game.getCategory(), game.getDiceSequence(), game.getScore());

                } else {
                    game.setPlayerFirstName(query.get("playerFirstName"));
                    game.setPlayerLastName(query.get("playerLastName"));
                    String fetchCategory = query.get("categoryName");
                    game.setCategory(query.get("categoryName"));
                    game.setDiceSequence(query.get("diceList"));
                    game.setGameDate(query.get("gameDate"));
                    game.setGameName(query.get("gameName"));
                    gameDao.insert(game);
                }

                outputStream.write(("HTTP/1.1 302 Redirect\r\n" +
                        "Location: http://localhost:8080/game.html/\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());
                return;
            } else if (requestPath != null && requestPath.equals("/api/games/all")) {
                int statusCode = 200;
                String body = getAllGames();
                int contentLength = body.length();
                String contentType = "text/html";

                outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                        "Content-type: " + contentType + "\r\n" +
                        "Content-length: " + contentLength + "\r\n" +
                        "Connection: close \r\n" +
                        "\r\n" +
                        body).getBytes());

                return;
            }

            int statusCode = 200;
            String body = getBody();
            int contentLength = body.length();
            String contentType = "text/html";

            outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                    "Content-type: " + contentType + "\r\n" +
                    "Content-length: " + contentLength + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" +
                    body).getBytes());
        } catch (SQLException e) {
            int statusCode = 500;
            logger.error("While handling request {}", requestPath, e);
            String body = e.toString();
            outputStream.write(("HTTP/1.0 " + statusCode + " OK\r\n" +
                    "Content-type: " + "text/html" + "\r\n" +
                    "Content-length: " + body.length() + "\r\n" +
                    "Connection: close \r\n" +
                    "\r\n" +
                    body).getBytes());
            e.printStackTrace();
        }
    }

    public String getAllGames() throws SQLException {
        return gameDao.listAll().stream()
                .map(p -> String.format("<tr id=%s><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        p.getId(), p.getGameDate(), p.getGameName(), p.getPlayerFirstName(), p.getPlayerLastName(), p.getCategory(), p.getScore()))
                .collect(Collectors.joining(""));
    }

    public String getBody() throws SQLException {
        return gameDao.listAll().stream()
                .map(p -> String.format("<option id='%s'>%s</option>",
                        p.getId(), p.getPlayerFirstName() + " " + p.getPlayerLastName()))
                .collect(Collectors.joining(""));
    }
}