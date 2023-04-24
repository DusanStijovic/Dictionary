package rs.ac.bg.etf.sd21335m.trie.types;

import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;

@ExtendWith({InitConfigManager.class})
public class BasicTrieTest extends TrieTest {

    @Override
    protected Trie makeTrie() {
        return new BasicTrie();
    }
}
