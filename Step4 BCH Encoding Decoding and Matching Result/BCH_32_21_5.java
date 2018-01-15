import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.util.Random;  
import java.util.Scanner;
import java.io.File;
  
public class BCH_32_21_5 {  
    int m = 5, n = 31, k = 21, t = 2, d = 5;  
    int length = 31;  
    int p[] = new int[6];  
    int alpha_to[] = new int[32];  
    int index_of[] = new int[32];  
    int g[] = new int[11];  
    int recd[] = new int[31];  
    int data[] = new int[21];  
    int bb[] = new int[11];  
    int numerr, decerror = 0;  
    int errpos[] = new int[32];  
    int seed;  
	static int match = 0;
	int before = 0;
  
    void read_p() {  
        p[0] = p[2] = p[5] = 1;  
        p[1] = p[3] = p[4] = 0;  
    }  
  
    void generate_gf() {  
        int i, mask;  
        mask = 1;  
        alpha_to[m] = 0;  
        for (i = 0; i < m; i++) {  
            alpha_to[i] = mask;  
            index_of[alpha_to[i]] = i;  
            if (p[i] != 0)  
                alpha_to[m] ^= mask;  
            mask <<= 1;  
        }  
        index_of[alpha_to[m]] = m;  
        mask >>= 1;  
        for (i = m + 1; i < n; i++) {  
            if (alpha_to[i - 1] >= mask)  
                alpha_to[i] = alpha_to[m] ^ ((alpha_to[i - 1] ^ mask) << 1);  
            else  
                alpha_to[i] = alpha_to[i - 1] << 1;  
            index_of[alpha_to[i]] = i;  
        }  
        index_of[0] = -1;  
    }  
  
    void gen_poly() {  
        int ii, jj, ll, kaux;  
        int test, aux, nocycles, root, noterms, rdncy;  
        int cycle[][] = new int[15][6];  
        int size[] = new int[15];  
        int min[] = new int[11];  
        int zeros[] = new int[11];  
        cycle[0][0] = 0;  
        size[0] = 1;  
        cycle[1][0] = 1;  
        size[1] = 1;  
        jj = 1;  
        do {  
            ii = 0;  
            do {  
                ii++;  
                cycle[jj][ii] = (cycle[jj][ii - 1] * 2) % n;  
                size[jj]++;  
                aux = (cycle[jj][ii] * 2) % n;  
            } while (aux != cycle[jj][0]);  
            ll = 0;  
            do {  
                ll++;  
                test = 0;  
                // (!test)  
                for (ii = 1; ((ii <= jj) && (test == 0)); ii++)  
                    // (!test)  
                    for (kaux = 0; ((kaux < size[ii]) && (test == 0)); kaux++)  
                        if (ll == cycle[ii][kaux])  
                            test = 1;  
            } while ((test != 0) && (ll < (n - 1)));// test  
            if (test == 0) {// (!test)  
                jj++; /* next cycle set index */  
                cycle[jj][0] = ll;  
                size[jj] = 1;  
            }  
        } while (ll < (n - 1));  
        nocycles = jj; /* number of cycle sets modulo n */  
        kaux = 0;  
        rdncy = 0;  
        for (ii = 1; ii <= nocycles; ii++) {  
            min[kaux] = 0;  
            for (jj = 0; jj < size[ii]; jj++)  
                for (root = 1; root < d; root++)  
                    if (root == cycle[ii][jj])  
                        min[kaux] = ii;  
            if (min[kaux] != 0) {  
                rdncy += size[min[kaux]];  
                kaux++;  
            }  
        }  
        noterms = kaux;  
        kaux = 1;  
        for (ii = 0; ii < noterms; ii++)  
            for (jj = 0; jj < size[min[ii]]; jj++) {  
                zeros[kaux] = cycle[min[ii]][jj];  
                kaux++;  
            }  
        System.out.printf("This is a (%d, %d, %d) binary BCH code\n", length,  
                k, d);  
        g[0] = alpha_to[zeros[1]];  
        g[1] = 1; /* g(x) = (X + zeros[1]) initially */  
        for (ii = 2; ii <= rdncy; ii++) {  
            g[ii] = 1;  
            for (jj = ii - 1; jj > 0; jj--)  
                if (g[jj] != 0)  
                    g[jj] = g[jj - 1]  
                            ^ alpha_to[(index_of[g[jj]] + zeros[ii]) % n];  
                else  
                    g[jj] = g[jj - 1];  
            g[0] = alpha_to[(index_of[g[0]] + zeros[ii]) % n];  
        }  
        System.out.printf("g(x) = ");  
        for (ii = 0; ii <= rdncy; ii++) {  
            System.out.printf("%d", g[ii]);  
            if ((ii != 0) && ((ii % 70) == 0))  
                System.out.printf("\n");  
        }  
        System.out.printf("\n");  
    }  
  
