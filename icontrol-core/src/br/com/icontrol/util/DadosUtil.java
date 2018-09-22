package br.com.icontrol.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Icontrol
 * 
 */

public class DadosUtil {
	
	public static void main(String[] args) {
		System.out.println("aline.barbosa;"+criptografarSenha("aline.barbosa", "promo147"));
		System.out.println("ROSANE.PINHEIRO;"+criptografarSenha("ROSANE.PINHEIRO", "promo147"));
		System.out.println("PATRICIA.PAIXAO;"+criptografarSenha("PATRICIA.PAIXAO", "promo147"));
		System.out.println("JESSIKA.COSTA;"+criptografarSenha("JESSIKA.COSTA", "promo147"));
		System.out.println("MERIANA.MENDES;"+criptografarSenha("MERIANA.MENDES", "promo147"));
		System.out.println("SUZENYR.PEREIRA;"+criptografarSenha("SUZENYR.PEREIRA", "promo147"));
		System.out.println("WILLIAM.SOARES;"+criptografarSenha("WILLIAM.SOARES", "promo147"));
		System.out.println("MAIRA.MARQUES;"+criptografarSenha("MAIRA.MARQUES", "promo147"));
		System.out.println("ANDREA.OLIVEIRA;"+criptografarSenha("ANDREA.OLIVEIRA", "promo147"));
		System.out.println("CHRISTIANE.LEITE;"+criptografarSenha("CHRISTIANE.LEITE", "promo147"));
		System.out.println("DANIELE.DINIZ;"+criptografarSenha("DANIELE.DINIZ", "promo147"));
		System.out.println("jose.godoi;"+criptografarSenha("jose.godoi", "temp"));
	}

	public static String criptografarSenha(String usuario, String senha) {
		usuario = usuario.toLowerCase();
		String assinatura = usuario + senha;
		return criptografar(assinatura);
	}
	
	public static boolean isEmpty(Long l) {
		return l == null || l.longValue() == 0;
	}
	
	public static boolean isEmpty(List<?> lista) {
		return lista == null || lista.isEmpty();
	}

	public static boolean isEmpty(Object[] v) {
		return v == null || v.length == 0;
	}
	
	public static boolean isEmpty(Object obj) {
		return obj == null;
	}
	
	public static boolean isEmpty(Double obj) {
		return obj == null || obj.doubleValue() == 0;
	}
	
	public static boolean isEmpty(Integer obj) {
		return obj == null || obj.intValue() == 0;
	}	
	
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty() || str.trim().equals("");
	}
	
	public static String criptografar(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new BigInteger(1, md.digest(str.getBytes())).toString(16);
		} catch (NoSuchAlgorithmException excecao) {
			return null;
		}
	}

	public static long arredondarDecimal(long numero) {
		int fator = 5;
		numero = numero + fator;
		long sobra = (numero % fator);
		long adicionar = fator - sobra;
		return numero + adicionar;
	}
}