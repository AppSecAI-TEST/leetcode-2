/*
 * buy[i]表示在第i天之前最后一个操作是买，此时的最大收益。

	sell[i]表示在第i天之前最后一个操作是卖，此时的最大收益。
	
	rest[i]表示在第i天之前最后一个操作是冷冻期，此时的最大收益。
	
	我们写出递推式为：
	
	buy[i]  = max(rest[i-1] - price, buy[i-1]) 
	sell[i] = max(buy[i-1] + price, sell[i-1])
	rest[i] = max(sell[i-1], buy[i-1], rest[i-1])
	
	上述递推式很好的表示了在买之前有冷冻期，买之前要卖掉之前的股票。一个小技巧是如何保证[buy, rest, buy]的情况不会出现，这是由于buy[i] <= rest[i]， 即rest[i] = max(sell[i-1], rest[i-1])，这保证了[buy, rest, buy]不会出现。
	
	另外，由于冷冻期的存在，我们可以得出rest[i] = sell[i-1]，这样，我们可以将上面三个递推式精简到两个：
	
	buy[i]  = max(sell[i-2] - price, buy[i-1]) 
	sell[i] = max(buy[i-1] + price, sell[i-1])
	 
	
	我们还可以做进一步优化，由于i只依赖于i-1和i-2，所以我们可以在O(1)的空间复杂度完成算法
 */
public class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int[] buy = new int[1];  //buy[i - 1]
        int[] sell = new int[2]; //sell[i - 1, i - 2]
        Arrays.fill(buy, Integer.MIN_VALUE);
        for (int price : prices) {
            int buy_cur = Math.max(sell[1] - price, buy[0]);
            int sell_cur = Math.max(buy[0] + price, sell[0]);
            buy[0] = buy_cur;
            sell[1] = sell[0];
            sell[0] = sell_cur;
        }
        return sell[0];
    }
}