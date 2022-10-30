package learnJava;

public class RedCowFarm
{
	static String farmName;
	RedCow cow;  // 内部声明对象
	RedCowFarm()
	{}
	RedCowFarm(String s)
	{
		cow = new RedCow(150, 112, 5000);
		farmName = s;
	}
	public void showCowMess()
	{
		cow.speak();
	}
	class RedCow  // 内部类的声明
	{
		String cowName = "红牛";
		int height, weight, price;
		RedCow(int h, int w, int p)
		{
			height = h;
			weight = w;
			price = p;
		}
		void speak()
		{
			System.out.printf("我是%s, 身高%d, 体重%d, 生活在%s", cowName, height, weight, farmName);
		}
	}
}