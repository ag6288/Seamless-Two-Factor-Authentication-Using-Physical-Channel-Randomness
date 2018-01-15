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
public class Cut_length_of_each_string
{
    public static void main(String[] args)
    throws IOException
    {
			newlength();
    }  
	
	public static void newlength()
	{
		
		int[] length = new int[336];
		
		try{
		Scanner s1 = new Scanner(new File("length_ab.txt"));
		Scanner s2 = new Scanner(new File("length_ba.txt"));
		
		int[] part1 = new int[336];
		int[] part2 = new int[336];
		String p1= "";
		String p2= "";
		int p1_in;
		int p2_in;
		int i =0;
		while(s1.hasNextLine() && s2.hasNextLine()){
			p1 = s1.nextLine();
			p2 = s2.nextLine();
			p1_in = Integer.parseInt(p1);
			p2_in = Integer.parseInt(p2);
			int diff = p1_in - p2_in;
			if(diff > 0){
				length[i] = p2_in;
				//System.out.println(length[i]);
			}
			if(diff <0){
				length[i] = p1_in;
				//System.out.println(length[i]);
			}
			if(diff == 0){
				length[i] = p1_in;
				//System.out.println(length[i]);
			}
			i++;
		}
	}
	catch(Exception e){
		
		}
		try{
		Scanner s3 = new Scanner(new File("output_ab.txt"));
		Scanner s4 = new Scanner(new File("output_ba.txt"));
		int j=0;
		while(s4.hasNextLine()){  //change
			int leng = length[j];
			String str = s4.nextLine(); //change
			char[] charArray = str.toCharArray();
			int m =0;
			String ss = "";
			while(m<length[j]){
			  ss = ss+charArray[m];
			  m++;
			}
			System.out.println(ss);
			j++;
		}
		}catch(Exception e){
		}
}
}