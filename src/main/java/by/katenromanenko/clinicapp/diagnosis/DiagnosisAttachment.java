package by.katenromanenko.clinicapp.diagnosis;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "diagnosis_attachments")
public class DiagnosisAttachment {

    @Id
    @Column(name = "attachment_id", nullable = false, updatable = false)
    private UUID attachmentId;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
