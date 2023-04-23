# Java-浅拷贝和深拷贝

Java 对象拷贝是为对象赋值的一种方式，是创建一个和原对象相同的对象。

## 引用

对象而言，其实赋值的只是这个对象的引用，即将原对象的引用传递过去，但是他们实际上还是指向的**同一个对象**。

```java
public static void main(String[] args) {

  int i1 = 10;
  int i2 = i1; // 基本数据类型的拷贝，拷贝值
  System.out.println("i2 = " + i2);

  Food milk = new Food("milk", 1, "fragrance");
  Food food = milk; 
  System.out.printf("food = " + food);
  System.out.println("milk = " + milk); // milk 和 food 都指向同一个堆内存对象
}
```

## 关于浅拷贝和深拷贝

浅拷贝和深拷贝其实就是在引用基础上来做区分的，如果在拷贝的时候，只对基本数据类型进行拷贝，对引用数据类型只是进行了引用的传递，没有真正的创建一个新的对象，这种拷贝方式就认为是浅拷贝。反之，在对引用数据类型进行拷贝的时候，**创建了一个新的对象，并且复制其内的成员变量**，这种拷贝方式就被认为是深拷贝。

### 浅拷贝

是在需要拷贝的类上**实现 `Cloneable` 接口并重写其 `clone()` 方法**就可以实现浅拷贝（Shallow copy）。

```java
public class Food implements Cloneable {
  
  ...
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }      
  ...
}

Food milk = new Food("milk", 1, "fragrance");
Food food = (Food) milk.clone();
System.out.println("milk = " + milk);
System.out.println("food = " + food);
```

```java
milk = com.cxuan.objectclone.Food@3cd1a2f1
food = com.cxuan.objectclone.Food@4d7e1886
```

> 如果类实现了 Cloneable 接口，再调用 Object 的 clone() 方法**可以合法地对该类实例进行按字段复制**。
>
> 如果在没有实现 Cloneable 接口的实例上调用 Object 的 clone() 方法，则会导致抛出`CloneNotSupporteddException`。

### 深拷贝

在进行对象拷贝的基础上，对**对象的成员变量也依次拷贝**的方式被称为深拷贝（Deep copy）。

```java
public class Food implements Cloneable{
    String name;
    int num;
    String taste;
    Drink drink;

    public Food(String name, int num, String taste,Drink drink) {
        this.name = name;
        this.num = num;
        this.taste = taste;
        this.drink = drink;
    }

    get and set...

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Food food = (Food)super.clone();
        food.drink = (Drink) drink.clone();
        return super.clone();
    }

    @Override
    public String toString() {
        return "Food[" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", taste='" + taste + '\'' +
                ", drink=" + drink +
                ']';
    }
}
```

### 序列化

使用序列化的方式主要是使用 `Serializable` 接口，这种方式还以解决多层拷贝的问题，**多层拷贝就是引用类型里面又有引用类型**，层层嵌套下去。使用 Serializable 的关键代码如下：

```java
public Person clone() {
  	Person person = null;
  	try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        // 将流序列化成对象
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        person = (Person) ois.readObject();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return person;
}
```

使用序列化可以实现深拷贝，它的原理是将二进制字节流内容写到一个文本或字节数组，然后是从这个文本或者字节数组中读取数据，原对象写入这个文本或者字节数组后再拷贝给 clone 对象，原对象的修改不会影响 clone 对象，因为 clone 对象是从文本或者字节数组中读取的。

### 如何选择拷贝方式

* 如果对象的属性都是基本数据类型，那么可以使用浅拷贝。

* 如果对象有引用类型，那就要基于具体的需求来选择浅拷贝还是深拷贝。
* 如果对象嵌套层数比较多，推荐使用 Serializable 接口实现深拷贝。

* 如果对象引用任何时候都不会被改变，那么没必要使用深拷贝，只需要使用浅拷贝。如果对象引用经常改变，那么就要使用深拷贝。

### 其他拷贝方式

数组的拷贝，你可以使用 `Arrays.copyof` 实现数组拷贝，还可以使用默认的 clone 进行拷贝，不过这两者都是浅拷贝。

集合也可以实现拷贝，因为集合的底层就使用的是数组，所以用法也是一样的。