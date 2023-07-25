package serverside.course.webapi.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import serverside.course.webapi.pojo.Player;
import serverside.course.webapi.pojo.Team;

import java.util.ArrayList;
import java.util.List;

@Value
@JsonPropertyOrder({"id","teamName" ,"conference","players"})
public class TeamDTO {
    @JsonIgnore
    private final Team team;

    public Long getId(){return this.team.getId();}

    public String getTeamName(){return this.team.getName();}

    public String getConference(){return this.team.getConference();}

    public List<String> getPlayers(){
        List<String> playersNames=new ArrayList<>();
        for (Player player: this.team.getPlayers()
             ) {
            playersNames.add(player.getName());

        }
        return playersNames;



    }


}
