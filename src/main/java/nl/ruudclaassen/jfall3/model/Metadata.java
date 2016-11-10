package nl.ruudclaassen.jfall3.model;

import javax.validation.constraints.NotNull;


public class Metadata {
	private String id;

	@NotNull
	private String title;
	private String note;
	
	private String creationDate;

	@NotNull
	private int numberOfCodes;
	private String winningCode;

	public Metadata(){

	}
	
	public Metadata(String id, String title, String note, String creationDate, int numberOfCodes, String winningCode) {		
		this.id = id;
		this.title = title;
		this.note = note;
		this.creationDate = creationDate;
		this.numberOfCodes = numberOfCodes;
		this.winningCode = winningCode;
	}
	
	public Metadata(String id, String title, String note, String creationDate, int numberOfCodes){
		this.id = id;
		this.title = title;
		this.note = note;
		this.creationDate = creationDate;
		this.numberOfCodes = numberOfCodes;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWinningCode() {
        return winningCode;
    }

    public void setWinningCode(String winningCode) {
        this.winningCode = winningCode;
    }

    @Override
	public String toString(){
		
		String stringOutput = this.getId() + "," + this.getTitle() + "," + this.getNote() + "," + this.getCreationDate() + "," + this.getNumberOfCodes();
		
		// Prevent writing "null" as string output to the end of the line
		if(this.getWinningCode() != null){
			 stringOutput += "," + this.getWinningCode();
		}
		
		return stringOutput;
		 
	}
}
