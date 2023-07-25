package serverside.course.webapi.services;

import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import serverside.course.webapi.DtoAssemblers.PlayerDtoAssembler;
import serverside.course.webapi.dtos.PlayerDTO;
import serverside.course.webapi.exceptions.PlayerAlreadyExistException;
import serverside.course.webapi.exceptions.PlayerNotFoundException;
import serverside.course.webapi.pojo.Player;
import serverside.course.webapi.pojo.PlayersEx;
import serverside.course.webapi.pojo.Team;
import serverside.course.webapi.repos.PlayerRepo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/* We use @Service to do 2 things:
 1. theoretical decoupling: between Views (HTML document, deserialized DAO/DTO),
 Controllers that serve users at different URLs with specific parameters
 and business logic of our application (Model)
 2. In a practical sense: this annotation makes Spring scan this component and use it as
 a candidate for autowiring*/
 

@Service

/**
 * This class uses a remote REST endpoint to get data from Github's REST API.*/
 

public class AggregatePlayerService {
    // RestTemplate is used to invoke an external REST endpoint by another service
    private RestTemplate restTemplate;
    private final PlayerDtoAssembler playerDtoAssembler;
    private  final PlayerRepo playerRepo;
    private String externalEndpointUrl="https://www.balldontlie.io/api/v1/players";
    private static final Logger serviceLogger = LoggerFactory.getLogger(AggregatePlayerService.class);


    public AggregatePlayerService(RestTemplateBuilder templateBuilder,PlayerDtoAssembler playerDtoAssembler,PlayerRepo playerRepo){
        this.restTemplate = templateBuilder.build();
        this.playerDtoAssembler= playerDtoAssembler;
        this.playerRepo=playerRepo;
    }

   /* Asynchronous tasks types in Java:
    1. Runnable - interface
    2. Callable<V> - interface
    3. RunnableFuture<V> - interface that defines a Runnable that can return as Future<V>
    4. A concrete type that implements RunnableFuture is FutureTask<V>. it is a generic type
    that can wrap both Runnable and Callable<V> tasks
    ** Disadvantages of Callable<V> in conjunction with Future<V>?
    1. no manual completion of a task in case of failure
    2. only 3 exceptions can be thrown (what about UnAuthorizedException if API requires authentication)
    3. no concurrent tasks result can be joined in a separate thread
    4. no pipelining of concurrent tasks that depend on each other
    Can be resolved using CompletableFuture<V>*/



    @Async
    public CompletableFuture<PlayersEx> getPlayerDetails(int id){
        String template = String.format("https://api.github.com/users/%s",id);
        PlayersEx aUser = this.restTemplate.getForObject(template,PlayersEx.class);
        serviceLogger.info("Running in thread = " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(aUser);
    }

    @Async
    public CompletableFuture<List<Player>> pullExternalData() {
        serviceLogger.info("Remote API URL - '" + externalEndpointUrl + "'");

        List playersTypesAsJson = this.restTemplate.getForObject(this.externalEndpointUrl, List.class).stream().toList();
        List<Player> playerTypeEntities = new ArrayList<>();
        assert playersTypesAsJson != null;
        int numberOfPlayerTypes = playersTypesAsJson.size();
        Player tempPlayer;

        for (int i = 0; i < numberOfPlayerTypes; i++) {

            tempPlayer = Parse((LinkedHashMap) playersTypesAsJson.get(i));
            playerTypeEntities.add(tempPlayer);
            serviceLogger.info("Parsed playerType {}/{} = {}", (i + 1), numberOfPlayerTypes, tempPlayer);
        }
        return CompletableFuture.completedFuture(playerTypeEntities);
    }

    private Player Parse(@NotNull LinkedHashMap dataToParse) {
        return new Player(dataToParse.get("first_name").toString().concat(""+dataToParse.get("last_name").toString()),
                dataToParse.get("position").toString(),
                2.12);

    }


        public EntityModel<PlayerDTO> createPlayer(@NotNull Player player) {
        if (playerRepo.existsByName(player.getName())) {
            throw new PlayerAlreadyExistException(player.getName());
        }
        serviceLogger.info("Saving new player to database . . . Info = " + player);
        playerRepo.save(player);
        return  playerDtoAssembler.toModel(new PlayerDTO(player));
    }


    public EntityModel<PlayerDTO> updatePlayer(Long playerId, Player player) {
        serviceLogger.info("Player update received in PUT request body = " + player);
        Player updatedPlayer = playerRepo.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        updatedPlayer.setName(player.getName() == null ? updatedPlayer.getName() : player.getName());
        updatedPlayer.setHeight(player.getHeight() == 0 ? updatedPlayer.getHeight() : player.getHeight());
        updatedPlayer.setPosition(player.getPosition() == null ? updatedPlayer.getPosition() : player.getPosition());
        updatedPlayer.setTeam(player.getTeam()== null ? updatedPlayer.getTeam() : player.getTeam() );
        playerRepo.save(updatedPlayer);

        serviceLogger.info("Updated Player info after PUT request = " + updatedPlayer);
        return playerDtoAssembler.toModel(new PlayerDTO(updatedPlayer));
    }



    
    



}
