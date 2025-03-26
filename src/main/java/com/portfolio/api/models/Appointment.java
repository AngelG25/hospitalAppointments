package com.portfolio.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Builder(toBuilder = true)
@Value
@JsonTypeName("APPOINTMENT")
@Jacksonized
public class Appointment {

  @JsonProperty(value = "idAppointment")
  @NotNull
  String idAppointment;

  @JsonProperty(value = "description")
  @NotNull
  String description;

  @JsonProperty(value = "date")
  @NotNull
  Instant date;

  @JsonProperty(value = "idDoctor")
  String idDoctor;

  @JsonProperty(value = "idPatient")
  String idPatient;
}
