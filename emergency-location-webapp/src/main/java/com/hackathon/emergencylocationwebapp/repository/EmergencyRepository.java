package com.hackathon.emergencylocationwebapp.repository;

import com.hackathon.emergencylocationwebapp.model.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
}
