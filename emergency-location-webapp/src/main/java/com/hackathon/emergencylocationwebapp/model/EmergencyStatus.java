package com.hackathon.emergencylocationwebapp.model;

import javax.persistence.Embeddable;

@Embeddable
public enum EmergencyStatus {
    OPEN,
    IN_PROGRESS,
    CLOSED;
}
