import java.util.HashMap;

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

        //
        var houseRobber = new HouseRobber(new int[]{7,3,4,5,4,5,4});
        System.out.println("----House Robber----");
        System.out.println("Solution 1 earn : "+houseRobber.rob_solution1(houseRobber.nums.length-1)+" time complexity: "+houseRobber.timeComplexitySolution1);
        System.out.println("Solution 2 earn : "+houseRobber.rob_solution2(houseRobber.nums.length-1)+" time complexity: "+houseRobber.timeComplexitySolution2);
        System.out.println("Solution 3 earn : "+houseRobber.rob_solution3()+" time complexity: "+houseRobber.timeComplexitySolution3);
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
        private int[] nums;
        private int[] memo1;
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
}
