package serverside.course.webapi.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import serverside.course.webapi.pojo.Game;

import java.util.Date;

@Value
@JsonPropertyOrder({"id","gameDate" ,"homeTeam","visitorTeam","season","home_team_score","visitor_team_score"})
public class GameDTO {
    @JsonIgnore
    private final Game game;

    public int getId(){return this.game.getGameNumber();}
    public Date getGameDate(){return this.game.getDate();}

    public String getHomeTeam(){return this.game.getHomeTeam();}
    public String getVisitorTeam(){return this.game.getVisitorTeam();}
    public int getSeason(){return this.game.getSeason();}

    public int getHomeTeamScore(){return this.game.getHomeTeamScore();}

    public int getVisitorTeamScore(){return this.game.getVisitorTeamScore();}

}
