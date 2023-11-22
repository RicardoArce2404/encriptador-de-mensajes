package modelo;

/**
 * Implementacion del algoritmo de cifrado basado en el sistema binario.
 * 
 * @author HeldyisAE
 */
public class CifCodificacionBinaria extends AlgoritmoCifrado {
  
  /**
   * Cifra un mensaje basado en el sistema binario utilizando solamente 5 bits.
   *
   * @param pMensaje Mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  
  @Override
  public String cifrarMensaje(String pMensaje) {
    StringBuilder mensajeCifrado = new StringBuilder();

    String[] lineas = pMensaje.split("\n");
    for (String linea : lineas) {
      for (char letra : linea.toCharArray()) {
        if (Character.isLetter(letra)) {
          char base = Character.isUpperCase(letra) ? 'A' : 'a';
          int valorBinario = letra - base;

          // Ajusta a 5 bits
          String binario = String.format("%05d", Integer.parseInt(Integer.toBinaryString(valorBinario)));

          mensajeCifrado.append(binario).append(" "); // Agrega un espacio después de cada letra
        } else if (letra == ' ') {
          mensajeCifrado.append("* "); // Representa espacios con "*"
        } // No incluir signos de puntuación en el cifrado
      }
      mensajeCifrado.append("\n"); // Agrega un salto de línea después de cada línea
    }

    return mensajeCifrado.toString().trim(); // Elimina el espacio en blanco adicional al final
  }
  
  /**
   * Descifra un mensaje usando los números en binario correspondientes para cada letra.
   *
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */ 
  
  @Override
  public String descifrarMensaje(String pMensaje) {
    StringBuilder mensajeDescifrado = new StringBuilder();

    String[] lineasCifradas = pMensaje.split("\n");
    for (String lineaCifrada : lineasCifradas) {
      String[] bloques = lineaCifrada.split(" ");
      for (String bloque : bloques) {
        if (bloque.equals("*")) {
          mensajeDescifrado.append(" "); // Representa "*" como espacio
        } else if (!bloque.isEmpty()) { // Evita procesar bloques vacíos
          try {
            int valorDecimal = Integer.parseInt(bloque, 2);
            char letra = (char) (valorDecimal + 'A');
            mensajeDescifrado.append(letra);
          } catch (NumberFormatException e) {
            // Manejar la excepción si el bloque no es una representación binaria válida
            mensajeDescifrado.append(bloque);
          }
        }
      }
      mensajeDescifrado.append("\n"); // Agrega un salto de línea después de cada línea descifrada
    }

    return mensajeDescifrado.toString().trim(); // Elimina el espacio en blanco adicional al final
  }

}
