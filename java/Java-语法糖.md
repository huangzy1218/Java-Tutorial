# Java-语法糖

## 语法糖

语法糖（Syntactic sugar），也叫做糖衣语法，是英国科学家发明的一个术语，通常来说使用语法糖能够增加程序的`可读性`，从而减少程序代码出错的机会。

语法糖指的是计算机语言中添加的某种语法，**这种语法对语言的功能并没有影响，但是更方便程序员使用**。因为 Java 代码需要运行在 JVM 中，JVM 是并不支持语法糖的，语法糖在程序编译阶段就会被还原成简单的基础语法结构，这个过程就是**解语法糖**。

### 泛型

泛型机制的本身是通过**类型擦除**来实现的，在 JVM 中没有泛型，只有普通类型和普通方法，泛型类的类型参数，在编译时都会被擦除。泛型并没有自己独特的 Class类型。

```java
List<Integer> aList = new ArrayList();
List<String> bList = new ArrayList();
// true
System.out.println(aList.getClass() == bList.getClass());
```

**泛型信息只存在于代码编译阶段，在进入 JVM 之前，与泛型相关的信息会被擦除掉**。但是，如果将一个 Integer 类型的数据放入到  `List<String>` 中或者将一个 String 类型的数据放在 `List<Ineger>` 中是不允许的。

### 自动拆箱和自动装箱

自动拆箱和自动装箱是一种语法糖，它说的是八种基本数据类型的包装类和其基本数据类型之间的自动转换。简单的说，拆箱就是自动将基本数据类型转换为包装器类型；装箱就是自动将包装器类型转换为基本数据类型。

```java
Integer integer = 66; // 自动装箱

int i1 = integer;   // 自动拆箱
```

其实这背后的原理是编译器做了优化。将基本类型赋值给包装类其实是调用了包装类的 `valueOf()` 方法创建了一个包装类再赋值给了基本类型。

### 枚举

但是在 Java 字节码结构中，并没有枚举类型。枚举类型是**在编译完成后就会被编译成一个普通的类，也是用 Class 修饰。这个类继承于 java.lang.Enum，并被 `final` 关键字修饰**。

编译器还会为我们生成两个方法，`values()` 方法和 `valueOf` 方法，这两个方法都是编译器为我们添加的方法，通过使用 values() 方法可以获取所有的 Enum 属性值，而通过 valueOf 方法用于获取单个的属性值。

```java
public enum School {

    STUDENT("Student"),
    TEACHER("Teacher");

    private String name;

    School(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        System.out.println(School.STUDENT.getName());

        School[] values = School.values();
        for(School school : values){
            System.out.println("name = "+ school.getName());
        }

    }
}
```

### 内部类

内部类是 Java 一个`小众` 的特性，在日常开发很少用到。

比如常见的 `ArrayList` 源码中就有一个 `Itr` 内部类继承于 `Iterator` 类；再比如 `HashMap` 中就构造了一个 `Node` 继承于 Map.Entry<K,V> 来表示 HashMap 的每一个节点。

内部类其实也是一个语法糖，因为其只是一个编译时的概念，一旦编译完成，编译器就会为内部类生成一个单独的class 文件，名为 outer$innter.class。

### 变长参数

一般我们开发不会使用到变长参数，而且变长参数也不推荐使用，它会使我们的程序变的难以处理。

```java
public class VariableArgs {
    public static void printMessage(String... args){
        for(String str : args){
            System.out.println("str = " + str);
        }
    }

    public static void main(String[] args) {
        VariableArgs.printMessage("l","am","cxuan");
    }
}
```

变长数组的参数就是使用了一个数组来接收。变长参数特性是在 JDK 1.5 中引入的，使用变长参数有两个条件，一是变长的那一部分参数具有相同的类型，二是变长参数必须位于方法参数列表的最后面。

### 增强 for 循环

增强 for 循环的对象要么是一个数组，要么实现了 Iterable 接口。这个语法糖主要用来对数组或者集合进行遍历，其在循环过程中**不能改变集合的大小**。

