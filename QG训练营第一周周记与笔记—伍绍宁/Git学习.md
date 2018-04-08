### Git简介
(学习连接) [廖雪峰](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013744142037508cf42e51debf49668810645e02887691000)
* Git是目前世界上最先进的分布式版本控制系
* 产生原因:适应现代文件多次改动且备份的需求发展(我的理解)
> 如果有一个软件，不但能自动帮我记录每次文件的改动，还可以让同事协作编辑，这样就不用自己管理一堆类似的文件了，也不需要把文件传来传去。如果想查看某次改动，只需要在软件里瞄一眼就可以
### 使用操作
>  第一步 —— 定位到对应目录  
```
$ mkdir learngit
$ cd learngit
$ pwd
/Users/michael/learngit
```
> 第二步 —— 变成可管理的仓库

```
$ git init

```
> 第三步 —— 添加文件

```
$ git add readme.txt
$ git commit -m "wrote a readme file"

```

### 集中式与分布式
* 先说集中式版本控制系统，版本库是集中存放在中央服务器的，而干活的时候，用的都是自己的电脑，所以要先从中央服务器取得最新的版本，然后开始干活，干完活了，再把自己的活推送给中央服务器。中央服务器就好比是一个图书馆，你要改一本书，必须先从图书馆借出来，然后回到家自己改，改完了，再放回图书馆
* 集中式的弊端促进了分布式的发展
### 登陆git
* $ git config --global user. name "Your Name"
* $ git config --global user.email"email@example .com"
### 把文件添加到版本库
* 所有的版本控制系统，其实只能跟踪文本文件的改动，比如TXT文件，网页，所有的程序代码等等，Git也不例外。版本控制系统可以告诉你每次的改动，比如在第5行加了一个单词“Linux”，在第8行删了一个单词“Windows”。而图片、视频这些二进制文件，虽然也能由版本控制系统管理，但没法跟踪文件的变化，只能把二进制文件每次改动串起来，也就是只知道图片从100KB改成了120KB，但到底改了啥，版本控制系统不知道，也没法知道。
* 初始化一个Git仓库，使用git init命令。
添加文件到Git仓库，分两步：
> 1. 第一步，使用命令git add <file>，注意，可反复多次使用，添加多个文件；
> 2. 第二步，使用命令git commit，完成。
> 3. 随时掌握工作区的状态，使用git status命令。  
> 4. 如果gitstatus告诉你有文件被修改过，用gitdiff可以查看修改内容

> 5.HEAD指向的版本就是当前版本，因此，Git允许我们在版本的历史之间穿梭，使用命令git reset --hard commit_id。穿梭前，用git log可以查看提交历史，以便确定要回退到哪个版本。要重返未来，用git reflog查看命令历史，以便确定要回到未来的哪个版本
### [工作区和暂存区](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013745374151782eb658c5a5ca454eaa451661275886c6000) (详解) 
**进阶** ：若进行两次修改，一次在add前，一次在提交前，则第二不会被提交。Git管理的是修改，当用gitadd命令后，在工作区的第一次修改被放入暂存区，准备提交，但是，在工作区的第二次修改并没有放入暂存区，所以，git commit只负责把暂存区的修改提交了，也就是第一次的修改被提交了，第二次的修改不会被提交。  
**解决办法** —— 再一次进行add  
**总结** ：缓存是存放修改后的文件，工作区则一次存放缓存区的文件  
### 撤销修改
* 命令git checkout -- readme.txt意思就是，把readme.txt文件在工作区的修改全部撤销，这里有两种情况：  
1. 一种是readme.txt自修改后还没有被放到暂存区，现在，撤销修改就回到和版本库一模一样的状态；  
2. 一种是readme.txt已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。
总之，就是让这个文件回到最近一次git commit或git add时的状态。
* 命令git reset HEAD file可以把暂存区的修改撤销掉（unstage），重新放回工作区：
> ***场景1：当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令git checkout -- file。***  
***场景2：当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令git reset HEAD file，就回到了场景1，第二步按场景1操作。***  
***场景3：已经提交了不合适的修改到版本库时，想要撤销本次提交，参考版本回退一节，不过前提是没有推送到远程库.***  
### 删除文件
* 命令git rm用于删除一个文件。如果一个文件已经被提交到版本库，那么你永远不用担心误删，但是要小心，你只能恢复文件到最新版本，你会丢失最近一次提交后你修改的内容。
### [远程仓库](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013752340242354807e192f02a44359908df8a5643103a000) (详解)

