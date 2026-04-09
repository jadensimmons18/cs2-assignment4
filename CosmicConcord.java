









public class CosmicConcord 
{

    int[] A;
    int[] B;

    int N;
    int M;

    int D;
    int G;
    int K;

    int[][][] memo;

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
        memo = new int[N][M][K+1];

        // initialize memo to -1
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                for (int k = 0; k <= K; k++)
                {
                    memo[i][j][k] = -1;
                }
            }
        }

        int memoSolution = solveMemo(0, 0, K);

    }

    // Determines if the two indecies are close enough to be able to match
    public boolean canMatch(int i, int j) 
    {
        if (Math.abs(A[i] - B[j]) <= D)
        {
            return true;
        }
        return false;
    }

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

    public int solveMemo(int i, int j, int k, int prevI, int prevJ){
        // if we reach the end of either array then the longest sequence = 0
        if (i == N || j == M)
        {
            return 0;
        }

        int skipA = solveMemo(i+1, j, k, prevI, prevJ);
        int skipB = solveMemo(i, j+1, k, prevI, prevJ);

        int best = Math.max(skipA, skipB);

        int match = 0;
        if (canMatch(i, j)) 
        {
            if (prevI == -1 || prevJ == -1 || isRising(i, j, prevI, prevJ))
                match = 1 + solveMemo(i+1, j+1, k, i, j);

            else if (k > 0)
                match = 1 + solveMemo(i+1, j+1, k-1, i, j);

            best = Math.max(best, match);
        }
        return best;
    }
}
