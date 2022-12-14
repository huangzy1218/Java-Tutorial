

# hon3基础教程

## Python3简介

Python 是一个高层次的结合了**解释性**、**编译性**、**互动性**和**面向对象**的**脚本**语言。

Python 的设计具有很强的可读性，相比其他语言经常使用英文关键字，其他语言的一些标点符号，它具有比其他语言更有特色语法结构。

```shell
$python -V
Python 3.10.0
```

### 第一个Python程序

```shell
$python
Python 3.10.0 (tags/v3.10.0:b494f59, Oct  4 2021, 19:00:18) [MSC v.1929 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license" for more information.
>>> print("Hello, world")
Hello, world
```

### Python的发展历史

Python 是由 Guido van Rossum 在八十年代末和九十年代初，在荷兰国家数学和计算机科学研究所设计出来的。

Python 本身也是由诸多其他语言发展而来的,这包括 ABC、Modula-3、C、C++、Algol-68、SmallTalk、Unix shell 和其他的脚本语言等等。

像 Perl 语言一样，Python 源代码同样遵循 GPL(GNU General Public License)协议。

现在 Python 是由一个核心开发团队在维护，Guido van Rossum 仍然占据着至关重要的作用，指导其进展。

Python 2.0 于 2000 年 10 月 16 日发布，增加了实现完整的垃圾回收，并且支持 Unicode。

Python 3.0 于 2008 年 12 月 3 日发布，此版不完全兼容之前的 Python 源代码。不过，很多新特性后来也被移植到旧的Python 2.6/2.7版本。

Python 3.0 版本，常被称为 Python 3000，或简称 Py3k。相对于 Python 的早期版本，这是一个较大的升级。

Python 2.7 被确定为最后一个 Python 2.x 版本，它除了支持 Python 2.x 语法外，还支持部分 Python 3.1 语法。

### Python特点

- **易于学习：**Python有相对较少的关键字，结构简单，和一个明确定义的语法，学习起来更加简单。
- **易于阅读：**Python代码定义的更清晰。
- **易于维护：**Python的成功在于它的源代码是相当容易维护的。
- **一个广泛的标准库：**Python的最大的优势之一是丰富的库，**跨平台**，在UNIX，Windows和Macintosh兼容很好。
- **互动模式：**互动模式的支持，您可以从终端输入执行代码并获得结果的语言，互动的测试和调试代码片断。
- **可移植：**基于其开放源代码的特性，Python已经被移植（也就是使其工作）到许多平台。
- **可扩展：**如果你需要一段运行很快的关键代码，或者是想要编写一些不愿开放的算法，你可以使用C或C++完成那部分程序，然后从你的Python程序中调用。
- **数据库：**Python提供所有主要的商业数据库的接口。
- **GUI编程：**Python支持GUI可以创建和移植到许多系统调用。
- **可嵌入:** 你可以将Python嵌入到C/C++程序，让你的程序的用户获得"脚本化"的能力。

### Python应用

-  **云计算**：云计算最热的语言，典型的应用OpenStack
-  **WEB开发**：许多优秀的 WEB 框架，许多大型网站是Python开发、YouTube、Dropbox、Douban……典型的Web框架包括Django
-  **科学计算和人工智能**：典型的图书馆NumPy、SciPy、Matplotlib、Enided图书馆、熊猫
-  **系统操作和维护**：操作和维护人员的基本语言
-  **金融**：定量交易、金融分析，在金融工程领域，Python 不仅使用最多，而且其重要性逐年增加。
-  **图形 GUI**：PyQT，WXPython，TkInter

## Python3基础语法

### 编码

默认情况下，Python 3 源码文件以 **UTF-8** 编码，所有字符串都是 unicode 字符串。 当然你也可以为源码文件指定不同的编码：

```python
# -*- coding: cp-1252 -*-
```

### 标识符

- 第一个字符必须是字母表中字母或下划线 `_ `。
- 标识符的其他的部分由字母、数字和下划线组成。
- 标识符对大小写敏感。

