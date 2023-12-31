package modelo;

/**
 * Implementación del algoritmo de cifrado por palabra inversa.
 *
 * @author Mary Paz.
 */
public class CifPalabraInversa extends AlgoritmoCifrado {

  private String ALGORITMO = "Cifrado por palabra inversa";

  /**
   * Invierte la palabra.
   *
   * @param palabraActual Palabra que se quiere invertir.
   * @return palabraNueva La palabra invertida.
   */
  private String invertirPalabra(String pPalabraActual) {
    int largoPalabra = pPalabraActual.length() - 1;
    String palabraNueva = "";
    for (int i = largoPalabra; i >= 0; i--) {
      char letra = pPalabraActual.charAt(i);
      palabraNueva += letra;
    }

    return palabraNueva;
  }

  /**
   * Cifra el mensaje.
   *
   * @param pMensaje El mensaje a crifrar.
   * @return El mensaje cifrado.
   */
  @Override
  public String cifrarMensaje(String pMensaje) {
    String mensajeCifrado = "";
    int largoMensaje = pMensaje.length();
    String palabraTemporal = "";
    // Recorre el texto y crea el mensaje cifrado
    for (int i = 0; i < largoMensaje; i++) {
      char letraPalabra = pMensaje.charAt(i);
      if (letraPalabra == ' ' || letraPalabra == '\n') {
        mensajeCifrado += invertirPalabra(palabraTemporal) + letraPalabra;
        palabraTemporal = "";
      } else if (i == (largoMensaje - 1)) {
        palabraTemporal += letraPalabra;
        mensajeCifrado += invertirPalabra(palabraTemporal);
        palabraTemporal = "";
      } else {
        palabraTemporal += letraPalabra;
      }

    }

    return mensajeCifrado;
  }

  /**
   * Descifrar el mensaje.
   *
   * @param pMensaje El mensaje a descifrar.
   * @return El mensaje descifrado.
   */
  @Override
  public String descifrarMensaje(String pMensaje) {
    String mensajeDescifrado = "";
    int largoMensaje = pMensaje.length();
    String palabraTemporal = "";
    // Recorre el texto y crea el mensaje decifrado
    for (int i = 0; i < largoMensaje; i++) {
      char letraPalabra = pMensaje.charAt(i);
      if (letraPalabra == ' ' || letraPalabra == '\n') {
        mensajeDescifrado += invertirPalabra(palabraTemporal) + letraPalabra;
        palabraTemporal = "";
      } else if (i == (largoMensaje - 1)) {
        palabraTemporal += letraPalabra;
        mensajeDescifrado += invertirPalabra(palabraTemporal);
        palabraTemporal = "";
      } else {
        palabraTemporal += letraPalabra;
      }

    }

    return mensajeDescifrado;
  }
}
