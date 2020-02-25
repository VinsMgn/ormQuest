package fr.epsi.vincent.exception;

public class AlreadyExistsException extends Exception {
    private static final long serialVersionUID = 4466964357831306478L;

    public AlreadyExistsException(String message) {
        super(message);
    }
}
