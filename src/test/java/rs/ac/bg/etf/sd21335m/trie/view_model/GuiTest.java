package rs.ac.bg.etf.sd21335m.trie.view_model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategyType;
import rs.ac.bg.etf.sd21335m.trie.types.ListFormatter;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;
import rs.ac.bg.etf.sd21335m.trie.types.WordInNewLineFormatter;
@ExtendWith({InitConfigManager.class})
class GuiTest {
    protected TrieModelView trieModelView;

    protected Gui gui;
    protected ListFormatter listFormatter;

    @BeforeEach
    void setUp() {
        listFormatter = new WordInNewLineFormatter();
        trieModelView = new TrieModelView(listFormatter);
        gui = new Gui(trieModelView);
    }

    @Test
    void checkIfHaveAllComponents() {
        Assertions.assertNotNull(gui.actionMessage);
        Assertions.assertNotNull(gui.basicTrie);
        Assertions.assertNotNull(gui.executeButton);
        Assertions.assertNotNull(gui.caseInsensitiveTrie);
        Assertions.assertNotNull(gui.exactMatch);
        Assertions.assertNotNull(gui.prefixMatch);
        Assertions.assertNotNull(gui.wildCardMatch);
        Assertions.assertNotNull(gui.insertNewWordAction);
        Assertions.assertNotNull(gui.deleteWordAction);
        Assertions.assertNotNull(gui.searchForWordAction);
        Assertions.assertNotNull(gui.actionMessage);
        Assertions.assertNotNull(gui.resultList);
        Assertions.assertEquals(trieModelView, gui.getTrieModelView());
    }

    @Test
    void checkBasicTrieSelectedOtherNotSelected() {
        Assertions.assertTrue(gui.basicTrie.isSelected());
        Assertions.assertFalse(gui.caseInsensitiveTrie.isSelected());
    }

    @Test
    void checkExactMatchSelectedOtherNotSelected() {
        Assertions.assertTrue(gui.exactMatch.isSelected());
        Assertions.assertFalse(gui.prefixMatch.isSelected());
        Assertions.assertFalse(gui.wildCardMatch.isSelected());
    }

    @Test
    void checkResultListIsNotEditable() {
        Assertions.assertFalse(gui.resultList.isEditable());
    }

    @Test
    void checkInputTextIsEditable() {
        Assertions.assertTrue(gui.inputText.isEditable());
    }

    @Test
    void checkTextEmpty() {
        Assertions.assertEquals("", gui.actionMessage.getText());
        Assertions.assertEquals("", gui.inputText.getText());
        Assertions.assertEquals("", gui.resultList.getText());
    }

    @Test
    void checkMatchStrategyEnabled() {
        Assertions.assertTrue(gui.exactMatch.isEnabled());
        Assertions.assertTrue(gui.prefixMatch.isEnabled());
        Assertions.assertTrue(gui.wildCardMatch.isEnabled());
    }

    @Test
    void checkTriesOptionEnabled() {
        Assertions.assertTrue(gui.basicTrie.isEnabled());
        Assertions.assertTrue(gui.caseInsensitiveTrie.isEnabled());
    }

    @Test
    void checkActionEnabled() {
        Assertions.assertTrue(gui.insertNewWordAction.isEnabled());
        Assertions.assertTrue(gui.searchForWordAction.isEnabled());
        Assertions.assertTrue(gui.deleteWordAction.isEnabled());
    }

    @Test
    void selectPrefixMatchStrategy() {
        gui.prefixMatch.setSelected(true);
        Assertions.assertTrue(gui.prefixMatch.isSelected());
        Assertions.assertFalse(gui.exactMatch.isSelected());
        Assertions.assertFalse(gui.wildCardMatch.isSelected());
        Assertions.assertEquals(MatchStrategyType.PREFIX, trieModelView.getMatchStrategy());
    }

    @Test
    void selectWildMatchStrategy() {
        gui.wildCardMatch.setSelected(true);
        Assertions.assertTrue(gui.wildCardMatch.isSelected());
        Assertions.assertFalse(gui.exactMatch.isSelected());
        Assertions.assertFalse(gui.prefixMatch.isSelected());
        Assertions.assertEquals(MatchStrategyType.WILD_CARD, trieModelView.getMatchStrategy());
    }

