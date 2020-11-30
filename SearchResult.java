@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class SearchResult implements Comparable<Object>
{
    PageEntry page;
    float relevance;

    public SearchResult(PageEntry p, float r)
    {
        page = p;
        relevance = r;
    }

    public PageEntry getPageEntry()
    {
        return page;
    }

    public float getRelevance()
    {
        return relevance;
    }

    public int compareTo(Object otherObject)
    {
        return (relevance - ((SearchResult)(otherObject)).getRelevance() > 0)? 1 : -1;
    }
}
