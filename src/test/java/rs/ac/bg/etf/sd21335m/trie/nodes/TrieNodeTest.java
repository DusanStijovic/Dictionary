package rs.ac.bg.etf.sd21335m.trie.nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.bg.etf.sd21335m.trie.TrieNode;
import rs.ac.bg.etf.sd21335m.trie.exception.ChildWithCharacterDoesNotExist;
import rs.ac.bg.etf.sd21335m.trie.exception.ChildWithCharacterExist;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class TrieNodeTest {

    protected TrieNode trieNode;

    @BeforeEach
    public void setUp() {
        trieNode = createTrieNodeForTesting();
    }

    protected abstract TrieNode createTrieNodeForTesting();

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
        TrieNode childAdded = trieNode.createNonWordChildAndReturnIt(character);
        checkIfChildInTreeWithCharacter(childAdded, character);
    }

    private void checkIfChildInTreeWithCharacter(TrieNode child, char character) {
        Optional<TrieNode> childRetrieved = trieNode.getChild(character);
        Assertions.assertTrue(childRetrieved.isPresent());
        Assertions.assertEquals(child, childRetrieved.get());
    }

    @Test
    public void testAddTwoChildrenSearchForBoth() {
        TrieNode firstChild = trieNode.createNonWordChildAndReturnIt('f');
        TrieNode secondChild = trieNode.createNonWordChildAndReturnIt('s');
        checkIfChildInTreeWithCharacter(firstChild, 'f');
        checkIfChildInTreeWithCharacter(secondChild, 's');
    }

    @Test
    public void getNonExistingChild() {
        Optional<TrieNode> child = trieNode.getChild('a');
        Assertions.assertTrue(child.isEmpty());
    }

    @Test
    public void addExistingChild() {
        trieNode.createNonWordChildAndReturnIt('c');
        Assertions.assertThrows(ChildWithCharacterExist.class, () -> trieNode.createNonWordChildAndReturnIt('c'));
    }

    @Test
    public void removeChildNotExistInTree() {
        TrieNode addedTrieNode = trieNode.createNonWordChildAndReturnIt('c');
        checkIfChildInTreeWithCharacter(addedTrieNode, 'c');
        trieNode.removeChild('c');
        Optional<TrieNode> retrievedChild = trieNode.getChild('c');
        Assertions.assertTrue(retrievedChild.isEmpty());
    }

    @Test
    public void removeNonExistingChild() {
        Assertions.assertThrows(ChildWithCharacterDoesNotExist.class, () -> trieNode.removeChild('n'));
    }

    @Test
    public void checkIfChildWithCharExist() {
        trieNode.createNonWordChildAndReturnIt('d');
        Assertions.assertTrue(trieNode.hasChild('d'));
    }

    @Test
    public void checkIfChildWithCharDoesNotExist() {
        trieNode.createNonWordChildAndReturnIt('d');
        Assertions.assertFalse(trieNode.hasChild('a'));
    }

    @Test
    public void checkIfSetToWordNode() {
        trieNode.setWordTrieNode(true);
        Assertions.assertTrue(trieNode.isWordTrieNode());
    }

    @Test
    public void checkIfSetToNonWordNode() {
        trieNode.setWordTrieNode(false);
        Assertions.assertFalse(trieNode.isWordTrieNode());
    }

    @Test
    public void testAddChildrenThanCheckIfHasChildren() {
        trieNode.createNonWordChildAndReturnIt('d');
        Assertions.assertTrue(trieNode.hasSomeChild());
    }

    @Test
    public void testIfDoesNotHaveChildrenWhenEmpty() {
        Assertions.assertFalse(trieNode.hasSomeChild());
    }

    @Test
    public void testGetParent() {
        TrieNode newTrieNode = trieNode.createNonWordChildAndReturnIt('d');
        Optional<TrieNode> newTrieNodeParent = newTrieNode.getParent();
        Assertions.assertTrue(newTrieNodeParent.isPresent());
        Assertions.assertEquals(trieNode, newTrieNodeParent.get());
    }

    @Test
    public void testGetParentAfterRemove() {
        TrieNode newTrieNode = trieNode.createNonWordChildAndReturnIt('d');
        trieNode.removeChild('d');
        Optional<TrieNode> newTrieNodeParent = newTrieNode.getParent();
        Assertions.assertFalse(newTrieNodeParent.isPresent());
    }


    @Test
    public void checkValueOfNode() {
        TrieNode newTrieNode = trieNode.createNonWordChildAndReturnIt('d');
        Optional<Character> trieNodeChar = newTrieNode.getCharacter();
        Assertions.assertTrue(trieNodeChar.isPresent());
        Assertions.assertEquals('d', trieNodeChar.get());
    }

    @Test
    public void checkValueOfNonAddedWord() {
        TrieNode newTrieNode = createTrieNodeForTesting();
        Assertions.assertTrue(newTrieNode.getCharacter().isEmpty());
    }

    @Test
    public void getNumberOfNodesEmptyNode() {
        Assertions.assertEquals(1, trieNode.getNumberOfNodes());
    }

    @Test
    public void getNumberOfNodesOneChild() {
        trieNode.createNonWordChildAndReturnIt('c');
        Assertions.assertEquals(2, trieNode.getNumberOfNodes());
    }

    @Test
    public void getNumberOfNodesTwoChild() {
        trieNode.createNonWordChildAndReturnIt('c');
        trieNode.createNonWordChildAndReturnIt('a');
        Assertions.assertEquals(3, trieNode.getNumberOfNodes());
    }


    @Test
    public void getNumberOfNodesChildHasChild() {
        TrieNode child = trieNode.createNonWordChildAndReturnIt('c');
        child.createNonWordChildAndReturnIt('a');
        Assertions.assertEquals(3, trieNode.getNumberOfNodes());
    }

    @Test
    public void getNodeFirstChildCharacters() {
        TrieNode aNode = trieNode.createNonWordChildAndReturnIt('a');
        TrieNode bNode = trieNode.createNonWordChildAndReturnIt('b');
        TrieNode cNode = trieNode.createNonWordChildAndReturnIt('c');
        Set<TrieNode> expected = new HashSet<>(Arrays.asList(aNode, bNode, cNode));
        Assertions.assertEquals(expected, trieNode.getChildren());
    }
}
