package nl.ruudclaassen.jfall3.model;

public class Participant {
    private String name;
    private String email;
    public String code;

    public Participant(String name, String code, String email) {
        this.name = name;
        this.email = email;
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}

    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}

    @Override
    public String toString(){
        String stringOutput = this.getName() + "," + this.getCode() + "," + this.getEmail();
        return stringOutput;
    }
}
