# 第06章 日期和时间API

## 基本概念

### 时区

时区有以下表示方式：

一种是以`GMT`或者`UTC`加时区偏移表示，例如：`GMT+08:00`或者`UTC+08:00`表示东八区。

`GMT`和`UTC`可以认为基本是等价的，只是`UTC`使用更精确的原子钟计时，每隔几年会有一个闰秒，我们在开发程序的时候可以忽略两者的误差，因为计算机的时钟在联网的时候会自动与时间服务器同步时间。

另一种是缩写，例如，`CST`表示`China Standard Time`，也就是中国标准时间。但是`CST`也可以表示美国中部时间`Central Standard Time USA`，因此，缩写容易产生混淆，我们尽量不要使用缩写。

最后一种是以洲／城市表示，例如，`Asia/Shanghai`，表示上海所在地的时区。特别注意城市名称不是任意的城市，而是由国际标准组织规定的城市。

### 本地化

在计算机中，通常使用`Locale`表示一个国家或地区的日期、时间、数字、货币等格式（习惯）。`Locale`由`语言_国家`的字母缩写构成，例如，`zh_CN`表示中文+中国，`en_US`表示英文+美国。语言使用小写，国家使用大写。

- zh_CN：2016-11-30
- en_US：11/30/2016

## `Date`和`Calendar`类

当我们用`System.out.println(n)`打印这个整数的时候，实际上`println()`这个方法在内部把`int`类型转换成`String`类型，然后打印出字符串`123400`。

```java
int n = 1234;
System.out.println(n);
System.out.println(Integer.toHexString(n));
```

日期和时间实际上是数据的展示格式。而这个“同一个时刻”在计算机中存储的本质上只是一个整数，我们称它为`Epoch Time`。

`Epoch Time`是计算从1970年1月1日零点（格林威治时区／GMT+00:00）到现在所经历的秒数。

```
1574208900 = 北京时间2019-11-20 8:15:00
           = 伦敦时间2019-11-20 0:15:00
           = 纽约时间2019-11-19 19:15:00
```

`Epoch Time`又称为**时间戳**，在不同的编程语言中，会有几种存储方式：

- 以秒为单位的整数：1574208900，缺点是精度只能到秒；
- 以毫秒为单位的整数：1574208900123，最后3位表示毫秒数；
- 以秒为单位的浮点数：1574208900.123，小数点后面表示零点几秒。

Java程序中，时间戳通常是用`long`表示的毫秒数，即：

```java
long t = 1574208900123L;
```

可以使用`System.currentTimeMillis()`，这是Java程序获取时间戳最常用的方法。

### 标准库API

我们再来看一下Java标准库提供的API。Java标准库有两套处理日期和时间的API：

- 一套定义在`java.util`这个包里面，主要包括`Date`、`Calendar`和`TimeZone`这几个类；
- 一套新的API是在Java 8引入的，定义在`java.time`这个包里面，主要包括`LocalDateTime`、`ZonedDateTime`、`ZoneId`等。

### `Date`类

`java.util.Date`是用于表示一个日期和时间的对象，注意与`java.sql.Date`区分，后者用在数据库中。

```java
public class Date implements Serializable, Cloneable, 
Comparable, Comparable<Date> {
    private transient long fastTime;
    ...
}
```

```java
// 获取当前时间
Date date = new Date();
// 必须+1900
System.out.println(date.getYear() + 1900);
// 0~11，必须+1
System.out.println(date.getMonth() + 1);
System.out.println(date.getDate());
System.out.println(date.toString());
// 转化为GMT时区
System.out.println(date.toGMTString());
// 转化为本地时区
System.out.println(date.toLocaleString());
```

```
2023
3
28
Tue Mar 28 16:50:12 CST 2023
28 Mar 2023 08:50:12 GMT
2023年3月28日 下午4:50:12
```