在 Python 3 中，可以用中文作为变量名，允许非ASCII字符作为标识符。

### Python保留字

```python
>>> import keyword
>>> keyword.kwlist
['False', 'None', 'True', 'and', 'as', 'assert', 'async', 'await', 'break', 'class', 'continue', 'def', 'del', 'elif', 'else', 'except', 'finally', 'for', 'from', 'global', 'if', 'import', 'in', 'is', 'lambda', 'nonlocal', 'not', 'or', 'pass', 'raise', 'return', 'try', 'while', 'with', 'yield']
```

### 注释

Python中单行注释以 `# `开头，多行注释可以用多个 `#` 号，还有` '''` 和 `"""`：

```python
# 一个注释

'''
注释
'''

"""
注释
"""

print("Hello, world")
```

### 行与缩进

python最具特色的就是使用缩进来表示代码块，不需要使用大括号 `{}` 。

缩进的空格数是可变的，但是同一个代码块的语句必须包含相同的缩进空格数。实例如下：

```python
if True:
    print("Answer")
    print("True")
else:
    print("Answer")
    print("False")
```

### 多行语句

Python 通常是一行写完一条语句，但如果语句很长，我们可以使用反斜杠`\`来实现多行语句，例如：

```python
total = item_one + \
        item_two + \
        item_three
```

## Python3基本数据类型

Python 中的变量不需要声明。每个变量在使用前都必须赋值，变量赋值以后该变量才会被创建。

```python
counter = 100
miles = 1000.0
name = "hello"

print(counter)
print(miles)
print(name)
```

Python允许你同时为多个变量赋值。例如：

```python
a = b = c = 1
a, b, c = 1, 2.0, "3.0"
```

### 基本数据类型

Python3 中有六个标准的数据类型：

- `Number`（数字）
- `String`（字符串）
- `List`（列表）
- `Tuple`（元组）
- `Set`（集合）
- `Dictionary`（字典）

**不可变数据：**Number、String、Tuple；

**可变数据：**List、Dictionary、Set。

### 数字

#### 数字类型

Python3支持`int`、`float`、`bool`、`complex`。

在Python 3里，只有一种整数类型 `int`，表示为长整型，没有 python2 中的 `long`。

内置的`type()` 函数可以用来查询变量所指的对象类型。

```python
>>> a, b, c, d = 20, 5.5, True, 3 + 4j
>>> print(type(a), type(b), type(c), type(d))
<class 'int'> <class 'float'> <class 'bool'> <class 'complex'>
```

此外还可以用` isinstance` 来判断：

```python
>>> a = 111
>>> isinstance(a, int)
True
```

#### 删除对象

您可以通过使用`del`语句删除单个或多个对象

```python
del var
del var_a, var_b
```

#### 数值运算

```python
>>> 5 + 1
6
>>> 4.3 - 2
2.3
>>> 2 / 4
0.5
>>> 2 // 4
0
>>> 17 % 3
2
>>> 2 ** 5
32
```

注：数值的除法包含两个运算符：`/` 返回一个浮点数，`//` 返回一个整数。

Python 还支持复数，复数由实数部分和虚数部分构成，可以用 `a + bj`，或者 `complex(a, b)` 表示， 复数的实部 `a` 和虚部 `b` 都是浮点型。

#### 相关函数

##### 数学函数

```python
abs(x)      # 绝对值
ceil(x)     # 向上取整
cmp(x, y)   # 比大小，已弃用
exp(x)      # 返回e的2次
fabs(x)     # 绝对值
log(x)      # 取自然对数，math.log(math.e)
log10(x)    # 取以10为底对数
max(x, y..) # 返回较大值
min(x, y..) # 返回较小值
modify(x)   # 返回x的整数部分和小数部分
pow(x, y)   # x**y
round(x,[n])# 返回四舍五入的值，n表示保留的位数
sqrt(x)	    # 返回数字x的平方根
```

##### 随机数函数

