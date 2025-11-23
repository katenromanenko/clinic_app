package by.katenromanenko.clinicapp.appointment.mapper;

import by.katenromanenko.clinicapp.appointment.Appointment;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "appointmentId",   target = "id")
    @Mapping(source = "patient.userId",  target = "patientId")
    @Mapping(source = "doctor.userId",   target = "doctorId")
    @Mapping(source = "slot.slotId",     target = "slotId")
    AppointmentDto toDto(Appointment entity);

    @Mapping(source = "id",              target = "appointmentId")
    @Mapping(target = "patient",         ignore = true)
    @Mapping(target = "doctor",          ignore = true)
    @Mapping(target = "slot",            ignore = true)
    Appointment toEntity(AppointmentDto dto);

    List<AppointmentDto> toDtoList(List<Appointment> entities);
}


