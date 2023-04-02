package rs.ac.bg.etf.sd21335m.trie.types;

import java.util.List;
import java.util.StringJoiner;

public class WordInNewLineFormatter implements ListFormatter {
    @Override
    public String format(List<String> objects) {
        StringJoiner joiner = new StringJoiner("\n");
        objects.forEach(joiner::add);
        return joiner.toString();
    }
}
