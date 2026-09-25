package com.example.crud.domain.product;

public enum DistributionCenter {
    MOGI_DAS_CRUZES("Mogi das Cruzes"),
    RECIFE("Recife"),
    PORTO_ALEGRE("Porto Alegre");

    private final String city;

    DistributionCenter(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public static DistributionCenter fromCity(String city) {
        for (DistributionCenter center : values()) {
            if (center.city.equalsIgnoreCase(city.trim())) {
                return center;
            }
        }
        return null;
    }
}
