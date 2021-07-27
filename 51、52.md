# [51. N 皇后](https://leetcode-cn.com/problems/n-queens/)、[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

### [51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

> n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击（任何两个皇后都不能处于同一条横行、纵行或斜线上）。
>
> 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
>
> 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

**示例 1：**

![image-20210714011334053](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210714011334053.png)

```tex
输入：n = 4
输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
解释：如上图所示，4 皇后问题存在两个不同的解法。
```

**示例 2：**

```tex
输入：n = 1
输出：[["Q"]]
```

### [52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

> **n 皇后问题** 研究的是如何将 `n` 个皇后放置在 `n×n` 的棋盘上，并且使皇后彼此之间不能相互攻击。
>
> 给你一个整数 `n` ，返回 **n 皇后问题** 不同的解决方案的数量。

**示例 1：**

```tex
输入：n = 4
输出：2
解释：如上图所示，4 皇后问题存在两个不同的解法。
```

**示例 2：**

```tex
输入：n = 1
输出：1
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.gif"/>
</div>
## 分析阶段

“N皇后”问题是回溯算法的经典问题，问题要求解的是：

在一个 N*N 的二维数组中，找到N个不同位置上的元素组成一个组合，要求这些元素的位置下标满足以下要求：

- 行下标互不相等
- 列下标互不相等
- 任意两元素不在同一条斜线上

典型的“在元素集合中寻找满足条件的组合”。

### 1、问题类型

第二类：对某种数结构和算法的使用

使用的算法：回溯算法

数据结构：回溯算法构造“空间状态树”，使用树形结构

### 2、解题思路

根据问题要求，我们可以从二维数组的第一行开始，遍历第一行的每个元素，分别以第一行的每个元素作为组合的起点，然后在第二行寻找符合条件的第二个元素，接着寻找第三行符合条件的第三个元素，以此类推，直到最后一行。如果组合中所有元素都符合要求，就把组合添加进解集中。

我们定义递归函数 **backtrack(res, path, row)** 表示：从二维数组的 **row** 行开始，寻找满足问题要求的组合，并添加到结果集 **res** 中。

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640%20(1).gif"/>
</div>

接下来，依次确定“回溯算法”的必备要素，最后编码实现。

#### （1）选择列表

整个二维数组。

#### （2）路径

题目要求：组合中的元素不能在同一行、同一类、同一个对角线，所以我们需要记录的数据有：已选择的元素、已选择元素所在的“行、列、斜线”。

在前面的分析中，我们定义函数是“从某一行开始选择一个元素，然后遍历下面的一行选择下一个元素”，可以避免元素之间行下标冲突，所以不需要记录路径中元素所在的行。



**问题：怎么判断两个元素是否在同一个斜线上？**

如果两个元素在同一条直线上，那么它们的行下标和列下标会满足某个线性关系，如下图所示：

- 左边的斜线满足条件：y=x+k1 --> k1=y-x
- 右边的斜线满足条件：y=-x+k2 --> k2=x+y

所以，通过元素的行下标、列下标，可以计算出它所在的斜线。

![imgimage-20210620143856740](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/imgimage-20210620143856740.png)

#### （3）结束条件

当行下标 **row == n** 时，表示已经遍历完每一行，遍历结束，把路径添加进结果集中。

#### （4）选择

根据题目要求，一个元素满足以下条件时，可以被条件进路径中：

- 所在列没有元素被选择进路径中
- 所在斜线上没有元素被选择进路径中

#### （5）空间状态树

以“示例1”的输入作为案例，演示求解过程，如下图所示：

![LeetCode-51](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/LeetCode-51.gif)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png"/>
</div>
## 编码阶段

### LeetCode-51

```java
class Solution {
    // 已经选择的斜线
    Set<Integer> k1 = new HashSet<>();
    Set<Integer> k2 = new HashSet<>();
    // 记录已经选择的列
    boolean[] cols;
    // 棋盘大小
    int n;

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        List<Node> nodes = new LinkedList<>();

        this.cols = new boolean[n];
        this.n = n;

        backtrack(res, nodes, 0);

        return res;
    }

    public void backtrack(List<List<String>> res, List<Node> nodes, int row) {
        if(row == n) {
            List<String> str = nodes.stream().map(o -> o.toString(n)).collect(Collectors.toList());
            res.add(str);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (cols[col]) {
                continue;
            }

            Node node = new Node(row, col);
            if (k1.contains(node.getK1())) {
                continue;
            }
            if (k2.contains(node.getK2())) {
                continue;
            }
            // 把当前位置的节点添加到路径中
            nodes.add(node);
            cols[col] = true;
            k1.add(node.getK1());
            k2.add(node.getK2());

            backtrack(res, nodes, row + 1);
            // 从路径中撤销节点
            nodes.remove(nodes.size() - 1);
            cols[col] = false;
            k1.remove(node.getK1());
            k2.remove(node.getK2());
        }
    }

    /**
     * 自定义类，表示二维数组中每个位置上的节点
     */
    public static class Node {
        int row;
        int col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getK1() {
            return row + col;
        }

        public int getK2() {
            return row - col;
        }

        public String toString(int n) {
            char[] chs = new char[n];
            Arrays.fill(chs, '.');
            chs[col] = 'Q';
            return new String(chs);
        }
    }
}
```

### LeetCode-52

LeetCode-52 求解的问题和 LeetCode-51 相同，只不过只返回最终解的个数，所以不需要记录“选择的元素路径和解集”，在达到结束条件时，返回组合个数1。

代码如下：

```java
class Solution {
    // 已经选择的斜线
    Set<Integer> k1 = new HashSet<>();
    Set<Integer> k2 = new HashSet<>();
    // 记录已经选择的列
    boolean[] cols;
    // 棋盘大小
    int n;

    public int totalNQueens(int n) {
        this.cols = new boolean[n];
        this.n = n;

        return backtrack(0);
    }

    public int backtrack(int row) {
        if (row == n) {
            return 1;
        }

        int result = 0;
        for (int col = 0; col < n; col++) {
            if (cols[col]) {
                continue;
            }

            Node node = new Node(row, col);
            if (k1.contains(node.getK1())) {
                continue;
            }
            if (k2.contains(node.getK2())) {
                continue;
            }
            // 把当前位置的节点添加到路径中
            cols[col] = true;
            k1.add(node.getK1());
            k2.add(node.getK2());

            result += backtrack(row + 1);
            // 从路径中撤销节点
            cols[col] = false;
            k1.remove(node.getK1());
            k2.remove(node.getK2());
        }
        return result;
    }

    /**
     * 自定义类，表示二维数组中每个位置上的节点
     */
    public static class Node {
        int row;
        int col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getK1() {
            return row + col;
        }

        public int getK2() {
            return row - col;
        }
    }
}
```

## 总结阶段

“N皇后”问题是经典的回溯问题，只要按照回溯问题的求解步骤，依次确定各个要素，就能很简单地求解。