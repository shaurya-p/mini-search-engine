@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class Position implements Comparable
{
    PageEntry page;
    int wordPosition;

    public Position(PageEntry p, int wordIndex)
    {
        page = p; //assigning pointers
        wordPosition = wordIndex;
    }

    public PageEntry getPageEntry()
    {
        return page;
    }

    public int getWordIndex()
    {
        return wordPosition;
    }

    public int compareTo(Object p)
    {
        return wordPosition - ((Position)(p)).getWordIndex();
    }
}
