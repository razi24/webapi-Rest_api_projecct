package serverside.course.webapi.controllers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serverside.course.webapi.DtoAssemblers.PlayerDtoAssembler;
import serverside.course.webapi.dtos.PlayerDTO;
import serverside.course.webapi.EntityAssemblers.PlayerEntityAssembler;
import serverside.course.webapi.exceptions.PlayerAlreadyExistException;
import serverside.course.webapi.exceptions.PlayerNotFoundException;
import serverside.course.webapi.pojo.Player;
import serverside.course.webapi.repos.PlayerRepo;
import serverside.course.webapi.services.AggregatePlayerService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class PlayerController {
    private final PlayerRepo playerRepo;
    private PlayerEntityAssembler playerEntityAssembler;
    private final AggregatePlayerService aggregatePlayerService;

    private PlayerDtoAssembler playerDtoAssembler;


    public PlayerController(PlayerRepo playerRepo, PlayerEntityAssembler playerEntityAssembler, PlayerDtoAssembler playerDtoAssembler,AggregatePlayerService aggregatePlayerService) {
        this.playerRepo = playerRepo;
        this.playerEntityAssembler = playerEntityAssembler;
        this.playerDtoAssembler = playerDtoAssembler;
        this.aggregatePlayerService=aggregatePlayerService;
    }

    @GetMapping("/players")
    public ResponseEntity<CollectionModel<EntityModel<Player>>> allPlayers() {
        return ResponseEntity.ok(playerEntityAssembler.toCollectionModel(playerRepo.findAll()));
    }
    @GetMapping("/Players/{id}")
    public ResponseEntity<EntityModel> singlePlayers(@PathVariable long id) {
        return playerRepo.findById(id).map(playerEntityAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/simplePlayers")
    public List<Player> allPlayersSimple(){
        return (List<Player>) playerRepo.findAll();
    }

    @GetMapping("/players/info")
    public ResponseEntity<CollectionModel<EntityModel<PlayerDTO>>> allPlayersInfo(){
        return ResponseEntity.ok(playerDtoAssembler.toCollectionModel(
                StreamSupport.stream(playerRepo.findAll().spliterator(),false)
                        .map(PlayerDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/players/{id}/info")
    public ResponseEntity<EntityModel<PlayerDTO>> singlePlayerInfo(@PathVariable long id){
        return playerRepo.findById(id)
                .map(PlayerDTO::new)
                .map(playerDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/players/{TeamName}")
    public ResponseEntity<EntityModel<PlayerDTO>> PlayerByTeam(@PathVariable String TeamName){
        return playerRepo.findByTeamName(TeamName)
                .map(PlayerDTO::new)
                .map(playerDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("players/{id}")
    public ResponseEntity<String> deletePlayerById(@PathVariable Long id)
    {
        Player player=playerRepo.findById(id).orElseThrow(()-> new PlayerNotFoundException(id));
        playerRepo.deleteById(id);
        return ResponseEntity.ok("Player with id = " + id + " was deleted");


    }

    @PostMapping
    //create new player
    public ResponseEntity<?> createPlayer(@RequestBody Player player){
        try {
            EntityModel<PlayerDTO> player1= aggregatePlayerService.createPlayer(player);
            return ResponseEntity.created(new URI(player1.getRequiredLink(IanaLinkRelations.SELF).getHref())).body(player1);
        } catch (URISyntaxException uriSyntaxException){
            return  ResponseEntity.badRequest().body("Failed to create player " + player);
        }


    }

    @PutMapping("/{playerId}")
    //update existing player
    public ResponseEntity<EntityModel<PlayerDTO>> updatePlayer(@PathVariable Long playerId, @RequestBody Player player){
        return ResponseEntity.ok(aggregatePlayerService.updatePlayer(playerId,player));
    }










}
