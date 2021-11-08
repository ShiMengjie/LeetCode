# LeetCode-斐波那契数和快速幂

LeetCode 上有几道斐波那契数相关的问题，这类问题一般使用动态规划求解。

后来看了 [《程序员代码面试指南：IT名企算法与数据结构题目最优解》](https://book.douban.com/subject/26638586/)，才知道这类问题使用快速幂求解矩阵乘积，可以在 $log(N)$ 的时间复杂内求解。

为了方便后面复习，把 LeetCode 上斐波那契数和快速幂相关问题统一整理起来。

## 问题列表

[509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

[1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

[70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

[50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)

相关问题

[746. 使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)

[842. 将数组拆分成斐波那契序列](https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/)

[873. 最长的斐波那契子序列的长度](https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/)

[372. 超级次方](https://leetcode-cn.com/problems/super-pow/)

## 动态规划求解

### [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

相同问题：[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

问题描述：

![](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210912145850436.png)


我们可以使用动态规划求解斐波那契数，求解过程如下：

1、状态：给定下标 $n$，求 $F(n)$ 的值

2、数组：定义长度是 $n + 1$ 的数组 $dp[]$，，$dp[n]$ 就是要求解的 $F(n)$

3、base case：斐波那契数列的边界，$dp[0] = 0、dp[1] = 1$

4、状态转移方程：
$$
\begin{aligned} 
dp[n] &=
\begin{cases}
dp[n-1] + dp[n-2], \quad n \ge 2 \\
n, \quad n \le 1 \\
\end{cases}
\end{aligned}
$$

斐波那契数列的定义本身就包含了动态规划的“状态、数组、base case、状态转移方程”，并且，$F(n)$ 只和 $F(n-1)、F(n-2)$ 有关，可以使用两个变量，来优化数组的存储。

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

![](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210912145903442.png)

该问题的求解思路与[509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)相似，区别在于 $T(n)$ 与 $T(n -1)、T(n-2)、T(n-3)$ 都相关。

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

![](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210912145915262.png)

这一题使用动态规划求解时，状态转移方程和斐波那契数相同。求解过程如下：

1、状态：给定台阶数 $n$，求到达 $n$ 的方式，定义为 $F(n)$ 

2、数组：定义长度是 $n + 1$ 数组 $dp[]$，$dp[n]$ 就是要求解的 $F(n)$ 

3、base case：每次可以爬1个台阶或2个台阶，得到边界条件，$dp[1] = 1、dp[2] = 2$

4、状态转移方程：

要到达台阶 $i$，可以从台阶 $i - 1$ 爬1个台阶到达，也可以从台阶 $i - 2$ 爬2个台阶到达，所以到达台阶 $i$ 的方式是 “到达台阶 $i - 1$ 的方式”与“到达台阶 $i - 2$ 的方式”之和，转移方程如下：

$$
dp[n] = 
\begin{cases} 
dp[n-1] + dp[n-2], \quad n \ge 3 \\
n, \quad n \le 2
\end{cases}
$$

动态规划的求解过程与前面两个问题相同。

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

前面的3个问题，都是简单的序列型动态规划题，使用上面介绍的动态规划方法，可以在 $O(N)$ 时间复杂度内求解。

那有没有更快的求解方法？能否在 $log(N)$ 的时间复杂度范围内求解？这就需要使用下面介绍的快速幂。

## 快速幂

> 快速幂的算法描述，来自参考阅读：[快速幂](https://oi-wiki.org/math/quick-pow/)

### 算法描述

> 二进制求幂

计算 $a$ 的 $n$ 次方就是把 $n$ 个 $a$ 乘在一起：
$$
a^{n} = \underbrace{a \times a \cdots \times a}_{n\text{ 个 a}}
$$
在 $a,n$ 值比较小的时候，可以通过遍历连乘的方式计算。但是当 $a,n$ 取值很大时，这种方法就不太适用了。

不过，根据幂运算规则，我们知道：
$$
a^{b+c} = a^b \cdot a^c,\quad a^{2b} = a^b \cdot a^b = (a^b)^2
$$
根据该运算规则，可以使用快速幂算法来求解。快速幂的基本思想是：把指数 $n$ 以二进制的形式表示，然后按二进制位来求幂，可以把求幂任务分割成更小的计算任务。

把整数 $n$ 转换成二进制表示，二进制表示的位数为：$ k= \lfloor \log_2 n \rfloor + 1$ 。$n$ 表示为：

$$
n = n_k \cdot 2^k + n_{k-1} \cdot 2^{k-1} + n_{k-2} \cdot 2^{k-2} + \cdots + n_1 \cdot 2^1 + n_0 \cdot 2^0,\quad \text{其中,}\,n_i\in{\{0,1\}}
$$

$a$ 的 $n$ 次方可以表示为：

$$
\begin{aligned} 
a^n &= a^{(n_k \cdot 2^k + n_{k-1} \cdot 2^{k-1} + n_{k-2} \cdot 2^{k-2} + \cdots + n_{1} \cdot 2^{1} + n_{0} \cdot 2^{0})} \\&
= a^{n_0\cdot2^0} \times a^{n_1\cdot2^1}\times \cdots \times a^{n_k \cdot 2^t}
\end{aligned}
$$

在上面的公式中，各个乘积项 $a$ 的指数值为：$2^0=1,2^1=2,2^2=4,...,2^k$，后一个数是前一个数的平方。因此，知道了 $a^{1}$ 的值，只需要经过  $\Theta(\log n)$ 次乘法就能计算出 $a^n$。

### 示例

我们通过一个例子（$a = 3,n = 13$）来演示上述的计算过程。

我们将 $n$ 表示为 2 进制，$a^n$ 表示为：
$$
3^{13} = 3^{(1101)_2} = {3^{1 \cdot 8}} \cdot {3^{1 \cdot 4}} \cdot {3^{0 \cdot 2}} \cdot {3^{1 \cdot1}}
$$
其中，每一项指数的系数 ${1,0}$ 对应了 $n$ 二进制表示 $(1101)_2$ 中每一位上的 1 和 0 值

根据前面的算法描述，我们可以快速的计算出 3 的各项次幂的值，如下所示：
$$
\begin{aligned} \\&
3^1 = 3 \\&
3^2 = (3^1)^2 = 9 \\&
3^4 = (3^2)^2 = 81 \\&
3^8 = (3^4)^2 = 6561
\end{aligned}
$$
要计算出 $3^{13}$，我们只需要把二进制位为 1 的次幂值相乘就可以了。

这个算法的复杂度是 $\Theta(\log n)$ ：

- 计算了 $\Theta(\log n)$ 个 2 的次幂
- 花费 $\Theta(\log n)$ 的时间选择二进制位为 1 的幂来相乘

### 代码实现

```java
public int binaryExpo(int a, int n) {
    int res = 1;
    while (n > 0) {
        // 当前位的值等于1，要乘上该位上的值
        if ((n & 1) == 1) {
            res = res * a;
        }
        // 每移动一位，要变成前一位值的平方
        a = a * a;
        n = n >> 1;
    }
    return res;
}
```

### [50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)

LeetCode 上计算幂函数的问题，可以使用上面介绍的快速幂求解。代码如下：

```java
class Solution {
   public double myPow(double x, int n) {
       // n 会超出位数
       long N = n;
       N = Math.abs(N);

        double res = binaryExpo(x, N);

        if (n < 0) {
            return 1 / res;
        } else {
            return res;
        }
    }

    public double binaryExpo(double a, long n) {
        double res = 1;
        while (n > 0) {
            // 当前位的值等于1，要乘上该位上的值
            if ((n & 1) == 1) {
                res = res * a;
            }
            // 每移动一位，要变成前一位值的平方
            a = a * a;
            n = n >> 1;
        }
        return res;
    }
}
```

## 快速幂计算矩阵乘积

> 下面的内容，来自参考阅读：《[程序员代码面试指南：IT名企算法与数据结构题目最优解](https://book.douban.com/subject/26638586/)》

快速幂怎么用来求解斐波那契数？

斐波那契数的转移方程：$F(n) = F(n-1) + F(n-2)$，可以写作矩阵乘法的形式：

$$
\begin{aligned}
\left[F(n),F(n-1)\right] 

= \left[F(n-1),F(n-2)\right] \times {\left| \begin{matrix} 1 & 1 \\ 1 & 0 \end{matrix} \right|} \\&

\end{aligned}
$$

斐波那契数的计算公式可以表示为：

$$
\begin{aligned}
\left[F(n),F(n-1)\right] \\&

= \left[F(n-1),F(n-2)\right] \times {\left| \begin{matrix} 1 & 1 \\ 1 & 0 \end{matrix} \right|} \\&

= \left[F(n-2),F(n-3)\right] \times {\left| \begin{matrix} 1 & 1 \\ 1 & 0 \end{matrix} \right|}^2 \\& 

= \cdots \\&

= \left[F(2),F(1)\right] \times {\left| \begin{matrix} 1 & 1 \\ 1 & 0 \end{matrix} \right|}^{n-2} \\& 

\end{aligned}
$$

需要计算矩阵的 $n-2$ 次幂。我们可以修改一下前面计算快速幂的代码，用来计算矩阵的次幂。

用于计算矩阵的快速幂代码如下：

```java
/**
 * 矩阵的快速幂
 *
 * @param mat 矩阵
 * @param n   次幂
 */
public int[][] binaryExpo(int[][] mat, int n) {
    int[][] res = new int[mat.length][mat[0].length];
    for (int i = 0; i < res.length; i++) {
        res[i][i] = 1;
    }
    while (n > 0) {
        // 当前位的值等于1，要乘上该位上的值
        if ((n & 1) == 1) {
            res = mulMat(res, mat);
        }
        // 每移动一位，要变成前一位值的平方
        mat = mulMat(mat, mat);
        n = n >> 1;
    }
    return res;
}

/**
 * 计算两个矩阵相乘：mat1 * mat2
 *
 * @param mat1 矩阵1
 * @param mat2 矩阵2
 */
public int[][] mulMat(int[][] mat1, int[][] mat2) {
    int m = mat1.length, n = mat1[0].length, l = mat2[0].length;
    int[][] res = new int[m][l];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < l; j++) {
            for (int k = 0; k < n; k++) {
                res[i][j] += mat1[i][k] * mat2[k][j];
            }
        }
    }
    return res;
}
```

有了矩阵的快速幂计算方法，就可以使用快速幂来计算前面的问题。

## 快速幂求解

按照前面的分析，前面斐波那契数、泰波那契数、爬楼梯问题，都可以使用快速幂来计算，它们的区别只在于初始值不同、状态转移矩阵不同。

代码实现如下。

### [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

```java
class Solution {
    public int fib(int n) {
        if (n < 1) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }

        int[][] res = binaryExpo(new int[][]{{1, 1}, {1, 0}}, n - 2);
        return res[0][0] + res[1][0];
    }

    public int[][] binaryExpo(int[][] mat, int n) {
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        while (n > 0) {
            // 当前位的值等于1，要乘上该位上的值
            if ((n & 1) == 1) {
                res = mulMat(res, mat);
            }
            // 每移动一位，要变成前一位值的平方
            mat = mulMat(mat, mat);
            n = n >> 1;
        }
        return res;
    }

    public int[][] mulMat(int[][] mat1, int[][] mat2) {
        int m = mat1.length, n = mat1[0].length, l = mat2[0].length;
        int[][] res = new int[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return res;
    }
}
```

### [1137. 第 N 个泰波那契数](https://leetcode-cn.com/problems/n-th-tribonacci-number/)

```java
class Solution {
    public int tribonacci(int n) {
        if (n <= 1) {
            return n;
        }
        if (n <= 3) {
            return n - 1;
        }

        int[][] res = binaryExpo(new int[][]{{1, 1, 0}, {1, 0, 1}, {1, 0, 0}}, n - 3);
        return 2 * res[0][0] + res[1][0] + res[2][0];
    }

    public int[][] binaryExpo(int[][] mat, int n) {
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        while (n > 0) {
            // 当前位的值等于1，要乘上该位上的值
            if ((n & 1) == 1) {
                res = mulMat(res, mat);
            }
            // 每移动一位，要变成前一位值的平方
            mat = mulMat(mat, mat);
            n = n >> 1;
        }
        return res;
    }

    public int[][] mulMat(int[][] mat1, int[][] mat2) {
        int m = mat1.length, n = mat1[0].length, l = mat2[0].length;
        int[][] res = new int[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return res;
    }
}
```

### [70. 爬楼梯](https://leetcode-cn.com/problems/climbing-stairs/)

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[][] res = binaryExpo(new int[][]{{1, 1}, {1, 0}}, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    /**
     * 矩阵的快速幂
     *
     * @param mat 矩阵
     * @param n   次幂
     */
    public int[][] binaryExpo(int[][] mat, int n) {
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        while (n > 0) {
            // 当前位的值等于1，要乘上该位上的值
            if ((n & 1) == 1) {
                res = mulMat(res, mat);
            }
            // 每移动一位，要变成前一位值的平方
            mat = mulMat(mat, mat);
            n = n >> 1;
        }
        return res;
    }

    /**
     * 计算两个矩阵相乘：mat1 * mat2
     *
     * @param mat1 矩阵1
     * @param mat2 矩阵2
     */
    public int[][] mulMat(int[][] mat1, int[][] mat2) {
        int m = mat1.length, n = mat1[0].length, l = mat2[0].length;
        int[][] res = new int[m][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < l; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }
        return res;
    }
}
```

## 小结

与传统的动态规划方法相比，快速幂能在更快地时间复杂度内求解问题。

但是在代码实现上，动态规划更加简单，快速幂比较较为复杂。

## 相关问题

### [746. 使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)

问题描述：

![](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210912150614391.png)

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

## 参考阅读

1、[快速幂](https://oi-wiki.org/math/quick-pow/)

2、[矩阵](https://oi-wiki.org/math/matrix/)

3、[程序员代码面试指南：IT名企算法与数据结构题目最优解](https://book.douban.com/subject/26638586/)
