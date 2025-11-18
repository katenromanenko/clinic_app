package by.katenromanenko.clinicapp.schedule;

import by.katenromanenko.clinicapp.user.AppUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "timeslots")
public class Timeslot {

    @Id
    @Column(name = "slot_id", nullable = false, updatable = false)
    private UUID slotId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private AppUser doctor;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private TimeslotState state;

    @Column(name = "is_blocked", nullable = false)
    private boolean blocked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
