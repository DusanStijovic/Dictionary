package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TrieNodeTest {

    private TrieNode trieNode;

    @BeforeEach
    public void setUp() {
        trieNode = TrieNode.createNonWordTrieNode();
    }

    @Test
    public void nonTrieNodeCreation() {
        Assertions.assertFalse(trieNode.isWordTrieNode());
    }

    @Test
    public void wordTrieNodeCreation() {
        TrieNode wordTrieNode = TrieNode.createWordTrieNode();
        Assertions.assertTrue(wordTrieNode.isWordTrieNode());
    }

    @Test
    public void testAddOneChildAndFindIt() {
        addOneChildAndSearchForIt('d');
    }

    @Test
    public void testAddOneChildFindItThenAddAnotherFindIt() {
        addOneChildAndSearchForIt('f');
        addOneChildAndSearchForIt('s');
    }

    private void addOneChildAndSearchForIt(char character) {
        TrieNode childAdded = addChildWithCharacterAndReturnIt(character);
        checkIfChildInTreeWithCharacter(childAdded, character);
    }

    private void checkIfChildInTreeWithCharacter(TrieNode child, char character) {
        Optional<TrieNode> childRetrieved = trieNode.getChild(character);
        Assertions.assertTrue(childRetrieved.isPresent());
        Assertions.assertEquals(child, childRetrieved.get());
    }

    @Test
    public void testAddTwoChildsSearchForBoth() {
        TrieNode firstChild = addChildWithCharacterAndReturnIt('f');
        TrieNode secondChild = addChildWithCharacterAndReturnIt('s');
        checkIfChildInTreeWithCharacter(firstChild, 'f');
        checkIfChildInTreeWithCharacter(secondChild, 's');
    }

    private TrieNode addChildWithCharacterAndReturnIt(char character) {
        TrieNode childAdded = TrieNode.createNonWordTrieNode();
        trieNode.addChild(character, childAdded);
        return childAdded;
    }

    @Test
    public void getNonExistingChild() {
        Optional<TrieNode> child = trieNode.getChild('a');
        Assertions.assertTrue(child.isEmpty());
    }

    @Test
    public void addExistingChild() {
        trieNode.addChild('c', TrieNode.createNonWordTrieNode());
        Assertions.assertThrows(ChildWithCharacterExist.class, () -> trieNode.addChild('c', TrieNode.createNonWordTrieNode()));
    }

    @Test
    public void addNullChild() {
        Assertions.assertThrows(AddingNullChildException.class, () -> trieNode.addChild('n', null));
    }

    @Test
    public void removeChildNotExistInTree() {
        TrieNode addedTrieNode = addChildWithCharacterAndReturnIt('c');
        checkIfChildInTreeWithCharacter(addedTrieNode, 'c');
        trieNode.removeChild('c');
        Optional<TrieNode> retrievedChild = trieNode.getChild('c');
        Assertions.assertTrue(retrievedChild.isEmpty());
    }

    @Test
    public void removeNonExistingChild() {
        Assertions.assertThrows(ChildWithCharacterDoesNotExist.class, () -> trieNode.removeChild('n'));
    }

}
