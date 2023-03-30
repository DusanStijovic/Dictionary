package rs.ac.bg.etf.sd21335m.trie.view_model;

import rs.ac.bg.etf.sd21335m.trie.match_strategy.*;
import rs.ac.bg.etf.sd21335m.trie.exception.IllegalWordException;
import rs.ac.bg.etf.sd21335m.trie.exception.WordAlreadyExist;
import rs.ac.bg.etf.sd21335m.trie.types.BasicTrie;
import rs.ac.bg.etf.sd21335m.trie.types.CaseInsensitiveTrie;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;
import rs.ac.bg.etf.sd21335m.trie.types.Trie;

import java.util.Objects;
import java.util.Set;

public class TrieModelView {

    private Trie trie;
    private ActionMessage lastActionMessage;
    private MatchStrategy matchStrategy;

    public TrieModelView() {
        setTrieType(TriType.BASIC);
        chooseMatchStrategy(MatchStrategyType.EXACT);
        lastActionMessage = ActionMessage.NO_ACTION;
    }

    public TriType getTrieType() {
        return trie.getType();
    }

    public void setTrieType(TriType trieType) {
        if (Objects.requireNonNull(trieType) == TriType.BASIC) {
            trie = new BasicTrie();
        } else if (trieType == TriType.CASE_INSENSITIVE) {
            trie = new CaseInsensitiveTrie();
        }
    }

    public void addWord(String word) {
        try {
            trie.addNewWord(word);
            lastActionMessage = ActionMessage.WORD_SUCCESSFULLY_ADDED;
        } catch (WordAlreadyExist wordAlreadyExist) {
            lastActionMessage = ActionMessage.WORD_ALREADY_EXIST;
        } catch (IllegalWordException illegalWordException) {
            lastActionMessage = ActionMessage.ILLEGAL_WORD;
        }
    }

    public boolean wordExist(String word) {
        return trie.wordExist(word);
    }

    public ActionMessage getLastActionMessage() {
        return lastActionMessage;
    }

    public void chooseMatchStrategy(MatchStrategyType matchStrategyType) {
        switch (matchStrategyType) {
            case EXACT -> matchStrategy = new ExplicitMatchStrategy();
            case PREFIX -> matchStrategy = new PrefixMatchStrategy();
            case WILD_CARD -> matchStrategy = new WildCardMatchStrategy();
        }
    }

    public MatchStrategyType getMatchStrategy() {
        return matchStrategy.getType();
    }

    public boolean isEmpty() {
        return trie.isEmpty();
    }

    public Set<String> searchByStrategy(String word) {
        Set<String> result = trie.searchByStrategy(matchStrategy, word);
        lastActionMessage = ActionMessage.SEARCH_DONE_SUCCESSFULLY;
        return result;
    }

    public String makeResultString(String word) {
        Set<String> results = searchByStrategy(word);
        StringBuilder stringBuilder = new StringBuilder();
        for (String result : results) {
            stringBuilder.append(result);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void deleteByStrategy(String word) {
        trie.removeByStrategy(matchStrategy, word);
        lastActionMessage = ActionMessage.DELETE_DONE_SUCCESSFULLY;
    }
}
