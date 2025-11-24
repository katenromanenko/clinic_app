package by.katenromanenko.clinicapp.user.mapper;

import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(source = "userId", target = "id")
    AppUserDto toDto(AppUser entity);

    @Mapping(source = "id", target = "userId")
    AppUser toEntity(AppUserDto dto);

    List<AppUserDto> toDtoList(List<AppUser> entities);
}
