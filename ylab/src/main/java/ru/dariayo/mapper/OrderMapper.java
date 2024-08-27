package ru.dariayo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import ru.dariayo.dto.OrderDTO;
import ru.dariayo.model.Order;

/**
 * OrderMapper - интерфейс, предоставляющий методы для преобразования
 * объектов между классами {@link Order} и {@link OrderDTO}
 * 
 * Этот интерфейс использует библиотеку MapStruct для автоматического
 * создания реализации во время компиляции.
 * 
 * Компонент модели, используемый MapStruct для внедрения этого маппера,
 * определяется как {@code MappingConstants.ComponentModel.DEFAULT}.
 * 
 * Реализация маппера доступна через {@link OrderMapper#INSTANCE}.
 * 
 * @see Order
 * @see OrderDTO
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Преобразует объект {@link Order} в объект {@link OrderDTO}.
     * 
     * @param Order объект {@link Order} для преобразования
     * @return объект {@link OrderDTO}, полученный из исходного объекта
     *         {@link Order}
     */
    OrderDTO toOrderDTO(Order order);

    /**
     * Преобразует объект {@link OrderDTO} в объект {@link Order}.
     * 
     * @param OrderDTO объект {@link OrderDTO} для преобразования
     * @return объект {@link Order}, полученный из исходного объекта
     *         {@link OrderDTO}
     */
    List<OrderDTO> toOrderDTOList(List<Order> orders);
}
