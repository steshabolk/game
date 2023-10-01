package com.task.game.util;

import com.task.game.dto.CreatureDto;
import com.task.game.dto.MonsterDto;
import com.task.game.dto.PlayerDto;
import com.task.game.model.Monster;
import com.task.game.model.Player;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DtoMapper {

    private final ModelMapper modelMapper;

    public Player convertToPlayer(CreatureDto creatureDto) {
        return modelMapper.map(creatureDto, Player.class);
    }

    public Monster convertToMonster(CreatureDto creatureDto) {
        return modelMapper.map(creatureDto, Monster.class);
    }

    public PlayerDto convertToPlayerDto(Player player) {
        return modelMapper.map(player, PlayerDto.class);
    }

    public MonsterDto convertToMonsterDto(Monster monster) {
        return modelMapper.map(monster, MonsterDto.class);
    }
}
