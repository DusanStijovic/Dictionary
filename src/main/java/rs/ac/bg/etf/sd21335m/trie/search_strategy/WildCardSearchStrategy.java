package rs.ac.bg.etf.sd21335m.trie.search_strategy;

import rs.ac.bg.etf.sd21335m.trie.TrieNode;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class WildCardSearchStrategy implements SearchStrategy{
    @Override
    public Set<String> search(TrieNode trieNode, String wildcard) {
        Set<String> results = new HashSet<>();
        searchHelper(wildcard.toCharArray(), 0, trieNode, new StringBuilder(), results);
        return results;
    }

    private void searchHelper(char[] query, int index, TrieNode node, StringBuilder currentWord, Set<String> results) {
        if (index == query.length) {
            if (node.isWordTrieNode()) {
                results.add(currentWord.toString());
            }
            return;
        }
        char c = query[index];
        if (c == '?') {
            for (TrieNode child : node.getChildren()) {
                Optional<Character> optionalCharacter = child.getCharacter();
                if (optionalCharacter.isPresent()) {
                    currentWord.append(optionalCharacter.get());
                    searchHelper(query, index + 1, child, currentWord, results);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }
        } else {
            Optional<TrieNode> childOptional = node.getChild(c);
            if (childOptional.isPresent()) {
                currentWord.append(c);
                searchHelper(query, index + 1, childOptional.get(), currentWord, results);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }
}
