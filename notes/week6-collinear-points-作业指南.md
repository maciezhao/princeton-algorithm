# Week 6 作业：Collinear Points（共线点问题）

> **重要**：这是Week 6 归并排序的计分作业，需要补做！

---

## 📋 作业概述

### 问题描述
给定平面上的n个点，找出所有包含**4个或更多点**的共线线段（在同一条直线上）。

### 应用场景
- **计算机视觉**：识别图像中的线条和边缘
- **模式识别**：发现数据中的线性关系
- **天文学**：检测星体的排列
- **数据分析**：找出相关性

### 作业要求
实现两个类：
1. **Point.java**：表示平面上的点
2. **BruteCollinearPoints.java**：暴力解法
3. **FastCollinearPoints.java**：快速解法（使用归并排序）

---

## 🎯 Part 1: Point 类

### 需要实现的方法

```java
public class Point implements Comparable<Point> {
    private final int x;     // x坐标
    private final int y;     // y坐标
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // 1. 计算到另一个点的斜率
    public double slopeTo(Point that) {
        // TODO: 实现
    }
    
    // 2. 按y坐标排序，y相同则按x排序
    public int compareTo(Point that) {
        // TODO: 实现
    }
    
    // 3. 返回按斜率排序的比较器
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {
            // TODO: 实现
        }
    }
    
    // 4. 字符串表示
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
```

### 关键实现：slopeTo() 方法

**斜率计算公式**：
```
slope = (y1 - y0) / (x1 - x0)
```

**特殊情况处理**：

| 情况 | 返回值 | 说明 |
|------|--------|------|
| 相同点 | `Double.NEGATIVE_INFINITY` | p == q |
| 垂直线 | `Double.POSITIVE_INFINITY` | x坐标相同 |
| 水平线 | `+0.0` | y坐标相同（注意正0） |
| 普通情况 | `(double)(y1-y0)/(x1-x0)` | 正常计算 |

```java
public double slopeTo(Point that) {
    // 相同点
    if (this.x == that.x && this.y == that.y) {
        return Double.NEGATIVE_INFINITY;
    }
    
    // 垂直线
    if (this.x == that.x) {
        return Double.POSITIVE_INFINITY;
    }
    
    // 水平线（重要：返回+0.0而不是-0.0）
    if (this.y == that.y) {
        return +0.0;
    }
    
    // 普通斜率
    return (double) (that.y - this.y) / (that.x - this.x);
}
```

⚠️ **常见错误**：
- 不要用整数除法：`(that.y - this.y) / (that.x - this.x)` ❌
- 水平线要返回`+0.0`：Java中`+0.0`和`-0.0`在`==`时相等，但`Double.compare()`会区分

### compareTo() 方法

```java
public int compareTo(Point that) {
    // 先比较y坐标
    if (this.y < that.y) return -1;
    if (this.y > that.y) return +1;
    
    // y相同，比较x坐标
    if (this.x < that.x) return -1;
    if (this.x > that.x) return +1;
    
    return 0;  // 完全相同
}
```

### slopeOrder() 比较器

```java
private class SlopeOrder implements Comparator<Point> {
    public int compare(Point q1, Point q2) {
        double slope1 = slopeTo(q1);
        double slope2 = slopeTo(q2);
        return Double.compare(slope1, slope2);
    }
}
```

---

## 🐌 Part 2: BruteCollinearPoints（暴力解法）

### 算法思路
枚举所有4个点的组合，检查是否共线。

### 共线判定
4个点 p, q, r, s 共线 ⟺ 三个斜率相等：
```
slope(p, q) == slope(p, r) == slope(p, s)
```

### 实现框架

