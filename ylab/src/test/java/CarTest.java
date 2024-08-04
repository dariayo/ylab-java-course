
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CarTest {

    private CarCollection carCollection;
    private AuditLogRepository auditLogRepository;

    @BeforeEach
    public void setUp() {
        auditLogRepository = new AuditLogRepository();
        carCollection = new CarCollection(auditLogRepository);
    }

    @Test
    public void testAddCar() {
        Car car = new Car("Bmw", "M5", 2004, 4000, "Well");
        carCollection.addCar(car);

        Car cars = carCollection.getByMark("Bmw", "M5");
        assertThat(cars.getMark().equals("Bmw"));
    }

    @Test
    public void testRemoveCar() {
        Car car = new Car("Bmw", "M5", 2004, 4000, "Well");
        carCollection.addCar(car);
        carCollection.removeCar("Bmw", "M5");

        assertThat(carCollection.getByMark("Bmw", "M5")).isNull();
        ;
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car("Bmw", "M5", 2004, 4000, "Well");

        carCollection.updateCar("Bmw", "M5");
        ByteArrayInputStream mark = new ByteArrayInputStream("new".getBytes());
        InputStream inputStream = System.in;
        System.setIn(mark);
        ByteArrayInputStream model = new ByteArrayInputStream("new".getBytes());
        System.setIn(model);
        ByteArrayInputStream year = new ByteArrayInputStream("2".getBytes());
        System.setIn(year);
        ByteArrayInputStream price = new ByteArrayInputStream("2".getBytes());
        System.setIn(price);
        ByteArrayInputStream condition = new ByteArrayInputStream("new".getBytes());
        System.setIn(condition);
        assertThat(car.getMark().equals("new"));
        System.setIn(inputStream);

    }

}