package com.hackathon.emergencylocationwebapp.repository;

import com.hackathon.emergencylocationwebapp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}



