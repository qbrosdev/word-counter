package com.qbros;

import com.qbros.model.FileWordCountResult;
import com.qbros.model.ResultEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


@RunWith(PowerMockRunner.class)
@PrepareForTest(FileReader.class)
public class ParallelWordCounterTest {

    private static final int NUMBER_OF_ENTRIES = 1_000_000;
    private ParallelWordCounter parallelWordCounterUnderTest;
    private FileReader fileReaderMock;
    private static List<String> mockedInput;

    static {
        mockedInput = IntStream.rangeClosed(1, NUMBER_OF_ENTRIES)
                .mapToObj(String::valueOf).collect(Collectors.toList());
    }

    @Before
    public void setUp() {
        fileReaderMock = PowerMockito.spy(new FileReader(null, false));
        parallelWordCounterUnderTest = new ParallelWordCounter(fileReaderMock);
    }

    @Test
    public void getWordsCount() throws Exception {
        PowerMockito.doReturn(mockedInput.stream())
                .when(fileReaderMock, "getWordsStream");
        PowerMockito.doReturn("test")
                .when(fileReaderMock, "getInputId");

        FileWordCountResult result = parallelWordCounterUnderTest.countWords();

        assertEquals(NUMBER_OF_ENTRIES, result.getEntries().size());
    }

    @Test
    public void getWordsCount_resultEntries() throws Exception {
        PowerMockito.doReturn(Stream.of("Abc", "xyz", "XYZ"))
                .when(fileReaderMock, "getWordsStream");
        PowerMockito.doReturn("test")
                .when(fileReaderMock, "getInputId");

        FileWordCountResult result = parallelWordCounterUnderTest.countWords();
        FileWordCountResult expected = new FileWordCountResult(new HashSet<>(
                Arrays.asList(new ResultEntry("abc", 1, "test")
                        , new ResultEntry("xyz", 2, "test"))));

        assertEquals(expected, result);
    }
}