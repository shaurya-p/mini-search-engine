@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class PageIndex
{
    MyLinkedList <WordEntry> wordEntries;

    public PageIndex()
    {
        wordEntries = new MyLinkedList<WordEntry>();
    }

    public void addPositionForWord(String str, Position p)
    {
        ListNode<WordEntry> curr = wordEntries.getFront();
        while(curr!=null)
        {
            if(curr.getValue().getWord().equals(str))
            {
                curr.getValue().addPosition(p);
                return;
            }
            curr = curr.getNext();
        }
        WordEntry word = new WordEntry(str);
        word.addPosition(p);
        wordEntries.insertRear(word);
    }

    public MyLinkedList<WordEntry> getWordEntries()
    {
        return wordEntries;
    }

    public WordEntry findWord(String str) throws WordNotFoundException
    {
        ListNode<WordEntry> curr = wordEntries.getFront();
        while(curr!=null)
        {
            if(curr.getValue().getWord().equals(str))
                return curr.getValue();
            curr = curr.getNext();
        }
        throw new WordNotFoundException("The word '" + str + "' was not found");
    }

    public int noOfWords()
    {
        ListNode<WordEntry> curr = wordEntries.getFront();
        int number = 0;
        while(curr!=null)
        {
            number+= curr.getValue().getAllPositionsForThisWord().getSize();
            curr = curr.getNext();
        }
        return number;
    }
}
