package no.kristiania.yatzygame;

import no.kristiania.yatzygame.games.Game;
import no.kristiania.yatzygame.games.GamesDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GamesDaoTest {

    private GamesDao gameDao;

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:yatzyGameTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        gameDao = new GamesDao(dataSource);
    }


    @Test
    public void calculateScore() throws SQLException {
        Game game = new Game();
        List<Integer> lst = new ArrayList<>();

        lst.add(2);
        lst.add(2);
        lst.add(2);
        lst.add(2);
        lst.add(1);

        assertThat(game.calculateScore("ONES", lst)).isEqualTo(Integer.toString(1));
        assertThat(game.calculateScore("TWOS", lst)).isEqualTo(Integer.toString(8));
        assertThat(game.calculateScore("THREES", lst)).isEqualTo(Integer.toString(0));
        assertThat(game.calculateScore("FOURS", lst)).isEqualTo(Integer.toString(0));

    }

//    @Test
//    void shouldListInsertGame() throws SQLException {
//        Game game = new Game();
//        game.setPlayerFirstName("Johannes");
//        game.setPlayerLastName("Unknownn");
//        game.setCategory("TWOS");
//        game.setDiceSequence("1,2,1,2,1");
//        game.setScore("4");
//
//        gameDao.insert(game);
//
//        assertThat(gameDao.listAll())
//                .extracting(Game::getDiceSequence)
//                .contains(game.getDiceSequence());
//
//        assertThat(gameDao.listAll())
//                .extracting(Game::getScore)
//                .contains(game.getScore());
//
//        assertThat(gameDao.listAll())
//                .extracting(Game::getPlayerFirstName)
//                .contains(game.getPlayerFirstName());
//
//        assertThat(gameDao.listAll())
//                .extracting(Game::getPlayerLastName)
//                .contains(game.getPlayerLastName());
//
//        assertThat(gameDao.listAll())
//                .extracting(Game::getCategory)
//                .contains(game.getCategory());
//    }
//
//    @Test
//    public void shouldRetrieveAllGameField() throws SQLException {
//        Game game = new Game();
//        game.setId(1L);
//        game.setPlayerFirstName("Tom");
//        game.setPlayerLastName("Bombadill");
//        game.setGameName("First game");
//        game.setScore("20");
//        game.setDiceSequence("2,3,4,5,6");
//        game.setCategory("LARGE STRAIGHT");
//        game.setGameDate("2021-03-26");
//        gameDao.insert(game);
//        assertThat(gameDao.retrieve(game.getId()))
//                .isEqualToComparingFieldByField(game);
//    }
//
//    @Test
//    public void shouldBeEqual() throws SQLException {
//
//        Game gameOne = new Game();
//
//        gameOne.setId(1L);
//        gameOne.setPlayerFirstName("Tom");
//        gameOne.setPlayerLastName("Bombadill");
//        gameOne.setGameName("First game");
//        gameOne.setScore("20");
//        gameOne.setDiceSequence("2,3,4,5,6");
//        gameOne.setCategory("LARGE STRAIGHT");
//        gameOne.setGameDate("2021-03-26");
//        gameDao.insert(gameOne);
//
//        Game gameTwo = new Game();
//        gameTwo.setId(1L);
//        gameTwo.setPlayerFirstName("Tom");
//        gameTwo.setPlayerLastName("Bombadill");
//        gameTwo.setGameName("First game");
//        gameTwo.setScore("20");
//        gameTwo.setDiceSequence("2,3,4,5,6");
//        gameTwo.setCategory("LARGE STRAIGHT");
//        gameTwo.setGameDate("2021-03-26");
//        gameDao.insert(gameTwo);
//
//        assertThat(gameOne).isEqualTo(gameTwo);
//        assertThat(gameOne.hashCode()).isEqualTo(gameTwo.hashCode());
//    }

}
