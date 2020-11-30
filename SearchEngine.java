import java.io.IOException;
import java.util.ArrayList;
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class SearchEngine {
    InvertedPageIndex invPageIndex;
    final String plurals[][] = { { "stack", "stacks" }, { "structure", "structures" },
    { "application", "applications" } };

    public SearchEngine() {
        invPageIndex = new InvertedPageIndex();
    }

    private String plural(String word)
    {
        String returnWord = word;
        for(int i = 0; i < plurals.length; i++)
        {
            if(returnWord.equals(plurals[i][1]))
                returnWord = plurals[i][0];
        }
        return returnWord;
    }

    public void performAction(String actionMessage) throws IOException {
        String input[] = actionMessage.split(" ");
        if (input[0].equals("addPage")) {
            try {
                invPageIndex.addPage(new PageEntry(input[1]));
            } catch (IOException e) {
                System.out.println("Webpage " + input[1] + " does not exist");
            }
        } else if (input[0].equals("queryFindPagesWhichContainWord")) {
            try {

                MySet<PageEntry> pages = invPageIndex.getPagesWhichContainWord(plural(input[1].toLowerCase()));
                MySet<Comparable> searches = new MySet<Comparable>();
                ListNode<PageEntry> page = pages.getReadOnlyList().getFront();
                while (page != null) {
                    String query[] = new String[1];
                    query[0] = plural(input[1]);
                    searches.Insert(new SearchResult(page.getValue(),
                            page.getValue().getRelevanceOfPage(query, false, invPageIndex)));
                    page = page.getNext();
                }
                ArrayList<Comparable> results = MySort.sortThisList(searches);
                for (int i = 0; i < results.size(); i++) {
                    System.out.print(((SearchResult) (results.get(i))).getPageEntry().getPageName() + " ");
                    System.out.print("(Relevance is " + ((SearchResult) (results.get(i))).getRelevance() + " ) ");
                }
                System.out.println();
                // MyLinkedList<PageEntry> pageList = pages.getReadOnlyList();
                // ListNode<PageEntry> curr = pageList.getFront();
                // while (curr != null && curr.getNext() != null) {
                //     System.out.print(curr.getValue().getPageName() + ", ");
                //     curr = curr.getNext();
                // }
                
                // if (curr != null)
                //     System.out.print(curr.getValue().getPageName());
                // System.out.println();
            } catch (WordNotFoundException e) {
                System.out.println("No webpage contains word " + input[1]);
                return;
            }
        } else if (input[0].equals("queryFindPositionsOfWordInAPage")) {
            try {
                MySet<PageEntry> pageSet = invPageIndex.getPagesWhichContainWord(plural(input[1]));
                MyLinkedList<PageEntry> pages = pageSet.getReadOnlyList();
                ListNode<PageEntry> curr = pages.getFront();
                while (curr != null) {
                    if (curr.getValue().getPageName().equals(input[2])) {
                        WordEntry w = curr.getValue().getPageIndex().findWord(plural(input[1]));
                        MyLinkedList<Position> positions = w.getAllPositionsForThisWord();
                        ListNode<Position> pos = positions.getFront();
                        while (pos != null && pos.getNext() != null) {
                            if (pos.getValue().getPageEntry().getPageName().equals(input[2]))
                                System.out.print(pos.getValue().getWordIndex() + " ");
                            pos = pos.getNext();
                        }
                        if (pos != null && pos.getValue().getPageEntry().getPageName().equals(input[2]))
                            System.out.print(pos.getValue().getWordIndex());
                        System.out.println();
                    }
                    curr = curr.getNext();
                }
            } catch (WordNotFoundException e) {
                System.out.println("Webpage " + input[2] + " does not contain word " + input[1]);
            }
        } else if (input[0].equals("queryFindPagesWhichContainAllWords")) {
            try {
                MySet<PageEntry> pages = invPageIndex.getPagesWhichContainWord(plural(input[1]));

                for (int i = 2; i < input.length; i++) {
                    pages = pages.Intersection(invPageIndex.getPagesWhichContainWord(plural(input[i])));
                }
                // create a linked list of Search results
                MySet<Comparable> searches = new MySet<Comparable>();
                ListNode<PageEntry> page = pages.getReadOnlyList().getFront();
                String query[] = new String[input.length - 1];
                for (int i = 1; i < input.length; i++) {
                    query[i - 1] = plural(input[i]);
                }
                while (page != null) {
                    searches.Insert(new SearchResult(page.getValue(),
                            page.getValue().getRelevanceOfPage(query, false, invPageIndex)));
                    page = page.getNext();
                }
                ArrayList<Comparable> results = MySort.sortThisList(searches);
                for (int i = 0; i < results.size(); i++) {
                    System.out.print(((SearchResult) (results.get(i))).getPageEntry().getPageName() + " ");
                    System.out.print("(Relevance is " + ((SearchResult) (results.get(i))).getRelevance() + ") ");
                }
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        } else if (input[0].equals("queryFindPagesWhichContainAnyOfTheseWords")) {
            MySet<PageEntry> pages;
            try {
                pages = invPageIndex.getPagesWhichContainWord(plural(input[1]));
            } catch (WordNotFoundException e) { pages = new MySet<>();}
            for (int i = 2; i < input.length; i++) {
                try {
                    pages = pages.Union(invPageIndex.getPagesWhichContainWord(plural(input[i])));
                } catch (WordNotFoundException e) {}
            }
            if(pages.IsEmpty())
            {
                System.out.println("None of the words are in any webpage");
                return;
            }
            // create a linked list of Search results
            MySet<Comparable> searches = new MySet<Comparable>();
            ListNode<PageEntry> page = pages.getReadOnlyList().getFront();
            String query[] = new String[input.length - 1];
            for (int i = 1; i < input.length; i++) {
                query[i - 1] = plural(input[i]);
            }
            while (page != null) {
                searches.Insert(new SearchResult(page.getValue(),
                        page.getValue().getRelevanceOfPage(query, false, invPageIndex)));
                page = page.getNext();
            }
            ArrayList<Comparable> results = MySort.sortThisList(searches);
            for (int i = 0; i < results.size(); i++) {
                System.out.print(((SearchResult) (results.get(i))).getPageEntry().getPageName() + " ");
                System.out.print("(Relevance is " + ((SearchResult) (results.get(i))).getRelevance() + ") ");
            }
            System.out.println();

        } else if (input[0].equals("queryFindPagesWhichContainPhrase")) {
            String query[] = new String[input.length - 1];
            for (int i = 1; i < input.length; i++) {
                query[i - 1] = plural(input[i]);
            }
            MySet<PageEntry> pagesWhichContainPhrase = invPageIndex.getPagesWhichContainPhrase(query);
            ListNode<PageEntry> page = pagesWhichContainPhrase.getReadOnlyList().getFront();
            if (pagesWhichContainPhrase.IsEmpty()) {
                System.out.print("No webpage contains the phrase ");
                for(int i = 0; i < query.length; i++)
                    System.out.print(query[i] + " ");
                System.out.println();
                return;
            }

            MySet<Comparable> searches = new MySet<Comparable>();
            while (page != null) {
                searches.Insert(new SearchResult(page.getValue(),
                        page.getValue().getRelevanceOfPage(query, true, invPageIndex)));
                page = page.getNext();
            }
            ArrayList<Comparable> results = MySort.sortThisList(searches);
            for (int i = 0; i < results.size(); i++) {
                System.out.print(((SearchResult) (results.get(i))).getPageEntry().getPageName() + " ");
                System.out.print("(Relevance is " + ((SearchResult) (results.get(i))).getRelevance() + ")");
            }
            System.out.println();
        }

    }
}
