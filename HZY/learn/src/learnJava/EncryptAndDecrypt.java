package learnJava;

public class EncryptAndDecrypt 
{
	// 加密算法
	String entrypt(String srcStr, String password)
	{
		char[] p = password.toCharArray();
		int n = p.length;
		char[] c = srcStr.toCharArray();
		int m = c.length;
		
		for (int k = 0; k < m; k++)
		{
			int mina = c[k] + p[k%n];
			c[k] = (char)mina;
		}
		return new String(c);   // 返回密文
	}
	
	// 解密算法
	String detrypt(String srcStr, String password)
	{
		char[] p = password.toCharArray();
		int n = p.length;
		char[] c = srcStr.toCharArray();
		int m = c.length;
		for (int k = 0; k < m; k++)
		{
			int mina = c[k] - p[k%n];
			c[k] = (char)mina;
		}
		return new String(c);
	}
}
