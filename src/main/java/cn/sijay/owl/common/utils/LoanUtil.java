package cn.sijay.owl.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * LoanUtil
 *
 * @author sijay
 * @since 2026-04-15
 */
public class LoanUtil {
    public static void cacl(long invest, int year, double rate) {
        rate = 0.031; // 年利率
        int month = year * 12;
        BigDecimal perMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, rate, month);
        System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, rate, month);
        System.out.println("等额本息---每月还款利息：" + mapInterest);
        Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, rate, month);
        System.out.println("等额本息---每月还款本金：" + mapPrincipal);
        HashMap<Integer, BigDecimal> map = new HashMap<>(mapInterest.size());
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            map.put(entry.getKey(), entry.getValue().add(mapPrincipal.get(entry.getKey())));
        }
        System.out.println("等额本息---每月还款：" + map);
        BigDecimal count = getInterestCount(invest, rate, month);
        System.out.println("等额本息---总利息：" + count);
    }

    /**
     * 每月偿还本金和利息
     * <p>
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     *
     * @param invest     总借款额（贷款本金,单位分）
     * @param yearRate   年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还本金和利息(入1 单位分)
     */
    public static BigDecimal getPerMonthPrincipalInterest(long invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        double perMonthPrincipalInterest = invest * (monthRate * Math.pow(1 + monthRate, totalMonth)) / (Math.pow(1 + monthRate, totalMonth) - 1);
        return new BigDecimal(perMonthPrincipalInterest).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 等额本息的每月偿还利息
     * <p>
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     *
     * @param invest     总借款额（贷款本金,分）
     * @param yearRate   年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还利息(入1 单位分)
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(long invest, double yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        double monthRate = yearRate / 12;
        double monthInterest;
        for (int i = 1; i < totalMonth + 1; i++) {
            double multiply = invest * monthRate;
            double sub = Math.pow(1 + monthRate, totalMonth) - Math.pow(1 + monthRate, i - 1);
            monthInterest = multiply * sub / (Math.pow(1 + monthRate, totalMonth) - 1);
            map.put(i, new BigDecimal(monthInterest).setScale(2, RoundingMode.HALF_UP));
        }
        return map;
    }

    /**
     * 等额本息的每月偿还本金（月还本息-月还利息）
     *
     * @param invest     总借款额（贷款本金,分）
     * @param yearRate   年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还本金(取整舍 单位分)
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(long invest, double yearRate, int totalMonth) {
        double monthRate = yearRate / 12;
        double monthIncome = invest * monthRate * Math.pow(1 + monthRate, totalMonth)
            / (Math.pow(1 + monthRate, totalMonth) - 1);
        BigDecimal perMonthPrincipalInterest = new BigDecimal(monthIncome).setScale(2, RoundingMode.HALF_UP);

        Map<Integer, BigDecimal> mapPrincipal = new HashMap<>();
        double monthInterest;
        for (int i = 1; i < totalMonth + 1; i++) {
            double multiply = invest * monthRate;
            double sub = (Math.pow(1 + monthRate, totalMonth)) - (Math.pow(1 + monthRate, i - 1));
            monthInterest = multiply * sub / (Math.pow(1 + monthRate, totalMonth) - 1);
            BigDecimal monthInterestL = new BigDecimal(monthInterest).setScale(2, RoundingMode.HALF_UP);
            mapPrincipal.put(i, perMonthPrincipalInterest.subtract(monthInterestL));
        }
        return mapPrincipal;
    }

    /**
     * 等额本息的总利息
     *
     * @param invest     总借款额（贷款本金）
     * @param yearRate   年利率
     * @param totalMonth 还款总月数
     * @return 总利息 (单位分)
     */
    public static BigDecimal getInterestCount(long invest, double yearRate, int totalMonth) {
        BigDecimal count = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> entry : getPerMonthInterest(invest, yearRate, totalMonth).entrySet()) {
            count = count.add(entry.getValue());
        }
        return count;
    }

}
