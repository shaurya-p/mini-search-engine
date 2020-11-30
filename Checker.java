import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class Checker
{
    public static void main ( String args [])
    {
        BufferedReader br = null;
        SearchEngine r = new SearchEngine();

        try
        {
            String actionString;
            br = new BufferedReader(new FileReader("actions2.txt"));

            while ((actionString = br.readLine()) != null)
            {
                r.performAction(actionString);
            }
            // ListNode <WordEntry> curr = r.invPageIndex.getPageList().getReadOnlyList().getFront().getValue().getPageIndex().getWordEntries().getFront();
            // while(curr!=null)
            // {
            //     System.out.println("Word is " + curr.getValue().getWord());
            //     ListNode<Position> positions = curr.getValue().getAllPositionsForThisWord().getFront();
            //     while(positions!=null)
            //     {
            //         System.out.println("position " + positions.getValue().getWordIndex() + " page " + positions.getValue().getPageEntry().getPageName());
            //         positions = positions.getNext();
            //     }
            //     curr = curr.getNext();
            // }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (br != null)br.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

    }
}
