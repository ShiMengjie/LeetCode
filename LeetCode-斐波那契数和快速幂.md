# LeetCode-斐波那契数和快速幂

LeetCode 上有几道斐波那契数相关的问题，这类问题一般使用动态规划求解，后来看了 [程序员代码面试指南：IT名企算法与数据结构题目最优解](https://book.douban.com/subject/26638586/)，才知道可以使用快速幂求解矩阵乘法，从而在 log(N) 的时间复杂度范围内求解结果。

因此，把 LeetCode 上斐波那契数和快速幂相关问题统一整理在该文档中，方便后面复习。

## 问题列表

[509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

[1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

[70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

[50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)

## [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

相同问题：[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

斐波那契数列，由 0 和 1 开始，后面的每一项数字都是它前面两项数字的和，通常用 F(n) 表示第 n 项数字，关系如下：

F(0) = 0，F(1) = 1

F(n) = F(n - 1) + F(n - 2)，其中 n > 1

给你 n ，请计算 F(n) 。



### 动态规划求解

我们可以使用动态规划求解斐波那契数，数列中数字的计算公式，就是动态规划的状态转移方程。求解过程如下：

1、状态：给定下标 n，求 F(n) 的值

2、数组：定义数组 dp[]，长度是 n + 1，dp[n] 就是要求的 F(n)

3、base case：斐波那契数列的边界，dp[0] = 0、dp[1] = 1

4、状态转移方程：

dp[n] = dp[n-1] + dp[n-2], n \ge 2 



可以看出，解斐波那契数列的定义本身就给出了动态规划的

一般用动态规划求解，斐波那契数的关系，就是动态规划的状态转移方程。





```java
class Solution {
    public int fib(int n) {
        final int MOD = 1000000007;
        if(n == 0) {
            return 0;
        }
        if(n == 1){
            return 1;
        }
        int num1 = 0, num2 = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = (num1 + num2) % MOD;
            num1 = num2;
            num2 = tmp;
        }
        return num2;
    }
}
```

## [1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

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

## [70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)





## 快速幂



## [746. 使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)



## [842. 将数组拆分成斐波那契序列](https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/)



## [873. 最长的斐波那契子序列的长度](https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/)



## [50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)



## [372. 超级次方](https://leetcode-cn.com/problems/super-pow/)

## 参考阅读

[快速幂](https://oi-wiki.org/math/quick-pow/)

[矩阵](https://oi-wiki.org/math/matrix/)

[程序员代码面试指南：IT名企算法与数据结构题目最优解](https://book.douban.com/subject/26638586/)

https://mp.weixin.qq.com/s/jLRQNB08PmHctSAhpjyo3A

https://mp.weixin.qq.com/s/rdQyri2HEWhql0Q7cNCvJg
