package com.shakhawat.journalapp.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shakhawat.journalapp.enums.Sentiment;
import com.shakhawat.journalapp.util.ObjectIdDeserializer;
import com.shakhawat.journalapp.util.ObjectIdSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journals")
@Data
@NoArgsConstructor
public class Journal {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

    private Sentiment sentiment;

}
