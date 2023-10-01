package com.task.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterDto {

    private Integer attack;
    private Integer defense;
    private Integer maxHealth;
    private Integer currHealth;
    private Integer minDamage;
    private Integer maxDamage;
    private Boolean isTurn;
}