可以使用`SimpleDateFormat`对一个`Date`进行转换。它用预定义的字符串表示格式化（`yyyy-MM-dd HH:mm:ss）`：

```java
Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(date));
```

```
2023-03-28 16:53:38
```

Java的格式化预定义了许多不同的格式：

```java
Date date = new Date();
var sdf = new SimpleDateFormat("E MMM dd, yyyy");
System.out.println(sdf.format(date));
```

```java
周二 3月 28, 2023
```

`Date`对象有几个严重的问题：它不能转换时区，除了`toGMTString()`可以按`GMT+0:00`输出外，Date总是以当前计算机系统的默认时区为基础进行输出。此外，我们也很难对日期和时间进行加减。

### `Calendar`类

`Calendar`可以用于获取并设置年、月、日、时、分、秒，它和`Date`比，主要多了一个可以做简单的日期和时间运算的功能。

```java
Calendar c = Calendar.getInstance();
int y = c.get(Calendar.YEAR);
int m = 1 + c.get(Calendar.MONTH);
int d = c.get(Calendar.DAY_OF_MONTH);
int w = c.get(Calendar.DAY_OF_WEEK);
int hh = c.get(Calendar.HOUR_OF_DAY);
int mm = c.get(Calendar.MINUTE);
int ss = c.get(Calendar.SECOND);
int ms = c.get(Calendar.MILLISECOND);
System.out.println(y + "-" + m + "-" + d + " " + w + " " + hh + ":" + mm + ":" + ss + "." + ms);
```

注意到`Calendar`获取年月日这些信息变成了`get(int field)`，返回的年份不必转换，返回的月份仍然要加1，返回的星期要特别注意，`1`~`7`分别表示周日，周一~周六。

`Calendar`只有一种方式获取，即`Calendar.getInstance()`，而且一获取到就是当前时间。

```java
Calendar c = Calendar.getInstance();
// 清除
c.clear();
c.set(Calendar.YEAR, 2023);
```

利用`Calendar.getTime()`可以将一个`Calendar`对象转换成`Date`对象，然后就可以用`SimpleDateFormat`进行格式化。

### `TimeZone`类

`Calendar`和`Date`相比，它提供了时区转换的功能。时区用`TimeZone`对象表示：

```java
TimeZone tzDefault = TimeZone.getDefault();
TimeZone tzGMT9 = TimeZone.getTimeZone("GMT+09.00");
TimeZone tzNY = TimeZone.getTimeZone("America/New_York");
System.out.println(TimeZone.getDefault());
```

```
sun.util.calendar.ZoneInfo[id="Asia/Shanghai",offset=28800000,dstSavings=0,useDaylight=false,transitions=29,lastRule=null]
```

```java
TimeZone tzDefault = TimeZone.getDefault();
Calendar c = Calendar.getInstance();
c.setTimeZone(tzDefault);
c.set(2023, 10 /* 11 月 */, 20, 9, 6, 0);
System.out.println(c.getTime());
```

1. 清除所有字段；
2. 设定指定时区；
3. 设定日期和时间；
4. 创建`SimpleDateFormat`并设定目标时区；
5. 格式化获取的`Date`对象

因此，本质上时区转换只能通过`SimpleDateFormat`在显示的时候完成。

## `LocalDateTime`类

从Java 8开始，`java.time`包提供了新的日期和时间API，主要涉及的类型有：

- 本地日期和时间：`LocalDateTime`，`LocalDate`，`LocalTime`；
- 带时区的日期和时间：`ZonedDateTime`；
- 时刻：`Instant`；
- 时区：`ZoneId`，`ZoneOffset`；
- 时间间隔：`Duration`。

以及一套新的用于取代`SimpleDateFormat`的格式化类型`DateTimeFormatter`。

和旧的API相比，新API严格区分了时刻、本地日期、本地时间和带时区的日期时间，并且，对日期和时间进行运算更加方便。

此外，新API修正了旧API不合理的常量设计：

