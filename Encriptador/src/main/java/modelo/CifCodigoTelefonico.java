package modelo;

/**
 * Implementación del algoritmo de cifrado por código telefónico.
 *
 * @author Mary Paz.
 */
public class CifCodigoTelefonico extends AlgoritmoCifrado {

  private String ALGORITMO = "Código telefónico";
  private static String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static String numerosTelefono = "123456789";

  /**
   * Consigue el valor númerico correspondiente a la letra.
   *
   * @param pLetraActual La letra del mensaje.
   * @return Valor númerico correspondiente tipo String.
   */
  private String getValorNumerico(char pLetraActual) {
    String pValorNumerico = "";
    int numero = 2;
    int posicion = 1;
    int largoAbecedario = abecedario.length();
    // Verifica si la pLetraActual a una letra del abecedario.
    // Asi define que numero y posicion le corresponden.
    for (int i = 0; i < largoAbecedario; i++) {
      char letraAbecedario = abecedario.charAt(i);
      if (pLetraActual == letraAbecedario) {
        pValorNumerico += Integer.toString(numero) + Integer.toString(posicion);
        break;
      }

      if ((numero == 7 || numero == 9) & posicion < 4) {
        posicion += 1;
      } else if (posicion == 4 || posicion == 3) {
        numero += 1;
        posicion = 1;
      } else {
        posicion += 1;
      }

    }

    return pValorNumerico;
  }

  /**
   * Cifrado el mensaje.
   *
   * @param pMensaje El mensaje a cifrar.
   * @return El mensaje cifrado.
   */
  @Override
  public String cifrarMensaje(String pMensaje) {
    String mensajeCifrado = "";
    // Pasa a mayuscula el texto
    String nuevoMensaje = pMensaje.toUpperCase();
    int largoMensaje = pMensaje.length();

    // Recorre el texto y crea el mensaje cifrado
    for (int i = 0; i < largoMensaje; i++) {
      char letraMensaje = nuevoMensaje.charAt(i);
      if (letraMensaje == ' ') {
        mensajeCifrado += '*';
      } else if (letraMensaje == '\n') {
        mensajeCifrado += '\n';
      } else {
        if (abecedario.contains(String.valueOf(letraMensaje))) {
          String pValorNumerico = getValorNumerico(letraMensaje);
          mensajeCifrado += pValorNumerico + ' ';
        }

      }

    }

    return mensajeCifrado;
  }

  /**
   * Consigue la nueva letra que corresponde según el número dado.
   *
   * @param pValorNumerico El valor numerico de la letra.
   * @return Letra que corresponde segun el valor númerico.
   */
  private char getValorLetra(String pValorNumerico) {
    int numero = 2;
    int posicion = 1;
    int largoAbecedario = abecedario.length();
    int numeroActual = pValorNumerico.charAt(0) - '0';
    int posicionActual = pValorNumerico.charAt(1) - '0';
    char valorLetra = ' ';
    // Verifica si el numero y la posicion son iguales
    // Asi define que numero y posicion le corresponden.
    for (int i = 0; i < largoAbecedario; i++) {
      valorLetra = abecedario.charAt(i);
      if ((numero == numeroActual) & (posicion == posicionActual)) {
        break;
      }

      if ((numero == 7 || numero == 9) & posicion < 4) {
        posicion += 1;
      } else if (posicion == 4 || posicion == 3) {
        numero += 1;
        posicion = 1;
      } else {
        posicion += 1;
      }

    }

    return valorLetra;
  }

  /**
   * Descifrado del mensaje.
   *
   * @param pMensaje El mensaje a descifrado.
   * @return El mensaje descifrado.
   */
  @Override
  public String descifrarMensaje(String pMensaje) {
    String mensajeDescifrado = "";
    int largoMensaje = pMensaje.length();
    String pValorNumerico = "";

    // Recorre el texto y crea el mensaje decifrado
    for (int i = 0; i < largoMensaje; i++) {
      char numMensaje = pMensaje.charAt(i);
      if (numMensaje == ' ' || i == (largoMensaje - 1)) {
        char valorLetra = getValorLetra(pValorNumerico);
        mensajeDescifrado += valorLetra;
        pValorNumerico = "";
      } else if (numMensaje == '*') {
        mensajeDescifrado += ' ';
      } else if (numMensaje == '\n') {
        mensajeDescifrado += '\n';
      } else {
        if (numerosTelefono.contains(String.valueOf(numMensaje))) {
          pValorNumerico += numMensaje;
        }

      }

    }

    mensajeDescifrado = mensajeDescifrado.substring(0, 1).toUpperCase()
            + mensajeDescifrado.substring(1).toLowerCase();
    return mensajeDescifrado;
  }
}
