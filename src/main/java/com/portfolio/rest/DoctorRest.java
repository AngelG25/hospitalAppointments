package com.portfolio.rest;

import com.portfolio.api.DoctorApi;
import com.portfolio.api.models.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorRest {

  private final DoctorApi doctorApi;

  @GetMapping("/")
  public List<Doctor> getAllDoctors() {
    return doctorApi.getDoctors();
  }

  @GetMapping("/{idDoctor}")
  public Doctor getDoctorById(@PathVariable String idDoctor) {
    return doctorApi.getDoctorById(idDoctor);
  }

  @PostMapping("/")
  public void createDoctor(@RequestBody Doctor doctor) {
    doctorApi.createDoctor(doctor);
  }

  @PutMapping("/")
  public void updateDoctor(@RequestBody Doctor doctor) {
    doctorApi.updateDoctor(doctor);
  }

  @DeleteMapping("/{idDoctor}")
  public void deleteDoctor(@PathVariable String idDoctor) {
    doctorApi.deleteDoctor(idDoctor);
  }

}
