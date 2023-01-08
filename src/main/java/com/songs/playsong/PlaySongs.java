package com.songs.playsong;

import com.songs.SearchSongs;
import com.songs.Songs;
import com.songs.playlist.ViewPlaylist;
import utils.exceptions.WrongOptionException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;

public class PlaySongs {

    public static void playSong(List<Songs> currentListToPlay)  {

        if(currentListToPlay.size() == 0)
            return;

        int playingIndex = 0;
        AudioPlayer player;
        Songs.displaySongs(currentListToPlay);
        System.out.println();
        Scanner sc = new Scanner(System.in);
        if(currentListToPlay.size() > 1) {
            System.out.println("Enter 1 to play in order 2 to shuffle the list! ");
            int isShuffle = sc.nextInt();
            if(isShuffle == 2){
                Collections.shuffle(currentListToPlay);
                Songs.displaySongs(currentListToPlay);
            }
        }

        try{
            songLoop : while(currentListToPlay.size() > playingIndex) {

                String songName = currentListToPlay.get(playingIndex).getSongName();
                String filePath = "src/main/resources/"+songName+".wav";
                player = new AudioPlayer(filePath);
                player.play();

                while (true)
                {

                    System.out.println("Enter the numbers to perform respective operations!");
                    System.out.println("1. Pause");
                    System.out.println("2. Resume");
                    System.out.println("3. Restart");
                    System.out.println("4. Forward 30s");
                    System.out.println("5. Rewind 30s");
                    System.out.println("6. Next Song");
                    System.out.println("7. Previous Song");
                    System.out.println("8. stop");

                    int c = sc.nextInt();
                    player.gotoChoice(c);
                    if (c==6)
                        break;
                    if (c==7){
                        playingIndex --;
                        if(playingIndex < 0)
                            playingIndex = 0;
                        continue songLoop;
                    }
                    if(c==8)
                        break songLoop;

                }
                playingIndex ++;
            }

            System.out.println("Song Playing Ended!");

        }catch(UnsupportedAudioFileException | LineUnavailableException | IOException e){
            System.out.println(e.getMessage());
            System.out.println("Not Able to play the song");
        }
    }


    public void playAllSongs() throws WrongOptionException {
        try{
            SearchSongs searchSongs = new SearchSongs();
            List<Songs> currentListToPlay;
            currentListToPlay = searchSongs.getAllSongs();
            playSong(currentListToPlay);

        }catch(IndexOutOfBoundsException e){
            throw new WrongOptionException("Entered Option Is Invalid");
        }

    }

    public void playParticularSong() throws WrongOptionException {

        try{
            Scanner sc = new Scanner(System.in);
            SearchSongs searchSongs = new SearchSongs();
            List<Songs> currentListToPlay;
            currentListToPlay = searchSongs.getAllSongs();
            searchSongs.viewAllSongs(currentListToPlay);
            System.out.println("Enter the Song.No to play");
            int option = sc.nextInt();
            List<Songs> newList = new ArrayList<>();
            newList.add(currentListToPlay.get(option-1));
            playSong(newList);

        }catch(IndexOutOfBoundsException e){
            throw new WrongOptionException("Entered Option Is Invalid");
        }
    }

    public void playPlaylist() throws WrongOptionException {

        try{
            Scanner sc = new Scanner(System.in);
            ViewPlaylist view = new ViewPlaylist();
            Map<String, Integer> userPlaylistNames = view.getUserPlaylistNames();
            List<String> playlistName = new ArrayList<>();
            int counter = 0;
            for(Map.Entry<String,Integer> data : userPlaylistNames.entrySet()) {
                playlistName.add(data.getKey());
                System.out.printf("%-8d %s\n", ++counter, data.getKey());
            }
            if(playlistName.size() == 0){
                System.out.println("You Have No Current Playlist! Create a New One");
                return;
            }
            System.out.println("Enter the Playlist No to play the respective playlist");

            int option = sc.nextInt();
            int playlistId = userPlaylistNames.get(playlistName.get(option-1));

            List<Songs> currentListToPlay = view.getPlaylist( playlistId);
            playSong(currentListToPlay);

        }catch(IndexOutOfBoundsException e){
            throw new WrongOptionException("Entered Option Is Invalid");
        }

    }

}
