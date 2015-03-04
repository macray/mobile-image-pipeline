package com.tdb.mip.pipeline;

import com.tdb.mip.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class IOSPipelineFactoryTest {

    private IOSPipelineFactory factory;

    @Mock
    private Configuration config;

    @Before
    public void setUp() throws IOException {
        factory = new IOSPipelineFactory();
    }

    @Test
    public void no_special_behavior_for_ios_yet() {
        // placeholder
    }

}