package com.example.ecomapbackend.model;

import com.example.ecomapbackend.enums.DayWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "opening_hours")
public class OpeningHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecopoint_id", nullable = false)
    private Ecopoint ecopoint;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayWeek day;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @NotNull
    @Column(name = "is_day_off", nullable = false)
    private Boolean isDayOff;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpeningHours)) return false;
        return id != null && id.equals(((OpeningHours) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
