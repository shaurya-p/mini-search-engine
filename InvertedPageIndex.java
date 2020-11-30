@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class InvertedPageIndex 
{
    private MyHashTable wordTable;
    private MySet<PageEntry> pageList;

    public InvertedPageIndex() {
        wordTable = new MyHashTable();
        pageList = new MySet<PageEntry>();
    }

    public void addPage(PageEntry p) {
        MyLinkedList<WordEntry> words = p.getPageIndex().getWordEntries();
        ListNode<WordEntry> curr = words.getFront();
        while (curr != null) {
            wordTable.addPositionsForWord(curr.getValue());
            curr = curr.getNext();
        }
        pageList.Insert(p);
    }

    public MySet<PageEntry> getPagesWhichContainWord(String str) throws WordNotFoundException {
        MySet<PageEntry> pages = new MySet<PageEntry>();
        WordEntry wordEntry = wordTable.getEntry(str);
        MyLinkedList<Position> positions = wordEntry.getAllPositionsForThisWord();
        ListNode<Position> curr = positions.getFront();
        while (curr != null) {
            pages.Insert(curr.getValue().getPageEntry());
            curr = curr.getNext();
        }
        return pages;
    }

    public MySet<PageEntry> getPagesWhichContainPhrase(String str[])
    {
        if (str.length == 0)
            return null; 
        
        MySet <PageEntry> returnPages = new MySet<PageEntry>();
        MySet <PageEntry> candidatePages;
        try
        {
            candidatePages = getPagesWhichContainWord(str[0]);
        }
        catch(WordNotFoundException e)
        {
            return returnPages;
        }
        ListNode<PageEntry> currPage = candidatePages.getReadOnlyList().getFront();
        while(currPage!=null)
        {
            int currPageOccurence = currPage.getValue().getPhraseOccurences(str);
            if(currPageOccurence>0)
                returnPages.Insert(currPage.getValue());
            currPage = currPage.getNext();
        }
        return returnPages;
    }
        /*
        while(curr!=null)
        {
            MyLinkedList<WordEntry> words= curr.getValue().getPageIndex().getWordEntries();
            ListNode<WordEntry> currWord = words.getFront();
            while(currWord!=null)
            {
                boolean found = true;
                for(int i = 0; i < str.length; i++)
                {
                    if(str[i].equals(currWord.getValue().getWord()))
                        currWord = currWord.getNext();
                    else
                    {
                        found = false;

                    }
                }
            }
        }*/
        /*
        MySet<PageEntry> returnSet = new MySet();
        WordEntry firstWord = wordTable.getEntry(str[0]);
        MyLinkedList<Position> listOfPositions = firstWord.getAllPositionsForThisWord();
        ListNode<Position> curr = listOfPositions.getFront();
        if(str.length == 1) 
            return getPagesWhichContainWord(str[0]);
        while(curr != null)
        {
            boolean found = false;
            for (int i = 1; i < str.length; i++) 
            {
                if (curr == null) 
                {
                    found = false;
                    break;
                }
                WordEntry currWord = wordTable.getEntry(str[i]);
                Position p = currWord.find(curr.getValue().getWordIndex() + i, curr.getValue().getPageEntry());
                if (p == null) 
                {
                    curr = curr.getNext();
                    i = 0;
                    found = false;
                    continue;
                } 
                else 
                {
                    found = true;
                }
            }
            if(found)
            {
                returnSet.Insert(curr.getValue().getPageEntry());
                curr = curr.getNext();
            }
        }
        if(returnSet.IsEmpty())
            return null;
        else return returnSet; */
    public MySet<PageEntry> getPageList()
    {
        return pageList;
    }

}
