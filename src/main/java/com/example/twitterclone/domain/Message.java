package com.example.twitterclone.domain;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Message {
    @Id // идентификатор, чтобы различать две записи в одной таблице
    @GeneratedValue(strategy= GenerationType.AUTO) // фреймворк и база данных сами разбираются, как генерируются идентификаторы
    private Integer id;
    private String text;
    private String tag;

    // меппинг, указание по поводу хранения в БД
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public Message() {}

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    // проверка наличия автора
    public String getAuthorName() {
        return author != null ? author.getUsername() : "<no author>";
    }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
