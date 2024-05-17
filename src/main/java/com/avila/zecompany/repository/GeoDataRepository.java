package com.avila.zecompany.repository;
import com.avila.zecompany.model.GeoData;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface GeoDataRepository extends ListCrudRepository<GeoData, Long> {
    Optional<GeoData> findByPartnerId(Long id);
}