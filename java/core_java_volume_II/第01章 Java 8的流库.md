# 第01章 Java 8的流库

与集合相比，流提供了一种可以让我们在更高的概念级别上指定计算任务的数据视图。

## 从迭代到流的操作

```java
var contents = new String(File.readAllBytes(
	Paths.get("alice.txt")). StandardCharactsets.UTF_8);
List<String> words = List.of(contents.split("\\PL+"));
// 迭代
long count = 0;
for (String word : words) {
    if (word.length() > 12) {
        count++;
    }
}
```

使用流进行操作如下：

```java
long count = words.stream()
    	.filter(word -> word.length() > 12)
    	.count();
```

仅将`stream` 修改为`parallelStrearn` 就可以让流库以并行方式来执行过滤和计数。

流遵循了“做什么而非怎么做”的原则。

流表面上看起来和集合很类似，都可以让我们转换和获取数据。但是，它们之间存在着显著的差异：

1. 流并不存储其元素。这些元素可能存储在底层的集合中，或者是按需生成的。

2. 流的操作不会修改其数据源。而是会生成个新的流， 生成符合条件的流对象。s
3. 流的操作是尽可能惰性执行的。这意味着直至需要其结果时，操作才会执行。例如，如果我们只想查找前5个长单词而不是所有长单词，那么`filter`方法就会在匹配到第5 个单词后停止过滤。因此，我们甚至可以操作无限流。

操作流时的典型流程如下：

1. 创建一个流。
2. 指定将初始流转化为其他流操作的中间操作可能包含多个步骤
3. 应用终止操作，从而产生结果。
4. 这个操作会强制执行之前的惰性操作。从此之后，这个流就再也不能用了。

## 流的创建

`Collection` 接口的`stream` 方法将任何集合转换为一个流，数组可使用`Stream.of`方法。

```java
Stream<String> words = Stream.of(content.split("\\PL+"));
```

`of `方法具有可变长参数， 因此我们可以构建具有任意数量引元的流：

```java
Stream.of("gently", "down", "the", "stream");
```

使用`Array.stream(array, from, to)` 可以用数组中的一部分元素来创建一个流。为了创建不包含任何元素的流，可以使用静态的`Stream.empty` 方法。

`Stream` 接口有两个用于创建无限流的静态方法。`generate` 方法会接受一个不包含任何引元的函数（或者从技术上讲，是一个`Supplier<T>` 接口的对象） 。无论何时， 只要需要一个流类型的值， 该函数就会被调用以产生一个这样的值。我们可以像下面这样获得一个常量值的流：

```java
Stream<String> echos = Stream.generate(() -> "Echo" 
});
```

如果要产生序列，可以使用`iterate`方法。它会接受一个“种子” 值，以及一个函数（从技术上讲，是一个`UnaryOperation<T>`，并且会反复地将该函数应用到之前的结果上。

```java
Stream<BigInteger> integers = Stream.iterator(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
```

如果要产生一个有限序列， 则需要添加一个谓词来描述迭代应该如何结束：

```java
var limit = new Biglnteger("10000000);
Stream<BigInteger> integers = Strearn.iterate(BigInteger.ZERO,
n -> n.compareTo(limit) < 0,
n -> n.add(BigInteger.ONE));
```

```java
public static <T> void show(String title, Stream<T> stream) {
    final int SIZE = 10;
    List<T> firstElements = stream
        .limit(SIZE + 1)
        .collect(Collectors.toList());
    System.out.print(title + ": ");
    for (int i = 0; i < firstElements.size(); i++) {
        if (i > 0) {
            System.out.print(", ");
        }
        if (i < SIZE) {
            System.out.print(firstElements.get(i));
        }
        else {
            System.out.print("...");
        }
    }
    System.out.println();
}

public static void main(String[] args) throws IOException {
    Path path = Paths.get("./cite.txt");
    // 将文件中的所有内容读入字符串
    var contents = Files.readString(path);

    Stream<String> words = Stream.of(contents.split("\\PL+"));
    show("words", words);
    Stream<String> song = Stream.of("gently", "down", "the", "stream");
    show("song", song);
    Stream<String> silence = Stream.empty();
    show("silence", silence);

    Stream<String> echos = Stream.generate(() -> "Echo");
    show("echos", echos);

    // 无限流
    Stream<Double> randoms = Stream.generate(Math::random);
    show("randoms", randoms);

    Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE,
                                                 n -> n.add(BigInteger.ONE));
    show("integers", integers);

    Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
    show("wordsAnotherWay", wordsAnotherWay);

    try (Stream<String> lines = Files.lines(path)) {
        show("lines", lines);
    }

    // 默认实现返回的分裂器具有较差的分割能力
    Iterable<Path> iterable = FileSystems.getDefault().getRootDirectories();
    Stream<Path> rootDirectories = StreamSupport.stream(iterable.spliterator(), false);
    show("rootDirectories", rootDirectories);

    Iterator<Path> iterator = Paths.get("/usr/share/dict/words").iterator();
    Stream<Path> pathComponents = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
        iterator, Spliterator.ORDERED), false);
    show("pathComponents", pathComponents);
}
```

## `filter` 、`map` 和`filterMap` 方法

```java
long count = words.stream()
    	.filter(word -> word.length() > 12)
    	.count();
```

`filter`的引元是`Predicate<T>` ，即从`T` 到`boolean` 的函数。

按照某种方式来转换流中的值，此时，可以使用`map` 方法并传递执行该
转换的函数。

```java
Stream<String> lowercaseWord = words.stream().map(String::toLower);
```

通常我们可以使用lambda 表达式来代替：

```java
Stream<String> firstLetters = words.stream().map(s -> s.substring(0, 1));
```

