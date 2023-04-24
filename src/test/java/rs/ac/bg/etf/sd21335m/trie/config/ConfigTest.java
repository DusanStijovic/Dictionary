package rs.ac.bg.etf.sd21335m.trie.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;
import rs.ac.bg.etf.sd21335m.trie.aspects.util.EmailConfigKey;

@ExtendWith({InitConfigManager.class})
class ConfigTest {

    private static ConfigurationManager configurationManager;

    @BeforeAll
    static void setUp() {
        configurationManager = ConfigurationManager.getInstance();
    }

    @Test
    void testSenderEmail(){
        Assertions.assertEquals("test.sender@gmail.com", configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_EMAIL));
    }

    @Test
    void testReceiverEmail(){
        Assertions.assertEquals("test.receiver@gmail.com", configurationManager.getProperty(EmailConfigKey.EMAIL_RECEIVER_EMAIL));
    }

    @Test
    void testHost(){
        Assertions.assertEquals("smtp.gmail.com", configurationManager.getProperty(EmailConfigKey.HOST));
    }

    @Test
    void testSenderPassword(){
        Assertions.assertEquals("testtest", configurationManager.getProperty(EmailConfigKey.EMAIL_SENDER_PASSWORD));
    }

    @Test
    void testShouldSendEmail(){
        Assertions.assertEquals("false", configurationManager.getProperty(EmailConfigKey.SHOULD_SEND_EMAIL));
    }
}
