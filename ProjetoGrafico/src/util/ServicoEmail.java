package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Classe utilitária para envio de emails através do servidor SOGo/SMTP da
 * Escola.
 * Utilizada para enviar confirmações de registo aos novos utilizadores.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ServicoEmail {

    private static final String EMAIL_REMETENTE = "a2023153270@alunos.estgoh.ipc.pt";
    private static final String PASSWORD = "Andreps2005?";

    private static final String SMTP_HOST = "mail.estgoh.ipc.pt";
    private static final String SMTP_PORT = "587";

    /**
     * Envia um email de confirmação de registo para o utilizador.
     * O envio é feito numa Thread separada para não bloquear a interface gráfica.
     *
     * @param emailDestino   endereço de email do destinatário
     * @param nome           nome do utilizador registado
     * @param tipoUtilizador tipo de conta (Cliente ou Funcionário)
     */
    public static void enviarEmailConfirmacaoRegisto(String emailDestino, String nome, String tipoUtilizador) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, PASSWORD);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message mensagem = new MimeMessage(session);
                    mensagem.setFrom(new InternetAddress(EMAIL_REMETENTE, "Departamento de Apoio ao Cliente"));
                    mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
                    mensagem.setSubject("Confirmação de Registo - Aplicação de Gestão de Reparações");
                    String corpo = "Olá " + nome + ",\n\n"
                            + "Confirmamos que o teu registo como " + tipoUtilizador + " foi submetido com sucesso.\n"
                            + "A tua conta já se encontra ATIVA e já podes iniciar sessão no sistema.\n\n"
                            + "Com os melhores cumprimentos,\nDepartamento de Apoio ao Cliente.";

                    mensagem.setText(corpo);
                    Transport.send(mensagem);
                    System.out.println("Email enviado com sucesso para: " + emailDestino);
                } catch (Exception e) {
                    System.err.println("Falha ao enviar email: " + e.getMessage());
                }
            }
        }).start();
    }
}
