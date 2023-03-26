package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.search_strategy.SearchStrategy;

import java.util.Set;

public class CaseInsensitiveTrie extends BasicTrie {

    @Override
    public void addNewWord(String word) {
        String lowerCaseWord = toLowerCaseIfNotNull(word);
        super.addNewWord(lowerCaseWord);
    }

    @Override
    public boolean wordExist(String word) {
        String lowerCaseWord = toLowerCaseIfNotNull(word);
        return super.wordExist(lowerCaseWord);
    }

    @Override
    public void removeWord(String word) {
        String lowerCaseWord = toLowerCaseIfNotNull(word);
        super.removeWord(lowerCaseWord);
    }
    @Override
    public Set<String> search(SearchStrategy searchStrategy, String lookFor) {
        String lowerCaseWord = toLowerCaseIfNotNull(lookFor);
        return super.search(searchStrategy, lowerCaseWord);
    }

    private String toLowerCaseIfNotNull(String word) {
        String lowerCaseWord = word;
        if (word != null) {
            lowerCaseWord = word.toLowerCase();
        }
        return lowerCaseWord;
    }
}
