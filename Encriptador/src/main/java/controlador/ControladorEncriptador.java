package controlador;

import java.awt.AWTError;
import java.awt.HeadlessException;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

/**
 * Módulo de comunicación entre el modelo y la interfaz del encriptador. Aquí se
 * inicia la ejecución de la aplicación.
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
    vista.btCopiar.addActionListener((ActionListener) this);
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
   * Verifica si el carácter ASCII especificado es un espacio en blanco, un
   * salto de línea o un retorno de carro (código ASCII 13).
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
   * Valida si el texto especificado contiene solo caracteres ASCII.
   *
   * @param pTexto Texto a validar.
   * @return true si se cumple la condición, sino false.
   */
  public boolean esTextoAscii(String pTexto) {
    for (char caracter : pTexto.toCharArray()) {
      if (!StandardCharsets.US_ASCII.newEncoder().canEncode(caracter)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Valida si el texto especificado contiene solo caracteres ASCII visibles.
   *
   * @param pTexto Texto a validar.
   * @return true si se cumple la condición, sino false.
   */  
  public boolean esTextoAsciiVisible(String pTexto) {
    for (char caracter : pTexto.toCharArray()) {
      int valorCaracter = (int) caracter;
      if (!StandardCharsets.US_ASCII.newEncoder().canEncode(caracter) || valorCaracter < 33 || valorCaracter > 126) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifica si el mensaje especificado contiene algún caracter inválido para
   * el algoritmo de cifrado especificado, exceptuando al carácter \n y al
   * espacio.
   *
   * @param pMensaje Mensaje a validar.
   * @param pCifrado Algoritmo de cifrado a usar para la validación.
   * @param pOperacion Operación a realizar sobre el mensaje.
   * @return true si el mensaje es válido para el cifrado especificado, sino
   * false.
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

      case "DES":  // Pasa al siguiente case.
      case "AES":
        if (pOperacion.equals("Cifrar")) {
          return esTextoAscii(pMensaje);
        }
        if (pOperacion.equals("Descifrar")) {
          return esTextoAsciiVisible(pMensaje);
        }
        return false;

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
    switch (pEvento.getActionCommand()) {
      case "comboBoxChanged" -> cambiarOperacion();
      case "Cargar archivo de texto" -> cargarArchivoTexto();
      case "Cifrar texto" -> cifrar();
      case "Descifrar texto" -> descifrar();
      case "Enviar por correo" -> enviarCorreo();
      case "Copiar" -> copiarSalida();
      case "Salir" -> salir();
      default -> {
        return;
      }
    }
  }

  /**
   * Cambia el texto del botón de cifrado/descifrado a la operación seleccionada
   * actualmente.
   */
  public void cambiarOperacion() {
    String operacionActual = (String) vista.cbOperacion.getSelectedItem();
    vista.btCifrar.setText(operacionActual + " texto");
  }

  /**
   * Carga un archivo de texto e introduce su contenido en el cuadro de entrada
   * de la interfaz.
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
   * Verifica cuál es el algoritmo de cifrado elegido y en base a eso cifra el
   * mensaje introducido por el usuario y lo muestra en la interfaz.
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
    String llave;

    switch (algoritmoElegido) {

      case "César":
        CifCesar cifCesar = new CifCesar();
        mensajeCifrado = cifCesar.cifrarMensaje(mensaje);
        break;

      case "Llave":
        CifLlave cifLlave = new CifLlave();
        llave = JOptionPane.showInputDialog(vista, "Ingrese la llave a usar para cifrar el mensaje.");
        if (llave.isEmpty()) {  // Si no se especifica una llave, se usa la predeterminada.
          mensajeCifrado = cifLlave.cifrarMensaje(mensaje);
        } else {
          mensajeCifrado = cifLlave.cifrarMensaje(mensaje, llave);
        }
        break;

      case "Vigenére":
        CifVigenere cifVigenere = new CifVigenere();
        mensajeCifrado = cifVigenere.cifrarMensaje(mensaje);
        break;

      case "Palabra inversa":
        CifPalabraInversa cifPalabraInversa = new CifPalabraInversa();
        mensajeCifrado = cifPalabraInversa.cifrarMensaje(mensaje);
        break;

      case "Mensaje inverso":
        CifMensajeInverso cifMensajeInverso = new CifMensajeInverso();
        mensajeCifrado = cifMensajeInverso.cifrarMensaje(mensaje);
        break;

      case "Código telefónico":
        CifCodigoTelefonico cifCodigoTelefonico = new CifCodigoTelefonico();
        mensajeCifrado = cifCodigoTelefonico.cifrarMensaje(mensaje);
        break;

      case "Codificación binaria":
        CifCodificacionBinaria cifCodificacionBinaria = new CifCodificacionBinaria();
        mensajeCifrado = cifCodificacionBinaria.cifrarMensaje(mensaje);
        break;

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

      case "DES":
        CifDes cifDes = new CifDes();
        mensajeCifrado = cifDes.cifrarMensaje(mensaje);
        break;

      case "AES":
        CifAes cifAes = new CifAes();
        llave = JOptionPane.showInputDialog(vista, "Ingrese la llave a usar para cifrar el mensaje.");
        if (llave.isEmpty()) {  // Si no se especifica una llave, se usa la predeterminada.
          mensajeCifrado = cifAes.cifrarMensaje(mensaje);
        } else {
          mensajeCifrado = cifAes.cifrarMensaje(mensaje, llave);
        }
        break;

      default:
        JOptionPane.showMessageDialog(vista, "No se pudo cifrar el mensaje.");
        return;
    }

    if (mensajeCifrado == null) {
      String msg = "Ocurrió un error con el cifrado del mensaje.";
      JOptionPane.showMessageDialog(vista, msg, "Error de cifrado", JOptionPane.ERROR_MESSAGE);
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
    String llave;

    switch (algoritmoElegido) {

      case "César":
        CifCesar cifCesar = new CifCesar();
        mensajeDescifrado = cifCesar.descifrarMensaje(mensaje);
        break;

      case "Llave":
        CifLlave cifLlave = new CifLlave();
        llave = JOptionPane.showInputDialog(vista,
                "Ingrese la llave a usar para descifrar el mensaje.");
        if (llave.isEmpty()) {  // Si no se especifica una llave, se usa la predeterminada.
          mensajeDescifrado = cifLlave.descifrarMensaje(mensaje);
        } else {
          mensajeDescifrado = cifLlave.descifrarMensaje(mensaje, llave);
        }
        break;

      case "Vigenére":
        CifVigenere cifVigenere = new CifVigenere();
        mensajeDescifrado = cifVigenere.descifrarMensaje(mensaje);
        break;

      case "Palabra inversa":
        CifPalabraInversa cifPalabraInversa = new CifPalabraInversa();
        mensajeDescifrado = cifPalabraInversa.descifrarMensaje(mensaje);
        break;

      case "Mensaje inverso":
        CifMensajeInverso cifMensajeInverso = new CifMensajeInverso();
        mensajeDescifrado = cifMensajeInverso.descifrarMensaje(mensaje);
        break;

      case "Código telefónico":
        CifCodigoTelefonico cifCodigoTelefonico = new CifCodigoTelefonico();
        mensajeDescifrado = cifCodigoTelefonico.descifrarMensaje(mensaje);
        break;

      case "Codificación binaria":
        CifCodificacionBinaria cifCodificacionBinaria = new CifCodificacionBinaria();
        mensajeDescifrado = cifCodificacionBinaria.descifrarMensaje(mensaje);
        break;

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

      case "DES":
        CifDes cifDes = new CifDes();
        mensajeDescifrado = cifDes.descifrarMensaje(mensaje);
        break;

      case "AES":
        CifAes cifAes = new CifAes();
        llave = JOptionPane.showInputDialog(vista,
                "Ingrese la llave a usar para descifrar el mensaje.");
        if (llave.isEmpty()) {  // Si no se especifica una llave, se usa la predeterminada.
          mensajeDescifrado = cifAes.descifrarMensaje(mensaje);
        } else {
          mensajeDescifrado = cifAes.descifrarMensaje(mensaje, llave);
        }
        break;

      default:
        JOptionPane.showMessageDialog(vista, "No se pudo descifrar el mensaje.");
        return;
    }

    if (mensajeDescifrado == null) {
      String msg = "Ocurrió un error con el descifrado del mensaje.";
      JOptionPane.showMessageDialog(vista, msg, "Error de descifrado", JOptionPane.ERROR_MESSAGE);
      return;
    }
    vista.txtSalida.setText(mensajeDescifrado);
  }

  /**
   * Envía el texto de salida a la dirección de correo deseada, siempre y cuando
   * no sea un texto vacío y la dirección de correo ingresada exista.
   */
  public void enviarCorreo() {
    String msg = "Porfavor escriba la dirección de correo a la cual desea enviar el texto.";
    String correo = JOptionPane.showInputDialog(vista, msg);
    
    if (correo == null) {
      return;
    }
    if (correo.isEmpty()) {
      JOptionPane.showMessageDialog(vista, "Porfavor especifique primero la dirección de correo.");
      return;
    }

    CuentaCorreo cuenta = new CuentaCorreo("cifradosrmh@gmail.com");
    if (!cuenta.correoValido(correo)) {
      String msgError = "La dirección de correo especificada es inválida.";
      JOptionPane.showMessageDialog(vista, msgError, "Dirección inválida", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String mensajeCifrado = vista.txtSalida.getText();
    String cuerpoCorreo = "El mensaje cifrado solicitado es el siguiente:\n\n" + mensajeCifrado;
    cuenta.enviarCorreo(correo, "Mensaje cifrado", cuerpoCorreo);
  }

  public void copiarSalida() {
    String textoSalida = vista.txtSalida.getText();
    if (textoSalida.isEmpty()) {
      String msg = "No hay texto para copiar aún.";
      JOptionPane.showMessageDialog(vista, msg, "Texto vacío", JOptionPane.ERROR_MESSAGE);
      return;
    }
    Clipboard portapapeles;

    try {
      portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();
      portapapeles.setContents(new StringSelection(textoSalida), null);
      JOptionPane.showMessageDialog(vista, "Texto copiado satisfactoriamente.");
    } catch (AWTError | HeadlessException e) {
      String msg = "Ocurrió un error al intentar acceder al portapapeles del sistema.";
      JOptionPane.showMessageDialog(vista, msg, "Error del sistema", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalStateException e) {
      String msg = "El portapapeles no está disponible en este momento.";
      JOptionPane.showMessageDialog(vista, msg, "Error de copiado", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Termina la ejecución del programa.
   */
  public void salir() {
    System.exit(0);
  }
}
