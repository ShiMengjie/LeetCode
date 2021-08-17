# LeetCode-二叉树遍历

## [144. 二叉树的前序遍历](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210817191420071.png" alt="image-20210817191420071" style="zoom:80%;" />

### 递归版本

定义函数 pre(list,root) 表示把根节点为 root 的二叉树前序遍历保存进列表 list 中。

root 的左子节点是左子树的根节点，root 的又子节点是右子树的根节点，可以不断递归。

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        pre(list, root);
        return list;
    }

    private void pre(List<Integer> list, TreeNode root) {
        if (root != null) {
            list.add(root.val);
            pre(list, root.left);
            pre(list, root.right);
        }
    }
}
```

### 迭代版本

当遍历到某个节点时，能知道它的左子节点和右子节点，当左子树的所有节点都添加进列表后，才会开始添加右子树，换句话说“右子节点被**先**遍历到了，但是**后**添加到列表中”，比如上图中，在遍历节点A的时候，已知了节点B和C，但是要把B的子树都添加进列表后再添加C的子树。

在遍历到A时，需要一个地方保存B和C的数据，而且要满足：先进后出，所以使用栈临时保存。

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) {
            stack.add(root);
        }
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            // 先把右节点压进栈中，因为右节点在左节点添加完之后，才会出栈
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return list;
    }
}
```

## [94. 二叉树的中序遍历](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/)

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210817192920426.png" alt="image-20210817192920426" style="zoom:80%;" />

### 递归版本

思路同“前序遍历”

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        inorder(list, root);
        return list;
    }

    private void inorder(List<Integer> list, TreeNode root) {
        if (root != null) {
            inorder(list, root.left);
            list.add(root.val);
            inorder(list, root.right);
        }
    }
}
```

### 迭代版本

当遍历到某个节点时，沿着它的左分支一直遍历，直到 null 为止，同时要把遍历到的节点依次 push 到栈中，这样在把左子节点添加进列表后，能够返回到该节点。比如，当遍历到节点A时，要把A先push到栈里，然后沿着左分支一直遍历，并且把遍历到的节点都push到栈中，然后再从栈中依次 pop 出节点。

当从左节点回退到父节点时，把值添加进列表中，并对右节点重复前面的操作。

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 把栈中的最后一个节点 pop 出来，添加进列表中
            TreeNode node = stack.pop();
            list.add(node.val);
            // 把右节点赋值给 root，在下一次循环时，执行重复前面的入栈操作
            root = node.right;
        }
        return list;
    }
}
```

## [145. 二叉树的后序遍历](https://leetcode-cn.com/problems/binary-tree-postorder-traversal/)

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210817200607741.png" alt="image-20210817200607741" style="zoom:80%;" />

### 递归版本

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        postOrder(list, root);
        return list;
    }

    private void postOrder(List<Integer> list, TreeNode root) {
        if (root != null) {
            postOrder(list, root.left);
            postOrder(list, root.right);
            list.add(root.val);
        }
    }
}
```

### 迭代版本

因为是先遍历到父节点，但是先添加左节点和右节点，所以用栈保存 父节点、右节点。

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                // 把根节点、右节点、左节点依次添加进栈
                // 如果把子节点添加进栈，就要把子节点的指针设置为 null，这样在后续从栈中取出节点时，不会重复添加
                // 也表明，如果一个节点左右指针都是 null，要么它是叶子节点，要么它的子节点都已经进栈了，且添加进列表了，可以添加该节点了
                stack.push(root);
                if (root.right != null) {
                    stack.push(root.right);
                    root.right = null;
                }
                TreeNode tmp = root.left;
                root.left = null;
                root = tmp;
            }
            // 把最后一个节点取出，如果没有子节点，就添加到列表中
            TreeNode node = stack.pop();
            if (node.left == null && node.right == null) {
                list.add(node.val);
            } else {
                // 如果有子节点，就作为子树的根节点，继续添加进栈中
                root = node;
            }
        }
        return list;
    }
}
```

## [102. 二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)

