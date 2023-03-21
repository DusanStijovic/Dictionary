package rs.ac.bg.etf.sd21335m.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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

    @Test
    public void checkIfChildWithCharExist() {
        trieNode.addChild('d', createTrieNodeForTesting());
        Assertions.assertTrue(trieNode.hasChild('d'));
    }

    @Test
    public void checkIfChildWithCharDoesNotExist() {
        trieNode.addChild('d', createTrieNodeForTesting());
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
        trieNode.addChild('d', createTrieNodeForTesting());
        Assertions.assertTrue(trieNode.hasSomeChild());
    }

    @Test
    public void testIfDoesNotHaveChildrenWhenEmpty() {
        Assertions.assertFalse(trieNode.hasSomeChild());
    }

    @Test
    public void testGetParent() {
        TrieNode newTrieNode = createTrieNodeForTesting();
        trieNode.addChild('d', newTrieNode);
        Optional<TrieNode> newTrieNodeParent = newTrieNode.getParent();
        Assertions.assertTrue(newTrieNodeParent.isPresent());
        Assertions.assertEquals(trieNode, newTrieNodeParent.get());
    }

    @Test
    public void testGetParentAfterRemove() {
        TrieNode newTrieNode = createTrieNodeForTesting();
        trieNode.addChild('d', newTrieNode);
        trieNode.removeChild('d');
        Optional<TrieNode> newTrieNodeParent = newTrieNode.getParent();
        Assertions.assertFalse(newTrieNodeParent.isPresent());
    }


    @Test
    public void checkValueOfNode() {
        TrieNode newTrieNode = createTrieNodeForTesting();
        trieNode.addChild('d', newTrieNode);
        Optional<Character> trieNodeChar =  newTrieNode.getCharacter();
        Assertions.assertTrue(trieNodeChar.isPresent());
        Assertions.assertEquals('d', newTrieNode.getCharacter().get());
    }

    @Test
    public void checkValueOfNonAddedWord() {
        TrieNode newTrieNode = createTrieNodeForTesting();
        Assertions.assertTrue(newTrieNode.getCharacter().isEmpty());
    }

}
