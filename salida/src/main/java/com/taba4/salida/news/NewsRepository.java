package com.taba4.salida.news;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsVo, Long> {

    public List<NewsVo> findByTitle(String title);
}
