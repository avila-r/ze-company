package com.avila.zecompany.repository;
import com.avila.zecompany.config.GeoDataRepositoryContextConfig;
import com.avila.zecompany.dto.GeoDataDTO;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.model.GeoData;
import com.avila.zecompany.model.Partner;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;

@DataJpaTest
@ActiveProfiles("test")
@Import(GeoDataRepositoryContextConfig.class)
class GeoDataRepositoryTest {
    @Autowired private EntityManager entityManager;
    @Autowired private GeoDataRepository repository;
    @Mock private PartnerRepository partnerRepository;

    private @NotNull Partner insertPartnerAndGeoData(PartnerRequestDTO request){
        Partner partner = build(request);
        Mockito.when(partnerRepository.findById(partner.getId()))
                .thenReturn(Optional.of(partner));
        this.entityManager.persist(partner);
        this.entityManager.persist(buildArea(partner, request));
        this.entityManager.persist(buildAddress(partner, request));
        return partner;
    }

    private Partner build(@NotNull PartnerRequestDTO request){
        return Partner.builder()
                .tradingName(request.tradingName())
                .ownerName(request.ownerName())
                .document(request.document())
                .build();
    }

    private GeoData buildArea(@NotNull Partner partner, @NotNull PartnerRequestDTO request){
        if (request.coverageArea().type().equals("MultiPolygon"))
            return GeoData.builder()
                .partner(partner)
                .type(GeoData.GeoType.AREA)
                .coordinates(request.coverageArea().coordinates())
                .build();
        else throw new RuntimeException();
    }

    private GeoData buildAddress(@NotNull Partner partner, @NotNull PartnerRequestDTO request){
        if (request.address().type().equals("Point"))
            return GeoData.builder()
                    .partner(partner)
                    .type(GeoData.GeoType.ADDRESS)
                    .coordinates(request.coverageArea().coordinates())
                    .build();
        else throw new RuntimeException();
    }

    @Transactional @Test
    @DisplayName("Should get valid Partner's GeoData successfully after persisting Partner Request data")
    void whenSaveGeoDataByPartnerDTO_thenCanGetValidGeoDataByPartnerId(){
        PartnerRequestDTO request = PartnerRequestDTO.builder()
                .tradingName("Test trading name")
                .ownerName("Test owner name")
                .document("10101010101010/1234")
                .coverageArea(
                        GeoDataDTO.builder()
                                .type("MultiPolygon")
                                .coordinates(List.of(
                                        List.of(102.0, 2.0),
                                        List.of(103.0, 2.0),
                                        List.of(100.0, 1.0),
                                        List.of(102.0, 3.0),
                                        List.of(103.0, 3.0)))
                                .build())
                .address(
                        GeoDataDTO.builder()
                                .type("Point")
                                .coordinates(List.of(105.0, 5.0))
                                .build())
                .build();;
        Partner partner = this.insertPartnerAndGeoData(request);
        Optional<GeoData> address = repository.findByPartnerIdAndType(partner.getId(), GeoData.GeoType.ADDRESS);
        Optional<GeoData> area = repository.findByPartnerIdAndType(partner.getId(), GeoData.GeoType.AREA);
        Assertions.assertThat(
                address.isPresent() &&
                        area.isPresent() &&
                        area.get().getType().equals(GeoData.GeoType.AREA) &&
                        address.get().getType().equals(GeoData.GeoType.ADDRESS)
        ).isTrue();
    }
}