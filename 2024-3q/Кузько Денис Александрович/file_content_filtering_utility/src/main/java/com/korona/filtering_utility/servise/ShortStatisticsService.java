package com.korona.filtering_utility.servise;

import com.korona.filtering_utility.dto.ShortStatisticsDTO;
import com.korona.filtering_utility.servise.api.IStatisticsService;

public class ShortStatisticsService implements IStatisticsService<ShortStatisticsDTO> {
    private final ShortStatisticsDTO dto = new ShortStatisticsDTO();

    public ShortStatisticsService() {

    }

    @Override
    public ShortStatisticsDTO getStatistics() {
        return dto;
    }

    @Override
    public void addIntegerData(String data) {
        dto.setCountInteger(dto.getCountInteger() + 1);
    }

    @Override
    public void addFloatData(String data) {
        dto.setCountFloat(dto.getCountFloat() + 1);
    }

    @Override
    public void addStringData(String data) {
        this.dto.setCountString(dto.getCountString() + 1);
    }

    @Override
    public void setFileNames(String[] fileNames){
        dto.setFileNameForInteger(fileNames[0]);
        dto.setFileNameForFloat(fileNames[1]);
        dto.setFileNameForString(fileNames[2]);
    }
}
