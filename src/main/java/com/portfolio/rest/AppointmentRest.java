package com.portfolio.rest;

import com.portfolio.api.AppointmentApi;
import com.portfolio.api.models.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@RequestMapping("/appointments")
@CrossOrigin
public class AppointmentRest {

  private final AppointmentApi appointmentApi;

  @GetMapping("/")
  public List<Appointment> getAppointments() {
    return appointmentApi.getAppointments();
  }

  @GetMapping("/{idAppointment}")
  public Appointment getAppointment(@PathVariable String idAppointment) {
    return appointmentApi.getAppointmentById(idAppointment);
  }

  @GetMapping("/findAllByDoctorId/{idDoctor}")
  public List<Appointment> getAppointmentByDoctor(@PathVariable String idDoctor) {
    return appointmentApi.getAppointmentsByDoctorId(idDoctor);
  }

  @GetMapping("/findAllByPatientId/{idPatient}")
  public List<Appointment> getAppointmentByPatient(@PathVariable String idPatient) {
    return appointmentApi.getAppointmentsByPatientId(idPatient);
  }

  @PostMapping("/")
  public void createAppointment(@RequestBody Appointment appointment) {
    appointmentApi.createAppointment(appointment);
  }

  @PutMapping("/")
  public void updateAppointment(@RequestBody Appointment appointment) {
    appointmentApi.updateAppointment(appointment);
  }

  @DeleteMapping("/{idAppointment}")
  public void deleteAppointment(@PathVariable String idAppointment) {
    appointmentApi.deleteAppointment(idAppointment);
  }

}
