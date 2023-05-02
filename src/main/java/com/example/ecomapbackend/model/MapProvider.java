package com.example.ecomapbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "map_provider")
public class MapProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @NotBlank
    @Size(max = 25)
    @Column(name = "name", nullable = false, unique = true, length = 25)
    private String name;

    @NotBlank
    @Column(name = "url", nullable = false)
    private String url;

    @NotBlank
    @Column(name = "attribution", nullable = false)
    private String attribution;

    @Column(name = "auth_token")
    private String authToken;

    @NotNull
    @Column(name = "is_main", nullable = false)
    private Boolean isMain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapProvider)) return false;
        return id != null && id.equals(((MapProvider) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
