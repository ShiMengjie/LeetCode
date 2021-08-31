# LeetCode-丑数问题

## [263. 丑数](https://leetcode-cn.com/problems/ugly-number/)

```java
class Solution {
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] nums = new int[]{2, 3, 5};
        for (int num : nums) {
            while (n % num == 0) {
                n = n / num;
            }
        }
        return n == 1;
    }
}
```

## [264. 丑数 II](https://leetcode-cn.com/problems/ugly-number-ii/)

最简单的方法：从1开始遍历每个数，判断是否是丑数，如果是就添加到结果集中，直到结果集大小为 n。

```java
class Solution {
    List<Integer> list;

    public int nthUglyNumber(int n) {
        // 初始化结果集
        list = new ArrayList<>(n);
        list.add(0, 1);
        list.add(1, 2);
        list.add(2, 3);
        list.add(3,4);
        list.add(4,5);

        int i = 5;
        int num = 6;
        while (i < n) {
            if (isUgly(num)) {
                list.add(i , num);
                i++;
            }
            num++;
        }
        return list.get(n - 1);
    }


    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        for (int i = list.size() - 1; i >= 1; i--) {
            int tmp = list.get(i);
            while (n % tmp == 0) {
                n = n / tmp;
            }
        }
        return n == 1;
    }
}
```



去重、排序：

- 去重用哈希
- 排序用有序堆





```java
class Solution {
    public int nthUglyNumber(int n) {
        int[] factors = new int[]{2, 3, 5};

        PriorityQueue<Long> queue = new PriorityQueue<>();
        Set<Long> set = new HashSet<>();

        queue.add(1L);
        set.add(1L);
        long cur = 1;
        for (int i = 1; i <= n; i++) {
            cur = queue.poll();
            for (int factor : factors) {
                long num = factor * cur;
                if (set.add(num)) {
                    queue.add(num);
                }
            }
        }
        return (int)cur;
    }
}
```



动态规划

```java
class Solution {
    public int nthUglyNumber(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        int p2 = 1, p3 = 1, p5 = 1;
        for (int i = 2; i <= n; i++) {
            int num2 = 2 * dp[p2], num3 = 3 * dp[p3], num5 = 5 * dp[p5];
            int min = Math.min(Math.min(num2, num3), num5);
            dp[i] = min;
            if (min == num2) {
                p2++;
            }
            if (min == num3) {
                p3++;
            }
            if (min == num5) {
                p5++;
            }
        }
        return dp[n];
    }
}
```

## [1201. 丑数 III](https://leetcode-cn.com/problems/ugly-number-iii/)

暴力解法，超时：

```java
public int nthUglyNumber(int n, int a, int b, int c) {

    int i = 1, idx = 1, res = 0;
    while (i <= n) {
        if (idx % a == 0 || idx % b == 0 || idx % c == 0) {
            i++;
            res = idx;
        }
        idx++;
    }
    return res;
}
```

二分查找：

```java
class Solution {
    public int nthUglyNumber(int n, int a, int b, int c) {
        long l = 1, r = Integer.MAX_VALUE;
        long ab = lcm(a,b);
        long ac = lcm(a,c);
        long bc = lcm(b,c);
        long abc = lcm(a,bc);

        while(l < r){
            long mid = l + r >> 1;
            long count = mid/a + mid/b + mid/c - mid/ab - mid/ac - mid/bc + mid/abc;
            if(count >= n) r = mid;
            else l = mid+1;
        }
        return (int)l;
    }

    private long lcm(long a, long b){
        return a*b/gcd(a,b);
    }

    private long gcd(long x, long y){
        if( x == 0) return y;
        return gcd(y%x, x);
    }
}
```



## [313. 超级丑数](https://leetcode-cn.com/problems/super-ugly-number/)