如果对数组进行增强 for 循环的话，其内部还是对数组进行遍历，只不过以一种更简洁的方式编写代码。

而对继承于 `Iterator` 迭代器进行增强 for 循环遍历的话，相当于是调用了 Iterator 的 `hasNext()` 和 `next()` 方法。

### Switch 支持字符串和枚举

`switch` 关键字原生只能支持`整数`类型。如果 switch 后面是 String 类型的话，编译器会将其转换成 String 的`hashCode` 的值，所以其实 switch 语法比较的是 String 的 hashCode 。

```java
public class SwitchCaseTest {

    public static void main(String[] args) {

        String str = "cxuan";
        switch (str){
            case "cuan":
                System.out.println("cuan");
                break;
            case "xuan":
                System.out.println("xuan");
                break;
            case "cxuan":
                System.out.println("cxuan");
                break;
            default:
                break;
        }
    }
}
```

### 条件编译

对其中一部分内容只在满足一定条件下才进行编译，即对一部分内容指定编译条件，这就是条件编译（conditional compile）。

使用 final + if 的组合可以实现条件编译。

```java
public static void main(String[] args) {  
  final boolean DEBUG = true;  
  if (DEBUG) {  
    System.out.println("Hello, world!");  
  }  else {
    System.out.println("nothing");
  }
}  
```

反编译结果如下：

```java
public class ConditionCompileTest {
    public static void main(String[] args) {
        boolean DEBUG = true;
        System.out.println("Hello World");
    }
}
```

Java 语法的条件编译，是通过判断条件为常量的 if 语句实现的，编译器不会为我们编译分支为 false 的代码。

### 断言

断言：也就是所谓的 `assert` 关键字，是 jdk 1.4 后加入的新功能。它主要使用在代码开发和测试时期，用于对某些关键数据的判断，如果这个关键数据不是你程序所预期的数据，程序就提出警告或退出。当软件正式发布后，可以取消断言部分的代码。

如果要开启断言检查，则需要用开关 `-enableassertions` 或 `-ea` 来开启。其实断言的底层实现就是 `if` 判断，如果断言结果为 `true`，则什么都不做，程序继续执行，如果断言结果为 `false`，则程序抛出 `AssertError` 来打断程序的执行。

`assert` 断言就是通过对布尔标志位进行了一个 `if` 判断。

### try-with-resources

JDK 1.7 开始，java引入了 try-with-resources 声明，将 try-catch-finally 简化为 try-catch，这其实是一种`语法糖`，在编译时会进行转化为 try-catch-finally 语句。新的声明包含三部分：try-with-resources 声明、`try` 块、`catch` 块。它要求在 try-with-resources 声明中定义的变量实现了 `AutoCloseable` 接口，这样在系统可以自动调用它们的 `close` 方法，从而替代了 `finally` 中关闭资源的功能。

public class TryWithResourcesTest {

```java
public static void main(String[] args) {
    try (InputStream inputStream = new FileInputStream(new File("xxx"))) {
        inputStream.read();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
反编译后如下：

```java
try {
    InputStream inputStream = new FileInputStream(new File("Hello.txt"));

    try {
        inputStream.read();
    } catch (Throwable var5) {
        try {
            inputStream.close();
        } catch (Throwable var4) {
            var5.addSuppressed(var4);
        }

        throw var5;
    }
}
```

### 字符串相加

那么使用 `+` 号连接的字符串会被编译器直接优化为相加的结果，如果编译期不能确定拼接的结果，底层会直接使用 `StringBuilder` 的 `append` 进行拼接。

```java
String s1 = "I am " + "cxuan";
String s2 = "I am " + new String("cxuan");
String s3 = "I am ";
String s4 = "cxuan";
String s5 = s3 + s4;
```

反编译后如下：

```java
String s1 = "I am cxuan";
String var10000 = new String("cxuan");
String s2 = "I am " + var10000;
String s3 = "I am ";
String s4 = "cxuan";
// 使用StringBuilder进行拼接
s3.makeConcatWithConstants<invokedynamic>(s3, s4);
```

