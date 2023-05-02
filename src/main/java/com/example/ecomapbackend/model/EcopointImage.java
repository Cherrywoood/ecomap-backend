package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ecopoint_image")
public class EcopointImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "image_path", nullable = false, unique = true)
    private String imagePath;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ecopoint_id", referencedColumnName = "id", nullable = false)
    private Ecopoint ecopoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EcopointImage)) return false;
        return id != null && id.equals(((EcopointImage) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
