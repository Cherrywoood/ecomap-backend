package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ecopoint_type")
public class EcopointType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EcopointType)) return false;
        return id != null && id.equals(((EcopointType) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
