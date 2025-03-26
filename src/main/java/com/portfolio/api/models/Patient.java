package com.portfolio.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder(toBuilder = true)
@Value
@JsonTypeName("PATIENT")
@Jacksonized
public class Patient {

  @JsonProperty(value = "idPatient")
  @NotNull
  String idPatient;

  @JsonProperty(value = "firstName")
  @NotNull
  String firstName;

  @JsonProperty(value = "lastName")
  @NotNull
  String lastName;

  @JsonProperty(value = "email")
  @NotNull
  String email;

  @JsonProperty(value = "appointments")
  List<Appointment> appointments;

  @JsonProperty(value = "doctors")
  List<Doctor> doctors;
}