```python
import random

a = random.choice(range(10)) # 从序列随机挑选一个元素
print(a)

# 从指定范围内，按指定基数递增的集合中获取一个随机数，基数默认值为 1
b = random.randrange(1, 100, 2) 
print(b)

# 随机生成下一个实数，它在[0,1)范围内。
c = random.random()
print(c)

# 改变随机数生成器的种子seed
random.seed(1)

set = [x for x in range(10)]
# 对序列重新洗牌
random.shuffle(set) 
print(set)

# 生成指定区间的随机数
d = random.uniform(1, 3) 
print(d)
```

##### 三角函数

```python
import math

print(math.acos(1))
print(math.cos(math.pi / 2))
print(math.atan2(1, 1)) # 给定y, x坐标的反正切 
print(math.hypot(3, 4)) # 返回欧几里得范数
print(math.degrees(math.pi / 2)) # 将弧度转化为角度
print(math.radians(90)) # 将角度转化为弧度
```

##### 数字常量

```python
print(math.pi)
print(math.e)
```

```
3.141592653589793
2.718281828459045
```

### 字符串

Python中的字符串用单引号 `'` 或双引号 `"` 括起来，同时使用反斜杠 `\` 转义特殊字符。

#### 访问字符串

字符串的截取的语法格式如下：

```python
变量[头下标:尾下标]
```

索引值以 0 为开始值，-1 为从末尾的开始位置。

```python
print(str)
print(str[0:-1]) #左闭右开
print(str[0])
print(str[2:5])
print(str[2:])
print(str * 2)
print(str + "!")
```

#### 转义字符

Python 使用反斜杠 `\` 转义特殊字符，如果你不想让反斜杠发生转义，可以在字符串前面添加一个 `r`，表示原始字符串：

```python
>>> print('Ru\noob')
Ru
oob
>>> print(r'Ru\noob')
Ru\noob
```

另外，反斜杠`\`可以作为续行符，表示下一行是上一行的延续。也可以使用 `"""..."""` 或者 `'''...'''` 跨越多行。

```python
print("Hello\
      world")   # 续行
print("\\")     # 反斜杠
print("\'")     # 单引号
print("\"")     # 双引号
print("\a")     # 响铃
print("H\bHi")  # 退格
print("\t\n\f") # 水平制表换行换页
print("hhh\rt") # 回车
print("\078")   # 8进制数
print("\x123")  # 16进制数
```

```
Hello      world
\
'
"

Hi



