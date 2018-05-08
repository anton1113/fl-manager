package com.arash.flm.gl.executor;

import com.arash.flm.gl.worker.ObsoleteEmailsDeleteJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by anton on 30.04.18.
 *
 */
@Component
public class GfExecutor {

    @Autowired private ObsoleteEmailsDeleteJob obsoleteEmailsDeleteJob;

    @PostConstruct
    private void init() {

        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(obsoleteEmailsDeleteJob, 0, 1, TimeUnit.DAYS);
    }
}
