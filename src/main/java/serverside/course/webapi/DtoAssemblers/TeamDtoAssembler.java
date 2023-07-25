package serverside.course.webapi.DtoAssemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import serverside.course.webapi.controllers.TeamController;
import serverside.course.webapi.dtos.TeamDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeamDtoAssembler implements SimpleRepresentationModelAssembler<TeamDTO> {

    @Override
    public void addLinks(EntityModel<TeamDTO> resource) {
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(TeamController.class)
                .singleTeamInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(TeamController.class)
                .allTeamsInfo())
                .withRel("Team information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<TeamDTO>> resources) {
        resources.add(linkTo(methodOn(TeamController.class).allTeamsInfo()).withSelfRel());
    }
}

