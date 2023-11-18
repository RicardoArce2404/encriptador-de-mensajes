package controlador;

import vista.FormVentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

/**
 * Módulo de comunicación entre el modelo y la interfaz del encriptador.
 * Aquí se inicia la ejecución de la aplicación.
 *
 * @author Ricardo.
 */
public class ControladorEncriptador implements ActionListener {
  
  public FormVentanaPrincipal vista;
  
  // Parámetros a usar para el cifrado RSA.
  public int modulo = 0;
  public int exponentePublico = 0;
  public int exponentePrivado = 0;
  
  /**
   * Punto de inicio de la aplicación.
   *
   * @param args Argumentos de la línea de comandos.
   */
  public static void main(String[] args) {
    FormVentanaPrincipal vista = new FormVentanaPrincipal();
    ControladorEncriptador controlador = new ControladorEncriptador(vista);
    controlador.vista.setVisible(true);
  }
  
  /**
   * Constructor de la clase ControladorEncriptador.
   *
   * @param pVista Interfaz gráfica a usar por el controlador.
   */
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
   * Verifica si el carácter ASCII especificado es una letra minúscula.
   *
   * @param pCaracter Carácter ASCII a validar.
   * @return true si se cumple la condición, sino false.
   */
  public boolean esLetraMinuscula(char pCaracter) {
    int valorLetra = (int) pCaracter;
    return valorLetra >= 97 && valorLetra <= 122;
  }
  
  /**
   * Verifica si el carácter ASCII especificado es un espacio en blanco,
   * un salto de línea o un retorno de carro (código ASCII 13).
   *
   * @param pCaracter Carácter ASCII a validar.
   * @return true si se cumple la condición, sino false.
   */
  public boolean esSeparadorPalabra(char pCaracter) {
    return pCaracter == ' ' || pCaracter == '\n' || pCaracter == (char) 13;
  }
  
  /**
   * Verifica si el carácter ASCII especificado es un dígito.
   *
   * @param pCaracter Carácter ASCII a validar.
   * @return true si se cumple la condición, sino false.
   */
  public boolean esDigito(char pCaracter) {
    int valorLetra = (int) pCaracter;
    return valorLetra >= 48 && valorLetra <= 57;
  }

  /**
   * Verifica si el mensaje especificado contiene algún caracter inválido para el
   * algoritmo de cifrado especificado, exceptuando al carácter \n y al espacio.
   *
   * @param pMensaje Mensaje a validar.
   * @param pCifrado Algoritmo de cifrado a usar para la validación.
   * @param pOperacion Operación a realizar sobre el mensaje.
   * @return true si el mensaje es válido para el cifrado especificado, sino false.
   */
  public boolean mensajeValido(String pMensaje, String pCifrado, String pOperacion) {
    pMensaje = pMensaje.toLowerCase();

    switch (pCifrado) {

      // El alfabeto válido del cifrado César, llave y Vigenére son iguales.
      case "César":  // Pasa al siguiente case.
      case "Llave":  // Pasa al siguiente case.
      case "Vigenére":
        for (char letra : pMensaje.toCharArray()) {
          int valorLetra = (int) letra;
          if (!esLetraMinuscula(letra) && !esSeparadorPalabra(letra)) {
            return false;
          }
        }
        return true;
        
      case "Palabra inversa":  // Pasa al siguiente case.
      case "Mensaje inverso":
        // El alfabeto válido para este cifrado es cualquier carácter.
        return true;

      case "Código telefónico":  // Aquí hay que hacer validaciones diferentes dependiendo de la operación.
        if (pOperacion.equals("Cifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            if (!esLetraMinuscula(letra) && !esSeparadorPalabra(letra)) {
              return false;
            }
          }
          return true;
        }
        if (pOperacion.equals("Descifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            if (!esDigito(letra) && !esSeparadorPalabra(letra) && letra != '*') {
              return false;
            }
          }
          return true;
        }
        return false;

