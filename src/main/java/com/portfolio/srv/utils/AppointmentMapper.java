package com.portfolio.srv.utils;

import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

  @Mapping(target = "patientDao", ignore = true)
  @Mapping(target = "doctorDao", ignore = true)
  AppointmentDao toDao(Appointment appointment);

  @Mapping(target = "idPatient", ignore = true)
  @Mapping(target = "idDoctor", ignore = true)
  Appointment toDto(AppointmentDao appointmentDao);
}
