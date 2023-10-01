package com.task.game.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.task.game.util.Helper.getRandom;
import static com.task.game.util.Helper.isFirst;

@Component
@NoArgsConstructor
@Getter
@Setter
public class Game {

    private Boolean isGameOn = false;
    private Integer round;
    private Player player;
    private List<Monster> monsters;
    private List<Integer> alive;
    private List<Integer> dead;
    private Integer currMonster;
    private RoundResult roundResult;

    public void start(Player player, List<Monster> monsters) {
        this.isGameOn = true;
        this.round = 1;
        player.init();
        monsters.forEach(Creature::init);
        this.player = player;
        this.monsters = monsters;
        this.alive = new ArrayList<>();
        this.dead = new ArrayList<>();
        for (int i = 0; i < monsters.size(); i++) {
            alive.add(i);
        }
        currMonster = getRandom(0, alive.size() - 1);
        roundResult = new RoundResult();
        chooseTurn();
    }

    public void nextRound() {
        round++;
    }

    public void changeTurn() {
        int i = alive.get(getRandom(0, alive.size() - 1));
        if (player.getIsTurn()) {
            player.setIsTurn(false);
            monsters.get(i).setIsTurn(true);
        } else {
            player.setIsTurn(true);
            monsters.get(currMonster).setIsTurn(false);
        }
        currMonster = i;
    }

    public Boolean getIsAttackSuccessful() {
        return this.roundResult.getIsAttackSuccessful();
    }

    public Integer getRoundDamage() {
        return this.roundResult.getDamage();
    }

    public void setIsAttackSuccessful(Boolean isAttackSuccessful) {
        this.roundResult.setIsAttackSuccessful(isAttackSuccessful);
    }

    public void setRoundDamage(Integer damage) {
        this.roundResult.setDamage(damage);
    }

    private void chooseTurn() {
        if (isFirst()) {
            player.setIsTurn(true);
        } else {
            monsters.get(currMonster).setIsTurn(true);
        }
    }

    @Getter
    @Setter
    private class RoundResult {
        private Boolean isAttackSuccessful;
        private Integer damage;
    }
}
