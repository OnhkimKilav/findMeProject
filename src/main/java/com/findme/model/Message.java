package com.findme.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "message")
public class Message {

    @Id
    @SequenceGenerator(name = "MESSAGE_SEQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_sent")
    private Date dateSent;

    @Column(name = "date_read")
    private Date dateRead;

    @ManyToOne
    @JoinColumn(name="user_from_id", nullable=false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name="user_to_id", nullable=false)
    private User userTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateSent=" + dateSent +
                ", dateRead=" + dateRead +
                ", userFrom=" + userFrom +
                ", userTo=" + userTo +
                '}';
    }
}
