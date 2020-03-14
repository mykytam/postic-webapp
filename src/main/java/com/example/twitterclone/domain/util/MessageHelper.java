package com.example.twitterclone.domain.util;

import com.example.twitterclone.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
            return author != null ? author.getUsername() : "<no author>";
    }
}
