package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.ArrayList;

import model.Candidat;

public interface VoteInterface extends Remote {

	public String vote(long candidatId, byte[] signature, PublicKey publicKey, String message) throws RemoteException;

	public String changeVote(long candidatId, byte[] signature, PublicKey publicKey, String message)
			throws RemoteException;

	public ArrayList<Candidat> getCandidats() throws RemoteException;

	public ArrayList<Candidat> getResults() throws RemoteException;

	public String register(String fullName, PublicKey publicKey) throws RemoteException;

}
