package by.katenromanenko.clinicapp.schedule.mapper;

import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeslotMapper {

    @Mapping(source = "slotId",      target = "id")
    @Mapping(source = "doctor.userId", target = "doctorId")
    TimeslotDto toDto(Timeslot entity);

    @Mapping(target = "slotId",      ignore = true)
    @Mapping(target = "doctor",      ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    @Mapping(target = "updatedAt",   ignore = true)
    Timeslot toEntity(TimeslotDto dto);

    List<TimeslotDto> toDtoList(List<Timeslot> entities);
}
