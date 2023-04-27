package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Order;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.EmailConfigKey;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.EmailSender;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.ExceptionFormatter;
import rs.ac.bg.etf.sd21335m.trie.config.ConfigurationManager;

@Aspect
@Order(4)
public class TrieExceptionAspect {

    private EmailSender emailSender;

    private final ExceptionFormatter exceptionFormatter = new ExceptionFormatter();
    private Throwable lastThrowable = null;

    private static int getHostPort(ConfigurationManager configurationManager) {
        try {
            return Integer.parseInt(configurationManager.getProperty(EmailConfigKey.PORT));
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private static boolean shouldSendEmailOnError() {
        return "true".equals(ConfigurationManager.getInstance().getProperty(EmailConfigKey.SHOULD_SEND_EMAIL));
    }

    private EmailSender createEmailSender() {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        String host = configurationManager.getProperty(EmailConfigKey.HOST);
        String emailSenderEmail = configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_EMAIL);
        String emailSenderPassword = configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_PASSWORD);
        int hostPort = getHostPort(configurationManager);
        return new EmailSender(host, hostPort, emailSenderEmail, emailSenderPassword);
    }

    @AfterThrowing(pointcut = "execution (* rs.ac.bg.etf.sd21335m.trie..*(..))", throwing = "exception")
    public void logException(Throwable exception) {
        emailSender = getEmailSender();
        if (lastThrowable != exception) {
            lastThrowable = exception;
            if (shouldSendEmailOnError()) {
                ConfigurationManager configurationManager = ConfigurationManager.getInstance();
                String emailReceiverEmail = configurationManager.getProperty(EmailConfigKey.EMAIL_RECEIVER_EMAIL);
                emailSender.sendEmail(emailReceiverEmail, "[Exception] Exception happened in trie app", exceptionFormatter.getStackTraceAsString(exception));
            }
        }
    }

    private EmailSender getEmailSender() {
        if (emailSender == null){
            emailSender = createEmailSender();
        }
        return emailSender;
    }
}
