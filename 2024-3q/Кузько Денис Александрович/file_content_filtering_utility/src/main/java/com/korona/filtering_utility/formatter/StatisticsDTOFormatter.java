package com.korona.filtering_utility.formatter;

import com.korona.filtering_utility.dto.FullStatisticsDTO;
import com.korona.filtering_utility.dto.ShortStatisticsDTO;

public class StatisticsDTOFormatter {
    public static String statisticsDTOtoString(ShortStatisticsDTO dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getFileNameForInteger()).append(" short statistic: elements = ").append(dto.getCountInteger()).append("\n")
                .append(dto.getFileNameForFloat()).append(" short statistic: elements = ").append(dto.getCountFloat()).append("\n")
                .append(dto.getFileNameForString()).append(" short statistic: elements = ").append(dto.getCountString()).append("\n");
        return sb.toString();


    }

    public static String statisticsDTOtoString(FullStatisticsDTO dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getFileNameForInteger()).append(" full statistic: elements = ").append(dto.getCountInteger())
                .append("; min = ").append(dto.getMinInteger())
                .append("; max = ").append(dto.getMaxInteger())
                .append("; sum = ").append(dto.getSumInteger())
                .append("; average = ").append(String.format("%.2f", dto.getAvgInteger())).append("\n")
                .append(dto.getFileNameForFloat()).append(" full statistic: elements = ").append(dto.getCountFloat())
                .append("; min = ").append(dto.getMinFloat())
                .append("; max = ").append(dto.getMaxFloat())
                .append("; sum = ").append(String.format("%.2f", dto.getSumFloat()))
                .append("; average = ").append(String.format("%.2f", dto.getAvgFloat())).append("\n")
                .append(dto.getFileNameForString()).append(" full statistic: elements = ").append(dto.getCountString())
                .append("; min length = ").append(dto.getMinLengthString())
                .append("; max length = ").append(dto.getMaxLengthString()).append("\n");
        return sb.toString();
    }
}