thh
8
3
```

与 C 字符串不同的是，Python 字符串不能被改变。向一个索引位置赋值，比如 `word[0] = 'm'` 会导致错误。

注意，Python 没有单独的字符类型，一个字符就是长度为1的字符串。

#### 字符串格式化

```python
>>> print('我叫% s 今年 %d 岁！' % ('小明', 10))
我叫小明 今年 10 岁！
```

| 符  号 | 描述                                 |
| :----- | :----------------------------------- |
| `%c`   | 格式化字符及其ASCII码                |
| `%s`   | 格式化字符串                         |
| `%d`   | 格式化整数                           |
| `%u`   | 格式化无符号整型                     |
| `%o`   | 格式化无符号八进制数                 |
| `%x`   | 格式化无符号十六进制数               |
| `%X`   | 格式化无符号十六进制数（大写）       |
| `%f`   | 格式化浮点数字，可指定小数点后的精度 |
| ` %e`  | 用科学计数法格式化浮点数             |
| `%E`   | 作用同`%e`，用科学计数法格式化浮点数 |
| `%g`   | `%f`和`%e`的简写                     |
| `%G`   | `%f` 和 `%E` 的简写                  |
| `%p`   | 用十六进制数格式化变量的地址         |

#### 三引号

```python
str = """ Ni
    Tab

    jjj
"""
print(str)
```

#### f-string

`f-string` 格式化字符串以 `f` 开头，后面跟着字符串，字符串中的表达式用大括号 {} 包起来，它会将变量或表达式计算后的值替换进去。

```python
>>> name = 'HZY'
>>> f'Hello {name}'
'Hello HZY'
>>> f'{1 + 2}'
'3'
```

#### 相关方法

```python
str = "Hello"
# 首字母大写
print(str.capitalize())
# 返回一个指定的宽度 width 居中的字符串，fillchar 为填充的字符，默认为空格      
print(str.center(10, '#'))      
# 返回 str 在 string 里面出现的次数，如果beg或者end指定则返回指定范围内str出现的次数
print(str.count('l', 0, len(str)))
# 检测 str 是否包含在字符串中，如果指定范围beg和end，则检查是否包含在指定范围内，如果包含返回开始的索引值，否则返回-1
print(str.find('l', 0, len(str)))
# 跟find()方法一样，只不过如果str不在字符串中会报一个异常
print(str.index('l', 0, len(str)))
# 如果字符串至少有一个字符并且所有字符都是字母或数字
print(str.isalnum())
# 以指定字符串作为分隔符，将seq中所有的元素(的字符串表示)合并为一个新的字符串
seq = ("h", "e", "l", "l", "o")
print("-".join(seq))
# 把 将字符串中的old替换成new,如果max指定，则替换不超过max次
print("11111".replace("1", "2", 3))
# 删除字符串末尾的空格或指定字符
txt = "banana,,,,,ssqqqww....."
print(txt.rstrip(",.qsw"))
# 删除字符串首的空格或指定字符
s = "     bannna"
print(s.lstrip())
# 以 str 为分隔符截取字符串，如果num有指定值，则仅截取num+1个子字符串
set = str.split()
print(set)
# 在字符串上执行 lstrip()和 rstrip()
print("    ss  ".strip())
# 返回"标题化"的字符串,就是说所有单词都是以大写开始，其余字母均为小写
print(str.title())
```

```
Hello
##Hello###
2
2
2
True
h-e-l-l-o
22211
banana
bannna
['Hello']
ss
Hello
```

### 列表

List 是 Python 中使用最频繁的数据类型。

列表可以完成大多数集合类的数据结构实现。列表中元素的类型可以不相同，它支持数字，字符串甚至可以包含列表（所谓嵌套）。

列表是写在方括号 `[]` 之间、用逗号分隔开的元素列表。

#### 访问列表

```python
list = ['abcd', 789, 2.34, True, complex(2, 3)]
tiny_list = [123, 34.2]

print(list)
print(list[0])
print(list[1:3])
print(tiny_list * 2)
print(list + tiny_list)
```

```
['abcd', 789, 2.34, True, (2+3j)]
abcd
[789, 2.34]
[123, 34.2, 123, 34.2]
['abcd', 789, 2.34, True, (2+3j), 123, 34.2]
```

与Python字符串不一样的是，列表中的元素是可以改变的：

```python
>>> a = [1, 2, 3, 4, 5]
>>> a[0] = 9
>>> a
[9, 2, 3, 4, 5]
>>> a[2:5] = []
>>> a
[9, 2]
```

Python 列表截取可以接收第三个参数，参数作用是截取的步长：

```python
>>> a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
>>> a[1::2]
[2, 4, 6, 8, 10]
```

如果第三个参数为负数表示逆向读取。

#### 列表比较

列表比较需要引入`operator` 模块的 `eq` 方法：

```python
import operator

a = [1, 2]
b = [2, 3]
c = [2, 3]
print("operator.eq(a,b): ", operator.eq(a,b))
print("operator.eq(c,b): ", operator.eq(c,b))
```

```
operator.eq(a,b):  False
operator.eq(c,b):  True
```

#### 相关方法

```python
list = [1, 2, 3]

