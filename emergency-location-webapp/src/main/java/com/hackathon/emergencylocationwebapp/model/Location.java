package com.hackathon.emergencylocationwebapp.model;

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
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    private Double latitude;

    private Double longitude;

}
