

用二叉树或前缀树试试看？

# 1、Two Sum

> Given an array of integers, return **indices** of the two numbers such that they add up to a specific target. You may assume that each input would have ***exactly*** one solution, and you may not use the *same* element twice.

**Example：**

> Given nums = [2, 7, 11, 15], target = 9,
>
> Because nums[**0**] + nums[**1**] = 2 + 7 = 9,
> return [**0**, **1**].

## 解法1

要在一个数组`nums`中找到两个数` num[i]、num[j]`，让两者之和等于目标值`target=num[i] + num[j]`。

可以把问题分解成两个小问题：

1、先找到第一个数`num[i]`

2、然后找到第二个数`target- num[i]`



**怎么解决这两个小问题？**

1、遍历数组`nums`，依次把数组中的每一个元素作为`num[i]`

2、根据前一步确定的`num[i]`，依次遍历数组中剩下的元素，判断每个元素是否等于`target- num[i]`

### 代码实现

```java
/**
 * 在数组中找到和值等于目标值得两个元素
 *
 * @param nums   数组
 * @param target 目标值
 * @return 和值等于目标值得两个元素下标
 */
public static int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        int number = target - nums[i];
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[j] == number) {
                return new int[]{i, j};
            }
        }
    }
    return null;
}
```

需要两层的嵌套循环，时间复杂度为：$O(n^2)$，没有使用额外空间，空间复杂度：$O(1)$。

## 解法2

在第二个小问题中，目的是根据元素 `nums[i]` 找到 `target - nums[i]` 的下标。

如果我们维护一个元素值与下标的映射关系，类似于 HashMap 中的 K-V 关系：`num[i] -> i`、`target - num[i] -> j`，当遍历到 `num[i]`时，判断 `target - nums[i]` 是否在 map 中，存在就取出下标，不存在就把 `num[i] -> i` 保存进 map 中，这样只需要一次遍历。

### 代码实现

```java
/**
 * 在数组中找到和值等于目标值得两个元素
 *
 * @param nums   数组
 * @param target 目标值
 * @return 和值等于目标值得两个元素下标
 */
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    int number = 0;
    for (int i = 0; i < nums.length; i++) {
        number = target - nums[i];
        if (map.containsKey(number)) {
            return new int[]{map.get(number), i};
        }
        map.put(nums[i], i);
    }
    return null;
}
```

只需要一次遍历，时间复杂度：$O(n)$，需要额外空间保存map，空间复杂度：$O(n)$。



------

# 15. 3Sum

> Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.
>
> Notice that the solution set must not contain duplicate triplets.

**Example 1:**

```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

**Example 2:**

```
Input: nums = []
Output: []
```

**Example 3:**

```
Input: nums = [0]
Output: []
```

## 解法1

在一个数组中，找到三个元素：`nums[i]、nums[j]、nums[k]`，要求它们的和值等于0，找到所有不同的三个元素。

问题有两个约束条件：

1、三个元素相加，结果等于0

2、三个元素组成的一个列表数组，数组中各个列表元素内容不相同

从简单到复杂，逐步增加约束条件：

* 假设只有第一个约束条件，可以先确定第一个元素`nums[i]`，在剩余的数组中，找到和值等于`-nums[i]`的两个元素。
* 增加第二个约束条件，在找到第二、第三个元素后，要对`nums[i]、nums[j]、nums[k]`的组合去重。

### 代码实现

```java
/**
 * 在数组中找到和值为0,且不重复的三元组
 *
 * @param nums 数组
 * @return 三元组列表
 */
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Set<String> set = new HashSet<>();
    Set<Integer> setNum = new HashSet<>();

    for (int i = 0; i < nums.length; i++) {
        
        if(setNum.contains(nums[i])) {
            continue;
        }
        setNum.add(nums[i]);

        Map<Integer, Integer> map = getTwoSumMap(nums, -nums[i], i);
        if (map.size() > 0) {
            for (Integer key : map.keySet()) {
                if (map.get(key) == null) {
                    continue;
                }
                List<Integer> subList = new ArrayList<>();
                subList.add(nums[i]);
                subList.add(key);
                subList.add(map.get(key));
                // 生成唯一的组合字符串
                String str = generateUniqueStr(subList);
                if (set.contains(str)) {
                    continue;
                } else {
                    set.add(str);
                }

                list.add(subList);
            }
        }
    }
    return list;
}

/**
 * 从数组中找到两个元素，它们的和值等于输入的目标值 target
 *
 * @param nums   数组
 * @param target 目标值
 * @param i      排除的元素下标
 * @return map<Integer, Integer>，包含两个元素
 */
public Map<Integer, Integer> getTwoSumMap(int[] nums, int target, int i) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int j = 0; j < nums.length; j++) {
        if (j == i) {
            continue;
        }
        int key = nums[j];
        // 如果当前元素不在 map 中：
        // 1、包含 target - key，设置对应的value值
        // 2、不包含 target - key，设置 key 值
        if (!map.containsKey(key)) {
            if (map.containsKey(target - key)) {
                map.put(target - key, key);
            } else {
                map.put(key, null);
            }
        } else {
            // 如果当前元素在 map 中，要注意一种特殊情况 key == target - key
            if (key == target - key) {
                map.put(key, target - key);
            }
        }
    }
    return map;
}

