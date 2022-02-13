package com.hackathon.emergencylocationwebapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Emergency {

    @Id
    @GeneratedValue
    private Long id;

    private Location location;

    private String callerName;

    private String callerLastname;

    private String reason;

    private EmergencyStatus status;
}
