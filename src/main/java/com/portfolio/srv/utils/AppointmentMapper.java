package com.portfolio.srv.utils;

import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

  AppointmentDao toDao(Appointment appointment);

  Appointment toDto(AppointmentDao appointmentDao);
}
