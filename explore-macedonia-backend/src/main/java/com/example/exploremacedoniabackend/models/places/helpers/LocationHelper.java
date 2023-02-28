package com.example.exploremacedoniabackend.models.places.helpers;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class LocationHelper {
    private String location;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String description;
    private Boolean isRecommended;
}
