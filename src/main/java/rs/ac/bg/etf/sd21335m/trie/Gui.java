package rs.ac.bg.etf.sd21335m.trie;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Gui {
    private JPanel panel1;
    private JTextArea resultList;
    private JFormattedTextField inputWord;
    private JRadioButton insertNewWordAction;
    private JRadioButton deleteWordAction;
    private JButton executeButton;
    private JRadioButton searchForWordAction;
    private JRadioButton basicTrie;
    private JRadioButton caseInsensitiveTrie;
    private JRadioButton wildCardTrie;
    private JRadioButton prefixRadioButton;
    private JRadioButton wildCardRadioButton;
    private JLabel actionMessage;

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        JFrame frame = new JFrame("Gui");
        frame.setContentPane(new Gui().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
