# LeetCode-二叉树遍历

一颗二叉树的唯一入口，就是它的根节点；在遍历到某个节点时，要考虑怎么记录它的前置节点（父节点、兄弟节点）。

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
            // 把最后一个节点取出，如果没有子节点（叶子结点），就添加到列表中
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

当遍历到左子节点时，父节点已经遍历过了，并且已经添加进列表中了；当遍历到右子节点时，父节点、左子节点都已经遍历过且添加进列表中了。需要用一个先进先出的结构（队列）来临时保存节点，把父节点、左节点、右节点依次添加进去，并按次序从队列取出。

每一次迭代，都对应这树的某一层，当迭代开始时，队列的大小就是当前层次的节点数。

```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 队列中剩余的节点数，就是当前层的节点数
            int size = queue.size();
            List<Integer> subList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                subList.add(node.val);
            }
            res.add(subList);
        }
        return res;
    }
}
```

## [107. 二叉树的层序遍历 II](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)

这个问题与“102”相同，只不过在返回时，把102的结果倒序，或者在每次遍历插入数据时，把数据插入到结果类表的头部。

```java
class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 队列中剩余的节点数，就是当前层的节点数
            int size = queue.size();
            List<Integer> subList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                subList.add(node.val);
            }
            res.add(0, subList);
        }
        return res;
    }
}
```

## [103. 二叉树的锯齿形层序遍历](https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/)

```java
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        // flag == true 表示从左向右，flag == false 表示从右向左
        boolean flag = true;
        while (!queue.isEmpty()) {
            // 按照正常的层序遍历，把节点添加进队列中
            int size = queue.size();
            List<TreeNode> nodeList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                // 把当前节点保存进临时列表中
                nodeList.add(node);
            }
            // 如果需要翻转，翻转临时列表
            if (!flag) {
                Collections.reverse(nodeList);
            }
            // 翻转标志
            flag = !flag;
            // 把临时列表中节点的值，依次添加进子列表中
            List<Integer> subList = new ArrayList<>();
            for (TreeNode node : nodeList) {
                subList.add(node.val);
            }
            res.add(subList);
        }
        return res;
    }
}
```

## [889. 根据前序和后序遍历构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/)

前序遍历数组 pre 的第一个元素 pre[0] 是根节点 root，第二个元素 pre[1] 是左子树的根节点；

后续遍历数组 post 的最后一个元素 post[n-1] 是根节点，倒数第二个元素 post[n-2] 是右子树的根节点；如果 pre[1] == post[n-2] 相等，说明根节点 root 只有一颗子树。

假设 post[n-2] 在 pre 数组中的下标为 i，pre[0+1] 在 post 数组中的下标为 j，那么存在如下关系：

* pre[0+1,i-1] 是左子树在 pre 中的范围，pre[i,n-1] 是右子树在 pre 中的范围
* post[0,j] 是左子树在 post 中的范围，post[j+1,n-2] 是右子树的后序遍历

定义一个递归函数，返回当前树的根节点。

```java
class Solution {
    Map<Integer, Integer> preMap, postMap;
    int[] preorder, postorder;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        this.preMap = getIndexMap(preorder);
        this.postMap = getIndexMap(postorder);
        this.preorder = preorder;
        this.postorder = postorder;

        return recursion(0, preorder.length - 1, 0, preorder.length - 1);
    }

    /**
     * 根据前序遍历、后序遍历，递归求解二叉树
     *
     * @param ls 树在前序遍历列表中的起始下标
     * @param le 树在前序遍历列表中的结束下标
     * @param rs 树在后序遍历列表中的起始下标
     * @param re 树在后序遍历数组中的结束下标
     * @return 根节点
     */
    private TreeNode recursion(int ls, int le, int rs, int re) {
        TreeNode root = new TreeNode(preorder[ls]);
        if (le <= ls) {
            return root;
        }
        // i ： 右子树根节点在 pre 中的下标， j : 左子树根节点在 post 中的下标
        int i = preMap.get(postorder[re - 1]), j = postMap.get(preorder[ls + 1]);
        if (preorder[ls + 1] == postorder[re - 1]) {
            root.left = recursion(ls + 1, le, rs, j);
        } else {
            root.left = recursion(ls + 1, i - 1, rs, j);
            root.right = recursion(i, le, j + 1, re - 1);
        }
        return root;
    }

    private Map<Integer, Integer> getIndexMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        return map;
    }
}
```



## [105. 从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

前序数组 pre 的第一个元素 pre[0] 是根节点 root，假设 pre[0] 在中序数组 in 中的下标是 i，那么有以下几种情况：