# 在列表尾添加新对象
list.append(4)  
# 统计某个元素出现个数
print(list.count(1))
seq = [5, 6, 7]
# 在末尾一次性追加多值
list.extend(seq)
# 从列表中找出某个值第一个匹配项的索引位置
print(list.index(1))
# 将对象插入列表
list.insert(0, 0)
print(list)
# 移除列表中的一个元素（默认最后一个元素），并且返回该元素的值
print(list.pop())
# 移除列表中某个值的第一个匹配项
list.remove(1)
# 反向列表元素
list.reverse()
print(list)
list.sort(reverse=False)
print(list)
# 拷贝列表
copy_list = list.copy()
print(copy_list)
```

### 元组

元组（tuple）与列表类似，不同之处在于元组的元素不能修改。元组写在小括号 `()` 里，元素之间用逗号隔开。

元组中的元素类型也可以不相同：

```python
tuple = ['abcd', 789, 2.34, True, complex(2, 3)]
tiny_tuple = [123, 34.2]

print(tuple)
print(tuple[0])
print(tuple[1:3])
print(tiny_tuple * 2)
print(tuple + tiny_tuple)
```

虽然`tuple`的元素不可改变，但它可以包含可变的对象，比如list列表。

### 集合

集合（set）是由一个或数个形态各异的大小整体组成的，构成集合的事物或对象称作元素或是成员。

基本功能是进行成员关系测试和删除重复元素。

#### 创建集合

创建格式：

```python
parame = {value01, value02, ...}
set(value)
```

```python
sites = {'Google', 'IE', 'Facebook', 'Twiter'}

print(sites)  # 输出集合，重复的元素被自动去掉

if 'Google' in sites:

  print('Google在集合中')

else:

  print('Google不在集合中')

a = set('abracadabra')
b = set('alacazam')

print(a)
print(a - b)   # a 和 b 的差集
print(a | b)   # a 和 b 的并集
print(a & b)   # a 和 b 的交集
print(a ^ b)   # a 和 b 中不同时存在的元素
```

```
Google在集合中
{'r', 'a', 'b', 'd', 'c'}
{'r', 'b', 'd'}
{'l', 'r', 'a', 'z', 'm', 'b', 'd', 'c'}
{'a', 'c'}
{'m', 'b', 'd', 'l', 'r', 'z'}
```

#### 相关方法

```python
set = {1, 2}
print(set)
# 移除集合中的所有元素
set.clear() 
print(set)
# 为集合添加元素
set.add(1)
set.add(2)
# 返回多个集合的差集
print(set.difference({1, 3}))
# 删除集合中指定的元素
set.discard(2)
# 返回集合的交集
print(set.intersection({1}))
# 判断两个集合是否包含相同的元素
print(set.isdisjoint({1, 2}))
# 判断指定集合是否为该方法参数集合的子集
print(set.issubset({1}))
# 判断该方法的参数集合是否为指定集合的子集
print(set.issuperset({1}))
# 随机删除元素
set.pop()
# 移出指定元素
set.add(1)
set.remove(1)
# 返回两个集合的并集
set = set.union({3, 4})
# 给集合添加元素（迭代器）
set.update({1, 2})
print(set)
```



### 字典

字典（dictionary）是Python中另一个非常有用的内置数据类型。

列表是有序的对象集合，字典是无序的对象集合。两者之间的区别在于：字典当中的元素是通过键来存取的，而不是通过偏移存取。

字典是一种映射类型，字典用 `{}` 标识，它是一个无序的 键（`key`） : 值（`value`） 的集合。

键(key)必须使用不可变类型。

在同一个字典中，键必须是唯一的。

#### 访问字典

```python
dict = {}
dict['one'] = "Hello"
dict[2] = "World"

tiny_dict = {'name': 'hzy', 'birth': '2002-12-18'}