    void encode_bch() {  
        int i, j;  
        int feedback;  
        for (i = 0; i < length - k; i++)  
            bb[i] = 0;  
        for (i = k - 1; i >= 0; i--) {  
            feedback = data[i] ^ bb[length - k - 1];  
            if (feedback != 0) {  
                for (j = length - k - 1; j > 0; j--)  
                    if (g[j] != 0)  
                        bb[j] = bb[j - 1] ^ feedback;  
                    else  
                        bb[j] = bb[j - 1];  
                bb[0] = g[0] & feedback;// g[0] && feedback  
            } else {  
                for (j = length - k - 1; j > 0; j--)  
                    bb[j] = bb[j - 1];  
                bb[0] = 0;  
            }  
        }  
    }  
  
    void decode_bch() {  
        int i, j, q;  
        int elp[] = new int[3], s[] = new int[5], s3;  
        int count = 0, syn_error = 0;  
        int loc[] = new int[3], err[] = new int[3], reg[] = new int[3];  
        int aux;  
        /* first form the syndromes */  
        System.out.printf("s[] = (");  
        for (i = 1; i <= 4; i++) {  
            s[i] = 0;  
            for (j = 0; j < length; j++)  
                if (recd[j] != 0)  
                    s[i] ^= alpha_to[(i * j) % n];  
            if (s[i] != 0)  
                syn_error = 1; /* set flag if non-zero syndrome */  
            /* 
             * NOTE: If only error detection is needed, then exit the program 
             * here... 
             */  
            /* convert syndrome from polynomial form to index form */  
            s[i] = index_of[s[i]];  
            System.out.printf("%3d ", s[i]);  
        }  
        System.out.printf(")\n");  
        if (syn_error != 0) { /* If there are errors, try to correct them */  
            if (s[1] != -1) {  
                s3 = (s[1] * 3) % n;  
                if (s[3] == s3) /* Was it a single error ? */  
                {  
                    System.out.printf("One error at %d\n", s[1]);  
                    recd[s[1]] ^= 1; /* Yes: Correct it */  
                } else { /* 
                         * Assume two errors occurred and solve for the 
                         * coefficients of sigma(x), the error locator 
                         * polynomail 
                         */  
                    if (s[3] != -1)  
                        aux = alpha_to[s3] ^ alpha_to[s[3]];  
                    else  
                        aux = alpha_to[s3];  
                    elp[0] = 0;  
                    elp[1] = (s[2] - index_of[aux] + n) % n;  
                    elp[2] = (s[1] - index_of[aux] + n) % n;  
                    System.out.printf("sigma(x) = ");  
                    for (i = 0; i <= 2; i++)  
                        System.out.printf("%3d ", elp[i]);  
                    System.out.printf("\n");  
                    System.out.printf("Roots: ");  
                    /* find roots of the error location polynomial */  
                    for (i = 1; i <= 2; i++)  
                        reg[i] = elp[i];  
                    count = 0;  
                    for (i = 1; i <= n; i++) { /* Chien search */  
                        q = 1;  
                        for (j = 1; j <= 2; j++)  
                            if (reg[j] != -1) {  
                                reg[j] = (reg[j] + j) % n;  
                                q ^= alpha_to[reg[j]];  
                            }  
                        if (q == 0) { /* store error location number indices */  
                            loc[count] = i % n;  
                            count++;  
                            System.out.printf("%3d ", (i % n));  
                        }  
                    }  
                    System.out.printf("\n");  
                    if (count == 2)  
                        /* no. roots = degree of elp hence 2 errors */  
                        for (i = 0; i < 2; i++)  
                            recd[loc[i]] ^= 1;  
                    else  
                        /* Cannot solve: Error detection */  
                        System.out.printf("incomplete decoding\n");  
                }  
            } else if (s[2] != -1) /* Error detection */  
                System.out.printf("incomplete decoding\n");  
        }  
    }  
	
