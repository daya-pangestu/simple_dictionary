package com.daya.dictio;

import com.daya.dictio.view.MainActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private testing testing;
    @Before
    public void init() {
        testing = new testing();
    }

    @Test
    public void testing() {
        assertEquals(4, testing.tambah(2, 2));
    }
}