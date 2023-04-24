package com.mauricio.onlinejob.repositories;

import com.mauricio.onlinejob.entities.Application;
import com.mauricio.onlinejob.entities.ApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {
}
