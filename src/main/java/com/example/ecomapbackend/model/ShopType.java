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
@Table(name = "shop_type")
public class ShopType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @NotBlank
    @Size(max = 25)
    @Column(name = "name", nullable = false, unique = true, length = 25)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopType)) return false;
        return id != null && id.equals(((ShopType) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
