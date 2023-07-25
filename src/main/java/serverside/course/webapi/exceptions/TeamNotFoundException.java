package serverside.course.webapi.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String name) { super("There is no team corresponding to name = " + name); }
}
