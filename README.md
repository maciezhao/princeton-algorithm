# Princeton算法课程项目

这是Princeton大学算法课程(Algorithms, Part I)的作业项目，采用规范化的目录结构便于管理多个作业。

## 📁 项目结构

```
princeton-algorithm/
├── README.md                           # 项目说明
├── pom.xml                            # Maven配置  
├── lib/                               # 外部依赖库
│   └── algs4.jar                      # Princeton算法库
├── assignments/                       # 所有作业的归档
│   ├── week1-hello/
│   │   ├── src/                      # 源码
│   │   │   ├── HelloWorld.java
│   │   │   ├── HelloGoodbye.java
│   │   │   └── RandomWord.java
│   │   └── data/                     # 测试数据
│   ├── week2-percolation/
│   │   ├── src/
│   │   │   ├── Percolation.java
│   │   │   ├── PercolationStats.java
│   │   │   └── [其他文件...]
│   │   └── data/                     # 测试数据和图片
│   └── [未来的作业...]
├── notes/                             # 学习笔记
│   ├── week1-notes.md
│   └── week2-notes.md
├── scripts/                           # 实用脚本
│   ├── switch-assignment.ps1          # 切换作业脚本
│   └── compile-run.ps1               # 编译运行脚本
└── target/                           # Maven编译输出
```

## 🚀 使用方法

### 1. 直接在作业目录中工作

推荐直接在具体作业目录中编辑和运行代码：

```powershell
# 进入具体作业目录
cd assignments/week2-percolation/src

# 编译（需要设置classpath）
javac -cp "../../../lib/algs4.jar" *.java

# 运行
java -cp ".;../../../lib/algs4.jar" Percolation < ../data/input20.txt
```

### 3. 手动编译（如需要）

```bash
# 设置classpath并编译
javac -cp "lib/algs4.jar" -d target/classes assignments/week2-percolation/src/*.java

# 运行
java -cp "lib/algs4.jar;target/classes" Percolation
```

## 🛠️ IDEA配置

1. **导入项目**：选择`pom.xml`作为Maven项目导入
2. **标记源码目录**：将具体的`assignments/*/src`目录标记为Sources Root
3. **添加外部库**：将`lib/algs4.jar`添加到项目Dependencies

## 📝 作业说明

### Week 1: Hello World

- **HelloWorld.java**: 基础Hello World程序
- **HelloGoodbye.java**: 命令行参数处理
- **RandomWord.java**: 随机单词选择，使用Knuth洗牌算法

### Week 2: Percolation

- **Percolation.java**: 渗透模型实现，使用Union-Find数据结构
- **PercolationStats.java**: 蒙特卡洛模拟统计分析
- **可视化工具**: 渗透过程的图形化展示

## 📚 学习笔记

详细的学习笔记保存在`notes/`目录下：

- [Week 1 笔记](notes/week1-notes.md): 课程介绍与基础编程
- [Week 2 笔记](notes/week2-notes.md): Union-Find与渗透问题

## 🔧 开发工具

- **Java**: JDK 8+
- **IDE**: IntelliJ IDEA (推荐)
- **构建工具**: Maven
- **依赖库**: Princeton algs4.jar

## 📋 注意事项

1. **无包名要求**: 作业文件不使用package声明，符合课程要求
2. **数据文件**: 测试数据保存在各作业的`data/`目录下
3. **版本控制**: 建议使用Git跟踪代码变更
4. **提交格式**: 按照课程要求的zip格式提交

---

*Princeton University - Algorithms, Part I*  
*Instructors: Robert Sedgewick and Kevin Wayne* 