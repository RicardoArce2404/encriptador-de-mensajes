package modelo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Implementación del algoritmo de cifrado DES.
 *
 * @autor HeldyisAE
 */
public class CifDes extends AlgoritmoCifrado {

  private String ALGORITMO = "DES";

  /**
   * Cifra un mensaje.
   * 
   * @param pMensaje Mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  @Override
  public String cifrarMensaje(String pMensaje) {
    try {
      String keyString = "mi_clave";
      byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
      DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITMO);
      SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

      Cipher cipher = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      byte[] encryptedBytes = cipher.doFinal(pMensaje.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Descifra un mensaje.
   * 
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */
  @Override
  public String descifrarMensaje(String pMensaje) {
    try {
      String keyString = "mi_clave";
      byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
      DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITMO);
      SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

      Cipher cipher = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);

      byte[] decodedBytes = Base64.getDecoder().decode(pMensaje);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
