package com.devs.frutybot.data.mapper;


import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.domain.model.Fruit;
import java.util.ArrayList;
import java.util.List;

public class FruitMapper {
    public static Fruit toDomain(FruitDto dto) {
        return new Fruit(
                dto.getId(),
                dto.getNombre(),
                dto.getImagen().trim(),
                dto.getDescripcion()
        );
    }

    public static List<Fruit> toDomainList(List<FruitDto> dtos) {
        List<Fruit> fruits = new ArrayList<>();
        for (FruitDto dto : dtos) {
            fruits.add(toDomain(dto));
        }
        return fruits;
    }
}
