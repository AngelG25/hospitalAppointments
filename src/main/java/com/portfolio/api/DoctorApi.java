package com.portfolio.api;

import com.portfolio.api.models.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorApi {

  /**
   * Create a doctor
   * @param doctor to be created
   */
  void createDoctor(Doctor doctor);

  /**
   * Updates a doctor
   * @param doctor with the updated fields
   */
  void updateDoctor(Doctor doctor);

  /**
   * Deletes a doctor by the id
   * @param id of the doctor to be deleted
   */
  void deleteDoctor(String id);

  /**
   * Find all the doctors in the DB
   * @return all the doctors
   */
  List<Doctor> getDoctors();

  /**
   * Find a doctor by the id
   * @param id of the doctor to be found
   * @return doctor with the specified id
   */
  Doctor getDoctorById(String id);
}
