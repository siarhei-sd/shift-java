package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dto.FullStatisticsDTO;
import com.korona.filtering_utility.dto.ShortStatisticsDTO;

public class FullStatisticsService extends ShortStatisticsService {

    private final FullStatisticsDTO fullStatisticsDTO;

    public FullStatisticsService() {
        super();
        fullStatisticsDTO = new FullStatisticsDTO();
        fullStatisticsDTO.setMinInteger(Integer.MAX_VALUE);
        fullStatisticsDTO.setMaxInteger(Integer.MIN_VALUE);
        fullStatisticsDTO.setMinFloat(Float.MAX_VALUE);
        fullStatisticsDTO.setMaxFloat(Float.MIN_VALUE);
        fullStatisticsDTO.setMinLengthString(Long.MAX_VALUE);
        fullStatisticsDTO.setMaxLengthString(Long.MIN_VALUE);
    }

    @Override
    public FullStatisticsDTO getStatistics() {
        ShortStatisticsDTO shortStatsDTO = super.getStatistics();

        fullStatisticsDTO.setCountInteger(shortStatsDTO.getCountInteger());
        fullStatisticsDTO.setCountFloat(shortStatsDTO.getCountFloat());
        fullStatisticsDTO.setCountString(shortStatsDTO.getCountString());

        fullStatisticsDTO.setFileNameForInteger(shortStatsDTO.getFileNameForInteger());
        fullStatisticsDTO.setFileNameForFloat(shortStatsDTO.getFileNameForFloat());
        fullStatisticsDTO.setFileNameForString(shortStatsDTO.getFileNameForString());

        if (shortStatsDTO.getCountInteger() == 0) {
            fullStatisticsDTO.setMinInteger(0);
            fullStatisticsDTO.setMaxInteger(0);
        }

        if (shortStatsDTO.getCountFloat() == 0) {
            fullStatisticsDTO.setMinFloat(0);
            fullStatisticsDTO.setMaxFloat(0);
        }

        if(shortStatsDTO.getCountString()==0){
            fullStatisticsDTO.setMinLengthString(0);
            fullStatisticsDTO.setMaxLengthString(0);
        }

        fullStatisticsDTO.setAvgInteger(calculateAvgInteger());
        fullStatisticsDTO.setAvgFloat(calculateAvgFloat());

        return fullStatisticsDTO;
    }

    @Override
    public void addIntegerData(String data) {
        super.addIntegerData(data);
        int numberInteger = Integer.parseInt(data);

        setMinInteger(numberInteger);
        setMaxInteger(numberInteger);
        setSumInteger(numberInteger);
    }

    @Override
    public void addFloatData(String data) {
        super.addFloatData(data);
        float numberFloat = Float.parseFloat(data);

        setMinFloat(numberFloat);
        setMaxFloat(numberFloat);
        setSumFloat(numberFloat);
    }

    @Override
    public void addStringData(String data) {
        super.addStringData(data);
        long length = data.length();

        setMinLengthString(length);
        setMaxLengthString(length);
    }

    private void setMinInteger(int numberInteger) {
        if (numberInteger < fullStatisticsDTO.getMinInteger()) {
            fullStatisticsDTO.setMinInteger(numberInteger);
        }
    }

    private void setMaxInteger(int numberInteger) {
        if (numberInteger > fullStatisticsDTO.getMaxInteger()) {
            fullStatisticsDTO.setMaxInteger(numberInteger);
        }
    }

    private void setSumInteger(int numberInteger) {
        fullStatisticsDTO.setSumInteger(fullStatisticsDTO.getSumInteger() + numberInteger);
    }

    private double calculateAvgInteger() {
        long countInteger = super.getStatistics().getCountInteger();
        return countInteger != 0 ? (double) fullStatisticsDTO.getSumInteger() / countInteger : 0;
    }

    private void setMinFloat(float numberFloat) {
        if (numberFloat < fullStatisticsDTO.getMinFloat()) {
            fullStatisticsDTO.setMinFloat(numberFloat);
        }
    }

    private void setMaxFloat(float numberFloat) {
        if (numberFloat > fullStatisticsDTO.getMaxFloat()) {
            fullStatisticsDTO.setMaxFloat(numberFloat);
        }
    }

    private void setSumFloat(float numberFloat) {
        fullStatisticsDTO.setSumFloat(fullStatisticsDTO.getSumFloat() + numberFloat);
    }

    private double calculateAvgFloat() {
        long countFloat = super.getStatistics().getCountFloat();
        return countFloat != 0 ? fullStatisticsDTO.getSumFloat() / countFloat : 0;
    }

    private void setMinLengthString(long length) {
        if (length < fullStatisticsDTO.getMinLengthString()) {
            fullStatisticsDTO.setMinLengthString(length);
        }
    }

    private void setMaxLengthString(long length) {
        if (length > fullStatisticsDTO.getMaxLengthString()) {
            fullStatisticsDTO.setMaxLengthString(length);
        }
    }
}