print(dict['one'])
print(dict[2])
print(tiny_dict.keys())
print(tiny_dict.values())
```

```
Hello
World
dict_keys(['name', 'birth'])
dict_values(['hzy', '2002-12-18'])
```

构造函数 `dict()` 可以直接从键值对序列中构建字典如下：

```python
>>> dict([('2',3), ('3', 5)])
{'2': 3, '3': 5}
>>> dict(One=1, Two=2)
{'One': 1, 'Two': 2}
```

#### 相关方法

```python
dic = dict([('2',3), ('3',4)])
print(dic)
print(dic.get('4', 5))
# 返回一个视图对象
print(dic.items())
# 返回一个视图对象
print(dic.keys())
# 但如果键不存在于字典中，将会添加键并将值设为default
dic.setdefault('4', 5)
dic2 = {'5':6, '6':7}
dic.update(dic)
# 把字典dict2的键/值对更新到dict里
print(dic)
print(dic.values())
# 删除字典 key（键）所对应的值，返回被删除的值
print(dic.pop('2'))
# 返回并删除字典中的最后一对键和值
print(dic.popitem()) 
print(dic)
```

### 类型转换

```python
x = int(2.8)        # 将浮点数转换为整数
y = int("2")        # 将字符串转换为整数
z = float("3.2")    # 将字符串转换为浮点数 
a = str(3.0)        # 将浮点数转换为字符串
b = int('16', 16)   # 将16进制转换为为10进制
c = repr("Hello")   # 将对象转化为供解释器读取的形式。
d = frozenset({1})  # 转换为不可变集合
e = hex(16)         # 16进制
f = oct(16)         # 8进制
g = eval('x * 2')   # 计算在字符串中的有效Python表达式

print(x, y, z)
print(a, b, c, d, e, f, g)
```

## Python3推导式

### 列表推导式

列表推导式格式为：

```python
[表达式 for 变量 in 列表 if 条件] 
[out_exp_res for out_exp in input_list if condition]
```

```python
>>> names = ['Bob', 'Tom', 'Jerry', 'Herry']
>>> new_names = [name.upper() for name in names if len(name) > 3]
>>> print(new_names)
['JERRY', 'HERRY']
```

计算 30 以内可以被 3 整除的整数：

```python
>>> multiples = [i for i in range(30) if i % 3 == 0]
>>> print(multiples)
[0, 3, 6, 9, 12, 15, 18, 21, 24, 27]
```

### 字典推导式

字典推导式基本格式：

```python
{ key_expr: value_expr for value in collection if condition }
```

```python
>>> listdemo = ['Google', 'Baidu', 'Taobao']
>>> new_dict = {key : len(key) for key in listdemo}
>>> print(new_dict)
{'Google': 6, 'Baidu': 5, 'Taobao': 6}
```

提供三个数字，以三个数字为键，三个数字的平方为值来创建字典：

```python
>>> dict = {x: x**2 for x in (2, 4, 6)}
>>> dict
{2: 4, 4: 16, 6: 36}
```

### 集合推导式

集合推导式基本格式：

```python
{ expression for item in Sequence if conditional }
```

```python
>>> set = {i**2 for i in range(4)}
>>> set
{0, 1, 4, 9}
```

### 元组推导式

元组推导式基本格式：

```python
(expression for item in Sequence if conditional )
```

元组推导式和列表推导式的用法也完全相同，只是元组推导式是用` () `圆括号将各部分括起来，而列表推导式用的是中括号` []`，另外元组推导式返回的结果是一个生成器对象。

```python
>>> a = (x for x in range(1,10))
>>> a
<generator object <genexpr> at 0x7faf6ee20a50>  # 返回的是生成器对象

>>> tuple(a)       # 使用 tuple() 函数，可以直接将生成器对象转换成元组
(1, 2, 3, 4, 5, 6, 7, 8, 9)
```

```python
list1 = ['python', 'test1', 'test2']
list2 = [word.title() if word.startswith('p') else word.upper() for word in list1]
print(list2)
```

## Python3解释器

### 交互式编程

当键入一个多行结构时，续行是必须的。

```python
>>> flag = True
>>> if flag:
...     print("True")
... else:
...     print("False")
True
```

### 脚本式编程

将如下代码拷贝至 `hello.py`文件中：

```python
print ("Hello, Python!");
```

通过以下命令执行该脚本：

```shell
python3 hello.py
```

输出结果为：

```python
Hello, Python!
```