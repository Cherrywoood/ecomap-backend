package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ecopoint")
public class Ecopoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "geometry", nullable = false, unique = true)
    private Point geometry;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photo", length = 50)
    private String photo;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "site")
    private String site;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "convenience", nullable = false)
    private Boolean convenience;

    @Builder.Default
    @OneToMany(mappedBy = "ecopoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningHours> openingHoursList = new ArrayList<>();

    public void addOpeningHours(OpeningHours openingHours){
        this.openingHoursList.add(openingHours);
        openingHours.setEcopoint(this);
    }
    public void removeOpeningHours(OpeningHours openingHours){
        this.openingHoursList.remove(openingHours);
        openingHours.setEcopoint(null);
    }

    @ManyToMany
    @JoinTable(name = "ecopoint_waste_type",
    joinColumns = @JoinColumn(name = "ecopoint_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "waste_type_id", referencedColumnName = "id"))
    private Set<WasteType> wasteTypes;

    @ManyToMany
    @JoinTable(name = "ecopoint_type_rel",
    joinColumns = @JoinColumn(name = "ecopoint_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "ecopoint_type_name_id", referencedColumnName = "id"))
    private Set<EcopointType> ecopointTypes;
}
