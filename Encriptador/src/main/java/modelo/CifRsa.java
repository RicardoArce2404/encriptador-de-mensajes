package modelo;

import java.math.BigInteger;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implementación del algoritmo de cifrado RSA.
 *
 * @author Ricardo.
 */
public class CifRsa extends AlgoritmoCifrado {

  // Valor a usar como límite superior para el generador de número aleatorios.
  private final int limiteGenerador = (int) Math.pow(2, 8);

  /**
   * Valida si el número especificado es un número primo.
   *
   * @param pNumero Número a validar.
   * @return true si el número especificado es primo, sino false.
   */
  public boolean esPrimo(int pNumero) {
    int mitadNumero = (int) (pNumero / 2) + 1;
    for (int i = 2; i <= mitadNumero; i++) {
      if (pNumero % i == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Genera un número primo aleatorio entre 1 y el valor dado por
   * limiteGenerador.
   *
   * @return Un número primo aleatorio.
   */
  public int generarPrimoAleatorio() {
    Random generador = new Random();
    int numeroAleatorio;
    do {
      numeroAleatorio = generador.nextInt(1, limiteGenerador);
    } while (!esPrimo(numeroAleatorio));
    return numeroAleatorio;
  }

  /**
   * Obtiene los divisores del número especificado.
   *
   * @param pNumero Número del cuál se van a obtener sus divisores.
   * @return Divisores del número especificado.
   */
  public List<Integer> divisores(int pNumero) {
    List<Integer> divisores = new ArrayList<Integer>();
    int mitadNumero = (int) pNumero / 2 + 1;

    for (int i = 1; i <= mitadNumero; i++) {
      if (pNumero % i == 0) {
        divisores.add((Integer) i);
      }
    }

    return divisores;
  }

  /**
   * Obtiene el máximo común divisor de los números especificados.
   *
   * @param pNumero1 Primer número a usar para el cálculo.
   * @param pNumero2 Segundo número a usar para el cálculo.
   * @return El máximo común divisor entre los 2 números especificados.
   */
  public int maximoComunDivisor(int pNumero1, int pNumero2) {
    List<Integer> divisoresNumero1 = divisores(pNumero1);
    List<Integer> divisoresNumero2 = divisores(pNumero2);
    List<Integer> divisoresComunes = new ArrayList();

    for (Integer divisorNumero1 : divisoresNumero1) {
      if (divisoresNumero2.contains(divisorNumero1)) {
        divisoresComunes.add(divisorNumero1);
      }
    }

    return Collections.max(divisoresComunes);
  }

  /**
   * Genera un módulo, un exponente público y un exponente privado para ser
   * usados por el algoritmo de cifrado.
   *
   * @return Una lista con el módulo, exponente público y exponente privado.
   */
  public List<Integer> generarParametros() {
    // Se escogen los números privados p y q.
    int p = generarPrimoAleatorio();
    int q;
    do {  // En caso de que p == q, se genera un nuevo valor para q.
      q = generarPrimoAleatorio();
    } while (p == q);

    int n = p * q;  // Módulo a usar para las claves.
    int phiDeN = (p - 1) * (q - 1);  // Resultado de φ(n).

    int e = 0;  // Exponente de la clave pública.
    for (int i = phiDeN - 1; i > 1; i--) {
      if (maximoComunDivisor(phiDeN, i) == 1) {
        e = i;
        break;
      }
    }

    int d = 0; // Exponenete de la clave privada.
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      if ((i * e - 1) % phiDeN == 0) {
        d = i;
        break;
      }
    }

    if (d == 0 || e == 0) {
      System.out.println("ocurrió un error con el cálculo de d o e");
      return null;
    }

    List<Integer> parametros = new ArrayList();
    parametros.add(n);  // Se guarda el módulo.
    parametros.add(e);  // Se guarda el exponente público.
    parametros.add(d);  // Se guarda el exponente privado.

    return parametros;
  }

  /**
   * Cifra un mensaje usando una llave pública predeterminada.
   *
   * @param pMensaje Mensaje a cifrar.
   * @return Mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje) {
    return cifrarMensaje(pMensaje, 3233, 17);
  }

  /**
   * Cifra un mensaje usando los datos de la llave pública especificados.
   *
   * @param pMensaje Mensaje a cifrar.
   * @param pModulo Módulo a usar para el cifrado.
   * @param pExponentePublico Exponente a usar para el algoritmo.
   * @return Mensaje cifrado.
   */
  public String cifrarMensaje(String pMensaje, int pModulo, int pExponentePublico) {
    StringBuilder mensajeCifrado = new StringBuilder();

    for (char letra : pMensaje.toCharArray()) {
      BigInteger valorLetra = BigInteger.valueOf((int) letra);
      BigInteger valorCifrado = valorLetra.pow(pExponentePublico);
      valorCifrado = valorCifrado.mod(BigInteger.valueOf(pModulo));
      mensajeCifrado.append(valorCifrado);
      mensajeCifrado.append('*');
    }

    mensajeCifrado.deleteCharAt(mensajeCifrado.length() - 1);
    return mensajeCifrado.toString();
  }

  /**
   * Descifra un mensaje usando una llave privada predeterminada.
   *
   * @param pMensaje Mensaje a descifrar.
   * @return Mensaje descifrado.
   */
  public String descifrarMensaje(String pMensaje) {
    return descifrarMensaje(pMensaje, 3233, 2753);
  }

  /**
   * Descifra un mensaje usando los datos de la llave privada especificados.
   *
   * @param pMensaje Mensaje a descifrar.
   * @param pModulo Módulo a usar para el descifrado.
   * @param pExponentePrivado Exponente a usar para el algoritmo.
   * @return Mensaje descifrado.
   */
  public String descifrarMensaje(String pMensaje, int pModulo, int pExponentePrivado) {

    List<BigInteger> valoresCaracteresCifrados = new ArrayList();
    for (String valorCaracterCifrado : pMensaje.split("\\*")) {
      // Se pasa el valor de String a int y luego de int a BigInteger.
      valoresCaracteresCifrados.add(BigInteger.valueOf(Integer.parseInt(valorCaracterCifrado)));
    }

    StringBuilder mensajeDescifrado = new StringBuilder();

    for (BigInteger valorCaracterCifrado : valoresCaracteresCifrados) {
      BigInteger valorCaracterDescifrado = valorCaracterCifrado.pow(pExponentePrivado);
      valorCaracterDescifrado = valorCaracterDescifrado.mod(BigInteger.valueOf(pModulo));
      mensajeDescifrado.append((char) valorCaracterDescifrado.intValue());
    }

    return mensajeDescifrado.toString();
  }

}
