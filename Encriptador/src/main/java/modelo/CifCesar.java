package modelo;

/**
 * Implementación del algoritmo de cifrado César.
 *
 * @author Mary Paz.
 */
public class CifCesar extends AlgoritmoCifrado {

  private String mensajeCifrado;
  private String mensajeDescifrado;
  private static String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  /**
   * Consigue la nueva letra que corresponde según el cifrado de cifrar.
   *
   * @param posicionLetraActual Posicion de la letra actual según el abecedario.
   * @return Letra que corresponde según cifrado.
   */
  private char getLetraNuevaCodificado(int posicionLetraActual) {
    int largoAbecedario = abecedario.length();
    int sumaPosicion = posicionLetraActual + 3;
    int posicionLetraNueva;
    if (sumaPosicion > (largoAbecedario - 1)) {
      posicionLetraNueva = sumaPosicion - largoAbecedario;
    } else {
      posicionLetraNueva = sumaPosicion;
    }
    return abecedario.charAt(posicionLetraNueva);
  }

  /**
   * Cifra el mensaje.
   *
   * @param pMensaje El mensaje a cifrar.
   * @return El mensaje descifrado.
   */
  @Override
  public String cifrarMensaje(String pMensaje) {
    // Pasa a mayuscula el texto
    mensajeCifrado = "";
    String nuevoMensaje = pMensaje.toUpperCase();
    int largoMensaje = nuevoMensaje.length();

    // Recorre el texto y crea el mensaje cifrado
    for (int i = 0; i < largoMensaje; i++) {
      char letraMensaje = nuevoMensaje.charAt(i);
      if (letraMensaje == ' ') {
        mensajeCifrado += letraMensaje;
      } else if (letraMensaje == '\n') {
        mensajeCifrado += letraMensaje;
      } else {
        if (abecedario.contains(String.valueOf(letraMensaje))) {
          char letraNueva = getLetraNuevaCodificado(abecedario.indexOf(letraMensaje));
          mensajeCifrado += letraNueva;
        }
      }
    }

    return mensajeCifrado;
  }

  /**
   * Consigue la nueva letra que corresponde según el cifrado de decifrado.
   *
   * @param posicionLetraActual Posicion de la letra actual según el abecedario.
   * @return Letra que corresponde segun el cifrado.
   */
  private char getLetraNuevaDecifrado(int posicionLetraActual) {
    int largoAbecedario = abecedario.length();
    int restaPosicion = posicionLetraActual - 3;
    int posicionLetraNueva;
    if (restaPosicion < 0) {
      posicionLetraNueva = restaPosicion + largoAbecedario;
    } else {
      posicionLetraNueva = restaPosicion;
    }
    return abecedario.charAt(posicionLetraNueva);
  }

  /**
   * Descifra el mensaje.
   *
   * @param pMensaje El mensaje a Descifrado.
   * @return El mensaje descifrado.
   */
  @Override
  public String descifrarMensaje(String pMensaje) {
    mensajeDescifrado = "";
    // Pasa a mayuscula el texto
    String nuevoMensaje = pMensaje.toUpperCase();
    int largoMensaje = nuevoMensaje.length();

    // Recorre el texto y crea el mensaje decifrado
    for (int i = 0; i < largoMensaje; i++) {
      char letraMensaje = nuevoMensaje.charAt(i);
      if (letraMensaje == ' ') {
        mensajeDescifrado += letraMensaje;
      } else if (letraMensaje == '\n') {
        mensajeDescifrado += letraMensaje;
      } else {
        if (abecedario.contains(String.valueOf(letraMensaje))) {
          char letraNueva = getLetraNuevaDecifrado(abecedario.indexOf(letraMensaje));
          mensajeDescifrado += letraNueva;
        }
      }
    }

    mensajeDescifrado = mensajeDescifrado.substring(0, 1).toUpperCase()
            + mensajeDescifrado.substring(1).toLowerCase();
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

  // mensajeDescifrado
  /**
   * Retorna el valor de mensajeDescifrado.
   *
   * @return El mensaje descifrado.
   */
  public String getMensajeDescifrado() {
    return mensajeDescifrado;
  }

}
