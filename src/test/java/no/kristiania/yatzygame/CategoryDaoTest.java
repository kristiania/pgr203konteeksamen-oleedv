package no.kristiania.yatzygame;

import no.kristiania.yatzygame.category.Category;
import no.kristiania.yatzygame.category.CategoryDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDaoTest {

    private static final Random random = new Random();
    private CategoryDao dao;

    static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:yatzyGameTest;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    static Category testCategory() {
        Category category = new Category();
        category.setName(pickOne(new String[]{"get categories", "ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES"}));
        return category;
    }

    private static String pickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = createDataSource();
        dao = new CategoryDao(dataSource);
    }

    @Test
    void shouldListCategory() throws SQLException {
        Category category = testCategory();
        dao.insert(category);
        assertThat(dao.listAll())
                .extracting(Category::getName)
                .contains(category.getName());
    }

    @Test
    public void shouldRetrieveCategory() throws SQLException {
        Category category = testCategory();
        dao.insert(category);
        assertThat(category).hasNoNullFieldsOrProperties();
        assertThat(dao.retrieve(category.getId()))
                .isEqualToComparingFieldByField(category);
    }

    @Test
    public void testString() throws SQLException {
        Category category = new Category();
        category.setName("Get Categories");
        dao.insert(category);
        assertThat(category.toString()).isEqualTo("Category{" +
                "id=" + category.getId() +
                ", name='" + category.getName() + '\'' +
                '}');
    }
}
