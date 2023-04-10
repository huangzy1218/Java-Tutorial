# Shell教程

## Shell概述

Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。

Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。

Ken Thompson 的 sh 是第一种 Unix Shell，Windows Explorer 是一个典型的图形界面 Shell。

### Shell脚本

Shell 脚本（shell script），是一种为 shell 编写的脚本程序。业界所说的 shell 通常都是指 shell 脚本。

### Shell环境

Shell 编程跟 JavaScript、php 编程一样，只要有一个能编写代码的文本编辑器和一个能解释执行的脚本解释器就可以了。

Linux 的 Shell 种类众多，常见的有：

- Bourne Shell（/usr/bin/sh或/bin/sh）
- Bourne Again Shell（/bin/bash）
- C Shell（/usr/bin/csh）
- K Shell（/usr/bin/ksh）
- Shell for Root（/sbin/sh）

本教程关注的是 Bash，也就是 Bourne Again Shell，由于易用和免费，Bash 在日常工作中被广泛使用。同时，Bash 也是大多数Linux 系统默认的 Shell。

### 第一个Shell脚本

打开文本编辑器，新建一个文件 test.sh，输入：

```sh
#!/bin/bash
echo "Hello World!"
```

`#!` 是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行。

运行Shell脚本两种方法：

1. 作为可执行程序

   ```sh
   [s2021013123@centos8 tmp]$ chmod +x ./test.sh
   [s2021013123@centos8 tmp]$ ./test.sh
   Hello World!
   ```

   > 直接写 test.sh，linux 系统会去 PATH 里寻找有没有叫 test.sh 的，而只有 /bin, /sbin, /usr/bin，/usr/sbin 等在 PATH

2. 作为解释器参数

   直接运行解释器，其参数就是 shell 脚本的文件名，如：

   ```sh
   [s2021013123@centos8 tmp]$ bash test.sh
   Hello World!
   ```



## Shell变量

### 声明变量

- `-a`：数组
- `-f`：函数
- `-i`：整数
- `-r`：只读
- `-x`：可被子进程访问，全局变量

```shell
[root@localhost linux-program]# declare -i age=20
[root@localhost linux-program]# declare -rx OS=LIUNX
```

### 使用变量

使用一个定义过的变量，只要在变量名前面加美元符号即可

```sh
name="HuangZY"
echo $name
echo ${name}
```

使用`readonly`命令可以将变量定义为只读变量，只读变量的值不能被改变。

```sh
readonly name="HuangZY"
echo $name
echo ${name}
name="Tim"
```

```
[s2021013123@centos8 tmp]$ bash test.sh
HuangZY
HuangZY
test.sh:行5: name: 只读变量
```

### 删除变量

使用 `unset` 命令可以删除变量。语法：

```sh
uset variable_name
```

变量被删除后不能再次使用。`unset`命令不能删除只读变量。

### 输入变量

```sh
read [options] varible-list
```

`-a`：输入数组

`-e`：把一整行读入第一个变量，其余为`null`

`-n`：在`echo`输出命令字符串后，光标仍停留在同一行

```sh
[root@localhost linux-program]# read -a array
1 2 3 4 5
[root@localhost linux-program]# echo $array
1
[root@localhost linux-program]# echo ${array[*]}
1 2 3 4 5

[root@localhost linux-program]# read -e a b c
1234 23 2 432 1 3
[root@localhost linux-program]# echo $a
1234
[root@localhost linux-program]# echo $b
23
[root@localhost linux-program]# read -n 1 b
h[root@localhost linux-program]# echo $b
h
```

### 变量类型

运行shell时，会同时存在三种变量：

- 局部变量： 局部变量在脚本或命令中定义，仅在当前shell实例中有效，其他shell启动的程序不能访问局部变量。
- 环境变量： 所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。
- shell变量： shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行

### 字符串

字符串是shell编程中最常用最有用的数据类型，字符串可以用单引号，也可以用双引号，也可以不用引号。

单引号字符串的限制：

- 单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
- 单引号字串中不能出现单独一个的单引号（对单引号使用转义符后也不行），但可成对出现，作为字符串拼接使用。

双引号的优点：

- 双引号里可以有变量
- 双引号里可以出现转义字符

```sh
#!/bin/bash
name="HuangZY"
str="Hello, my name is $name!"
echo -e $str
```

> #### `echo`命令
>
> ```sh
> echo [-ne] str
> ```
>
> - `-n`：取消自动换行
> - `-e`：转义（默认）
> - `-E`：取消转义

```sh
# 输出长度
echo ${#str}
# 截取子串（第2个开始截取4个）
echo ${str:1:4}
# 查找字符H或e的位置（从1开始）
echo `expr index "$str" He`
```

```sh
26
ello
1
```

