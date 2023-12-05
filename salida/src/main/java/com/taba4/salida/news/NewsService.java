package com.taba4.salida.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<NewsVo> findAll() {
        return new ArrayList<>(newsRepository.findAll());
    }
}
