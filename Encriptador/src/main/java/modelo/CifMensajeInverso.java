package modelo;

/**
 * Implementación del algoritmo de cifrado por mensaje inverso.
 *
 * @author Ricardo.
 */
public class CifMensajeInverso extends AlgoritmoCifrado {

  /**
   * Cifra un mensaje.
   *
   * @param pMensaje Mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje) {
    return new StringBuilder(pMensaje).reverse().toString();
  }

  /**
   * Descifra un mensaje.
   *
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */
  public String descifrarMensaje(String pMensaje) {
    return new StringBuilder(pMensaje).reverse().toString();
  }

}