```java
public class BruteCollinearPoints {
    private LineSegment[] segments;
    
    // 构造函数：找出所有共线线段
    public BruteCollinearPoints(Point[] points) {
        // 1. 检查输入合法性
        if (points == null) throw new IllegalArgumentException();
        
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        
        // 2. 检查重复点
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i+1]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }
        
        // 3. 暴力搜索4个点的组合
        List<LineSegment> segmentList = new ArrayList<>();
        int n = sortedPoints.length;
        
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = sortedPoints[i];
                        Point q = sortedPoints[j];
                        Point r = sortedPoints[k];
                        Point s = sortedPoints[l];
                        
                        // 检查共线
                        double slope1 = p.slopeTo(q);
                        double slope2 = p.slopeTo(r);
                        double slope3 = p.slopeTo(s);
                        
                        if (slope1 == slope2 && slope2 == slope3) {
                            // 找到共线的4个点
                            // 因为已排序，p是最小点，s是最大点
                            segmentList.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
        
        segments = segmentList.toArray(new LineSegment[0]);
    }
    
    // 返回线段数量
    public int numberOfSegments() {
        return segments.length;
    }
    
    // 返回所有线段
    public LineSegment[] segments() {
        return segments.clone();
    }
}
```

### 时间复杂度
- **O(n⁴)**：四重循环
- 适合小规模测试（n < 100）

---

## 🚀 Part 3: FastCollinearPoints（快速解法）

### 核心思想
利用**归并排序**优化斜率查找。

### 算法步骤

对每个点p作为"基点"：
1. 计算p到其他所有点的斜率
2. **按斜率排序**（使用归并排序！）
3. 检查相邻点：斜率相同的点都与p共线
4. 找出≥3个点（加上p就是≥4个点）的组

### 为什么用归并排序？
- 这是Week 6的作业，要求使用归并排序
- 归并排序是**稳定排序**，保持相同斜率点的相对顺序
- 时间复杂度：O(n log n)

### 实现框架

```java
public class FastCollinearPoints {
    private LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points) {
        // 1. 检查输入（同暴力法）
        if (points == null) throw new IllegalArgumentException();
        
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        
        // 检查重复
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i+1]) == 0) {
                throw new IllegalArgumentException("Duplicate points");
            }
        }
        
        // 2. 快速搜索
        List<LineSegment> segmentList = new ArrayList<>();
        int n = sortedPoints.length;
        
        for (int i = 0; i < n; i++) {
            Point p = sortedPoints[i];
            
            // 复制其他点
            Point[] otherPoints = sortedPoints.clone();
            
            // 按相对于p的斜率排序
            Arrays.sort(otherPoints, p.slopeOrder());
            
            // 查找相同斜率的点
            int j = 1;  // 跳过otherPoints[0]（可能是p自己）
            while (j < n) {
                // 找到一组斜率相同的点
                List<Point> collinearPoints = new ArrayList<>();
                double slope = p.slopeTo(otherPoints[j]);
                
                while (j < n && p.slopeTo(otherPoints[j]) == slope) {
                    collinearPoints.add(otherPoints[j]);
                    j++;
                }
                
                // 如果找到≥3个点（加上p就是≥4个）
                if (collinearPoints.size() >= 3) {
                    collinearPoints.add(p);
                    Collections.sort(collinearPoints);
                    
                    // 去重：只在p是最小点时添加
                    if (collinearPoints.get(0).equals(p)) {
                        Point min = collinearPoints.get(0);
                        Point max = collinearPoints.get(collinearPoints.size() - 1);
                        segmentList.add(new LineSegment(min, max));
                    }
                }
            }
        }
        
        segments = segmentList.toArray(new LineSegment[0]);
    }
    
    public int numberOfSegments() {
        return segments.length;
    }
    
    public LineSegment[] segments() {
        return segments.clone();
    }
}
```

### 时间复杂度
- **O(n² log n)**
  - 外层循环：n次
  - 每次排序：O(n log n)
  - 总计：n × n log n = n² log n

### 去重策略
**问题**：同一条线段会被多次发现
- 点p1, p2, p3, p4共线
- 以p1为基点找到一次
- 以p2为基点又找到一次

**解决**：只在当前基点是**最小点**时才添加线段
```java
if (collinearPoints.get(0).equals(p)) {
    // 只在p是最小点时添加
    segmentList.add(new LineSegment(min, max));
}
```

---

## ⚠️ 常见错误与注意事项

### 1. 浮点数比较
❌ **错误**：
```java
if (slope1 == slope2) // 可能因精度问题出错
```

