package serverside.course.webapi.repos;

import org.springframework.data.repository.CrudRepository;
import serverside.course.webapi.pojo.Team;

;import java.util.List;
import java.util.Optional;

public interface TeamRepo extends CrudRepository<Team, Long> {
    List<Team> findByConference(String conference);


    Optional<Team> findByName(String name);
    void deleteByName(String name);


}
