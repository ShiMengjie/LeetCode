# LeetCode-N Sum问题整理

## 问题列表

[1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

[15. 三数之和](https://leetcode-cn.com/problems/3sum/)

[18. 四数之和](https://leetcode-cn.com/problems/4sum/)

相似问题：

[167. 两数之和 II - 输入有序数组](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)

[560. 和为 K 的子数组](https://leetcode-cn.com/problems/subarray-sum-equals-k/)

[653. 两数之和 IV - 输入 BST](https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst/)

## [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

### 问题描述

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

你可以按任意顺序返回答案。

**示例 1：**

```tex
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
```

### 解题思路

在一个整数数组 `nums` 中找到两个数 `nums[i]`、`nums[j]`，要两者之和要等于目标值 `target`。

#### 方法1

最直接的做法是：第一次遍历数组时，把每个元素作为一个数 `nums[i]`，然后第二次遍历数组，找到 `nums[j] = target - nums[i]`，对数组进行两次遍历或两层遍历就可以求解。

#### 方法2

方法1中，在寻找第二个数 `nums[j]` 时，问题变成了“在数组中寻找某个数”，这类问题的求解思路一般是：

- 在有序数组中使用二分查找（比如 Mysql）
- 在无序数组中使用 hash 查找（比如 Redis）

因为问题要求返回数字的下标，所以只能使用 hash 查找。

我们可以维护一个 `hashMap`，`key`值是数中数字的值，`value`是数字的下标。在遍历数组的过程中，判断 `nums[j] = target - nums[i]` 是否在 `hashMap`中，如果存在就取出下标，如果不存在就把 ` (nums[i],i)` 保存进 `hashMap`中。



两种方法的代码实现如下。

### 代码实现

#### 方法1

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            int num1 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - num1) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
}
```

#### 方法2

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        int num2;
        for (int i = 0; i < nums.length; i++) {
            num2 = target - nums[i];
            if (map.containsKey(num2)) {
                return new int[]{map.get(num2), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
```

## [15. 三数之和](https://leetcode-cn.com/problems/3sum/)

### 问题描述

给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。

注意：答案中不可以包含重复的三元组。

**示例 1：**

```
输入：nums = [-1,0,1,2,-1,-4]
输出：[[-1,-1,2],[-1,0,1]]
```

### 解题思路

与《[1. 两数之和](https://leetcode-cn.com/problems/two-sum/)》相比，这一题额外要求找出三个数 `nums[i]、nums[j]、nums[k]` 的组合，且组合不能重复。

可以仿照《[1. 两数之和](https://leetcode-cn.com/problems/two-sum/)》的解法：在第一层遍历中确定第一个数 `nums[i]`，第二层遍历在数组剩余范围内确定第二个数和第三个数。

那么怎么保证每次的组合不会重复呢？

最简单的方法是先对数组做<font color=ff0000>**排序**</font>，然后在确定第一个数时，要跳过相同的数，确定第二个数时，跳过相同的数。通过跳过相同的数，从而确保组合不会重复。

> 之所以可以排序，是因为问题不需要返回下标，只需要返回元素值，如果要求返回下标，只能先找到组合，再去重。

### 代码实现

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        // 排序
        Arrays.sort(nums);

        List<List<Integer>> list = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {
            // 跳过与前面元素相等的元素
            if (i >= 1 && nums[i] == nums[i - 1]) {
                continue;
            }
            // target : 剩余要找的目标数、j : 第二个数的下标、k : 第三个数的下标
            // 用双指针 j、k 遍历剩余的数
            int target = -nums[i], j = i + 1, k = nums.length - 1;
            while (j < k) {
                if (nums[j] + nums[k] == target) {
                    List<Integer> subList = new ArrayList<>(3);
                    subList.add(nums[i]);
                    subList.add(nums[j]);
                    subList.add(nums[k]);
                    list.add(subList);

                    j++;
                    k--;
                    // 同理，跳过与前一个值相等的元素
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else if (nums[j] + nums[k] < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }
        return list;
    }
}
```

## [18. 四数之和](https://leetcode-cn.com/problems/4sum/)

### 问题描述

给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] ：

- 0 <= a, b, c, d < n
- a、b、c 和 d 互不相同
- nums[a] + nums[b] + nums[c] + nums[d] == target

你可以按 任意顺序 返回答案 。

**示例 1：**

```tex
输入：nums = [1,0,-1,0,-2,2], target = 0
输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

### 解题思路

这一题与《[15. 三数之和](https://leetcode-cn.com/problems/3sum/)》相似，只不过多了一层循环，在第一层循环中确定第一个数，然后调用《[15. 三数之和](https://leetcode-cn.com/problems/3sum/)》的求解方法，求解出其他三个数。

### 代码实现

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return new ArrayList<>(1);
        }
        // 排序
        Arrays.sort(nums);

        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (i >= 1 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 找到剩余3个数
            List<List<Integer>> list = getThreeSum(nums, target - nums[i], i + 1);
            for (List<Integer> subList : list) {
                subList.add(nums[i]);
            }
            res.addAll(list);
        }
        return res;
    }

    public List<List<Integer>> getThreeSum(int[] nums, int target, int start) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>(1);
        }

        List<List<Integer>> list = new LinkedList<>();
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    List<Integer> subList = new ArrayList<>(4);
                    subList.add(nums[i]);
                    subList.add(nums[j]);
                    subList.add(nums[k]);
                    list.add(subList);

                    j++;
                    k--;

                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else if (sum < target) {
                    j++;
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                } else {
                    k--;
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                }
            }
        }
        return list;
    }
}
```

## NSum

前面问题的求解方法都很类似，求解 N 个数之和等于 target 值，就是在 “求解 N - 1 个数之和等于 target 值” 基础上，再加一层循环。最底层的 2Sum 的复杂度可以降低到 O(L)，所以 NSum 的复杂度是 O(L^{N-1})，L 是数组长度。

如果 N 值很大，比如100、10000，如果按照前面的写法，要不断地增加嵌套循环，代码无法实现，因此我们需要写出一个统一的能适用于 NSum 的代码模板。

### 回溯

问题是求解组合，所以很自然地想到使用回溯算法来求解。

定义回溯方法 $backtrack(res, path, idx, target)$ 含义是：从数组 nums 的下标 idx 开始，寻找和值等于 target 的组合，把组合添加到结果集 res 中，path 是选择路径。

回溯求解过程如下：

- 结束条件：路径长度等于 N，且 target == 0 时，表示已经找到一个组合，添加进结果集，返回上一个回溯点；
- 选择路径：记录已经选中的数字，作为组合中的元素；
- 选择：当前数字不是该层递归中第一个元素，且与前数组中前一个元素值不相等，就添加进选择列表中；
- 空间状态树：略。



回溯的确能够以一个模板方法来求解 NSum 问题，但是它的复杂度是 O(L^N)，时间复杂度比较高，一直无法通过 LeetCode 的最后几个测试用例。代码实现如下：

```java
class NSum {

