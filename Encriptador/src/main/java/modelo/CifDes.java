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

  @Override
  public String cifrarMensaje(String pTexto) {
    try {
      String keyString = "mi_clave";
      byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
      DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITMO);
      SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

      Cipher cipher = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      byte[] encryptedBytes = cipher.doFinal(pTexto.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String descifrarMensaje(String pCodigo) {
    try {
      String keyString = "mi_clave";
      byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
      DESKeySpec desKeySpec = new DESKeySpec(keyBytes);

      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITMO);
      SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

      Cipher cipher = Cipher.getInstance(ALGORITMO + "/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);

      byte[] decodedBytes = Base64.getDecoder().decode(pCodigo);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
