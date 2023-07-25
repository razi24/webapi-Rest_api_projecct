package serverside.course.webapi.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import serverside.course.webapi.pojo.Player;

@Value
@JsonPropertyOrder({"id","name" ,"Team","position"
        , "height","avgPoints","avgAssists","avgRebounds","avgBlocks","avgSteals"})
public class PlayerDTO {
    @JsonIgnore

    private final Player player;

    //getters to get actual information

    public Long getId(){return this.player.getId();}

    public String getName(){return this.player.getName();}

    public String getTeamName(){return this.player.getTeam() == null ? "free agent" : this.player.getTeam().getName();}

    public String getPosition(){return this.player.getPosition();}

    public double getHeight(){return this.player.getHeight();}

    public double getAvgPoints(){return this.player.getAvgPoints();}
    public double getAvgAssists(){return this.player.getAvgAssists();}
    public double getAvgRebounds(){return this.player.getAvgRebounds();}

    public double getAvgBlocks(){return this.player.getAvgBlocks();}

    public double getAvgSteals(){return this.player.getAvgSteals();}



}
