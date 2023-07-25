package serverside.course.webapi.exceptions;


public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(Long id) {super("There is no Player corresponding to id ="+ id);
    }
}