    @Test
    void selectExactMatchStrategy() {
        gui.exactMatch.setSelected(true);
        Assertions.assertTrue(gui.exactMatch.isSelected());
        Assertions.assertFalse(gui.wildCardMatch.isSelected());
        Assertions.assertFalse(gui.prefixMatch.isSelected());
        Assertions.assertEquals(MatchStrategyType.EXACT, trieModelView.getMatchStrategy());
    }

    @Test
    void selectBasicTrieType() {
        gui.basicTrie.setSelected(true);
        Assertions.assertTrue(gui.basicTrie.isSelected());
        Assertions.assertFalse(gui.caseInsensitiveTrie.isSelected());
        Assertions.assertEquals(TriType.BASIC, trieModelView.getTrieType());
    }

    @Test
    void selectCaseInsensitiveTrieType() {
        gui.caseInsensitiveTrie.setSelected(true);
        Assertions.assertTrue(gui.caseInsensitiveTrie.isSelected());
        Assertions.assertFalse(gui.basicTrie.isSelected());
        Assertions.assertEquals(TriType.CASE_INSENSITIVE, trieModelView.getTrieType());
    }

    @Test
    void addNewWord() {
        insertNewWord("dusan");
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
        gui.searchForWordAction.setSelected(true);
        gui.executeButton.doClick();
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY.toString(), gui.actionMessage.getText());
        Assertions.assertEquals(trieModelView.makeResultString("dusan"), gui.resultList.getText());
    }

    @Test
    void addTwoWordSearchPrefix() {
        insertNewWord("dusan");
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
        insertNewWord("dus");
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
        gui.prefixMatch.setSelected(true);
        searchForWord("dus");
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY.toString(), gui.actionMessage.getText());
        Assertions.assertEquals(trieModelView.makeResultString("dus"), gui.resultList.getText());
    }

    @Test
    void addTwoWordSearchWild() {
        insertNewWord("dusan");
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
        insertNewWord("dasan");
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
        gui.wildCardMatch.setSelected(true);
        searchForWord("d?s?n");
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY.toString(), gui.actionMessage.getText());
        Assertions.assertEquals(trieModelView.makeResultString("d?s?n"), gui.resultList.getText());
    }

    private void searchForWord(String word) {
        gui.inputText.setText(word);
        gui.searchForWordAction.setSelected(true);
        gui.executeButton.doClick();
    }

    private void insertNewWord(String word) {
        gui.insertNewWordAction.setSelected(true);
        gui.inputText.setText(word);
        gui.executeButton.doClick();
        Assertions.assertEquals(ActionMessage.WORD_SUCCESSFULLY_ADDED.toString(), gui.actionMessage.getText());
    }

    @Test
    void addTwoWordDeleteWild() {
        insertNewWord("dusan");
        insertNewWord("dasan");
        gui.wildCardMatch.setSelected(true);
        deleteWord("d?s?n");
        Assertions.assertEquals(trieModelView.makeResultString("d?s?n"), gui.resultList.getText());
    }

    @Test
    void addTwoWordDeletePrefix() {
        insertNewWord("dusan");
        insertNewWord("du");
        gui.prefixMatch.setSelected(true);
        deleteWord("du");
        String result = trieModelView.makeResultString("du");
        Assertions.assertEquals(result, gui.resultList.getText());
    }

    @Test
    void addTwoWordDeleteExact() {
        insertNewWord("dusan");
        insertNewWord("du");
        gui.exactMatch.setSelected(true);
        deleteWord("du");
        searchForWord("du");
        String result = trieModelView.makeResultString("du");
        Assertions.assertEquals(result, gui.resultList.getText());
    }

    private void deleteWord(String word) {
        gui.inputText.setText(word);
        gui.deleteWordAction.setSelected(true);
        gui.executeButton.doClick();
        Assertions.assertEquals(ActionMessage.DELETE_DONE_SUCCESSFULLY.toString(), gui.actionMessage.getText());
    }

    @Test
    void changeTrieType() {
        gui.caseInsensitiveTrie.setSelected(true);
        insertNewWord("dusan");
        insertNewWord("DU");
        gui.prefixMatch.setSelected(true);
        searchForWord("Du");
        String result = trieModelView.makeResultString("du");
        Assertions.assertEquals(result, gui.resultList.getText());
        Assertions.assertEquals(ActionMessage.SEARCH_DONE_SUCCESSFULLY.toString(), gui.actionMessage.getText());
    }

}
