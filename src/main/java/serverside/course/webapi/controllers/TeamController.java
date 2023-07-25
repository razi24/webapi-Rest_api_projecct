package serverside.course.webapi.controllers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import serverside.course.webapi.DtoAssemblers.TeamDtoAssembler;
import serverside.course.webapi.dtos.TeamDTO;
import serverside.course.webapi.EntityAssemblers.TeamEntityAssembler;
import serverside.course.webapi.exceptions.TeamNotFoundException;
import serverside.course.webapi.pojo.Team;
import serverside.course.webapi.repos.TeamRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TeamController {
    private final TeamRepo teamRepo;
    private TeamEntityAssembler teamEntityAssembler;

    private TeamDtoAssembler teamDtoAssembler;


    public TeamController(TeamRepo TeamRepo, TeamEntityAssembler TeamEntityAssembler, TeamDtoAssembler TeamDtoAssembler) {
        this.teamRepo = TeamRepo;
        this.teamEntityAssembler = TeamEntityAssembler;
        this.teamDtoAssembler = TeamDtoAssembler;
    }

    @GetMapping("/Teams")
    public ResponseEntity<CollectionModel<EntityModel<Team>>> allTeams() {
        return ResponseEntity.ok(teamEntityAssembler.toCollectionModel(teamRepo.findAll()));
    }
    @GetMapping("/Teams/{id}")
    public ResponseEntity<EntityModel> singleTeams(@PathVariable long id) {
        return teamRepo.findById(id).map(teamEntityAssembler::toModel).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/simpleTeams")
    public List<Team> allTeamsSimple(){
        return (List<Team>) teamRepo.findAll();
    }

    @GetMapping("/Teams/info")
    public ResponseEntity<CollectionModel<EntityModel<TeamDTO>>> allTeamsInfo(){
        return ResponseEntity.ok(teamDtoAssembler.toCollectionModel(
                StreamSupport.stream(teamRepo.findAll().spliterator(),false)
                        .map(TeamDTO::new).collect(Collectors.toList())));
    }



    @GetMapping("/Teams/{id}/info")
    public ResponseEntity<EntityModel<TeamDTO>> singleTeamInfo(@PathVariable long id){
        return teamRepo.findById(id)
                .map(TeamDTO::new)
                .map(teamDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Teams/{TeamName}")
    public ResponseEntity<EntityModel<TeamDTO>> TeamByTeam(@PathVariable String TeamName){
        return teamRepo.findByName(TeamName)
                .map(TeamDTO::new)
                .map(teamDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("Teams/{name}")
    public ResponseEntity<String> deleteTeamByName(@PathVariable String name)
    {
        Team team=teamRepo.findByName(name).orElseThrow(()-> new TeamNotFoundException(name));
        teamRepo.deleteByName(name);
        return ResponseEntity.ok("Player with id = " + name + " was deleted");


    }




}
