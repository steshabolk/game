package com.task.game.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class CreatureDto {

    @Range(min = 1, max = 30, message = "{range_1_30}")
    private Integer attack;

    @Range(min = 1, max = 30, message = "{range_1_30}")
    private Integer defense;

    @Range(min = 1, max = 500, message = "{range_1_500}")
    private Integer maxHealth;

    @Range(min = 1, max = 100, message = "{range_1_100}")
    private Integer minDamage;

    @Range(min = 1, max = 100, message = "{range_1_100}")
    private Integer maxDamage;
}