- Month的范围用1~12表示1月到12月；
- Week的范围用1~7表示周一到周日。

新API的类型几乎全部是不变类型（和String类似），可以放心使用不必担心被修改。

### `LocalDateTime`类

```java
LocalDate d = LocalDate.now();
LocalDateTime dt = LocalDateTime.now();
// 严格按照ISO 8601哥哥是打印
System.out.println(d);
System.out.println(dt);
```

```
2023-03-28
2023-03-28T17:31:20.453820
```

为了保证获取到同一时刻的日期和时间，可以改写如下：

```java
LocalDateTime dt = LocalDateTime.now();
LocalDate d = dt.toLocalDate();
```

通过指定的日期和时间创建`LocalDateTime`可以通过`of()`方法：

```java
LocalDate d2 = LocalDate.of(2023, 11, 30);
LocalTime t2 = LocalTime.of(15, 16, 17);
LocalDateTime dt2 = LocalDateTime.of(2023, 11, 30, 15, 16, 17);
LocalDateTime dt3 = LocalDateTime.of(d2, t2);
```

将字符串转换为`LocalDateTime`就可以传入标准格式：

```java
LocalDateTime dt = LocalDateTime.parse("2023-03-29T15:16:17");
System.out.println(dt);
```

### `DateTimeFormatter`类

如果要自定义输出的格式，或者要把一个非ISO 8601格式的字符串解析成`LocalDateTime`，可以使用新的`DateTimeFormatter`：

```java
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
System.out.println(dtf.format(LocalDateTime.now()));
```

##  `LocalDateTime`

从Java 8开始，`java.time`包提供了新的日期和时间API，主要涉及的类型有：

- 本地日期和时间：`LocalDateTime`，`LocalDate`，`LocalTime`；
- 带时区的日期和时间：`ZonedDateTime`；
- 时刻：`Instant`；
- 时区：`ZoneId`，`ZoneOffset`；
- 时间间隔：`Duration`。

以及一套新的用于取代`SimpleDateFormat`的格式化类型`DateTimeFormatter`。

和旧的API相比，新API严格区分了时刻、本地日期、本地时间和带时区的日期时间，并且，对日期和时间进行运算更加方便。

此外，新API修正了旧API不合理的常量设计：

- Month的范围用1~12表示1月到12月；
- Week的范围用1~7表示周一到周日。

最后，新API的类型几乎全部是不变类型（和String类似），可以放心使用不必担心被修改。

### `LocalDateTime`类

我们首先来看最常用的`LocalDateTime`，它表示一个本地日期和时间：

```
import java.time.*;
```

 Run

