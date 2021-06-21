package main;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;

import model.Candidat;
import model.Voter;
import service.VoteInterface;
import utils.DigitalSignature;

public class Client {
	public static void main(String args[]) {
		// Recherche de LOD
		String url = "rmi://localhost/vote";
		try {
			VoteInterface voteInterface = (VoteInterface) Naming.lookup(url);
			Voter voter = new Voter();
			Scanner sc = new Scanner(System.in);

			boolean on = true;
			while (on) {
				System.out.println("\n \n Choisissez un nombre:");
				System.out.println("1: pour générer une keyPair");
				System.out.println("2: inscription au élection");
				System.out.println("3: pour voter");
				System.out.println("4: changer votre vote");
				System.out.println("5: pour voir les résultats");
				System.out.println("6: pour quitter");

				int choix = sc.nextInt();

				switch (choix) {
				case 1: {
					voter.setKeyPair(DigitalSignature.generateKeyPair());
					voter.setFullName("El Ab");
					break;
				}
				case 2: {
					if (voter.getKeyPair() == null) {
						System.out.println("vous devez  générer une keyPair!!  \n \n \n");
						break;
					}
					String ms = voteInterface.register("El Ab", voter.getKeyPair().getPublic());
					System.out.println(ms);
					break;
				}
				case 3: {

					if (voter.getKeyPair() == null) {
						System.out.println("vous devez  générer une keyPair!!  \n \n \n");
						break;
					}
					ArrayList<Candidat> candidats = voteInterface.getCandidats();
					System.out.println("Choisissez le candidat que vous voulez !  \n \n \n");

					for (int i = 0; i < candidats.size(); i++) {
						System.out.println("candidate id :" + candidats.get(i).getId() + "  full name: "
								+ candidats.get(i).getFullName());
					}
					Scanner s = new Scanner(System.in);
					long candidateId = s.nextLong();

					String msg = voter.getFullName() + String.valueOf(candidateId);

					byte[] signMessage = DigitalSignature.signMessage(voter.getKeyPair().getPrivate(), msg);

					String result = voteInterface.vote(candidateId, signMessage, voter.getKeyPair().getPublic(), msg);
					System.out.println(result);
					break;
				}
				case 4: {
					if (voter.getKeyPair() == null) {
						System.out.println("vous devez  générer une keyPair!!  \n \n \n");
						break;
					}
					ArrayList<Candidat> candidats = voteInterface.getCandidats();
					System.out.println("Choisissez le candidat que vous voulez !  \n \n \n");

					for (int i = 0; i < candidats.size(); i++) {
						System.out.println("candidate id :" + candidats.get(i).getId() + "  full name: "
								+ candidats.get(i).getFullName());
					}
					Scanner s = new Scanner(System.in);
					long candidateId = s.nextLong();

					String msg = voter.getFullName() + String.valueOf(candidateId);

					byte[] signMessage = DigitalSignature.signMessage(voter.getKeyPair().getPrivate(), msg);

					String message = voteInterface.changeVote(candidateId, signMessage, voter.getKeyPair().getPublic(),
							msg);
					System.out.println(message);

				}
				case 5: {
					if (voter.getKeyPair() == null) {
						System.out.println("vous devez  générer une keyPair!!/n/n");
						break;
					}
					System.out.println("resultats !");
					ArrayList<Candidat> candidats = voteInterface.getResults();

					for (int i = 0; i < candidats.size(); i++) {
						System.out.println("candidate id :" + candidats.get(i).getId() + "  full name: "
								+ candidats.get(i).getFullName() + "count :" + candidats.get(i).getCount());
					}
					break;
				}
				case 6: {
					on = false;
					break;
				}

				default:
					throw new IllegalArgumentException("Unexpected value: " + choix);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
