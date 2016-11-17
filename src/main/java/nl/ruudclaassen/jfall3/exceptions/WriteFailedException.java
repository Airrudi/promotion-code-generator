package nl.ruudclaassen.jfall3.exceptions;

/**
 * Created by OCS on 16/11/2016.
 */
public class WriteFailedException extends RuntimeException {

    public WriteFailedException(String message){
        super(message);
    }
}
