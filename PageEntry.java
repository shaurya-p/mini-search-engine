import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class PageEntry {
    PageIndex page;
    String pageName;

    public PageEntry(String pageName) throws IOException {
        this.pageName = pageName;
        BufferedReader br = new BufferedReader(new FileReader("webpages/" + pageName));
        String line;
        final char punctuation[] = { '{', '}', '[', ']', '<', '>', '=', '(', ')', '.', ',', ';', '\'', '"', '?', '#',
                '!', '-', ':' };
        final String connectors[] = { "a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or",
                "and", "does", "will", "whose" };
        final String plurals[][] = { { "stack", "stacks" }, { "structure", "structures" },
                { "application", "applications" } };
        page = new PageIndex();
        int running = 0, offset = 0;
        while ((line = br.readLine()) != null) {
            for (int i = 0; i < punctuation.length; i++)
                line = line.replace(punctuation[i], ' ');
            for (int i = 0; i < plurals.length; i++)
                line = line.replaceAll(plurals[i][1], plurals[i][0]);

            String words[] = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                boolean isConnector = false;
                if (words[i].isEmpty())
                    offset++;
                for (int j = 0; j < connectors.length; j++) {
                    if (words[i].toLowerCase().equals(connectors[j])) {
                        isConnector = true;
                        break;
                    }
                }
                if (isConnector)
                    continue;
                Position p = new Position(this, running + i + 1 - offset);
                page.addPositionForWord(words[i].toLowerCase(), p);
            }
            running += words.length;
        }
    }

    public String getPageName() {
        return pageName;
    }

    public PageIndex getPageIndex() {
        return page;
    }

    public int getPhraseOccurences(String str[]) {
        int occurences = 0;
        WordEntry firstWord;
        try {
            firstWord = page.findWord(str[0]);
        } catch (WordNotFoundException e) {
            return 0;
        }
        ListNode<Position> currPosition = firstWord.getAllPositionsForThisWord().getFront();
        while (currPosition != null) {
            boolean isConsecutive = true;
            int k = currPosition.getValue().getWordIndex();
            for (int i = 1; i < str.length; i++) {
                WordEntry secondWord;
                try {
                    secondWord = page.findWord(str[i]);
                } catch (WordNotFoundException e) {
                    return 0;
                }
                int m = secondWord.getXPlus(k);
                ListNode<WordEntry> pageWord = page.getWordEntries().getFront();
                while (pageWord != null) {
                    int n;
                    try {
                        n = page.findWord(pageWord.getValue().getWord()).getXMinus(m);

                        if (k < n && n < m) {
                            isConsecutive = false;
                            break;
                        }
                    } catch (WordNotFoundException e) {

                    }
                
                    pageWord = pageWord.getNext();
                
                }
                if (!isConsecutive)
                    break;
            }
            if (isConsecutive)
                occurences++;
            currPosition = currPosition.getNext();
        }
        return occurences;
    }

    public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase, InvertedPageIndex pageIndex) {
        float relevance = 0;
        if (!doTheseWordsRepresentAPhrase) {
            for (int i = 0; i < str.length; i++) {
                try {
                    WordEntry w = page.findWord(str[i]);
                    int m = w.getTermOccurences(this);
                    // System.out.println("In webpage " + pageName);
                    // System.out.println("m is " + m);
                    // System.out.println("no of words is " + page.noOfWords());
                    // System.out.println("no of webpages is " + pageIndex.getPageList().getSize());
                    // System.out.println("no of webpages with word " + pageIndex.getPagesWhichContainWord(str[i]).getSize());
                    float tfw = (float)  m/ page.noOfWords();
                    // float idfw = (float) Math.log((float) pageIndex.getPageList().getSize())/(float)Math.log(pageIndex.getPagesWhichContainWord(str[i]).getSize());
                    float idfw = (float) Math.log((float) pageIndex.getPageList().getSize()/pageIndex.getPagesWhichContainWord(str[i]).getSize());
                    relevance += tfw * idfw;
                    // System.out.println("tfw " + tfw);
                    // System.out.println("idfw " + idfw);
                    // System.out.println("relevance " + relevance);
                } catch (WordNotFoundException e) {

                }
            }
        } else {
            int m = getPhraseOccurences(str);
            float tfp = (float)m/(page.noOfWords() - (str.length-1) * m);
            // float idfp = (float) Math.log((float)pageIndex.getPageList().getSize())/(float)Math.log(pageIndex.getPagesWhichContainPhrase(str).getSize());
            float idfp = (float) Math.log((float)pageIndex.getPageList().getSize()/pageIndex.getPagesWhichContainPhrase(str).getSize());
            
            relevance = tfp * idfp;
        }
        return relevance;
    }
}
