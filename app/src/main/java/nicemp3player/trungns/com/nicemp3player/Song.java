package nicemp3player.trungns.com.nicemp3player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thanh Nguyen on 7/19/2016.
 */
public class Song {
    private String name, fileName, path, artist, album, time;
    private long duration;
    private boolean isPlaying = false;


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Song(String name, String fileName, String path, String artist, String album, long duration) {
        this.name = name;
        this.fileName = fileName;
        this.path = path;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        convertDate();
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTime() {
        return time;
    }

    public long getDuration() {
        return duration;
    }

    private void convertDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        time = dateFormat.format(new Date(duration));
    }
}
