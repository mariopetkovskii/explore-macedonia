package com.example.exploremacedoniabackend.models.places.entity;

import com.example.exploremacedoniabackend.models.userroles.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "location", schema = "visited_locations")
public class UnvisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_table_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public UnvisitedLocation(User user, Location location) {
        this.user = user;
        this.location = location;
    }
}
