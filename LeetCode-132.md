# [132. 分割回文串 II](https://leetcode-cn.com/problems/palindrome-partitioning-ii/)

> 给你一个字符串 `s`，请你将 `s` 分割成一些子串，使每个子串都是回文。
>
> 返回符合要求的 **最少分割次数** 。

 **示例 1：**

```tex
输入：s = "aab"
输出：1
解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
```

**示例 2：**

```tex
输入：s = "a"
输出：0
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

# 分析阶段

> 动态规划

把字符串分割成多个子串，要求所有子串是回文串，返回最少的分割次数。最直接的做法是，把所有的子串都列举出来，从中找到最少的一种组合。题目 [131. 分割回文串](https://leetcode-cn.com/problems/palindrome-partitioning/) 就是找到所有的回文串组合，但是使用这种方法在 LeetCode 提交结果如下，需要使用新的解法：

![image-20210807200228889](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210807191921048.png)

假设字符串 $s$ 只有一种分割方法，如下所示：

$s[0,i_1],s[i_1+1,i_2],s[i_2+1,i_3],...,s[i_{k-1},i_k],s[i_k+1,n-1]$，分割次数 $k$

可以看出，如果 $s[i_k+1,n-1]$ 是回文串，那么$s[0,n-1]$的最少分割次数就是 "$s[0,k]$的最少分割次数加1"。

如果字符串$s$有多种分割方法，假设有三种方法，如下所示：

1、$s[0,i_{11}],s[i_{11}+1,i_12],s[i_{12}+1,i_{13}],...,s[i_{k1-1},i_{k1}],s[i_{k1}+1,n-1]$，分割次数 $k1$

2、$s[0,i_{21}],s[i_{21}+1,i_{22}],s[i_{22}+1,i_{23}],...,s[i_{k2-1},i_{k2}],s[i_{k2}+1,n-1]$，分割次数 $k2$

3、$s[0,i_{31}],s[i_{31}+1,i_{32}],s[i_{32}+1,i_{33}],...,s[i_{k3-1},i_{k3}],s[i_{k3}+1,n-1]$，分割次数 $k3$

要找到最少的分割次数，就是在 $k1、k2、k3$ 中找到最小值，而  $k1、k2、k3$ 分别是$s[0,i_{k1}]、s[0,i_{k2}]、s[0,i_{k3}]$ 的最少分割次数加1。

因此，问题的子问题就为：在$s[0,i_{k1}]、s[0,i_{k2}]、s[0,i_{k3}]$的分割次数中找到最小值，并且$s[0,i_{k1}]、s[0,i_{k2}]、s[0,i_{k3}]$的分割次数也可以通过求解它们的子问题得到。

## 1、问题类型

第二类：对某种数据结构和算法的使用

使用的算法：动态规划

数据结构：构造状态数组

## 2、解题思路

从前面已经知道，在$s[0,n-1]$ 范围内找到最少分割次数，就是要找到所有的回文子串下标$k(k1、k2、k3....)$，$s[0,n-1]$  的最少分割次数等于$ s[0,k1]、s[0,k2]、s[0,k3]、... $ 中最小分割次数加1。

因此，我们可以构造一个数组，使用动态规划来求解最少分割次数，求解过程如下：

1、要求解的“状态”是：到字符串任意下标 $i$ 处，$s[0,i]$ 的最少的回文子串分割次数；

2、简化状态，得到“base case”：$i == 0$ 或者 $s[0,i]$ 本身就是回文串，分割次数为0；

3、构造“数组”：构造数组 $dp[n]$​​​，$dp[i]$​​​ 表示子串 $s[0,i]$​​​ 的最少分割次数；

4、构造状态转移方程：
$$
dp[i]=
\begin{cases}
0, & s[0,i]\; \text{是回文子串}
\\[2ex] min\{dp[k]\}, & k < i\; and\; s[k,i]\;\text{是回文子串}
\end{cases}
$$
从“构造函数”可以看出，在确定 $dp[i]$ 的值之前，要先知道所有 $dp[k](k \le i) $的值，所以在遍历字符串时，外层下标 $i$ 从0开始遍历到 $n$，内层下标 $k$ 从0开始遍历到 $i$。另外，我们要知道 $s[k,i]$ 是否是回文子串，可以使用题目 [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/) 构造的二维数组。

代码实现在“编码阶段”，在 LeetCode 的提交结果如下：

![image-20210807200228889](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210807200228889.png)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.png"/>
</div>

# 编码阶段

```java
class Solution {
    public int minCut(String s) {
        int n = s.length();
        boolean[][] isPalindrome = isPalindrome(s);
        int[] dp = new int[n];
        // 初始值
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 0; i < n; i++) {
            if (isPalindrome[0][i]) {
                dp[i] = 0;
            } else {
                for (int k = 0; k < i; k++) {
                    if (isPalindrome[k + 1][i]) {
                        // 依次遍历下标 k，取出最小值
                        dp[i] = Math.min(dp[i], dp[k] + 1);
                    }
                }
            }
        }
        return dp[n - 1];
    }

    private boolean[][] isPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (i == j) {
                    dp[i][j] = true;
                } else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    if (j == i + 1) {
                        dp[i][j] = b;
                    } else {
                        dp[i][j] = b & dp[i + 1][j - 1];
                    }
                }
            }
        }
        return dp;
    }
}
```

# 总结阶段

“分割回文串II”解题思路：

1、先用一个简单的例子，找到问题的子问题：最少次数是在多个回文子串中取最小值加1，再不断缩小子问题；

2、构造状态数组，直接保存状态：$s[0,i]$ 的最少分割次数。

