# LeetCode-单词搜索和数独

[79. 单词搜索](https://leetcode-cn.com/problems/word-search/)

[212. 单词搜索 II](https://leetcode-cn.com/problems/word-search-ii/)

[36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

[37. 解数独](https://leetcode-cn.com/problems/sudoku-solver/)

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



