package ru.dariayo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.dariayo.controllers.CarController;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class)
@AutoConfigureMockMvc
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarCollection carRepository;

    private Car car;

    @BeforeEach
    public void setup() {
        car = new Car("Toyota", "Camry", 2024, 2000, "good");
    }

    @Test
    public void getCars_shouldReturnListOfCars() throws Exception {
        List<Car> cars = Arrays.asList(car);
        given(carRepository.getCars()).willReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mark").value(car.getMark()))
                .andExpect(jsonPath("$[0].model").value(car.getModel()));
    }

    @Test
    public void addCar_shouldAddCarSuccessfully() throws Exception {
        doNothing().when(carRepository).addCar(any(Car.class));

        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"mark\":\"Toyota\",\"model\":\"Camry\",\"year\":\"2024\",\"price\":\"2000\",\"condition\":\"good\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car added successfully"));
    }

    @Test
    public void updateCar_shouldUpdateCarSuccessfully() throws Exception {
        doNothing().when(carRepository).updateCar(anyString(), anyString(), any(Car.class));

        mockMvc.perform(put("/api/cars/update/Toyota/Camry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"mark\":\"Toyota\",\"model\":\"Camry\",\"year\":\"2025\",\"price\":\"20300\",\"condition\":\"good\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car updated successfully"));
    }

    @Test
    public void removeCar_shouldRemoveCarSuccessfully() throws Exception {
        given(carRepository.removeCar(anyString(), anyString())).willReturn(true);

        mockMvc.perform(delete("/api/cars/remove/Toyota/Camry"))
                .andExpect(status().isOk())
                .andExpect(content().string("Car removed successfully"));
    }

    @Test
    public void searchCar_shouldReturnListOfCars() throws Exception {
        List<Car> cars = Arrays.asList(car);
        given(carRepository.searchCar(anyString(), anyString())).willReturn(cars);

        mockMvc.perform(get("/api/cars/search")
                .param("param", "mark")
                .param("value", "Toyota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mark").value(car.getMark()))
                .andExpect(jsonPath("$[0].model").value(car.getModel()));
    }

    @Test
    @DisplayName("addCar should return 400 when input is invalid")
    public void addCar_shouldReturn400ForInvalidInput() throws Exception {
        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mark\":\"Toyota\",\"model\":\"\",\"year\":\"\",\"price\":\"\",\"condition\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("removeCar should return 404 when car is not found")
    public void removeCar_shouldReturn404WhenCarNotFound() throws Exception {
        given(carRepository.removeCar(anyString(), anyString())).willReturn(false);

        mockMvc.perform(delete("/api/cars/remove/Toyota/Camry"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Car not found"));
    }

    @Test
    @DisplayName("searchCar should return empty list when no cars found")
    public void searchCar_shouldReturnEmptyListWhenNoCarsFound() throws Exception {
        given(carRepository.searchCar(anyString(), anyString())).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cars/search")
                .param("param", "mark")
                .param("value", "NonExistingBrand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}