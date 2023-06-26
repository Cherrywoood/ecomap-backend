package com.example.ecomapbackend.repository.specification;

import com.example.ecomapbackend.enums.TimeWork;
import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.model.WorkSchedule;
import com.example.ecomapbackend.utils.DayOfWeekFinder;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class EcopointSpecifications {

    private EcopointSpecifications() {
    }

    public static Specification<Ecopoint> withinBoundingBox(double minLon, double minLat, double maxLon, double maxLat) {
        return (root, query, builder) -> {
            Expression<Object> envelope = builder.function(
                    "ST_MakeEnvelope",
                    Object.class,
                    builder.literal(minLon),
                    builder.literal(minLat),
                    builder.literal(maxLon),
                    builder.literal(maxLat),
                    builder.literal(3857)
            );
            Expression<Boolean> containsExpression = builder.function(
                    "ST_Contains",
                    Boolean.class,
                    envelope,
                    root.get("position")
            );
            query.where(containsExpression);
            return null;

        };
    }

    public static Specification<Ecopoint> byEcopointTypeIn(List<Short> typeIds) {
        return (root, query, builder) ->
                typeIds == null
                        ? null
                        : root.get("ecopointTypes").get("id").in(typeIds);
    }

    public static Specification<Ecopoint> byWasteTypeIn(List<Short> wasteTypeIds) {
        return (root, query, builder) ->
                wasteTypeIds == null
                        ? null
                        : root.get("wasteTypes").get("id").in(wasteTypeIds);
    }

    public static Specification<Ecopoint> byShopTypeIn(List<Short> shopTypeIds) {
        return (root, query, builder) ->
                shopTypeIds == null
                        ? null
                        : root.get("shopTypes").get("id").in(shopTypeIds);
    }

    public static Specification<Ecopoint> byEventTypeIn(List<Short> eventTypeIds) {
        return (root, query, builder) ->
                eventTypeIds == null
                        ? null
                        : root.get("eventTypes").get("id").in(eventTypeIds);
    }

    public static Specification<Ecopoint> byEcopointTimeWork(TimeWork timeWork) {
        return (root, query, builder) -> {
            switch (timeWork) {
                case CONVENIENCE -> {
                    return builder.equal(root.get("isConvenience"), true);
                }
                case OPEN_NOW -> {
                    LocalTime currentTime = LocalTime.now();
                    final Path<WorkSchedule> workSchedules = root.get("workSchedules");


                    var dayOfWeekPredicate = builder.equal(workSchedules.get("day").as(String.class),
                            DayOfWeekFinder.getDayOfWeek().name());

                    var openTimePredicate = builder.lessThanOrEqualTo(workSchedules.get("openTime"),
                            currentTime);
                    var closeTimePredicate = builder.greaterThan(workSchedules.get("closeTime"),
                            currentTime);
                    return builder.and(dayOfWeekPredicate, openTimePredicate, closeTimePredicate);
                }
                case OPEN_TWO_HOURS -> {
                    final Path<WorkSchedule> workSchedules = root.get("workSchedules");

                    var dayOfWeekPredicate = builder.equal(workSchedules.get("day").as(String.class),
                            DayOfWeekFinder.getDayOfWeek().name());

                    LocalTime twoHoursLater = LocalTime.now().plusHours(2);
                    var remainingTimePredicate = builder.lessThanOrEqualTo(
                            workSchedules.get("closeTime"), twoHoursLater
                    );

                    return builder.and(dayOfWeekPredicate, remainingTimePredicate);
                }
                case ALL -> {
                    return null;
                }
                default -> throw new IllegalArgumentException("Invalid TimeWork: " + timeWork);
            }
        };
    }

    public static Specification<Ecopoint> distinct() {
        return (root, query, builder) -> {
            query.distinct(true);
            return null;
        };
    }

}
