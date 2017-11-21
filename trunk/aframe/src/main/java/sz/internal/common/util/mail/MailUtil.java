package sz.internal.common.util.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import sz.internal.common.SystemConfig;
import sz.internal.common.component.code.CodeConstant;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * User: wanggang
 * Date: 2/2/16
 * Time: 10:53 AM
 */
public class MailUtil {
    private static JavaMailSenderImpl senderImpl = null;

    private static JavaMailSender getMailSender() throws Exception {
        if (senderImpl == null) {
            senderImpl = new JavaMailSenderImpl();
            initSender();
        }
        return senderImpl;
    }

    private static void initSender() throws Exception {
        String host = SystemConfig.getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_HOST);
        String userName = SystemConfig.getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_USERNAME);
        String password = SystemConfig.getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_MAIL_SMTP_PASSWORD);
        if (StringUtils.isEmpty(host)) {
            throw new Exception("Please config SMTP host.");
        }
        senderImpl.setHost(host);
        String needAuth = "false";
        if (!StringUtils.isEmpty(userName)) {
            senderImpl.setUsername(userName);
            senderImpl.setPassword(password);
            needAuth = "true";
        }
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", needAuth);
        prop.put("mail.smtp.timeout", "25000");
        senderImpl.setJavaMailProperties(prop);
    }

    public static void sendMail(String to, String subject, String body) throws Exception {
        sendMail(to, subject, body, false, null);
    }

    public static void sendMail(String to, String subject, String body, boolean html) throws Exception {
        sendMail(to, subject, body, html, null);
    }

    public static void sendMail(String to, String subject, String body, boolean html, File[] files) throws Exception {
        JavaMailSender sender = getMailSender();
        MimeMessage mailMessage = sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

        messageHelper.setTo(to);
        String senderName = SystemConfig.getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_MAIL_SENDER_NAME);
        String senderAddr = SystemConfig.getSystemConfigValue(CodeConstant.SYSTEM_CONFIG_MAIL_SENDER_ADDR);
        messageHelper.setFrom(new InternetAddress(senderAddr, senderName));
        messageHelper.setSubject(subject);
        messageHelper.setText(body, html);
        for (int i = 0; files != null && i < files.length; i++) {
            String fileName = files[i].getName();
            messageHelper.addAttachment(fileName, files[i]);
        }
        sender.send(mailMessage);
    }

}
