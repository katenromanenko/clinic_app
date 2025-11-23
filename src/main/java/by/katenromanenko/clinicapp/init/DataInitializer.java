package by.katenromanenko.clinicapp.init;

import by.katenromanenko.clinicapp.specialization.SpecializationService;
import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;
import by.katenromanenko.clinicapp.user.AppUserService;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import by.katenromanenko.clinicapp.user.UserRole;
import by.katenromanenko.clinicapp.user.SexType;
import by.katenromanenko.clinicapp.schedule.TimeslotService;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import by.katenromanenko.clinicapp.schedule.TimeslotState;
import by.katenromanenko.clinicapp.appointment.AppointmentService;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SpecializationService specializationService;
    private final AppUserService appUserService;
    private final TimeslotService timeslotService;
    private final AppointmentService appointmentService;

    public DataInitializer(SpecializationService specializationService,
                           AppUserService appUserService,
                           TimeslotService timeslotService,
                           AppointmentService appointmentService) {
        this.specializationService = specializationService;
        this.appUserService = appUserService;
        this.timeslotService = timeslotService;
        this.appointmentService = appointmentService;
    }

    @Override
    public void run(String... args) {

        // 1. специализации
        SpecializationDto spec1 = new SpecializationDto();
        spec1.setName("Терапевт");
        spec1.setDescription("Терапия");
        spec1 = specializationService.create(spec1);

        SpecializationDto spec2 = new SpecializationDto();
        spec2.setName("Невролог");
        spec2.setDescription("Неврология");
        spec2 = specializationService.create(spec2);

        // 2. врачи
        AppUserDto doctor1 = new AppUserDto();
        doctor1.setLogin("doctor1");
        doctor1.setPasswordHash("123"); // пароль обязателен
        doctor1.setFirstName("Екатерина");
        doctor1.setLastName("Романенко");
        doctor1.setEmail("doctor1@gmail.com");
        doctor1.setRole(UserRole.DOCTOR);
        doctor1.setSpecializationId(spec1.getId());
        doctor1 = appUserService.create(doctor1);

        AppUserDto doctor2 = new AppUserDto();
        doctor2.setLogin("doctor2");
        doctor2.setPasswordHash("123");
        doctor2.setFirstName("Игорь");
        doctor2.setLastName("Бородачев");
        doctor2.setEmail("doctor2@gmail.com");
        doctor2.setRole(UserRole.DOCTOR);
        doctor2.setSpecializationId(spec2.getId());
        doctor2 = appUserService.create(doctor2);

        // 3. пациенты
        AppUserDto patient1 = new AppUserDto();
        patient1.setLogin("patient1");
        patient1.setPasswordHash("123");
        patient1.setFirstName("Инна");
        patient1.setLastName("Мелешко");
        patient1.setEmail("patient1@gmail.com");
        patient1.setRole(UserRole.PATIENT);
        patient1.setBirthDate(LocalDate.of(1990, 1, 10));
        patient1.setSex(SexType.FEMALE);
        patient1 = appUserService.create(patient1);

        AppUserDto patient2 = new AppUserDto();
        patient2.setLogin("patient2");
        patient2.setPasswordHash("123");
        patient2.setFirstName("Виктор");
        patient2.setLastName("Наказнюк");
        patient2.setEmail("patient2@gmail.com");
        patient2.setRole(UserRole.PATIENT);
        patient2.setBirthDate(LocalDate.of(1985, 5, 20));
        patient2.setSex(SexType.MALE);
        patient2 = appUserService.create(patient2);

        // 4. слоты
        TimeslotDto slot1 = new TimeslotDto();
        slot1.setDoctorId(doctor1.getId());
        slot1.setStartTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        slot1.setEndTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
        slot1.setState(TimeslotState.AVAILABLE);
        slot1 = timeslotService.create(slot1);

        TimeslotDto slot2 = new TimeslotDto();
        slot2.setDoctorId(doctor2.getId());
        slot2.setStartTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        slot2.setEndTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(30));
        slot2.setState(TimeslotState.AVAILABLE);
        slot2 = timeslotService.create(slot2);

        // 5. приёмы
        AppointmentDto app1 = new AppointmentDto();
        app1.setPatientId(patient1.getId());
        app1.setDoctorId(doctor1.getId());
        app1.setSlotId(slot1.getId());
        app1.setStartAt(slot1.getStartTime());
        app1.setDurationMin(30);
        app1.setStatus(AppointmentStatus.SCHEDULED);
        appointmentService.create(app1);

        AppointmentDto app2 = new AppointmentDto();
        app2.setPatientId(patient2.getId());
        app2.setDoctorId(doctor2.getId());
        app2.setSlotId(slot2.getId());
        app2.setStartAt(slot2.getStartTime());
        app2.setDurationMin(30);
        app2.setStatus(AppointmentStatus.SCHEDULED);
        appointmentService.create(app2);
    }
}
