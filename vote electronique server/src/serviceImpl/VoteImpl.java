package serviceImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;

import database.Database;
import model.Candidat;
import model.Election;
import model.Voter;
import service.VoteInterface;
import utils.DigitalSignature;

public class VoteImpl extends UnicastRemoteObject implements VoteInterface {

	public VoteImpl() throws RemoteException {
		super();
	}

	@Override
	public String vote(long candidatId, byte[] signature, PublicKey publicKey, String message) throws RemoteException {

		String msg = this.validatorVote(candidatId, signature, publicKey, message);

		if (msg != null) {
			return msg;
		}

		Database.candidats.forEach(c -> {
			if (c.getId() == candidatId) {
				c.setCount(c.getCount() + 1);
			}
		});

		Database.voters.forEach(v -> {
			if (v.getPublicKey().equals(publicKey)) {
				v.getBallot().setBallotCast(true);
				v.getBallot().setCandidatSelected(candidatId);
			}
		});

		return "Votre vote a été comptété avec succès.";

	}

	@Override
	public ArrayList<Candidat> getCandidats() throws RemoteException {
		// TODO Auto-generated method stub
		return Database.candidats;
	}

	@Override
	public String register(String fullName, PublicKey publicKey) throws RemoteException {
		ArrayList<Voter> voters = Database.voters;
		for (int i = 0; i < voters.size(); i++) {
			if (voters.get(i).getPublicKey() == publicKey) {
				return "Vous etes déja inscrit !";
			}
		}

		Database.voters.add(new Voter(fullName, publicKey));

		return "Vous avez bien été enregistré";

	}

	private boolean voted(PublicKey publicKey) {
		ArrayList<Voter> voters = Database.voters;
		for (int i = 0; i < voters.size(); i++) {
			if (voters.get(i).getPublicKey().equals(publicKey)) {
				return voters.get(i).getBallot().isBallotCast();
			}
		}

		return false;
	}

	private boolean registered(PublicKey publicKey) {
		ArrayList<Voter> voters = Database.voters;
		for (int i = 0; i < voters.size(); i++) {
			if (voters.get(i).getPublicKey().equals(publicKey)) {
				return true;
			}
		}

		return false;
	}

	private boolean candidatExist(long id) {
		ArrayList<Candidat> candidats = Database.candidats;
		for (int i = 0; i < candidats.size(); i++) {
			if (candidats.get(i).getId() == id) {
				return true;
			}
		}

		return false;

	}

	private String validatorVote(long candidatId, byte[] signature, PublicKey publicKey, String message) {
		try {
			boolean correct = DigitalSignature.verify(publicKey, message, signature);
			if (!correct) {
				return "signature est incorrect";
			}

			boolean registered = this.registered(publicKey);
			if (!registered) {
				return "Vous n'etes pas  inscrit !!";
			}

			boolean voted = this.voted(publicKey);

			if (voted) {
				return "Vous avez déja voté !!";
			}

			boolean exist = this.candidatExist(candidatId);

			if (!exist) {
				return "Le candidat que vous avez sélectionné n'existe pas !!";
			}

			Date aujourdhui = new Date();
			Election election = Database.election;

			if (aujourdhui.before(election.getStartDate())) {
				return "Le vote n'est pas encore ouvert!!";
			}

			if (aujourdhui.after(election.getEndDate())) {
				return "Le vote est terminé !!";
			}

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public String changeVote(long candidatId, byte[] signature, PublicKey publicKey, String message)
			throws RemoteException {

		String msg = this.validatorChangeVote(candidatId, signature, publicKey, message);

		if (msg != null) {
			return msg;
		}

		boolean voted = this.voted(publicKey);

		if (!voted) {
			return "Vous n'avez jamais voté !!";
		}

		return "";
	}

	private String validatorChangeVote(long candidatId, byte[] signature, PublicKey publicKey, String message) {
		try {
			boolean correct = DigitalSignature.verify(publicKey, message, signature);
			if (!correct) {
				return "signature est incorrect";
			}

			boolean registered = this.registered(publicKey);
			if (!registered) {
				return "Vous n'etes pas  inscrit !!";
			}

			boolean exist = this.candidatExist(candidatId);

			if (!exist) {
				return "Le candidat que vous avez sélectionné n'existe pas !!";
			}
			Date aujourdhui = new Date();
			Election election = Database.election;

			if (aujourdhui.before(election.getStartDate())) {
				return "Le vote n'est pas encore ouvert!!";
			}

			if (aujourdhui.after(election.getEndDate())) {
				return "Le vote est terminé !!";
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

		Database.candidats.forEach(c -> {
			if (c.getId() == candidatId) {
				c.setCount(c.getCount() + 1);
			}
		});

		Database.voters.forEach(v -> {
			if (v.getPublicKey().equals(publicKey)) {
				Database.candidats.forEach(c -> {
					if (c.getId() == v.getBallot().getCandidatSelected()) {
						c.setCount(c.getCount() - 1);
					}
				});
				v.getBallot().setCandidatSelected(candidatId);

			}
		});

		return "Votre vote a été change avec succès.";

	}

	@Override
	public ArrayList<Candidat> getResults() throws RemoteException {
		return Database.candidats;

	}
}
