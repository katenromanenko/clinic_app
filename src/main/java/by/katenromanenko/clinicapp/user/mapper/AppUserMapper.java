package by.katenromanenko.clinicapp.user.mapper;

import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "specialization.specId", target = "specializationId")
    AppUserDto toDto(AppUser entity);

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "specialization", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AppUser toEntity(AppUserDto dto);

    List<AppUserDto> toDtoList(List<AppUser> entities);
}
