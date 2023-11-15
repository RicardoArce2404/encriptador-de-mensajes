package modelo;

/**
 * Representación abstracta de un algoritmo de cifrado en general.
 * 
 * @author Ricardo.
 */
public abstract class AlgoritmoCifrado {
  public abstract String cifrarMensaje(String pTexto);
  public abstract String descifrarMensaje(String pCodigo);
}
