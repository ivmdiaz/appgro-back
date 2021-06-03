package com.appgro.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

public class EncriptadorAES {
	
	private static final String clave = "appgr0Un1vers1d4dD1str1t4l";
	
	/**
	 * Crea la clave de encriptacion usada internamente
	 * 
	 * @param clave Clave que se usara para encriptar
	 * @return Clave de encriptacion
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] claveEncriptacion = clave.getBytes("UTF-8");

		MessageDigest sha = MessageDigest.getInstance("SHA-1");

		claveEncriptacion = sha.digest(claveEncriptacion);
		claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

		SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

		return secretKey;
	}

	/**
	 * Aplica la encriptacion AES a la cadena de texto usando la clave indicada
	 * 
	 * @param datos        Cadena a encriptar
	 * @param claveSecreta Clave para encriptar
	 * @return Información encriptada
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String encriptar(String datos) {

		try {
			SecretKeySpec secretKey = this.crearClave(clave);

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] datosEncriptar = datos.getBytes("UTF-8");
			byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
			String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

			return encriptado;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return datos;
	}

	/**
	 * Desencripta la cadena de texto indicada usando la clave de encriptacion
	 * 
	 * @param datosEncriptados Datos encriptados
	 * @param claveSecreta     Clave de encriptacion
	 * @return Informacion desencriptada
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String desencriptar(String datosEncriptados) {

		try {
			SecretKeySpec secretKey = this.crearClave(clave);

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
			byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
			String datos = new String(datosDesencriptados);

			return datos;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return datosEncriptados;
	}
}
