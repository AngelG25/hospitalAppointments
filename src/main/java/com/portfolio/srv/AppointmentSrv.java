package com.portfolio.srv;

import com.portfolio.api.AppointmentApi;
import com.portfolio.api.exceptions.AppointmentNotFoundException;
import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import com.portfolio.repositories.AppointmentRepository;
import com.portfolio.srv.utils.AppointmentManagement;
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

  public static final String APPOINTMENT_WITH_ID = "Appointment with id ";
  public static final String NOT_FOUND = " not found";
  public static final String DOCTOR_WITH_ID = "Doctor with id ";

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final AppointmentManagement appointmentManagement;

  @Override
  public void createAppointment(Appointment appointment) {
    appointmentManagement.checkDoctorPatientExistence(appointment);
    AppointmentDao appointmentDao = appointmentMapper.toDao(appointment);
    appointmentRepository.save(appointmentDao);
    appointmentManagement.assignAppointments(appointment, appointmentDao);
  }

  @Override
  public void updateAppointment(Appointment appointment) {
    if (appointmentRepository.findById(appointment.getIdAppointment())
        .isEmpty()) {
      throw new AppointmentNotFoundException(APPOINTMENT_WITH_ID + appointment.getIdAppointment() + NOT_FOUND);
    }
    appointmentManagement.checkDoctorPatientExistence(appointment);
    AppointmentDao appointmentDao = appointmentMapper.toDao(appointment);
    appointmentRepository.save(appointmentDao);
    appointmentManagement.assignAppointments(appointment, appointmentDao);
  }

  @Override
  public Appointment getAppointmentById(String id) {
    return appointmentRepository.findById(id)
        .map(appointmentMapper::toDto)
        .orElseThrow(() -> new AppointmentNotFoundException(APPOINTMENT_WITH_ID + id + NOT_FOUND));
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
    AppointmentDao appointmentDao = appointmentRepository.findById(id)
        .orElseThrow(() -> new AppointmentNotFoundException(APPOINTMENT_WITH_ID + id + NOT_FOUND));
    appointmentManagement.removeAppointments(appointmentDao);
    appointmentRepository.deleteById(id);
  }

  @Override
  public List<Appointment> getAppointmentsByPatientId(String patientId) {
    return appointmentRepository.findAppointmentsByIdPatient(patientId)
        .stream()
        .map(appointmentMapper::toDto)
        .toList();
  }

  @Override
  public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
    return appointmentRepository.findAppointmentsByIdDoctor(doctorId)
        .stream()
        .map(appointmentMapper::toDto)
        .toList();
  }
}
