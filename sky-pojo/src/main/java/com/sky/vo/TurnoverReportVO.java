package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoverReportVO implements Serializable {

    // 日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // 营业额，以逗号分隔，例如：406.0,1520.0,75.0
    private String turnoverList;

    public static void main(String[] args) {
/*  select amount
    from orders where order_time between '2023-07-01' and '2023-07-10';*/
        LocalDate begin = LocalDate.of(2023, 7, 1);
        LocalDate end = LocalDate.of(2023, 7, 10);
        // 当开始时间不大于结束时间，循环执行
        StringBuilder dateList = new StringBuilder();
        while (!begin.isAfter(end)) {
            String date = begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println("当前日期" + date);
            // System.out.println(begin.until(end, ChronoUnit.DAYS));
            if (begin.until(end, ChronoUnit.DAYS) == 0) {
                dateList.append(date);
            } else {
                dateList.append(date).append(",");
            }
            // 日期+1，继续执行
            begin = begin.plusDays(1);
        }
        System.out.println(dateList);
    }
}
