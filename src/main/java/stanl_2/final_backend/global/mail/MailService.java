package stanl_2.final_backend.global.mail;

import jakarta.mail.MessagingException;

public interface MailService {

    /* 메일 전송 */
    void sendEmail(String toEmail) throws MessagingException;

    // 보낸 이메일과 인증번호 일치하는지 확인
    Boolean verifyEmailCode(String email, String code);


    void sendPwdEmail(String decrypt, StringBuilder password) throws MessagingException;
}
