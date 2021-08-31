# LeetCode-打家劫舍问题

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

遍历到一个节点，有两条路径：从父节点向子节点遍历、从子节点向父节点遍历。

如果从父节点向子节点遍历，随着遍历层次的加深，需要记录的前置状态不断增多，因为每一层的父节点在增加。

如果从子节点向父节点遍历，随着遍历层次不断减少，需要记录的前置状态是减少的，因为父节点数在不断减少。

因此，选择从子节点向父节点遍历。



从问题可知，在遍历到某个父节点时，需要知道它的两个子节点是或否被选中，所以使用“后序遍历”的方式遍历整棵二叉树。

```java
class Solution {
    public int rob(TreeNode root) {
        Map<TreeNode, Integer> f = new HashMap<>();
        Map<TreeNode, Integer> g = new HashMap<>();

        postOrder(f, g, root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    private void postOrder(Map<TreeNode, Integer> f, Map<TreeNode, Integer> g, TreeNode node) {

        if (node == null) {
            return;
        }

        postOrder(f, g, node.left);
        postOrder(f, g, node.right);

        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        g.put(node, Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0))
                + Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0)));
    }
}
```



