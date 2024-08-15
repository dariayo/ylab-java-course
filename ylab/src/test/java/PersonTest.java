import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import ru.dariayo.LiquibaseManager;
import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

public class PersonTest {
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:16-alpine"));

    private PersonCollection personCollection;
    private AuditLogRepository auditLogRepository;
    private DBManager dbManager;

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    public void setUp() {
        LiquibaseManager liquibaseManager = new LiquibaseManager();
        liquibaseManager.createBase();
        auditLogRepository = new AuditLogRepository();
        dbManager = new DBManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        personCollection = new PersonCollection(auditLogRepository, dbManager);
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @Test
    public void shouldLoginPerson() {
        Person person = new Person("Mike", "1234", "user", "mike@gmail.com");
        try {
            personCollection.addPerson(person);

            personCollection.checkUser("Mike", "1234");
            assertThat(personCollection.getPerson().getName().equals("Mike"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFindUser() {
        Person person = new Person("Katrin", "1234", "user", "katrin@gmail.com");
        try {
            personCollection.addPerson(person);
            assertTrue(personCollection.userExist(person));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
