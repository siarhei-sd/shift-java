package com.javaded78.service;

import com.javaded78.config.AppConfig;
import com.javaded78.domain.stat.Statistics;

import java.util.List;

public interface Processor {

    List<Statistics> process(AppConfig config);
}
