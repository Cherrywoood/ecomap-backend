package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.Ecopoint;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcopointRepository extends JpaRepository<Ecopoint, Integer> {
    @Query("SELECT e FROM Ecopoint e WHERE ST_Within(e.geometry, ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326))")
    List<Ecopoint> findEcopointsWithinRectangularPolygon(@Param("minLon") Double minLon, @Param("minLat") Double minLat,
                                                         @Param("maxLon") Double maxLon, @Param("maxLat") Double maxLat);

    boolean existsByGeometryAndIdNot(Point point, Integer id);

    boolean existsByGeometry(Point point);
}
