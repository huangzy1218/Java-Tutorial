package learnJava;

public class Rational 
{
	int numerator = 1;  // 分子
	int denominator = 1;  // 分母
	void setNumerator(int a)
	{
		int c = gcd(Math.abs(a), denominator);
		numerator = a / c;
		denominator = denominator / c;
		if (denominator < 0 && numerator < 0)
		{
			numerator = -numerator;
			denominator = -denominator;
		}
	}
	void setDenominator(int b)
	{
		int c = gcd(Math.abs(b), numerator);
		numerator = numerator / c;
		denominator = b / c;
		if (denominator < 0 && numerator < 0)
		{
			numerator = -numerator;
			denominator = -denominator;
		}
	}
	int getNumerator()
	{
		return numerator;
	}
	int getDonominator()
	{
		return denominator;
	}
	int gcd(int a, int b)  // 求a和b最大公约数
	{
		if (a == 0)
		{
			return 1;
		}
		if (a > b)
		{
			int c = a;
			a = b;
			b = c;
		}
		int r = a % b;
		while (r != 0)
		{
			a = b;
			b = r;
			r = a % b;
		}
		return b;
	}
	Rational add(Rational r)  // 加法运算
	{
		int a = r.getNumerator();
		int b = r.getDonominator();
		int newNumerator = numerator * b + denominator * a;
		int newDenominator = denominator * b;
		Rational result = new Rational();
		result.setNumerator(newNumerator);
		result.setDenominator(newDenominator);
		
		return result;
	}
	Rational sub(Rational r)  // 减法运算
	{
		int a = r.getNumerator();
		int b = r.getDonominator();
		int newNumerator = numerator * b - denominator * a;
		int newDenominator = denominator * b;
		Rational result = new Rational();
		result.setNumerator(newNumerator);
		result.setDenominator(newDenominator);
		
		return result;		
	}
	Rational muti(Rational r)
	{
		int a = r.getNumerator();
		int b = r.getDonominator();
		int newNumerator = numerator * a;
		int newDenominator = denominator * b;
		Rational result = new Rational();
		result.setNumerator(newNumerator);
		result.setDenominator(newDenominator);
		
		return result;		
	}
	Rational div(Rational r)
	{
		int a = r.getNumerator();
		int b = r.getDonominator();
		int newNumerator = numerator * b;
		int newDenominator = denominator * a;
		Rational result = new Rational();
		result.setNumerator(newNumerator);
		result.setDenominator(newDenominator);
		
		return result;		
	}
}
