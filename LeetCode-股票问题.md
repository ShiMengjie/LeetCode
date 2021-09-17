# LeetCode-股票问题

LeetCode 的股票系列问题一共有六道题，如下所示：

[121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)

[122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)

[123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

[188. 买卖股票的最佳时机 IV](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/)

[309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

[714. 买卖股票的最佳时机含手续费](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

”参考阅读1“给出了”适用于全部股票问题的通解，以及对于每个特定问题的特解“，本文整理了自己在计算上述问题过程中，从特殊到一般的思考过程。

## [121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)

### 问题描述

> 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
> 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
> 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。

### 解法1

#### 解题思路

“只能买卖一次，且要求获得最大的利润”，就是在数组中找到两个数 $nums[i]、nums[j], j \lt i$，令 $nums[i] - nums[j]$​ 的值最大。

我们可以遍历整个数组，遍历到下标 i 时，记录前面的最小值和最大差值，不断更新这两个变量，直到遍历完成。

#### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        // max : 最大的利润， minPrev : 前面元素中的最小值
        int n = prices.length, max = 0, minPrev = prices[0];
        for (int i = 1; i < n; i++) {
            minPrev = Math.min(minPrev, prices[i]);
            max = Math.max(prices[i] - minPrev, max);
        }
        return max;
    }
}
```

时间复杂度：O(N)，空间复杂度：O(1)。

## [122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)

### 问题描述

> 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
> 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
> 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

### 解法1

#### 解题思路

不限交易次数，如果最终交易次数为K，可以理解为：从数组中取出了K段（例如 nums[i_1,j_1],...,nums[i_2,j_2],...,nums[i_k,j_k] 

），在每一段内都获得了那一段内的最大收益。

那么我们思考一下这个问题：为什么不在 nums[j_1+1,i_2-1] 范围内进行交易？很显然，因为这段元素无法计算出正值（无法得到正利润），也就是说这个子段中的元素是逆序的。

进一步理解就是：整个数组 nums 被一些逆序的子段分割了，这些逆序的子段不能进行交易，我们要找到正序的子段，在正序的子段上做交易，就可以得到尽可能大的利润。

在 正序的子段上怎么进行交易？

假设子段 nums[i,j] 是正序的，那么根据“问题121“，用最大值减去最小值（nums[j] - nums[i]）就能得到最大利润，该公式又可以写成：

nums[j] - nums[i] = nums[j] - nums[j-1] + nums[j-1] - nums[j-2]+ ... + nums[i+1] - nums[i]

也就是说，在一个正序的子段里，可以用后续的元素减去前一个元素，就就能得到这个子段内的最大利润。

#### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        // 最大的利润
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            // 正序子段中，后一个元素减去前一个元素
            if (prices[i] > prices[i - 1]) {
                max += prices[i] - prices[i - 1];
            }
        }
        return max;
    }
}
```

时间复杂度：O(N)，空间复杂度：O(1)。

### 解法2

#### 解题思路

上面的解法能够求解出问题答案，但是并没有使用到”动态规划“相关的内容，后来看了[官方题解](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/mai-mai-gu-piao-de-zui-jia-shi-ji-ii-by-leetcode-s/)，发现可以使用“动态规划”的思路来求解问题，下面的解题思路来自[官方题解](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/mai-mai-gu-piao-de-zui-jia-shi-ji-ii-by-leetcode-s/)。

每天结束的时候，手上的股票持有状态有两种：持有股票、不持有股票，要求解的是在这两种可能状态下的最大利润；第 i 天的状态和最大利润与前一天的持有状态、以及当天所做的操作（不买不卖、买、卖）有关。因此，我们可以构造一个数组，用来记录在不同状态下的最大利润，求解过程如下：

1、“状态”：在第 i 天结束后，持有股票或不持有股票，所拥有的最大利润

2、“数组”：构造数组 $dp[i][2]$​，$dp[i][0]$​ 表示第 i 天结束后不持有股票的最大利润，dp[i][1] 表示第 i 天结束后持有股票的最大利润

3、“base case”：$i == 0$​ 时，dp[0][0] = 0，dp[0][1] = -nums[0]

4、构造状态转移方程：
$$
\begin{cases}
dp[i][0]= Max\{dp[i-1][0],dp[i-1][1] + nums[i]\}
\\dp[i][1] = Max\{dp[i-1][1],dp[i-1][0] - nums[i]\}
\end{cases}
$$
从“构造函数”可以看出，整个求解过程，只使用到了4个变量：dp[i-1][0]、dp[i-1][1]、dp[i][0]、dp[i][1]，可以用4个变量记录这4个元素的值。

#### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        // dp[i-1][0]、dp[i-1][1]
        int prev0 = 0, prev1 = -prices[0];
        // dp[i][0]、dp[i][1]
        int cur0 = 0, cur1 = 0;
        for (int i = 1; i < n; i++) {
            cur0 = Math.max(prev0, prev1 + prices[i]);
            cur1 = Math.max(prev1, cur0 - prices[i]);

            prev0 = cur0;
            prev1 = cur1;
        }
        return Math.max(cur0, cur1);
    }
}
```

时间复杂度：O(N)，空间复杂度：O(1)。

## [123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

### 问题描述

> 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
> 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
> 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

### 解题思路

该问题与“问题122“相似，只不过增加了“只能完成2次交易”的限制条件，因此第 i 天结束时，手上的股票状态要增加了一个“交易次数”，此时股票的状态有：

* 不持有股票 + 从没买过也没卖过，这种状态下利润永远是0，不满足问题要求，忽略不计
* 持有股票 + 只买过一次
* 不持有股票 + 买卖过一次
* 持有股票 + 第二次买入（完成过一次交易）
* 不持有股票 + 第二次卖（完成过两次交易）

我们同样适用动态规划来求解问题，求解过程如下：

1、“状态”：在第 i 天结束后，4种状态所拥有的最大利润

2、“数组”：构造数组 $dp[i][2][2]$​：

* $dp[i][1][0]$ 表示第 i 天结束后持有股票，且只买过一次的最大利润
* $dp[i][0][0]$​ 表示第 i 天结束后不持有股票，且买卖过一次（完成过一次交易）的最大利润
* $dp[i][1][1]$​ 表示第 i 天结束后持有股票，且第二次买入（完成过一次交易）的最大利润
* $dp[i][0][1]$ 表示第 i 天结束后不持有股票，且第二次卖（完成两次交易）的最大利润

3、“base case”：dp[0][0][0] = dp[0][0][1] = 0, dp[0][1][0] = dp[0][1][1] = - nums[0]

4、构造状态转移方程：
$$
\begin{cases}
dp[i][1][0] = Max\{dp[i-1][1][0],-nums[i]\}
\\dp[i][0][0] = Max\{dp[i-1][0][0],dp[i-1][1][0] + nums[i]\}
\\dp[i][1][1] = Max\{dp[i-1][1][1],dp[i-1][0][0]-nums[i]\}
\\dp[i][0][1] = Max\{dp[i-1][0][1],dp[i-1][1][1] + nums[i]\}
\end{cases}
$$
同样可以用4个变量来记录计算过程中所需的变量值。

### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        // buy1 : dp[i][1][0],持有股票且只买过一次，sell1 : dp[i][0][0],不持有股票且只卖过一次
        // buy2 : dp[i][1][1],持有股票且第二次买，sell2 : dp[i][0][1],不持有股票且第二次卖
        int buy1 = -prices[0], sell1 = 0, buy2 = -prices[0], sell2 = 0;
        for (int i = 1; i < n; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }
}
```

