package stanl_2.final_backend.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.SecureRandom;

@Slf4j
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

    private String setErrorContext(String loginId, String name, String position, Log logEntry) {
        // Thymeleaf Context 생성
        Context context = new Context();
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        if("DIRECTOR".equals(position)){
            position = "임원";
        } else {
            position = "대표이사";
        }
        // 매개변수 저장
        context.setVariable("loginId", loginId);
        context.setVariable("name", name);
        context.setVariable("position", position);
        context.setVariable("status", logEntry.getStatus());
        context.setVariable("errorMessage", logEntry.getErrorMessage());
        context.setVariable("uri", logEntry.getUri());
        context.setVariable("method", logEntry.getMethod());
        context.setVariable("queryString", logEntry.getQueryString());
        context.setVariable("userAgent", logEntry.getUserAgent());
        context.setVariable("ipAddress", logEntry.getIpAddress());
        context.setVariable("hostName", logEntry.getHostName());
        context.setVariable("remotePort", logEntry.getRemotePort());

        // Thymeleaf 템플릿 설정
        templateResolver.setPrefix("templates/"); // 템플릿 디렉토리 설정
        templateResolver.setSuffix(".html"); // 파일 확장자 설정
        templateResolver.setTemplateMode(TemplateMode.HTML); // 템플릿 모드 설정
        templateResolver.setCacheable(false); // 캐싱 비활성화

        // 템플릿 엔진에 리졸버 설정
        templateEngine.setTemplateResolver(templateResolver);

        // `errorMail.html` 파일 처리 및 반환
        return templateEngine.process("errorMail", context);
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

    private MimeMessage createErrorEmailForm(String email, String loginId, String name, String position, Log logEntry) throws MessagingException {
        // MimeMessage 객체 생성
        MimeMessage message = mailSender.createMimeMessage();

        // 이메일 수신자, 제목 설정
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("🚨 STANL2 시스템 에러 알림");
        message.setFrom(configEmail);
        message.setText(setErrorContext(loginId, name, position, logEntry), "utf-8", "html");

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

    @Override
    public void sendErrorEmail(String email, String loginId, String name, String position, Log logEntry) throws MessagingException {

        MimeMessage emailForm = createErrorEmailForm(email, loginId, name, position, logEntry);

        mailSender.send(emailForm);
    }
}
