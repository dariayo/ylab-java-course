package ru.dariayo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import ru.dariayo.dto.CarDTO;
import ru.dariayo.model.Car;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO toCarDTO(Car car);

    List<CarDTO> toCarDTOList(List<Car> cars);
}
