package serverside.course.webapi.EntityAssemblers;


import org.springframework.stereotype.Component;
import serverside.course.webapi.SimpleIdentifiableRepresentationModelAssembler;
import serverside.course.webapi.controllers.GameController;

@Component
public class GameEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler {
    public GameEntityAssembler(){super(GameController.class);}


}
