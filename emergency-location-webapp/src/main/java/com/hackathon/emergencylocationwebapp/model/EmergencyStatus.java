package com.hackathon.emergencylocationwebapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public enum EmergencyStatus {
    OPEN,
    ONGOING,
    CLOSED
}
