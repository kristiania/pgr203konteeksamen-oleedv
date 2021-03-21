package no.kristiania.yatzygame.games;

import no.kristiania.yatzygame.AbstractDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GamesDao extends AbstractDao<Game> {
    public GamesDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(Game game) throws SQLException {
        long id = insert(game, "insert into games (playerFirstName, playerLastName, gameDate, gameName,  category, dice, score) values(?,?,?,?,?,?,?)");
        game.setId(id);
    }
    public Game getExistingGamePlayer(String playerFirstName, String playerLastName) throws SQLException {
        return gamesBasedOnFNameAndLName(playerFirstName, playerLastName, "select * from games where playerFirstName = ? and playerLastName = ?");
    }
    public void update(Long id, String description) throws SQLException {
        update(description, id, "update games set gameName = ? where id = ?");
    }

    public void updatePlayerName(Long id, String playerName) throws SQLException {
        update(playerName, id, "update games set playerLastName = ? where id = ?");

    }

    public void updateGameDetails(Long id, String category, String dice, String score) throws SQLException {
        updateGameDetail(category, dice, score, id, "update games set category = ?, dice = ?, score = ? where id = ?");


    }

    public Game retrieve(Long id) throws SQLException {
        return retrieve(id, "select * from games where id = ?");
    }

    public List<Game> listAll() throws SQLException {
        return listAll("select * from games");
    }

    @Override
    protected void mapToStatement(Game game, PreparedStatement statement) throws SQLException {
        statement.setString(1, game.getPlayerFirstName());
        statement.setString(2, game.getPlayerLastName());
        statement.setString(3, game.getGameDate());
        statement.setString(4, game.getGameName());
        statement.setString(5, game.getDiceSequence());
        statement.setString(6, game.getScore());
        statement.setString(7, game.getCategory());


    }


    @Override
    protected Game mapFromResultSet(ResultSet resultSet) throws SQLException {
        Game game = new Game();
        game.setId(resultSet.getLong("id"));
        game.setPlayerFirstName(resultSet.getString("playerFirstName"));
        game.setPlayerLastName(resultSet.getString("PlayerLastName"));
        game.setGameDate(resultSet.getString("gameDate"));
        game.setGameName(resultSet.getString("gameName"));
        game.setCategory(resultSet.getString("category"));
        game.setDiceSequence(resultSet.getString("dice"));
        game.setScore(resultSet.getString("score"));
        return game;
    }


    public Game retrieve(Long id, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapFromResultSet(resultSet);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public void delete(long id) throws SQLException {
        delete(id, "delete from games where id = ?");
    }
}
