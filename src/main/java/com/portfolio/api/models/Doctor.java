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
@JsonTypeName("DOCTOR")
@Jacksonized
public class Doctor {

  @JsonProperty(value = "idDoctor")
  @NotNull
  String idDoctor;

  @JsonProperty(value = "firstName")
  @NotNull
  String firstName;

  @JsonProperty(value = "lastName")
  String lastName;

  @JsonProperty(value = "email")
  @NotNull
  String email;

  @JsonProperty(value = "schedule")
  @NotNull
  String schedule;

  @JsonProperty(value = "appointments")
  List<Appointment> appointments;

}
