package com.task.game.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitRequest {

    private CreatureDto player;
    private List<CreatureDto> monsters;
}
