import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公平公正公开的抽奖
 * Created by liuzhongzheng on 2017/1/29.
 */
public class Lottery4Chicken {
    private static String BIG_AWARD_TEMPLATE = "恭喜 %s 在54哥特约赞助的集资活动中抽中特等奖!";
    private static String FIRST_AWARD_TEMPLATE = "恭喜 %s 在54哥特约赞助的集资活动中抽中一等奖!";
    private static String SECOND_AWARD_TEMPLATE = "恭喜 %s 在54哥特约赞助的集资活动中抽中二等奖!";
    private static String THIRD_AWARD_TEMPLATE = "恭喜 %s 在54哥特约赞助的集资活动中抽中三等奖!";

    /**
     * 鸽子们（这不是鸡吗？）
     */
    private static class Chicken extends LotteryService.LotteryRate {
        /**
         * 概率模型的构造函数
         * @param rate 概率
         * @param nick 昵称
         */
        Chicken(double rate, Object nick) {
            super(rate, nick);
        }

        String getNick() {
            return this.getTarget().toString();
        }
    }

    /**
     * 初始化数据
     * @return 鸽子们和他们的概率列表
     */
    private static List<LotteryService.LotteryRate> initData() {
        List<LotteryService.LotteryRate> chickens = new ArrayList<>();

        int amount = 17;
        while (amount > 0) {
            chickens.add(new Chicken(1, amount + "号"));
            amount--;
        }

        return chickens;
    }

    /**
     * 欢呼吧，鸽子们！抽中会自动把你剔除！
     * @param chickens 可以参加抽奖的鸽子们
     * @return 一只鸽子
     */
    private static Chicken lottery4Chicken(List<LotteryService.LotteryRate> chickens) {
        Chicken chicken = (Chicken)LotteryService.lottery(chickens);

        // 避免重复抽到
        chickens.remove(chicken);

        return chicken;
    }

    /**
     * 一千万次模拟抽奖
     */
    private static void check(long seed) {
        LotteryService.setSeed(seed);
        // statistics
        Map<String, Integer> count = new HashMap<String, Integer>();
        double num = 10000000;
        for (int i = 0; i < num; i++) {
            Chicken chicken = lottery4Chicken(initData());

            Integer value = count.get(chicken.getNick());
            count.put(chicken.getNick(), value == null ? 1 : value + 1);
        }

        System.out.println("1千万次模拟抽奖检验结果：");
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            System.out.println(entry.getKey() + ", 抽中总数：" + entry.getValue() + ", 概率：" + entry.getValue() / num);
        }
    }

    // 飞吧，鸽子们
    public static void main(String[] args) {
        // 设置种子
        LotteryService.setSeed(20170103L);
        // 鸽子们的数据
        List<LotteryService.LotteryRate> chickens = initData();

        System.out.println(String.format(BIG_AWARD_TEMPLATE, lottery4Chicken(chickens).getNick()));
        System.out.println(String.format(FIRST_AWARD_TEMPLATE, lottery4Chicken(chickens).getNick()));
        System.out.println(String.format(SECOND_AWARD_TEMPLATE, lottery4Chicken(chickens).getNick()));
        System.out.println(String.format(THIRD_AWARD_TEMPLATE, lottery4Chicken(chickens).getNick()));
    }
}
