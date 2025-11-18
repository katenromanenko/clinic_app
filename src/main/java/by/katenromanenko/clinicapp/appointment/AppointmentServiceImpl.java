package by.katenromanenko.clinicapp.appointment;

import java.util.List;
import java.util.UUID;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getById(UUID id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment update(UUID id, Appointment appointment) {
        appointment.setAppointmentId(id);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(UUID id) {
        appointmentRepository.deleteById(id);
    }
}