      case "Codificación binaria":  // Aquí hay que hacer validaciones diferentes dependiendo de la operación.
        if (pOperacion.equals("Cifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            if (!esLetraMinuscula(letra) && !esSeparadorPalabra(letra)) {
              return false;
            }
          }
          return true;
        }
        if (pOperacion.equals("Descifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            if (letra != '0' && letra != '1' && letra != '*' && !esSeparadorPalabra(letra)) {
              return false;
            }
          }
          return true;
        }
        return false;

      case "RSA":
        if (pOperacion.equals("Cifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            int valorLetra = (int) letra;
            if (!(valorLetra >= 32 && valorLetra <= 126) && !esSeparadorPalabra(letra)) {
              System.out.println((int) letra);
              return false;
            }
          }
          return true;
        }
        if (pOperacion.equals("Descifrar")) {
          for (char letra : pMensaje.toCharArray()) {
            if (!esDigito(letra) && !esSeparadorPalabra(letra) && letra != '*') {
              System.out.println((int) letra);
              return false;
            }
          }
          return true;
        }
        return false;
        
      case "DES":  // Por implementar.
      case "AES":  // Por implementar.
      default:
        return false;
    }
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
  
  /**
   * Cambia el texto del botón de cifrado/descifrado a la operación seleccionada actualmente.
   */
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

    if (opcionElegida != JFileChooser.APPROVE_OPTION) {
      return;
    }

    BufferedReader lectorArchivo;
    try {
      lectorArchivo = new BufferedReader(new FileReader(electorArchivo.getSelectedFile()));
      
      StringBuilder textoArchivo = new StringBuilder();
      String lineaArchivo;
      lineaArchivo = lectorArchivo.readLine();

      while (lineaArchivo != null) {
        textoArchivo.append(lineaArchivo);
        textoArchivo.append(System.lineSeparator());
        lineaArchivo = lectorArchivo.readLine();
      }

      vista.txtEntrada.setText(textoArchivo.toString());
      lectorArchivo.close();

    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(vista, "No se encontró el archivo seleccionado.",
                                    "Archivo no encontrado", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(vista, "Ocurrió un problema con la lectura del archivo.",
                                    "Error de lectura", JOptionPane.ERROR_MESSAGE);
    }


  }
  
  /**
   * Verifica cuál es el algoritmo de cifrado elegido y en base a eso cifra
   * el mensaje introducido por el usuario y lo muestra en la interfaz.
   */
  public void cifrar() {
    String algoritmoElegido = (String) vista.cbAlgoritmo.getSelectedItem();
    String mensaje = vista.txtEntrada.getText();
    if (mensaje.isEmpty()) {
      String msgError = "Porfavor introduzca primero el mensaje a cifrar.";
      JOptionPane.showMessageDialog(vista, msgError, "Entrada vacía", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (!mensajeValido(mensaje, algoritmoElegido, "Cifrar")) {
      String msgError = "Se encontraron 1 o más caracteres no soportados por el algoritmo de cifrado elegido.";
      JOptionPane.showMessageDialog(vista, msgError, "Entrada inválida", JOptionPane.ERROR_MESSAGE);
      return;
    }
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
        
        // En caso de que no se hayan generado los parámetros
        // necesarios para el cifrado RSA, se generan aquí.
        if (modulo == 0) {
          List<Integer> parametros = cifRsa.generarParametros();
          modulo = parametros.get(0);
          exponentePublico = parametros.get(1);
          exponentePrivado = parametros.get(2);
        }
        
        mensajeCifrado = cifRsa.cifrarMensaje(mensaje, modulo, exponentePublico);
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
    if (mensaje.isEmpty()) {
      String msgError = "Porfavor introduzca primero el mensaje a cifrar.";
      JOptionPane.showMessageDialog(vista, msgError, "Entrada vacía", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (!mensajeValido(mensaje, algoritmoElegido, "Descifrar")) {
      String msgError = "El mensaje especificado no es descifrable con el algoritmo elegido.";
      JOptionPane.showMessageDialog(vista, msgError, "Entrada inválida", JOptionPane.ERROR_MESSAGE);
      return;
    }
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
        
        if (modulo == 0) {
          String msg = """
                       Aún no se han generado los parámetros necesarios para el decifrado con RSA.
                       Porfavor cifre algún mensaje con RSA antes de continuar.""";
          JOptionPane.showMessageDialog(vista, msg, "Faltan parámetros", JOptionPane.ERROR_MESSAGE);
          return;
        }
        
        mensajeDescifrado = cifRsa.descifrarMensaje(mensaje, modulo, exponentePrivado);
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