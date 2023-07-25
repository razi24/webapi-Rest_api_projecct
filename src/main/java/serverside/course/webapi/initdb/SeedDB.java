package serverside.course.webapi.initdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import serverside.course.webapi.pojo.Game;
import serverside.course.webapi.pojo.Player;
import serverside.course.webapi.pojo.PlayersEx;
import serverside.course.webapi.pojo.Team;
import serverside.course.webapi.repos.GameRepo;
import serverside.course.webapi.repos.PlayerRepo;
import serverside.course.webapi.repos.TeamRepo;
import serverside.course.webapi.services.AggregatePlayerService;
//import serverside.course.webapi.services.AggregatePlayerService;
;import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Configuration
    public class SeedDB {
        private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
        // class declares one or more @Bean method
        // Spring IoC generates bean definitions and handles request beans (at runtime)
        @Bean
        // CommandLineRunner beans are created once the application context is loaded
        CommandLineRunner seed(PlayerRepo playerRepo, TeamRepo teamRepo, GameRepo gameRepo){
            return args -> {


                Team team=teamRepo.save(new Team("Lakers","west"));
                Team team2=teamRepo.save(new Team("Rockets","west"));
                Player player22=new Player("razi","f",2.18);
                Player player = new Player("razir","center",2.18 );
                Player player2= new Player("Harden","PF",2.00 );
                player.setTeam(team);
                player22.setTeam(team);
                player2.setTeam(team2);
                playerRepo.save(player22);
                playerRepo.save(player2);
                playerRepo.save(player);



                Game game= gameRepo.save(new Game(1,
                        new Date(123 ,2,22),"Lakers","Rockets",2023,106,87));
                game.setPlayers(Arrays.asList(player,player2));
                logger.info("" +team2 );
                logger.info(""+game);
                logger.info(""+ team);
            };
            
            
        }

 /*   @Bean
    public CommandLineRunner initPlayers(AggregatePlayerService aggregatePlayerService,
                                         PlayerRepo playerRepo) {
        return args -> {
            logger.info("initializing CoinTypes...");
            CompletableFuture<List<Player>> completableFutureCoinTypes = aggregatePlayerService.pullExternalData();
            completableFutureCoinTypes.join();
            List<Player> playerList = completableFutureCoinTypes.get();
            int iterationNumber = 1;
            for (Player coinTypeEntity : playerList) {
                logger.info("Saved to repo CoinType {}/{} = {}",
                        iterationNumber++, playerList.size(), playerRepo.save(coinTypeEntity));
            }
        };
    }*/


    @Bean
    public CommandLineRunner initPlayers(AggregatePlayerService aggregatePlayerService , PlayerRepo playerRepo) throws Exception
    {
        CompletableFuture<PlayersEx> p1=aggregatePlayerService.getPlayerDetails(1);
        CompletableFuture<PlayersEx> p2=aggregatePlayerService.getPlayerDetails(237);
        CompletableFuture.allOf(p1,p2).join();
        logger.info("111" + p1.get());
        logger.info("111" + p2.get());
        return args ->
                System.out.println("hi");

    }
        
        
    }

