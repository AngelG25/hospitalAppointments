package com.portfolio.srv.utils;

import com.portfolio.api.exceptions.DoctorNotFoundException;
import com.portfolio.api.exceptions.PatientNotFoundException;
import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import com.portfolio.dao.DoctorDao;
import com.portfolio.dao.PatientDao;
import com.portfolio.repositories.DoctorRepository;
import com.portfolio.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.portfolio.srv.AppointmentSrv.DOCTOR_WITH_ID;

@RequiredArgsConstructor
@Service
public class AppointmentManagement {

  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  public static final String NOT_FOUND = " not found";
  public static final String PATIENT_WITH_ID = "Patient with id ";

  public void checkDoctorPatientExistence(Appointment appointment) {
    if (!patientRepository.existsById(appointment.getIdPatient())) {
      throw new PatientNotFoundException(PATIENT_WITH_ID + appointment.getIdPatient() + NOT_FOUND);
    }
    if (!doctorRepository.existsById(appointment.getIdDoctor())) {
      throw new DoctorNotFoundException(DOCTOR_WITH_ID + appointment.getIdDoctor() + NOT_FOUND);
    }
  }

  public void assignAppointments(Appointment appointment, AppointmentDao appointmentDao) {
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

  public void removeAppointments(AppointmentDao appointmentDao) {
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
