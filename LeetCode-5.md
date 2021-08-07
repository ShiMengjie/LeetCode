# [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/)

> 给你一个字符串 `s`，找到 `s` 中最长的回文子串。

**示例1**

```tex
输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。
```

**示例2**

```tex
输入：s = "cbbd"
输出："bb"
```

**示例 3**

```tex
输入：s = "a"
输出："a"
```

**示例 4**

```tex
输入：s = "ac"
输出："a"
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

# 分析阶段

> 动态规划

在字符串中找到最长的回文子串，目的是要找到两个下标 $start、end$，令 $s[start,end]$ 是回文的。又因为要找到最长的回文子串，所以还要求在所有的回文子串中， $end - start + 1$的值最大，这就需要找到所有的回文子串。

## 1、问题类型

第二类：对某种数据结构和算法的使用

使用的算法：动态规划

数据结构：构造状态数组

## 2、解题思路

### 解法1

最直接的求解方法，在遍历所有子串，分别判断每个子串是否是回文的：

* 如果不是，跳过
* 如果是，判断子串长度是否是最长的，更新 $start、end$ 变量值

代码实现在“编码阶段-实现1”部分，很显然时间复杂度是 $O(N^3)$，在 LeetCode 的提交结果超出时间限制，如下所示：

![image-20210807112309419](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210807112309419.png)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

### 解法2

“解法1”的问题在于：在判断每个子串 $s[i,j]$ 是否是回文串时，都要遍历一遍子串。

可以利用回文串的性质：如果一个子串是回文串，那么把它首尾两个字母去除后，它依然是一个回文串。例如，如果 $s[i,j]$ 是回文串，那么 $s[i+1,j-1] $也是回文串。

我们可以构造一个数组，使用动态规划来求解任意子串是否是回文串，求解过程如下：

1、要求解的“状态”是：字符串中任意一个子串$s[i,j]$ 是否是回文串；

2、简化状态，得到“base case”：$i == j$ 时，单个字母是回文串；

3、构造“数组”：构造二维数组 $dp[n][n]$，$dp[i][j]$ 表示子串 $s[i,j]$ 是否是回文串；

4、构造状态转移方程：

$$
dp[i][j]=
\begin{cases}
true, & i == j
\\[2ex] dp[i+1][j-1] \land s[i]==s[j], & j>i+1
\\[3ex] s[i]==s[j], & j == i+1
\end{cases}
$$

从"状态转移方程"可以看出，$i$ 下标的变化是从大到小，$j$ 下标的变化是从小到大，且 $j \ge i$，所以在遍历字符串时，外层 $i$ 下标是从后向前遍历，内层$j$ 下标是从$i$ 向后遍历。

代码实现在“编码阶段-实现2”部分，构造出 $dp$ 数组后，以 $O(1)$ 的时间复杂度就能判断任意子串是否是回文，整体的时间复杂度是$O(N^2)$。在 LeetCode 的提交结果如下：

![image-20210807124200765](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210807124200765.png)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.png"/>
</div>

# 编码阶段

## 实现1

```java
public class Solution {

    public String longestPalindrome(String s) {
        int n = s.length(), start = 0, end = 0, maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j + 1)) {
                    if (j - i + 1 > maxLength) {
                        start = i;
                        end = j;
                        maxLength = j - i + 1;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * 判断字符串 s 在下标 start、end 范围内的子串是否是回文的
     *
     * @param s     字符串
     * @param start 起始下标，包含
     * @param end   截止下标，不包含
     * @return 是否是回文
     */
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end - 1)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
}
```

## 实现2

```java
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length(), start = 0, end = 0;
        boolean[][] dp = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                // 设置 dp[i][j] 的值
                if (i == j) {
                    dp[i][j] = true;
                } else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    if (j == i + 1) {
                        dp[i][j] = b;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1] & b;
                    }
                }
                // 更新 start 和 end
                if (dp[i][j] && end - start + 1 < j - i + 1) {
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end + 1);
    }
}
```

# 总结阶段

“字符串”回文子串问题解题思路：

1、构造一个数组，记录任意子串是否是回文串；

2、在构造数组的过程中，使用动态规划减少计算复杂度。