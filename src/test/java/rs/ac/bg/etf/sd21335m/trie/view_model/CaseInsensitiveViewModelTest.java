package rs.ac.bg.etf.sd21335m.trie.view_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategyType;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CaseInsensitiveViewModelTest extends TrieModelViewTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        trieModelView.setTrieType(TriType.CASE_INSENSITIVE);
    }

    @Test
    void chooseCaseInsensitiveTrie() {
        trieModelView.setTrieType(TriType.CASE_INSENSITIVE);
        Assertions.assertEquals(TriType.CASE_INSENSITIVE, trieModelView.getTrieType());
    }

    @Test
    void changeTrieTypeCheckIfTrieEmpty() {
        trieModelView.addWord("dusan");
        TriType old = trieModelView.getTrieType();
        trieModelView.setTrieType(TriType.BASIC);
        Assertions.assertEquals(TriType.BASIC, trieModelView.getTrieType());
        Assertions.assertTrue(trieModelView.isEmpty());
        trieModelView.setTrieType(old);
        Assertions.assertEquals(old, trieModelView.getTrieType());
    }

    @Test
    void checkDefaultTrie(){
        Assertions.assertEquals(TriType.CASE_INSENSITIVE, trieModelView.getTrieType());
    }

    @Test
    void searchWithExactStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        Set<String> expected = new HashSet<>(List.of("dusan"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("DUSAN"));
    }

    @Test
    void searchWithPrefixStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dus");
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        Set<String> expected = new HashSet<>(Arrays.asList("dusan", "dus"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("DUS"));
    }


    @Test
    void searchWithWildStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dasan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        Set<String> expected = new HashSet<>(Arrays.asList("dusan", "dasan"));
        Assertions.assertEquals(expected, trieModelView.searchByStrategy("D?S?N"));
    }

    @Test
    void deleteWithExactStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        trieModelView.deleteByStrategy("Dusan");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
    }

    @Test
    void deleteWithExactStrategyAddCase(){
        trieModelView.addWord("Dusan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.EXACT);
        trieModelView.deleteByStrategy("dusan");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
    }

    @Test
    void deleteWithPrefixStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dus");
        trieModelView.chooseMatchStrategy(MatchStrategyType.PREFIX);
        trieModelView.deleteByStrategy("DUS");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
        Assertions.assertFalse(trieModelView.wordExist("dus"));
    }

    @Test
    void deleteWithWildStrategy(){
        trieModelView.addWord("dusan");
        trieModelView.addWord("dasan");
        trieModelView.chooseMatchStrategy(MatchStrategyType.WILD_CARD);
        trieModelView.deleteByStrategy("D?S?N");
        Assertions.assertFalse(trieModelView.wordExist("dusan"));
        Assertions.assertFalse(trieModelView.wordExist("dasan"));
    }
}
