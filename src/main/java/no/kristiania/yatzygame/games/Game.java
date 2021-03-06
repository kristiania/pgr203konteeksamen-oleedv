package no.kristiania.yatzygame.games;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game {

    private Long id;
    private String playerFirstName;
    private String playerLastName;
    private String gameName;
    private String score;
    private String diceSequence;
    private String category;
    private String gameDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(playerFirstName, game.playerFirstName) && Objects.equals(playerLastName, game.playerLastName) && Objects.equals(gameName, game.gameName) && Objects.equals(score, game.score) && Objects.equals(diceSequence, game.diceSequence) && Objects.equals(category, game.category) && Objects.equals(gameDate, game.gameDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerFirstName, playerLastName, gameName, score, diceSequence, category, gameDate);
    }

    public String getDiceSequence() {
        return diceSequence;
    }

    public void setDiceSequence(String diceSequence) {
        this.diceSequence = diceSequence;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    public String getPlayerLastName() {
        return playerLastName;
    }

    public void setPlayerLastName(String playerLastName) {
        this.playerLastName = playerLastName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    //calculating yatze score
    public String calculateScore(String fetchCategory, List<Integer> lst) {
        int categoryStringToInt = 0;
        switch (fetchCategory) {
            case "ONES":
                categoryStringToInt = 1;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "TWOS":
                categoryStringToInt = 2;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "THREES":
                categoryStringToInt = 3;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "FOURS":
                categoryStringToInt = 4;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "FIVES":
                categoryStringToInt = 5;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "SIXES":
                categoryStringToInt = 6;
                score = calculateForSingles(categoryStringToInt, lst);
                break;
            case "ONE PAIR":
                score = calculateForPairs(lst);
                break;
            case "TWO PAIRS":
                score = calculateForTwoPairs(lst);
                break;
            case "THREE OF A KIND":
                score = calculateForThreePairs(lst);
                break;
            case "FOUR OF A KIND":
                score = calculateFourOfAkind(lst);
                break;
            case "SMALL STRAIGHT":
                score = calculateForSmallStraight(lst);
                break;
            case "LARGE STRAIGHT":
                score = calculateForLargeStraight(lst);
                break;
            case "FULL HOUSE":
                score = calculateForFullHouse(lst);
                break;
            case "CHANCE":
                score = calculateForChance(lst);
                break;
            case "YATZY":
                score = calculateForYatzy(lst);
                break;
            default:
                throw new IllegalArgumentException("Category type not found");
        }
        return score;
    }

    private String calculateForSingles(int categoryStringToInt, List<Integer> lst) {
        int score;
        score = Collections.frequency(lst, categoryStringToInt) * categoryStringToInt;
        return Integer.toString(score);
    }

    private String calculateForYatzy(List<Integer> lst) {
        int score = 0;
        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) == 5) {
                score = 50;
                break;
            }
        }
        return Integer.toString(score);
    }

    private String calculateForChance(List<Integer> lst) {
        int score = 0;
        for (int diceValue : lst) {
            score += diceValue;
        }
        return Integer.toString(score);
    }

    private String calculateForLargeStraight(List<Integer> lst) {
        int score = 0;
        if (lst.contains(2) &&
                lst.contains(3) &&
                lst.contains(4) &&
                lst.contains(5) &&
                lst.contains(6)
        ) {
            score = 20;
        }
        return Integer.toString(score);
    }

    private String calculateForSmallStraight(List<Integer> lst) {
        int score = 0;
        if (lst.contains(1) &&
                lst.contains(2) &&
                lst.contains(3) &&
                lst.contains(4) &&
                lst.contains(5)
        ) {
            score = 15;
        }
        return Integer.toString(score);
    }

    private String calculateForFullHouse(List<Integer> lst) {
        int score = 0;
        int threesSum;
        int twosSum;

        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) > 2) {
                threesSum = j * 3;
                for (int k = 6; k > 0; k--) {
                    if (Collections.frequency(lst, k) > 1 && k != j) {
                        twosSum = k * 2;
                        score = threesSum + twosSum;
                        break;
                    }
                }
                break;
            }
        }
        return Integer.toString(score);
    }

    private String calculateFourOfAkind(List<Integer> lst) {
        int score = 0;
        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) > 3) {
                score = j * 4;
                break;
            }
        }
        return Integer.toString(score);
    }

    private String calculateForThreePairs(List<Integer> lst) {
        int score = 0;
        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) > 2) {
                score = j * 3;
                break;
            }
        }
        return Integer.toString(score);
    }


    private String calculateForPairs(List<Integer> lst) {
        int score = 0;
        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) > 1) {
                score = j * 2;
                break;
            }
        }
        return Integer.toString(score);
    }

    private String calculateForTwoPairs(List<Integer> lst) {
        int score = 0;
        int firstPairSum;
        int secondPairSum;

        for (int j = 6; j > 0; j--) {
            if (Collections.frequency(lst, j) > 1) {
                firstPairSum = j * 2;
                for (int k = j - 1; k > 0; k--) {
                    if (Collections.frequency(lst, k) > 1) {
                        secondPairSum = k * 2;
                        score = firstPairSum + secondPairSum;
                        break;
                    }
                }
                break;
            }
        }
        return Integer.toString(score);
    }
}

