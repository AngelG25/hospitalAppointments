package com.portfolio.srv;

import com.portfolio.api.AppointmentApi;
import com.portfolio.api.exceptions.AppointmentNotFoundException;
import com.portfolio.api.exceptions.DoctorNotFoundException;
import com.portfolio.api.exceptions.PatientNotFoundException;
import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import com.portfolio.repositories.AppointmentRepository;
import com.portfolio.repositories.DoctorRepository;
import com.portfolio.repositories.PatientRepository;
import com.portfolio.srv.utils.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@ApplicationScope
@Log4j2
public class AppointmentSrv implements AppointmentApi {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  @Override
  public void createAppointment(Appointment appointment) {
    checkDoctorPatientExistence(appointment);
    appointmentRepository.save(appointmentMapper.toDao(appointment));
  }

  @Override
  public void updateAppointment(Appointment appointment) {
    if (appointmentRepository.findById(appointment.getIdAppointment())
        .isEmpty()) {
      throw new AppointmentNotFoundException("Appointment with id " + appointment.getIdAppointment() + " not found");
    }
    checkDoctorPatientExistence(appointment);
    AppointmentDao appointmentDao = appointmentMapper.toDao(appointment);
    appointmentRepository.save(appointmentDao);
  }

  @Override
  public Appointment getAppointmentById(String id) {
    return appointmentRepository.findById(id)
        .map(appointmentMapper::toDto)
        .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + id + " not found"));
  }

  @Override
  public List<Appointment> getAppointments() {
    return appointmentRepository.findAll()
        .stream()
        .map(appointmentMapper::toDto)
        .toList();
  }

  @Override
  public void deleteAppointment(String id) {
    appointmentRepository.findById(id)
        .orElseThrow(() -> new AppointmentNotFoundException("Appointment with id " + id + " not found"));
    appointmentRepository.deleteById(id);
  }

  private void checkDoctorPatientExistence(Appointment appointment) {
    if (!patientRepository.existsById(appointment.getIdPatient())) {
      throw new PatientNotFoundException("Patient with id " + appointment.getIdPatient() + " not found");
    }
    if (!doctorRepository.existsById(appointment.getIdDoctor())) {
      throw new DoctorNotFoundException("Doctor with id " + appointment.getIdDoctor() + " not found");
    }
  }
}
