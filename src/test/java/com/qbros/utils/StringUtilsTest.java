package com.qbros.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {


    @Test
    public void strAppend_Null() {
        String result = StringUtils.strAppend(null);
        Assert.assertEquals("", result);
    }

    @Test
    public void strAppend_SingleString() {
        String result = StringUtils.strAppend("Str1");
        Assert.assertEquals("Str1", result);
    }

    @Test
    public void strAppend_MultipleStrings() {
        String result = StringUtils.strAppend("Str1", "Str2");
        Assert.assertEquals("Str1 Str2", result);
    }

}