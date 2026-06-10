
package net.hackyourfuture.backend.week6.postify.exception;

public class LyricsNotFoundException extends RuntimeException {

    public LyricsNotFoundException(String artistName, String trackTitle) {
        super("Lyrics not found for '" + trackTitle + "' by " + artistName);
    }

}



