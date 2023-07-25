package serverside.course.webapi.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import serverside.course.webapi.exceptions.GameNotFoundException;
import serverside.course.webapi.exceptions.PlayerNotFoundException;

@ControllerAdvice
public class PlayerAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlayerNotFoundException.class)
    String PlayerNotFoundHandler(PlayerNotFoundException cnfe) { return cnfe.getMessage(); }
}
