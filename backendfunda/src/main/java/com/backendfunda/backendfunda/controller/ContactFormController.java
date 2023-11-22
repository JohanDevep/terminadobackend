package com.backendfunda.backendfunda.controller;

import com.backendfunda.backendfunda.model.ContactForm;
import com.backendfunda.backendfunda.repository.ContactFormRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/contacto/")
public class ContactFormController {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    // Metodo para enviar el correo con un diseño
    private void sendDefaultResponse(String recipientEmail) {
        // Crear un objeto que permita multimedia y texto para el correo electrónico
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setFrom(emailFrom); // Establecer el que envia el correo
            helper.setTo(recipientEmail); // Establecer al q le llegue el correo
            helper.setSubject("Pronto estaremos en contacto contigo"); // Establecer el asunto en el correo

            // HTML con diseño para el correo
            String htmlContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "  body { font-family: Arial, sans-serif; }"
                    + "  .container { background-color: #f2e7ff; padding: 20px; }"
                    + "  h1 { color: #6a3ab2; }"
                    + "  p { color: #333; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<h1>Gracias por ponerte en contacto con nosotros</h1>"
                    + "<p>Pronto estaremos en contacto contigo.</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            helper.setText(htmlContent, true); // Establecer el contenido del correo como HTML
            javaMailSender.send(message); // Enviar el correo electrónico
        } catch (MessagingException e) {
            e.printStackTrace();
            // Manejar la excepción en caso de un error en el envío del correo
        }
    }

    // Endpoint para manejar la solicitud POST para enviar un mensaje
    @PostMapping("/enviarMensaje")
    public ResponseEntity<String> enviarMensaje(@RequestBody ContactForm contactForm) {
        // Validar los datos del formulario
        if (contactForm.getEmail() == null || contactForm.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo de correo electrónico es obligatorio");
        }
        if (contactForm.getSubject() == null || contactForm.getSubject().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo de asunto es obligatorio");
        }
        if (contactForm.getMessage() == null || contactForm.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo de mensaje es obligatorio");
        }

        // Crear un nuevo objeto SimpleMailMessage para enviar el correo electrónico
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom); // Usar la dirección configurada como remitente
        message.setTo(contactForm.getEmail()); // Usar la dirección proporcionada en el formulario como destinatario
        message.setSubject(contactForm.getSubject()); // Establecer el asunto del correo
        message.setText(contactForm.getMessage()); // Establecer el mensaje del correo

        javaMailSender.send(message); // Enviar el correo electrónico

        // Enviar una respuesta predefinida al correo electrónico del remitente
        sendDefaultResponse(contactForm.getEmail());

        // Guardar el formulario en la base de datos utilizando el repositorio
        contactFormRepository.save(contactForm);

        return ResponseEntity.ok("Mensaje enviado correctamente"); // Respuesta exitosa
    }
}
