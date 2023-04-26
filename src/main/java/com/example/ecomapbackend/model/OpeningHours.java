package com.example.ecomapbackend.model;

import com.example.ecomapbackend.enums.DayWeek;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecopoint_id", referencedColumnName = "id", nullable = false)
    private Ecopoint ecopoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private DayWeek day;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "is_day_off", nullable = false)
    private Boolean isDayOff;

}
