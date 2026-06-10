package net.hackyourfuture.backend.week6.postify.exception;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(int trackId) {
        super("Track with id " + trackId + " not found");
    }
}
