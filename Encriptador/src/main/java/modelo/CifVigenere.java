package modelo;

/**
 * Implementación del algoritmo sustitución de Vigenére.
 * 
 * @author HeldyisAE.
 */
public class CifVigenere extends AlgoritmoCifrado {
  
  private final String clave = "clave";
  
  /**
   * Cifra un mensaje usando el alfabeto, la primera letra avanza 2 letras y la segunda 3 letras.
   * Se repite en bucle hasta que se acabe el mensaje.
   *
   * @param pMensaje mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  
  @Override
  public String cifrarMensaje(String pMensaje) {
    StringBuilder mensajeCifrado = new StringBuilder();
    int claveIndex = 0;

    for (String palabra : pMensaje.split("\\s+")) { // Divide el mensaje en palabras
      for (char letra : palabra.toCharArray()) {
        if (Character.isLetter(letra)) {
          char base = Character.isUpperCase(letra) ? 'A' : 'a';
          int desplazamiento = (claveIndex % 2 == 0) ? 2 : 3;
          char cifrada = (char) ((letra - base + desplazamiento) % 26 + base);
          mensajeCifrado.append(cifrada);
          claveIndex = (claveIndex + 1) % palabra.length();
        } else {
          mensajeCifrado.append(letra);
        }
      }
      mensajeCifrado.append(" "); // Agrega un espacio entre palabras
    }
    return mensajeCifrado.toString().trim(); // Elimina el espacio adicional al final
  }
  
  /**
   * Descifra un mensaje usando una las leyes de la sustitución Vigenere a la inversa.
   *
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */ 
  
  @Override
  public String descifrarMensaje(String pMensaje) {
    StringBuilder mensajeDescifrado = new StringBuilder();
    int claveIndex = 0;

    for (String palabra : pMensaje.split("\\s+")) { // Divide el mensaje en palabras
      for (char letra : palabra.toCharArray()) {
        if (Character.isLetter(letra)) {
          char base = Character.isUpperCase(letra) ? 'A' : 'a';
          int desplazamiento = (claveIndex % 2 == 0) ? 2 : 3;
          char descifrada = (char) ((letra - base - desplazamiento + 26) % 26 + base);
          mensajeDescifrado.append(descifrada);
          claveIndex = (claveIndex + 1) % palabra.length();
        } else {
          mensajeDescifrado.append(letra);
        }
      }
      mensajeDescifrado.append(" "); // Agrega un espacio entre palabras
    }

    return mensajeDescifrado.toString().trim(); // Elimina el espacio adicional al final
  }
}
