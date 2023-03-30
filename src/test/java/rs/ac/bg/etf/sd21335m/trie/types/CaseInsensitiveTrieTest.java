package rs.ac.bg.etf.sd21335m.trie.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class CaseInsensitiveTrieTest extends TrieTest {
    @Override
    protected Trie makeTrie() {
        return new CaseInsensitiveTrie();
    }

    @Test
    @Override
    void addNewWordCheckIfExistWithVariousCases() {
        trie.addNewWord("dusan");
        Assertions.assertTrue(trie.wordExist("DUSAN"));
        Assertions.assertTrue(trie.wordExist("Dusan"));
        Assertions.assertTrue(trie.wordExist("DusaN"));
    }

    @Test
    void addWordDeleteWithOtherCaseCheckIfExist(){
        trie.addNewWord("dusan");
        trie.removeWord("DUSAN");
        Assertions.assertFalse(trie.wordExist("dusan"));
    }

    @Test
    void searchWordWithPrefix(){
        trie.addNewWord("dusan");
        trie.addNewWord("dus");
        Set<String> wordsPrefix = trie.searchByStrategy(prefixMatchStrategy, "DUS");
        Assertions.assertEquals(wordsPrefix, new HashSet<>(Arrays.asList("dusan", "dus")));
    }

    @Test
    void addExistingWordDifferentCase(){
        trie.addNewWord("dusan");
        Assertions.assertThrows(WordAlreadyExist.class, ()-> trie.addNewWord("DUSAN"));
    }
}
