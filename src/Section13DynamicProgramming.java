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
        var houseRobber = new HouseRobber();
        System.out.println("----House Robber----");
        System.out.println("We can earn: "+houseRobber.rob(new int[]{7,3,4,5}));
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
        };

        calculationsOptimized++;

        memo[n] = fibonacciShuYu(n-1)+fibonacciShuYu(n-2);
        return memo[n];
    }

    static class HouseRobber{
        int[] memo;

        public int rob(int[] nums) {
            return rob_solution1(nums, nums.length -1);
//            memo = new int[nums.length];
//            return rob_solution2(nums, nums.length -1);
        }

        // first solution: time complexity O(2^n)
        public int rob_solution1(int[] nums, int i){
            if(i<0) return 0;

            return Math.max(
                    rob_solution1(nums, i-1),   // do not rob
                    rob_solution1(nums, i-2)+nums[i]);  // rob and go to the next next house
        }

        // divide and conquer
        // time complexity O(n)
        // space complexity O(n)
        public int rob_solution2(int[] nums, int i){
            if (i<0) return 0;
            if (memo[i]>0) return memo[i];
            memo[i] = Math.max(rob_solution2(nums, i-1), rob_solution2(nums, i-2)+nums[i]);
            return memo[i];
        }
    }
}
