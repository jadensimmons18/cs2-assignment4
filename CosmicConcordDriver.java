/*  INSERT STUDENT NAME
    Cosmic Concordance
    COP3503 Computer Science 2
    CosmicConcordDriver.java
    Compile: javac CosmicConcordDriver.java
    Run: java CosmicConcordDriver [CASE]
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// import java.io.*;
// import java.util.*;

public class CosmicConcordDriver 
{
    public static void main(String args[])
    {
        if (args.length == 0) 
        {
            System.err.println("Usage: java SudokuSolverDriver <CASE>");
            return;
        }

        int testCase = Integer.parseInt(args[0]);

        CosmicConcord ccSolver = new CosmicConcord();

        try(Scanner sc = new Scanner(new File("inp" + testCase + ".in")))
        {
            int N = sc.nextInt();   //length of readings from Astra-1
            int M = sc.nextInt();   //length of readings from Astra-2
            int D = sc.nextInt();   //tolerance for approximate matching 
            int G = sc.nextInt();   //minimum required increase for a step to be considered rising
            int K = sc.nextInt();   //maximum number of allowed dip steps
            int A[] = new int[N];   //Array to store readings from Astra-1
            int B[] = new int[M];   //Array to store readings from Astra-2

            //Readings from Astra-1
            for (int i = 0; i < N; i++) 
            {
                A[i] = sc.nextInt();
            }
            //Readings from Astra-2
            for (int i = 0; i < M; i++) 
            {
                B[i] = sc.nextInt();
            }
            sc.close();

            ccSolver.solve(A, B, N, M, D, G, K);
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Could not open file \"inp" + testCase + ".in\"");
            return;
        }
    }
}
