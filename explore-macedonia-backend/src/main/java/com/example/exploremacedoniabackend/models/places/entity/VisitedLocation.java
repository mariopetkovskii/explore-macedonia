package com.example.exploremacedoniabackend.models.places.entity;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "visited_locations", schema = "locations")
public class VisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public VisitedLocation(User user, Location location) {
        this.user = user;
        this.location = location;
    }
}
