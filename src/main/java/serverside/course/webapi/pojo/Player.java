package serverside.course.webapi.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Player {
    @Id
    @GeneratedValue

    private Long id;
    private String name;
    private String position;
    private double height;
    private double avgPoints=0;
    private double avgAssists=0;
    private double avgRebounds=0;
    private double avgBlocks=0;
    private double avgSteals=0;



    @JsonIgnore
    @ManyToMany
    private List<Game> games = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private  Team team;



    public Player(String name, String position, double height) {
        this.name = name;
        this.position = position;
        this.height = height;
        //this.team=team;
        //this.seasonStats=seasonStats;
    }
}
