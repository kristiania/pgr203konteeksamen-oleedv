package no.kristiania;

import no.kristiania.http.HttpServer;
import no.kristiania.yatzygame.category.CategoryController;
import no.kristiania.yatzygame.category.CategoryDao;
import no.kristiania.yatzygame.games.GamesController;
import no.kristiania.yatzygame.games.GamesDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class YatzyServer {
    private final HttpServer httpServer;

    public YatzyServer(int port) throws IOException {

        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("src/main/resources/pgr203.properties")) {
            properties.load(fileReader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();

        httpServer = new HttpServer(port);
        httpServer.setFileLocation("src/main/resources/yatzy-game");
        httpServer.addController("/api/category", new CategoryController(new CategoryDao(dataSource)));
        httpServer.addController("/api/games", new GamesController(new GamesDao(dataSource)));
        httpServer.addController("/api/games/all", new GamesController(new GamesDao(dataSource)));
//        httpServer.addController("/api/player", new GamesController(new GamesDao(dataSource)));
    }

    public static void main(String[] args) throws IOException {
        new YatzyServer(8080).start();
    }

    private void start() {
        httpServer.start();
    }

}
