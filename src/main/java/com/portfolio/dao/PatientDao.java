package com.portfolio.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class PatientDao {

  @Id
  private String idPatient;

  private String firstName;

  private String lastName;

  private String email;

  private List<AppointmentDao> appointments = new ArrayList<>();

  private List<DoctorDao> doctors = new ArrayList<>();

}
