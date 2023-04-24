package rs.ac.bg.etf.sd21335m.trie.view_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;

@ExtendWith({InitConfigManager.class})
class BasicTrieModelViewTest extends TrieModelViewTest {

    @Test
    void chooseBasicTrie() {
        trieModelView.setTrieType(TriType.BASIC);
        Assertions.assertEquals(TriType.BASIC, trieModelView.getTrieType());
    }

    @Test
    void changeTrieTypeCheckIfTrieEmpty() {
        trieModelView.addWord("dusan");
        TriType old = trieModelView.getTrieType();
        trieModelView.setTrieType(TriType.CASE_INSENSITIVE);
        Assertions.assertEquals(TriType.CASE_INSENSITIVE, trieModelView.getTrieType());
        Assertions.assertTrue(trieModelView.isEmpty());
        trieModelView.setTrieType(old);
        Assertions.assertEquals(old, trieModelView.getTrieType());
    }
}
