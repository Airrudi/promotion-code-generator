package nl.ruudclaassen.jfall3.model;

import javax.persistence.*;

@Entity
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String email;

	@ManyToOne
	private Promotion promotion;

	@OneToMany
	public String code;

	public Participant() {}

	public Participant(int id, String name, String code, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.code = code;
	}

	// Participants zijn gekoppeld aan een promo
	// Codes zijn gekoppeld aan een promo
	// Geen overlap promo's en participants
	// Code moet uniek zijn (je kunt dus met meerdere codes deelnemen), niet uniek is codeAlreadyExistsException)
	// Email hoeft niet uniek te zijn



	public int getId() {
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
