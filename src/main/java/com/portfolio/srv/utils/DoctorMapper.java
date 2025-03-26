package com.portfolio.srv.utils;

import com.portfolio.api.models.Doctor;
import com.portfolio.dao.DoctorDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AppointmentMapper.class)
public interface DoctorMapper {

  @Mapping(target = "patients", ignore = true)
  DoctorDao toDao(Doctor doctor);

  Doctor toDto(DoctorDao doctorDao);
}
