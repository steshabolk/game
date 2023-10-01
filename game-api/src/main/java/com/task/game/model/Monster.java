package com.task.game.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class Monster extends Creature {

    @Override
    public String toString() {
        return "Monster : " +
            super.toString();
    }
}
