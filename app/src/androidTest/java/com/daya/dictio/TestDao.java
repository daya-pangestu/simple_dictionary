package com.daya.dictio;

import com.daya.dictio.model.db.DictIdDao;
import com.daya.dictio.model.db.DictIndoDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(JUnit4.class)
public class TestDao {

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    private DictIndoDatabase dictIndoDatabase;
    private DictIdDao dictIdDao;


    @Before
    public void initdb() {
        dictIndoDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                DictIndoDatabase.class)
                .allowMainThreadQueries()
                .build();

        dictIdDao = dictIndoDatabase.dictIdDao();
    }

    @After
    public void closedb() {
        dictIndoDatabase.close();
    }


}
