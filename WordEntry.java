@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class WordEntry
{
    String word;
    MyLinkedList<Position> positions;
    AVLTree<Position> positionTree;

    public WordEntry(String word)
    {
        this.word = word;
        positions = new MyLinkedList<Position>();
        positionTree = new AVLTree<Position>();
    }

    public void addPosition(Position position)
    {   
        positions.insertRear(position);
        /*ListNode <Position> curr = positions.getFront();
        if(curr==null)
           positions.insertRear(position);
        else if(position.getPageEntry().getPageName().compareTo(curr.getValue().getPageEntry().getPageName()) < 0)
        {
            positions.insertFront(position);
        }
        else
        {
            while(curr.getNext()!=null && position.getPageEntry().getPageName().compareTo(curr.getNext().getValue().getPageEntry().getPageName()) > 0)
                curr=curr.getNext();
            while(curr.getNext()!=null && position.getPageEntry().getPageName().equals(curr.getNext().getValue().getPageEntry().getPageName()) &&  position.getWordIndex() > curr.getNext().getValue().getWordIndex())
                curr=curr.getNext();
            
            ListNode <Position> newNode = new ListNode<Position>(position, curr.getNext(), curr);
            curr.setNext(newNode);
        }*/
        positionTree.insert(position);
    }

    public void addPositions(MyLinkedList<Position> positions)
    {
        ListNode<Position> curr = positions.getFront();
        while(curr!=null)
        {
            this.addPosition(curr.getValue());
            curr=curr.getNext();
        }
    }

    public MyLinkedList<Position> getAllPositionsForThisWord()
    {
        return positions;
    }

    public int getTermOccurences(PageEntry e)
    {
        ListNode<Position> curr = positions.getFront();
        int ans = 0;
        while(curr!=null)
        {
            if(curr.getValue().getPageEntry() == e)
            {
                ans++;
            }
            curr = curr.getNext();
        }
        return ans;
    }

    public int getXPlus(int index)
    {
        TreeNode<Position> curr = positionTree.getRoot();
        TreeNode<Position> returnNode=null;
        while(curr!=null)
        {
            int currIndex = curr.getData().getWordIndex();
            if(currIndex > index)
            {
                returnNode = curr;
                curr = curr.getLeft();
            }
            else
            {
                curr = curr.getRight();
            }
        }
        if(returnNode == null)
            return -1;
        else return returnNode.getData().getWordIndex();
    }

    public int getXMinus(int index)
    {
        TreeNode<Position> curr = positionTree.getRoot();
        TreeNode<Position> returnNode=null;
        while(curr!=null)
        {
            int currIndex = curr.getData().getWordIndex();
            if(currIndex < index)
            {
                returnNode = curr;
                curr = curr.getRight();
            }
            else
            {
                curr = curr.getLeft();
            }
        }
        if(returnNode == null)
            return -1;
        else return returnNode.getData().getWordIndex();
    }

    public Position find(int wordIndex, PageEntry page)
    {
        TreeNode<Position> curr = positionTree.getRoot();
        while(curr!=null)
        {
            if(curr.getData().getWordIndex() == wordIndex)
                if(curr.getData().getPageEntry() == page)
                    return curr.getData();
                else curr = curr.getLeft();
            else if(curr.getData().getWordIndex() > wordIndex)
                curr = curr.getLeft();
            else curr = curr.getRight();
        }
        return null;
    }


    public String getWord()
    {
        return word;
    }
}