本地日期和时间通过now()获取到的总是以当前默认时区返回的，和旧API不同，`LocalDateTime`、`LocalDate`和`LocalTime`默认严格按照[ISO 8601](https://www.iso.org/iso-8601-date-and-time-format.html)规定的日期和时间格式进行打印。

上述代码其实有一个小问题，在获取3个类型的时候，由于执行一行代码总会消耗一点时间，因此，3个类型的日期和时间很可能对不上（时间的毫秒数基本上不同）。为了保证获取到同一时刻的日期和时间，可以改写如下：

```
LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
LocalDate d = dt.toLocalDate(); // 转换到当前日期
LocalTime t = dt.toLocalTime(); // 转换到当前时间
```

反过来，通过指定的日期和时间创建`LocalDateTime`可以通过`of()`方法：

```
// 指定日期和时间:
LocalDate d2 = LocalDate.of(2019, 11, 30); // 2019-11-30, 注意11=11月
LocalTime t2 = LocalTime.of(15, 16, 17); // 15:16:17
LocalDateTime dt2 = LocalDateTime.of(2019, 11, 30, 15, 16, 17);
LocalDateTime dt3 = LocalDateTime.of(d2, t2);
```

因为严格按照ISO 8601的格式，因此，将字符串转换为`LocalDateTime`就可以传入标准格式：

```
LocalDateTime dt = LocalDateTime.parse("2019-11-19T15:16:17");
LocalDate d = LocalDate.parse("2019-11-19");
LocalTime t = LocalTime.parse("15:16:17");
```

注意ISO 8601规定的日期和时间分隔符是`T`。标准格式如下：

- 日期：yyyy-MM-dd
- 时间：HH:mm:ss
- 带毫秒的时间：HH:mm:ss.SSS
- 日期和时间：yyyy-MM-dd'T'HH:mm:ss
- 带毫秒的日期和时间：yyyy-MM-dd'T'HH:mm:ss.SSS

### `DateTimeFormatter`类

如果要自定义输出的格式，或者要把一个非ISO 8601格式的字符串解析成`LocalDateTime`，可以使用新的`DateTimeFormatter`：

```java
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
System.out.println(dtf.format(LocalDateTime.now()));

// 用自定义格式解析
LocalDateTime dt2 = LocalDateTime.parse("2019/11/30 15:16:17", dtf);
System.out.println(dt2);
```

另一种创建`DateTimeFormatter`的方法是，传入格式化字符串时，同时指定`Locale`：

```java
DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("E, yyyy-MMMM-dd HH:mm", Locale.CANADA);
DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("E, yyyy-MMMM-dd HH:mm", Locale.CHINA);
System.out.println(formatter1.format(LocalDateTime.now()));
System.out.println(formatter2.format(LocalDateTime.now()));
```

```
Wed., 2023-March-29 14:52
周三, 2023-三月-29 14:52
```

`LocalDateTime`提供了对日期和时间进行加减的非常简单的链式调用：

```java
LocalDateTime dt = LocalDateTime.now();
System.out.println(dt);
// +5天-3小时
LocalDateTime dt2 = dt.plusDays(5).minusHours(3);
System.out.println(dt2);
```

```
2023-03-29T14:12:26.709757900
2023-04-03T11:12:26.709757900
```

月份加减会自动调整日期，对日期和时间进行调整则使用`withXxx()`方法：

```java
LocalDateTime dt2 = dt.withHour(2);
```

要判断两个`LocalDateTime`的先后，可以使用`isBefore()`、`isAfter()`方法：

```java
System.out.println(LocalDateTime.now().isBefore(LocalDateTime.now())); // false
```

### `Duration`和`Period`类

`Duration`表示两个时刻之间的时间间隔。`Period`表示两个日期之间的天数：

```java
LocalDateTime start = LocalDateTime.of(2019, 11, 19, 8, 15, 0);
LocalDateTime end = LocalDateTime.of(2020, 1, 9, 19, 25, 30);
Duration d = Duration.between(start, end);
// PT1235H10M30S
System.out.println(d);

Period p = LocalDate.of(2019, 11, 19).until(LocalDate.of(2020, 1, 9));
// P1M21D
System.out.println(p);
```

## `ZoneDateTime`类

可以简单地把`ZonedDateTime`理解成`LocalDateTime`加`ZoneId`。`ZoneId`是`java.time`引入的新的时区类。

要创建一个`ZonedDateTime`对象，有以下几种方法，一种是通过`now()`方法返回当前时间：

```java
ZonedDateTime zdt = ZonedDateTime.now();
ZonedDateTime zdt1 = ZonedDateTime.now(ZoneId.of("America/New_York"));
System.out.println(zdt);
System.out.println(zdt1);
```

```
2023-03-29T14:29:51.629236700+08:00[Asia/Shanghai]
2023-03-29T02:29:51.630236400-04:00[America/New_York]
```

另一种方式是通过给一个`LocalDateTime`附加一个`ZoneId`，就可以变成`ZonedDateTime`：

```java
LocalDateTime ldt = LocalDateTime.now();
ZonedDateTime zdt = ldt.atZone(ZoneId.of("America/New_York"));
System.out.println(ldt);
System.out.println(zdt);
```

以这种方式创建的`ZonedDateTime`，它的日期和时间与`LocalDateTime`相同，但附加的时区不同，因此是两个不同的时刻.

### 时区转换

要转换时区，首先我们需要有一个`ZonedDateTime`对象，然后，通过`withZoneSameInstant()`将关联时区转换到另一个时区，转换后日期和时间都会相应调整。

```java
ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
ZonedDateTime zdt2 = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
System.out.println(zdt);
System.out.println(zdt2);
```

有了`ZonedDateTime`，将其转换为本地时间：

```java
ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("America/New_York"));
LocalDateTime ldt = zdt.toLocalDateTime();
System.out.println(zdt);
System.out.println(ldt);
```

## `Instant`类

计算机存储的当前时间，本质上只是一个不断递增的整数。Java提供的`System.currentTimeMillis()`返回的就是以毫秒表示的当前时间戳。

这个当前时间戳在`java.time`中以`Instant`类型表示，我们用`Instant.now()`获取当前时间戳，效果和`System.currentTimeMillis()`类似：

```java
Instant now = Instant.now();
System.out.println(now.getEpochSecond());
System.out.println(now.toEpochMilli());
```

实际上，`Instant`内部只有两个核心字段：

```javs
public final class Instant implements ... {
    private final long seconds;
    private final int nanos;
}
```

可见，对于某一个时间戳，给它关联上指定的`ZoneId`，就得到了`ZonedDateTime`，继而可以获得了对应时区的`LocalDateTime`。

```ascii
┌─────────────┐
│LocalDateTime│────┐
└─────────────┘    │    ┌─────────────┐
                   ├───>│ZonedDateTime│
┌─────────────┐    │    └─────────────┘
│   ZoneId    │────┘           ▲
└─────────────┘      ┌─────────┴─────────┐
                     │                   │
                     ▼                   ▼
              ┌─────────────┐     ┌─────────────┐
              │   Instant   │<───>│    long     │
              └─────────────┘     └─────────────┘
```

### 旧API转新API

如果要把旧式的`Date`或`Calendar`转换为新API对象，可以通过`toInstant()`方法转换为`Instant`对象，再继续转换为`ZonedDateTime`：

```java
Instant instant = new Date().toInstant();
Calendar calendar = Calendar.getInstance();
ZonedDateTime zdt = instant.atZone(calendar.getTimeZone().toZoneId());
System.out.println(zdt);
```

### 在数据库中存储日期和时间

除了旧式的`java.util.Date`，我们还可以找到另一个`java.sql.Date`，它继承自`java.util.Date`，但会自动忽略所有时间相关信息。

在数据库中，也存在几种日期和时间类型：

- `DATETIME`：表示日期和时间；
- `DATE`：仅表示日期；
- `TIME`：仅表示时间；
- `TIMESTAMP`：和`DATETIME`类似，但是数据库会在创建或者更新记录的时候同时修改`TIMESTAMP`。

实际上，在数据库中，我们需要存储的最常用的是时刻（`Instant`），因为有了时刻信息，就可以根据用户自己选择的时区，显示出正确的本地时间。所以，最好的方法是直接用长整数`long`表示，在数据库中存储为`BIGINT`类型。

```java
public static void main(String[] args) {
    long ts = 1574208900000L;
    System.out.println(timestampToString(ts, Locale.CHINA, "Asia/Shanghai"));
    System.out.println(timestampToString(ts, Locale.US, "America/New_York"));
}

static String timestampToString(long epochMilli, Locale lo, String zoneId) {
    Instant ins = Instant.ofEpochMilli(epochMilli);
    DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
    return f.withLocale(lo).format(ZonedDateTime.ofInstant(ins, ZoneId.of(zoneId)));
}
```

```
2019年11月20日 上午8:15
Nov 19, 2019, 7:15 PM
```

