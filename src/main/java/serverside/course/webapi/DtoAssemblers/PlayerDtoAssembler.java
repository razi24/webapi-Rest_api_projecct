package serverside.course.webapi.DtoAssemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import serverside.course.webapi.controllers.PlayerController;
import serverside.course.webapi.dtos.PlayerDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayerDtoAssembler implements SimpleRepresentationModelAssembler<PlayerDTO> {

    @Override
    public void addLinks(EntityModel<PlayerDTO> resource) {
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(PlayerController.class)
                .singlePlayerInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(PlayerController.class)
                .allPlayersInfo())
                .withRel("Player information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<PlayerDTO>> resources) {
        resources.add(linkTo(methodOn(PlayerController.class).allPlayersInfo()).withSelfRel());
    }
}

