package learnJava;

public class Student 
{
	int number;
	String name;
	Student()
	{}
	Student(int number, String name)
	{
		this.number = number;
		this.name = name;
		System.out.println("我的名字是：" + name + "学号是：" + number);
	}
}

class UniverStudent extends Student
{
	boolean maritalStatus;
	UniverStudent(int number, String name, boolean ms)
	{
		super(number, name);
		maritalStatus = ms;
		System.out.println("婚否 = " + maritalStatus);
	}
}

