package serverside.course.webapi.repos;

import org.springframework.data.repository.CrudRepository;
import serverside.course.webapi.pojo.Player;
import serverside.course.webapi.pojo.Team;

import java.util.List;
import java.util.Optional;

public interface PlayerRepo extends CrudRepository<Player,Long> {
    Optional<Player> findByTeamName(String teamName);
    Optional<Player> findPlayerById(Long id);


    List<Player> findPlayerByTeamOrderByAvgAssists(Team team);
    Optional<Player> findPlayerByTeamOrderByAvgPoints(Team team);

    List<Player> findPlayerByAvgPointsAfter(double minPoints);

    Boolean existsByName(String name);

}
