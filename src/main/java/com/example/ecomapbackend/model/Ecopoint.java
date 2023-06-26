package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
    private Long id;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "position", columnDefinition = "geometry(Point,3857)", nullable = false, unique = true)
    private Point position;

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
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "ecopoint_waste_type",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "waste_type_id"))
    private Set<WasteType> wasteTypes;

    @ManyToMany(cascade = CascadeType.MERGE)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "ecopoint_shop_type",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_type_id"))
    private Set<ShopType> shopTypes;

    @ManyToMany(cascade = CascadeType.MERGE)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "ecopoint_event_type",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "event_type_id"))
    private Set<EventType> eventTypes;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "ecopoint_type_ecopoint",
            joinColumns = @JoinColumn(name = "ecopoint_id"),
            inverseJoinColumns = @JoinColumn(name = "ecopoint_type_id"))
    private Set<EcopointType> ecopointTypes;


    @Builder.Default
    @OneToMany(mappedBy = "ecopoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EcopointImage> ecopointImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ecopoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<WorkSchedule> workSchedules = new ArrayList<>();

    public void addEcopointImage(EcopointImage ecopointImage) {
        this.ecopointImages.add(ecopointImage);
        ecopointImage.setEcopoint(this);
    }

    public void removeEcopointImage(EcopointImage ecopointImage) {
        this.ecopointImages.remove(ecopointImage);
        ecopointImage.setEcopoint(null);
    }

    public void addWorkSchedule(WorkSchedule workSchedule) {
        this.workSchedules.add(workSchedule);
        workSchedule.setEcopoint(this);
    }

    public void removeWorkSchedule(WorkSchedule workSchedule) {
        this.workSchedules.remove(workSchedule);
        workSchedule.setEcopoint(null);
    }

    public void removeAllWorkSchedule() {
        this.workSchedules.forEach(openingHours -> openingHours.setEcopoint(null));
        this.workSchedules.clear();
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
