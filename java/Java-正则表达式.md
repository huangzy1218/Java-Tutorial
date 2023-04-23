# 正则表达式

## 匹配规则

### 常用匹配

`\`也是Java字符串的转义字符，两个`\\`实际上表示的是一个`\`。

如果想匹配非ASCII字符，例如中文，那就用`\u####`的十六进制表示，例如：`a\u548cc`匹配字符串`"a和c"`，中文字符`和`的Unicode编码是`548c`。

`.`匹配一个字符且仅限一个字符

`\d`可匹配数字

`\w`可匹配一个字母、数字或下划线

`\s`匹配空格，包括tab（`\t`）

`\D`匹配非数字，`\W`匹配非字符，`\S`匹配非空格。

```java
String regex = "a.c";
System.out.println("abc".matches(regex));
System.out.println("a/c".matches(regex));
```

### 重复匹配

修饰符`*`可以匹配任意个字符，包括0个字符。

修饰符`+`可以匹配至少一个字符。我们用`A\d+`可以匹配。

修饰符`?`匹配0或1个字符

修饰符`\{n}`可以实现精确匹配。

`{n,m}`可以实现范围`[n, m]`匹配

## 复杂匹配规则

### 匹配开头和结尾

多行匹配时，我们用`^`表示开头，`$`表示结尾。例如，`^A\d{3}$`，可以匹配`"A001"`、`"A380"`。

### 匹配指定范围

使用`[...]`可以匹配范围内的字符，例如，`[123456789]`可以匹配`1`~`9`，这样就可以写出上述电话号码的规则：`[123456789]\d{6,7}`。简化为`[1-9]/d{6,7}`。

匹配大小写不限的十六进制数：`[0-9a-fA-F]`。

### 或规则匹配

用`|`连接的两个正则规则是*或*规则，例如，`AB|CD`表示可以匹配`AB`或`CD`。

### 使用括号

用`(...)`把子规则包含起来：`learn\\s(java|php|go)`

## 分组匹配

引入`java.util.regex`包，用`Pattern`对象匹配，匹配后获得一个`Matcher`对象，如果匹配成功，就可以直接从`Matcher.group(index)`返回子串：

```java
Pattern pattern = Pattern.compile("(\\d{3,4})\\-(\\d{7,8})");
Matcher matcher = pattern.matcher("010-12345678");
if (matcher.matches()) {
    String g1 = matcher.group(1);
    String g2 = matcher.group(2);
    System.out.println(g1);
    System.out.println(g2);
} else {
    System.out.println("匹配失败!");
}
```

### `Pattern`

`String.matches()`方法内部调用`Pattern`和`Matcher`类的方法。反复使用`String.matches()`对同一个正则表达式进行多次匹配效率较低，因为每次都会创建出一样的`Pattern`对象。

## 非贪婪匹配

正则表达式匹配默认使用贪婪匹配，可以使用`?`表示对某一规则进行非贪婪匹配。

给定一个字符串表示的数字，判断该数字末尾`0`的个数。

```java
Pattern pattern = Pattern.compile("(\\d+?)(0*)");
Matcher matcher = pattern.matcher("12340000");
if (matcher.matches()) {
    System.out.println("group1=" + matcher.group(1));
}
```

## 搜索和替换

### 分割字符串

使用合适的正则表达式，可以消除不规范输入，提取规范的字符串。

```java
String regex = "[\\,\\;\\s]+";
String[] strings = "a, b; c  ".split(regex);
for (String string : strings) {
    System.out.println(string);
}
```

### 搜索字符串

```java
String s = "the quick brown fox jumps over the lazy dog.";
Pattern pattern = Pattern.compile("\\w+o\\w+");
Matcher matcher = pattern.matcher(s);
while (matcher.find()) {
    String sub = s.substring(matcher.start(), matcher.end());
    System.out.println(sub);
}
```

### 替换字符串

使用正则表达式替换字符串可以直接调用`String.replaceAll(regex, s)`。

```java
String s = "The    quick\t\t    brown fox    jumps over   the lazy\tdog";
String r = s.replaceAll("\\s+", " ");
System.out.println(r);
```

### 反向引用

```java
String s = "the quick brown fox jumps over the lazy dog.";
// $1表示第一个匹配组
String r = s.replaceAll("\\s([a-z]{4})\\s", " <b>$1<b> ");
System.out.println(r);
```

### 模板引擎

1. 它从输入序列中读取字符，从追加位置开始，并将它们附加到给定的字符串构建器。  它在读取上一个匹配之前的最后一个字符后停止，即索引号为`start() - 1  `的字符。 
2. 它将给定的替换字符串附加到字符串构建器。 
3. 它将此匹配器的追加位置设置为匹配的最后一个字符的索引加上一个，即`end()`  。 

```java
public class Template {
    private final String template;
    private final Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");
    public Template(String template) {
        this.template = template;
    }

    public String render(Map<String, Object> data) {
        Matcher matcher = pattern.matcher(template);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            Object result = data.get(matcher.group(1));
            if (result != null) {
                matcher.appendReplacement(sb, result.toString());
            }
        }
        // appendReplacement方法的一次或多次调用之后调用，以便复制输入序列的其余部分，在本例中为!
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        Template template1 = new Template("Hello, ${name}! You are learning ${lang}!");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Tim");
        map.put("lang", "Java");
        String result = template1.render(map);
        System.out.println(result);
    }
}
```

