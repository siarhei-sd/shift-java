package com.javaded78.service.sender;

import com.javaded78.domain.stat.Statistics;

import java.util.List;

public interface Sender {

    void send(List<Statistics> statistics);
}
