package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import rs.ac.bg.etf.sd21335m.trie.config.ConfigurationManager;

public class InitConfigManager  implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {
    public static final String TEST_EMAIL_CONFIG_PATH = "/config.properties";

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        ConfigurationManager.init(TEST_EMAIL_CONFIG_PATH);
    }

    @Override
    public void close() {

    }
}
