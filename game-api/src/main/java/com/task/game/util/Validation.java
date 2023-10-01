package com.task.game.util;

import com.task.game.dto.CreatureDto;
import com.task.game.dto.InitRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class Validation {

    public static void validate(InitRequest initRequest, BindingResult bindingResult) {
        isDamageRangeValid(initRequest.getPlayer(), bindingResult);
        initRequest.getMonsters().forEach(monster -> isDamageRangeValid(monster, bindingResult));
    }

    private static void isDamageRangeValid(CreatureDto creatureDto, BindingResult bindingResult) {
        if (creatureDto != null && creatureDto.getMinDamage() != null && creatureDto.getMaxDamage() != null &&
            creatureDto.getMinDamage() > creatureDto.getMaxDamage()) {
            bindingResult.rejectValue("maxDamage", "", "min <= max");
        }
    }

    public static String buildErrMsg(BindingResult bindingResult) {
        StringBuilder errMsg = new StringBuilder();
        for (FieldError e : bindingResult.getFieldErrors()) {
            errMsg
                .append(e.getField())
                .append(" : ")
                .append(e.getDefaultMessage())
                .append("; ");
        }
        return errMsg.toString().trim();
    }
}
