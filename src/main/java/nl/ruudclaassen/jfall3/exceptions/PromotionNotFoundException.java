package nl.ruudclaassen.jfall3.exceptions;


public class PromotionNotFoundException extends RuntimeException{
    public PromotionNotFoundException(String message){
        super(message);
    }
}
