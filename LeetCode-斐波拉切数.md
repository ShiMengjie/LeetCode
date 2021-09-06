# LeetCode-斐波拉切数

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

