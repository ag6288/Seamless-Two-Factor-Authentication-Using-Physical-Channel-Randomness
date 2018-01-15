import java.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import java.io.FileInputStream;

/*
		1.Read the length file of each input
		2.Compare two files and put the lesser one 
		2.Purge the smaller one on both sides of the two files
*/
public class get_length 
{
    public static void main(String[] args) throws IOException
    {
	


  byte[] chunks  = null;
        BufferedReader  in = 
        new BufferedReader (new InputStreamReader(new FileInputStream("output_ba.txt"),"UTF-8"));
        String eachLine  = "";  
        while( (eachLine = in.readLine()) != null) 
        {
            chunks = eachLine.getBytes("UTF-8");
            System.out.println(chunks.length);
        } 
		
	}
}
