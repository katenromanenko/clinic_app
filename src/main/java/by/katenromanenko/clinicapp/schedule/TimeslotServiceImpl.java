package by.katenromanenko.clinicapp.schedule;

import java.util.List;
import java.util.UUID;

public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;

    public TimeslotServiceImpl(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    @Override
    public Timeslot create(Timeslot timeslot) {
        return timeslotRepository.save(timeslot);
    }

    @Override
    public Timeslot getById(UUID id) {
        return timeslotRepository.findById(id).orElse(null);
    }

    @Override
    public List<Timeslot> getAll() {
        return timeslotRepository.findAll();
    }

    @Override
    public Timeslot update(UUID id, Timeslot timeslot) {
        timeslot.setSlotId(id);
        return timeslotRepository.save(timeslot);
    }

    @Override
    public void delete(UUID id) {
        timeslotRepository.deleteById(id);
    }
}
