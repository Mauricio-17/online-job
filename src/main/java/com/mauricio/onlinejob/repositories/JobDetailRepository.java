package com.mauricio.onlinejob.repositories;

import com.mauricio.onlinejob.services.JobService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDetailRepository extends JpaRepository<JobService, Long> {

}
