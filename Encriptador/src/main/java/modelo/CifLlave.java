package modelo;

/**
 * Implementación del algoritmo de cifrado por llave.
 *
 * @author Ricardo.
 */
public class CifLlave extends AlgoritmoCifrado {

  /**
   * Cifra un mensaje usando una llave predeterminada.
   *
   * @param pMensaje Mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje) {
    String llave = "llave predeterminada";
    String mensaje = pMensaje.toLowerCase();
    StringBuilder mensajeCifrado = new StringBuilder();

    for (int i = 0; i < mensaje.length(); i++) {
      Character letraMensaje = pMensaje.charAt(i);
      int valorLetraMensaje = Character.getNumericValue(letraMensaje) - 9;

      Character letraLlave = llave.charAt(i % llave.length());
      int valorLetraLlave = Character.getNumericValue(letraLlave) - 9;

      int valorLetraCifrada = (valorLetraMensaje + valorLetraLlave) % 27;      
      mensajeCifrado.append((char) (valorLetraCifrada + 96));
    }
    
    return mensajeCifrado.toString();
  }

  /**
   * Cifra un mensaje usando la llave especificada.
   *
   * @param pMensaje Mensaje a cifrar.
   * @param pLlave Llave a usar para cifrar el mensaje.
   * @return Mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje, String pLlave) {
    String mensaje = pMensaje.toLowerCase();
    StringBuilder mensajeCifrado = new StringBuilder();

    for (int i = 0; i < mensaje.length(); i++) {
      Character letraMensaje = pMensaje.charAt(i);
      int valorLetraMensaje = Character.getNumericValue(letraMensaje) - 9;

      Character letraLlave = pLlave.charAt(i % pLlave.length());
      int valorLetraLlave = Character.getNumericValue(letraLlave) - 9;

      int valorLetraCifrada = (valorLetraMensaje + valorLetraLlave) % 27;      
      mensajeCifrado.append((char) (valorLetraCifrada + 96));
    }
    
    return mensajeCifrado.toString();
  }

  /**
   * Descifra un mensaje usando una llave predeterminada.
   *
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */  
  public String descifrarMensaje(String pMensaje) {
    String llave = "llave predeterminada";
    String mensaje = pMensaje.toLowerCase();
    StringBuilder mensajeDescifrado = new StringBuilder();

    for (int i = 0; i < mensaje.length(); i++) {
      Character letraMensaje = pMensaje.charAt(i);
      int valorLetraMensaje = Character.getNumericValue(letraMensaje) - 9;

      Character letraLlave = llave.charAt(i % llave.length());
      int valorLetraLlave = Character.getNumericValue(letraLlave) - 9;

      int valorLetraDescifrada = (valorLetraMensaje - valorLetraLlave) % 27;
      if (valorLetraDescifrada < 0) {
        valorLetraDescifrada += 5;
      }
      mensajeDescifrado.append((char) (valorLetraDescifrada + 96));
    }
    
    return mensajeDescifrado.toString();
  }

  /**
   * Descifra un mensaje usando la llave especificada.
   *
   * @param pMensaje Mensaje a descifrar.
   * @param pLlave Llave a usar para descifrar el mensaje.
   * @return Mensaje descifrado.
   */  
  public String descifrarMensaje(String pMensaje, String pLlave) {
    String mensaje = pMensaje.toLowerCase();
    StringBuilder mensajeDescifrado = new StringBuilder();

    for (int i = 0; i < mensaje.length(); i++) {
      Character letraMensaje = pMensaje.charAt(i);
      int valorLetraMensaje = Character.getNumericValue(letraMensaje) - 9;

      Character letraLlave = pLlave.charAt(i % pLlave.length());
      int valorLetraLlave = Character.getNumericValue(letraLlave) - 9;

      int valorLetraDescifrada = (valorLetraMensaje - valorLetraLlave) % 27;
      if (valorLetraDescifrada < 0) {
        valorLetraDescifrada += 5;
      }
      mensajeDescifrado.append((char) (valorLetraDescifrada + 96));
    }
    
    return mensajeDescifrado.toString();
  }

}