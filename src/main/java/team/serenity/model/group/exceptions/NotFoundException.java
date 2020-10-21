package team.serenity.model.group.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Item not found");
    }

}
