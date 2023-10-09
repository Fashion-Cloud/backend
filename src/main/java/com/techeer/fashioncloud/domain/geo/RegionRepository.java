package com.techeer.fashioncloud.domain.geo;

import com.techeer.fashioncloud.domain.geo.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value =
            "SELECT * FROM region r " +
                    "WHERE ST_DistanceSphere(ST_GeomFromText('POINT(' || :lng || ' ' || :lat || ')'), r.position) < 10000 " +
                    "ORDER BY ST_DistanceSphere(ST_GeomFromText('POINT(' || :lng || ' ' || :lat || ')'), r.position) ASC LIMIT 1",
            nativeQuery = true
    )
    Optional<Region> findNearestLocation(@Param("lat") Double latitude, @Param("lng") Double longitude);

}
