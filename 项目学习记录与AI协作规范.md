# 项目学习记录与AI协作规范

> **创建日期**: 2025年12月21日  
> **项目**: Princeton算法课程 - 60天求职准备计划  
> **学习者**: 4年Java开发经验  
> **目标**: 完成算法学习 + 准备技术面试

---

## 🎯 项目概况

### 基本信息
- **课程**: Algorithms Part I (Coursera - Princeton University)
- **学习模式**: 每天1小时，工作日晚上学习
- **当前进度**: Week 6 Collinear Points作业（Day 1/4完成）
- **总体进度**: 约30% (5/18周 + 额外作业)

### 学习资源位置
```
princeton-algorithm/
├── assignments/          # 作业代码
│   ├── week6-merge-sort/src/
│   │   ├── Point.java                    ✅ 完成
│   │   └── BruteCollinearPoints.java    🔧 95%
├── notes/               # 学习笔记
│   ├── 学习进度追踪.md                   📊 每日打卡
│   ├── week6-collinear-points-作业指南.md 📖 作业详解
│   └── week7-notes.md                    📝 快速排序
└── 学习计划-求职准备.md                   🎯 总体规划
```

---

## 🤝 AI协作规范（重要！）

### ✅ AI应该做的事

1. **代码审查**：
   - 指出代码中的问题（bug、逻辑错误、性能问题）
   - 解释为什么有问题
   - 给出修改建议（但不直接修改）

2. **概念讲解**：
   - 深入解释技术概念
   - 提供多角度的理解方式（代码示例、图解、类比）
   - 回答追问，直到完全理解

3. **学习记录**：
   - 更新学习进度
   - 记录关键突破和理解
   - 记录遇到的问题和解决方案

4. **资源提供**：
   - 推荐相关LeetCode题目
   - 提供文档链接
   - 整理知识点

### ❌ AI不应该做的事

1. **不要随便改动代码**：
   - 除非明确要求"切换到agent模式帮我修复"
   - 保持学习者的代码控制权

2. **不要主动补全代码**：
   - 让学习者自己实现
   - 只在学习者卡住时提供提示

3. **不要过度保护**：
   - 允许学习者犯错
   - 犯错是学习的一部分

4. **不要替代思考**：
   - 给方向而不是答案
   - 引导而不是替代

### 🎯 理想的AI交互模式

**学习者**：写代码 → 遇到问题 → 问AI
**AI**: 检查代码 → 指出问题 → 解释原理 → 给建议
**学习者**：理解问题 → 自己修复 → 继续学习
**AI**: 确认修复 → 记录进度 → 鼓励继续

---

## 📚 Day 1 学习记录（2025.12.21 周日）

### 完成内容
- ✅ Point.java 完美实现
- ✅ BruteCollinearPoints.java 95%完成

### 关键突破

#### 1. 理解Point.this的本质

**错误理解**：以为Point.this是固定的原点(0, 0)

**正确理解**：
- Point.this是**动态的**，指向调用`slopeOrder()`的那个Point对象
- 不同对象调用，Point.this就指向不同对象
- 这是Java内部类持有外部类引用的机制

**实例**：
```java
Point p1 = new Point(5, 10);
Comparator<Point> comp1 = p1.slopeOrder();
// 在comp1中，Point.this = p1 (5, 10)

Point p2 = new Point(20, 30);
Comparator<Point> comp2 = p2.slopeOrder();
// 在comp2中，Point.this = p2 (20, 30)
```

**为什么不能用固定原点**：
- FastCollinearPoints算法需要以**每个点**作为基准
- 每次循环的基准点都不同
- 固定原点会导致算法完全错误

#### 2. 内部类如何访问外部对象

Java在编译内部类时，会隐式地：
```java
// 我们写的代码
private class SlopeOrder implements Comparator<Point> {
    public int compare(Point p, Point q) {
        return Double.compare(Point.this.slopeTo(p), Point.this.slopeTo(q));
    }
}

// Java实际生成的代码（简化版）
class SlopeOrder implements Comparator<Point> {
    private final Point outer;  // 隐藏字段
    
    SlopeOrder(Point outer) {   // 隐藏构造函数
        this.outer = outer;
    }
    
    public int compare(Point p, Point q) {
        return Double.compare(this.outer.slopeTo(p), this.outer.slopeTo(q));
    }
}
```

#### 3. Cursor快捷键

**自动引入import**：
- Windows/Linux: `Ctrl + .` 或 `Ctrl + Space`
- Mac: `Cmd + .` 或 `Cmd + Space`
- 或点击灯泡图标选择Import

**整理所有import**：
- Windows/Linux: `Ctrl + Shift + O`
- Mac: `Cmd + Shift + O`

### 待修复问题

#### BruteCollinearPoints.java 的3个小问题

1. **第29行**：应该用`sortedPoints`而不是`points`
   - 原因：需要确保找到的线段端点是排序好的
   
2. **数据结构**：字段用List，但应该用数组
   - 当前：`private final List<LineSegment> segments = new ArrayList<>();`
   - 建议：在构造函数中用List收集，最后转成数组

