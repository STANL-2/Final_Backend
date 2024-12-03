package stanl_2.final_backend.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.SecureRandom;

@Service(value = "MailService")
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Value("${spring.mail.host.port.username}")
    private String configEmail;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,
                           RedisService redisService) {
        this.mailSender = mailSender;
        this.redisService = redisService;
    }

    /* 6자리 난수 생성 */
    private String createCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    /* Thymeleaf 기반의 html 파일에 값 넣고 연결하는 메소드 */
    private String setContext(String code) {

        // thymeleaf 기반의 html 파일에 값을 넣고 연결
        Context context = new Context();
        // templateengine과 classloadertemplateresolver를 활용하여 resource/template에 위치한 mail.html 연결
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        // 매개변수 저장
        context.setVariable("code", code);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("mail", context);
    }

    private String setPwdContext(String newPwd) {

        // thymeleaf 기반의 html 파일에 값을 넣고 연결
        Context context = new Context();
        // templateengine과 classloadertemplateresolver를 활용하여 resource/template에 위치한 mail.html 연결
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        // 매개변수 저장
        context.setVariable("tempPassword", newPwd);

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("pwdMail", context);
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createCode();

        // MimeMessage에 코드, 송신 이메일, 내용 보관
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("STANL2 본인 확인");
        message.setFrom(configEmail);
        message.setText(setContext(authCode), "utf-8", "html");

        // redis에 인증번호, 이메일 저장
        redisService.setKeyWithTTL(email, authCode, 60 * 5L);

        return message;
    }

    private MimeMessage createNewPwdEmailForm(String email, String newPwd) throws MessagingException {

        // MimeMessage에 코드, 송신 이메일, 내용 보관
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("STANL2 본인 확인");
        message.setFrom(configEmail);
        message.setText(setPwdContext(newPwd), "utf-8", "html");

        return message;
    }

    /* 만든 메일 전송 */
    @Override
    public void sendEmail(String toEmail) throws MessagingException {

        // redis에 해당 이메일이 있으면 db에서 삭제
        if(redisService.getKey(toEmail) != null) {
            redisService.clearMailCache(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);

        mailSender.send(emailForm);
    }

    @Override
    public void sendPwdEmail(String email, StringBuilder password) throws MessagingException {

        MimeMessage emailForm = createNewPwdEmailForm(email, password.toString());

        mailSender.send(emailForm);
    }
}
