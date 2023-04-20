package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Order;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.EmailConfig;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.EmailSender;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.ExceptionFormatter;
@Aspect
@Order(4)
public class TrieExceptionAspect {
    private final EmailSender emailSender = new EmailSender(EmailConfig.HOST, 587, EmailConfig.EMAIL_SENDER_EMAIL, EmailConfig.EMAIL_SENDER_PASSWORD);

    private final ExceptionFormatter exceptionFormatter = new ExceptionFormatter();
    private Throwable lastThrowable = null;

    @AfterThrowing(pointcut = "execution (* rs.ac.bg.etf.sd21335m.trie..*(..))", throwing = "exception")
    public void logException(Throwable exception) {
        if (lastThrowable != exception) {
            lastThrowable = exception;
            emailSender.sendEmail(EmailConfig.EMAIL_RECEIVER_EMAIL, "[Exception] Exception happened in trie app", exceptionFormatter.getStackTraceAsString(exception));
        }
    }
}
