package com.taba4.salida.news;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emergency_news")
public class NewsVo {

    @Id
    @Column(name = "news_id")
    private Long newsId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "summary")
    private String summary;

    @Column(name = "link")
    private String link;

    @Column(name = "emergency_type")
    private String emergencyType;  //추후 Enum으로 변경할 것
    
}
