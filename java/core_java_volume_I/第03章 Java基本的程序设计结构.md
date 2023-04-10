# 第3章 Java基本的程序设计结构

## 一个简单的Java程序

类作为一个加载程序逻辑的容器，程序逻辑定义了应用程序的行为。

## 注释

```java
// comments
/* comments */
/**
 file comments
 */
```

## 数据类型

### 整型

| 类型    | 存储需求 |
| ------- | -------- |
| `int`   | 4字节    |
| `short` | 2字节    |
| `long`  | 8字节    |
| `byte`  | 1字节    |

长整型数值有一个后缀`L` （如`4000000000L` ) ，十六进制数值有一个前缀`0x` （如`0xCAFE `），八进制有一个前缀`0`Java无任何无符号类型。

### 浮点类型

| 类型     | 存储需求 |
| -------- | -------- |
| `float`  | 4字节    |
| `double` | 8字节    |

`float`类型的数值有一个后缀`F` （例如3.402F ）。没有后缀`F`的浮点数值（如3.402 ）默认为`double`类型。

### 字符类型

`char`类型用于表示单个字符。通常用来表示字符常量。Unicode编码单－元可以表示为十六进制值，其范围从`\u0000`到`\uffff`。

Unicode打破了传统字符编码方法的限制。在Unicode 出现之前，已经有许多种不同的标准：美国的ASCII 、西欧语言中的ISO 8859-1 、俄国的KOI-8 、中国的GB118030和BIG-5等等。Unicode字符超过了65 536个，16位的`char`类型已经不能满足描述所有Unicode字符的需要。

在Java中， `char`类型用UTF-16编码描述一个代码单元。我们强烈建议不要在程序中使用`char`类型，除非确实需要对UTF-16代码单元进行操作。最
好将需要处理的字符串用抽象数据类型表示。

### 布尔类型

boolean （布尔）类型有两个值`false`和`true` ，用来判定逻辑条件。整型值和布尔值之间不能进行相互转换。

## 变量

### 变量初始化

声明一个变量之后，必须用赋值语句对变量进行显式初始化。

在Java 中，不区分变量的声明与定义。

### 常量

在Java中，利用关键字`final`声明常量。习惯上，常量名使用大写。

```java
final MAXIUM = 10;
```

在Java中，经常希望某个常量可以在一个类中的多个方法中使用，通常将这些常量称为类常量。可以使用关键字`static final`设置一个类常量。

## 运算符

可移植性是Java语言的设计目标之一。无论在哪个虚拟机上运行，同一运算应该得一到同样的结果。对于浮点数的算术运算，实现这样的可移植性是相当困难的。`double`类型使用64位存储一个`double`数值，而有些处理器使用80位浮点寄存器。例如下列运算：

```java
double w = x * y / z;
```

很多Intel处理器计算`x * y` ，并且将结果存储在80位的寄存器中，再除以z并将结果截断为64位。这样可以得到一个史加精确的计算结果，并且还能够避免产生指数溢出。但是，这个结果可能与始终在64住机器上计算的结果不一样。

在默认情况下，虚拟机设计者允许将中间计算结果采用扩展的精度。但是，对于使用`strictfp `关键字标记的方法必须使用严格的浮点计算来产生理想的结果。例如可将`main`方法标记为：

```java
public static strictfp void main(String[] args)
```

于是，在`main` 方法中的所有指令都将使用严格的浮点计算。如果将一个类标记为`strictfp`，这个类中的所有方法都要使用严格的浮点计算。

### 自增运算符和自减运算符

我们建议不要在其他表达式的内部使用`++`、`--`。

### 关系运算符和`boolean`表达式

`&&`和`||` ；是按照“短路”方式求值的。如果第一个操作数已经能够确
定表达式的值，第二个操作数就不必计算。

`&`和`|`运算符应用于布尔运算，得到的结果也是布尔值。只是不按“短路”方式计算。即在得到计算结果之前，一定要计算两个操作数的值。

### 位运算符

`>>>`运算符将用**0**填充高位，`>>`运算符用符号位填充高位。

### 数学函数与常量

从JDK 5.0开始，不必在数学方法名和常量名前添加前提`Math. `，而只需导入`import static java.lang.Math`即可。

在`Math`类中，为了达到最快的性能，所有的方法都使用计算机浮点单元中的例程。如果得到一个完全可预测的结果比运行速度史重要的话，那么就应该使用`StrictMath`类。它使用“自由发布的Math库”（fdlibm）实现算法，以确保在所有平台上得到相同的结果。

### 数值类型之间的转换

当使用上面两个数值进行二元操作时（例如`n + f`, n是整数， f是浮点数），先要将两个操作数转换为同一种类型，然后再进行计算。

### 强制类型转换

当调用`round`的时候，仍然需要使用强制类型转换（ `int` ）。其原因是`round`方法返回的结果为`long`类型，由于存在信息丢失的可能性。

果试图将一个数位从一种类型强制转换为另一种类型，而又超出了目标类型的表示范围，结果就会截断断成一个完全不同的值。例如，`(byte)300`的实际佳为44 。

### 括号与运算符优先级

由于`&&`的优先级比`||` 的优先级高，所以表达式`a && b || c`等价于
`(a && b) || c`。
因为`+=`是右结合运算符，所以表达式`a += b += c`等价于`a += (b += c)`。

与C或C++不同，Java不使用逗号运算符。

### 枚举类型

