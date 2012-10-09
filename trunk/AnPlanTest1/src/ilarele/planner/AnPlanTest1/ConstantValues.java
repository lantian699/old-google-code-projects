package ilarele.planner.AnPlanTest1;

public class ConstantValues
{
	public final static int[] MONTH_30 = {3, 5, 8, 10};
	public final static int[] MONTH_31 = {0, 2, 4, 6, 7, 9, 11};
	public final static int MONTH_VARIABLE = 1;
	
	
	
	public static int getDays(int m, int y)
	{
		if (has31days(m))
			return 31;
		if (has30days(m))
			return 30;
		if (has28days(m, y))
			return 28;
		return 29;
	}
	public static boolean has30days(int crt)
	{
		for (int el : MONTH_30)
		{
			if (el == crt)
				return true;
		}
		return false;
	}
	
	public static boolean has31days(int crt)
	{
		for (int el : MONTH_31)
		{
			if (el == crt)
				return true;
		}
		return false;
	}
	
	public static boolean has28days(int m, int y)
	{
		if (m == MONTH_VARIABLE)
		{
			if (y % 4 != 0)
				return true;
		}
		return false;
	}
}
