## git操作

-  *本地所有修改的。没有的提交的，都返回到原来的状态* 

  ```shell
  git checkout .
  ```

-  *把所有没有提交的修改暂存到stash里面。可用git stash pop回复* 

  ```shell
  git stash
  ```

- 查看所在分支所有的变动，每一个变动会有一个编号hash

  ```shell
  git reflog
  ```

- 返回到某个时间节点

  ```shell
  git reset --hard hash
  ```

- 查看当前分支的详细信息，可看到当前分支与对应的远程追踪分支

  ```shell
  git remote -vv
  ```

- 查看当前远程仓库的信息

  ```shell
  git remote -v
  ```

- 新建分支

  ```shell
  git checkout -b 新分支名
  ```

- 推送新分支

  ```shell
  git push origin 本地分支名：远程分支名
  ```

- 切换分支

  ```shell
  git checkout <分支名>
  ```

- 合并分支

  ```shell
  git merge <待合并的分支名>
  ```

- 建立本地分支与远程分支的追踪关系

  ```shell
  1、git branch --set-upstream-to=<远程主机名>/<远程分支名> <本地分支名>
  
  2、git push -u <远程主机名> <本地分支名>
  ```

- 查看所有分支

  ```shell
  git branch -a
  ```

- 查看远程分支

  ```shell
  git branch -r
  ```

- 添加远程主机

  ```shell
  git remote add origin git@github.com:kong0827/SpringBoot-Demo.git
  ```

- 删除远程主机

  ```shell
  git remote rm origin
  ```

- 重命名远程主机

  ```shell
  git remote rename origin new-origin
  ```

- 将远程主机的更新到本地

  ```shell
  # 更新指定分支
  git fetch <远程主机名> <分支名>
  # 更新远程所有分支
  git fetch <远程主机名> / git fetch
  ```

  ```shell
  git pull <远程主机名> <分支名>
  # 等同于
  git fetch <远程主机名>
  git merge <远程主机名> <分支名>
  
  git pull --rebase
  # 等同于
  git fetch <远程主机名>
  git rebase  <远程主机名>/<分支名>
  ```

- 删除本地分支

  ```shell
  git branch -b 分支名
  # 强制删除
  git branch -D 分支名
  ```

- 删除远程分支

  ```shell
  git push <远程主机名> -d <分支名>
  git push origin --delete Chapater6
  
   注：也可以直接推送一个空分支到远程分支，其实就相当于删除远程分支：
  git push origin :demo  //推送本地的空分支(冒号前面的分支)到远程origin的demo(冒号后面的分支)(没有会自动创建)
  ```

- 修改最近一次的提交记录

  ```shell
  git commit --amend
  ```

- 将本地分支my-test关联到远程分支my-test上  
```shell
  git checkout -b my-test  //在当前分支下创建my-test的本地分支分支
  git push origin my-test  //将my-test分支推送到远程
  git branch --set-upstream-to=origin/my-test //将本地分支my-test关联到远程分支my-test上   
  git branch -a //查看远程分支
```

撤销本地分支与远程分支的映射关系
```shell
git branch --unset-upstream
```
- 在本地创建分支dev并切换到该分支
```shell
  git checkout -b dev(本地分支名称) origin/dev(远程分支名称)
```



### 撤销操作

- 文件被修改，但未执行`git add`操作

  ```powershell
  git checkout fileName
  git checkout .
  ```

- 同时对多个文件执行了`git add`操作，但本次只想提交其中的一部分文件

  ```powershell
  git add *
  git status
  # 取消暂存
  git reset HEAD <filename>
  ```

-  文件执行了`git add`操作，但想撤销对其的修改（index内回滚）

  ```powershell
  # 取消暂存
  git reset HEAD filename
  # 撤销修改
  git checkout filename
  ```

- 修改的文件已被`git commit`，但想再次修改不产生新的`commit`

  ```powershell
  # 修改最后一次提交
  git add sample.txt
  git commit --amend -m '说明'
  ```

- 已在本地进行了多次`git commit`操作，现在想撤销到其中某次Commit

  ```git
  git reset [--hard|soft|mixed|merge|keep] [commit|HEAD]
  ```

### 回滚操作

 已进行`git push`，即已推送到“远程仓库”中。我们将已被提交到“远程仓库”的代码还原操作叫做“回滚” 

- 撤销指定文件到执行版本

  ```powershell
  # 查看指定文件的历史版本
  git log <filename>
  # 回滚到指定的commitId
  git checkout <commitId> <filename>
  ```

- 删除最后一次远程提交

  *方式一：使用 revert*

  ```git
  git revert HEAD
  git push origin master
  12
  ```

  *方式二：使用 reset*

  ```git
  git reset --hard HEAD^
  git push origin master -f
  ```

  *二者区别：*

  - **revert** 是放弃指定提交的修改，但是会生成一次新的提交，需要填写提交注释，以前的历史记录都在；
  - **reset** 是指将HEAD指针指到指定提交，历史记录中不会出现放弃的提交记录。