从JDK 5.0开始，可以自定义枚举类型。枚举类型包括有限个命名的值。
例如：

```java
enum Size { 
    SMALL, MEDIUM, LARGE, EXTRA_LARGE
};
Size s = Size.MEDIUM;
```

`Size`类型的变量只能存储这个类型声明中给定的某个枚举值，或者`null`值。

## 字符串

Java没有内置的字符串类型，而是在标准Java类库中提供了一个预定义类
`String`。

### 子串

```java
String substring(fromInex, toIndex)
```

### 拼接

与绝大多数的程序设计语言一样， Java语言允许使用`＋`号连接（拼接）两个字符串。

### 不可变字符串

`String`类没有提供用于修改字符串的方法。

编译器可以让字符串共享。将各种字符串存放在公共的存储池中。字符串变量指向存储池中相应的位置。如果复制一个字符串变量，原始字符串与复制的字符串共享相同的字符。

### 检测字符串是否相等

```java
public boolean equal(String)
```

如果虚拟机始终将相同的字符串共享，就可以使用`==`运算符检测是否相等。但实际上只有字符串常量是共享的，而`+`或`substring`等操作产生的结果并不是共享的。

### 代码点和代码单元

`length`方法将返回采用UTF-16编码表示的给定字符串所需要的代码单元数量。例如：

```java
String greeting = "Hello";
int n greeting.length();
```

要想得到实际的长度，即代码点数量，可以调用：

```java
int cpCount = greeting.codePointCount(0, greeting.length());
// 要想得到第i个代码点，应该使用下列语句
int index= greeting.offsetByCodePoints(0, i);
int cp = greeting.codePointAt(index);
```

```java
String greeting = "Hello";
int n = greeting.length();
int cpCount = greeting.codePointCount(0, n);
System.out.println(cpCount);
for (int i = 0; i < n; i++) {
	int index = greeting.offsetByCodePoints(0, i);
	int cp = greeting.codePointAt(index);
	System.out.print(cp);
}
```

```
5
72101108108111
```

使用UTF-16编码表示某些字符需要两个代码单元。调用
`char ch= sentence.charAt(1);`，返回的不是空格（辅助字符），而是第二个代码单元的字符。`codePointAt`方法能够辨别一个代码单元是辅助字符的第一部分还是第二部分，并能够返回正确的结果。

### 构建字符串

使用`StringBuilder`类构建字符串，当每次需要添加一部分内容时，就调用`append(char/String)`方法。

在JDK 5.0引入`StringBuilder`类． 这个类的前身是`StringBuffer` ，其效率略低，但允许采用**多线程**的方式执行添加或删除字符的操作。如果所有字符串在一个单线程中（通常都是这样）编辑，则应该用`StringBuilder`替代它。两者API相同。

## 输入输出

### 读取输入

```java
Scanner in = new Scanner(System.in);
String name = in.nextLine(); // 在输入行中有可能包含空格
```

为输入是可见的，所以`Scanner`类不适用于从控制台读取密码，Java SE 6引入`Console`类实现这个目的。

```java
Console cons = System.console();
String username = cons.readLine("Username: ");
char[] password = cons.readPassword("Password: "); // 返回char[]
System.out.println("Username: " + username);
System.out.println("Password: " + Arrays.toString(password)); 
```

### 格式化输出

可以使用静态的`String.format`方法创建一个格式化的字符串：

```java
String message = String.format("Hello, %s. You are %d years old", name, age);
```

### 文件输入输出

要想对文件进行读取，就需要一个用`File`对象构造一个`Scanner`对象；要想写入文件，就需要构造一个`PrintWriter`对象。

```java
public static void main(String[] args) throws IOException 
{
    File file = new File("myfile.txt");
    Scanner in = new Scanner(new File("myfile.txt"));
    String message = in.nextLine();
    System.out.println(message);
    PrintWriter out = new PrintWriter("myfile.txt");
    out.println("Hello, World");
}
```

## 控制流程

### 块作用域

在C++中，可以在嵌套的块中重定义一个变量。在内层定义的变量会覆盖外层定义的变量。这样，有可能会导筑程序设计错误，因此在Java 中不允许这样做。

### 条件语句

### 循环

`for` 语句的3个部分应该对同一个计数器变量进行初始化、检测和更新。若不遵守这一规则，编写的循环常常晦涩难懂。

## 大数值

如果基本的整数和浮点数精度不能够满足需求，那么可以使用`java.math`包中的两个很有用的类： `BigInteger`和`BigDecimal` 。 这两个类可以处理包含任意长度数字序列的数值。`BigInteger`类实现了任意精度的整数运算，`BigDecimal`实现了任意精度的浮点数运算。

使用静态的`valueOf`方法可以将普通的数值转换为大数值：

```java
BigInteger a = BitInteger.valueOf(100);
BigInteger c = a.add(b); // c = a + b;
BigInteger d = c.multiply(b.divide(c)); // d = c * (b / c);
```

`BigDecimal divide(BigDecilllal other, RoundingMode mode)`，要想计算商，必须给出舍入方式（rounding mode）。`RoundingMode.HALF_UP`为四舍五入，`.CEILING`、`.FLOOR`

## 数组

在Java 中，允许数组长度为0。在编写一个结果为数组的方法时，如果碰巧结果为空，则这种语法形式就显得非常有用。

## 命令行参数

在Java应用程序的`main`方法中，程序名并没有存储在`args`数组中。
