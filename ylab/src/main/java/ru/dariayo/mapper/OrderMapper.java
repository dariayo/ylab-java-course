package ru.dariayo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import ru.dariayo.dto.OrderDTO;
import ru.dariayo.model.Order;

@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOList(List<Order> orders);
}
