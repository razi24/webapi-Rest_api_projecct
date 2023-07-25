package serverside.course.webapi.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import serverside.course.webapi.exceptions.GameNotFoundException;

@ControllerAdvice
public class GameAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GameNotFoundException.class)
    String GameNotFoundHandler(GameNotFoundException cnfe) { return cnfe.getMessage(); }

}
