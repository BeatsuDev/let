package no.ntnu.let.letapi.model.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Chat chat;

    @Enumerated(EnumType.STRING)
    private Sender sender;
}

