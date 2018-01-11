package com.server;

import com.server.User.User;

import java.awt.image.BufferedImage;

public class Photo {
    private final BufferedImage image;
    private final User author;

    public int getHash() {
        return hash;
    }

    private final int hash;
    private int rating;

    public BufferedImage getImage() {
        return image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Photo(BufferedImage image, int rating, User author) {
        this.image = image;
        this.rating = rating;
        this.hash = image.hashCode();
        this.author = author;
    }

    public boolean equals(Photo obj) {
        boolean t = super.equals(obj);
        return t && obj.hash == hash && obj.rating == rating;
    }
}
