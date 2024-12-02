package stanl_2.final_backend.global.mail;

import jakarta.mail.MessagingException;

public interface MailService {

    /* 메일 전송 */
    void sendEmail(String toEmail) throws MessagingException;

    void sendPwdEmail(String decrypt, StringBuilder password) throws MessagingException;
}
