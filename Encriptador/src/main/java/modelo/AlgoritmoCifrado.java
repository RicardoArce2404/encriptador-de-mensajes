package modelo;

/**
 * Representación abstracta de un algoritmo de cifrado general.
 *
 * @author Ricardo.
 */
public abstract class AlgoritmoCifrado {
  
  private String ALGORITMO;

  public abstract String cifrarMensaje(String pMensaje);

  public abstract String descifrarMensaje(String pMensaje);

  public String toString() {
    return "Nombre del algoritmo de cifrado/descifrado: " + ALGORITMO + ".";
  }
}
