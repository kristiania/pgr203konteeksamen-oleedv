package no.kristiania.yatzygame;

import no.kristiania.yatzygame.games.Game;
import no.kristiania.yatzygame.games.GamesController;
import no.kristiania.yatzygame.games.GamesDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class GamesControllerTest {
    private GamesDao dao;

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:GameDaoTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new GamesDao(dataSource);
    }

    @Test
    void shouldReturnGameFromDb() throws SQLException {
        Game gameOne = new Game();
        Game gameTwo = new Game();

        gameOne.setPlayerFirstName("Ole");
        gameOne.setPlayerFirstName("LastName");
        gameTwo.setPlayerFirstName("Glizzy");
        gameTwo.setPlayerLastName("Blizzy");
        gameOne.setCategory("ONES");
        gameTwo.setCategory("HREE OF A KIND");
        gameOne.setDiceSequence("1,1,1,1,1");
        gameTwo.setDiceSequence("6,6,6,6,6");
        gameOne.setScore("5");
        gameTwo.setScore("5");

        dao.insert(gameOne);
        dao.insert(gameTwo);

        GamesController controller = new GamesController(dao);
        assertThat(controller.getBody())
                .contains(String.format("<tr id=%s><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", gameOne.getId(), gameOne.getPlayerFirstName(), gameOne.getDiceSequence(), gameOne.getCategory(), gameOne.getScore()))
                .contains(String.format("<tr id=%s><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", gameTwo.getId(), gameTwo.getPlayerLastName(), gameTwo.getDiceSequence(), gameTwo.getCategory(), gameTwo.getScore()));
        assertThat(gameOne.getPlayerFirstName()).isEqualTo("Ole");
        assertThat(gameOne.getPlayerFirstName()).isEqualTo("LastName");
        assertThat(gameTwo.getPlayerLastName()).isEqualTo("Glizzy");
        assertThat(gameTwo.getPlayerLastName()).isEqualTo("Blizzy");
    }
}

