package com.portfolio.rest;

import com.portfolio.api.PatientApi;
import com.portfolio.api.models.Patient;
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
@RequestMapping("/patients")
@CrossOrigin
public class PatientRest {

  private final PatientApi patientApi;

  @GetMapping("/")
  public List<Patient> getPatients() {
    return patientApi.getPatients();
  }

  @GetMapping("/{idPatient}")
  public Patient getPatientById(@PathVariable String idPatient) {
    return patientApi.getPatientById(idPatient);
  }

  @PostMapping("/")
  public void createPatient(@RequestBody Patient patient) {
    patientApi.createPatient(patient);
  }

  @PutMapping("/")
  public void updatePatient(@RequestBody Patient patient) {
    patientApi.updatePatient(patient);
  }

  @DeleteMapping("/{idPatient}")
  public void deletePatient(@PathVariable String idPatient) {
    patientApi.deletePatient(idPatient);
  }
}
