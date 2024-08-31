package ru.dariayo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import ru.dariayo.dto.UserDTO;
import ru.dariayo.model.Person;

/**
 * UserMapper - интерфейс, предоставляющий методы для преобразования
 * объектов между классами {@link User} и {@link UserDTO}
 * 
 * Этот интерфейс использует библиотеку MapStruct для автоматического
 * создания реализации во время компиляции.
 * 
 * Компонент модели, используемый MapStruct для внедрения этого маппера,
 * определяется как {@code MappingConstants.ComponentModel.DEFAULT}.
 * 
 * Реализация маппера доступна через {@link UserMapper#INSTANCE}.
 * 
 * @see User
 * @see UserDTO
 */
@Mapper(componentModel = MappingConstants.ComponentModel.DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Преобразует объект {@link User} в объект {@link UserDTO}.
     * 
     * @param User объект {@link User} для преобразования
     * @return объект {@link UserDTO}, полученный из исходного объекта
     *         {@link User}
     */
     UserDTO personToPersonDTO(Person person);

    Person personDTOToPerson(UserDTO personDTO);

}
