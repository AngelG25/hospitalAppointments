package com.portfolio.repositories;

import com.portfolio.dao.DoctorDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends MongoRepository<DoctorDao, String> {

}
