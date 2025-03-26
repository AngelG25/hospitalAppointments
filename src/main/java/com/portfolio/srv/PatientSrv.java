package com.portfolio.srv;

import com.portfolio.api.PatientApi;
import com.portfolio.api.exceptions.DoctorNotFoundException;
import com.portfolio.api.exceptions.PatientNotFoundException;
import com.portfolio.api.models.Patient;
import com.portfolio.dao.DoctorDao;
import com.portfolio.dao.PatientDao;
import com.portfolio.repositories.DoctorRepository;
import com.portfolio.repositories.PatientRepository;
import com.portfolio.srv.utils.PatientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
@ApplicationScope
public class PatientSrv implements PatientApi {

  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;
  private final DoctorRepository doctorRepository;

  @Override
  public void createPatient(Patient patient) {
    List<DoctorDao> doctors = patient.getDoctors()
        .stream()
        .map(doctor -> doctorRepository.findById(doctor.getIdDoctor())
            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found")))
        .map(doctorRepository::save)
        .toList();
    PatientDao patientDao = patientMapper.toDao(patient);
    patientDao.setDoctors(doctors);
    patientRepository.save(patientDao);
  }

  @Override
  public void updatePatient(Patient patient) {
    PatientDao patientDao = patientRepository.findById(patient.getIdPatient())
        .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

    patientDao.setEmail(patient.getEmail());

    List<DoctorDao> existingDoctors = patientDao.getDoctors();

    List<DoctorDao> updatedDoctors = patient.getDoctors()
        .stream()
        .map(doctor -> doctorRepository.findById(doctor.getIdDoctor())
            .orElse(null))
        .filter(Objects::nonNull)
        .toList();

    if (!updatedDoctors.isEmpty()) {
      patientDao.setDoctors(updatedDoctors);
    } else {
      patientDao.setDoctors(existingDoctors);
    }

    patientRepository.save(patientDao);
  }


  @Override
  public void deletePatient(String id) {
    patientRepository.findById(id)
        .orElseThrow(() -> new PatientNotFoundException("Patient with id: " + id + " not found"));
    List<DoctorDao> doctorsWithPatient = doctorRepository.findByPatients_IdPatient(id);
    for (DoctorDao doctor : doctorsWithPatient) {
      doctor.getPatients().removeIf(pat -> pat.getIdPatient().equals(id));
      doctorRepository.save(doctor);
    }
    patientRepository.deleteById(id);
  }

  @Override
  public List<Patient> getPatients() {
    return patientRepository.findAll()
        .stream()
        .map(patientMapper::toDto)
        .toList();
  }

  @Override
  public Patient getPatientById(String id) {
    return patientRepository.findById(id)
        .map(patientMapper::toDto)
        .orElseThrow(() -> new PatientNotFoundException("Patient with id: " + id + " couldn't be found"));
  }
}
