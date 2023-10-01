package com.task.game.model;

import com.task.game.exception.RequestException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.task.game.util.Helper.print;

@Component
@Setter
@Getter
public class Player extends Creature {

    private Integer recovery = 4;
    private static final Double RECOVERY_CONST = 0.3;

    public void recover() {
        if (recovery == 0) {
            throw new RequestException("All recovery options have been used", HttpStatus.BAD_REQUEST);
        }
        if (this.getCurrHealth() == this.getMaxHealth()) {
            throw new RequestException("Health is already max", HttpStatus.BAD_REQUEST);
        }
        if (this.getCurrHealth() == 0) {
            throw new RequestException("You are dead", HttpStatus.BAD_REQUEST);
        }
        if (recovery > 0) {
            recovery--;
            this.setCurrHealth(Math.min(this.getMaxHealth(), this.getCurrHealth() + (int) (this.getMaxHealth() * RECOVERY_CONST)));
            print("The player has recovered his health", this.toString());
        }
    }

    @Override
    public String toString() {
        return "Player : " +
            "recovery=" + recovery +
            ", " + super.toString();
    }
}
