package com.avila.zecompany.dto;

public record PartnerRequestDTO (
        String tradingName,
        String ownerName,
        String document,
        GeoDataDTO coverageArea,
        GeoDataDTO address) { }