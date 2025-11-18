package by.katenromanenko.clinicapp.diagnosis;

import by.katenromanenko.clinicapp.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "diagnosis_attachments")
public class Diagnosis {

    @Id
    @Column(name = "record_id", nullable = false, updatable = false)
    private UUID recordId;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @Column(name = "text", nullable = false, columnDefinition = "text")
    private String text;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
