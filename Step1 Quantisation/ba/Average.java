import java.util.*;
import java.io.File;
import java.io.IOException;

public class Average
{
	//int sum = 0;
	int sum1 = 0;
	int sum2 = 0;
	
    public static void main(String[] args)
    throws IOException
    {
		double[] avg = new double[337];
		double[] variance = new double[337];
		double[] above = new double[337];
		double[] below = new double[337];
		int average = 0;
		for(int i=1;i<=336;i++)
		{
			String filename = "value"+i+".txt";
			Scanner textfile = new Scanner(new File(filename));
			avg[i] = filereader(textfile)/64;  // average
			Scanner textfile2 = new Scanner(new File(filename));
			variance[i] = getVariance(avg[i],textfile2); // variance calculation
			above[i] = avg[i] + (0.1 * variance[i]); // Upper limit
			below[i] = avg[i] - (0.1 * variance[i]); // Lower limit
			Scanner textfile3 = new Scanner(new File(filename));
			String match = getmatch(textfile3, above[i],below[i]);
			System.out.println(match);
		}
    }  

	static String getmatch(Scanner textfile, double above, double below)
	{
		int i=0;
		String match = "";
		while(i<64)
		{
			int nextInt = textfile.nextInt();
			if(nextInt < below)
			{
				match += "0";
			}
			if(nextInt > above)
			{
				match += "1";
			}
			i++;
		}
		return match;
	}
	
	static double filereader(Scanner textfile)     
	{         
		int i = 0;         
		double sum = 0;          
		while(i < 64)         
		{       
			int nextInt = textfile.nextInt();            
			sum = sum + nextInt;
			i++;         
		}    
		return sum; 		
	}
	
	static double getVariance(double average, Scanner textfile)
	{
		int i=0;
		double temp = 0;
		while(i<64)
		{
			int nextInt = textfile.nextInt();
			temp += (nextInt - average) * (nextInt - average);
			i++;
		}
		return temp/63;
	}
	
	/*
	static void filecompare(Scanner textfile)     
	{         
		int i = 0;          
		while(i < 64)         
		{       
			int nextInt = textfile.nextInt();            
			if(nextInt>sum1)
			{
					System.out.println("1");
			}
			if(nextInt<sum2)
			{
					System.out.println("0");
			}
			i++;         
		}     
	}
	
	*/


}