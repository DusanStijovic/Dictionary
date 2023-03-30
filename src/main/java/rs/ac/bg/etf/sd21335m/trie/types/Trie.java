package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategy;

import java.util.Set;

public interface Trie {
    void addNewWord(String word);

    Set<String> searchByStrategy(MatchStrategy matchStrategy, String lookFor);
    void removeByStrategy(MatchStrategy matchStrategy, String query);
    boolean wordExist(String word);

    void removeWord(String word);

    boolean isEmpty();

    void removeAllWords();

    int getNumberOfNodes();

    TriType getType();
}
