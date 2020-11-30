@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})
public class TreeNode<Type extends Comparable>
{
    Type data;
    TreeNode<Type> parent;
    TreeNode<Type> left;
    TreeNode<Type> right;
    int height;
    public TreeNode(Type p, TreeNode<Type> left, TreeNode<Type> right, TreeNode<Type> parent)
    {
        data = p;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public TreeNode(Type p)
    {
        data = p;
        left = right = parent = null;
        height = 0;
    }

    public void setLeft(TreeNode<Type> left)
    {
        this.left = left;
        if(left!=null)
            left.setParent(this);
      //  this.updateHeight();
    }
    
    public void setRight(TreeNode<Type> right)
    {
        this.right = right;
        if(right!=null)
            right.setParent(this);
       // this.updateHeight();
    }

    public void updateHeight()
    {
        int leftHeight = (this.left==null) ? -1 : this.left.getHeight();
        int rightHeight = (this.right==null) ? -1 : this.right.getHeight();
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }

    public void setParent(TreeNode<Type> parent)
    {
        this.parent = parent;
    }

    public void setData(Type data)
    {
        this.data = data;
    }

    public TreeNode<Type> getLeft()
    {
        return left;
    }

    public TreeNode<Type> getRight()
    {
        return right;
    }

    public TreeNode<Type> getParent()
    {
        return parent;
    }

    public Type getData()
    {
        return data;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getHeight()
    {
        return height;
    }
}
