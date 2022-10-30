package learnJava;

import java.util.StringTokenizer;

public class PriceToken 
{
	public double getPriceSum(String shoppingReceipt)
	{
		String regex = "[^0123456789.]+";  // 匹配非数字序列
		shoppingReceipt = shoppingReceipt.replaceAll(regex, "#");
		StringTokenizer analyze = new StringTokenizer(shoppingReceipt, "#");
		double sum = 0;
		while (analyze.hasMoreTokens())
		{
			String item = analyze.nextToken();
			double price = Double.parseDouble(item);
			sum += price;
		}
		return sum;
	}
	public int getGoodsAmount(String shoppingReceipt)
	{
		String regex = "[^0123456789.]+";
		shoppingReceipt = shoppingReceipt.replaceAll(regex, "#");
		StringTokenizer analyze = new StringTokenizer(shoppingReceipt, "#");
		int ammount = analyze.countTokens();
		return ammount;
	}
	public double getAveragePrice(String shoppingReceipt)
	{
		double priceSum = getPriceSum(shoppingReceipt);
		int goodAmmount = getGoodsAmount(shoppingReceipt);
		return priceSum / goodAmmount;
	}

}
