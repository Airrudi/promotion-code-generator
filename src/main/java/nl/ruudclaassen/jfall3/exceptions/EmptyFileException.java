package nl.ruudclaassen.jfall3.exceptions;

/**
 * Created by OCS on 16/11/2016.
 */
public class EmptyFileException extends RuntimeException {

    public EmptyFileException(String message){
        super(message);
    }
}