时间复杂度：O(N)，空间复杂度：O(1)。

## [188. 买卖股票的最佳时机 IV](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/)

### 问题描述

> 给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。
> 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
> 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

### 解题思路

该问题与“问题123“相似，交易次数变成了“最多K笔交易”，因此第 i 天结束时的状态有：

* 持有股票 + 交易过 k 次
* 不持有股票 + 交易过 k 次

有了“问题122“和”问题123“的经验，我们很容易地使用“动态规划”来求解问题，求解过程如下：

1、“状态”：在第 i 天结束后，股票的持有状态

2、“数组”：构造数组 $dp[i][K][2]$​：

* $dp[i][k][1]$​ 表示第 i 天结束后持有股票，且已经交易过k次
* $dp[i][k][0]$ 表示第 i 天结束后不持有股票，且已经交易过k次

3、“base case”：

* dp[0][0][1] = -nums[0]、dp[0][0][0] = 0
* dp[0][k][0] = dp[0][k][1] = Integer.MIN_VALUE;

4、构造状态转移方程：
$$
\begin{cases}
dp[i][k][0] = Max\{dp[i-1][k][0],dp[i-1][k-1][1]+nums[i]\}
\\dp[i][k][1] = Max\{dp[i-1][k][1],dp[i-1][k][0] - nums[i]\}
\end{cases}
$$
同样可以用4个变量来记录计算过程中所需的变量值。