/**
 * 对列表中元素生成为一个字符串
 *
 * @param subList 列表
 * @return 唯一的字符串组合
 */
public String generateUniqueStr(List<Integer> subList) {
    // 先排序
    subList.sort(Comparator.comparingInt(o -> o));
    // 用字符串拼接
    StringBuilder sb = new StringBuilder();
    for (Integer num : subList) {
        sb.append(num).append(":");
    }
    return sb.toString();
}
```

然而，这个解法的性能惨不忍睹，LeetCode 的结果如下：

![question_15](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/imgquestion_15.png)

分析一下这个解法的时间复杂度：

1、为了找到第一个元素，在外层循环中对数组进行一次遍历

2、为了找到第二、第三个元素，需要再一次遍历整个数组

3、为了确定三个元素的唯一性，需要对三个元素进行排序和遍历，时间复杂度为常量

综上，这个解法的时间复杂度为：$O(N^2) + O(N)$

## 解法2

上面的解法中，浪费时间的步骤有两处：

1、每次在查找第二、第三个元素时，都要对整个数组进行遍历

2、在找到所有三个元素后，要排序后生成唯一字符串

之所以这样做，原因有两个：

1、当我们确定了第一个元素`nums[i]`时，无法知道它的第二、第三个元素`nums[j]、nums[k]`在前面是否已经遍历过了，只能对整个数组进行遍历查找

2、确定了三个元素后，无法知道这三个元素组合是否被保存过，只能做一次去重

比如，已经保存了三个元素`[A,B,C]`，之后在遍历到`[B,A,C]`组合中的`B`时，要去查找`A、C`，如果我们能知道`A`之前被遍历过，就可以跳过查找的步骤？

<font color=ff0000>**怎么才能知道知道要查找的元素已经被遍历过？**</font>

大佬的解法是：对数组先做<font color=ff0000>**排序**</font>，然后在有序数组中查找组合。

### 代码实现

```java
public List<List<Integer>> threeSum(int[] nums) {
    // 排序
    Arrays.sort(nums);

    List<List<Integer>> list = new ArrayList<>();

    for (int i = 0; i < nums.length; i++) {
        // 如果当前元素不是第一个且与前一个数值相等，说明该元素已经遍历过了，跳过
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        int target = -nums[i];

        int j = i + 1;
        int k = nums.length - 1;
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
```

一共需要遍历2次数组，但是在内层遍历中，不需要对整个数组进行查找，时间复杂度是：$O(N^2)$，空间复杂度是：$O(1)$。

在这个解法中，假设有序为：`[AABBCC]`，针对重复组合的处理如下：

* 第一个下标`i`遍历完元素`A`，保存元素组合`[A,B,C]`
* 遍历到元素`B`时，这个时候从第二个下标`j = i+1`开始查找第二个元素，不会遍历整个数组

因为数组是有序的，当遍历到元素`B`时，就已经保证所有小于`B`的元素（比如A）所在的组合，都已经找到了，不需要再重新遍历整个数组，用有序性避免了重复查找。

## 总结

对数组进行排序和去重，都可以避免重复元素被再次查找出来。



------

# 18. [4Sum](https://leetcode.com/problems/4sum)

> Given an array `nums` of `n` integers, return *an array of all the **unique** quadruplets* `[nums[a], nums[b], nums[c], nums[d]]` such that:
>
> * `0 <= a, b, c, d < n`
>
> * ``a`, `b`, `c`, and `d` are **distinct**.
>
> * `nums[a] + nums[b] + nums[c] + nums[d] == target`
>
> You may return the answer in **any order**.

 **Example 1:**

```
Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
```

**Example 2:**

```
Input: nums = [2,2,2,2,2], target = 8
Output: [[2,2,2,2]]
```

## 解法

给定一个数组 `nums `和一个目标值 `target`，判断数组 `nums `中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与  `target`相等？找出所有满足条件且不重复的四元组。

这道题类似于`Q_0015`，只不过多了一层循环，代码如下。

### 代码实现

```java
public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums == null || nums.length < 4) {
        return result;
    }
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        List<List<Integer>> list = getThreeSum(nums, target - nums[i], i);
        if (list.size() > 0) {
            for (List<Integer> subList : list) {
                subList.add(nums[i]);
            }
            result.addAll(list);
        }
    }
    return result;
}

/**
     * 从有序数组中，找到和值等于目标值 target 的三个元素
     *
     * @param nums   有序数组
     * @param target 目标值
     * @param begin  开始排除的下标
     * @return
     */
public List<List<Integer>> getThreeSum(int[] nums, int target, int begin) {
    List<List<Integer>> list = new ArrayList<>();
    if (nums == null || nums.length < 3) {
        return list;
    }
    for (int i = begin + 1; i < nums.length; i++) {
        if (i > begin + 1 && nums[i] == nums[i - 1]) {
            continue;
        }
        int j = i + 1;
        int k = nums.length - 1;
        while (j < k) {
            int sum = nums[i] + nums[j] + nums[k];
            if (sum == target) {
                List<Integer> subList = new ArrayList<>(3);
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
```

时间复杂度：$O(N^3)$

## 总结

没啥好总结的。

## 参考阅读

[一个函数秒杀 2Sum 3Sum 4Sum 问题](https://mp.weixin.qq.com/s/fSyJVvggxHq28a0SdmZm6Q)