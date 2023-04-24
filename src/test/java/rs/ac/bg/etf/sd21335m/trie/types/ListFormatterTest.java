package rs.ac.bg.etf.sd21335m.trie.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rs.ac.bg.etf.sd21335m.trie.InitConfigManager;

import java.util.Collections;
import java.util.List;

@ExtendWith({InitConfigManager.class})
class ListFormatterTest {


    private ListFormatter listFormatter;

    @BeforeEach
    void setUP() {
        listFormatter = new WordInNewLineFormatter();
    }

    @Test
    void newWordsEmptyString(){
        String formattedList = listFormatter.format(Collections.emptyList());
        Assertions.assertTrue(formattedList.isEmpty());
    }

    @Test
    void oneWord(){
        List<String> listToFormat = List.of("dusan");
        checkListFormatting(listToFormat, "dusan");
    }

    @Test
    void twoWords(){
        List<String> listToFormat = List.of("marko", "petar");
        checkListFormatting(listToFormat, "marko\npetar");
    }

    @Test
    void threeWords(){
        List<String> listToFormat = List.of("marko", "petar", "vuk");
        checkListFormatting(listToFormat, "marko\npetar\nvuk");
    }

    private void checkListFormatting(List<String> listToFormat, String expected) {
        String formattedList = listFormatter.format(listToFormat);
        Assertions.assertFalse(formattedList.isEmpty());
        Assertions.assertEquals(expected, formattedList);
    }
}
