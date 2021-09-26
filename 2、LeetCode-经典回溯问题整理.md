# LeetCode-经典回溯问题整理

“N皇后、数独、单词搜索、黄金矿工”等问题，是回溯算法的经典问题，这些问题都是在二维数组上，使用回溯算法寻找组合。

本文整理了 LeetCode 上的相关问题，方便后续复习。

## 问题列表

[51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

[面试题 08.12. 八皇后](https://leetcode-cn.com/problems/eight-queens-lcci/)

[36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

[37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

[79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

[212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)

[1219. 黄金矿工](https://leetcode-cn.com/problems/path-with-maximum-gold/)

------

## [51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

> 相同问题：[面试题 08.12. 八皇后](https://leetcode-cn.com/problems/eight-queens-lcci/)
>
> 相似问题：[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

**n 皇后问题** 研究的是如何将 `n` 个皇后放置在 `n×n` 的棋盘上，并且使皇后彼此之间不能相互攻击。

- 皇后彼此不能相互攻击：任何两个皇后都不能处于同一条横行、纵行或斜线上。

问题描述：

> 给你一个整数 n ，返回所有不同的 “n 皇后问题” 的解决方案。
>
> 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

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

**示例 2：**

```tex
输入：n = 1
输出：[["Q"]]
```

### 解题思路

“N皇后”问题要求解的是，在一个 N*N 的二维数组中，找到N个不同位置上的元素，要求这些元素的位置下标满足以下要求：

- 行下标互不相等
- 列下标互不相等
- 任意两个元素不在同一条斜线上

这其实是在 N * N 的二维数组中寻找满足条件的元素组合。



解题思路也很简单：

1、遍历二维数组 mat 第一行中的每个元素 mat[0][i](i=0,1,3,...,N-1)，以 mat[0][i] 作为组合中的第一个元素；

2、确定了第一个元素后，开始遍历第二行的元素，找到符合条件的第二个元素 mat[1][j](j=0,1,2,3,...,N-1)；

3、依次类推，直到遍历到最后一行的最后一个元素，此时选择的所有元素组成一个有效解。

如果在某一行找不到满足条件的元素，就需要“回退”到上一行，继续上一行中下一个元素的遍历。比如，从 mat[i][j] 到下一行 mat[i+1][...] 寻找元素，如果在 mat[i+1][...] 中找不到有效元素，就需要回退到 mat[i][j]，从 mat[i][j+1] 继续在 mat[i+1][...] 寻找。这是典型的“回溯”操作。



我们定义递归函数 **backtrack(res, path, row)** 表示：从二维数组的 **row** 行开始，寻找满足问题要求的组合，并添加到结果集 **res** 中。

回溯问题的通用求解步骤如下：

**1、选择列表**

整个二维数组。

**2、选择路径**

根据问题要求，选择的元素不能在同一行、同一列、同一个斜线上，所以选择路径中需要记录的有：已选择的元素、已选择元素所在的“行、列、斜线”。



**问题：怎么判断两个元素是否在同一个斜线上？**

如果两个元素在同一条直线上，那么它们的行下标和列下标会满足某个线性关系，如下图所示：

- 左边的斜线满足条件：y=x+k1 --> k1=y-x
- 右边的斜线满足条件：y=-x+k2 --> k2=x+y

因此，通过元素的下标，可以计算出它所在的斜线。

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/imgimage-20210620143856740.png" alt="imgimage-20210620143856740" style="zoom:50%;" />

**3、结束条件**

当行行下标 **row == n** 时，表示已经遍历完最后一行，此时可以结束遍历，把路径中记录的元素添加到结果集中。

**4、选择**

根据问题要求，一个元素满足以下条件时，可以被条件进路径中：

- 所在行、列没有元素被选择进路径中，因为定义的参数中 row 保证了每次递归遍历的元素不在同一行，所以可以不用检查是否在同一行
- 所在斜线上没有元素被选择进路径中

**5、空间状态树**

二维数组的空间状态树比较复杂，这里以动态图片演示“示例1”的求解过程，如下图所示：

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
     * 二维数组单个维度的大小
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

## [52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/) 与 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 求解过程相同，只不过这一题是求解“解决方案的个数”。

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

## [36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

问题描述：

请你判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可：

- 数字 1-9 在每一行只能出现一次。
- 数字 1-9 在每一列只能出现一次。
- 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）

数独部分空格内已填入了数字，空白格用 '.' 表示。

注意：

一个有效的数独（部分已被填充）不一定是可解的。只需要根据以上规则，验证已经填入的数字是否有效即可。

![image-20210926195231317](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210926195231317.png)

### 解题思路

这一题是验证一个已经填入部分数字的 9x9 二维数组，是否是一个有效的数独。

根据数独的规则，1~9 的数字，在每一行、每一列、每一个小九宫格中，只能出现一次。按照这个规则，求解方式就是：

从二维数组 mat 的第一行第一列元素 mat[0][0] 开始遍历，依次判断 1~9 这几个数字在“当前行、当前列、当前九宫格”中是否出现过。

### 代码实现

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        // row[i][n] : 第 i 行中，数字 n-1 出现的次数，columns[i][n] : 第 i 列中，数字 n-1 出现的次数，
        boolean[][] row = new boolean[9][9], col = new boolean[9][9];
        // subMat[i][j][n] : 第 i 行、第 j 列的小九宫格中，数字 n-1 出现的次数
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

## [37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

问题描述：

编写一个程序，通过填充空格来解决数独问题。数独的解法需遵循如下规则：

- 数字 1-9 在每一行只能出现一次。

- 数字 1-9 在每一列只能出现一次。

- 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）

数独部分空格内已填入了数字，空白格用 '.' 表示。

示例：

输入和输出如下所示：

![image-20210926200351825](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210926200351825.png)![image-20210926200525768](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210926200525768.png)

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

这一题与 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 很相似，区别在于：N皇后是中元素不能在斜线上，数独中元素不能在同一个九宫格中。



仿照 [51. N 皇后](https://leetcode-cn.com/problems/n-queens/) 的解题思路来求解这一题，我们定义递归函数 **backtrack(row, col)** 表示：求解二维数组中 “行下标为 row、列下标为 col” 位置处的数字。

回溯算法的求解步骤如下：

**1、选择列表**

1~9 的数字。

**2、选择路径**

根据问题要求，选择的数字不能在同一行、同一列、同一个九宫格中，所以选择路径中需要记录的有：已选择的数字、已选择数字所在的“行、列、九宫格”。

**3、结束条件**

传入的 row 和 col 超出二维数组的有效下标范围（row ≥ N -1,col ≥ N），表示每个下标位置都已经求解完毕了。

**4、选择**

根据问题要求，一个数字满足以下条件时，可以作为该下标位置处的解：

- 当前位置还没有值
- 所在行、列没有相同数字被选中
- 所在九宫格中没有相同数字被选中

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
     * 记录每一行、每一列、每个九宫格中对应
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
        // 已经遍历完所欲位置
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
            // 当前位置没有数字，依次判断每个数字是否能作为该位置的解
            int idx;
            for (char num : nums) {
                idx = num - '0' - 1;
                // 数字已经被选中过
                if (rows[row][idx] || cols[col][idx] || subMat[row / 3][col / 3][idx]) {
                    continue;
                }
                // 设置该位置的数字
                rows[row][idx] = true;
                cols[col][idx] = true;
                subMat[row / 3][col / 3][idx] = true;

                board[row][col] = num;

                backtrack(row, col + 1);
                // 如果没有求解完毕，要继续求解，否则直接退出循环
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
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640%20(1).gif"/>
</div>

## [79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

> 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
>
> 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
>

**示例 1：**

![img](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/word2.jpg)

```tex
输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
输出：true
```

**示例 2：**

![img](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/word-1.jpg)

```tex
输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
输出：true
```

**示例 2：**

![img](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/word3.jpg)

```tex
输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
输出：false
```

### 分析阶段

在二维数组中，找到“连续”的元素，是否能组成目标单词。

1、问题类型

第二类：对某种数结构和算法的使用

使用的算法：在给定的元素集合中，**判断是否存在满足条件的组合**，使用“回溯算法“

数据结构：回溯算法需要构建空间状态树，使用树结构

2、解题思路

“回溯算法”要确定以下条件，然后构建出解集的空间状态树。

（1）选择列表

二维数组中的所有元素。

（2）路径

因为每个元素只能使用一次，所以需要记录已经选择的元素下标；另外，不需要返回结果，所以不需要记录已经选择元素。

**（3）结束条件**

达到什么条件时结束结束当前节点的遍历？

1、已经确定的字母个数，大于等于单词长度，说明能找到目标单词，返回 true

2、当前元素的下标不在二维数组范围内，说明不能找到目标单词，返回false

3、当前元素与指定的字母不相同，说明不能找到目标单词，返回false

4、当前元素已经选择过了，不能再继续遍历下去，返回false

**（4）选择**

什么条件下才把当前元素添加进路径中？

除了上述结束条件外，就可以被选择。



"空间状态树"如下图所示：

![image-20210622233158158](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210622233158158.png)

### 编码阶段

```java
public class Solution {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'}};
        String word = "AAAAAAAAAAAAAAB";
        System.out.println("'========='" + new Solution().exist(board, word));
    }

    public boolean exist(char[][] board, String word) {

        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                boolean result = backtrack(board, word, visited, 0, row, col);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean backtrack(char[][] board, String word, boolean[][] visited, int idx, int row, int col) {
        // 这里要先判断已经确定的字母个数
        if (idx == word.length()) {
            return true;
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        if (visited[row][col]) {
            return false;
        }
        if (board[row][col] != word.charAt(idx)) {
            return false;
        }

        visited[row][col] = true;
        // 左边列的元素
        boolean result = backtrack(board, word, visited, idx + 1, row, col - 1);
        if (result) {
            return true;
        }
        // 右边列的元素
        result = backtrack(board, word, visited, idx + 1, row, col + 1);
        if (result) {
            return true;
        }
        // 上一行的元素
        result = backtrack(board, word, visited, idx + 1, row - 1, col);
        if (result) {
            return true;
        }
        // 下一行的元素
        result = backtrack(board, word, visited, idx + 1, row + 1, col);
        if (result) {
            return true;
        }
        visited[row][col] = false;
        return false;
    }
}
```

在每层递归中，首先要判断是否已经找到所有字母，否则当输入为以下用例时：

> **[["a"]] **
>
> **"a"**

在第二层递归中 row 和 col 都等于1，会直接返回 false，即使已经找到到单词，依然会返回 false。



可以根据[官方题解](https://leetcode-cn.com/problems/word-search/solution/dan-ci-sou-suo-by-leetcode-solution/)对上面的代码中“上下左右元素”部分进行优化，优化后代码如下：

```java
public class Solution {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'A'}};
        String word = "AAAAAAAAAAAAAAB";
        System.out.println("'========='" + new Solution().exist(board, word));
    }

    public boolean exist(char[][] board, String word) {

        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                boolean result = backtrack(board, word, visited, 0, row, col);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean backtrack(char[][] board, String word, boolean[][] visited, int idx, int row, int col) {
        if (idx == word.length()) {
            return true;
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        if (visited[row][col]) {
            return false;
        }
        if (board[row][col] != word.charAt(idx)) {
            return false;
        }

        visited[row][col] = true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] direction : directions) {
            int newi = row + direction[0], newj = col + direction[1];
            boolean flag = backtrack(board, word, visited, idx + 1, newi, newj);
            if (flag) {
                return true;
            }
        }
        visited[row][col] = false;
        return false;
    }
}
```

只是简化了代码样式，性能并没有提升。

总结阶段

依然是在元素集合中找组合，只不过选择条件更加复杂；对于二维数组元素“上下左右”，可以像“官方题解”那样，使用一个二维向量数组表示。

## [212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)



<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

## [1219. 黄金矿工](https://leetcode-cn.com/problems/path-with-maximum-gold/)

> 你要开发一座金矿，地质勘测学家已经探明了这座金矿中的资源分布，并用大小为 m * n 的网格 grid 进行了标注。每个单元格中的整数就表示这一单元格中的黄金数量；如果该单元格是空的，那么就是 0。
>
> 为了使收益最大化，矿工需要按以下规则来开采黄金：
>
> 每当矿工进入一个单元，就会收集该单元格中的所有黄金。
> 矿工每次可以从当前位置向上下左右四个方向走。
> 每个单元格只能被开采（进入）一次。
> 不得开采（进入）黄金数目为 0 的单元格。
> 矿工可以从网格中 任意一个 有黄金的单元格出发或者是停止。

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

**示例 2：**

```tex
输入：grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
输出：28
解释：
[[1,0,7],
 [2,0,6],
 [3,4,5],
 [0,3,0],
 [9,0,20]]
一种收集最多黄金的路线是：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7。
```

![640](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.gif)

### 分析阶段

问题要求：在一个二维数组中，寻找某种组合，使得组合中所有数字的和最大，并且组合中的数字要满足题目的约束条件。

“寻找组合”类的问题，可以使用“回溯算法”求解。

1、问题类型

第二类：对某种数结构和算法的使用

使用的算法：回溯算法

数据结构：回溯算法构造“空间状态树”，使用树形结构

2、解题思路

根据问题要求，从二维数组中每个位置的元素出发，寻找以该元素为起点的组合，并计算组合中所有元素之和，最后选择出和值最大的一个。

我们定义递归函数 **backtrack(grid, row, col)** 表示：从二维数组 **grid** 下标 **(row,col)** 位置处开始，能够得到的最大和值。

![640 (1)](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640 (1).gif)

接下来，依次确定“回溯算法”的必备要素，最后编码实现。

（1）选择列表

整个二维数组。

（2）路径

因为问题不需要记录选择的路径，所以只需要记录路径中所有元素的和值。

（3）结束条件

当下标 **(row,col)** 超出二维数组的有效范围，或者当前元素值为0时，表示遍历结束，返回0值。

（4）选择

每个元素只能被添加一次，所以只要当前元素没有被选择过，就可以添加到路径中。

（5）空间状态树

以“示例1”的输入作为案例，演示求解过程，如下图所示：

![LeetCode-1219](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/LeetCode-1219.gif)

![640](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/640.png)

### 编码阶段

```java
class Solution {
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public int getMaximumGold(int[][] grid) {

        int result = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                result = Math.max(result, backtrack(grid, row, col));
            }
        }
        return result;
    }

    public int backtrack(int[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == 0) {
            return 0;
        }
        // 把 row,col 位置的值设置为0，表示已经添加过，防止重复添加
        int tmp = grid[row][col];
        grid[row][col] = 0;
        int result = 0;
        for (int[] direction : directions) {
            result = Math.max(result, backtrack(grid, row + direction[0], col + direction[1]));
        }
        // 恢复原值
        grid[row][col] = tmp;
        return result + grid[row][col];
    }
}
```

## 总结阶段

“黄金矿工问题”类似于 [LeetCode-79](https://mp.weixin.qq.com/s?__biz=MzAxOTgzMzk0NA==&mid=2247483758&idx=1&sn=6c8a17270e67a1f1aaa5b46ba163cedb&scene=21#wechat_redirect)、[LeetCode-51](http://mp.weixin.qq.com/s?__biz=MzAxOTgzMzk0NA==&mid=2247483756&idx=1&sn=b291b86bcf32ddb7be8263e31f3d2b02&chksm=9bc1b45bacb63d4dc2ecc1ece8ce6fe3ca6161c7580189349cd522420e8c55cc9e837724031d&scene=21#wechat_redirect)，都是在一个二维数组中选择满足约束条件的数字组合，把二维数组中每个位置的元素，分别作为组合的起点，依次找到所有满足条件的组合。
