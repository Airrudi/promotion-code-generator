package nl.ruudclaassen.jfall3.model;

public class Participant {
	private String id;
	private String name;
	private String email;
	public String code;

	public Participant(String id, String name, String code, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.code = code;
	}

	public String getId() {
		return id;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		String stringOutput = this.getId() + "," + this.getName() + "," + this.getCode() + "," + this.getEmail();
		return stringOutput;
	}
}
