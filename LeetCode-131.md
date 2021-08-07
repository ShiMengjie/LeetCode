# [131. 分割回文串](https://leetcode-cn.com/problems/palindrome-partitioning/)

> 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
>
> 回文串 是正着读和反着读都一样的字符串。

 **示例 1：**

```tex
输入：s = "aab"
输出：[["a","a","b"],["aa","b"]]
```

**示例 2：**

```tex
输入：s = "a"
输出：[["a"]]
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

# 分析阶段

> 回溯 + 动态规划

在字符串中找到所有的回文子串，问题可以分为两步：找到所有子串、子串如果是回文串，就添加到解集中。

找到一个字符串的所有子串，是一种“求解组合”的问题，可以使用回溯算法，求解过程与题目 [90. 子集 II](https://leetcode-cn.com/problems/subsets-ii/) 类似（[90. 子集 II](https://leetcode-cn.com/problems/subsets-ii/) 要求子集不能重复）。

问题变成了：使用回溯算法求解所有子串。

那么“动态规划”用在什么场景下？

在回溯过程中生成每个子串时，要判断子串是否是回文串，在这个步骤可以使用“动态规划”。在题目 [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/) 中，我们已经构造出一个数组，保存字符串中任意子串$s[i,j]$是否是回文串，因此在回溯开始之前，先构造好该数组，在回溯过程用于判断子串是否是回文串。

## 1、问题类型

第二类：对某种数据结构和算法的使用

使用的算法：回溯算法

数据结构：构造空间状态树、构造状态数组

## 2、解题思路

先按照  [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zui-chang-hui-wen-zi-chuan-xiao-bai-jie-y4rup/) 构造数组 $dp$，$dp[i][j]$表示子串 $s[i,j]$ 是否是回文串，该数组用于回溯过程中。

然后，我们使用“回溯算法”求解所有子串，求解过程如下：

定义方法 $backtrack(res,path,s,idx)$，该方法表示：从字符串 $s$ 的下标 $idx$ 处开始遍历，把回文子串添加到路径 $path$ 中。

**（1）选择列表**

“回溯算法”会递归地调用方法 $backtrack()$，每一层递归的选择列表是：字符串 $s$ 从下标 $idx$ 开始，直到最后一个元素，也就是 $s[idx,id]、s[idx,idx+1]、s[idx,idx+2]、s[idx,idx+3]、...、s[idx,n-1]$。

**（2）路径**

题目要求返回所有的回文子串，所以路径 $path$ 要记录每个回文子串。

**（3）结束条件**

当 $idx \ge n$ 时，表示已经遍历到字符串的最后一个元素，此时可以把路径中的所有子串添加到解集中，并结束方法，返回到上一层调用。

**（4）选择**

满足什么条件，才把当前的子串添加到路径中？

从 $idx$开始遍历，如果遍历的子串 $s[idx,i], i \ge idx$ 是回文串时，就把该子串添加到路径中；判断子串 $s[idx,i] $是否是回文串，就需要使用最开始构建的动态规划数组。

"空间状态树"如下图所示：

<video id="video" controls="" preload="none">       
    <source id="mp4" src="https://gameacademy.v.netease.com/2021/0807/60018256b4573c39c6a955651aa450f9qt.mp4" type="video/mp4">
</video>

代码实现在“编码阶段”，在 LeetCode 的提交结果如下：

![image-20210807173100953](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210807173100953.png)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.png"/>
</div>

# 编码阶段

```java
class Solution {
    boolean[][] dp;

    public List<List<String>> partition(String s) {
        // 动态规划构造状态数组
        dp = getDPArr(s);

        List<List<String>> res = new LinkedList<>();
        List<String> path = new LinkedList<>();
        // 回溯求解
        backtrack(res, path, s, 0);
        return res;
    }

    private void backtrack(List<List<String>> res, List<String> path, String s, int idx) {
        if (idx >= s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = idx; i < s.length(); i++) {
            // 子串 s[idx,i],i = idx,idx+1,idx+2,...
            if (dp[idx][i]) {
                path.add(s.substring(idx, i + 1));
                // 从 大于 idx 的下标开始递归遍历
                backtrack(res, path, s, i + 1);
                path.remove(path.size() - 1);
            }
        }
    }

    private boolean[][] getDPArr(String s) {
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

“分割回文串”解题思路：

1、使用“回溯”求解所有子串

2、在回溯过程中，使用动态规划生成的状态数组，加快“选择”步骤的判断