```
$ git remote add origin git@github.com:michaelliao/learngit.git
```
* 把上面的michaelliao替换成自己的GitHub账户名，否则，在本地关联的就是别人的远程库。

```
$ git push -u origin master
```
> * 本地库的内容推送到远程，用git push命令，实际上是把当前分支master推送到远程。
> * 由于远程库是空的，第一次推送master分支时，加上了-u参数，Git不但会把本地的master分支内容推送的远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。

```
$ git push origin master
```
* 使用后可直接上传
> 分布式版本系统的最大好处之一是在本地工作完全不需要考虑远程库的存在，也就是有没有联网都可以正常工作，而SVN在没有联网的时候是拒绝干活的！当有网络的时候，再把本地提交推送一下就能完成了同步.
### 从远程库克隆
1. 首先在giuhub克隆
2. 在git输入命令
```
$ git clone git@github.com:gdrcn/gitskills.git
```
> 总结——要克隆一个仓库，首先必须知道仓库的地址，然后使用git clone命令克隆。   
Git支持多种协议，包括https，但通过ssh支持的原生git协议速度最快。
### 创建与合并分支


>Git鼓励大量使用分支：

>查看分支：git branch

>创建分支：git branch <name>

>切换分支：git checkout <name>

>创建+切换分支：git checkout -b <name>

>合并某分支到当前分支：git merge <name>

>删除分支：git branch -d <name>

### 解决冲突
> 当Git无法自动合并分支时，就必须首先解决冲突。解决冲突后，再提交，合并完成。用git log --graph命令可以看到分支合并图。
### [分支管理](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/0013758410364457b9e3d821f4244beb0fd69c61a185ae0000)
> Git分支十分强大，在团队开发中应该充分应用。
合并分支时，加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并。
### bug分支
> 修复bug时，我们会通过创建新的bug分支进行修复，然后合并，最后删除；
当手头工作没有完成时，先把工作现场git stash一下，然后去修复bug，修复后，再git stash pop，回到工作现场
### 分支
> master分支是主分支，因此要时刻与远程同步；  

>dev分支是开发分支，团队所有成员都需要在上面工作，所以也需要与远程同步；  

>bug分支只用于在本地修复bug，就没必要推到远程了，除非老板要看看你每周到底修复了几个bug；  

>feature分支是否推到远程，取决于你是否和你的小伙伴合作在上面开发。
### 多人协作
1. 首先，可以试图用git push origin branch-name推送自己的修改；

2. 如果推送失败，则因为远程分支比你的本地更新，需要先用git pull试图合并；

3. 如果合并有冲突，则解决冲突，并在本地提交；

4. 没有冲突或者解决掉冲突后，再用git push origin branch-name推送就能成功！

5. 如果git pull提示“no tracking information”，则说明本地分支和远程分支的链接关系没有创建，用命令git branch --set-upstream branch-name origin/branch-name。
* 总结:  
> 查看远程库信息，使用git remote -v；

> 本地新建的分支如果不推送到远程，对其他人就是不可见的；

> 从本地推送分支，使用git push origin branch-name，如果推送失败，先用git pull抓取远程的新提交；

> 在本地创建和远程分支对应的分支，使用git checkout -b branch-name origin/branch-name，本地和远程分支的名称最好一致；

> 建立本地分支和远程分支的关联，使用git branch --set-upstream branch-name origin/branch-name；

> 从远程抓取分支，使用git pull，如果有冲突，要先处理冲突

### 标签
> 命令git tag <name>用于新建一个标签，默认为HEAD，也可以指定一个commit id；

> git tag -a <tagname> -m "blablabla..."可以指定标签信息；

> git tag -s <tagname> -m "blablabla..."可以用PGP签名标签；

> 命令git tag可以查看所有标签。
### 操作标签
> 命令git push origin <tagname>可以推送一个本地标签；

> 命令git push origin --tags可以推送全部未推送过的本地标签；

> 命令git tag -d <tagname>可以删除一个本地标签；

> 命令git push origin :refs/tags/<tagname>可以删除一个远程标签。
### Github
> 在GitHub上，可以任意Fork开源仓库；
自己拥有Fork后的仓库的读写权限；
可以推送pull request给官方仓库来贡献代码。
# 基础学习告一段落