package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.delete_strategy.DeleteStrategy;
import rs.ac.bg.etf.sd21335m.trie.search_strategy.SearchStrategy;

import java.util.Set;

public interface Trie {
    void addNewWord(String word);

    Set<String> search(SearchStrategy searchStrategy, String lookFor);
    void delete(DeleteStrategy deleteStrategy, String query);
    boolean wordExist(String word);

    void removeWord(String word);

    boolean isEmpty();

    void removeAllWords();

    int getNumberOfNodes();
}
