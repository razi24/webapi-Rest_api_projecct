package serverside.course.webapi.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(Long id) {super("There is no Player corresponding to id ="+ id);
    }
}
