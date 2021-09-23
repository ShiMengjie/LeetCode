# LeetCode-N皇后、数独和单词搜索问题

LeetCode 上的“N皇后、数独、单词搜索问题”，可以使用回溯算法在二维数组上寻找有效路径，进而求解。

因此，本文整理 LeetCode 上的相关问题和求解过程，方便后续复习。

## 问题列表

[51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

[52. N皇后 II](https://leetcode-cn.com/problems/n-queens-ii/)

[36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

[37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

[79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

[212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)

## [51. N 皇后](https://leetcode-cn.com/problems/n-queens/)

**n 皇后问题** 研究的是如何将 `n` 个皇后放置在 `n×n` 的棋盘上，并且使皇后彼此之间不能相互攻击。

- 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。

问题描述：

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

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

“N皇后”问题求解的结果是：

在一个 N*N 的二维数组中，找到N个不同位置上的元素，要求这些元素的位置下标满足以下要求：

- 行下标互不相等
- 列下标互不相等
- 任意两个元素不在同一条斜线上

这是回溯问题中典型的“组合”问题。

### 解题思路

1、遍历二维数组“mat" 第一行中的每个元素 mat[0][i](i=0,1,3,...,N-1)，以 mat[0][i] 作为组合的起点；

2、确定了第一个元素后，在第二行开始遍历，找到符合条件的第二个元素 mat[1][j](j=0,1,2,3,...,N-1)；

3、依次类推，直到遍历到最后一行，此时选择的所有元素组成一个有效解。

这种不断试探，

我们定义递归函数 **backtrack(res, path, row)** 表示：从二维数组的 **row** 行开始，寻找满足问题要求的组合，并添加到结果集 **res** 中。

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640%20(1).gif"/>
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

<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/imgimage-20210620143856740.png" alt="imgimage-20210620143856740" style="zoom:50%;" />

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





## [36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

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



![image-20210707233403862](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233403862.png)

![image-20210707233431438](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233431438.png)

![image-20210707233450969](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233450969.png)

![image-20210707233527767](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233527767.png)

![image-20210707233539788](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233539788.png)

![image-20210707233553701](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233553701.png)

![image-20210707233616214](https://cdn.jsdelivr.net/gh/shimengjie/image-repo//img/image-20210707233616214.png)



```java
public class Solution {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        new Solution().solveSudoku(board);
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println("");
        }
    }

    private final char[] nums = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private boolean res = false;
    boolean[][] rows, cols, pads;

    public void solveSudoku(char[][] board) {
        // 分别记录每一行、每一列、每个九宫格已经存在的数字
        rows = new boolean[board.length][9];
        cols = new boolean[board[0].length][9];
        pads = new boolean[9][9];
        // 记录当前输入的二维数组的初始状态
        int index;
        char ch;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ch = board[i][j];
                if (ch != '.') {
                    index = ch - '0' - 1;
                    rows[i][index] = true;
                    cols[j][index] = true;
                    pads[i / 3 * 3 + j / 3][index] = true;
                }
            }
        }
        // 回溯求解
        backtrack(board, 0, 0);
    }

    private void backtrack(char[][] board, int row, int col) {
        if (row == board.length - 1 && col == board[0].length) {
            res = true;
            return;
        }
        if (col == board[0].length) {
            row = row + 1;
            col = 0;
        }
        if (board[row][col] != '.') {
            backtrack(board, row, col + 1);
        } else {
            int idx;
            for (char num : nums) {
                idx = num - '0' - 1;
                if (rows[row][idx] || cols[col][idx] || pads[row / 3 * 3 + col / 3][idx]) {
                    continue;
                }

                rows[row][idx] = true;
                cols[col][idx] = true;
                pads[row / 3 * 3 + col / 3][idx] = true;
                board[row][col] = num;

                backtrack(board, row, col + 1);

                if (!res) {
                    board[row][col] = '.';

                    rows[row][idx] = false;
                    cols[col][idx] = false;
                    pads[row / 3 * 3 + col / 3][idx] = false;
                } else {
                    break;
                }
            }
        }
    }
}
```





```java
public class Solution {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        new Solution().solveSudoku(board);
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println("");
        }
    }

    private final char[] nums = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private boolean valid = false;

    public void solveSudoku(char[][] board) {
        // 分别记录每一行、每一列、每个九宫格已经存在的数字
        boolean[][] line = new boolean[board.length][10], column = new boolean[board[0].length][10], pad = new boolean[9][10];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != '.') {
                    line[row][board[row][col] - '0'] = true;
                    column[col][board[row][col] - '0'] = true;
                    pad[row / 3 * 3 + col / 3][board[row][col] - '0'] = true;
                }
            }
        }

        backtrack(board, 0, 0, line, column, pad);
    }

    private void backtrack(char[][] board, int row, int col, boolean[][] line, boolean[][] column, boolean[][] pad) {
        if (row == board.length - 1 && col == board[0].length) {
            valid = true;
            return;
        }
        if (col == board[0].length) {
            row = row + 1;
            col = 0;
        }
        if (board[row][col] != '.') {
            backtrack(board, row, col + 1, line, column, pad);
        } else {
            for (char num : nums) {

                if (line[row][num - '0'] || column[col][num - '0'] || pad[row / 3 * 3 + col / 3][num - '0']) {
                    continue;
                }

                line[row][num - '0'] = true;
                column[col][num - '0'] = true;
                pad[row / 3 * 3 + col / 3][num - '0'] = true;
                board[row][col] = num;
                backtrack(board, row, col + 1, line, column, pad);
                if (!valid) {
                    board[row][col] = '.';

                    line[row][num - '0'] = false;
                    column[col][num - '0'] = false;
                    pad[row / 3 * 3 + col / 3][num - '0'] = false;
                }
            }
        }
    }
}
```

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

## 分析阶段

在二维数组中，找到“连续”的元素，是否能组成目标单词。

### 1、问题类型

第二类：对某种数结构和算法的使用

使用的算法：在给定的元素集合中，**判断是否存在满足条件的组合**，使用“回溯算法“

数据结构：回溯算法需要构建空间状态树，使用树结构

### 2、解题思路

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

## 编码阶段

### 基础版本

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

### 简化版本

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

## 总结阶段

依然是在元素集合中找组合，只不过选择条件更加复杂；对于二维数组元素“上下左右”，可以像“官方题解”那样，使用一个二维向量数组表示。
