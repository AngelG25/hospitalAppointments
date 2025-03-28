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
public class DoctorDao {

  @Id
  private String idDoctor;

  private String firstName;

  private String lastName;

  private String email;

  private String schedule;

  private List<AppointmentDao> appointments = new ArrayList<>();

  private List<PatientDao> patients = new ArrayList<>();
}
