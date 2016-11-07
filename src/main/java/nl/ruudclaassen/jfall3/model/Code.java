package nl.ruudclaassen.jfall3.model;

public class Code {
    private String code;
    private Participant participant;

    public Code(String code) {
        this.code = code;
    }

    public Code(String code, Participant participant) {
        this.code = code;
        this.participant = participant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
