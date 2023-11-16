package aplicacion;

import vista.FormVentanaPrincipal;
import controlador.ControladorEncriptador;

/**
 * @author Ricardo.
 */
public class AplEncriptador {
  public static void main(String[] args) {
    FormVentanaPrincipal vista = new FormVentanaPrincipal();
    ControladorEncriptador controlador = new ControladorEncriptador(vista);
    controlador.vista.setVisible(true);
  }
}