	private static boolean bitOf(char in) {
    return (in == '1');
}

private static char charOf(boolean in) {
    return (in) ? '1' : '0';
}


/***************************************************************************************
									Do XOR operation for the strings
*****************************************************************************************/
public static String xor_op(String a, String b, String c) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < a.length(); i++) {
        sb.append(charOf(bitOf(a.charAt(i)) ^ bitOf(b.charAt(i))
                ^ bitOf(c.charAt(i))));
    }
    String result = sb.toString();
    //System.out.println(result);
	return result;
}
  
    public void run() {  
	int count0 = 0;
        int i;  
        read_p(); /* read generator polynomial g(x) */  
        generate_gf(); /* generate the Galois Field GF(2**m) */  
        gen_poly(); /* Compute the generator polynomial of BCH code */  
        seed = 1;  
        // srand(seed);  
        Random random = new Random(seed);  
        /* Randomly generate DATA */  
        for (i = 0; i < k; i++)  
            data[i] = (random.nextInt() & 67108864) >> 26;  
  
        /* ENCODE */  
        encode_bch(); /* encode data */  
			String temp_str_encode = ""; // the complete encoded string
        for (i = 0; i < length - k; i++)  
            recd[i] = bb[i]; /* first (length-k) bits are redundancy */  
        for (i = 0; i < k; i++)  
            recd[i + length - k] = data[i]; /* last k bits are data */  
        System.out.printf("c(x) = ");  
        for (i = 0; i < length; i++) {  
            System.out.printf("%1d", recd[i]);
			temp_str_encode = temp_str_encode + recd[i]; // Concatenate encoded string 31 length
            if ((i != 0) && ((i % 70) == 0))  
                System.out.printf("\n");  
        }  
        System.out.printf("\n");  
		
		/*********************************************************
			XOR the encoded string with string ab and ba
		*********************************************************/
		try{
		Scanner s1 = new Scanner(new File("same_length_split_strings_ab.txt"));
		Scanner s2 = new Scanner(new File("same_length_split_strings_ba.txt"));
		String str_ab = "";
		String str_ba = "";
		  /* ERRORS */  
       // System.out.printf("Enter the number of errors and their positions: ");  
  
        //BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));  
        String numerrStr = "3";  
		
        
        numerr = Integer.valueOf(numerrStr);  
       // for (i = 0; i < numerr; i++) {  
          //  String errposStr = null;  
          //  try {  
         //       errposStr = wt.readLine();  
          //  } catch (IOException e) {  
                // TODO Auto-generated catch block  
          //      e.printStackTrace();  
          //  }  
            errpos[0] = Integer.valueOf("2");
			errpos[1] = Integer.valueOf("6");
			errpos[2] = Integer.valueOf("10");
			
            recd[errpos[i]] ^= 1;  
			int cnts = 0;
        //} 
		while(s1.hasNextLine() && s2.hasNextLine()){
		str_ab = s1.nextLine();
		str_ba = s2.nextLine();
		String tempstr  = xor_op (temp_str_encode, str_ab, str_ba);
		//System.out.println("tempstr--------------"+tempstr);
		char[] charArray = tempstr.toCharArray();
		
		for (i = 0; i < length; i++) {  
		recd[i] = Character.getNumericValue(charArray[i]);
		}
		
       
        System.out.printf("r(x) = ");  
        for (i = 0; i < length; i++)  
            System.out.printf("%1d", recd[i]);  
        System.out.printf("\n");  
  
        /* DECODE */  
        decode_bch();  
        /* 
         * print out original and decoded data 
         */  
        System.out.printf("Results:\n");  
        System.out.printf("original data  = ");  
        for (i = 0; i < k; i++)  
            System.out.printf("%1d", data[i]);  
        System.out.printf("\nrecovered data = "); 
		cnts++;		
        for (i = length - k; i < length; i++)  
            System.out.printf("%1d", recd[i]);  
        System.out.printf("\n");  
        /* decoding errors: we compare only the data portion */  
        for (i = length - k; i < length; i++)  
            if (data[i - length + k] != recd[i])  
                decerror++;  
		
		
        if (decerror != 0)  {
			
			if(decerror == before){
				System.out.printf("index : = " + cnts);
				match++;}
			before = decerror;
		System.out.printf("-------------------------------%d message decoding errors\n", decerror);  }
        else  {
			count0++;
            System.out.printf("Succesful decoding\n");
		}			
		//System.out.println("----"+count0);
		//System.out.println("----"+decerror);
		}
		}
			catch(Exception e){
		
		}
		
		
    }  
  
    public static void main(String[] args) {  
        BCH_32_21_5 bch_32_21_5 = new BCH_32_21_5();  
        bch_32_21_5.run();  
		
		System.out.printf("total matches : ----------------"+ match);
    }  
}  