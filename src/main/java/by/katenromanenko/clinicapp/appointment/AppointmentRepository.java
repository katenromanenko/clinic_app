package by.katenromanenko.clinicapp.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query(value = """
        select exists(
            select 1
            from appointments a
            where a.doctor_id = :doctorId
              and a.status <> 'CANCELED'
              and a.start_at < :endAt
              and (a.start_at + make_interval(mins => a.duration_min)) > :startAt
        )
        """, nativeQuery = true)
    boolean existsDoctorOverlap(
            @Param("doctorId") UUID doctorId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );

    @Query(value = """
        select exists(
            select 1
            from appointments a
            where a.doctor_id = :doctorId
              and a.appointment_id <> :appointmentId
              and a.status <> 'CANCELED'
              and a.start_at < :endAt
              and (a.start_at + make_interval(mins => a.duration_min)) > :startAt
        )
        """, nativeQuery = true)
    boolean existsDoctorOverlapExcludingSelf(
            @Param("doctorId") UUID doctorId,
            @Param("appointmentId") UUID appointmentId,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt
    );
}
