package com.example.ecomapbackend.model;

import jakarta.persistence.*;
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

    @Column(name = "name", nullable = false, unique = true, length = 25)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "attribution", nullable = false)
    private String attribution;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;
}
