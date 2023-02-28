package com.example.exploremacedoniabackend.models.places.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "location", schema = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "longitude", precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "description", length = 512)
    private String description;
    @Column(name = "is_recommended")
    private Boolean isRecommended;

    public Location(String location, BigDecimal longitude, BigDecimal latitude, String description) {
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.isRecommended = false;
    }
}
