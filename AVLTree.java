import java.util.*;
@SuppressWarnings({"unchecked", "rawtypes", "serial", "auxiliaryclass"})

public class AVLTree<Type extends Comparable>
{
    private TreeNode<Type> root;
    
    public AVLTree()
    {
        root = null;
    }

    public void insert(Type p)
    {
        TreeNode<Type> node = new TreeNode<Type>(p);
        //first do naive BST insertion
        insertBST(node);
        //update heights
        updateHeightsAndFixViolation(node);
    }

    public TreeNode<Type> getRoot()
    {
        return root;
    }

    private void updateHeightsAndFixViolation(TreeNode <Type> node)
    {
        if(node == null) return;
        TreeNode<Type> curr = node.getParent();
        TreeNode<Type> child = node;
        TreeNode<Type> grandchild = null;

        while(curr != null)
        {
            boolean right;
            if(curr.getRight() == child)
                right = true;
            else right = false;

            if(right)
            {
                if(curr.getLeft() == null)
                    curr.setHeight(curr.getHeight()+1);
                else
                {
                    int leftHeight = curr.getLeft().getHeight();
                    if(leftHeight < child.getHeight())
                        curr.setHeight(curr.getHeight()+1);
                    else return;
                }
            }
            else
            {
                if(curr.getRight() == null)
                    curr.setHeight(curr.getHeight()+1);
                else
                {
                    int rightHeight = curr.getRight().getHeight();
                    if(rightHeight < child.getHeight())
                        curr.setHeight(curr.getHeight()+1);
                    else return;
                }
            }

            //check for violation
            int leftHeight;
            if(curr.getLeft() == null) leftHeight = -1;
            else leftHeight = curr.getLeft().getHeight();

            int rightHeight;
            if(curr.getRight() == null) rightHeight = -1;
            else rightHeight = curr.getRight().getHeight();
            
            if(Math.abs(leftHeight - rightHeight) > 1)
            {
                TreeNode<Type> violationChild = child;
                TreeNode<Type> violationGrandchild = grandchild;
                TreeNode<Type> parent = curr.getParent();
                TreeNode<Type> middle;
                boolean rightChild=false;
                if(parent!=null)
                {
                    if(curr == parent.getLeft())
                        rightChild = false;
                    else rightChild = true;
                }
                if(curr.getRight() == violationChild)
                {
                    if(violationChild.getRight() == violationGrandchild)
                    {
                        middle = violationChild;
                        curr.setRight(middle.getLeft());
                        middle.setLeft(curr);
                        curr.updateHeight();
                        middle.updateHeight();
                        //curr.setHeight(curr.getHeight()-2);
                    }
                    else
                    {
                        middle = violationGrandchild;
                        curr.setRight(middle.getLeft());
                        middle.setLeft(curr);
                        violationChild.setLeft(middle.getRight());
                        middle.setRight(violationChild);
                        curr.updateHeight();
                        violationChild.updateHeight();
                        violationGrandchild.updateHeight();
                      //  violationChild.setHeight(violationChild.getRight()!=null? violationChild.getRight().getHeight()+1 : 0);
                       // curr.setHeight(curr.getHeight()-2);
                       // violationGrandchild.setHeight(violationGrandchild.getHeight()+2);
                    }
                }
                else
                {
                    if(violationChild.getLeft() == violationGrandchild)
                    {
                        middle = violationChild;
                        curr.setLeft(middle.getRight());
                        middle.setRight(curr);
                        curr.updateHeight();
                        violationChild.updateHeight();
                        violationGrandchild.updateHeight();
                        //curr.setHeight(curr.getHeight()-2);
                    }
                    else
                    {
                        middle = violationGrandchild;
                        curr.setLeft(middle.getRight());
                        middle.setRight(curr);
                        violationChild.setRight(middle.getLeft());
                        middle.setLeft(violationChild);
                        curr.updateHeight();
                        violationChild.updateHeight();
                        violationGrandchild.updateHeight();
                        //violationChild.setHeight(violationChild.getLeft()!=null? violationChild.getLeft().getHeight()+1 : 0);
                        //curr.setHeight(curr.getHeight()-2);
                        //violationGrandchild.setHeight(violationGrandchild.getHeight()+2);
                    }
                }
                //adjust parent
                
                if(parent == null)
                {
                    root = middle;
                    root.setParent(null);
                }
                else
                {
                    if(rightChild)
                        parent.setRight(middle);
                    else
                        parent.setLeft(middle);
                }
            }
            
            grandchild = child;
            child = curr;
            curr = curr.getParent();
        }
        
    }

    private TreeNode<Type> insertBST(TreeNode<Type> node)
    {
        TreeNode<Type> curr = root;
        TreeNode<Type> prev = null;
        boolean isRight = false;
        while(curr!=null)
        {
            prev = curr;
            if(node.getData().compareTo(curr.getData()) > 0)
            {
                curr = curr.getRight();
                isRight = true;
            }
            else
            {
                curr = curr.getLeft();
                isRight = false;
            }
        }
        if(prev == null)
            root = node;
        else
        {
            if(isRight)
                prev.setRight(node);
            else
                prev.setLeft(node);
            node.setParent(prev);
        }
        return prev;
    }
}
