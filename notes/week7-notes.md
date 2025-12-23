# Week 7: 快速排序（Quick Sort）

## 📚 学习目标

- 掌握快速排序的基本原理
- 理解分区（Partition）算法
- 了解随机化和三向切分优化
- 完成Interview Questions练习题（不计分）

> **注意**：Week 7没有计分作业，只有Interview Questions练习

---

## 🎯 核心概念

### 1. 快速排序基本思想

**分治策略**：
1. **分区（Partition）**：选择一个元素作为切分元素（pivot），将数组分为两部分
   - 左边：所有元素 ≤ pivot
   - 右边：所有元素 ≥ pivot
2. **递归排序**：分别对左右两部分递归调用快速排序
3. **合并**：无需合并，数组已经有序

### 2. 分区算法详解

```java
// 经典分区算法（Hoare Partition）
private static int partition(Comparable[] a, int lo, int hi) {
    int i = lo, j = hi + 1;
    Comparable v = a[lo];  // 切分元素
    
    while (true) {
        // 从左向右找到第一个 >= v 的元素
        while (less(a[++i], v)) {
            if (i == hi) break;
        }
        
        // 从右向左找到第一个 <= v 的元素
        while (less(v, a[--j])) {
            if (j == lo) break;
        }
        
        // 指针相遇，退出
        if (i >= j) break;
        
        // 交换
        exch(a, i, j);
    }
    
    // 将切分元素放到正确位置
    exch(a, lo, j);
    return j;
}
```

### 3. 快速排序实现

```java
public class Quick {
    
    public static void sort(Comparable[] a) {
        // StdRandom.shuffle(a);  // 打乱数组，消除对输入的依赖
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        
        int j = partition(a, lo, hi);  // 分区
        sort(a, lo, j - 1);            // 左半部分排序
        sort(a, j + 1, hi);            // 右半部分排序
    }
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
```

---

## 🚀 性能分析

### 时间复杂度

| 情况 | 复杂度 | 说明 |
|------|--------|------|
| **最好情况** | O(n log n) | 每次分区都平衡 |
| **平均情况** | O(n log n) | 随机输入 |
| **最坏情况** | O(n²) | 数组已排序（未随机化） |

### 空间复杂度
- O(log n) - 递归调用栈

### 特点
- ✅ **原地排序**：不需要额外空间（除了递归栈）
- ✅ **实际性能优秀**：常数因子小，缓存友好
- ❌ **不稳定**：相同元素的相对位置可能改变
- ⚠️ **对输入敏感**：需要随机化

---

## 🔧 优化技术

### 1. 随机化

**问题**：如果数组已经有序，未优化的快速排序会退化到O(n²)

**解决**：在排序前随机打乱数组
```java
StdRandom.shuffle(a);
```

### 2. 小数组优化

**策略**：对小数组（如长度<10）使用插入排序
```java
private static final int CUTOFF = 10;

private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo + CUTOFF - 1) {
        Insertion.sort(a, lo, hi);
        return;
    }
    // ... 正常快速排序
}
```

### 3. 三向切分（3-Way Partitioning）

**适用场景**：数组中有大量重复元素

**思想**：将数组分为三部分
- 小于切分元素
- 等于切分元素
- 大于切分元素

```java
public class Quick3way {
    
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else              i++;
        }
        
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
```

**性能提升**：
- 对于有k个不同值的n个元素
- 时间复杂度从O(n log n)降到O(n log k)

---

## 💡 快速选择算法（Quick Select）

**问题**：找第k大的元素

**思路**：利用快速排序的分区操作，只递归处理包含第k个元素的那一半

```java
public static Comparable select(Comparable[] a, int k) {
    StdRandom.shuffle(a);
    int lo = 0, hi = a.length - 1;
    
    while (hi > lo) {
        int j = partition(a, lo, hi);
        
        if      (j < k) lo = j + 1;  // k在右半部分
        else if (j > k) hi = j - 1;  // k在左半部分
        else            return a[k]; // 找到了
    }
    return a[k];
}
```

**时间复杂度**：平均O(n)，最坏O(n²)

---

## 📝 Interview Questions（Week 7）

Week 7的练习题都是不计分的，主要用于巩固快速排序的理解。

### 常见题型

#### 1. **Nuts and Bolts Problem**
给定n个螺母和n个螺栓，每个螺母对应一个螺栓。只能比较螺母和螺栓，不能比较螺母之间或螺栓之间。如何配对？

**思路**：类似快速排序的双向分区
- 用一个螺栓对螺母数组分区
- 用对应的螺母对螺栓数组分区
- 递归处理

**时间复杂度**：O(n log n)

#### 2. **Selection in Two Sorted Arrays**
给定两个已排序数组，找第k小的元素。

**思路**：二分查找
- 在两个数组中各取一半
- 比较中位数，排除不可能的部分

**时间复杂度**：O(log n)

#### 3. **Decimal Dominants**
找出数组中出现次数超过n/10的元素。

**思路**：
- 快速选择的变种
- 或使用HashMap计数

**时间复杂度**：O(n)

---

## 🎓 LeetCode相关题目

| 题号 | 题目 | 难度 | 知识点 |
|------|------|------|--------|
| 912 | 排序数组 | Medium | 快速排序 |
| 215 | 数组中的第K个最大元素 | Medium | 快速选择 |
| 75 | 颜色分类（荷兰国旗） | Medium | 三向切分 |
| 973 | 最接近原点的K个点 | Medium | 快速选择 |
| 347 | 前K个高频元素 | Medium | 快速选择变种 |

---

## 🔍 快速排序 vs 归并排序

| 特性 | 快速排序 | 归并排序 |
|------|---------|---------|
| **平均时间** | O(n log n) | O(n log n) |
| **最坏时间** | O(n²) | O(n log n) |
| **空间复杂度** | O(log n) | O(n) |
| **稳定性** | ❌ 不稳定 | ✅ 稳定 |
| **实际性能** | ✅ 更快（常数小） | 较慢 |
| **应用场景** | 通用排序 | 需要稳定性 |

---

## ✅ 关键要点总结

1. **核心思想**：分治 + 分区
2. **分区操作**：O(n)时间，将元素放到正确位置
3. **性能关键**：随机化避免最坏情况
4. **优化技巧**：小数组插入排序、三向切分
5. **快速选择**：找第k大元素的线性时间算法
6. **实际应用**：系统排序库的首选（如Java的Arrays.sort对基本类型）

---

## 📖 延伸阅读

- 《算法（第4版）》2.3节 - 快速排序
- Hoare, C. A. R. (1961). "Quicksort"
- Bentley, J., and McIlroy, M. D. (1993). "Engineering a Sort Function"

---

## 🤔 思考题

1. 为什么快速排序在实践中通常比归并排序快？
2. 如果数组中所有元素都相同，未优化的快速排序会怎样？
3. 快速排序是原地排序吗？递归调用栈算不算额外空间？
4. 如何改进快速排序使其在接近有序的数组上表现更好？

---

## 📅 学习进度

- [ ] 理解快速排序原理
- [ ] 手写标准快速排序
- [ ] 理解三向切分
- [ ] 实现快速选择算法
- [ ] 完成Week 7 Interview Questions
- [ ] 做LeetCode #912（排序数组）
- [ ] 做LeetCode #215（第K大元素）
- [ ] 做LeetCode #75（荷兰国旗-三向切分）
- [ ] 总结快速排序vs其他排序

---

**学习日期**：  
**完成日期**：  
**学习耗时**：

