package com.portfolio.srv.utils;

import com.portfolio.api.models.Patient;
import com.portfolio.dao.PatientDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AppointmentMapper.class, DoctorMapper.class})
public interface PatientMapper {

  PatientDao toDao(Patient patient);

  Patient toDto(PatientDao patientDao);
}
