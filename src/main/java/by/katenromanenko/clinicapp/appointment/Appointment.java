package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.user.AppUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private UUID appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private AppUser patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private AppUser doctor;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Timeslot slot;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "cancellation_reason", length = 250)
    private String cancellationReason;

    @Column(name = "notification_sent", nullable = false)
    private boolean notificationSent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;
}
