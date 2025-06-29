package com.reservas.backend.service;

import com.reservas.backend.model.Reserva;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import java.time.Year;

/**
 * Servicio de envío de correos HTML.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Genera el HTML responsive para la confirmación de reserva.
     */
    public String buildHtmlReserva(Reserva r) {

        String urlProducto = "http://localhost:5173/producto/" + r.getProducto().getId();

        return """
        <!DOCTYPE html>
        <html lang="es">
        <head><meta charset="UTF-8"><title>Confirmación de reserva</title></head>
        <body style="margin:0;padding:0;background:#f5f7fa;font-family:Arial,Helvetica,sans-serif">
          <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" style="padding:20px 0">
            <tr><td align="center">
              <table role="presentation" width="600" cellspacing="0" cellpadding="0" style="background:#ffffff;border-radius:8px;overflow:hidden">
                
                <tr>
                  <td style="background:#2a8bf2;color:#fff;padding:20px 30px;font-size:24px;font-weight:bold">
                    ReservaFácil
                  </td>
                </tr>
                
                <tr><td style="padding:30px">
                  <p style="margin:0 0 18px;font-size:18px">¡Hola <strong>%s</strong>!</p>
                  <p style="margin:0 0 18px">
                    Tu reserva para <strong>%s</strong> se ha registrado con éxito.
                  </p>

                  <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" style="margin:18px 0">
                    <tr><td style="padding:8px 0;font-weight:bold;width:120px">Desde:</td><td>%s</td></tr>
                    <tr><td style="padding:8px 0;font-weight:bold">Hasta:</td><td>%s</td></tr>
                  </table>

                  <p style="text-align:center;margin:30px 0">
                    <a href="%s" target="_blank"
                       style="background:#2a8bf2;color:#fff;text-decoration:none;padding:12px 24px;border-radius:6px;display:inline-block">
                       Ver producto
                    </a>
                  </p>

                  <p style="font-size:14px;color:#555;margin:0 0 10px">
                    Si tenés alguna consulta, respondé a este correo y con gusto te ayudaremos.
                  </p>
                  <p style="font-size:14px;color:#555;margin:0">
                    ¡Gracias por confiar en <strong>ReservaFácil</strong>!
                  </p>
                </td></tr>

                <tr>
                  <td style="background:#f0f0f0;padding:16px;text-align:center;font-size:12px;color:#777">
                    © %s ReservaFácil · Todos los derechos reservados
                  </td>
                </tr>

              </table>
            </td></tr>
          </table>
        </body>
        </html>
        """.formatted(
                r.getUsuario().getNombre(),
                r.getProducto().getNombre(),
                r.getFechaInicio(),
                r.getFechaFin(),
                urlProducto,
                Year.now()
        );
    }

    /**
     * Envía un e‑mail HTML a un destinatario.
     */
    // EmailService.java
    public void enviarEmailHtml(String to, String subject, String html) throws MessagingException {
        if (to == null || to.isBlank()) {          // ⬅️  evita el 500
            //Log log = null;
            //log.warn("Destinatario vacío, no se envía correo");
            return;
        }
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(msg);
    }


}

