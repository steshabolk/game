package com.task.game.controller;

import com.task.game.dto.InitRequest;
import com.task.game.exception.RequestException;
import com.task.game.model.Player;
import com.task.game.service.GameService;
import com.task.game.util.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.task.game.util.Validation.buildErrMsg;
import static com.task.game.util.Validation.validate;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final DtoMapper dtoMapper;

    @Autowired
    public GameController(GameService gameService, DtoMapper dtoMapper) {
        this.gameService = gameService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping()
    public ResponseEntity<?> initGame(@RequestBody @Valid InitRequest initRequest, BindingResult bindingResult) {
        validate(initRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            String message = buildErrMsg(bindingResult);
            throw new RequestException(message, HttpStatus.BAD_REQUEST);
        }
        gameService.initGame(dtoMapper.convertToPlayer(initRequest.getPlayer()),
            initRequest.getMonsters().stream().map(dtoMapper::convertToMonster).toList());
        return new ResponseEntity<>(gameService.getGameInfo(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getGameInfo() {
        if (!gameService.isGameOn()) {
            throw new RequestException("No active game", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gameService.getGameInfo(), HttpStatus.OK);
    }

    @GetMapping("/fight")
    public ResponseEntity<?> fight() {
        if (!gameService.isGameOn()) {
            throw new RequestException("No active game", HttpStatus.BAD_REQUEST);
        }
        gameService.fight();
        return new ResponseEntity<>(gameService.getGameInfo(), HttpStatus.OK);
    }

    @GetMapping("/recover")
    public ResponseEntity<?> recover() {
        if (!gameService.isGameOn()) {
            throw new RequestException("No active game", HttpStatus.BAD_REQUEST);
        }
        Player player = gameService.recover();
        return new ResponseEntity<>(dtoMapper.convertToPlayerDto(player), HttpStatus.OK);
    }
}
