package nl.ruudclaassen.jfall3.exceptions;

/**
 * Created by OCS on 16/11/2016.
 */
public class FileTooBigException extends RuntimeException {
    public FileTooBigException(String message){
        super(message);
    }

}
