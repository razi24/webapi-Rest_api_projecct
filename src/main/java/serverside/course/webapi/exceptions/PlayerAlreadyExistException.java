package serverside.course.webapi.exceptions;

public class PlayerAlreadyExistException extends  RuntimeException {
    public PlayerAlreadyExistException(String name){
        super("Player with name " + name + " already exists");
    }
}
