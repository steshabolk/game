package com.task.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GameResponse {

    private Boolean isGameOn;
    private Integer round;
    private Boolean isAttackSuccessful;
    private Integer damage;
    private PlayerDto player;
    private List<MonsterDto> monsters;
    private Integer currMonster;
}
