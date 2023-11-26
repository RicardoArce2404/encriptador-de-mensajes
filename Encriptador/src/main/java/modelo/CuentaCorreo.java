package modelo;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.MessagingException;
import org.apache.http.client.fluent.*;
import org.json.JSONObject;

/**
 * Implementación de una cuenta de correo que permite enviar un correo con o sin
 * archivos adjuntos.
 *
 * @author Mary Paz
 * @version 15/11/23
 */
public class CuentaCorreo {

  private final String usuario;
  private final String clave = "lvywbejxjyqqwdva";
  private final String servidor = "smtp.gmail.com";
  private final String puerto = "587";
  private final Properties propiedades;

  /**
   * Constructor de la clase para asignar el remitente y inicializar las
   * propiedades.
   *
   * @param pCorreo la dirección de correo del que se va a enviar el correo
   * (remitente).
   */
  public CuentaCorreo(String pCorreo) {
    propiedades = new Properties();
    propiedades.put("mail.smtp.host", servidor);
    propiedades.put("mail.smtp.port", puerto);
    propiedades.put("mail.smtp.auth", "true");
    propiedades.put("mail.smtp.starttls.enable", "true");
    usuario = pCorreo;
  }

  /**
   * Método para abrir la sesion de correo.
   *
   * @return sesion la sesion del correo abierta.
   */
  private Session abrirSesion() {
    Session sesion = Session.getInstance(propiedades,
            new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(usuario, clave);
      }
    });
    return sesion;
  }

  /**
   * Método para enviar un correo con asunto, destinatario y mensaje.
   *
   * @param pDestinatario direccion de correo a la que se va a enviar el correo.
   * @param pTituloCorreo asunto del correo.
   * @param pCuerpo mensaje que quiera enviar al pDestinatario.
   */
  public void enviarCorreo(String pDestinatario, String pTituloCorreo, String pCuerpo) {
    Session sesion = abrirSesion();

    try {
      Message message = new MimeMessage(sesion);
      message.setFrom(new InternetAddress(usuario));
      message.setRecipients(
              Message.RecipientType.TO,
              InternetAddress.parse(pDestinatario)
      );
      message.setSubject(pTituloCorreo);
      message.setText(pCuerpo);

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }

  /**
   * Hace una solicitud al API de abstract para validar el correo.
   *
   * @param pCorreo Correo que se quiere verificar.
   * @return Un objeto json con la información de validación del correo o null
   * si ocurrio un error.
   */
  private static JSONObject makeAbstractRequest(String pCorreo) {
    try {
      String jsonString = Request.Get("https://emailvalidation.abstractapi.com/v1/?api_"
              + "key=4b8ff173ab51406ebb3402908a1cca92&email="
              + pCorreo).execute().returnContent().asString();
      JSONObject json = new JSONObject(jsonString);
      return json;
    } catch (Exception e) {
      System.out.println("Ocurrió un error en makeAbstractRequest");
      System.out.println(e.toString());
      e.printStackTrace();
      return null;
    }

  }

  /**
   * Valida si el formato de correo es correcto y si el dominio se encuentra
   * entre la lista de proveedores de correo electrónico gratuitos de Abstract:
   * Gmail, Yahoo, etc.
   *
   * @param pCorreoAValidar El correo que se va a validar.
   * @return true si "value" de is_valid_format y is_free_format son verdaderos
   * y false si ocurre lo contrario o algún error.
   */
  public boolean correoValido(String pCorreoAValidar) {
    JSONObject content = makeAbstractRequest(pCorreoAValidar);
    boolean valueFormat = false;
    boolean valueFreeEmail = false;

    if (content == null) {
      return false;
    }

    try {
      JSONObject formatObject = content.getJSONObject("is_valid_format");
      valueFormat = formatObject.getBoolean("value");

      JSONObject freeEmailObject = content.getJSONObject("is_free_email");
      valueFreeEmail = freeEmailObject.getBoolean("value");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    if (valueFormat && valueFreeEmail) {
      return true;
    } else {
      return false;
    }

  }

}
