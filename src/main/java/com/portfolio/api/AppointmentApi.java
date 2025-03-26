package com.portfolio.api;

import com.portfolio.api.models.Appointment;

import java.util.List;

public interface AppointmentApi {

  /**
   * Creates an appointment assigned for the client and doctor
   * @param appointment that will be created
   */
  void createAppointment(Appointment appointment);

  /**
   * Updates an appointment
   * @param appointment that will be updated
   */
  void updateAppointment(Appointment appointment);

  /**
   * Find the appointment by the id
   * @param id of the appointment
   * @return appointment found
   */
  Appointment getAppointmentById(String id);

  /**
   * Finds all the appointments
   * @return all the appointments in the database
   */
  List<Appointment> getAppointments();

  /**
   * Deletes an appointment by the id
   * @param id of the appointment to be deleted
   */
  void deleteAppointment(String id);

  /**
   * Get all the appointments of the specified patient
   * @param patientId id of the patient
   * @return all the appointments of the patient
   */
  List<Appointment> getAppointmentsByPatientId(String patientId);

  /**
   * Get all the appointments of the specified doctor
   * @param doctorId id of the doctor
   * @return all the appointments of the doctor
   */
  List<Appointment> getAppointmentsByDoctorId(String doctorId);
}
