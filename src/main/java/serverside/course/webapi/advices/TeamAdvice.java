package serverside.course.webapi.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import serverside.course.webapi.exceptions.PlayerNotFoundException;

@ControllerAdvice
public class TeamAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlayerNotFoundException.class)
    String TeamNotFoundHandler(PlayerNotFoundException cnfe) { return cnfe.getMessage();}
}
