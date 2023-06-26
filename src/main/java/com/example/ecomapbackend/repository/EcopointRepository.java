package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.Ecopoint;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcopointRepository extends JpaRepository<Ecopoint, Long>, JpaSpecificationExecutor<Ecopoint> {

/*    @Query("SELECT e FROM Ecopoint e JOIN e.ecopointTypes et " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND et.id IN :typeIds")
    List<Ecopoint> findEcopointsInBoundsByTypes(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                @Param("typeIds") List<Short> typeIds);*/

    @Query(value = "SELECT DISTINCT e.* FROM ecopoint e JOIN ecopoint_type_ecopoint et ON e.id = et.ecopoint_id " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND et.ecopoint_type_id IN :typeIds",
    countQuery ="SELECT count(DISTINCT e.*) FROM ecopoint e JOIN ecopoint_type_ecopoint et ON e.id = et.ecopoint_id " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND et.ecopoint_type_id IN :typeIds", nativeQuery = true)
    Page<Ecopoint> findEcopointsInBoundsByTypes(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                @Param("typeIds") List<Short> typeIds,
                                                Pageable pageable);
    @Query("SELECT e FROM Ecopoint e WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857)," +
            " e.position)")
    List<Ecopoint> findEcopointsInBoundsAll(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                            @Param("maxLon") double maxLon, @Param("maxLat") double maxLat);

    @Query(value = "SELECT DISTINCT e.* FROM ecopoint e " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position)",
            countQuery ="SELECT count(DISTINCT e.*) FROM ecopoint e " +
                    "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position)", nativeQuery = true)
    Page<Ecopoint> findEcopointsInBoundsAll(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                            @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                            Pageable pageable);

    @Query("SELECT e FROM Ecopoint e JOIN e.wasteTypes wt " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND wt.id IN :typeIds")
    List<Ecopoint> findEcopointsInBoundsByWastes(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                 @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                 @Param("typeIds") List<Short> typeIds);
    @Query(value = "SELECT DISTINCT e.* FROM ecopoint e JOIN ecopoint_waste_type wt on e.id = wt.ecopoint_id " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND wt.waste_type_id IN :wasteIds",
            countQuery = "SELECT DISTINCT count(e.*) FROM ecopoint e JOIN ecopoint_waste_type wt on e.id = wt.ecopoint_id " +
                    "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
                    "AND wt.waste_type_id IN :wasteIds",
            nativeQuery = true)
    Page<Ecopoint> findEcopointsInBoundsByWastes(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                 @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                 @Param("wasteIds") List<Short> wasteIds, Pageable pageable);

    @Query("SELECT e FROM Ecopoint e JOIN e.shopTypes st " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND st.id IN :typeIds")
    List<Ecopoint> findEcopointsInBoundsByShops(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                @Param("typeIds") List<Short> typeIds);

    @Query(value = "SELECT DISTINCT e.* FROM ecopoint e JOIN ecopoint_shop_type st on e.id = st.ecopoint_id " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND st.shop_type_id IN :shopIds",
            countQuery = "SELECT DISTINCT count(e.*) FROM ecopoint e JOIN ecopoint_shop_type st on e.id = st.ecopoint_id " +
                    "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
                    "AND st.shop_type_id IN :shopIds",
            nativeQuery = true)
    Page<Ecopoint> findEcopointsInBoundsByShops(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                @Param("shopIds") List<Short> shopIds, Pageable pageable);

    @Query("SELECT e FROM Ecopoint e JOIN e.eventTypes et " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND et.id IN :typeIds")
    List<Ecopoint> findEcopointsInBoundsByEvents(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                 @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                 @Param("typeIds") List<Short> typeIds);

    @Query(value = "SELECT DISTINCT e.* FROM ecopoint e JOIN ecopoint_event_type et on e.id = et.ecopoint_id " +
            "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
            "AND et.event_type_id IN :eventIds",
            countQuery = "SELECT DISTINCT count(e.*) FROM ecopoint e JOIN ecopoint_event_type et on e.id = et.ecopoint_id " +
                    "WHERE ST_Contains(ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 3857), e.position) " +
                    "AND et.event_type_id IN :eventIds",
            nativeQuery = true)
    Page<Ecopoint> findEcopointsInBoundsByEvents(@Param("minLon") double minLon, @Param("minLat") double minLat,
                                                 @Param("maxLon") double maxLon, @Param("maxLat") double maxLat,
                                                 @Param("eventIds") List<Short> eventIds, Pageable pageable);
    List<Ecopoint> findEcopointsByNameContainsIgnoreCase(String name);

    boolean existsByPositionAndIdNot(Point point, Long id);

    boolean existsByPosition(Point point);
}
