# LeetCode-回溯算法典型问题整理1

“N皇后、数独、单词搜索、黄金矿工”等问题，是典型的回溯问题，这些问题都是使用回溯算法在二维数组上寻找满足条件的组合。

本文整理了 LeetCode 上与之相关的问题，方便后续复习。

## 问题列表

[51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

[37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

[79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

[212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)

[1219. 黄金矿工](https://leetcode-cn.com/problems/path-with-maximum-gold/)

相似问题：

[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

[36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

------

## [51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

> 相同问题：[面试题 08.12. 八皇后](https://leetcode-cn.com/problems/eight-queens-lcci/)

问题描述：

**n 皇后问题** 研究的是如何将 `n` 个皇后放置在 `n×n` 的棋盘上，并且使皇后彼此之间不能相互攻击。

> 皇后彼此不能相互攻击：任何两个皇后都不能处于同一条横行、纵行或斜线上。

给你一个整数 n ，返回所有不同的 “n 皇后问题” 的解决方案。

每一种解法包含一个不同的 “n 皇后问题” 的放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

**示例 1：**

![image-20210714011334053](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210714011334053.png)

```tex
输入：n = 4
输出：[
[
".Q..",
"...Q",
"Q...",
"..Q."
],
[
"..Q.",
"Q...",
"...Q",
".Q.."]
]
解释：如上图所示，4 皇后问题存在2个不同的解法。
```

### 解题思路

“N皇后”问题要求解的是，在一个 $N \times N$ 的二维数组中，找到 N 个不同位置上的元素，要求这些元素的位置下标同时满足以下条件：

- 行下标互不相等
- 列下标互不相等
- 任意两个元素不在同一条斜线上

其实就是在 $N \times N$ 的二维数组中寻找满足条件的元素组合。



解题思路也很简单：

1、遍历二维数组 $mat$ 第一行中的每个元素 $mat[0][i],i=0,1,3,...,N-1$，以 $mat[0][i]$ 作为组合中的第一个元素；

2、确定了第一个元素后，开始依次遍历第二行的元素 $mat[1][j],j=0,1,2,3,...,N-1$，从中找到符合条件的第二个元素 ；

3、依次类推，直到遍历到最后一行的最后一个元素，此时选择的所有元素组成一个有效解。

这是一种暴力求解思路，回溯算法本质上就是一种暴力算法，只不过在暴力求解的过程中，会及时停止对无效情况的遍历，降低复杂度。

在上面的遍历过程中，如果在某一行找不到满足条件的元素，就“回退”到上一行，不会在继续向下一行寻找。例如，如果在 $mat[i+1][...]$ 找不到符合条件的元素，就会回退到  $mat[i][...]$ 行，而不是继续向  $mat[i+2][...]$ 行遍历。



我们定义递归函数 $backtrack(res, path, row)$ 表示：在二维数组的第 $row$ 行中，寻找满足问题要求的元素添加到列表 $path$ 中，如果找到一个有效解，就把 $path$ 添加到结果集 $res$ 中。

回溯算法的通用求解步骤如下：

**1、选择列表**

整个二维数组。

**2、选择路径**

根据问题要求，选择的元素不能在“同一行、同一列、同一个斜线”上，所以选择路径中需要记录的有：已选中的元素下标、已选择元素所在的“行、列、斜线”。

因为函数的参数 $row$ 保证了每次递归时，遍历的元素都不在同一行，所以可以不用记录已选中元素所在的行。

**3、结束条件**

当行下标 $row == n$ 时，表示已经遍历完二维数组中的所有元素，此时可以结束遍历，$path$ 就是一个有效解。

**4、选择**

根据问题要求，一个元素满足以下条件时，可以被条件进路径中：

- 所在行、列没有元素被选中进路径
- 所在斜线上没有元素被选中进路径



**问题：怎么判断两个元素是否在同一个斜线上？**

如果两个元素在同一条直线上，那么它们的行下标和列下标会满足某个线性关系，如下图所示：

- 左边的斜线满足条件：$y=x+k1 \, -> k1=y-x$
- 右边的斜线满足条件：$y=-x+k2 \,-> k2=x+y$

因此，通过元素的下标可以计算出它所在的斜线。

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/imgimage-20210620143856740.png" alt="imgimage-20210620143856740" style="zoom:50%;"/>

**5、空间状态树**

这里以动态图片演示“示例1”的求解过程，如下图所示：

![screenshot-20210926-193707](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/screenshot-20210926-193707.gif)

### 代码实现

```java
class Solution {

    /**
     * 已经选择的斜线 k1 和 k2
     */
    Set<Integer> k1 = new HashSet<>(), k2 = new HashSet<>();
    /**
     * 记录已经选择的列
     */
    boolean[] cols;
    /**
     * 传入的参数 n
     */
    int n;

    public List<List<String>> solveNQueens(int n) {
        this.cols = new boolean[n];
        this.n = n;

        List<List<String>> res = new LinkedList<>();
        backtrack(res, new ArrayList<>(n), 0);

        return res;
    }

    public void backtrack(List<List<String>> res, List<QueenNode> path, int row) {
        // 触发结束条件，把 path 中的元素作为解，添加到解集合中
        if (row == n) {
            List<String> list = new ArrayList<>(path.size());
            for (QueenNode queenNode : path) {
                list.add(queenNode.toString(n));
            }
            res.add(list);
            return;
        }
        // 遍历当前行的元素
        for (int col = 0; col < n; col++) {
            // 如果所在列，已经有元素被添加进 path 中，就跳过
            if (cols[col]) {
                continue;
            }
            // 如果所在斜线上，已经有元素被选择进 path 中，就跳过
            QueenNode node = new QueenNode(row, col);
            if (k1.contains(node.getK1())) {
                continue;
            }
            if (k2.contains(node.getK2())) {
                continue;
            }
            // 把当前位置的节点添加到路径中
            path.add(node);
            cols[col] = true;
            k1.add(node.getK1());
            k2.add(node.getK2());

            backtrack(res, path, row + 1);
            // 从路径中撤销节点
            path.remove(path.size() - 1);
            cols[col] = false;
            k1.remove(node.getK1());
            k2.remove(node.getK2());
        }
    }

}

/**
 * 表示二维数组中每个位置上的元素
 */
class QueenNode {
    int row, col, k1, k2;

    public QueenNode(int row, int col) {
        this.row = row;
        this.col = col;
        this.k1 = row + col;
        this.k2 = row - col;
    }

    public int getK1() {
        return k1;
    }

    public int getK2() {
        return k2;
    }

    public String toString(int n) {
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            if (i == col) {
                chs[i] = 'Q';
            } else {
                chs[i] = '.';
            }
        }
        return new String(chs);
    }
}
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png"/>
</div>

## [37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

问题描述：

编写一个程序，通过填充空格来解决数独问题。数独的解法需遵循如下规则：

- 数字 1-9 在每一行只能出现一次。

- 数字 1-9 在每一列只能出现一次。

- 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）

数独部分空格内已填入了数字，空白格用 '.' 表示。

示例：

输入和输出如下所示：

![image-20210928192705825](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210928192705825.png)

```shell
输入：board = [
["5","3",".",".","7",".",".",".","."],
["6",".",".","1","9","5",".",".","."],
[".","9","8",".",".",".",".","6","."],
["8",".",".",".","6",".",".",".","3"],
["4",".",".","8",".","3",".",".","1"],
["7",".",".",".","2",".",".",".","6"],
[".","6",".",".",".",".","2","8","."],
[".",".",".","4","1","9",".",".","5"],
[".",".",".",".","8",".",".","7","9"]
]

输出：[
["5","3","4","6","7","8","9","1","2"],
["6","7","2","1","9","5","3","4","8"],
["1","9","8","3","4","2","5","6","7"],
["8","5","9","7","6","1","4","2","3"],
["4","2","6","8","5","3","7","9","1"],
["7","1","3","9","2","4","8","5","6"],
["9","6","1","5","3","7","2","8","4"],
["2","8","7","4","1","9","6","3","5"],
["3","4","5","2","8","6","1","7","9"]
]
```

### 解题思路

这一题与 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 很相似，区别在于：N皇后是元素不能在同一条斜线上，数独中数字不能重复出现在同一个九宫格中，仿照 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 的解题思路来求解这一题。



我们定义递归函数 $backtrack(row, col)$ 表示：从二维数组中 “行下标为 row、列下标为 col” 位置开始求解数独。

回溯算法的求解步骤如下：

**1、选择列表**

1~9 的数字。

**2、选择路径**

根据问题要求，选择的数字不能在同一行、同一列、同一个九宫格中，所以选择路径中需要记录的有：每一行已选择的数字、每一列已选择的数字、每一个九宫格已经选择的数字。

**3、结束条件**

传入的 $row$ 和 $col$ 超出二维数组的有效下标范围 $(row ≥ N -1,col ≥ N)$，表示每个下标位置都已经遍历过了。

**4、选择**

根据问题要求，一个数字满足以下条件时，可以作为该下标位置处的解：

- 当前位置还没有值
- 所在行、列没有相同数字被选中过
- 所在九宫格没有相同数字被选中过

**5、空间状态树**

状态树比较复杂，过程与 N皇后 问题相似，这里就不展示了。

### 代码实现

```java
class Solution {
    /**
     * 选择列表
     */
    private final char[] nums = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * 是否已经求解成功
     */
    private boolean res = false;
    /**
     * 记录每一行、每一列、每个九宫格中已数字是否已经选中
     */
    boolean[][] rows = new boolean[9][9], cols = new boolean[9][9];
    boolean[][][] subMat = new boolean[3][3][9];
    char[][] board;

    public void solveSudoku(char[][] board) {
        this.board = board;
        // 记录当前输入的二维数组的初始状态
        int idx;
        char ch;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ch = board[i][j];
                if (ch != '.') {
                    idx = ch - '0' - 1;
                    rows[i][idx] = true;
                    cols[j][idx] = true;
                    subMat[i / 3][j / 3][idx] = true;
                }
            }
        }
        // 回溯求解
        backtrack(0, 0);
    }

    private void backtrack(int row, int col) {
        // 已经遍历完所有位置
        if (row == board.length - 1 && col == board[0].length) {
            res = true;
            return;
        }

        // 当前行遍历完毕，开始遍历下一行
        if (col == board[0].length) {
            row = row + 1;
            col = 0;
        }

        // 当前位置已经数字了，跳过，继续遍历下一个位置
        if (board[row][col] != '.') {
            backtrack(row, col + 1);
        } else {
            // 当前位置没有数字，判断每个数字是否能放置在该位置
            int idx;
            for (char num : nums) {
                idx = num - '0' - 1;
                // 数字已经被选中过
                if (rows[row][idx] || cols[col][idx] || subMat[row / 3][col / 3][idx]) {
                    continue;
                }
                // 设置行、列、九宫格的数字选中状态
                rows[row][idx] = true;
                cols[col][idx] = true;
                subMat[row / 3][col / 3][idx] = true;

                board[row][col] = num;

                backtrack(row, col + 1);
                // 如果已经得到答案，退出循环，否则撤销选择
                if (!res) {
                    board[row][col] = '.';

                    rows[row][idx] = false;
                    cols[col][idx] = false;
                    subMat[row / 3][col / 3][idx] = false;
                } else {
                    break;
                }
            }
        }
    }
}
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.png"/>
</div>

## [79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

问题描述：

给定一个 $m \times n$ 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

**示例 1：**

![img](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/word2.jpg)

```tex
输入：board = [
["A","B","C","E"],
["S","F","C","S"],
["A","D","E","E"]], 
word = "ABCCED"
输出：true
```

### 解题思路

在一个二维数组中，寻找连续“相邻”的元素，组成目标字符串 $word$。

最直接的暴力求解方法是：遍历二维数组中每个元素，以每个元素分别作为一个单词的起点字符，生成以该元素为起点的所有字符串，判断其中是否有字符串与 $word$  相等。

这种做法是一种暴力求解的思路，可以使用回溯来提升求解速度。在暴力求解的过程中，如果发现当前字符串中部分字符已经与 $word$ 不相等了，就可以不用再继续生成该字符串了。比如，在示例中，输入的 word = "ABCCED"，以第一行第二元素 B 为起点的字符串肯定是不符合要求的，可以直接跳过，继续遍历下一个满足条件的元素。



我们定义函数 $backtrack(row,col,len)$，表示：从下标 $(row,col)$ 处开始，是否能够寻找到组成目标字符串 $word$ 的字符， $len$ 表示已经选中的字符个数。

回溯算法的求解步骤如下：

**1、选择列表**

二维数组中的所有元素。

**2、选择路径**

因为每个元素只能使用一次，所以需要记录每个下标是否已经被选择过；问题不需要返回结果，所以不需要记录已经选择元素。

**3、结束条件**

什么时候结束遍历？

- 已选择的字符个数 $\ge$ 大于等于单词长度，说明已经找到目标单词，返回 true
- 当前元素的下标不在二维数组范围内，说明已经遍历完所有元素，且不能找到目标单词，返回 false

**4、选择**

满足什么条件的元素可以被添加进路径中？同时满足以下条件的字符，可以被选中添加进路径：

- 当前元素与前一个元素“相邻”
- 当前元素未被选择过
- 当前元素与 $word$ 对应位置的字符相同

**5、空间状态树**

"空间状态树"如下图所示：

![image-20210927164503535](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210927164503535.png)

### 代码实现

```java
public class Solution {

    char[][] board;
    String word;
    /**
     * 记录二维数组每个下标处的元素是否已经选择过
     */
    boolean[][] visited;
    /**
     * 遍历的方向
     */
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        this.visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                boolean result = backtrack(row, col, 0);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean backtrack(int row, int col, int len) {
        // 已选择的字符个数
        if (len == word.length()) {
            return true;
        }
        // 当前下标不在数组范围内
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        // 当前下标的元素已经选择过、不等于单词对应位置处的字符
        if (visited[row][col] || board[row][col] != word.charAt(len)) {
            return false;
        }

        // 选择该位置的字符
        visited[row][col] = true;

        for (int[] direction : directions) {
            int newRow = row + direction[0], newCol = col + direction[1];
            boolean flag = backtrack(newRow, newCol, len + 1);
            if (flag) {
                return true;
            }
        }
        // 撤销选择
        visited[row][col] = false;
        // 返回 false，表示以当前元素为起点，找不到有效解
        return false;
    }
}
```

------

## [212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)

问题描述：

给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词。

单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

**示例1：**

![image-20210927171601818](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210927171601818.png)

```shell
输入：board = [
["o","a","a","n"],
["e","t","a","e"],
["i","h","k","r"],
["i","f","l","v"]
], 
words = ["oath","pea","eat","rain"]
输出：["eat","oath"]
```

### 解题思路

这一题与 [79. 单词搜索](https://leetcode-cn.com/problems/word-search/) 的求解过程相似，我们可以依次判断 $words$ 中每个单词是否能在二维数组中找到，如果能在二维数组中找到就添加进结果集。

这种方法是可以得到答案，但是存在问题：每个单词都要对二维数组做相同的回溯过程，导致了大量重复的工作。

问题给的条件是：

-  $words$ 中单词数量最大有 $10^4$ 个，按照上面的解法，要做 $10^4$ 次重复的操作
- 每个单词不超过10个字符

因此，我们可以对上面的解法做一些优化：

- 对二维数组做一次回溯遍历，得到所有字符串集合，从中找到存在于  $words$ 的字符串
- 在回溯遍历二维数组时，如果已经选择的字符个数大于等于10，就可以结束遍历。

### 代码实现

把  [79. 单词搜索](https://leetcode-cn.com/problems/word-search/) 代码稍作修改，实现如下。

```java
public class Solution {

    char[][] board;
    /**
     * 记录对应位置上的元素是否已经选择过
     */
    boolean[][] visited;
    /**
     * 遍历的方向
     */
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * 记录要查询的字符串
     */
    Set<String> set;

    /**
     * 结果集
     */
    List<String> res = new LinkedList<>();

    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        this.visited = new boolean[board.length][board[0].length];
        this.set = new HashSet<>(words.length);
        // 把输入的字符串添加进集合中
        set.addAll(Arrays.asList(words));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                backtrack(row, col, new StringBuilder());
            }
        }

        return res;
    }

    public void backtrack(int row, int col, StringBuilder sb) {
        // 如果二维数组中拼接的字符串在 set 中，表明是要查找的字符串，保存进结果集中，并从 set 中移除
        // 问题限制了，单词长度不会超过 10
        if (sb.length() <= 10) {
            if (set.contains(sb.toString())) {
                res.add(sb.toString());
                set.remove(sb.toString());
            }
        }else {
            return;
        }
        // 当前下标不在数组范围内
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }
        // 当前下标的元素已经选择过
        if (visited[row][col]) {
            return;
        }

        // 选择该位置的字符
        visited[row][col] = true;
        sb.append(board[row][col]);

        for (int[] direction : directions) {
            int newRow = row + direction[0], newCol = col + direction[1];
            backtrack(newRow, newCol, sb);
        }
        // 撤销选择
        visited[row][col] = false;
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png"/>
</div>

## [1219. 黄金矿工](https://leetcode-cn.com/problems/path-with-maximum-gold/)

问题描述：

你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 $m \times n$ 的网格 grid 进行了标注。每个单元格中的整数就表示这一单元格中的黄金数量；如果该单元格是空的，那么就是 0。

为了使收益最大化，矿工需要按以下规则来开采黄金：

- 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
- 矿工每次可以从当前位置向上下左右四个方向走。
- 每个单元格只能被开采（进入）一次。
- 不得开采（进入）黄金数目为 0 的单元格。
- 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。

**示例 1：**

```tex
输入：grid = [[0,6,0],[5,8,7],[0,9,0]]
输出：24
解释：
[[0,6,0],
 [5,8,7],
 [0,9,0]]
一种收集最多黄金的路线是：9 -> 8 -> 7。
```

### 解题思路

在一个二维数组中，寻找"相邻"的元素组合，使得所有元素的和值，在所有组合中是最大的。

可以像 [79. 单词搜索](https://leetcode-cn.com/problems/word-search/) 那样，遍历二维数组的每个元素，分别以每个元素作为组合的起点，找到所有的组合结果，从中选择和值最大的。



我们定义递归函数 $backtrack(grid, row, col)$，表示从二维数组 **grid** 下标 $(row,col)$ 处开始始，能够得到的最大和值。

回溯算法求解步骤如下：

**1、选择列表**

整个二维数组。

**2、选择路径**

因为二维数组中的元素，在一个组合中，不能重复使用，所以要记录每个下标位置处的元素是否已经被选择过。

因为问题不要求返回结果组合中的元素，所以不需要记录选择元素，只需要记录已选择元素的和值。

**3、结束条件**

当下标 $(row,col)$ 超出二维数组的有效范围，或者当前元素值为0时，表示遍历结束，返回0值。

**4、选择**

当一个元素没有被选择过、该元素值不为0时，该元素可以被添加进路径中。

**5、空间状态树**

以“示例1”的输入作为案例，演示求解过程，如下图所示：

![screenshot-20210927-174733](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/screenshot-20210927-174733.gif)

### 编码阶段

```java
class Solution {
	// 遍历方向
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    // 记录每个下标是否已经选择过
    boolean[][] visited;

    public int getMaximumGold(int[][] grid) {

        visited = new boolean[grid.length][grid[0].length];

        int result = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                result = Math.max(result, backtrack(grid, row, col));
            }
        }
        return result;
    }

    public int backtrack(int[][] grid, int row, int col) {
        // 下标已经超出二维数组范围、或者当前位置为0值
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == 0) {
            return 0;
        }
        // 已经选择过该位置的值
        if (visited[row][col]) {
            return 0;
        }
        // 选择该位置
        visited[row][col] = true;

        int result = 0;
        for (int[] direction : directions) {
            result = Math.max(result, backtrack(grid, row + direction[0], col + direction[1]));
        }
        // 撤销选择
        visited[row][col] = false;
        // 加上当前元素的值
        return result + grid[row][col];
    }
}
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png"/>
</div>

## 相似问题

### [52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

这一题的求解过程与 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 相似，区别在于：这一题是求解求解有效解的个数，只需要对前面的代码稍作修改就可以了。

因此，不需要记录选择路径和结果集，只需要在到达结束条件时，给方案个数加1。

代码实现如下：

```java
class Solution {

    /**
     * 已经选择的斜线 k1 和 k2
     */
    Set<Integer> k1 = new HashSet<>(), k2 = new HashSet<>();
    /**
     * 记录已经选择的列
     */
    boolean[] cols;
    /**
     * 二维数组单个维度的大小
     */
    int n;

    /**
     * 有效解法个数
     */
    int res = 0;


    public int totalNQueens(int n) {
        this.cols = new boolean[n];
        this.n = n;

        backtrack(0);

        return res;
    }

    public void backtrack(int row) {
        if (row == n) {
            res++;
            return;
        }
        // 遍历当前行的元素
        for (int col = 0; col < n; col++) {
            // 如果所在列，已经有元素遍历过了，就跳过
            if (cols[col]) {
                continue;
            }
            // 如果所在斜线上，已经有元素遍历过了，就跳过
            QueenNode node = new QueenNode(row, col);
            if (k1.contains(node.getK1())) {
                continue;
            }
            if (k2.contains(node.getK2())) {
                continue;
            }
            // 设置对应的列和斜线
            cols[col] = true;
            k1.add(node.getK1());
            k2.add(node.getK2());

            backtrack(row + 1);
            // 取消对应的列和斜线
            cols[col] = false;
            k1.remove(node.getK1());
            k2.remove(node.getK2());
        }
    }
}

/**
 * 表示二维数组中每个位置上的元素
 */
class QueenNode {
    int row, col, k1, k2;

    public QueenNode(int row, int col) {
        this.row = row;
        this.col = col;
        this.k1 = row + col;
        this.k2 = row - col;
    }

    public int getK1() {
        return k1;
    }

    public int getK2() {
        return k2;
    }
}
```

------

## [36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

问题描述：

请你判断一个 $9 \times 9$ 的数独是否有效，只需要根据以下规则，验证已经填入的数字是否有效即可：

- 数字 1-9 在每一行只能出现一次。
- 数字 1-9 在每一列只能出现一次。
- 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）

数独部分空格内已填入了数字，空白格用 '.' 表示。

注意：

一个有效的数独（部分已被填充）不一定是可解的。只需要根据以上规则，验证已经填入的数字是否有效即可。

![image-20210926195231317](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210926195231317.png)

### 解题思路

问题是要验证一个已经填入部分数字的 $9 \times 9$ 二维数组，是否是满足数独的条件。

根据数独的规则，在每一行、每一列、每一个小九宫格中，数字 1~9 只能出现一次。

按照这个规则，求解方式就是：

从二维数组 $mat$ 的第一行第一列元素 $mat[0][0]$ 开始遍历，依次判断数字 1~9 是否在“当前行、当前列、当前九宫格”重复出现。

### 代码实现

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // row[i][n] : 第 i 行中，数字 n-1 是否出现过，columns[i][n] : 第 i 列中，数字 n-1 是否出现过
        boolean[][] row = new boolean[9][9], col = new boolean[9][9];
        // subMat[i][j][n] : 第 i 行、第 j 列的小九宫格中，数字 n-1 是否出现过
        boolean[][][] subMat = new boolean[3][3][9];

        char ch;
        int index;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                ch = board[i][j];
                if (ch != '.') {
                    index = ch - '0' - 1;
                    // 中途某个数字不合法，直接返回 false
                    if (row[i][index] || col[j][index] || subMat[i / 3][j / 3][index]) {
                        return false;
                    }
                    row[i][index] = true;
                    col[j][index] = true;
                    subMat[i / 3][j / 3][index] = true;
                }
            }
        }
        return true;
    }
}
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png"/>
</div>

## 总结

从前面的“二位数组“问题可以发现，在使用回溯算法求解时，都需要遍历二维数组的所有元素。

这种求解方式与暴力求解方式很相似，区别在于，回溯算法在“遇到不满足条件”的情况时，会回退到上一级或提前终止遍历，避免了无效计算。
