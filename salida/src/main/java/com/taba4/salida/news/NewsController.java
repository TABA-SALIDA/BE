package com.taba4.salida.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NewsVo> getAllNews() {
        return newsService.findAll();
    }

}
