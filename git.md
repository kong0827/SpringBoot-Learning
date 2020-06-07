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
  ```

- 修改最近一次的提交记录

  ```shell
  git commit --amend
  ```

- 将本地分支my-test关联到远程分支my-test上  
git branch --set-upstream-to=origin/my-test



