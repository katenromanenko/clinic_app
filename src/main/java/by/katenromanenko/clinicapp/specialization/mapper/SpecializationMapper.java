package by.katenromanenko.clinicapp.specialization.mapper;

import by.katenromanenko.clinicapp.specialization.Specialization;
import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    @Mapping(source = "specId", target = "id")
    SpecializationDto toDto(Specialization entity);

    @Mapping(source = "id", target = "specId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Specialization toEntity(SpecializationDto dto);

    List<SpecializationDto> toDtoList(List<Specialization> entities);
}
