# LeetCode-斐波那契数和快速幂

LeetCode 上有几道斐波那契数相关的问题，这类问题一般使用动态规划求解。

后来看了 [《程序员代码面试指南：IT名企算法与数据结构题目最优解》](https://book.douban.com/subject/26638586/)，知道了这类问题可以使用快速幂来求解矩阵幂，可以在 log(N) 的时间复杂度范围内得到答案。

因此，把 LeetCode 上斐波那契数和快速幂相关问题统一整理起来，方便后面复习。

## 问题列表

[509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

[1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

[70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

[50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)

## 动态规划求解

### [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

相同问题：[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

问题描述：

> 斐波那契数列，由 0 和 1 开始，后面的每一项数字都是它前面两项数字的和，通常用 F(n) 表示第 n 项数字，关系如下：
> F(0) = 0，F(1) = 1
> F(n) = F(n - 1) + F(n - 2)，其中 n > 1
> 给你 n ，请计算 F(n) 。


我们可以使用动态规划求解斐波那契数，求解过程如下：

1、状态：给定下标 n，求 F(n) 的值

2、数组：定义数组 dp[]，长度是 n + 1，dp[n] 就是要求解的 F(n)

3、base case：斐波那契数列的边界，dp[0] = 0、dp[1] = 1

4、状态转移方程：

dp[n] = dp[n-1] + dp[n-2], n \ge 2 



可以看出，解斐波那契数列的定义本身就给出了动态规划的“状态、数组、base case、状态转移方程”。

从状态转移方程可以看出，F(n) 只和 F(n-1)、F(n-2) 有关，因此可以使用两个变量，来优化数组的存储。

代码实现如下：

```java
class Solution {
    public int fib(int n) {
        final int MOD = (int) 1e9 + 7;
        if (n <= 1) {
            return n;
        }
        int f1 = 0, f2 = 1;
        for (int i = 2; i <= n; i++) {
            int f3 = (f1 + f2) % MOD;
            f1 = f2;
            f2 = f3;
        }
        return f2;
    }
}
```

### [1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

问题描述：

> 泰波那契序列 Tn 定义如下：
> T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
> 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。

该问题的求解思路与[509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)相似，区别在于 T(n) 与 T(n -1)、T(n-2)、T(n-3) 都相关。

代码实现如下：

```java
class Solution {
    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n <= 2) {
            return 1;
        }
        int num1 = 0, num2 = 1, num3 = 1;
        for (int i = 3; i <= n; i++) {
            int tmp = (num1 + num2 + num3);
            num1 = num2;
            num2 = num3;
            num3 = tmp;
        }
        return num3;
    }
}
```

### [70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

问题描述：

> 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
> 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
> **注意：**给定 n 是一个正整数。

这一题看似与前面两题不同，但使用动态规划求解时，写出的状态方程是相同的。求解过程如下：

1、状态：给定台阶数 n，求到达 n 的方式，定义为 F(n) 

2、数组：定义数组 dp[]，长度是 n + 1，dp[n] 就是要求解的 F(n)

3、base case：每次可以爬1个台阶或2个台阶，得到边界条件，dp[1] = 1、dp[2] = 2

4、状态转移方程：

要到达台阶 i，可以从台阶 i - 1 爬1个台阶到达，也可以从台阶 i - 2 爬2个台阶到达，所以到达台阶 i 的方式是 “到达台阶 i - 1 的方式”与“到达台阶 i - 2 的方式”之和，转移方程如下：

dp[i] = dp[i-1] + dp[i-2]



可以发现，动态规划的求解过程与前面两个问题相同。

代码实现如下：

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int f1 = 1, f2 = 2, f3 = 0;
        for (int i = 3; i <= n; i++) {
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
        }
        return f3;
    }
}
```

前面的3个问题，都是简单的序列型动态规划题，使用上面介绍的动态规划方法，都可以在 O(N) 时间复杂度内求解。

那有没有更快的求解方法？能否在 log(N) 的时间复杂度范围内求解？就需要使用下面介绍的快速幂。

![POPO-screenshot-20210908-205947](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/POPO-screenshot-20210908-205947.png)

## 快速幂

### 方法介绍



### [50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)



### [372. 超级次方](https://leetcode-cn.com/problems/super-pow/)



## 快速幂计算矩阵幂





## 其他相似问题

### [746. 使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)

问题描述：

> 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
> 每当你爬上一个阶梯你都要花费对应的体力值，一旦支付了相应的体力值，你就可以选择向上爬一个阶梯或者爬两个阶梯。
> 请你找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。

解题思路与[70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)相似，只不过在状态转移时，要加上当前台阶上的花费，并取最小值。


```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        if (n == 1) {
            return cost[0];
        }
        if (n == 2) {
            return Math.min(cost[0], cost[1]);
        }
        int f1 = cost[0], f2 = cost[1], res = 0;
        for (int i = 2; i <= n; i++) {
            int cs = 0;
            if (i < n) {
                cs = cost[i];
            }
            res = Math.min(f1 + cs, f2 + cs);
            f1 = f2;
            f2 = res;
        }
        return res;
    }
}
```

### [842. 将数组拆分成斐波那契序列](https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/)



### [873. 最长的斐波那契子序列的长度](https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/)



## 参考阅读

[快速幂](https://oi-wiki.org/math/quick-pow/)

[矩阵](https://oi-wiki.org/math/matrix/)

[程序员代码面试指南：IT名企算法与数据结构题目最优解](https://book.douban.com/subject/26638586/)

https://mp.weixin.qq.com/s/jLRQNB08PmHctSAhpjyo3A

https://mp.weixin.qq.com/s/rdQyri2HEWhql0Q7cNCvJg

