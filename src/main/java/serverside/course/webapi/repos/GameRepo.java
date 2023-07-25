package serverside.course.webapi.repos;

import org.springframework.data.repository.CrudRepository;
import serverside.course.webapi.pojo.Game;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GameRepo extends CrudRepository<Game,Long> {

    List<Game> findByDate(Date date);
    Optional<Game> findBySeason(int season);




}
