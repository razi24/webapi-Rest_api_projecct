package serverside.course.webapi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue

    private Long id;
    private int gameNumber;
    private Date date;
    private String homeTeam;
    private String visitorTeam;
    private int season;
    private int homeTeamScore;
    private int visitorTeamScore;

    @JsonIgnore
    @ManyToMany
    private List<Player> players=new ArrayList<>();

    public Game(int gameNumber, Date date, String homeTeam, String visitorTeam, int season, int homeTeamScore, int visitorTeamScore) {
        this.gameNumber=gameNumber;
        this.date = date;
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
        this.season = season;
        this.homeTeamScore = homeTeamScore;
        this.visitorTeamScore = visitorTeamScore;
    }
}
