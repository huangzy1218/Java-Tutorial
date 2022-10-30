package learnJava;

public class TV 
{
	double price;
	public void setPrice(int m)
	{
		price = m;
	}
	public String toString()
	{
		String oldStr = super.toString();
		return oldStr + "\n这是电视机，价格是" + price;
	}
}

