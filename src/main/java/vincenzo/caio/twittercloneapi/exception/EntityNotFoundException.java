package vincenzo.caio.twittercloneapi.exception;


public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entity, String message) {
        super("Error trying to find " + entity + ". " + message);
    }
}
