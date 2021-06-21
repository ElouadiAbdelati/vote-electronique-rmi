package utils;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class DigitalSignature {

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {

		/**
		 * the first step is to get a key-pair generator object for generating keys for
		 * the DSA signature algorithm. DSA : Digital Signature Algorithm Cryptographic
		 * implementations in the JDK are distributed through several different
		 * providers ("SUN",,)
		 */
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");

		/**
		 * The next step is to initialize the key-pair generator. SHA1PRNG
		 * pseudo-random-number generation algorithm
		 */
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(1024, random);

		/**
		 * The final step is to generate the key pair and to store the keys in
		 * PrivateKey and PublicKey objects.
		 */
		KeyPair pair = keyGen.generateKeyPair();
		return pair;
	}

	public static byte[] signMessage(PrivateKey sk, String message)
			throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {

		/**
		 * Initialize the Signature Object for Verification The algorithm used by the
		 * GenSig program was the SHA1withDSA algorithm from the SUN provider.
		 */
		Signature sig = Signature.getInstance("SHA1withDSA", "SUN");

		sig.initSign(sk);
		sig.update(message.getBytes());
		return sig.sign();
	}

	public static boolean verify(PublicKey pk, String message, byte[] signatrue)
			throws NoSuchAlgorithmException, NoSuchProviderException, SignatureException, InvalidKeyException {

		/**
		 * Verify the Signature
		 * 
		 */
		Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
		sig.initVerify(pk);
		sig.update(message.getBytes());
		return sig.verify(signatrue);
	}
}
