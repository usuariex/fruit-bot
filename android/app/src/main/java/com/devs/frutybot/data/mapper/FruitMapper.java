package com.devs.frutybot.data.mapper;

import com.devs.frutybot.data.dto.Fruit;
import com.devs.frutybot.domain.model.FruitDomain;

import java.util.ArrayList;
import java.util.List;

public class FruitMapper {

    // Convierte un solo Fruit (DTO) a FruitDomain
    public static FruitDomain toDomain(Fruit dto) {
        return new FruitDomain(
                dto.getId(),
                dto.getNombre(),
                dto.getImagen() != null ? dto.getImagen().trim() : "",
                dto.getDescripcion()
        );
    }

    // Convierte una lista de Fruit (DTO) a una lista de FruitDomain
    public static List<FruitDomain> toDomainList(List<Fruit> dtos) {
        List<FruitDomain> fruits = new ArrayList<>();
        if (dtos != null) {
            for (Fruit dto : dtos) {
                fruits.add(toDomain(dto));
            }
        }
        return fruits;
    }
}
