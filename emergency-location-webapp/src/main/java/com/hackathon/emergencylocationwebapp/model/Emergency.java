package com.hackathon.emergencylocationwebapp.model;

import com.hackathon.emergencylocationwebapp.config.EmergencyStatusConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Emergency {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    private String callerName;

    private String callerLastname;

    private String callerTelephoneNumber;

    private String reason;

//    @Enumerated(EnumType.STRING)
//    private EmergencyStatus status;

    private String status;
}
