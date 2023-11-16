package controlador;

import vista.FormVentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import modelo.*;

/**
 * Módulo de comunicación entre el modelo y la interfaz.
 *
 * @author Ricardo.
 */
public class ControladorEncriptador implements ActionListener {
  
  public FormVentanaPrincipal vista;
  
  public ControladorEncriptador(FormVentanaPrincipal pVista) {
    vista = pVista;
    vista.cbAlgoritmo.addActionListener((ActionListener) this);
    vista.cbOperacion.addActionListener((ActionListener) this);
    vista.btCargarArchivo.addActionListener((ActionListener) this);
    vista.btCifrar.addActionListener((ActionListener) this);
    vista.btEnviarCorreo.addActionListener((ActionListener) this);
    vista.btSalir.addActionListener((ActionListener) this);
  }
  
  /**
   * Maneja los eventos generados en la interfaz gráfica.
   *
   * @param pEvento Evento a ser manejado.
   */
  public void actionPerformed(ActionEvent pEvento) {
    switch(pEvento.getActionCommand()) {
      case "comboBoxChanged" -> cambiarOperacion();
      case "Cargar archivo de texto" -> cargarArchivoTexto();
      case "Cifrar texto" -> cifrar();
      case "Descifrar texto" -> descifrar();
      case "Enviar por correo" -> enviarCorreo();
      case "Salir" -> salir();
      default -> {
        System.out.println(pEvento.getActionCommand());
        return;
      }
    }
  }
  
  public void cambiarOperacion() {
    String operacionActual = (String) vista.cbOperacion.getSelectedItem();
    vista.btCifrar.setText(operacionActual + " texto");
  }
  
  /**
   * Carga un archivo de texto e introduce su contenido en el cuadro de entrada de la interfaz.
   */
  public void cargarArchivoTexto() {
    JFileChooser electorArchivo = new JFileChooser();
    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo de texto plano", "txt");
    electorArchivo.setFileFilter(filtro);
    int opcionElegida = electorArchivo.showOpenDialog(vista);

    if(opcionElegida == JFileChooser.APPROVE_OPTION) {
      FileReader lectorArchivo;
      try {
        lectorArchivo = new FileReader(electorArchivo.getSelectedFile());
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(vista, "No se encontró el archivo seleccionado",
                                      "Archivo no encontrado", JOptionPane.ERROR_MESSAGE);
      }
      // Lógica de lectura de archivo aquí.
    }
  }
  
  /**
   * Verifica cuál es el algoritmo de cifrado elegido y en base a eso cifra
   * el mensaje introducido por el usuario y lo muestra en la interfaz.
   */
  public void cifrar() {
    String algoritmoElegido = (String) vista.cbAlgoritmo.getSelectedItem();
    String mensaje = vista.txtEntrada.getText();
    String mensajeCifrado;


    switch (algoritmoElegido) {

//      case "César":

      case "Llave":
        CifLlave cifLlave = new CifLlave();
        String llave = JOptionPane.showInputDialog(vista,
                                                   "Ingrese la llave a usar para cifrar el mensaje.");
        mensajeCifrado = cifLlave.cifrarMensaje(mensaje, llave);
        break;

//      case "Vigenére":
//      case "Palabra inversa":

      case "Mensaje inverso":
        CifMensajeInverso cifMensajeInverso = new CifMensajeInverso();
        mensajeCifrado = cifMensajeInverso.cifrarMensaje(mensaje);
        break;

//      case "Código telefónico":
//      case "Codificación binaria":

      case "RSA":
        CifRsa cifRsa = new CifRsa();
        mensajeCifrado = cifRsa.cifrarMensaje(mensaje);
        break;

//      case "DES":
//      case "AES":

      default:
        JOptionPane.showMessageDialog(vista, "El algoritmo de cifrado elegido es inválido.");
        return;
    }

    vista.txtSalida.setText(mensajeCifrado);
  }
  
  /**
   * Verifica cuál es el algoritmo de cifrado elegido y en base a eso descifra
   * el mensaje introducido por el usuario y lo muestra en la interfaz.
   */
  public void descifrar() {
    String algoritmoElegido = (String) vista.cbAlgoritmo.getSelectedItem();
    String mensaje = vista.txtEntrada.getText();
    String mensajeDescifrado;

    switch (algoritmoElegido) {

//      case "César":

      case "Llave":
        CifLlave cifLlave = new CifLlave();
        String llave = JOptionPane.showInputDialog(vista,
                                                   "Ingrese la llave a usar para descifrar el mensaje.");
        mensajeDescifrado = cifLlave.descifrarMensaje(mensaje, llave);
        break;

//      case "Vigenére":
//      case "Palabra inversa":

      case "Mensaje inverso":
        CifMensajeInverso cifMensajeInverso = new CifMensajeInverso();
        mensajeDescifrado = cifMensajeInverso.descifrarMensaje(mensaje);
        break;

//      case "Código telefónico":
//      case "Codificación binaria":

      case "RSA":
        CifRsa cifRsa = new CifRsa();
        mensajeDescifrado = cifRsa.descifrarMensaje(mensaje);
        break;

//      case "DES":
//      case "AES":

      default:
        JOptionPane.showMessageDialog(vista, "El algoritmo de cifrado elegido es inválido.");
        return;
    }
    
    vista.txtSalida.setText(mensajeDescifrado);
  }

  /**
   * Verifica si una dirección de correo electrónico es válida y existente.
   * @param pCorreo Dirección de correo electrónico a verificar.
   * @return true si se cumple la condición, sino false.
   */
  public boolean correoValido(String pCorreo) {
    // Lógica de validación de correo aquí.
    return true;
  }
  
  /**
   * Envía el texto de salida a la dirección de correo deseada, siempre y
   * cuando no sea un texto vacío y la dirección de correo ingresada exista.
   */
  public void enviarCorreo() {
    String msg = "Porfavor escriba la dirección de correo a la cual desea enviar el texto.";
    String correo = JOptionPane.showInputDialog(vista, msg);
    if (correoValido(correo)) {
      // Lógica de envío de correo aquí.
    }            
  }

  /**
   * Termina la ejecución del programa.
   */
  public void salir() {
    System.exit(0);
  }
}