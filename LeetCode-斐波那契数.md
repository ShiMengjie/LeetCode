# LeetCode-斐波那契数

## [509. 斐波那契数](https://leetcode-cn.com/problems/fibonacci-number/)

相同问题：[剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

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



## [746. 使用最小花费爬楼梯](https://leetcode-cn.com/problems/min-cost-climbing-stairs/)



## [842. 将数组拆分成斐波那契序列](https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/)



## [873. 最长的斐波那契子序列的长度](https://leetcode-cn.com/problems/length-of-longest-fibonacci-subsequence/)



