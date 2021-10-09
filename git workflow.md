## git workflow

### 目的

1、统一多人协作git提交规范

2、解决git使用困惑

3、高效开发，规范的git使用



### 分支命名

**master 分支**  *生产分支(目前我们Java使用的是pro分支*)  目前github是main分支

- master 为主分支，也是用于部署生产环境的分支，确保master分支稳定性

- master 分支一般由develop以及hotfix分支合并，任何时间都不能直接修改代码

  

**develop 分支** *开发分支(目前我们Java使用的是master分支)*

- develop 为开发分支，始终保持最新完成以及bug修复后的代码

- 一般开发的新功能时，feature分支都是基于develop分支下创建的

  

**feature 分支** （目前我们命名不规范）

- 正常开发新功能时，以develop为基础创建feature分支，合并进开发分支即可删除

- 功能分支，功能分支是由需求确立而成，每一个需求或功能就必须建立一个功能分支

- 分支命名: feature/ 开头的为特性分支， 命名规则: feature/时间/作者/功能名

  把大的需求细分为各个小模块和功能，进行开发。好处就是各个功能独立开发则不受影响，同时团队成员之间的协作隔离不容易产生冲突

  

**release分支**（目前我们公司不存在，或者可以看成test分支）

- release 为预上线分支，发布提测阶段，会release分支代码为基准提测

```
当有一组feature开发完成，首先会合并到develop分支，进入提测时，会创建release分支。
如果测试过程中若存在bug需要修复，则直接由开发者在release分支修复并提交。
当测试完成之后，合并release分支到master和develop分支，此时master为最新代码，用作上线。
复制代码
```



**hotfix 分支** （热修分支，目前我使用，其他人未知）

- 分支命名: hotfix/ 开头的为修复分支，它的命名规则与 feature 分支类似

- 线上出现紧急问题时，需要及时修复，以master分支为基线，创建hotfix分支，修复完成后，需要合并到master分支和develop分支

  

分支与生产分支分离的时间越长，合并冲突和部署挑战的风险就越高。**短期分支促进更清洁的合并和部署**

**以下文档所写分支名，请自动转为我们公司对应分支**



### 规范提交

1、提交时尽量按照模块合并自己的提交记录，不要出现同一模块多个提交记录

2、合并请求时，对应开发功能必须有对应的测试类且自测无误

3、git提交规范，待补充



开发期间要保持与master分支的同步

```
# 如果你使用rebase，将会得到更干净的提交记录，详细看后面rebase的介绍
# on branch feature-download-page
$ git fetch origin master
$ git merge origin/master
```







### 流程

1、每个人根据自己的任务从 `develop`  开发分支切出对应的 `feature`分支，进行开发

2、功能开发完成后，把最新的 `develop`分支合并进自己的  `feature`分支，解决冲突后，提交合并请求，由对应的负责人进行review和合并确认

3、删除feature分支





### 多版本协作开发

1、拆分出最新要上线的功能，从`master` 生产分支（目前我们生产分支是pro）切出新分支

2、开发完成后，提交合并请求到测试分支，测试验证无误后，拉取最新的线上分支，解决完冲突后再次合入线上分支

3、删除feature分支





### 疑问





### 学习文档

https://learngitbranching.js.org/?locale=zh_CN 

https://www.atlassian.com/git/tutorials/comparing-workflows