### 代码实现

```java
class Solution {
    public int maxProfit(int K, int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        // 最多交易次数为 n/2
        K = Math.min(K, n / 2);

        int[][][] dp = new int[n][K + 1][2];
        // base case
        dp[0][0][0] = 0;
        dp[0][0][1] = -prices[0];
        for (int k = 1; k <= K; k++) {
            dp[0][k][0] = dp[0][k][1] = Integer.MIN_VALUE / 2;
        }
        // 最大利润
        int max = Math.max(dp[0][0][0], dp[0][0][1]);
        
        for (int i = 1; i < n; i++) {
            dp[i][0][1] = Math.max(dp[i - 1][0][1], dp[i - 1][0][0] - prices[i]);
            for (int k = 1; k <= K; k++) {
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k][0] - prices[i]);
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k - 1][1] + prices[i]);
                max = Math.max(max, Math.max(dp[i][k][1], dp[i][k][0]));
            }
        }
        return max;
    }
}
```

## [309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

### 问题描述

> 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。
> 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
> 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
> 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

### 解题思路

与”问题122“相似，只不过在买的时候多了一天冷却期，同样我们可以使用“动态规划”来求解问题，求解过程如下：

1、“状态”：在第 i 天结束后，持有股票或不持有股票，所拥有的最大利润

2、“数组”：构造数组 $dp[i][2]$​，$dp[i][0]$​ 表示第 i 天结束后不持有股票的最大利润，dp[i][1] 表示第 i 天结束后持有股票的最大利润

3、“base case”：$i == 0$​ 时，dp[0][0] = 0，dp[0][1] = -nums[0]

4、构造状态转移方程（在买入的时候，要考虑冷却期）：
$$
\begin{cases}
dp[i][0]= Max\{dp[i-1][0],dp[i-1][1] + nums[i]\}
\\dp[i][1] = Max\{dp[i-1][1],dp[i-2][0] - nums[i]\}
\end{cases}
$$
使用3个变量代替数组。

### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        // prev0 : i-1 天不持有股票的利润，prev1 : i-1 天持有股票的利润，prev2 : i-2 天不持有股票的利润
        int prev0 = 0, prev1 = -prices[0],  prev2 = 0;
        for (int i = 1; i < n; i++) {
            prev1 = Math.max(prev1, prev2 - prices[i]);
            // 更新 prev2 的值
            prev2 = prev0;
            prev0 = Math.max(prev0, prev1 + prices[i]);
        }
        return Math.max(prev1, prev0);
    }
}
```

## [714. 买卖股票的最佳时机含手续费](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

### 问题描述

> 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
> 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
> 返回获得利润的最大值。
> 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。

### 解题思路

与”问题122“相似，只不过在卖的时候要额外支付手续费，同样我们使用“动态规划”来求解问题，求解过程如下：

1、“状态”：在第 i 天结束后，持有股票或不持有股票，所拥有的最大利润

2、“数组”：构造数组 $dp[i][2]$​，$dp[i][0]$​ 表示第 i 天结束后不持有股票的最大利润，dp[i][1] 表示第 i 天结束后持有股票的最大利润

3、“base case”：$i == 0$​ 时，dp[0][0] = 0，dp[0][1] = -nums[0]

4、构造状态转移方程（在卖出的时候，要减去手续费 fee）：
$$
\begin{cases}
dp[i][0]= Max\{dp[i-1][0],dp[i-1][1] + nums[i] - fee\}
\\dp[i][1] = Max\{dp[i-1][1],dp[i-2][0] - nums[i]\}
\end{cases}
$$
使用2个变量代替数组。

### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        // prev0 : 不持有股票的利润，prev1 : 持有股票的利润
        int prev0 = 0, prev1 = -prices[0];
        for (int i = 1; i < n; i++) {
            prev1 = Math.max(prev1, prev0 - prices[i]);
            // 卖出的时候，要减去手续费
            prev0 = Math.max(prev0, prev1 + prices[i] - fee);
        }
        return Math.max(prev1, prev0);
    }
}
```



## 总结



## 参考阅读

1、[股票问题系列通解](https://leetcode-cn.com/circle/article/qiAgHn/)