    int n, target;
    int[] nums;

    public NSum(int[] nums, int n, int target) {
        // 排序
        Arrays.sort(nums);

        this.nums = nums;
        this.n = n;
        this.target = target;
    }

    public List<List<Integer>> findSumList() {
        List<List<Integer>> res = new LinkedList<>();
        this.backtrack(res, new LinkedList<>(), 0, target);
        return res;
    }

    private void backtrack(List<List<Integer>> res, List<Integer> path, int idx, int target) {
        if (path.size() == n && target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            if (i > idx && nums[i] == nums[i - 1]) {
                continue;
            }
            path.add(nums[i]);
            backtrack(res, path, i + 1, target - nums[i]);
            path.remove(path.size() - 1);
        }
    }
}
```

### 递归

前面在求解《[15. 三数之和](https://leetcode-cn.com/problems/3sum/)》和《[18. 四数之和](https://leetcode-cn.com/problems/4sum/)》时发现，两个问题的代码形式很相似，我们可以在这两题的代码上做一些修改，形成一个递归求解的方式。NSum 对象如下：

```java
/**
 * NSum 求解对象
 */
class NSum {

    int n, target;
    int[] nums;

    public NSum(int[] nums, int n, int target) {
        Arrays.sort(nums);
        this.nums = nums;
        this.n = n;
        this.target = target;
    }

    public List<List<Integer>> findSumList() {
        return recursion(n, target, 0);
    }

    public List<List<Integer>> recursion(int n, int target, int start) {
        if (n == 2) {
            return twoSum(target, start);
        } else {
            List<List<Integer>> res = new LinkedList<>();
            for (int i = start; i < nums.length; i++) {
                if (i >= start + 1 && nums[i - 1] == nums[i]) {
                    continue;
                }
                List<List<Integer>> list = recursion(n - 1, target - nums[i], i + 1);
                for (List<Integer> sub : list) {
                    sub.add(nums[i]);
                    res.add(sub);
                }
            }
            return res;
        }
    }

    /**
     * 查询和值为 target 的两个数的组合
     */
    private List<List<Integer>> twoSum(int target, int start) {
        List<List<Integer>> res = new LinkedList<>();
        int i = start, j = nums.length - 1;
        while (i < j) {
            if (nums[i] + nums[j] == target) {
                List<Integer> subList = new ArrayList<>(n);
                subList.add(nums[i]);
                subList.add(nums[j]);
                res.add(subList);

                i++;
                j--;
                while (i < j && nums[i] == nums[i - 1]) {
                    i++;
                }
                while (i < j && nums[j] == nums[j + 1]) {
                    j--;
                }
            } else if (nums[i] + nums[j] < target) {
                i++;
                while (i < j && nums[i] == nums[i - 1]) {
                    i++;
                }
            } else {
                j--;
                while (i < j && nums[j] == nums[j + 1]) {
                    j--;
                }
            }
        }
        return res;
    }
}
```

此时《[15. 三数之和](https://leetcode-cn.com/problems/3sum/)》和《[18. 四数之和](https://leetcode-cn.com/problems/4sum/)》的求解代码如下：

```java
public List<List<Integer>> threeSum(int[] nums) {
    return new NSum(nums,3,0).findSumList();
}

public List<List<Integer>> fourSum(int[] nums, int target) {
    return new NSum(nums,4,target).findSumList();
}
```

## 总结

通过对几个相似问题的求解，得出了一个求解 NSum 问题的通用模板。

## 参考阅读

1、[一个函数秒杀 2Sum 3Sum 4Sum 问题](https://mp.weixin.qq.com/s/fSyJVvggxHq28a0SdmZm6Q)
