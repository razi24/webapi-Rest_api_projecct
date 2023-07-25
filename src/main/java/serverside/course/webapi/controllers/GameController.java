package serverside.course.webapi.controllers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import serverside.course.webapi.DtoAssemblers.GameDtoAssembler;
import serverside.course.webapi.dtos.GameDTO;
import serverside.course.webapi.EntityAssemblers.GameEntityAssembler;
import serverside.course.webapi.exceptions.GameNotFoundException;
import serverside.course.webapi.pojo.Game;
import serverside.course.webapi.repos.GameRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class GameController {
    private final GameRepo gameRepo;
    private GameEntityAssembler gameEntityAssembler;

    private GameDtoAssembler gameDtoAssembler;


    public GameController(GameRepo gameRepo, GameEntityAssembler gameEntityAssembler, GameDtoAssembler gameDtoAssembler) {
        this.gameRepo = gameRepo;
        this.gameEntityAssembler = gameEntityAssembler;
        this.gameDtoAssembler = gameDtoAssembler;
    }

    @GetMapping("/Games")
    public ResponseEntity<CollectionModel<EntityModel<Game>>> allGames() {
        return ResponseEntity.ok(gameEntityAssembler.toCollectionModel(gameRepo.findAll()));
    }
    @GetMapping("/Games/{id}")
    public ResponseEntity<EntityModel> singleGames(@PathVariable long id) {
        return gameRepo.findById(id).map(gameEntityAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/simpleGames")
    public List<Game> allGamesSimple(){
        return (List<Game>) gameRepo.findAll();
    }

    @GetMapping("/Games/info")
    public ResponseEntity<CollectionModel<EntityModel<GameDTO>>> allGamesInfo(){
        return ResponseEntity.ok(gameDtoAssembler.toCollectionModel(
                StreamSupport.stream(gameRepo.findAll().spliterator(),false)
                        .map(GameDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/Games/{id}/info")
    public ResponseEntity<EntityModel<GameDTO>> singleGameInfo(@PathVariable long id){
        return gameRepo.findById(id)
                .map(GameDTO::new)
                .map(gameDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Games/{season}")
    public ResponseEntity<EntityModel<GameDTO>> GameBySeason(@PathVariable int season){
        return gameRepo.findBySeason(season)
                .map(GameDTO::new)
                .map(gameDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("Games/{id}")
    public ResponseEntity<String> deleteGameById(@PathVariable Long id) {
        Game game = gameRepo.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        gameRepo.deleteById(id);
        return ResponseEntity.ok("Game with id = " + id + " was deleted");
    }







}
