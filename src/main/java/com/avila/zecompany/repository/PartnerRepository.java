package com.avila.zecompany.repository;
import com.avila.zecompany.model.Partner;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface PartnerRepository extends ListCrudRepository<Partner, Long> {
    Optional<Partner> findByDocument(String document);
}