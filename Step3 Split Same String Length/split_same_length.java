import java.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;

/*
		1.Read the length file of each input
		2.Compare two files and put the lesser one 
		2.Purge the smaller one on both sides of the two files
*/
public class split_same_length
{
    public static void main(String[] args)
    throws IOException
    {
			newlength();
    }  
	
	public static String[] split(String src, int len) 
	{
		String[] result = new String[(int)Math.ceil((double)src.length()/(double)len)];
		for (int i=0; i<result.length; i++)
			result[i] = src.substring(i*len, Math.min(src.length(), (i+1)*len));
		return result;
	}
	public static void newlength()
	{
		
		int[] length = new int[336];
		
		try{
		Scanner s1 = new Scanner(new File("SameLength_ab.txt"));
		Scanner s2 = new Scanner(new File("SameLength_ba.txt"));
		
		int[] part1 = new int[336];
		int[] part2 = new int[336];
		String p1= "";
		String p2= "";
		int p1_in;
		int p2_in;
		int i =0;
		while(s1.hasNextLine() && s2.hasNextLine()){
			p1 = p1 + s1.nextLine();
			p2 = p2 + s2.nextLine();
		}
		
		String[] p1_str = split(p1,31);
		String[] p2_str = split(p2,31);
		
		int p1_length = p1_str.length;
		int p2_length = p2_str.length;
		
		for(int l=0; l< p2_length; l++){  //change
			System.out.println(p2_str[l]);  //change
		}
	}
	catch(Exception e){
		
		}
		
}
}