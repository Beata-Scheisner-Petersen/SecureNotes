package securenotes.repository.interfaces;

import java.util.List;
public interface IBannedWordRepository {
    List<String> getBannedWords ();
    Boolean addWord(String word);
    Boolean deleteWord(String word);
}
