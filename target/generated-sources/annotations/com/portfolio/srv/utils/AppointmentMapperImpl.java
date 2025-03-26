package com.portfolio.srv.utils;

import com.portfolio.api.models.Appointment;
import com.portfolio.dao.AppointmentDao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T09:10:48+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public AppointmentDao toDao(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDao appointmentDao = new AppointmentDao();

        appointmentDao.setIdAppointment( appointment.getIdAppointment() );
        appointmentDao.setDescription( appointment.getDescription() );
        appointmentDao.setDate( appointment.getDate() );

        return appointmentDao;
    }

    @Override
    public Appointment toDto(AppointmentDao appointmentDao) {
        if ( appointmentDao == null ) {
            return null;
        }

        Appointment.AppointmentBuilder appointment = Appointment.builder();

        appointment.idAppointment( appointmentDao.getIdAppointment() );
        appointment.description( appointmentDao.getDescription() );
        appointment.date( appointmentDao.getDate() );

        return appointment.build();
    }
}
