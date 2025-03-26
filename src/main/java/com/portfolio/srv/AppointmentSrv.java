package com.portfolio.srv;

import com.portfolio.api.AppointmentApi;
import com.portfolio.api.models.Appointment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@ApplicationScope
@Log4j2
public class AppointmentSrv implements AppointmentApi {


  @Override
  public void createAppointment(Appointment appointment) {

  }

  @Override
  public void updateAppointment(Appointment appointment) {

  }

  @Override
  public Appointment getAppointmentById(String id) {
    return null;
  }

  @Override
  public List<Appointment> getAppointments() {
    return List.of();
  }

  @Override
  public void deleteAppointment(String id) {

  }
}
