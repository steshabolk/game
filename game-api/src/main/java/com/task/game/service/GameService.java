package com.task.game.service;

import com.task.game.dto.GameResponse;
import com.task.game.model.Creature;
import com.task.game.model.Game;
import com.task.game.model.Monster;
import com.task.game.model.Player;
import com.task.game.util.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.task.game.util.Helper.getRandom;
import static com.task.game.util.Helper.print;

@Service
public class GameService {

    private final Game game;
    private final DtoMapper dtoMapper;

    public GameService(Game game, DtoMapper dtoMapper) {
        this.game = game;
        this.dtoMapper = dtoMapper;
    }

    public void initGame(Player player, List<Monster> monsters) {
        game.start(player, monsters);
        print("Start new game!", player.toString(), monsters.toString());
    }

    public void fight() {
        Creature attacking;
        Creature defending;
        Player player = game.getPlayer();
        Monster monster = game.getMonsters().get(game.getCurrMonster());
        if (player.getIsTurn()) {
            print(player.toString(), "attacks ", monster.toString());
            attacking = player;
            defending = monster;
        } else {
            print(monster.toString(), " attacks ", player.toString());
            attacking = monster;
            defending = player;
        }
        _fight(attacking, defending);
        if (game.getIsAttackSuccessful()) print("Defending : " + defending);
        if (defending.getCurrHealth() == 0) {
            if (player.getIsTurn()) {
                game.getDead().add(game.getCurrMonster());
                game.getAlive().remove(game.getCurrMonster());
                print("Monster is dead");
            }
            if (!player.getIsTurn() || game.getAlive().size() == 0) {
                if (player.getCurrHealth() == 0) print("Player is dead");
                if (game.getAlive().size() == 0) print("All monsters are dead");
                game.setIsGameOn(false);
                print("The game is over!");
            }
        }
        if (isGameOn()) {
            game.changeTurn();
            game.nextRound();
        }
    }

    private void _fight(Creature attacking, Creature defending) {
        int mod = Math.abs(attacking.getAttack() - defending.getDefense()) + 1;
        boolean isSuccessful = false;
        for (int i = 1; i <= mod; i++) {
            int n = rollDice();
            if (n == 5 || n == 6) {
                isSuccessful = true;
                break;
            }
        }
        print("The attack was " + (isSuccessful ? "successful" : "unsuccessful"));
        Integer damage = null;
        if (isSuccessful) {
            damage = attacking.attack(defending);
            print("Damage " + damage);
        }
        game.setIsAttackSuccessful(isSuccessful);
        game.setRoundDamage(damage);
    }

    private int rollDice() {
        return getRandom(1, 6);
    }

    public Player recover() {
        game.getPlayer().recover();
        return game.getPlayer();
    }

    public GameResponse getGameInfo() {
        return GameResponse.builder()
            .isGameOn(isGameOn())
            .round(game.getRound())
            .isAttackSuccessful(game.getIsAttackSuccessful())
            .damage(game.getRoundDamage())
            .player(dtoMapper.convertToPlayerDto(game.getPlayer()))
            .monsters(game.getMonsters().stream().map(dtoMapper::convertToMonsterDto).toList())
            .currMonster(game.getCurrMonster())
            .build();
    }

    public boolean isGameOn() {
        return game.getIsGameOn();
    }
}