> 字符串下标从0开始
>
> 脚本中 是反引号，而不是单引号 `'`

### 命令替换

如```command` `` 时，Shell将其替换为这个命令的**输出结果**。

```sh
[root@localhost linux-program]# pwd
/bin/linux-program
[root@localhost linux-program]# cmd1=pwd
[root@localhost linux-program]# echo "The value of command is $cmd1."
The value of command is pwd.
[root@localhost linux-program]# cmd=$(pwd)
[root@localhost linux-program]# echo "The value of command is $cmd."
The value of command is /bin/linux-program.
[root@localhost linux-program]# cmd=`pwd`
[root@localhost linux-program]# echo "The value of command is $cmd."
The value of command is /bin/linux-program.
```

### 数组

bash支持一维数组（不支持多维数组），并且没有限定数组的大小。

#### 定义数组

```sh
array_name=(value0, value1)

array_name[0]=value0
array_name[1]=value1
array_name[n]=valuen
```

#### 读取数组

```sh
valuen=${array_name[n]}
```

使用 `@` 符号可以获取数组中的所有元素，例如：

```sh
echo ${array_name[@]}
```

```sh
#!/bin/bash
array=(1 2 3 4 5)
# 访问单个元素
echo "array[0]=${array[0]}"
# 访问全部元素
echo "array=(${array[@]})"
# 获取元素个数
echo "len(array)=${#array[@]}"
echo "len(array)=${#array[*]}"
echo "len(array[0])=${#array[0]}"
```

```sh
[s2021013123@centos8 tmp]$ bash test.sh
array[0]=1
array=(1 2 3 4 5)
len(array)=5
len(array)=5
len(array[0])=1
```

#### 提取子串

```sh
[root@localhost linux-program]# echo ${array[@]:1}
two three four
[root@localhost linux-program]# echo ${array[@]:1:2}
two three
```

#### 删除子串

```sh
# 删除从左最短的匹配
[root@localhost linux-program]# echo ${array[@]#t*e}
one, two e four
# 删除从左最长的匹配
[root@localhost linux-program]# echo ${array[@]##t*e}
one, two four
# 删除从右最短的匹配
[root@localhost linux-program]# echo ${array[@]%o}
one, tw three four
# 删除从右最长的匹配
[root@localhost linux-program]# echo ${array[@]%%o}
one, tw three four
```

#### 子串替换

```sh
# 替换第一个
[root@localhost linux-program]# echo ${array[@]/o/m}
mne, twm three fmur
# 替换全部
[root@localhost linux-program]# echo ${array[@]//o/m}
mne, twm three fmur
[root@localhost linux-program]# echo ${array[@]//o}
ne, tw three fur
# 替换前端第一个
[root@localhost linux-program]# echo ${array[@]/#o/k}
kne, two three four
# 替换后端第一个
[root@localhost linux-program]# echo ${array[@]/%o/k}
one, twk three four
```

#### 关联数组

Bash 支持关联数组，可以使用任意的字符串、或者整数作为下标来访问数组元素。

```sh
declare -A array_name
```

`-A`用于声明一个关联数组。关联数组的键是唯一的。

```sh
#!/bin/bash
declare -A site=(["goolge"]="www.google.com" ["baidu"]="www.baidu.com")
site["runoob"]="www.runoob.com"

echo ${site["rubboob"]}
echo ${site[@]}
```

### 注释

#### 单行注释

```sh
# 这是一个注释
```

#### 多行注释

```sh
:<<EOF
这是一个注释
这是一个注释
这是一个注释
EOF
```

## Shell传递参数

脚本内获取参数的格式为：`$n`。`n`代表一个数字，1 为执行脚本的第一个参数，2 为执行脚本的第二个参数，以此类推。

```sh
#!/bin/bash
echo "执行文件名：$0"
echo "第1个参数： $1"
echo "第2个参数： $2"
```

```sh
[s2021013123@centos8 tmp]$ ./test.sh 1 2 3
执行文件名：./test.sh
第1个参数： 1
第2个参数： 2
```

| 参数处理 | 说明                                                         |
| :------- | :----------------------------------------------------------- |
| `$#`     | 传递到脚本的参数个数                                         |
| `$*`     | 以一个单字符串显示所有向脚本传递的参数。                     |
| `$$`     | 脚本运行的当前进程ID号                                       |
| `$!`     | 后台运行的最后一个进程的ID号                                 |
| `$@`     | 与$*相同，但是使用时加引号，并在引号中返回每个参数。 如"$@"用「"」括起来的情况、以"$1" "$2" … "$n" 的形式输出所有参数。 |
| `$-`     | 显示Shell使用的当前选项，与`set`命令功能相同。               |
| `$?`     | 显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。 |

