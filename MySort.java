import java.util.ArrayList;
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class MySort
{
    public static ArrayList<Comparable> sortThisList(MySet <Comparable> listOfSortableEntries)
    {
        ArrayList<Comparable> list = new ArrayList<Comparable>();
        ListNode<Comparable> curr = listOfSortableEntries.getReadOnlyList().getFront();
        while(curr!=null)
        {
            int i = 0;
            while(i < list.size() && list.get(i).compareTo(curr.getValue()) > 0)
            {
                i++;
            }
            list.add(i, curr.getValue());
            curr = curr.getNext();
        }
        return list;
    }
}
