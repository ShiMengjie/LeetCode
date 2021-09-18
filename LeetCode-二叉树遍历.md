# LeetCode-二叉树遍历

整理了 LeetCode 上一些二叉树遍历相关问题。

## 问题列表

[144. 二叉树的前序遍历](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)

[94. 二叉树的中序遍历](https://leetcode-cn.com/problems/binary-tree-inorder-traversal/)

[145. 二叉树的后序遍历](https://leetcode-cn.com/problems/binary-tree-postorder-traversal/)

[102. 二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)

[107. 二叉树的层序遍历 II](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)

[103. 二叉树的锯齿形层序遍历](https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/)

[889. 根据前序和后序遍历构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/)

## 前言

定义二叉树的节点类 `TreeNode`：

```java
public class TreeNode {
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
```

在遍历二叉树之前，我们先明确一个事实：

一棵二叉树是从根节点 root 开始，这是它唯一的入口，只能从一个父节点找到它的左右子节点。

因此，当我们遍历到某个节点 node 时，要想继续遍历到它的父节点或兄弟节点，就需要在遍历到 node 节点之前记录下它的父节点或兄弟节点，不同的遍历方式，实际上可以看做是，使用不同的方式记录和取出父节点、兄弟节点。

## [144. 二叉树的前序遍历](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)

> 遍历方式：根-左-右

如下图所示，树的入口是根节点 A，遍历顺序如下：

1、遍历根节点 A

------

遍历 A 的左子树，B = A.left 作为左子树的根节点，是左子树的入口，遍历顺序如下：

2.1、遍历节点B

2.2、遍历 B 的左子节点 D

2.3、遍历 B 的右子节点 E

------

遍历A的右子树，C = A.right 作为右子树的根节点，是右子树的入口，遍历顺序如下：

3.1、遍历节点 C

3.2、遍历 C 的左子节点 F

3.3、遍历 C 的右子节点 G

![image-20210829155351605](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210817191420071.png)

![image-20210829155351605](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210829155351605.png)

### 递归

从前面的遍历过程可以看出，前序遍历都是先遍历根节点，再遍历左子树，最后遍历右子树，过程相同。因此，可以使用递归函数来实现遍历过程。

定义函数：$pre(list,root)$
含义：把根节点为 root 的二叉树，以前序遍历的方式保存进列表 list 中，$pre(list,root.letf)、pre(list,root.right)$分别表示遍历root 节点的左子树和右子树。

代码实现如下：

```java
class Solution {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        pre(list, root);
        return list;
    }

    private void pre(List<Integer> list, TreeNode root) {
        if (root != null) {
            // 遍历根节点
            list.add(root.val);
            // 遍历左子树
            pre(list, root.left);
            // 遍历右子树
            pre(list, root.right);
        }
    }
}
```

### 迭代

怎么用迭代的方式现实前序遍历？

我们回顾一下前面的遍历过程：

- 在“2.3”遍历完节点D后，再遍历它的兄弟节点E
- 在“3.1”遍历完左子树后，再开始遍历右子树根节点
- 在“3.3”遍历完节点F后，再遍历它的兄弟节点G

在“前言”部分，我们已经知道，只能在父节点找到左右子节点，不能从子节点找到父节点或兄弟节点。因此，我们在遍历到节点 node 前，要记录下它的兄弟节点。

**在遍历过程中，怎么记录节点？**

从上面树的结构图和遍历结果发现：

- 遍历节点B之前，要记录B的兄弟节点C
- 遍历节点D之前，要记录D的兄弟节点E

在遍历的结果数组中我们发现：

- 同一层的节点，右子节点比左子节点先记录，但是排在后面，B和C、D和E都是如此
- 右子树的所有节点，都在左子树节点之后

这种“先进-后出”记录结构，很适合用栈来实现：

- 先 push 右子节点，再 push 左子节点
- pop 出左子节点后，再 push 左子节点的“右子节点、左子节点”，保证左子树先遍历

代码实现如下：

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if(root == null) {
            return list;
        }
        // 根节点先遍历
        list.add(root.val);

        Stack<TreeNode> stack = new Stack<>();
        // 依次记录右子节点、左子节点
        if (root.right != null) {
            stack.push(root.right);
        }
        if (root.left != null) {
            stack.push(root.left);
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

> 遍历方式：左-根-右

如下图所示，树的入口是根节点 A，遍历顺序如下：

------

如下图所示，树的入口是根节点 A，遍历顺序如下：

1.1、遍历 B 的左子节点 D

1.2、遍历 B 节点

1.3、遍历 B 右子节点 E

------

2、遍历根节点 A

------

遍历A的右子树，C = A.right 作为右子树的根节点，是右子树的入口，遍历顺序如下：

3.1、遍历 C 的左子节点 F

3.2、遍历节点 C

3.2、遍历 C 右子节点 G

![image-20210829155351605](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210817192920426.png)

![image-20210829163341263](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210829163341263.png)

### 递归

从前面的遍历过程可以看出，终须遍历都是先遍历它的左子树、再遍历根节点、最后遍历右子树，过程相同。因此，可以使用递归函数来实现遍历过程。

定义函数：$inorder(list,root)$

含义：把根节点为 root 的二叉树的中序遍历保存进列表 list 中，$inorder(list,root.letf)、inorder(list,root.right)$分别表示遍历root 节点的左子树和右子树。

代码实现如下：

```java
class Solution {
    
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        inorder(list, root);
        return list;
    }

    private void inorder(List<Integer> list, TreeNode root) {
        if (root != null) {
            // 遍历左子树
            inorder(list, root.left);
            // 遍历根节点
            list.add(root.val);
            // 遍历右子树
            inorder(list, root.right);
        }
    }
}
```

### 迭代

有了前序遍历的迭代经验，我们很容易发现，中序遍历用栈的痕迹更明显：从根节点（父节点）进入，但是根节点（父节点）却排在左子树后面。

因此，我们可以用一个栈，从根节点开始，不断遍历左子树，把父节点和左子节点都 push 进栈。



**每个节点的右子树怎么遍历？**

我们知道，要进入右子节点，就要先遍历到它的父节点，所以当我们从栈中 pop 出一个节点 node 时，判断 node 是否有右子节点。如果有右子节点，那么就从 node.right 开始，遍历该右子树。



代码实现如下：

```java
class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            // 从 root 节点进入，沿着左子树不断遍历，把父节点、左子节点 push 进栈
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // pop 栈顶节点
            TreeNode node = stack.pop();
            list.add(node.val);
            // 如果有右子节点，让 root 指向右子树的根节点，在下一次循环时，遍历 node 的右子树
            root = node.right;
        }
        return list;
    }
}
```

## [145. 二叉树的后序遍历](https://leetcode-cn.com/problems/binary-tree-postorder-traversal/)

> 遍历方式：左-右-根

如下图所示，树的入口是根节点 A，遍历顺序如下：

遍历 A 的左子树，B = A.left 作为左子树的根节点，是左子树的入口，遍历顺序如下：

1.1、遍历 B 的左子节点 D

1.2、遍历 B 的右子节点 E

1.3、遍历节点 B

------

遍历A的右子树，C = A.right 作为右子树的根节点，是右子树的入口，遍历顺序如下：

2.1、遍历 C 的左子节点 F

2.2、遍历 C 的右子节点 G

2.3、遍历节点 C

------

3、遍历根节点A

![image-20210829155351605](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210829165839783.png)

![image-20210829170257962](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210829170257962.png)

### 递归

从前面的遍历过程可以看出，后序遍历都是先遍历它的左子树、再遍历右子树、最后遍历根节点，过程相同。因此，可以使用递归函数来实现遍历过程。

定义函数：$postOrder(list,root)$

含义：把根节点为 root 的二叉树的中序遍历保存进列表 list 中，$postOrder(list,root.letf)、postOrder(list,root.right)$分别表示遍历root 节点的左子树和右子树。

代码实现如下：

```java
class Solution {
    
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        postOrder(list, root);
        return list;
    }

    private void postOrder(List<Integer> list, TreeNode root) {
        if (root != null) {
            // 遍历左子树
            postOrder(list, root.left);
            // 遍历右子树
            postOrder(list, root.right);
            // 遍历根节点
            list.add(root.val);
        }
    }
}
```

### 迭代

根据前面的经验：从根节点（父节点）进入，但是根节点（父节点）却排在左子树、右子树后面。



因此，我们用一个栈，从根节点开始，把根节点、右子节点、左子节点，依次 push 进栈中，然后再从栈中依次 pop 出栈顶节点。



**从栈顶 pop 出节点后，要做什么？**

从后序遍历的规则可以看出，它依次遍历父节点的左子树、右子树、父节点自身。因此，在从栈顶 push 出节点 node 后，要判断 node 是否有子节点：

- 如果没有子节点，就可以把节点值添加进结果集

- 如果有子节点，就要把 node 节点作为子树的根节点入口，按照上面的步骤 push 进栈中



**这样做会有什么问题？**

我们把节点 node 从栈顶 pop 出来后，如果它有子节点，会执行入栈操作。那么当从栈顶再 push 出来时，还会入栈，形成了一个死循环。所以，在入栈后，要把 node 节点的 左右子节点指针 指向 null，避免死循环。



代码实现如下：

```java
class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                // 把根节点 push 入栈
                stack.push(root);
                // 把右子节点 push 入栈，并把有指针设为 null
                if (root.right != null) {
                    stack.push(root.right);
                    root.right = null;
                }
                // 指向左子节点，在下一个循环时，把左子节点 push 入栈，把左指针设为 null
                TreeNode tmp = root.left;
                root.left = null;
                root = tmp;
            }
            // pop 出栈顶节点，如果没有子节点，就添加进结果集
            // 如果一个节点左右指针都是 null，它要么是叶子节点，要么它的子节点都已经进栈了，且添加进列表了，可以把该节点遍历进结果集
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

> 遍历方式：逐层遍历整棵数，同层节点从左向右遍历

如下图所示，树的入口是根节点 A，遍历顺序如下：

1、遍历根节点A

------

遍历 A 的两个子节点，遍历顺序如下：

2.1、遍历左子节点 B

2.2、遍历右子节点 C

------

遍历 B 和 C 的两个两个子节点，遍历顺序如下：

3.1、遍历 B 的左子节点 D

3.2、遍历 B 的右子节点 E

3.3、遍历 C 的左子节点 F

3.4、遍历 C 的右子节点 G

![Snipaste_2021-09-05_16-44-29](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/Snipaste_2021-09-05_16-44-29.gif)

我们只能从根节点进入一棵树。

在层序遍历结果中，根节点（父节点）排在它的左右子节点的前面，因此和前面不同的是，我们需要使用一个先进先出的队列，把父节点、左子节点、右子节点依次添加进队列，再依次从队列取出。

代码实现如下：

```java
class Solution {

    public List<Integer> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new LinkedList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        // 把父节点添加进队列
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // 把左子节点添加进队列
            if (node.left != null) {
                queue.offer(node.left);
            }
            // 把右子节点添加进队列
            if (node.right != null) {
                queue.offer(node.right);
            }
            // 把父节点值添加进列表
            res.add(node.val);
        }
        return res;
    }
}
```

------

然而，问题要求把二叉树中每一层的所有节点添加进一个列表。这就要求，我们在遍历某一层节点时，知道该层有多少个节点，遍历完这一层所有节点后，再遍历下一层。

**怎么知道某一层有多少个节点？**

从前面的图片和代码我们可以发现，当遍历某一层节点时：

- 当遍历这一层最左边的节点时，才开始在队列中添加下一层的节点

- 当遍历完这一层最右边的节点时，下一层的节点添加完毕

因此，我们在遍历某一层最左边节点时，队列中的节点数就是这一层的节点数，我们取出这一层的节点数后，取出相同数量的节点数后，队列中剩余的节点就是下一层的节点数。


代码实现如下：

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
            // 取出当前层的节点，并把下一层节点添加进队列
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

### [107. 二叉树的层序遍历 II](https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/)

该问题与 [102. 二叉树的层序遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/) 相同，在把每一层子节点列表添加进结果列表时，添加到结果列表的头部。

代码实现如下：

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
            // 把这一层的节点列表，添加到结果哦列表的头部
            res.add(0, subList);
        }
        return res;
    }
}
```

### [103. 二叉树的锯齿形层序遍历](https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/)

同样是层序遍历，只不过在遍历过程中，要每隔一层就要翻转一次节点列表。可以使用一个标志位来表示是否要翻转，并在每一层遍历后修改标志位。

代码实现如下：

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
            // 正常的层序遍历
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
            // 修改翻转标志
            flag = !flag;
            // 把临时列表中节点的值转换成列表，依次添加进子列表中
            res.add(nodeList.stream().map(o -> o.val).collect(Collectors.toList()));
        }
        return res;
    }
}
```

# TODO

## [889. 根据前序和后序遍历构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/)

![image-20210829155351605](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210829155351605.png)

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
