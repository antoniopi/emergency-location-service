package com.hackathon.emergencylocationwebapp.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
public enum EmergencyStatus {
    OPEN("Open"),
    ONGOING("On going"),
    CLOSED("Closed");

    private String name;
}
