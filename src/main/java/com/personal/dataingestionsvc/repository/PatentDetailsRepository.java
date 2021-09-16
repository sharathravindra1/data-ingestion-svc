package com.personal.dataingestionsvc.repository;

import com.personal.dataingestionsvc.entity.PatentDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatentDetailsRepository extends CrudRepository<PatentDetails, String> {

    PatentDetails findByPatentApplicationNumber(String patentApplicationNumber);
}
