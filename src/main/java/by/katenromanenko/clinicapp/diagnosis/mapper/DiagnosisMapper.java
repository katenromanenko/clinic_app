package by.katenromanenko.clinicapp.diagnosis.mapper;

import by.katenromanenko.clinicapp.diagnosis.DiagnosisAttachment;
import by.katenromanenko.clinicapp.diagnosis.dto.DiagnosisDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosisMapper {

    @Mapping(source = "attachmentId",             target = "id")
    @Mapping(source = "appointment.appointmentId", target = "appointmentId")
    @Mapping(source = "parentRecord.attachmentId", target = "parentRecordId")
    DiagnosisDto toDto(DiagnosisAttachment entity);

    @Mapping(target = "attachmentId", ignore = true)
    @Mapping(target = "appointment",  ignore = true)
    @Mapping(target = "parentRecord", ignore = true)
    @Mapping(target = "createdAt",    ignore = true)
    @Mapping(target = "updatedAt",    ignore = true)
    DiagnosisAttachment toEntity(DiagnosisDto dto);

    List<DiagnosisDto> toDtoList(List<DiagnosisAttachment> entities);
}
