[![github](https://img.shields.io/badge/Java-%E5%8D%8E%E5%AE%B9%E9%81%93-brightgreen)](https://github.com/qizong007)

# 环境

## 开发环境

- IDE：IntelliJ IDEA 2020.1.3
- 编程语言：Java 11.0.1
- 单元测试：JUnit-4.9

## 运行环境

（同上即可）

需要注意的是，该项目使用了Lombok插件，需在IDEA商店中安装插件才可使用



# 使用指南

## 单个题目

如果只是跑单个题目的话，可以像test文件夹下TestCase中的`battleForSingle()`方法

```java
@Test
public void battleForSingle() throws Exception {
    // 初始化，把图片转成内存中的byte[]
    ImgCompetition.init();
    Play.battle(PathUtil.TEAM_ID,PathUtil.TEAM_TOKEN,"bed7baa0-d7b8-4aaa-b488-eb3ca7be46dc",true);
}
```

我来解释一下这个方法：

- `ImgCompetition.init();`负责初始化
- ` Play.battle()`就是真正跑的那个了，具体参数使用如下：
  - teamid直接用我上面写好的PathUtil里的路径就行
  - teamtoken同上
  - 题目的uuid，复制过来就行了
  - stepFirst，一个布尔值，false为时间优先，true为步数优先

## 在题库里拿题写

如果跑多个就是这样：

```java
@Test
public void battle() throws Exception {
    // 初始化，把图片转成内存中的byte[]
    ImgCompetition.init();
    List<Challenge.Question> questions = Challenge.getList();
    for(Challenge.Question question : questions){
        Play.battle(PathUtil.TEAM_ID,PathUtil.TEAM_TOKEN,question.getUuid(),false);
    }
    Rank.getTeamDetail(PathUtil.TEAM_ID);
}
```



# 开发者日志

## 第五阶段

（2020.10.12）

- 添加比赛接口



## 第四阶段

（2020.10.6）

- 添加了判断有无解的逆序数，更快了，现在平均1s，服务器qps都被我淦出来了（听说这玩意儿还有人dos）
- 添加了部分比赛接口



## 第三阶段

（2020.9.29）

- BUG成功修复，现在成功率100%
- 顺带着速度还提升了hhh



（2020.9.28）

- 解BUG，于是有了新BUG（发现交换写的很有问题啊）



## 第二阶段

（2020.9.27）

- 空间换时间优化成功，时间减半



（2020.9.24）

- 已POST请求到成功参数，但有一定几率失败（待解决，与模拟游戏的算法有关）

- 下个目标打算优化时间，也就是图像处理

  - 空间换时间，把图片以hash值的形式保存在配置文件中

  - 下下策：试试多线程，把CPU吃满



（2020.9.23）

- 算法优化，时间复杂度降低，所需时间缩短至原来的20%左右

- 部分代码小重构



（2020.9.22）

- 完成了base64编码
- 完了图片识别和匹配
- 完成了图片数组化，以便模拟游戏
- 完成了步骤的整合



## 第一阶段
（2020.9.21）
- 完成了游戏模拟算法部分（"wsad"）
- 完成了JSON部分

- 下一步是完成base64的转换，和算法中的强制交换部分