![](https://github.com/ShiMengjie/LeetCode/blob/master/pictures/Pic.png)

做算法题目的是锻炼解决问题的思维和能力，解决问题的基本思维是什么？

1、把问题不断分解，分解成多个自己会的小问题，依次去解决这些小问题

2、对于约束条件比较多的问题，可以先从一般性考虑，然后再不断增加条件，逐渐复杂化。

## 做题策略

`必读文章1`中给出了做算法题的建议，把做题分成三个阶段，每个阶段用番茄时钟来控制时间，一来可以保证思维的高度活跃，二来避免过度疲劳。

### 三个阶段

#### 1、分析阶段

**内容**：确定问题所属的类型、制定解题思路

**要求**：对不同类型的问题，有不同的要求



#### 2、编码阶段

**内容**：实现/验证“分析阶段”提出的解题思路

**要求**：想清楚、一次写好，如果发现实现过程比较困难，说明没有分析清楚，要回到上一阶段，重新分析



#### 3、总结阶段

**内容**：对解题的整个过程做一个总结

**要求**：反思犯下的错误、问题的特点和独特之处、解题过程中的关键



### 问题类型

`必读文章1`把问题分为以下三类：

1、对某种复杂规则的彻底解析，很有可能要构造状态机，充分考虑边界情况

2、对某种数据结构及算法的应用

3、对数学概念、遍历、动态规划等的综合应用

#### 问题分析和总结模板

每种类型的分析和总结模板：

1、第一类：对某种复杂规则的彻底解析

分析：

- 理清题目背后解法要用的技术
- 充分收集可能涉及到的边界

总结：

- 是否理清了要用的技术
- 是否有不确定的地方
- 收集到的边界是否能覆盖所有情况



2、第二类：对某种数据结构及算法的应用

分析：

- 将问题转化为对相应数据结构的问题

总结：

- 需要一种数据结构还是多种
- 相应数据结构是否能完全覆盖题目问题中的所有情况



3、第三类：对数学概念、遍历、动态规划等的综合应用

分析：

* 判断出题目类型

- 如果发现题目能从遍历的角度解决问题，那么可以往遍历的优化上去想，例如是否在遍历的时候能够排除掉一些情况，或者通过排序等手段之后能实现遍历时排除某些情况
- 如果发现题目中存在多种约束关系，然后求某个值，那么可以往数学方程组上去想
- 如果发现问题可以被递归解决，并且能够将递归方式转化成顺序方式，可以往动态规划上去想

总结：

- 是否有其他类型更适合
- 是否需要多种手段结合



### 禁止的行为

1、编码过程中切忌边写边改分析阶段的思路和方案

2、编程状态如果不好，要加长休息或结束掉，禁止强行工作

3、每个阶段结束，进入下个阶段之前，要休息一段时间，彻底理解上个阶段的内容后再开始，禁止不加思考地进入下个阶段



## 题解

### Array

#### [001. Two Sum](https://github.com/ShiMengjie/LeetCode/blob/master/Q_001.md)

#### [004. Median of Two Sorted Arrays](https://github.com/ShiMengjie/LeetCode/blob/master/Q_004.md)

#### [0011. Container With Most Water](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0011.md)

#### [0015. 3Sum](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0015.md)

#### [0016. 3Sum Closest](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0016.md)

#### [0018. 4Sum](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0018.md)

#### [0026. Remove Duplicates from Sorted Array](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0026.md)

#### [0027. Remove Element](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0027.md)

#### [0031. Next Permutation](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0031.md)

#### [0031. Search in Rotated Sorted Array](https://github.com/ShiMengjie/LeetCode/blob/master/Q_0033.md)

## 必读文章

1、[如何有效地做算法题](https://www.cnblogs.com/sskyy/p/8268976.html)

