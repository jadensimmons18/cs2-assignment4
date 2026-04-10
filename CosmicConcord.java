/* Jaden Simmons
 Cosmic Concordance
 COP3503 Computer Science 2
 CosmicConcord.java
*/


public class CosmicConcord 
{

    int[] A;
    int[] B;

    int N;
    int M;

    int D;
    int G;
    int K;

    int[][][][][] memo;

    // Main method that calls the two solving methods
    public void solve(int A[], int B[], int N, int M, int D, int G, int K)
    {

        // store values into class variables
        this.A = A;
        this.B = B;

        this.N = N;
        this.M = M;

        this.D = D;
        this.G = G;
        this.K = K;

        // create memo table
        memo = new int[N][M][K+1][N+1][M+1];

        // initialize memo to -1
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                for (int k = 0; k <= K; k++)
                {
                    for (int pI = 0; pI <= N; pI++)
                    {
                        for (int pJ = 0; pJ <= M; pJ++)
                        {
                            memo[i][j][k][pI][pJ] = -1;
                        }
                    }
                }
            }
        }

        int memoSolution = solveRecMemo(0, 0, K, -1, -1);
        int tabSolution = solveTab();
        System.out.println("Maximum length using Recursion (with Memoization): " + memoSolution);
        System.out.println("Maximum length using Tabulation: " + tabSolution);
    }

    // (Helper Method) Determines if the two indecies are close enough to be able to match 
    public boolean canMatch(int i, int j) 
    {
        if (Math.abs(A[i] - B[j]) <= D)
        {
            return true;
        }
        return false;
    }

    // (Helper method) Determines if the new point set is rising 
    public boolean isRising(int i, int j, int prevI, int prevJ) 
    {
        if (prevI == -1 || prevJ == -1)
        {
            return true;
        }
            
        if ((A[i] - A[prevI]) >= G && (B[j] - B[prevJ]) >= G) 
        {
            return true;
        }
        return false;
    }

    // Solves the problem using memoization
    public int solveRecMemo(int i, int j, int k, int prevI, int prevJ)
    {
        // If you reach the end of either array then the longest sequence = 0
        if (i == N || j == M)
        {
            return 0;
        }

        // -1 becomes 0
        int pi = prevI + 1;
        int pj = prevJ + 1;

        // Check memo table
        if (memo[i][j][k][pi][pj] != -1)
        {
            return memo[i][j][k][pi][pj];
        }

        // Try skipping A[i]
        int skipA = solveRecMemo(i + 1, j, k, prevI, prevJ);

        // Try skipping B[j]
        int skipB = solveRecMemo(i, j + 1, k, prevI, prevJ);

        // Best result so far
        int best = Math.max(skipA, skipB);

        // Try matching A[i] and B[j]
        if (canMatch(i, j))
        {
            int match = 0;

            // Rising step
            if (isRising(i, j, prevI, prevJ))
            {
                match = 1 + solveRecMemo(i + 1, j + 1, k, i, j);
            }

            // Dip step
            else if (k > 0)
            {
                match = 1 + solveRecMemo(i + 1, j + 1, k - 1, i, j);
            }

            best = Math.max(best, match);
        }

        // Store result in memo table
        memo[i][j][k][pi][pj] = best;

        return best;
    }

    // Solves the problem using tabulation
    public int solveTab()
    {
    // Create table
    int[][][][][] dp = new int[N + 1][M + 1][K + 1][N + 1][M + 1];

    for (int i = N - 1; i >= 0; i--)
    {
        for (int j = M - 1; j >= 0; j--)
        {
            for (int k = 0; k <= K; k++)
            {
                for (int prevI = -1; prevI < N; prevI++)
                {
                    for (int prevJ = -1; prevJ < M; prevJ++)
                    {
                        int pi = prevI + 1;
                        int pj = prevJ + 1;

                        // Try skipping A[i]
                        int skipA = dp[i + 1][j][k][pi][pj];

                        // Try skipping B[j]
                        int skipB = dp[i][j + 1][k][pi][pj];

                        int best = Math.max(skipA, skipB);

                        // Try matching
                        if (canMatch(i, j))
                        {
                            int match = 0;

                            // Rising step
                            if (isRising(i, j, prevI, prevJ))
                            {
                                match = 1 + dp[i + 1][j + 1][k][i + 1][j + 1];
                            }

                            // Dip step
                            else if (k > 0)
                            {
                                match = 1 + dp[i + 1][j + 1][k - 1][i + 1][j + 1];
                            }

                            best = Math.max(best, match);
                        }

                        // Store result
                        dp[i][j][k][pi][pj] = best;
                    }
                }
            }
        }
    }
    return dp[0][0][K][0][0];
    }
}
