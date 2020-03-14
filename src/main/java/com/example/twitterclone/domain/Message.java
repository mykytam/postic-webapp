package com.example.twitterclone.domain;

import com.example.twitterclone.domain.util.MessageHelper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Message {
    @Id // идентификатор, чтобы различать две записи в одной таблице
    @GeneratedValue(strategy= GenerationType.AUTO) // фреймворк и база данных сами разбираются, как генерируются идентификаторы
    private Long id;

    @NotBlank(message = "Please fill in the message")
    @Length(max = 2048, message = "Message is too long")
    private String text;
    @Length(max = 255, message = "Tag is too long")
    private String tag;

    // меппинг, указание по поводу хранения в БД
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> likes = new HashSet<>();


    public Message() {}

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    // проверка наличия автора
    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public Set<User> getLikes() { return likes; }

    public void setLikes(Set<User> likes) { this.likes = likes; }
}
