@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class MySet<Type>
{
    protected MyLinkedList<Type> set;

    public MySet()
    {
        set = new MyLinkedList<Type>();
    }

    public Boolean IsEmpty()
    {
        return set.isEmpty();
    }

    public Boolean IsMember(Type o)
    {
        return set.isMember(o);
    }

    public void Insert(Type o)
    {
        if(!set.isMember(o))
            set.insertRear(o);
    }

    public void Delete(Type o) throws MySetEmptyDeleteException, MySetElementNotFoundException
    {
        try
        {
            set.delete(o);
        }
        catch(EmptyMyLinkedListDeleteException e)
        {
            throw new MySetEmptyDeleteException("ERROR: Attempting to delete elements from an empty MySet");
        }
        catch(ListElementNotFoundException e)
        {
            throw new MySetElementNotFoundException("ERROR: Attemping to delete an element not present in the set");
        }
    }

    public MySet<Type> Union(MySet<Type> a)
    {
        MySet<Type> temp = new MySet<Type>();
        MyLinkedList<Type> l = a.set.duplicate();
        ListNode<Type> curr = set.getFront();
        while(curr!=null)
        {
            if(!l.isMember(curr.getValue()))
                l.insertRear(curr.getValue());
            curr = curr.getNext();
        }

        temp.set = l;
        return temp;
    }

    public MySet<Type> Intersection(MySet<Type> a)
    {
        MySet<Type> temp = new MySet<Type>();
        MyLinkedList<Type> l = new MyLinkedList<Type>();
        ListNode<Type> curr = set.getFront();
        while(curr!=null)
        {
            if(a.set.isMember(curr.getValue()))
                l.insertRear(curr.getValue());
            curr = curr.getNext();
        }

        temp.set = l;
        return temp;
    }

    public MyLinkedList<Type> getReadOnlyList()
    {
        //TODO: make it read only
        return set;
    }
    public int getSize()
    {
        return set.getSize();
    }
}
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
class MySetEmptyDeleteException extends Exception
{
    public MySetEmptyDeleteException(String message)
    {
        super(message);
    }
}
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
class MySetElementNotFoundException extends Exception
{
    public MySetElementNotFoundException(String message)
    {
        super(message);
    }
}