* 如果 i == 0，就说明 root 只有右子树，pre[1,n-1] 是右子树，in[i+1,n-1] 是右子树
* 如果 i == n- 1，就说明 root 只有左子树，pre[1,n-1] 是左子树，in[0,i-1] 是左子树
* i为其他值：
  * 根据前序遍历和中序遍历的规则（中左右、左中右），pre 中左子树的长度等于 in 中左子树的长度，pre 中右子树的长度等于 in 中右子树的长度
  * 因此，pre[1,i]、in[0,i-1] 是左子树，pre[i+1,n-1]、in[i+1,n-1] 是右子树

```java
class Solution {
    Map<Integer, Integer> preMap, inMap;
    int[] preorder, inorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        this.preMap = getIndexMap(preorder);
        this.inMap = getIndexMap(inorder);

        return recursion(0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode recursion(int ls, int le, int rs, int re) {
        if (ls >= preorder.length || le < ls || re < rs) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[ls]);
        int i = inMap.get(preorder[ls]);
        // in 数组中，左子树的长度
        int l = i - rs;
        root.left = recursion(ls + 1, ls + l, rs, i - 1);
        root.right = recursion(ls + l + 1, le, i + 1, re);
        return root;
    }

    private Map<Integer, Integer> getIndexMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        return map;
    }
}
```

## [106. 从中序与后序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)

后序数组 post 的最后一个元素 post[n-1] 是根节点 root，假设 post[n-1] 在中序数组 in 中的下标为 i，那么根据遍历规则，左子树在 in 中的长度为 l = i，可以得到以下信息：

* in[0,i-1] 是左子树的中序遍历，post[0,l-1] 是左子树的后序遍历	
* in[i+1,n-1] 是右子树的中序遍历，post[l,n-2] 是右子树的后序遍历

```java
class Solution {
    Map<Integer, Integer> postMap, inMap;
    int[] postorder, inorder;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.inorder = inorder;
        this.postorder = postorder;
        this.inMap = getIndexMap(this.inorder);
        this.postMap = getIndexMap(this.postorder);
        return recursion(0, this.inorder.length - 1, 0, this.postorder.length - 1);
    }

    private TreeNode recursion(int ls, int le, int rs, int re) {
        // 因为需要在两个数组中寻找元素，所以只要有一个不满足条件，就可以结束递归
        if (re >= postorder.length || le < ls || re < rs) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[re]);
        int i = inMap.get(root.val);
        // 左子树的长度
        int l = i - ls;
        root.left = recursion(ls, i - 1, rs, rs + l - 1);
        root.right = recursion(i + 1, le, rs + l , re - 1);
        return root;
    }

    private Map<Integer, Integer> getIndexMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        return map;
    }
}
```

## [1028. 从先序遍历还原二叉树](https://leetcode-cn.com/problems/recover-a-tree-from-preorder-traversal/)

```java
class Solution {

    public TreeNode recoverFromPreorder(String traversal) {
        // 保存取出的节点，保存进 path 中
        Stack<TreeNode> path = new Stack<>();
        int pos = 0;
        // 遍历的下标不能超过字符串长度
        while (pos < traversal.length()) {
            // level ： 当前节点的深度， -- 的长度
            int level = 0;
            while (traversal.charAt(pos) == '-') {
                ++level;
                ++pos;
            }
            // 取出数字
            int value = 0;
            while (pos < traversal.length() && Character.isDigit(traversal.charAt(pos))) {
                value = value * 10 + (traversal.charAt(pos) - '0');
                ++pos;
            }
            // 构造节点
            TreeNode node = new TreeNode(value);
            // 根据前序遍历的规则，从 root 一直遍历它的左子节点，所以如果 level == path.size，说明当前节点是它父节点的左节点
            // 设置当前节点为栈顶节点的左子节点
            if (level == path.size()) {
                if (!path.isEmpty()) {
                    path.peek().left = node;
                }
            } else {
                // 如果 level != path.size，说明当前节点是路径上某个节点的右节点，那么不断地把栈中节点 pop 出，直到 level == path.size
                // 设置当前节点为栈顶节点的右子节点
                while (level != path.size()) {
                    path.pop();
                }
                path.peek().right = node;
            }
            // 把当前节点 push 到栈顶
            path.push(node);
        }
        // 全部 pop 出，只剩下根节点
        while (path.size() > 1) {
            path.pop();
        }
        return path.peek();
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
```




## [114. 二叉树展开为链表](https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/)

在生成前序遍历的过程中，生成链表。

```java
class Solution {
    public void flatten(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) {
            stack.add(root);
        }
        TreeNode tmp = null, node = null;
        while (!stack.isEmpty()) {
            node = stack.pop();
            // 先把右节点压进栈中，因为右节点在左节点添加完之后，才会出栈
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
                // 把左指针设置为 null
                node.left = null;
            }
            // 更新 tmp 的指向，如果 tmp 为null，表示当前node 是根节点
            if (tmp != null) {
                tmp.right = node;
            }
            tmp = node;
        }
    }
}
```











