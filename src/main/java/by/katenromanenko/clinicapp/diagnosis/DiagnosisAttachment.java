package by.katenromanenko.clinicapp.diagnosis;

import by.katenromanenko.clinicapp.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "diagnosis_attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisAttachment {

    @Id
    @Column(name = "attachment_id", nullable = false, updatable = false)
    private UUID attachmentId;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private DiagnosisAttachment parentRecord;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
