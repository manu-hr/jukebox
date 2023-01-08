package com.songs.playsong;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface AudioPlayerInterface {
    public void play();

    public void pause();

    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException;

    public void forwardAudio();

    public void rewindAudio();

    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    public void stop();

}
