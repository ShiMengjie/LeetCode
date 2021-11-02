# LeetCode-股票问题

LeetCode 上有6道股票相关的问题，都可以使用动态规划来求解。本文把这些问题整理起来，最后给出一个通用的求解模板。

## 问题列表

[121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)

[122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)

[123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

[188. 买卖股票的最佳时机 IV](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/)

[309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

[714. 买卖股票的最佳时机含手续费](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

## [121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)

### 问题描述

给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。

你只能选择 **某一天** 买入这只股票，并选择在 **未来的某一个不同的日子** 卖出该股票。设计一个算法来计算你所能获取的最大利润。

返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。

**示例 1：**

```
输入：[7,1,5,3,6,4]
输出：5
解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
```

### 解题思路

“只能买卖一次，且要求获得最大的利润”，实际上就是在数组中找到两个数：$nums[i]、nums[j], i \lt j$，求解出 $nums[j] - nums[i]$​ 的最大值。

我们可以遍历整个数组，遍历到下标 $i$ 时，记录前面的最小值和最大差值，不断更新这两个变量，直到遍历完成。

### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        // max : 最大的利润， minPrev : 前面元素中的最小值
        int n = prices.length, max = 0, minPrev = prices[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(prices[i] - minPrev, max);
            minPrev = Math.min(minPrev, prices[i]);
        }
        return max;
    }
}
```

时间复杂度：$O(N)$，空间复杂度：$O(1)$。

## [122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)

### 问题描述

给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。

注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1:**

```
输入: prices = [7,1,5,3,6,4]
输出: 7
解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
```

### 解题思路1：遍历

不限交易次数，假设获得最大利润时，交易次数为 $K$，可以把数组划分成 $K$ 段，例如 $nums[i_1,j_1],...,nums[i_2,j_2],...,nums[i_k,j_k]$，每一段内都能获得一个正值的收益，且该收益是该段内最大的收益。

我们为什么不在 $nums[j_1+1,i_2-1]$ 范围内进行交易？因为这段元素无法计算出一个正值的收益，也就是说这个子段中的元素是逆序的。

进一步理解就是：整个数组 $nums$ 被一些逆序的子段分割了，在这些逆序的子段范围内不能进行交易，我们只需要在正序的子段上做交易，就可以得到最大的利润。

**在正序的子段上怎么进行交易？**

假设子段 $nums[i,j]$ 是正序的，根据问题《[121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)》，用最大值减去最小值（$nums[j] - nums[i]$）就能得到这个子段上的最大利润。该公式等价于：

$nums[j] - nums[i] = nums[j] - nums[j-1] + nums[j-1] - nums[j-2]+ ... + nums[i+1] - nums[i]$

因此，在一个正序的子段里，可以用后续的元素减去前一个元素，把所有的差值累加，就能得到这个子段内的最大利润。

#### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        int res = 0;
        for (int i = 1;i < prices.length;i++) {
            if(prices[i] > prices[i-1]) {
                res += prices[i] - prices[i-1];
            }
        }
        return res;
    }
}
```

时间复杂度：$O(N)$，空间复杂度：$O(1)$。

### 解题思路2：动态规划

可以使用“动态规划”来求解这一题。

问题要求解的是 ”最后一天结束后用户能够获得的最大利润“，我们可以记录每一天结束后，用户已获得的利润。每天用户已获得的利润与 “用户的持有状态（用户当天的操作）” 有关：

- 手上持有股票 + 买入：已获得的利润 = 前一天的利润 - 股票的价格
- 手上不持有股票 + 卖出：已获得的利润 = 前一天的利润 + 股票的价格
- 不买不买，股票持有状态不变，已获得的利润 与 前一天的利润相同

用户当天的 “买入和卖出” 操作会导致已获得的利润发生变化，因此，我们需要使用一个二维数组，用来记录在不同操作下已获得的最大利润。

求解过程如下：

1、“状态”：在第 $i$ 天结束后，用户当天买入或卖出后，已获得的最大利润；

2、“数组”：构造数组 $dp[i][2]$，$dp[i][0]$ 表示第 $i$ 天卖出股票后（不持有）已获得的最大利润，$dp[i][1]$ 表示第 $i$ 天买入股票后（持有）已获得的最大利润；

3、“base case”：$i == 0$ 时，$dp[0][0] = 0，dp[0][1] = -nums[0]$

4、构造状态转移方程：
$$
\begin{cases}
dp[i][0]= Max\{dp[i-1][0],dp[i-1][1] + nums[i]\}
\\dp[i][1] = Max\{dp[i-1][1],dp[i-1][0] - nums[i]\}
\end{cases}
$$
从“构造函数”可以看出，只使用到了4个变量：$dp[i-1][0]、dp[i-1][1]、dp[i][0]、dp[i][1]$，可以使用 4 个变量来降低动态规划的空间复杂度。

#### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        // pre0 = dp[i-1][0], pre1 = dp[i-1][1], cur0 = do[i][0], cur1 = dp[i][1]
        int pre0 = 0, pre1 = -prices[0], cur0 = 0, cur1 = 0;
        for (int i = 1; i < prices.length; i++) {
            cur0 = Math.max(pre0, pre1 + prices[i]);
            cur1 = Math.max(pre1, pre0 - prices[i]);
            pre0 = cur0;
            pre1 = cur1;
        }
        return Math.max(cur0, cur1);
    }
}
```

时间复杂度：$O(N)$，空间复杂度：$O(1)$。

## [123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

### 问题描述

给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。

注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1:**

```
输入：prices = [3,3,5,0,0,3,1,4]
输出：6
解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
```

### 解题思路

该问题与《[122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)》相似，额外限制了 “只能完成2次交易”，因此，某一天结束时，除了要记录股票的持有状态、当天的交易，还要记录已经交易的次数，此时的”状态“有：

* 持有股票 + 只买过一次
* 不持有股票 + 买卖过一次
* 持有股票 + 第二次买入（完成过一次交易）
* 不持有股票 + 第二次卖（完成过两次交易）
* 不持有股票 + 从没买过也没卖过，这种状态下利润永远是0，不满足问题要求，忽略不计

使用动态规划来求解问题，求解过程如下：

1、“状态”：在第 $i$ 天结束后，用户在4种状态下，已获得的最大利润；

2、“数组”：构造数组 $dp[i][2][2]$​：

* $dp[i][1][0]$ 表示第 $i$ 天结束后，持有股票，且只买过一次的最大利润；
* $dp[i][0][0]$ 表示第 $i$ 天结束后，不持有股票，且只卖出过一次的最大利润；
* $dp[i][1][1]$ 表示第 $i$ 天结束后，持有股票，且第二次买入的最大利润；
* $dp[i][0][1]$ 表示第 $i$ 天结束后，不持有股票，且第二次卖出后的最大利润；

3、“base case”：$dp[0][0][0] = dp[0][0][1] = 0, dp[0][1][0] = dp[0][1][1] = - nums[0]$

4、构造状态转移方程：
$$
\begin{cases}
dp[i][1][0] = Max\{dp[i-1][1][0],-nums[i]\}
\\dp[i][0][0] = Max\{dp[i-1][0][0],dp[i-1][1][0] + nums[i]\}
\\dp[i][1][1] = Max\{dp[i-1][1][1],dp[i-1][0][0]-nums[i]\}
\\dp[i][0][1] = Max\{dp[i-1][0][1],dp[i-1][1][1] + nums[i]\}
\end{cases}
$$
同样可以用4个变量来优化空间复杂度。

### 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        // buy1 : dp[i][1][0], sell1 : dp[i][0][0]
        // buy2 : dp[i][1][1], sell2 : dp[i][0][1]
        int n = prices.length, buy1 = -prices[0], buy2 = -prices[0], sell1 = 0, sell2 = 0;

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

时间复杂度：$O(N)$，空间复杂度：$O(1)$。

## [188. 买卖股票的最佳时机 IV](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/)

### 问题描述

给定一个整数数组 prices ，它的第 i 个元素 prices[i] 是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。

注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1：**

```
输入：k = 2, prices = [2,4,1]
输出：2
解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
```

### 解题思路

这一题和《[123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)》相似，限制条件从“最多交易2笔”变成了“最多交易K笔”。因此，我们要记录每一天结束后，当前交易的次数。

动态规划求解过程与问题《[123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)》相似，过程如下：

1、“状态”：在第 $i$ 天结束后，用户的股票持有状态、应已经交易过次数，以及此时获得的最大利润；

2、“数组”：构造数组 $dp[i][K][2]$​：

* $dp[i][k][1]$ 表示第 $i$ 天结束后持有股票，且已经交易过 $k$ 次
* $dp[i][k][0]$ 表示第 $i$ 天结束后不持有股票，且已经交易过 $k$ 次

3、“base case”：

* $dp[0][0][1] = -nums[0]、dp[0][0][0] = 0$;
* 因为第0天时，除了买入，不能做任何事，所以用一个最小值来表示 $dp[0][k][0]、dp[0][k][1]$ 不合法：$dp[0][k][0] = dp[0][k][1] = Integer.MIN$; 

4、构造状态转移方程：
$$
\begin{cases}
dp[i][k][0] = Max\{dp[i-1][k][0],dp[i-1][k-1][1]+nums[i]\}
\\dp[i][k][1] = Max\{dp[i-1][k][1],dp[i-1][k][0] - nums[i]\}
\end{cases}
$$
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
        int max = 0;
        
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

给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

- 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
- 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

**示例:**

```
输入: [1,2,3,0,2]
输出: 3 
解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
```

### 解题思路

这一题与《[122. 买卖股票的最佳时机 II](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/)》相似，都没有限制交易笔数，只不过在每次卖出后多了一天的冷却期。

同样我们可以使用“动态规划”来求解问题，求解过程如下：

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

## 通用模板



## 总结



## 参考阅读

1、[股票问题系列通解](https://leetcode-cn.com/circle/article/qiAgHn/)

