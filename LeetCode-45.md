# [45. 跳跃游戏 II](https://leetcode-cn.com/problems/jump-game-ii/)

> 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
>
> 数组中的每个元素代表你在该位置可以跳跃的最大长度。
>
> 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
>
> 假设你总是可以到达数组的最后一个位置。

**示例 1:**

```tex
输入: nums = [2,3,1,1,4]
输出: 2
解释: 跳到最后一个位置的最小跳跃数是 2。
     从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
```

**示例 2:**

```tex
输入: nums = [2,3,0,1,4]
输出: 2
```

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>


# 分析阶段

> 动态规划

输入一个数组 $nums$，可以从下标 $i$ 处，最远跳到下标 $i+nums[i]$ 处，求解最少跳跃几次，能够达到最后一个下标。

按照“动态规划”的解题思路，我们需要确定：状态、base case、数组、状态转移方程。

## 1、问题类型

第二类：对某种数结构和算法的使用

使用的算法：动态规划

数据结构：构造状态数组

## 2、解题思路

### 解法1

要求“最少跳跃几次”能够到达最后一个下标，我们可以直接构造一个数组，来保存在每个下标处需要的最少次数。求解过程如下：

1、要求解的“状态”是：输入长度为 $n$ 的数组，最少跳跃几次到最后一个下标；

2、简化状态，得到“base case”：在下标 $n-1$ 处，需要跳跃的次数为0；

3、构造“数组”：构造一个长度为 $n$ 的数组 $dp$，$dp[i] == j$ 表示在下标 $i$ 处需要最少跳跃 $j$ 次才能到最后；

4、构造状态转移方程：

（1）在下标 $n-1$ 处，跳跃次数为 0，$dp[n-1] = 0$；

（2）在下标 $i$ 处，假设 $nums[i]==j$，那么此时可以跳跃的范围下标是：$[i+1,i+2,...,i+j]，dp[i] = min(dp[i+1,i+2,...,i+j]) + 1$；

状态转移方程如下：
$$
dp[i]=
\begin{cases}
0, & i = n-1
\\[2ex] min(dp[i+1],dp[i+2],...,dp[i+j]), & nums[i] == j, 0 \le i < n-1
\end{cases}
$$
代码实现在“编码阶段-实现1”部分。

在每个下标处，都要在一个范围内找出最小值，性能很差，在 LeetCode 的提交结果如下：

![image-20210728233525903](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20210728233525903.png)

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.gif"/>
</div>

### 官方题解

[官方题解](https://leetcode-cn.com/problems/jump-game-ii/solution/tiao-yue-you-xi-ii-by-leetcode-solution/)给出的解法很难理解，用一个简单示例来描述：一辆汽车从下标0处开始出发，每个下标上的值表示它在该位置加油后能够走的距离，最少加几次油能到达终点。

官方题解中的两个变量：边界 end、最远下标 maxPosition，可以理解为：

* 边界 end：如果汽车在中途不加油，最远只能行驶到 end 这个位置，到了这个位置，就必须要加油，不然没法继续前进
* 最远下标 maxPosition：如果汽车在中途某个位置 i 处加一次油，最远能跑到的位置

这样，汽车从起点开始出发，路过每一个加油点，不断计算着自己的 end 和 maxPosition，整个过程如下：

* 在到达 end 之前中途不加油，直到 end 才加油，加油次数加1
* 在到达 end 之前，在途中下标 $i$ 处加了一次油，到了 end 不需要加油，加油次数也要加1，这个算的是下标 $i$ 处的那一次加油，并且 end 变成 maxPosition，表示在下标 $i$ 处加油后能够到达 maxPosition
* 越过第一个 end 之后，汽车重复前面步骤，最终到达终点

代码实现在“编码阶段-官方题解”部分。视频演示如下：

<video id="video" controls="" preload="none">       
    <source id="mp4" src="https://gameacademy.v.netease.com/2021/0807/718a4f4869ecece017d98c3c5523756dqt.mp4" type="video/mp4">
</video>

<div align=center>
<img src="https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/640.png"/>
</div>

# 编码阶段

## 实现1

```java
public int jump(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    dp[n - 1] = 0;
    for (int i = n - 2; i >= 0; i--) {
        if (nums[i] > 0) {
            dp[i] = getMin(dp, i + 1, i + nums[i]) + 1;
        } else {
            dp[i] = Integer.MAX_VALUE;
        }
    }
    return dp[0];
}

/**
 * 从数组 dp 中，取出 dp[start,...,end] 范围内的最小值
 */
private int getMin(int[] dp, int start, int end) {
    int min = Integer.MAX_VALUE;
    for (int i = start; i <= end; i++) {
        if (i >= dp.length) {
            return min;
        }
        if (dp[i] < min) {
            min = dp[i];
        }
    }
    if (min == Integer.MAX_VALUE) {
        // 如果返回 MAX，返回时再加1，会越界
        return Integer.MAX_VALUE - 1;
    } else {
        return min;
    }
}
```

## 官方题解

```java
public int jump(int[] nums) {
    if (nums.length == 1) {
        return 0;
    }
    int n = nums.length, maxPosition = nums[0], end = nums[0], step = 1;
    for (int i = 1; i < n - 1; i++) {
        // 不断计算 maxPosition
        maxPosition = Math.max(maxPosition, i + nums[i]);
        // 到达边界，必须加一次油，或者在前面某个下标处加过一次油
        if (i == end) {
            end = maxPosition;
            step++;
        }
    }
    return step;
}
```

# 总结阶段

通过“汽车加油”来类比“跳跃几次”，每次到达边界都加油一次。

