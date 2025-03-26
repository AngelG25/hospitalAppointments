package com.portfolio.dao;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Getter
@Setter
@NoArgsConstructor
public class AppointmentDao {

  @Id
  private String idAppointment;

  private String description;

  @Temporal(TemporalType.TIMESTAMP)
  private Instant date;

  private PatientDao patientDao;

  private DoctorDao doctorDao;
}
