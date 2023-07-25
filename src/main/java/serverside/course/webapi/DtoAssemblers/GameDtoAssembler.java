package serverside.course.webapi.DtoAssemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import serverside.course.webapi.controllers.GameController;
import serverside.course.webapi.dtos.GameDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class GameDtoAssembler implements SimpleRepresentationModelAssembler<GameDTO> {
    @Override
    public void addLinks(EntityModel<GameDTO> resource) {
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(GameController.class)
                .singleGameInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(GameController.class)
                .allGamesInfo())
                .withRel("Game information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<GameDTO>> resources) {
        resources.add(linkTo(methodOn(GameController.class).allGamesInfo()).withSelfRel());
    }
}
