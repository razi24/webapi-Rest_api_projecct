package serverside.course.webapi.EntityAssemblers;


import org.springframework.stereotype.Component;
import serverside.course.webapi.SimpleIdentifiableRepresentationModelAssembler;
import serverside.course.webapi.controllers.PlayerController;

@Component
public class PlayerEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler {
    public PlayerEntityAssembler(){super(PlayerController.class);}


}
