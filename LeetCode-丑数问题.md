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





## [1201. 丑数 III](https://leetcode-cn.com/problems/ugly-number-iii/)



## [313. 超级丑数](https://leetcode-cn.com/problems/super-ugly-number/)