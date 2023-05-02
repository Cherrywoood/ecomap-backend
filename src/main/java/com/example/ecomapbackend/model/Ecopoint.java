package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "geometry", nullable = false, unique = true)
    private Point geometry;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "site")
    private String site;

    @Size(min = 16, max = 16, message = "Номер телефона должен состоять из 16 символов")
    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @Email
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "is_convenience", nullable = false)
    private Boolean isConvenience;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ecopoint_waste_type",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "waste_type_id"))
    private Set<WasteType> wasteTypes;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "ecopoint_type_ecopoint",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "ecopoint_type_id"))
    private Set<EcopointType> ecopointTypes;

    @Builder.Default
    @OneToMany(mappedBy = "ecopoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EcopointImage> ecopointImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ecopoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningHours> openingHoursList = new ArrayList<>();

    public void addEcopointImage(EcopointImage ecopointImage) {
        this.ecopointImages.add(ecopointImage);
        ecopointImage.setEcopoint(this);
    }

    public void removeEcopointImage(EcopointImage ecopointImage) {
        this.ecopointImages.remove(ecopointImage);
        ecopointImage.setEcopoint(null);
    }

    public void addOpeningHours(OpeningHours openingHours) {
        this.openingHoursList.add(openingHours);
        openingHours.setEcopoint(this);
    }

    public void removeOpeningHours(OpeningHours openingHours) {
        this.openingHoursList.remove(openingHours);
        openingHours.setEcopoint(null);
    }

    public void removeAllOpeningHours() {
        this.openingHoursList.forEach(openingHours -> openingHours.setEcopoint(null));
        this.openingHoursList.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ecopoint)) return false;
        return id != null && id.equals(((Ecopoint) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
