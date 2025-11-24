package by.katenromanenko.clinicapp.diagnosis;

import by.katenromanenko.clinicapp.appointment.Appointment;
import by.katenromanenko.clinicapp.appointment.AppointmentRepository;
import by.katenromanenko.clinicapp.diagnosis.dto.DiagnosisDto;
import by.katenromanenko.clinicapp.diagnosis.mapper.DiagnosisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final AppointmentRepository appointmentRepository;
    private final DiagnosisMapper diagnosisMapper;

    @Override
    public DiagnosisDto create(DiagnosisDto dto) {
        DiagnosisAttachment entity = diagnosisMapper.toEntity(dto);

        entity.setAttachmentId(UUID.randomUUID());

        // находим приём
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Приём не найден: " + dto.getAppointmentId()));
        entity.setAppointment(appointment);

        // если передан parentRecordId — подтянем родителя
        if (dto.getParentRecordId() != null) {
            DiagnosisAttachment parent = findDiagnosis(dto.getParentRecordId());
            entity.setParentRecord(parent);
        }

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(null);

        DiagnosisAttachment saved = diagnosisRepository.save(entity);
        return diagnosisMapper.toDto(saved);
    }

    @Override
    public DiagnosisDto getById(UUID id) {
        DiagnosisAttachment entity = findDiagnosis(id);
        return diagnosisMapper.toDto(entity);
    }

    @Override
    public List<DiagnosisDto> getAll() {
        List<DiagnosisAttachment> list = diagnosisRepository.findAll();
        return diagnosisMapper.toDtoList(list);
    }

    @Override
    public DiagnosisDto update(UUID id, DiagnosisDto dto) {
        DiagnosisAttachment entity = findDiagnosis(id);

        if (dto.getText() != null) {
            entity.setText(dto.getText());
        }

        if (dto.getParentRecordId() != null) {
            DiagnosisAttachment parent = findDiagnosis(dto.getParentRecordId());
            entity.setParentRecord(parent);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        DiagnosisAttachment saved = diagnosisRepository.save(entity);
        return diagnosisMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        diagnosisRepository.deleteById(id);
    }

    private DiagnosisAttachment findDiagnosis(UUID id) {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Диагноз не найден: " + id));
    }
}
