package com.avila.zecompany.dto;
import lombok.Builder;

@Builder
public record PartnerRequestDTO (
        String tradingName,
        String ownerName,
        String document,
        GeoDataDTO coverageArea,
        GeoDataDTO address) { }