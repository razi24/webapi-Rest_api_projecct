package serverside.course.webapi.EntityAssemblers;


import org.springframework.stereotype.Component;
import serverside.course.webapi.SimpleIdentifiableRepresentationModelAssembler;
import serverside.course.webapi.controllers.TeamController;

@Component
public class TeamEntityAssembler extends SimpleIdentifiableRepresentationModelAssembler {
    public TeamEntityAssembler(){super(TeamController.class);}


}