```sh
#!/bin/bash
echo "传递参数个数：$#"
echo "所有参数：    $*"
echo "进程ID号：    $$"
echo "后台运行最后一个进程号：$!"
echo "所有参数：    $@"
echo "显示选项：    $-"
echo "显示退出状态：$?"
```

```sh
[s2021013123@centos8 tmp]$ ./test.sh 1 2 3 4 5 6 7
传递参数个数：7
所有参数：    1 2 3 4 5 6 7
进程ID号：    562703
后台运行最后一个进程号：
所有参数：    1 2 3 4 5 6 7
显示选项：    hB
显示退出状态：0
```

> `$*`与 `$@` 区别：
>
> - 相同点：都是引用所有参数。
> - 不同点：只有在双引号中体现出来。假设在脚本运行时写了三个参数 1、2、3，则 " * " 等价于 "1 2 3"（传递了一个参数），而 "@" 等价于 "1" "2" "3"（传递了三个参数）。

## Shell运算符

原生bash不支持简单的数学运算，但是可以通过其他命令来实现，例如`awk` 和 `expr`，`expr` 最常用。

### 算术运算符

expr 是一款表达式计算工具，使用它能完成表达式的求值操作。

```sh
#!/bin/bash
val=`expr 2 + 2`
echo "两数之和：$val"
```

- 表达式和运算符之间要有空格，例如 2+2 是不对的，必须写成 2 + 2，这与我们熟悉的大多数编程语言不同。  
- 完整的表达式要被 ` `` ` 包含，注意这个字符不是常用的单引号
- 乘号`*`前边必须加反斜杠`\`才能实现乘法运算；

```sh
#!/bin/bash

val=`expr 2 \* 2`
echo "两数之积：$val"
```

### 关系运算符

关系运算符只支持数字，不支持字符串，除非字符串的值是数字。

```sh
if [ $a -eq $b ]
then
   echo "$a -eq $b : a 等于 b"
else
   echo "$a -eq $b: a 不等于 b"
fi
if [ $a -ne $b ]
then
   echo "$a -ne $b: a 不等于 b"
else
   echo "$a -ne $b : a 等于 b"
fi
if [ $a -gt $b ]
then
   echo "$a -gt $b: a 大于 b"
else
   echo "$a -gt $b: a 不大于 b"
fi
if [ $a -lt $b ]
then
   echo "$a -lt $b: a 小于 b"
else
   echo "$a -lt $b: a 不小于 b"
fi
if [ $a -ge $b ]
then
   echo "$a -ge $b: a 大于或等于 b"
else
   echo "$a -ge $b: a 小于 b"
fi
if [ $a -le $b ]
then
   echo "$a -le $b: a 小于或等于 b"
else
   echo "$a -le $b: a 大于 b"
fi
```

```sh
[s2021013123@centos8 tmp]$ bash test.sh
1 -eq 2: a 不等于 b
1 -ne 2: a 不等于 b
1 -gt 2: a 不大于 b
1 -lt 2: a 小于 b
1 -ge 2: a 小于 b
1 -le 2: a 小于或等于 b
```

### 布尔运算符

```sh
#! 非运算
[!false] 返回true
#-o 或运算
#-a 与运算
```

### 逻辑运算符

```sh
# 逻辑与
&& 
# 逻辑或
||
```

### 字符串运算符

```sh
# 判等
= 
# 判不等
!=
-z
判空
-n
判非空
$
判非空
```

### 文件测试运算符

```sh

#!/bin/bash

file=$1

if [ -r $file ]
then
        echo "文件可读"
fi
if [ -w $file ]
then
        echo "文件可写"
fi

if [ -x $file ]
then
        echo "文件可执行"
fi

if [ -f $file ]
then
        echo "文件为普通文件"
fi

if [ -d $file ]
then
        echo "文件为目录"
fi

if [ -s $file ]
then
        echo "文件为空"
fi

if [ -e $file ]
then
        echo "文件存在"
fi
```

```sh
[s2021013123@centos8 tmp]$ bash test.sh test1.txt
文件可读
文件可写
文件为普通文件
文件存在
```

- `-S`: 判断某文件是否 socket。
- `-L`: 检测文件是否存在并且是一个符号链接。

> 表达式可使用`test`命令或`[]`来检测。

## `printf`命令

`printf` 命令模仿 C 程序库（library）里的 `printf()` 程序。

`printf` 由 POSIX 标准所定义，因此使用 `printf` 的脚本比使用 `echo` 移植性好。

```sh
str="Hello World"
echo $str
printf "%s\n" $str
```

```sh
printf "%-10s %-8s %-4s\n" 姓名 性别 体重kg  
printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234
printf "%-10s %-8s %-4.2f\n" 杨过 男 48.6543
printf "%-10s %-8s %-4.2f\n" 郭芙 女 47.9876
```

```sh
[s2021013123@centos8 tmp]$ bash test.sh
姓名     性别   体重kg
郭靖     男      66.12
杨过     男      48.65
郭芙     女      47.99
```

```sh
#!/bin/bash

