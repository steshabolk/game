package com.task.game.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {

    private Integer attack;
    private Integer defense;
    private Integer maxHealth;
    private Integer currHealth;
    private Integer minDamage;
    private Integer maxDamage;
    private Integer recovery;
    private Boolean isTurn;
}
