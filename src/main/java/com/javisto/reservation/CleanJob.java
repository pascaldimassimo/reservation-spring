package com.javisto.reservation;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanJob
{
    private static final int DELAY = 15 * 60 * 1000;

    @Scheduled(fixedDelay = DELAY)
    public void clean()
    {
        // TODO
        System.out.println("The time is now : " + new Date());
    }
}
