package com.portfolio.repositories;

import com.portfolio.dao.PatientDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends MongoRepository<PatientDao, String> {

}
