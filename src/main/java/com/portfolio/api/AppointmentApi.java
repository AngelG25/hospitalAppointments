package com.portfolio.api;

import com.portfolio.api.models.Appointment;

import java.util.List;
import java.util.UUID;

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
}
