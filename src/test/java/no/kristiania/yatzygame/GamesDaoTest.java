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
    void shouldDeleteGame() throws SQLException {
        GamesDao gameDao = new GamesDao(GamesDaoTest.createDataSource());
        Game game = new Game();

        game.setPlayerFirstName("Ola");
        game.setPlayerFirstName("Nordmann");
        game.setCategory("FIVES");
        game.setDiceSequence("1,2,5,4,1");
        game.setScore("5");

        assertThat(gameDao.listAll()).doesNotContain(game);
        gameDao.insert(game);

        assertThat(gameDao.listAll()).contains(game);
        gameDao.delete(game.getId());
        assertThat(gameDao.listAll()).doesNotContain(game);
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

    @Test
    void shouldListInsertGame() throws SQLException {
        Game game = new Game();
        game.setPlayerFirstName("Johannes");
        game.setPlayerLastName("Unknownn");
        game.setCategory("TWOS");
        game.setDiceSequence("1,2,1,2,1");
        game.setScore("4");

        gameDao.insert(game);

        assertThat(gameDao.listAll())
                .extracting(Game::getDiceSequence)
                .contains(game.getDiceSequence());

        assertThat(gameDao.listAll())
                .extracting(Game::getScore)
                .contains(game.getScore());

        assertThat(gameDao.listAll())
                .extracting(Game::getPlayerFirstName)
                .contains(game.getPlayerFirstName());

        assertThat(gameDao.listAll())
                .extracting(Game::getPlayerLastName)
                .contains(game.getPlayerLastName());

        assertThat(gameDao.listAll())
                .extracting(Game::getCategory)
                .contains(game.getCategory());
    }

    @Test
    public void shouldRetrieveAllGameField() throws SQLException {
        Game game = new Game();
        game.setPlayerFirstName("Per");
        game.setPlayerFirstName("Hemmingway");
        game.setCategory("FOURS");
        game.setDiceSequence("1,1,1,1,4");
        game.setScore("4");
        gameDao.insert(game);
        assertThat(gameDao.retrieve(game.getId()))
                .isEqualToComparingFieldByField(game);
    }

    @Test
    public void shouldBeEqual() throws SQLException {

        Game gameOne = new Game();
        Game gameTwo = new Game();

        gameOne.setPlayerFirstName("Tom");
        gameOne.setPlayerLastName("Bombadill");
        gameOne.setCategory("ONES");
        gameOne.setDiceSequence("1,2,3,4,4");
        gameOne.setScore("1");
        gameTwo.setPlayerFirstName("Muhammed");
        gameTwo.setPlayerLastName("Ali");
        gameTwo.setCategory("LARGE STRAIGHT");
        gameTwo.setDiceSequence("2,3,4,5,6");
        gameTwo.setScore("20");

        gameDao.insert(gameOne);
        gameDao.insert(gameTwo);

        assertThat(gameOne).isEqualTo(gameTwo);
        assertThat(gameOne.hashCode()).isEqualTo(gameTwo.hashCode());
    }

    @Test
    public void testToString() throws SQLException {
        Game game = new Game();
        game.setId(1l);
        game.setGameDate("2018-07-22");
        game.setPlayerFirstName("Mamma");
        game.setPlayerFirstName("Mia");
        game.setCategory("CHANCE");
        game.setDiceSequence("2,2,2,2,2");
        game.setScore("10");
        gameDao.insert(game);
        assertThat(game.toString()).isEqualTo("{Game" + "id=" + game.getId() + ", date=" + game.getGameDate() + ", score='" + game.getScore() + '\'' + ", playerFirstName='" + game.getPlayerFirstName() + ", playerLastName='" + game.getPlayerLastName() + '\'' + ", category='" + game.getCategory() + '\'' + ", diceSequence='" + game.getDiceSequence() + '\'' + '}');
    }

    @Test
    void shouldUpdatePlayer() throws SQLException {
        GamesDao gamesDao = new GamesDao(GamesDaoTest.createDataSource());
        Game game = new Game();
        game.setPlayerFirstName("Mia");
        game.setPlayerLastName("Miaasen");
        game.setCategory("TWOS");
        game.setDiceSequence("1,2,3,2,1");
        game.setScore("4");
        gamesDao.insert(game);

        assertThat(gamesDao.listAll())
                .extracting(Game::getPlayerFirstName)
                .contains("Mia");

        assertThat(gamesDao.listAll())
                .extracting(Game::getPlayerLastName)
                .contains("Miaasen");

        gamesDao.updatePlayerName(game.getId(), "Ola");
        gamesDao.updatePlayerName(game.getId(), "Nordmann");

        assertThat(gamesDao.listAll())
                .extracting(Game::getPlayerFirstName)
                .contains("Ola");
        assertThat(gamesDao.listAll())
                .extracting(Game::getPlayerFirstName)
                .contains("Nordmann");

    }

}
