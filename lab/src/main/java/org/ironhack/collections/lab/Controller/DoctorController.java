package org.ironhack.collections.lab.Controller;


import jakarta.validation.Valid;
import org.ironhack.collections.lab.Exception.DoctorNotFoundException;
import org.ironhack.collections.lab.Model.Doctor;
import org.ironhack.collections.lab.Service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.findById(id);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody @Valid Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody @Valid Doctor doctor) {
        return doctorService.update(id, doctor);
    }

    // Exception handler
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<String> handleDoctorNotFound(DoctorNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}