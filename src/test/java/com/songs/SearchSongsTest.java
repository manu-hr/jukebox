package com.songs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchSongsTest {
    SearchSongs search;
    List<Songs> currentSongPlayList;

    @Before
    public void setup(){
        search = new SearchSongs();
        currentSongPlayList = new ArrayList<>();
    }

    @After
    public void tearDown(){
        search = null;
    }

    @Test
    public void getAllSongsTest(){
        currentSongPlayList = search.getAllSongs();
        assertEquals(4,currentSongPlayList.size());
    }


}
