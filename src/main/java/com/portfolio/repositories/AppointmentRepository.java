package com.portfolio.repositories;

import com.portfolio.dao.AppointmentDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentRepository extends MongoRepository<AppointmentDao, String> {

}