# 警告字符
printf "警告\a"
# 回退
printf "回退\b"
# 换页
printf "换页\f"
#换行
printf "换行\n"
# 回车
printf "回车\r"
# 水平制表符
printf "水平\t制表\t符"
# 垂直制表符
printf "垂直\t制表\t符"
```

## `test`命令

Shell 还提供了与( `-a` )、或(`-o` )、非( `!` )三个逻辑操作符用于将测试条件连接起来，其优先级为： `!` 最高， `-a` 次之， `-o` 最低

```
[huangzy@localhost bin]$ if test -e ./notFile -o -e ./bash
> then echo "至少有一个文件存在"
> else
> echo "两个文件都不存在"
> fi
至少有一个文件存在
```

## 流程控制

### `if-else`语句

语法格式：

```sh
if condition1
then
    command1
elif condition2 
then 
    command2
else
    commandN
fi
```

```sh
a=10
b=20
if [ $a == $b ]
then
    echo "a == b"
elif [ $a -lt $b ]
then
    echo "a > b"
elif [ $a -lt $b ]
then
    echo "a < b"
else
    echo "没有符合条件"
fi
```

### `for`循环

语法格式：

```sh
for var in item1 item2 ... itemN
do
    command1
    command2
    ...
    commandN
done
```

### `while`语句

语法格式：

```sh
while condition
do
	command
done
```

```sh
# 无限循环
while :
do
	command
done
```

### `until`语句

`until` 循环执行一系列命令直至条件为 `true` 时停止。

```sh
until condition
do
	command
done
```

### `case-esac`语句

`case...esac` 为多选择语句，与其他语言中的 switch ... case 语句类似，是一种多分支选择结构。

```sh
echo '输入1到4之间的数字：'
echo '你输入的数字为：'
read num;
case $num in
    1)  echo '你选择了1'
    ;;
    2)  echo '你选择了2'
    ;;
    3)  echo '你选择了3'
    ;;
    4)  echo '你选择了4'
    ;;
    *)  echo '你没有输入1到4之间的数字';
    ;;
esac
```

## 函数

```sh
[ function ] funname [()]

{
    action;

    [return int;]

}
```

```sh
#bin/bash

demo_fun() {
    echo "Hello World";
}
echo "--function start--"
demo_fun;
echo "--function end--"
```

```sh
#! /bin/bash
sample_txt="global variable"
foo() {
    local sample_txt="local variable"
    echo "function foo is comming"
    echo $sampe_txt
}

echo "script starting"
foo
echo $sample_txt
exit 0
```

## 输入输出重定向

| 命令              | 说明                                               |
| :---------------- | :------------------------------------------------- |
| `command > file`  | 将输出重定向到 file。                              |
| `command < file`  | 将输入重定向到 file。                              |
| `command >> file` | 将输出以追加的方式重定向到 file。                  |
| `n > file`        | 将文件描述符为 n 的文件重定向到 file。             |
| `n >> file`       | 将文件描述符为 n 的文件以追加的方式重定向到 file。 |
| `n >& m`          | 将输出文件 m 和 n 合并。                           |
| `n <& m`          | 将输入文件 m 和 n 合并。                           |
| `<< tag`          | 将开始标记 tag 和结束标记 tag 之间的内容作为输入。 |

### 输出重定向

```sh
[root@localhost linux-program]# who > users
```

如果不希望文件内容被覆盖，可以使用 `>>` 追加到文件末尾

### 输入重定向

```sh
[root@localhost linux-program]# wc -l < users
4
```

一般情况下，每个 Unix/Linux 命令运行时都会打开三个文件：

- 标准输入文件(stdin)：stdin的文件描述符为0，Unix程序默认从stdin读取数据。
- 标准输出文件(stdout)：stdout 的文件描述符为1，Unix程序默认向stdout输出数据。
- 标准错误文件(stderr)：stderr的文件描述符为2，Unix程序会向stderr流中写入错误信息。

默认情况下，command > file 将 stdout 重定向到 file，command < file 将stdin 重定向到 file。

如果希望 stderr 重定向到 file，可以这样写：

```sh
$ command 2>file
```

### Here Document

Here Document 是 Shell 中的一种特殊的重定向方式，用来将输入重定向到一个交互式 Shell 脚本或程序。

```sh
[root@localhost linux-program]# wc << delimiter
> Hello World Hello Java Hello C
> delimiter
 1  6 31
```

