package ru.dariayo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import ru.dariayo.dto.CarDTO;
import ru.dariayo.model.Car;

/**
 * CarMapper - интерфейс, предоставляющий методы для преобразования
 * объектов между классами {@link CarDTO} и {@link CarDTO}
 * 
 * Этот интерфейс использует библиотеку MapStruct для автоматического
 * создания реализации во время компиляции.
 * 
 * Компонент модели, используемый MapStruct для внедрения этого маппера,
 * определяется как {@code MappingConstants.ComponentModel.DEFAULT}.
 * 
 * Реализация маппера доступна через {@link CarMapper#INSTANCE}.
 * 
 * @see CarDTO
 * @see CarDTO
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    /**
     * Преобразует объект {@link CarDTO} в объект {@link CarDTO}.
     * 
     * @param car объект {@link CarDTO} для преобразования
     * @return объект {@link CarDTO}, полученный из исходного объекта {@link CarDTO}
     */
    CarDTO carToCarDTO(Car car);

    /**
     * Преобразует объект {@link CarDTO} в объект {@link CarDTO}.
     * 
     * @param carDTO объект {@link CarDTO} для преобразования
     * @return объект {@link CarDTO}, полученный из исходного объекта {@link CarDTO}
     */
    Car carDTOToCar(CarDTO carDTO);
}
