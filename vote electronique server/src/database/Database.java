package database;

import java.util.ArrayList;

import model.Candidat;
import model.Election;
import model.Voter;

public class Database {
	public static ArrayList<Voter> voters = new ArrayList<Voter>();

	public static ArrayList<Candidat> candidats = new ArrayList<Candidat>();

	public static Election election = new Election();
}
