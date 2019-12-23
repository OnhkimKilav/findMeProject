package com.findme.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST")
public class Post {
    @Id
    @Column(name = "POST_ID")
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    private Long id;

    @Column(name = "POST")
    private String post;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_POSTED_ID", nullable=false)
    private User userPosted;
    //TODO
    //levels permissions

    //TODO
    //comments


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public User getUserPosted() {
        return userPosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", post='" + post + '\'' +
                ", datePosted=" + datePosted +
                ", userPosted=" + userPosted +
                '}';
    }
}
