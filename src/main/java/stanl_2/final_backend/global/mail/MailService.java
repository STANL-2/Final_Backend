package stanl_2.final_backend.global.mail;

import jakarta.mail.MessagingException;
import stanl_2.final_backend.domain.log.command.aggregate.Log;

public interface MailService {

    /* 메일 전송 */
    void sendEmail(String toEmail) throws MessagingException;

    void sendPwdEmail(String decrypt, StringBuilder password) throws MessagingException;

    void sendErrorEmail(String email, String loginId, String name, String position, Log logEntry) throws MessagingException;
}
