package com.task.game.model;

import com.task.game.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.task.game.model.GameConstant.DEAD_HEALTH;
import static com.task.game.model.GameConstant.MAX_ATTACK;
import static com.task.game.model.GameConstant.MAX_DAMAGE;
import static com.task.game.model.GameConstant.MAX_DEFENSE;
import static com.task.game.model.GameConstant.MAX_HEALTH;
import static com.task.game.model.GameConstant.MIN_ATTACK;
import static com.task.game.model.GameConstant.MIN_DAMAGE;
import static com.task.game.model.GameConstant.MIN_DEFENSE;
import static com.task.game.model.GameConstant.MIN_HEALTH;
import static com.task.game.util.Helper.getRandom;

@Component
@Getter
@Setter
public abstract class Creature {

    @Range(min = 1, max = 30, message = "{range_1_30}")
    private Integer attack;

    @Range(min = 1, max = 30, message = "{range_1_30}")
    private Integer defense;

    @Range(min = 1, max = 500, message = "{range_1_500}")
    private Integer maxHealth;

    @Range(min = 1, max = 500, message = "{range_1_500}")
    private Integer currHealth;

    @Range(min = 1, max = 100, message = "{range_1_100}")
    private Integer minDamage;

    @Range(min = 1, max = 100, message = "{range_1_100}")
    private Integer maxDamage;

    private Boolean isTurn = false;

    public void init() {
        if (this.attack == null) {
            this.attack = getRandom(MIN_ATTACK, MAX_ATTACK);
        } else {
            _validate(this.attack, MIN_ATTACK, MAX_ATTACK);
        }
        if (this.defense == null) {
            this.defense = getRandom(MIN_DEFENSE, MAX_DEFENSE);
        } else {
            _validate(this.defense, MIN_DEFENSE, MAX_DEFENSE);
        }
        if (this.maxHealth == null) {
            this.maxHealth = getRandom(MIN_HEALTH, MAX_HEALTH);
        } else {
            _validate(this.maxHealth, MIN_HEALTH, MAX_HEALTH);
        }
        this.currHealth = this.maxHealth;
        if (this.minDamage == null) {
            this.minDamage = getRandom(MIN_DAMAGE, Objects.requireNonNullElse(this.maxDamage, MAX_DAMAGE));

        } else {
            _validate(this.minDamage, MIN_DAMAGE, Objects.requireNonNullElse(this.maxDamage, MAX_DAMAGE));

        }
        if (this.maxDamage == null) {
            this.maxDamage = getRandom(this.minDamage, MAX_DAMAGE);
        } else {
            _validate(this.maxDamage, this.minDamage, MAX_DAMAGE);
        }
    }

    public Integer attack(Creature creature) {
        Integer damage = getRandom(this.getMinDamage(), this.getMaxDamage());
        creature.setCurrHealth(Math.max(DEAD_HEALTH, creature.getCurrHealth() - damage));
        return damage;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    private void _validate(Integer val, Integer min, Integer max) {
        if (val < min || val > max) {
            throw new ValidationException(String.format("Value should be in range [%s-%s]", min, max));
        }
    }


    @Override
    public String toString() {
        return "{ attack=" + attack +
            ", defense=" + defense +
            ", maxHealth=" + maxHealth +
            ", currHealth=" + currHealth +
            ", minDamage=" + minDamage +
            ", maxDamage=" + maxDamage +
            ", isTurn=" + isTurn + " }";
    }
}
