package model;

public class Ballot {
	// d�ja vot� ou non
	private boolean ballotCast = false;
	private long candidatSelected;

	public boolean isBallotCast() {
		return ballotCast;
	}

	public void setBallotCast(boolean ballotCast) {
		this.ballotCast = ballotCast;
	}

	public long getCandidatSelected() {
		return candidatSelected;
	}

	public void setCandidatSelected(long candidatSelected) {
		this.candidatSelected = candidatSelected;
	}

}
