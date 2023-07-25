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

public class Team {

    @Id
    @GeneratedValue

    private Long id;

    private String name;


    private String conference;

  @JsonIgnore
  @OneToMany
    private List<Game> games = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    public Team(String name, String conference) {
        this.name = name;
        this.conference = conference;
    }
}
