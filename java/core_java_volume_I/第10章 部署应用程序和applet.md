# 第10章 部署应用程序和applet

## JAR文件

在将应用程序进行打包时，使用者一定希望仅提供给其一个单独的文件，而不是一个含有大量类文件的目录， Java 归档（JAR）文件就是为此目的而设计的。一个JAR文件既可以包含类文件，也可以包含诸如图像和声音这些其他类型的文件。此外， JAR文件是压缩的，它使用了大家熟悉的ZIP压缩格式。

可以使用jar工具制作JAR文件（在默认的JDK安装中，位于jdk/bin 目录下）。创建一个新的JAR文件应该使用的常见命令格式为：

```shell
jar cvf JARFileName File1 File2 ...
```

例如：

```shell
jar cvf CalculatorClasses.jar *.class, icon.gif
```

### 清单文件

除了类文件、图像和其他资源外，每个JAR文件还包含一个用于描述归档特征的清单文件。

清单文件被命名为MANIFEST.MF ，它位于JAR文件的一个特殊META-INF子目录中。最小的符合标准的清单文件是很简单的：

```java
Manifest-Version: 1.0
```

复杂的清单文件可能包含更多条目。这些清单条目被分成多个节。第一节被称为主节（main section ）。它作用于整个JAR文件．随后的条目用来指定己命名条目的属性，这些已命名的条目可以是某个文件、包或者URL。它们都必须起始于名为Name的条目。节与节之间用空行分开。例如：

```jar
Manifest-Version: 1.0
描述归档文件的行
Name: Woozle.class
描述这个包的行
```

想要编辑清单文件，需要将希望添加到清单文件的行放到文本文件中，运行：

```shell
jar cfm JARFileName ManifestFileName
```

