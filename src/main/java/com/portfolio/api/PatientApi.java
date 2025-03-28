package com.portfolio.api;

import com.portfolio.api.models.Patient;

import java.util.List;

public interface PatientApi {

  /**
   * Create a patient
   * @param patient to be created
   */
  void createPatient(Patient patient);

  /**
   * Update a patient
   * @param patient with the modified fields
   */
  void updatePatient(Patient patient);

  /**
   * Deletes a patient
   * @param id of the patient to be removed
   */
  void deletePatient(String id);

  /**
   * Find all the patients in the DB
   * @return all the patients
   */
  List<Patient> getPatients();

  /**
   * Find a patient by the id
   * @param id of the patient to be found
   * @return patient with the id
   */
  Patient getPatientById(String id);
}
