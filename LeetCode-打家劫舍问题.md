# LeetCode-打家劫舍系列问题

![image-20210816091443234](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210816091443234.png)

## [198. 打家劫舍](https://leetcode-cn.com/problems/house-robber/)

类似于[309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)问题，不能连续偷窃，就像问题中有冷却期一样。

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        // dp[i][0] : 离开 i 家时没有偷窃时获得的利益，dp[i][1] : 离开 i 家时有偷窃时获得的利益
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = nums[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][0] + nums[i];
        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }
}
```

## [213. 打家劫舍 II](https://leetcode-cn.com/problems/house-robber-ii/)

当 nums 只有一个值时，必须得偷，已经不能使用动态规划了。

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 1) {
            return nums[0];
        }
        // dp[i][0] : 离开 i 家时没有偷窃时获得的利益，dp[i][1] : 离开 i 家时有偷窃时获得的利益
        int[][][] dp = new int[n][2][2];
        dp[0][0][0] = 0;
        dp[0][0][1] = 0;
        dp[0][1][0] = 0;
        dp[0][1][1] = nums[0];

        for (int i = 1; i < n; i++) {
            dp[i][0][0] = Math.max(dp[i - 1][0][0], dp[i - 1][1][0]);
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][1][1]);
            dp[i][1][0] = dp[i - 1][0][0] + nums[i];
            dp[i][1][1] = dp[i - 1][0][1] + nums[i];
        }
        return Math.max(Math.max(dp[n - 1][0][0], dp[n - 1][0][1]), dp[n - 1][1][0]);
    }
}
```

## [337. 打家劫舍 III](https://leetcode-cn.com/problems/house-robber-iii/)

