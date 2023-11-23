package modelo;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Implementación del algoritmo de cifrado AES.
 *
 * @author Mary Paz.
 */
public class CifAes extends AlgoritmoCifrado {

  private String mensajeCifrado;
  private String mensajeDescifrado;

  /**
   * Crea una clave secreta para aplicar el cifrado AES.
   *
   * @param llave Llave creada por el usuario.
   * @return Clave creada o null si ocurrio un error.
   */
  private SecretKeySpec crearSecretKey(String llave) {
    try {
      byte[] bytesLlave = llave.getBytes("UTF-8");
      // Uso del algoritmo SHA-1
      MessageDigest mHash = MessageDigest.getInstance("SHA-1");
      // Calculo del hash
      bytesLlave = mHash.digest(bytesLlave);
      // Recorte de 16 bytes y creacion de la seretKey
      bytesLlave = Arrays.copyOf(bytesLlave, 16);
      SecretKeySpec secretKey = new SecretKeySpec(bytesLlave, "AES");
      return secretKey;
    } catch (Exception e) {
      return null;
    }

  }

  /**
   * Cifra el mensaje por el cifrado AES.
   *
   * @param pMensaje Mensaje que se va a cifrar.
   * @return El mensaje cifrado.
   */
  @Override
  public String cifrarMensaje(String pMensaje) {
    mensajeCifrado = "";
    String llave = "LlavePredeterminada";
    try {
      SecretKeySpec secretKey = crearSecretKey(llave);
      // Configuración del cifrado por AES
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      // Se codifica el mensaje
      byte[] bytesMensaje = pMensaje.getBytes("UTF-8");
      byte[] bytesMensajeCodificado = cipher.doFinal(bytesMensaje);
      mensajeCifrado = Base64.getEncoder().encodeToString(bytesMensajeCodificado);
    } catch (Exception e) {
      // Si ocurre un error el mensaje va ser vacio
      mensajeCifrado = "";
    }

    return mensajeCifrado;
  }

  /**
   * Cifra el mensaje por el cifrado AES.
   *
   * @param pMensaje Mensaje que se va a cifrar.
   * @param pLlave Llave para cifrar el mensaje.
   * @return El mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje, String pLlave) {
    mensajeCifrado = "";
    try {
      SecretKeySpec secretKey = crearSecretKey(pLlave);
      // Configuración del cifrado por AES
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      // Se codifica el mensaje
      byte[] bytesMensaje = pMensaje.getBytes("UTF-8");
      byte[] bytesMensajeCodificado = cipher.doFinal(bytesMensaje);
      mensajeCifrado = Base64.getEncoder().encodeToString(bytesMensajeCodificado);
    } catch (Exception e) {
      // Si ocurre un error el mensaje va ser vacio
      mensajeCifrado = "";
    }

    return mensajeCifrado;
  }

  /**
   * Descifrar el mensaje por el cifrado AES.
   *
   * @param pMensaje Mensaje que se va a descifrar.
   * @return El mensaje descifrado.
   */
  @Override
  public String descifrarMensaje(String pMensaje) {
    mensajeDescifrado = "";
    String llave = "LlavePredeterminada";
    try {
      SecretKeySpec secretKey = crearSecretKey(llave);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      byte[] bytesMensaje = Base64.getDecoder().decode(pMensaje);
      byte[] bytesMensajeDescifrado = cipher.doFinal(bytesMensaje);
      mensajeDescifrado = new String(bytesMensajeDescifrado);
    } catch (Exception e) {
      return null;
    }

    return mensajeDescifrado;
  }

  /**
   * Descifrar el mensaje por el cifrado AES.
   *
   * @param pMensaje Mensaje que se va a descifrado.
   * @param pLlave Llave correspondiente para descifrado del mensaje.
   * @return El mensaje descifrado.
   */
  public String descifrarMensaje(String pMensaje, String pLlave) {
    mensajeDescifrado = "";
    try {
      SecretKeySpec secretKey = crearSecretKey(pLlave);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      byte[] bytesMensaje = Base64.getDecoder().decode(pMensaje);
      byte[] bytesMensajeDescifrado = cipher.doFinal(bytesMensaje);
      mensajeDescifrado = new String(bytesMensajeDescifrado);
    } catch (Exception e) {
      return null;
    }

    return mensajeDescifrado;
  }

  // Métodos getter
  // mensajeCifrado
  /**
   * Retorna el valor de mensajeCifrado.
   *
   * @return El mensaje cifrado.
   */
  public String getMensajeCifrado() {
    return mensajeCifrado;
  }

  // mensajeDsecifrado
  /**
   * Retorna el valor de mensajeDescifrado.
   *
   * @return El mensaje descifrado.
   */
  public String getMensajeDescifrado() {
    return mensajeDescifrado;
  }

}
