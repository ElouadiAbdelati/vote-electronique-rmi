package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import database.Database;
import model.Candidat;
import model.Election;
import serviceImpl.VoteImpl;

public class AppServer {
	public static void main(String args[]) {
		try {

			init();

			VoteImpl VoteImpl = new VoteImpl();
			LocateRegistry.createRegistry(1099);

			// Enregistrement de l'OD dans RMI
			Naming.rebind("vote", VoteImpl);

			System.out.println("L'Objet distant (OD) est enregistré dans RMI.. Serveur Pret");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init() {
		try {
			Candidat candidat1 = new Candidat();
			candidat1.setFullName("EL OUADI ABDELATI");
			candidat1.setId(1);

			Candidat candidat2 = new Candidat();
			candidat2.setFullName("EL hamri you");
			candidat2.setId(2);

			Candidat candidat3 = new Candidat();
			candidat3.setFullName("EL  jawad");
			candidat3.setId(3);

			Database.candidats.add(candidat1);
			Database.candidats.add(candidat2);
			Database.candidats.add(candidat3);

			Election election = new Election();
			Scanner sc = new Scanner(System.in);
			System.out.println("Entrez le nom et la date de debut et de fin de l'election (dd-M-yyyy)");
			String name = sc.next();
			String startDate = sc.next();
			String endDate = sc.next();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
			election.setStartDate(formatter.parse(startDate));
			election.setEndDate(formatter.parse(endDate));
			election.setName(name);
			Database.election = election;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
