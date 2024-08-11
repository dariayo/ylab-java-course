import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
    private PersonCollection personCollection;
    private AuditLogRepository auditLogRepository;

    // @BeforeEach
    // public void setUp() {
    //     auditLogRepository = new AuditLogRepository();
    //     personCollection = new PersonCollection(auditLogRepository);
    // }

    // @Test
    // public void testAddCar() {
    //     Person person = new Person("Mike", "1234", "user", "mike@gmail.com");
    //     personCollection.addPerson(person);

    //     Person person2 = personCollection.getPerson();
    //     assertThat(person2.getName().equals("Mike"));
    // }

    // @Test
    // public void testLogin() {
    //     Person person = new Person("Mike", "1234", "user", "mike@gmail.com");
    //     personCollection.addPerson(person);

    //     personCollection.checkUser("Mike", "1234");
    //     assertThat(personCollection.getPerson().getName().equals("Mike"));
    // }
}
