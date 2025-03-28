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
import com.portfolio.srv.utils.AppointmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@ApplicationScope
@Log4j2
public class AppointmentSrv implements AppointmentApi {

  public static final String APPOINTMENT_WITH_ID = "Appointment with id ";
  public static final String NOT_FOUND = " not found";
  public static final String PATIENT_WITH_ID = "Patient with id ";
  public static final String DOCTOR_WITH_ID = "Doctor with id ";

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  @Override
  public void createAppointment(Appointment appointment) {
    checkDoctorPatientExistence(appointment);
    AppointmentDao appointmentDao = appointmentMapper.toDao(appointment);
    appointmentRepository.save(appointmentDao);
    assignAppointments(appointment, appointmentDao);
  }

  @Override
  public void updateAppointment(Appointment appointment) {
    if (appointmentRepository.findById(appointment.getIdAppointment())
        .isEmpty()) {
      throw new AppointmentNotFoundException(APPOINTMENT_WITH_ID + appointment.getIdAppointment() + NOT_FOUND);
    }
    checkDoctorPatientExistence(appointment);
    AppointmentDao appointmentDao = appointmentMapper.toDao(appointment);
    appointmentRepository.save(appointmentDao);
    assignAppointments(appointment, appointmentDao);
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
    removeAppointments(appointmentDao);
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

  private void checkDoctorPatientExistence(Appointment appointment) {
    if (!patientRepository.existsById(appointment.getIdPatient())) {
      throw new PatientNotFoundException(PATIENT_WITH_ID + appointment.getIdPatient() + NOT_FOUND);
    }
    if (!doctorRepository.existsById(appointment.getIdDoctor())) {
      throw new DoctorNotFoundException(DOCTOR_WITH_ID + appointment.getIdDoctor() + NOT_FOUND);
    }
  }

  private void assignAppointments(Appointment appointment, AppointmentDao appointmentDao) {
    assignAppointmentPatient(appointment, appointmentDao);
    assignAppointmentDoctor(appointment, appointmentDao);
  }

  private void assignAppointmentDoctor(Appointment appointment, AppointmentDao appointmentDao) {
    DoctorDao doctorDao = doctorRepository.findById(appointment.getIdDoctor())
        .orElseThrow(() -> new DoctorNotFoundException(DOCTOR_WITH_ID + appointment.getIdDoctor() + NOT_FOUND));
    doctorDao.getAppointments().add(appointmentDao);
    doctorRepository.save(doctorDao);
  }

  private void assignAppointmentPatient(Appointment appointment, AppointmentDao appointmentDao) {
    PatientDao patientDao = patientRepository.findById(appointment.getIdPatient())
        .orElseThrow(() -> new PatientNotFoundException(PATIENT_WITH_ID + appointment.getIdPatient() + NOT_FOUND));
    patientDao.getAppointments().add(appointmentDao);
    patientRepository.save(patientDao);
  }

  private void removeAppointments(AppointmentDao appointmentDao) {
    removeDoctorAppointment(appointmentDao);
    removePatientAppointment(appointmentDao);
  }

  private void removePatientAppointment(AppointmentDao appointmentDao) {
    PatientDao patientDao = patientRepository.findById(appointmentDao.getIdPatient())
        .orElseThrow(() -> new PatientNotFoundException(PATIENT_WITH_ID + appointmentDao.getIdPatient() + NOT_FOUND));
    Optional<AppointmentDao> appointmentToRemove = patientDao.getAppointments()
        .stream()
        .filter(patientAppointment -> patientAppointment.getIdAppointment()
            .equals(appointmentDao.getIdAppointment()))
        .findFirst();

    appointmentToRemove.ifPresent(a -> patientDao.getAppointments()
        .remove(a));
    patientRepository.save(patientDao);
  }

  private void removeDoctorAppointment(AppointmentDao appointmentDao) {
    DoctorDao doctorDao = doctorRepository.findById(appointmentDao.getIdDoctor())
        .orElseThrow(() -> new DoctorNotFoundException(DOCTOR_WITH_ID + appointmentDao.getIdDoctor() + NOT_FOUND));
    Optional<AppointmentDao> appointmentToRemove = doctorDao.getAppointments()
        .stream()
        .filter(doctorAppointment -> doctorAppointment.getIdAppointment()
            .equals(appointmentDao.getIdAppointment()))
        .findFirst();

    appointmentToRemove.ifPresent(a -> doctorDao.getAppointments()
        .remove(a));
    doctorRepository.save(doctorDao);
  }
}
