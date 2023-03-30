package rs.ac.bg.etf.sd21335m.trie.types;

import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategy;

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
    public Set<String> searchByStrategy(MatchStrategy matchStrategy, String lookFor) {
        String lowerCaseWord = toLowerCaseIfNotNull(lookFor);
        return super.searchByStrategy(matchStrategy, lowerCaseWord);
    }
    @Override
    public void removeByStrategy(MatchStrategy matchStrategy, String query) {
        String lowerCaseWord = toLowerCaseIfNotNull(query);
        matchStrategy.delete(root, lowerCaseWord);
    }
    private String toLowerCaseIfNotNull(String word) {
        String lowerCaseWord = word;
        if (word != null) {
            lowerCaseWord = word.toLowerCase();
        }
        return lowerCaseWord;
    }

    @Override
    public TriType getType() {
        return TriType.CASE_INSENSITIVE;
    }
}
