# 第9章 Swing用户界面组件

## Swing和模型-视图-控制器设计模式

### 设计模式

设计模式就是一种方法，这种方法以一种结构化的方式展示专家们的心血。在“模型-视图-控制器”模式中，背景是显示信息和接收用户输入的用户界面系统。对于同一数据来说，可能需要同时更新多个可视化表示。解决方案是将这些功能分布到三个独立的交互组件：模型、视图和控制器。

模型-视图-控制器模式并非AWT和Swing设计中使用的唯一模式。下列是另外几种模式：

- 容器和组件**组合**模式
- 带滚动条的面板是**装饰**模式
- 布局管理器是**策略**模式

### 模式-视图-控制器模式

每个组件都有三个要素：

- 内容
- 外观
- 行为（对事件的反应）

模式-视图-控制器模式遵循面向对象的一个基本原则：限制一个对象拥有的功能数量。MVC模式实现三个独立的类：

- 模型（model）：存储内容。
- 视图（view）：显示内容。
- 控制器（control）：处理用户输入。

模型必须实现改变内容和查找内容的方法。模型是完全不可见的。显示存储在模型中的数据是视图的工作。

模型-视图-控制器模式的一个优点是一个模型可以有多个视图，其中每个视图可以显示全部内容的不同部分或不同形式。

控制器负责处理用户输入事件，如点击鼠标和敲击键盘。然后决定是否把这些事件转化成对模型或视图的改变。

在程序员使用Swing组件时，通常不需要考虑MVC体系结构。每个用户界面元素都有一个**包装器类**（如`JButton`或`JTextField` ）来保存模型和视图。

### Swing按钮的模型-视图-控制器分析

对于大多数组件来说，模型类将实现一个名字以`Model`结尾的接口，实现了此接口的类可以定义各种按钮的状态。例如，按钮实现`ButtonModel`接口。

```java
JButton button = new JButton("Blue");
ButtonModel model = button.getModel();
```

> 当使用Metal观感时， `JButton`类用`BasicButtonUl`类作为其视图，用`ButtonUIListener类`作为其控制器。通常，每个Swing组件都有一个相关的后缀为Ul的视图对象，但并不是所有的Swing组件都有专门的控制器对象。
>
> `JButton`仅仅是一个继承了`JComponent`的包装器，`JComponent`包含了一个`DefaultButtonModel`对象，一些视图数据（例如按钮标签和图标）和一个负责按钮视图的`BasicButtonUI`对象。

## 布局管理器概述

流布局管理器（flow layout manager）是**面板**的默认布局管理器。

通常，组件放置在容器中，布局管理器决定容器中的组件具体放置的位置和大小。由于`Container`类继承于`Component`类，所以容器也可以放置在另一个容器中。

```java
FlowLayout(int align, int hgap, int vgap);
```

### 边框布局

边框布局管理器（border layout manager）是每个`JFrame`的内容
窗格的默认布局管理器。

先放置边缘组件，剩余的可用空间由中间组件占据。当容器被缩放时，边缘组件的厚度不会改变，而中部组件的大小会发生变化。

与流布局不同，边框布局会扩展所有组件的尺寸以便填满可用空间
件的最佳尺寸）。解决这个问题的常见方法是使用另外一个面板（ panel）。

```java
JPanel panel = new JPanel();
panel.add(yellowButton);
panel.add(blueButton);
panel.add(redButton);
frame.add(panel, BorderLayout.SOUTH);
```

### 网格布局

