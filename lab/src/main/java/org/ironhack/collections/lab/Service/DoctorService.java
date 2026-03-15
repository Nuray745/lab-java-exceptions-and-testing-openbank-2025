package org.ironhack.collections.lab.Service;
import org.ironhack.collections.lab.Exception.DoctorNotFoundException;
import org.ironhack.collections.lab.Model.Doctor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    private List<Doctor> doctors = new ArrayList<>();

    public List<Doctor> findAll() {
        return doctors;
    }

    public Doctor findById(Long id) {
        return doctors.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new DoctorNotFoundException(id));
    }

    public Doctor save(Doctor doctor) {
        doctor.setId((long) (doctors.size() + 1));
        doctors.add(doctor);
        return doctor;
    }

    public Doctor update(Long id, Doctor doctor) {
        Doctor existing = findById(id);
        existing.setName(doctor.getName());
        existing.setSpecialty(doctor.getSpecialty());
        return existing;
    }
}