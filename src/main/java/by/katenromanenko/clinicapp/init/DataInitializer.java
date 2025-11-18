package by.katenromanenko.clinicapp.init;

import by.katenromanenko.clinicapp.appointment.Appointment;
import by.katenromanenko.clinicapp.appointment.AppointmentRepository;
import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import by.katenromanenko.clinicapp.diagnosis.Diagnosis;
import by.katenromanenko.clinicapp.diagnosis.DiagnosisAttachment;
import by.katenromanenko.clinicapp.diagnosis.DiagnosisAttachmentRepository;
import by.katenromanenko.clinicapp.diagnosis.DiagnosisRepository;
import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.schedule.TimeslotRepository;
import by.katenromanenko.clinicapp.schedule.TimeslotState;
import by.katenromanenko.clinicapp.specialization.Specialization;
import by.katenromanenko.clinicapp.specialization.SpecializationRepository;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import by.katenromanenko.clinicapp.user.SexType;
import by.katenromanenko.clinicapp.user.UserRole;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DataInitializer implements CommandLineRunner {

    private final SpecializationRepository specializationRepository;
    private final AppUserRepository appUserRepository;
    private final TimeslotRepository timeslotRepository;
    private final AppointmentRepository appointmentRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisAttachmentRepository diagnosisAttachmentRepository;

    public DataInitializer(SpecializationRepository specializationRepository,
                           AppUserRepository appUserRepository,
                           TimeslotRepository timeslotRepository,
                           AppointmentRepository appointmentRepository,
                           DiagnosisRepository diagnosisRepository,
                           DiagnosisAttachmentRepository diagnosisAttachmentRepository) {
        this.specializationRepository = specializationRepository;
        this.appUserRepository = appUserRepository;
        this.timeslotRepository = timeslotRepository;
        this.appointmentRepository = appointmentRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisAttachmentRepository = diagnosisAttachmentRepository;
    }

    @Override
    public void run(String... args) {

        // 1. специализации
        Specialization spec1 = new Specialization();
        spec1.setSpecId(UUID.randomUUID());
        spec1.setName("Терапевт");
        spec1.setDescription("Терапия");
        spec1.setCreatedAt(LocalDateTime.now());

        Specialization spec2 = new Specialization();
        spec2.setSpecId(UUID.randomUUID());
        spec2.setName("Невролог");
        spec2.setDescription("Неврология");
        spec2.setCreatedAt(LocalDateTime.now());

        spec1 = specializationRepository.save(spec1);
        spec2 = specializationRepository.save(spec2);

        // 2. врачи
        AppUser doctor1 = new AppUser();
        doctor1.setUserId(UUID.randomUUID());
        doctor1.setLogin("doctor1");
        doctor1.setPasswordHash("hash1");
        doctor1.setRole(UserRole.DOCTOR);
        doctor1.setFirstName("Екатерина");
        doctor1.setLastName("Романенко");
        doctor1.setEmail("doctor1@egmail.com");
        doctor1.setPhone("+375291111111");
        doctor1.setSpecialization(spec1);
        doctor1.setOffice("101");
        doctor1.setWorkHours("Пн-Пт 09:00-17:00");
        doctor1.setCreatedAt(LocalDateTime.now());
        doctor1.setActive(true);

        AppUser doctor2 = new AppUser();
        doctor2.setUserId(UUID.randomUUID());
        doctor2.setLogin("doctor2");
        doctor2.setPasswordHash("hash2");
        doctor2.setRole(UserRole.DOCTOR);
        doctor2.setFirstName("Игорь");
        doctor2.setLastName("Бородачев");
        doctor2.setEmail("doctor2@gmail.com");
        doctor2.setPhone("+375292222222");
        doctor2.setSpecialization(spec2);
        doctor2.setOffice("102");
        doctor2.setWorkHours("Пн-Ср 10:00-18:00");
        doctor2.setCreatedAt(LocalDateTime.now());
        doctor2.setActive(true);

        doctor1 = appUserRepository.save(doctor1);
        doctor2 = appUserRepository.save(doctor2);

        // 3. пациенты
        AppUser patient1 = new AppUser();
        patient1.setUserId(UUID.randomUUID());
        patient1.setLogin("patient1");
        patient1.setPasswordHash("phash1");
        patient1.setRole(UserRole.PATIENT);
        patient1.setFirstName("Инна");
        patient1.setLastName("Мелешко");
        patient1.setEmail("patient1@gmail.com");
        patient1.setPhone("+375293333333");
        patient1.setBirthDate(LocalDate.of(1990, 1, 10));
        patient1.setSex(SexType.FEMALE);
        patient1.setCreatedAt(LocalDateTime.now());
        patient1.setActive(true);

        AppUser patient2 = new AppUser();
        patient2.setUserId(UUID.randomUUID());
        patient2.setLogin("patient2");
        patient2.setPasswordHash("phash2");
        patient2.setRole(UserRole.PATIENT);
        patient2.setFirstName("Виктор");
        patient2.setLastName("Наказнюк");
        patient2.setEmail("patient2@gmail.com");
        patient2.setPhone("+375294444444");
        patient2.setBirthDate(LocalDate.of(1985, 5, 20));
        patient2.setSex(SexType.MALE);
        patient2.setCreatedAt(LocalDateTime.now());
        patient2.setActive(true);

        patient1 = appUserRepository.save(patient1);
        patient2 = appUserRepository.save(patient2);

        // 4. слоты
        Timeslot slot1 = new Timeslot();
        slot1.setSlotId(UUID.randomUUID());
        slot1.setDoctor(doctor1);
        slot1.setStartTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        slot1.setEndTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
        slot1.setState(TimeslotState.AVAILABLE);
        slot1.setBlocked(false);
        slot1.setCreatedAt(LocalDateTime.now());

        Timeslot slot2 = new Timeslot();
        slot2.setSlotId(UUID.randomUUID());
        slot2.setDoctor(doctor2);
        slot2.setStartTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        slot2.setEndTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(30));
        slot2.setState(TimeslotState.AVAILABLE);
        slot2.setBlocked(false);
        slot2.setCreatedAt(LocalDateTime.now());

        slot1 = timeslotRepository.save(slot1);
        slot2 = timeslotRepository.save(slot2);

        // 5. приёмы
        Appointment app1 = new Appointment();
        app1.setAppointmentId(UUID.randomUUID());
        app1.setPatient(patient1);
        app1.setDoctor(doctor1);
        app1.setSlot(slot1);
        app1.setStartAt(slot1.getStartTime());
        app1.setDurationMin(30);
        app1.setStatus(AppointmentStatus.SCHEDULED);
        app1.setNotificationSent(false);
        app1.setCreatedAt(LocalDateTime.now());

        Appointment app2 = new Appointment();
        app2.setAppointmentId(UUID.randomUUID());
        app2.setPatient(patient2);
        app2.setDoctor(doctor2);
        app2.setSlot(slot2);
        app2.setStartAt(slot2.getStartTime());
        app2.setDurationMin(30);
        app2.setStatus(AppointmentStatus.SCHEDULED);
        app2.setNotificationSent(false);
        app2.setCreatedAt(LocalDateTime.now());

        app1 = appointmentRepository.save(app1);
        app2 = appointmentRepository.save(app2);

        // 6. диагнозы
        Diagnosis diag1 = new Diagnosis();
        diag1.setRecordId(UUID.randomUUID());
        diag1.setAppointment(app1);
        diag1.setText("ОРВИ");
        diag1.setCreatedAt(LocalDateTime.now());

        Diagnosis diag2 = new Diagnosis();
        diag2.setRecordId(UUID.randomUUID());
        diag2.setAppointment(app2);
        diag2.setText("Головная боль напряжения");
        diag2.setCreatedAt(LocalDateTime.now());

        diag1 = diagnosisRepository.save(diag1);
        diag2 = diagnosisRepository.save(diag2);

    }
}