网格布局像电子数据表一样，按行列排列所有的组件。不过，它的每个单元大小相同。

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CalculatorFrame frame = new CalculatorFrame();
            frame.setTitle("Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
class CalculatorFrame extends JFrame {
    public CalculatorFrame() {
        add(new CalculatorPanel());
        pack(); // 缩放窗口时，将组件调整至最佳尺寸
        try { // 设置观感
            UIManager.setLookAndFeel(plafName);
            SwingUtilities.updateComponentTreeUI(CalculatorFrame.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private String plafName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
}

class CalculatorPanel extends JPanel {
    private JButton display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;

    public CalculatorPanel() {
        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start = true;

        display = new JButton("0");
        display.setEnabled(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        InsertAction insert = new InsertAction();
        CommandAction command = new CommandAction();

        // 在 4 * 4 网格布局按钮
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        add(panel, BorderLayout.CENTER);
    }

    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(button);
    }

    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String input = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            // System.out.println(command);
            if (start) {
                if (command.equals("-")) { // 负数
                    display.setText(command);
                    start = false;
                } else
                    lastCommand = command;
            } else {
                calculate(Double.parseDouble(display.getText())); // 处理负数
                lastCommand = command;
                start = true; // 可获取命令状态
            }
        }
    }

    public void calculate(double x) {
        if (lastCommand.equals("+"))
            result += x;
        else if (lastCommand.equals("-"))
            result -= x;
        else if (lastCommand.equals("*"))
            result *= x;
        else if (lastCommand.equals("/"))
            result /= x;
        else if (lastCommand.equals("="))
            result = x;
        display.setText("" + result);
    }
}
```

![image-20230113034859269](typora-user-images/image-20230113034859269.png)

## 文本输入

文本域（`JTextField`）和文本区（`JTextArea`）组件用于获取文本输入．文本域只能接收单行文本的输入，而文本区能够接收多行文本的输入。`JPassword`也只能接收单行文本的输入，但不会将输入的内容显示出来。

### 文本域

把文本域添加到窗口的常用办法是将它添加到面板或者其他容器中。

> 使用`setColumns`方法改变了一个文本域的大小之后，需要调用包含这个文本框的容器的`revalidate`方法。
>
> ```java
> textField.setColumn(10);
> panel.revalidate();
> ```
>
> `revalidate`方法会重新计算容器内所有组件的大小，并且对它们重新进行布局。

### 标签和标签组件

标签是容纳文本的组件，它们没有任何的修饰（例如没有边缘），也不能响应用户输入。可以利用标签标识组件。

```java
JLabel(Icon icon);
JLabel label = new JLabel("User name: ", SwingConstants.RIGHT); // JLabel.RIGHT
```

> 从JDKl.3 开始， 可以在按钮、标签和菜单项土使用无格式丈本或HTML文本。但会影响观感，不建议使用。

### 密码域

密码域采用与常规的文本域相同的模型来存储数据，但是，它的视图却改为显示回显字符，而不是实际的字符。

```java
void setEchoChar(char echo);
char[] getPassword();
```

### 文本区

如果文本区的文本超出显示的范围，那么剩下的文本就会被剪裁掉。可以通过开启换行特性来避免裁剪过长的行：

```java
textArea.setLineWrap(true);
```

换行只是视觉效果：文档中的文本没有改变，在文本中并没有插入`\n`字符。

### 滚动窗格

在Swing中，文本区没有滚动条。如果需要滚动条，可以将文本区插入到滚动窗格。

```java
textArea = new JTextArea(8, 40);
JScrollPane scrollPane = new JSrcollPane(textArea);
```

```java
import javax.swing.*;
import java.awt.*;

public class TextComponentTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TextComponentFrame frame = new TextComponentFrame();
            frame.setTitle("TextComponentTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class TextComponentFrame extends JFrame {
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

    public TextComponentFrame() {
        JTextField textField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(new JLabel("User name: ", SwingConstants.RIGHT));
        northPanel.add(textField);
        northPanel.add(new JLabel("Password: ", SwingConstants.RIGHT));
        northPanel.add(passwordField);

        add(northPanel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        JButton insertButton = new JButton("Insert");
        southPanel.add(insertButton);
        insertButton.addActionListener(event -> textArea.append("User name: " + textField.getText() + "\nPassword: "
                + new String(passwordField.getPassword()) + "\n"));

        add(southPanel, BorderLayout.SOUTH);
        pack();
    }
}
```

![image-20230113035145740](typora-user-images/image-20230113035145740.png)

## 选择组件

### 复选框

复选框自动地带有标识标签。用户通过点击某个复选框来选择相应的选项，再点击则取消选取。

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CheckBoxTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CheckBoxFrame frame = new CheckBoxFrame();
            frame.setTitle("CheckBoxTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
class CheckBoxFrame extends JFrame {
    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;
    private static final int FONTSIZE = 24;
    private String plafName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public CheckBoxFrame() {
        try {
            UIManager.setLookAndFeel(plafName);
            SwingUtilities.updateComponentTreeUI(CheckBoxFrame.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        add(label, BorderLayout.CENTER);

        ActionListener listener = event -> { // 匿名函数
            int mode = 0;
            if (bold.isSelected())
                mode += Font.BOLD; // 添加模式
            if (italic.isSelected())
                mode += Font.ITALIC;
            label.setFont(new Font("Serif", mode, FONTSIZE));
        };

        JPanel buttonPanel = new JPanel();

        bold = new JCheckBox("Bold");
        bold.addActionListener(listener);
        bold.setSelected(true);
        buttonPanel.add(bold);

        italic = new JCheckBox("Italic");
        italic.addActionListener(listener);
        buttonPanel.add(italic);

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}
```

![image-20230113035800058](typora-user-images/image-20230113035800058.png)

### 单选按钮

为单选按钮组构造一个`ButtonGroup`的对象。然后，再将`JRadioButton`类型的对象添加到按钮组中。按钮组负责在新按钮被按下时，取消前一个被按下的按钮的选择状态。

```java
ButtonGroup group = new ButtonGroup();
JRadioButton smallButton = new JRadioButton("small", false);
group.add(smallButton);
JRadioButton mediumButton = ne JRadioButton("medium", true);
group.add(mediumButton);
```

> 单选按钮为圆形，选择圈内出现圆点表示被选择；
>
> 复选按钮为正方向，方形中出现对勾表示被选择；

`ButtonGroup` 类中有一个`getSelection`方法， 但是这个方法并不返回被选择的单选按钮， 而是返回附加在那个按钮上的模型`ButtonModel` 的引用，并没有什么实际的应用价值。可通过调用方法`buttonGroup.getSelection().getActionCommand()`获得当前选择的按钮的动作命令。

```java
public class RadioButtonTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            RadioButtonFrame frame = new RadioButtonFrame();
            frame.setTitle("RadioButtonTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class RadioButtonFrame extends JFrame {
    private JPanel buttonPanel;
    private ButtonGroup group;
    private JLabel label;
    private static final int DEFAULT_SIZE = 36;

    public RadioButtonFrame() {
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        add(label, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        group = new ButtonGroup();

        addRadioButton("Small", 8);
        addRadioButton("Medium", 12);
        addRadioButton("Large", 18);
        addRadioButton("Extra large", 36);

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    public void addRadioButton(String name, int size) {
        boolean selected = size == DEFAULT_SIZE;
        JRadioButton button = new JRadioButton(name, selected);
        group.add(button);
        buttonPanel.add(button);

        ActionListener listener = event -> label.setFont(new Font("Serif", Font.PLAIN, size));

        button.addActionListener(listener);
    }
}
```

![image-20230113043833147](typora-user-images/image-20230113043833147.png)