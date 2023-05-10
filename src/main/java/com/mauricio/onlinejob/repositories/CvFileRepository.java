package com.mauricio.onlinejob.repositories;

import com.mauricio.onlinejob.entities.CvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvFileRepository extends JpaRepository<CvFile, Long> {

}
