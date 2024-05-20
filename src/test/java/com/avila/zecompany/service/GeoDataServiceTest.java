package com.avila.zecompany.service;
import com.avila.zecompany.config.GeoDataRepositoryContextConfig;
import com.avila.zecompany.dto.GeoDataDTO;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.exception.geodata.GeoDataNotFoundException;
import com.avila.zecompany.exception.geodata.InvalidGeoDataTypeException;
import com.avila.zecompany.model.Partner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@Import(GeoDataRepositoryContextConfig.class)
public class GeoDataServiceTest {
    @Autowired private GeoDataService service;

    @Test
    @DisplayName("Should throw GeoDataNotFoundException when Partner doesn't exist")
    void whenTryToSaveAddressWithNonExistentId_thenThrowsValidException(){
        Partner partner = Partner.builder()
                .id(999L)
                .tradingName("Test trading name")
                .ownerName("Test owner name")
                .document("12345678910/1234")
                .build();
        Assertions.assertThatThrownBy(() -> service.getAddressByPartner(partner))
                .isInstanceOf(GeoDataNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw GeoDataNotFoundException when Partner doesn't exist")
    void whenTryToSaveAreaWithNonExistentId_thenThrowsValidException(){
        Partner partner = Partner.builder()
                .id(999L)
                .tradingName("Test trading name")
                .ownerName("Test owner name")
                .document("12345678910/1234")
                .build();
        Assertions.assertThatThrownBy(() -> service.getAreaByPartner(partner))
                .isInstanceOf(GeoDataNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw InvalidGeoDataTypeException when save Address as MultiPolygon")
    void whenTryToSaveAddressAsMultiPolygon_thenThrowsValidException(){
        PartnerRequestDTO request = PartnerRequestDTO.builder()
                .tradingName("Test trading name")
                .ownerName("Test owner name")
                .document("12345678910/1234")
                .coverageArea(GeoDataDTO.builder()
                                .type("MultiPolygon")
                                .coordinates(List.of(
                                        List.of(102.0, 2.0),
                                        List.of(103.0, 2.0),
                                        List.of(100.0, 1.0),
                                        List.of(102.0, 3.0),
                                        List.of(103.0, 3.0)))
                                .build())
                .address(GeoDataDTO.builder()
                                .type("MultiPolygon")
                                .coordinates(List.of(102.0, 1.0))
                                .build())
                .build();

        Partner partner = Partner.builder()
                .id(new Random().nextLong())
                .tradingName(request.tradingName())
                .ownerName(request.ownerName())
                .document(request.document())
                .build();

        Assertions.assertThatThrownBy(() -> service.saveAddressByPartnerRequest(request, partner))
                .isInstanceOf(InvalidGeoDataTypeException.class);
    }

    @Test
    @DisplayName("Should throw InvalidGeoDataTypeException when save Area as Point")
    void whenTryToSaveAreaAsPoint_thenThrowsValidException(){
        PartnerRequestDTO request = PartnerRequestDTO.builder()
                .tradingName("Test trading name 2")
                .ownerName("Test owner name 2")
                .document("9819832131/1000 2")
                .coverageArea(GeoDataDTO.builder()
                        .type("Point")
                        .coordinates(List.of(
                                List.of(102.0, 2.0),
                                List.of(103.0, 2.0),
                                List.of(100.0, 1.0),
                                List.of(102.0, 3.0),
                                List.of(103.0, 3.0)))
                        .build())
                .address(GeoDataDTO.builder()
                        .type("Point")
                        .coordinates(List.of(102.0, 1.0))
                        .build())
                .build();

        Partner partner = Partner.builder()
                .id(new Random().nextLong())
                .tradingName(request.tradingName())
                .ownerName(request.ownerName())
                .document(request.document())
                .build();

        Assertions.assertThatThrownBy(() -> service.saveAreaByPartnerRequest(request, partner))
                .isInstanceOf(InvalidGeoDataTypeException.class);
    }
}