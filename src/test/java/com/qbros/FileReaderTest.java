package com.qbros;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RunWith(PowerMockRunner.class)
@PrepareForTest(FileReader.class)
public class FileReaderTest {

    private FileReader fileReaderUnderTest;

    @Before
    public void setUp() {
        fileReaderUnderTest = PowerMockito.spy(new FileReader(null, false));
    }

    @Test
    public void extractWords() throws Exception {
        PowerMockito.doReturn(Arrays.asList("pest", "pet", "set", "met"))
                .when(fileReaderUnderTest, "extractLines");

        Stream<String> actual = fileReaderUnderTest.getWordsStream();
        Assert.assertEquals(Arrays.asList("pest", "pet", "set", "met"), actual.collect(Collectors.toList()));
    }

    @Test
    public void extractWords_NoContent() throws Exception {
        PowerMockito.doReturn(Collections.emptyList())
                .when(fileReaderUnderTest, "extractLines");

        Stream<String> actual = fileReaderUnderTest.getWordsStream();
        Assert.assertEquals(Collections.emptyList(), actual.collect(Collectors.toList()));
    }

}
