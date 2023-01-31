# 第2章 Java程序设计环境

## 安装Java开发工具箱

Oracle公司为Solaris 、Linux和Windows提供了Java开发工具箱（ JDK ）的最新、最完整的版本。

### 下载JDK

下载链接：http://www.oracle.com/technetwork/java/javase/downloads/index.html

Java2为一个过时的术语．用于描述1998年－2006年之间的Java版本。2006
年，日趋完善的Java SE开始流行。无意义的Java 2被遗弃， Java当前的标准版本被称为Java SE 6.0，偶尔还会看到使用1.5版本和1.6版本，但这些只是5.0版本和6版本的同义词。

JDK是Java Develop Kit的缩写。工具箱的版本1.2版本称为Java SDK (Software Development Kit ）。另外，Java 运行时环境（JRE ）包含虚拟机但不包含编译器。这并不是开发者所想要的环境，而是专门为不需要编译器的用户而设计。

### 设置执行路径

在完成了JDK 的安装之后，还需要执行另外一个步骤：把jdk/bin 目录添加到执行路径中。所谓执行路径是指操作系统搜索本地可执行文件的目录列表。

测试上述设置是否正确的方法：打开一个shell窗口，键人：

```shell
java -version
java version "1.8.0_351"
Java(TM) SE Runtime Environment (build 1.8.0_351-b10)
Java HotSpot(TM) 64-Bit Server VM (build 25.351-b10, mixed mode)
```

### 安装源码库和文档

库源文件在JDK中以一个压缩文件src.zip 的形式发布， 必须将其解压缩后才能够访问源代码。

## 选择开发环境

Eclipse、IDEA、Microsoft Visual Studio

## 使用命令行工具

```shell
javac Welcome.java
java Welcome
Welcome to Core Java!
=====================
```

javac程序是一个Java编译器。它将文件Welcom.java编译成Welcom.class ，并发送到Java虚拟机。虚拟机执行编译器存放在class文件中的字节码。

## 使用集成开发环境

本节将介绍如f可使用Eclipse编译一个程序。Eclipse是一个可以从网站http://eclipse.org上免费下载得到的集成开发环境。Eclipse是采用java编写的，然而，由于所使用的是非标准视窗类库，所以，不如Java那样具有可移植性。尽管如此，这个开发环境已经拥有可以应用在Linux 、Mac OS X、Solaris以及Windows的版本了。还有一些使用比较普遍的IDE ，但就目前而言，Eclipse是最通用的。