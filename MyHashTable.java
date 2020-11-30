@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class MyHashTable
{
    // we implement the hash table using an array of linked lists
    private final int SIZE = 1000; // roughly 1000 words for now
    private MyLinkedList<WordEntry> [] entries;

    public MyHashTable()
    {
        entries = new MyLinkedList[SIZE];
    }

    private int getHashIndex(String str)
    {
        return Math.abs(str.hashCode()) % SIZE;
    }

    public void addPositionsForWord(WordEntry w)
    {
        int index = getHashIndex(w.getWord());
        if (entries[index] == null)
            entries[index] = new MyLinkedList<WordEntry>();
        ListNode<WordEntry> curr = entries[index].getFront();
        while (curr != null)
        {
            if (curr.getValue().getWord().equals(w.getWord()))
            {
                curr.getValue().addPositions(w.getAllPositionsForThisWord());
                return;
            }
            curr = curr.getNext();
        }
        WordEntry temp = new WordEntry(w.getWord());
        temp.addPositions(w.getAllPositionsForThisWord());
        entries[index].insertRear(temp);

    }

    public WordEntry getEntry(String word) throws WordNotFoundException
    {
        MyLinkedList<WordEntry> wordList = entries[getHashIndex(word)];
        if (wordList != null)
        {
            ListNode<WordEntry> curr = wordList.getFront();
            while (curr != null)
            {
                if (curr.getValue().getWord().equals(word))
                    return curr.getValue();
                curr = curr.getNext();
            }
        }
        throw new WordNotFoundException("The word '" + word + "' was not found");
    }
}


