package rs.ac.bg.etf.sd21335m.trie.aspects;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.MatchStrategy;
import rs.ac.bg.etf.sd21335m.trie.match_strategy.PrefixMatchStrategy;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrieLoggingAspectTest {

    private final TrieLoggingAspect aspect = new TrieLoggingAspect();
    @Mock
    private JoinPoint joinPoint;
    @Mock
    private Logger logger;

    @Test
    void logSearchedWords() {
        MatchStrategy matchStrategy = Mockito.mock(PrefixMatchStrategy.class);
        HashSet<String> searchResult = new HashSet<>(List.of("dusan"));
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{matchStrategy, "dusan"});
        aspect.logSearchedWords(joinPoint, searchResult);
    }

    @Test
    void logDeletedWords() {
        MatchStrategy matchStrategy = Mockito.mock(PrefixMatchStrategy.class);
        joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{matchStrategy, "dusan"});
        aspect.logDeletedWords(joinPoint);
    }

    @Test
    void logAddedWord() {
        joinPoint = Mockito.mock(JoinPoint.class);
        Mockito.when(joinPoint.getArgs()).thenReturn(new Object[]{"dusan"});
        aspect.logAddedWord(joinPoint);
    }
}