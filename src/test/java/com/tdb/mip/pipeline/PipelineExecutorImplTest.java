package com.tdb.mip.pipeline;

import static org.truth0.Truth.ASSERT;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

public class PipelineExecutorImplTest {

    private PipelineExecutor pipelineExecutor;

    @Before
    public void setUp() throws Exception {
        pipelineExecutor = new PipelineExecutorImpl();
    }

    @Test(timeout = 1000)
    public void wait_until_no_more_pending_task() {
        final CountDownLatch taskExecutedCountDown = new CountDownLatch(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    ASSERT.fail(e.getMessage());
                }
                taskExecutedCountDown.countDown();
            }
        };

        pipelineExecutor.execute(runnable);
        pipelineExecutor.execute(runnable);
        pipelineExecutor.execute(runnable);

        ASSERT.that(taskExecutedCountDown.getCount() > 0);
        pipelineExecutor.waitUntilEverythingHasBeenExecuted();
        ASSERT.that(taskExecutedCountDown.getCount()).isEqualTo(0);
    }


}
