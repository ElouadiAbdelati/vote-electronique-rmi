package model;

import java.io.Serializable;
import java.security.PublicKey;

public class Voter implements Serializable {
	private String fullName;
	private PublicKey publicKey;
	private Ballot ballot;

	public Voter(String fullName, PublicKey publicKey) {
		super();
		this.fullName = fullName;
		this.publicKey = publicKey;
		this.ballot = new Ballot();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public Ballot getBallot() {
		return ballot;
	}

	public void setBallot(Ballot ballot) {
		this.ballot = ballot;
	}

}