3. **循环逻辑**：用sortedPoints后，索引递增保证了点的顺序

### 学习策略

1. **保持主动性**：自己写代码，AI只检查
2. **深入追问**：不理解的概念要问到底
3. **记录突破**：每次理解关键概念都要记录
4. **控制节奏**：每天1小时，不贪多

---

## 🎓 技术要点总结

### Java核心概念

#### 内部类（Inner Class）
- 非静态内部类持有外部类的引用
- 通过`OuterClass.this`访问外部实例
- 静态内部类（`static class`）不持有外部引用

#### Comparator vs Comparable
- `Comparable`：定义对象的"自然顺序"（如Point按y坐标排序）
- `Comparator`：定义"自定义排序"（如按斜率排序）

#### 防御性编程
```java
// 1. 检查null
if (points == null) throw new IllegalArgumentException();

// 2. 不修改输入
Point[] sortedPoints = points.clone();

// 3. 返回副本
return segments.clone();
```

### 算法要点

#### 共线点问题
- **暴力法**: O(n⁴) - 枚举所有4点组合
- **快速法**: O(n² log n) - 排序优化
- **关键**: 线段端点必须是最小和最大点

#### 斜率计算
```java
// 4种特殊情况
相同点 → Double.NEGATIVE_INFINITY
垂直线 → Double.POSITIVE_INFINITY  
水平线 → +0.0 (注意正零)
普通斜率 → (y1-y0)/(x1-x0)
```

---

## 📊 学习数据

### 时间统计
- Day 1: 120分钟（2小时）
- 预计总时长: 60小时（60天 × 1小时）
- 实际进度: 超前（1天完成1.5天任务）

### 完成度
- Week 6作业: 25% (Day 1/4)
- 总体进度: 30%
- Point.java: 100% ✅
- BruteCollinearPoints: 95% 🔧

---

## 🚀 下一步计划

### Day 2计划（明天）
1. 修复BruteCollinearPoints的3个问题（20分钟）
2. 测试代码功能（20分钟）
3. 开始FastCollinearPoints框架（20分钟）

### 本周目标
- 完成Week 6 Collinear Points作业
- 开始Week 7快速排序学习

---

## 💡 经验教训

### 学习方法
1. ✅ **主动学习**: 自己写代码，不依赖AI补全
2. ✅ **深入理解**: 追问到完全明白为止
3. ✅ **及时记录**: 学习进度和突破点要记录
4. ✅ **适度求助**: 卡住超过15分钟就问AI

### AI使用技巧
1. ✅ 明确告诉AI"只检查不修改"
2. ✅ 追问概念本质，不满足于表面理解
3. ✅ 要求AI记录学习进度
4. ✅ 把AI当作代码审查员和导师，不是代写工具

### 代码习惯
1. ✅ 先写核心逻辑，再处理边界情况
2. ✅ 变量命名清晰（sortedPoints vs points）
3. ✅ 理解代码背后的原理
4. ✅ 不盲目接受别人的代码

---

## 📝 未来参考

### 这份文档的用途
1. **作为prompt**: 告诉新的AI session你的学习方式
2. **复习指南**: 回顾关键概念和突破点
3. **经验总结**: 记录有效的学习方法
4. **进度追踪**: 看到自己的成长轨迹

### 如何使用这份文档

**当你在新的AI session中开始学习时，请说：**

```
请阅读项目根目录的"项目学习记录与AI协作规范.md"文件，
了解我的学习方式和AI协作规范，然后：
1. 遵守其中的AI协作规范（特别是不要随便改代码）
2. 查看最新的学习进度
3. 帮我继续学习
```

**AI应该做的第一件事：**
1. 读取`项目学习记录与AI协作规范.md`
2. 读取`notes/学习进度追踪.md`查看最新进度
3. 了解当前任务和待解决问题
4. 按照协作规范提供帮助

**重要提醒**：
- ✅ 这份文档会持续更新
- ✅ 每次完成重要milestone都会更新
- ✅ 记录了所有重要的学习突破和经验
- ✅ 是跨session学习的关键桥梁

---

## 🌟 激励语录

> "每天1小时，60天后你会感谢现在的自己！"

> "理解比记忆更重要，思考比完成更有价值。"

> "保持主动性，AI是助手不是替代。"

---

**最后更新**: 2025年12月23日（Day 3完成）  
**下次更新**: 完成Day 4后更新

---

## 🔄 最新进度更新（2025.12.23）

### Day 2 (12月22日)
- ⏸️ 未学习（工作日忙）

### Day 3 (12月23日) ✅
- ✅ FastCollinearPoints.java完成（93行，95%完成度）
- ✅ 核心算法正确
- ⚠️ 有3个小bug待修复
- 学习时长：1.5小时

### 当前状态
- Week 6作业：75%完成（Point✅, BruteCollinear✅, FastCollinear⚠️）
- 总进度：36%
- 累计学习：3.5小时

