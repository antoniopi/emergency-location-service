package com.hackathon.emergencylocationwebapp.config;

import com.hackathon.emergencylocationwebapp.model.EmergencyStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmergencyStatusConverter implements AttributeConverter<EmergencyStatus,String> {

    @Override
    public String convertToDatabaseColumn(EmergencyStatus attribute) {
        switch (attribute) {
            case OPEN:
                return "Open";
            case CLOSED:
                return "Closed";
            case IN_PROGRESS:
                return "In progress";
            default:
                throw new IllegalArgumentException("Unknown" + attribute);
        }
    }

    @Override
    public EmergencyStatus convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "Open":
                return EmergencyStatus.OPEN;
            case "Closed":
                return EmergencyStatus.CLOSED;
            case "In progress":
                return EmergencyStatus.IN_PROGRESS;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
