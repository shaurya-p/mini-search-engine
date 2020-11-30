@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
class ListNode<Type>
{
    private Type value;
    private ListNode<Type> next;
    private ListNode<Type> prev;

    public ListNode(Type val, ListNode<Type> nextNode, ListNode<Type> prevNode)
    {
        value = val;
        next = nextNode;
        prev = prevNode;
    }

    public ListNode()
    {
        value = null;
        next = null;
        prev = null;
    }

    public Type getValue()
    {
        return value;
    }

    public ListNode<Type> getNext()
    {
        return next;
    }

    public ListNode<Type> getPrev()
    {
        return prev;
    }

    public void setValue(Type val)
    {
        value = val;
    }

    public void setNext(ListNode<Type> nextNode)
    {
        next = nextNode;
    }

    public void setPrev(ListNode<Type> prevNode)
    {
        prev = prevNode;
    }
}

@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
class EmptyMyLinkedListDeleteException extends Exception
{
    public EmptyMyLinkedListDeleteException(String message)
    {
        super(message);
    }
}

@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
class ListElementNotFoundException extends Exception
{
    public ListElementNotFoundException(String message)
    {
        super(message);
    }
}

@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class MyLinkedList<Type>
{
    private ListNode<Type> first;
    private ListNode<Type> last;
    private int size;

    public MyLinkedList()
    {
        first = null;
        last = null;
        size = 0;
    }

    public void insertFront(Type o)
    {
        ListNode<Type> temp = first;
        first = new ListNode<Type>(o, temp, null);
    }

    public void insertRear(Type o)
    {
        if(first == null)
        {
            first = new ListNode<Type>(o, null, null);
            last = first;
        }
        else
        {
            ListNode<Type>node = new ListNode<Type>(o, null, last);
            last.setNext(node);
            last = node;
        }
        size++;
    }

    public void delete(Type o) throws EmptyMyLinkedListDeleteException, ListElementNotFoundException
    {
        if(first == null)
            throw new EmptyMyLinkedListDeleteException("ERROR: Attempting to delete empty linked list!");
        else if(first == last)
        {
            if(!first.getValue().equals(o))
                throw new ListElementNotFoundException("ERROR: Attempting to delete an element not in the list!");

            else
            {
                first = null;
                last = null;
            }
        }
        else
        {
            ListNode<Type>curr = first;
            boolean found = false;
            while(curr!=null)
            {
                if(curr.getValue().equals(o))
                {
                    if(curr != first) //pointer comparison
                        curr.getPrev().setNext(curr.getNext());
                    else
                        first = curr.getNext();
                    if(curr != last) //pointer comparison
                        curr.getNext().setPrev(curr.getPrev());
                    else last = curr.getPrev();
                    found = true;
                    break;
                }
                else curr = curr.getNext();
            }
            if(!found)
                throw new ListElementNotFoundException("ERROR: Attemping to delete an element not in the list");

            size--;
        }
    }

    public Boolean isMember(Type o)
    {
        Boolean found = Boolean.valueOf(false);
        ListNode<Type>curr = first;
        while(curr!=null)
        {
            //System.out.println(curr.getValue());
            if(curr.getValue().equals(o))
            {
                found = Boolean.valueOf(true);
                break;
            }
            else curr = curr.getNext();
        }
        return found;
    }

    public Boolean isEmpty()
    {
        return Boolean.valueOf(first==null);
    }

    public Type returnRear()
    {
        return last.getValue();
    }

    public ListNode<Type> getFront()
    {
        return first;
    }

    public MyLinkedList<Type> duplicate()
    {
        MyLinkedList<Type> temp = new MyLinkedList<Type>();
        ListNode<Type>curr = first;
        while(curr != null)
        {
            temp.insertRear(curr.getValue());
            curr = curr.getNext();
        }

        return temp;
    }

    public int getSize()
    {
        return size;
    }

    public Type deleteRear() throws EmptyMyLinkedListDeleteException
    {
        if(last == null)
            throw new EmptyMyLinkedListDeleteException("ERROR: Trying to delete an element from an empty linked list");

        Type t = last.getValue();
        if(first == last)
            first = last = null;
        else
            last = last.getPrev();

        return t;
    }
}