✅ **正确**：
```java
if (Double.compare(slope1, slope2) == 0)
// 或者直接用 ==（在本题中可以，因为斜率计算是确定的）
```

### 2. 空指针检查
```java
// 检查数组本身
if (points == null) throw new IllegalArgumentException();

// 检查数组元素
for (Point p : points) {
    if (p == null) throw new IllegalArgumentException();
}
```

### 3. 重复点检查
```java
Arrays.sort(points);
for (int i = 0; i < points.length - 1; i++) {
    if (points[i].compareTo(points[i+1]) == 0) {
        throw new IllegalArgumentException("Duplicate points");
    }
}
```

### 4. 不要修改输入数组
```java
Point[] sortedPoints = points.clone();  // 复制一份
Arrays.sort(sortedPoints);               // 排序副本
```

### 5. 线段去重
只在基点是线段最小点时添加。

---

## 🧪 测试数据

### 测试文件格式
```
8          // 点的数量
10000  0   // x y 坐标
 0  10000
 3000  7000
 7000  3000
20000  21000
 3000  4000
14000  15000
 6000  7000
```

### 可视化测试
使用提供的可视化工具：
```bash
java-algs4 BruteCollinearPoints input8.txt
java-algs4 FastCollinearPoints input8.txt
```

### 性能要求
- **Brute Force**: 处理小数据（< 100点）
- **Fast**: 处理大数据（10000+ 点）

---

## 📊 评分标准

| 项目 | 分数 | 说明 |
|------|------|------|
| **Point.java** | 10分 | slopeTo, compareTo, slopeOrder |
| **BruteCollinearPoints** | 15分 | 正确性 + 性能 |
| **FastCollinearPoints** | 25分 | 正确性 + 性能 + 内存 |
| **设计** | 10分 | 代码风格、文档 |
| **总计** | **60分** | 及格36分 |

---

## 🎯 学习重点

### 本作业考察的核心技能
1. ✅ **几何算法**：斜率计算、共线判定
2. ✅ **排序应用**：归并排序的实际应用
3. ✅ **比较器**：自定义Comparator
4. ✅ **算法优化**：从O(n⁴)到O(n²log n)
5. ✅ **去重技巧**：线段去重策略
6. ✅ **边界处理**：特殊情况、空指针

### 与面试的关联
这道题是经典的：
- **LeetCode类似题**：
  - 149. 直线上最多的点数（Max Points on a Line）
  - 类似的几何问题
  
- **考察能力**：
  - 几何算法
  - 数据结构应用
  - 优化思维

---

## ✅ 完成检查清单

- [ ] Point类实现完成
  - [ ] slopeTo() 正确处理所有情况
  - [ ] compareTo() 正确排序
  - [ ] slopeOrder() 比较器正确
  
- [ ] BruteCollinearPoints实现
  - [ ] 找出所有4点共线
  - [ ] 处理边界情况
  - [ ] 通过小数据测试
  
- [ ] FastCollinearPoints实现
  - [ ] 使用排序优化
  - [ ] 正确去重
  - [ ] 通过大数据测试
  
- [ ] 错误处理
  - [ ] 空指针检查
  - [ ] 重复点检查
  - [ ] 非法输入处理
  
- [ ] 性能优化
  - [ ] 不修改输入数组
  - [ ] 避免重复计算
  - [ ] 内存使用合理

---

## 🔗 参考资源

- [作业说明](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)
- 《算法第4版》2.2节 归并排序
- Java Comparator文档

---

## 💡 实现建议

### Day 1-2: Point类 + 暴力解法
- 先实现Point类并测试
- 实现暴力解法，确保逻辑正确
- 通过小数据测试

### Day 3-4: 快速解法
- 理解排序优化的思路
- 实现FastCollinearPoints
- 解决去重问题
- 通过所有测试

---

**预计完成时间**：4-5小时（分4天，每天1小时）  
**难度等级**：⭐⭐⭐⭐ (Medium-Hard)  
**重要程度**：⭐⭐⭐⭐⭐ (计分作业，必做)

