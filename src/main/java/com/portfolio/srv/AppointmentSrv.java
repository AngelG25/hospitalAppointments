package com.portfolio.srv;

import com.portfolio.api.AppointmentApi;
import com.portfolio.api.exceptions.AppointmentNotFoundException;
import com.portfolio.api.exceptions.DoctorNotFoundException;
import com.portfolio.api.exceptions.PatientNotFoundException;
import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import com.portfolio.dao.DoctorDao;
import com.portfolio.dao.PatientDao;
import com.portfolio.repositories.AppointmentRepository;
import com.portfolio.repositories.DoctorRepository;
import com.portfolio.repositories.PatientRepository;
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
  public static final String PATIENT_WITH_ID = "Patient with id ";

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final AppointmentManagement appointmentManagement;
  private final EmailSrv emailService;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  @Override
  public void createAppointment(Appointment appointment) {
    appointmentManagement.checkDoctorPatientExistence(appointment);
    sendAppointmentNotifications(appointment);
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

  private void sendAppointmentNotifications(Appointment appointment) {
    DoctorDao doctorDao = doctorRepository.findById(appointment.getIdDoctor())
        .orElseThrow(() -> new DoctorNotFoundException(DOCTOR_WITH_ID + appointment.getIdDoctor() + NOT_FOUND));
    PatientDao patientDao = patientRepository.findById(appointment.getIdPatient())
        .orElseThrow(() -> new PatientNotFoundException(PATIENT_WITH_ID + appointment.getIdPatient() + NOT_FOUND));

    String doctorEmail = doctorDao.getEmail();
    String patientEmail = patientDao.getEmail();
    String subject = "New date programmed";
    String message = String.format(
        "Hello,<br><br>A new date has been programmed.<br><br><b>Doctor:</b> %s %s<br><b>Patient:</b> %s %s<br><b>Date:</b> %s<br><br>Greetings.",
        doctorDao.getFirstName(), doctorDao.getLastName(), patientDao.getFirstName(), patientDao.getLastName(),
        appointment.getDate());

    emailService.sendEmail(doctorEmail, subject, message);
    emailService.sendEmail(patientEmail, subject, message);
  }
}