在使用`map` 时，会有一个函数应用到每个元素上，并且其结果是包含了应用该函数后所产生的所有结果的流。

以下示例会将字符串转换为字符串流，即一个个的编码点：

```java
public static Stream<String> codePoint(String s) {
    var result = new ArrayList<String>();
    int i = 0;
    while (i < s.length()) {
        int j = s.offsetByteCodePoint(i, 1);
        result.add(s.substring(i, j));
        i = j;
    }
    return result.stream();
}
```

这个方法可以正确地处理需要用两个`char `值来表示的Unicode 字符，因为本来就应该这样处理。

## 抽取子流和组合流

调用`stream.limit(n)` 会返回一个新的流，它在`n`个元素之后结束（如果原来的流比`n`短，那么就会在该流结束时结束）。这个方法对于裁剪无限流的尺寸特别有用。

```java
Stream<Double> randoms = Stream.generate(Math::randowm).limit(100);
```

调用`stream.skip(n)` 正好相反：它会丢弃前`n`个元素。

`stream.takeWhile(predicate)`调用会在谓同为真时获取流中的所有元素，然后停止。

`dropWhile` 方法的做法正好相反，它会在条件为真时丢弃元素，并产生一个由第一个使i亥条件为假的字符开始的所有元素构成的流。

我们可以用`Stream` 类的静态`concat` 方法将两个流连接起来。

## 其他的流转换

`distinct`方法会返回一个流， 它的元素是从原有流中产生的， 即原来的元素按照同样的顺序剔除重复元素后产生。

```java
Stream<String> uniqueWords = Stream.of("merily", "merily", "gently");
```

对于流的排序，有多种`sorted`方法的变体可用。其中一种用于操作`Comparable`元素的流，另一种可以接受一个`Comparator`。

```java
List<String> words = List.of("Hello", "World", "Spring");
Stream<String> longestFirst = words.stream().sorted(Comparator.comparing(String::length).reversed());
```

`peek` 方法会产生另一个流，它的元素与原来流巾的元素相同，但是在每次获取一个元素时，都会调用一个两数。

```java
// 主与实际访问一个元素时，就会打印归来－条消息
Object[] powers = Stream.iterate(1.0, p -> p * 2)
    .peek(e-> System.out.println("Fetching " + e))
    .limit(20).toArray();
System.out.println(Arrays.toString(powers));
```

## 简单约简

从流数据中获得答案称为约简。约简是一种终结操作（terminal operation），它们会将流约简为可以在程序中使用的非流值。

这些方法返回的是一个类型`Optional<T>` 的值，它要么在其中包装了答案，要么表示没有任何值（因为流碰巧为空） 。

```java
Optional<String> largest = words.max(String::compareTo lgnoreCase);
System.out.println("larget: " + larget.orElse());
```

`findFirst` 返回的是非空集合中的第一个值。它通常与`filter`组合使用。

```java
List<String> words = List.of("Hello", "Question", "Query");
Optional<String> startWithQ = words.stream().filter(s -> s.startsWith("Q")).findFirst();
System.out.println(startWithQ.orElse(""));
```

还有`allMatch` 和`noneMatch`方法，它们分别在所有元素和没有任何元素匹配谓词的情况下返回`true`。

## `Optional`类型

`Optional<T>` 对象是一种包装器对象，要么包装了类型`T`的对象，要么没有包装任何对象。`Optional<T>` 类型被当作一种更安全的方式，用来替代类型`T`的引用，这种引用要么引用某个对象，要么为`null`。

### 获取`Optional`对象

有效地使用`Optional`的关键是要使用这样的方法：它在值不存在的情况下会产生一个可替代物，而只有在值存在的情况下才会使用这个值。

```java
String result = optionlString.orElse("");
String result = optionString.orElseGet(() -> System.getProperty("myapp.defauly"));
String result = optionString.orElseThrow(IllegalStateException::new);
```

### 消费`Optional`值

另一条使用可选值的策略是只有在其存在的情况下才消费该值。
`ifPresent` 方法会接受一个函数。如果可选值存在，那么它会被传递给该函数。否则，不会发生任何事情。

```java
optionValue.ifPresent(v -> process operation);
```

如果想要在可选值存在时执行一种动作，在可选值不存在时执行另一种动作，可调用：

```java
// void ifPresentOrElse(Consumer＜？ supe T> action, Runnable emptyAction)
optionValue.ifPresentOrElse(v -> System.out.println("found " + v),
	() -> logger.warning("not match"));
```

管道化`Optional`值

另一种有用的策略是保持`Optional`完整，使用`map`方法来转换`Optional`内部的值：

```java
Optional<String> transformed = optionString.map(String::toUppercase);
optionalValue.map(results::add);
```

### 不适合使用`Optional`值的方式

`get`方法会在`Optional`值存在的情况下获得其中包装的元素，或者在不存在的情况下抛出一个`NoSuchElementException`异常。

```java
if (value != null) {
    value.method();
}
```

不要在集合中放置`Optional`对象，并且不要将它们用作`map`的键。应该直接收集其中的值。

### 创建`Optional`值

有多个方法可以用于创建`Option`对象，包括`Optiona.of(result)`和`Optional.empty()` 。

`ofNullable`方法被用来作为可能出现的`null`值和可选值之间的桥梁。会在`obj`不为`null`的情况下返回`Optional.of(obj)` ，否则会返回`Optional.empty()` 。

### 用`flatMap`构建`Optional`值的函数

假设你有一个可以产生`Optional<T>`对象的方法`f` ， 并且目标类型T 具有一个可以产生`Optional<U>` 对象的方法`g`。如果它们都是普通的方法，那么你可以通过调用`s.f().g()` 来将它们组合起来。但是这种组合无法工作。

```java
Optional<U)
```

