package com.devs.frutybot.data.mapper;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.presentation.common.Fruit;

public class FruitMapper {
    public static Fruit toUiModel(FruitDto dto) {
        return new Fruit(
                dto.getId(),
                dto.getName(),
                dto.getColor(),
                dto.getWeight(),
                dto.getOrigin()
        );
    }
}
