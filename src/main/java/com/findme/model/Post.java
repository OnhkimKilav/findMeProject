package com.findme.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "POST")
public class Post {
    @Id
    @Column(name = "POST_ID")
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "USERS_TAGGED")
    @OneToMany(mappedBy="id", cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
    private Set<User> usersTagged;

    @OneToOne(cascade = CascadeType.MERGE  )
    //@JoinColumn(name = "USER_POSTED_FK")
    @PrimaryKeyJoinColumn
    private User userPosted;

    @OneToOne(cascade = CascadeType.MERGE  )
    //@JoinColumn(name = "USER_PAGE_POSTED_FK")
    @PrimaryKeyJoinColumn
    private User userPagePosted;

    public Post() {
    }

    public Post(String message, Date datePosted, String location, Set<User> usersTagged, User userPosted, User userPagePosted) {
        this.message = message;
        this.datePosted = datePosted;
        this.location = location;
        this.usersTagged = usersTagged;
        this.userPosted = userPosted;
        this.userPagePosted = userPagePosted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<User> getUsersTagged() {
        return usersTagged;
    }

    public void setUsersTagged(Set<User> usersTagged) {
        this.usersTagged = usersTagged;
    }

    public User getUserPosted() {
        return userPosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    public User getUserPagePosted() {
        return userPagePosted;
    }

    public void setUserPagePosted(User userPagePosted) {
        this.userPagePosted = userPagePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(message, post.message) &&
                Objects.equals(datePosted, post.datePosted) &&
                Objects.equals(location, post.location) &&
                Objects.equals(usersTagged, post.usersTagged) &&
                Objects.equals(userPosted, post.userPosted) &&
                Objects.equals(userPagePosted, post.userPagePosted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, message, datePosted, location, usersTagged, userPosted, userPagePosted);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", datePosted=" + datePosted +
                ", location='" + location + '\'' +
                ", usersTagged=" + usersTagged +
                ", userPosted=" + userPosted +
                ", userPagePosted=" + userPagePosted +
                '}';
    }
}
