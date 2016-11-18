package nl.ruudclaassen.jfall3.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;
    private String note;

    // TODO: CR why is this a string and not a date?
    private String creationDate;

    @NotNull
    private int numberOfCodes;
    private int numberOfParticipants;

    @OneToOne
    private Participant winner;

    public Promotion() {}

    public Promotion(int id) {
        this.id = id;
    }

    public Promotion(int id, String title, String note, String creationDate, int numberOfCodes,
                    int numberOfParticipants, Participant winner) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.creationDate = creationDate;
        this.numberOfCodes = numberOfCodes;
        this.numberOfParticipants = numberOfParticipants;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getNumberOfCodes() {
        return numberOfCodes;
    }

    public void setNumberOfCodes(int numberOfCodes) {
        this.numberOfCodes = numberOfCodes;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public Participant getWinner() {
        return winner;
    }

    public void setWinner(Participant winner) {
        this.winner = winner;
    }

    // TODO: CR look into using the ReflectionToStringBuilder for this
    @Override
    public String toString() {

        String stringOutput = this.getId() + "," + this.getTitle() + "," + this.getNote() + ","
                + this.getCreationDate() + "," + this.getNumberOfCodes() + "," + this.getNumberOfParticipants();

        // Prevent writing "null" as string output to the end of the line
        if (this.getWinner() != null) {
            stringOutput += "," + this.winner.getId();
        }

        return stringOutput;

    }
}
