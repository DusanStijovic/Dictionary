package rs.ac.bg.etf.sd21335m.trie.view_model;

import com.formdev.flatlaf.FlatDarkLaf;
import rs.ac.bg.etf.sd21335m.trie.config.ConfigurationManager;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategyType;
import rs.ac.bg.etf.sd21335m.trie.types.TriType;
import rs.ac.bg.etf.sd21335m.trie.types.WordInNewLineFormatter;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class Gui {

    private final TrieModelView trieModelView;
    JTextArea resultList;
    JRadioButton insertNewWordAction;
    JRadioButton deleteWordAction;
    JButton executeButton;
    JRadioButton searchForWordAction;
    JRadioButton basicTrie;
    JRadioButton caseInsensitiveTrie;
    JRadioButton exactMatch;
    JRadioButton prefixMatch;
    JRadioButton wildCardMatch;
    JLabel actionMessage;
    JTextField inputText;
    private JPanel panel1;

    public Gui(TrieModelView trieModelView) {
        this.trieModelView = trieModelView;
        executeButton.addActionListener(e -> {
            resultList.setText("");
            if (insertNewWordAction.isSelected()) {
                trieModelView.addWord(inputText.getText());
            }
            if (searchForWordAction.isSelected()) {
                String text = trieModelView.makeResultString(inputText.getText());
                resultList.setText(text);
            }
            if (deleteWordAction.isSelected()) {
                trieModelView.deleteByStrategy(inputText.getText());
            }
            actionMessage.setText(trieModelView.getLastActionMessage().toString());
        });

        exactMatch.addItemListener(e -> setMatchStrategyIfSelectedEvent(e, MatchStrategyType.EXACT));
        prefixMatch.addItemListener(e -> setMatchStrategyIfSelectedEvent(e, MatchStrategyType.PREFIX));
        wildCardMatch.addItemListener(e -> setMatchStrategyIfSelectedEvent(e, MatchStrategyType.WILD_CARD));

        basicTrie.addItemListener(e -> setTrieTypeIfSelectedEvent(e, TriType.BASIC));
        caseInsensitiveTrie.addItemListener(e -> setTrieTypeIfSelectedEvent(e, TriType.CASE_INSENSITIVE));
    }

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        ConfigurationManager.init(args[0]);
        JFrame frame = new JFrame("Gui");
        frame.setContentPane(new Gui(new TrieModelView(new WordInNewLineFormatter())).panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setMatchStrategyIfSelectedEvent(ItemEvent e, MatchStrategyType wildCard) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            trieModelView.chooseMatchStrategy(wildCard);
        }
    }

    private void setTrieTypeIfSelectedEvent(ItemEvent e, TriType caseInsensitive) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            trieModelView.setTrieType(caseInsensitive);
        }
    }

    public TrieModelView getTrieModelView() {
        return trieModelView;
    }
}
