package com.portfolio.srv;

import com.portfolio.api.DoctorApi;
import com.portfolio.api.exceptions.DoctorNotFoundException;
import com.portfolio.api.models.Doctor;
import com.portfolio.dao.DoctorDao;
import com.portfolio.repositories.DoctorRepository;
import com.portfolio.srv.utils.DoctorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@ApplicationScope
public class DoctorSrv implements DoctorApi {

  private final DoctorRepository doctorRepository;
  private final DoctorMapper doctorMapper;

  @Override
  public void createDoctor(Doctor doctor) {
    DoctorDao doctorDao = doctorMapper.toDao(doctor);
    doctorRepository.save(doctorDao);
  }

  @Override
  public void updateDoctor(Doctor doctor) {
    DoctorDao doctorDao = doctorRepository.findById(doctor.getIdDoctor())
        .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
    doctorDao.setEmail(doctor.getEmail());
    doctorDao.setSchedule(doctor.getSchedule());
    doctorRepository.save(doctorDao);
  }

  @Override
  public void deleteDoctor(String id) {
    doctorRepository.deleteById(id);
  }

  @Override
  public List<Doctor> getDoctors() {
    return doctorRepository.findAll()
        .stream()
        .map(doctorMapper::toDto)
        .toList();
  }

  @Override
  public Doctor getDoctorById(String id) {
    return doctorRepository.findById(id)
        .map(doctorMapper::toDto)
        .orElseThrow(() -> new DoctorNotFoundException("Doctor with id: " + id + "couldn't be found"));
  }
}
