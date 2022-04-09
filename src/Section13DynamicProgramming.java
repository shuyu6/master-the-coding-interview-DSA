public class Section13DynamicProgramming {
    private static long calculations = 0;
    private static long calculationsOptimized = 0;
    private static long[] memo;
    public static void main(String[] args) {
        // original solution
        System.out.println("Fibonacci solution 1 : "+fibonacci(6));
        System.out.println("Calculations: "+calculations);
        // shuyu solution
        System.out.println("Fibonacci solution 2 : "+fibonacciShuYu(6));
        System.out.println("Calculations: "+calculationsOptimized);

        // House Robber leet code problem
        var houseRobber = new HouseRobber(new int[]{7,3,4,5,4,5,4});
        System.out.println("----House Robber----");
        System.out.println("Solution 1 earn : "+houseRobber.rob_solution1(houseRobber.nums.length-1)+" time complexity: "+houseRobber.timeComplexitySolution1);
        System.out.println("Solution 2 earn : "+houseRobber.rob_solution2(houseRobber.nums.length-1)+" time complexity: "+houseRobber.timeComplexitySolution2);
        System.out.println("Solution 3 earn : "+houseRobber.rob_solution3()+" time complexity: "+houseRobber.timeComplexitySolution3);

        // Best time to buy and sell
        var maxProfit = new BestTimeBuyAndSell(new int[]{7,6,4,3,1});
        System.out.println("-------Best time to buy and sell-------");
        System.out.println("Solution 1 max profit : "+maxProfit.maxProfit_1()+" time complexity: "+maxProfit.timeComplexity1);
        System.out.println("Solution 2 max profit : "+maxProfit.maxProfit_2()+" time complexity: "+maxProfit.timeComplexity2);
        System.out.println("Solution 3 max profit : "+maxProfit.maxProfit_godSolution()+" time complexity: "+maxProfit.timeComplexity3);

        var maxProfit2 = new BestTimeBuyAndSell(new int[]{7,1,3,4,6,1});
        System.out.println("Test 2 ");
        System.out.println("Solution 1 max profit : "+maxProfit2.maxProfit_1()+" time complexity: "+maxProfit2.timeComplexity1);
        System.out.println("Solution 2 max profit : "+maxProfit2.maxProfit_2()+" time complexity: "+maxProfit2.timeComplexity2);
        System.out.println("Solution 3 max profit : "+maxProfit2.maxProfit_godSolution()+" time complexity: "+maxProfit2.timeComplexity3);

        var climbingStairs = new ClimbingStairs();
        System.out.println("-------Climbing Stairs-------");
        System.out.println("3 stairs take "+climbingStairs.climbStairs(3)+" steps ");
        System.out.println("4 stairs take "+climbingStairs.climbStairs(4)+" steps ");
    }
    // time complexity O(2^n)
    private static long fibonacci(int n){
        calculations++;
        if (n<2) return n;
        return fibonacci(n-1)+fibonacci(n-2);
    }
    // my solution
    // time complexity O(n)
    // space complexity O(n)
    private static long fibonacciShuYu(int n){
        if (memo == null) memo = new long[n+1];
        if (memo[n]>0) return memo[n];
        if (n<2){
            memo[n] = n;
            return memo[n];
        }

        calculationsOptimized++;

        memo[n] = fibonacciShuYu(n-1)+fibonacciShuYu(n-2);
        return memo[n];
    }

    // #leetcode: 198
    static class HouseRobber{
        private final int[] nums;
        private final int[] memo1;
        private int timeComplexitySolution1 = 0;
        private int timeComplexitySolution2 = 0;
        private int timeComplexitySolution3 = 0;
        public HouseRobber(int[] nums){
            this.nums = nums;
            this.memo1 = new int[nums.length];
        }

        // first solution: time complexity O(2^n)
        public int rob_solution1(int i){
            if(i<0) return 0;
            timeComplexitySolution1++;

            return Math.max(
                    rob_solution1(i-1),   // do not rob
                    rob_solution1(i-2)+nums[i]);  // rob and go to the next next house
        }

        // divide and conquer
        // time complexity O(n)
        // space complexity O(n)
        public int rob_solution2(int i){
            if (i<0) return 0;
            if (memo1[i]>0) return memo1[i];
            timeComplexitySolution2++;
            memo1[i] = Math.max(rob_solution2(i-1), rob_solution2(i-2)+nums[i]);
            return memo1[i];
        }

        // time complexity O(n)
        // space complexity O(1)
        public int rob_solution3(){
            if (nums.length == 0) return 0;
            int prev1 = 0;
            int prev2 = 0;
            // prev1, prev2, num [4,2,3,4]
            for (int num: nums){
                timeComplexitySolution3++;
                int tmp = prev1;
                prev1 = Math.max(prev2+num, prev1); // take the money, skip the house
                prev2 = tmp;    // prev2 change to prev1 value
            }
            return prev1;
        }
    }
    // #leetcode: 121
    static class BestTimeBuyAndSell{
        private final int[] prices;
        private int timeComplexity1;
        private int timeComplexity2;
        private int timeComplexity3;

        public BestTimeBuyAndSell(int[] prices){
            this.prices = prices;
            this.timeComplexity1 = 0;
            this.timeComplexity2 = 0;
            this.timeComplexity3 = 0;
        }

        // time complexity O(2^n)
        public int maxProfit_1(){
            if(prices.length <2) return 0;
            int maxProfit = 0;
            for(int b=0; b<prices.length-1;b++){
                for(int s=b+1; s<prices.length;s++){
                    timeComplexity1++;
                    maxProfit = Math.max(prices[s]-prices[b], maxProfit);
                }
            }
            return maxProfit;
        }
        // time complexity O(n)
        public int maxProfit_2(){
            if(prices.length<2) return 0;
            int b = 0; //buy index
            int maxProfit = 0;

            for(int s=1; s<prices.length;s++){  // is sell
                timeComplexity2++;
                var tempProfit = prices[s] - prices[b];
                if (tempProfit>0){
                    maxProfit = Math.max(maxProfit, tempProfit);
                }else{
                    if (prices[b]>prices[s]) b=s;
                }
            }
            return maxProfit;
        }
        public int maxProfit_godSolution(){
            var left = 0; // buy
            var right = 1; // sell
            var maxProfit = 0;
            while(right<prices.length){
                timeComplexity3++;

                if (prices[left]<prices[right]){
                    maxProfit = Math.max(prices[right]-prices[left], maxProfit);
                }else {
                    left=right;
                }
                right++;
            }
            return maxProfit;

        }

    }
    // Leetcode #70
    static class ClimbingStairs{

        // complexity O(n)
        public int climbStairs(int n) {
            if (n<=2) return n;
            int prev1 = 1;
            int prev2 = 2;
            for (int i=3; i<=n;i++){
                int temp = prev2;
                prev2 = prev1+prev2;
                prev1 = temp;
            }
            return prev2;
        }
    }
}
