import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by liuzhongzheng on 2017/1/11.
 */
public class LotteryService {
    // 零值
    private static double baseZero = 0.0000000001d;
    // 伪随机数发生器
    private static Random random = new Random();

    /**
     * 抽奖
     *
     * @param originalRates
     *            原始的概率列表，里面是一个概率对象
     * @return
     *
     */
    public static LotteryRate lottery(List<LotteryRate> originalRates) {
        if (originalRates == null || originalRates.isEmpty()) {
            return null;
        }

        int size = originalRates.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        // 计算概率区间，即每个物品在总概率的基础下的概率情况
        List<Double> sortOriginalRates = new ArrayList<Double>(size);
        for (LotteryRate rate : originalRates) {
            double r = rate.getRate();
            if (r >= baseZero) {
                sumRate += r;
            }
            sortOriginalRates.add(sumRate);
        }

        // 生成区间内随机数
        double nextDouble = random.nextDouble() * sumRate;
        // 根据区块值来获取抽取到的物品索引
        sortOriginalRates.add(nextDouble);
        Collections.sort(sortOriginalRates);

        int index = sortOriginalRates.indexOf(nextDouble);

        return originalRates.get(index);
    }

    /**
     * 设置抽奖随机数的种子
     * @param seed 种子数
     */
    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    /**
     * 概率对象
     */
    public static class LotteryRate {
        // 该目标的概率，可以是任意值
        private double rate;
        private Object target;

        public LotteryRate(double rate, Object target) {
            this.rate = rate;
            this.target = target;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }
    }
}
