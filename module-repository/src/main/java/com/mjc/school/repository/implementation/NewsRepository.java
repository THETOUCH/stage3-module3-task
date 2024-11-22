package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
    private final DataSource dataSource;

    public NewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNews();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return dataSource.getNews().stream().filter(news -> news.getId().equals(id)).findFirst();
    }

    @Override
    public NewsModel create(NewsModel entity) {
        List<NewsModel> newsModel = dataSource.getNews();
        newsModel.sort(Comparator.comparing(NewsModel::getId));
        if (!newsModel.isEmpty()) {
            entity.setId(newsModel.get(newsModel.size() - 1).getId() + 1);
        } else {
            entity.setId(1L);
        }
        newsModel.add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel newsModel = readById(entity.getId()).get();
        newsModel.setTitle(entity.getTitle());
        newsModel.setContent(entity.getContent());
        newsModel.setLastUpdatedDate(entity.getLastUpdatedDate());
        newsModel.setAuthorId(entity.getAuthorId());
        return newsModel;
    }

    @Override
    public boolean deleteById(Long id) {
        NewsModel newsModel = readById(id).get();
        return dataSource.getNews().remove(newsModel);
    }

    @Override
    public boolean existById(Long id) {
        return dataSource.getNews().stream().anyMatch(newsModel -> newsModel.getId().equals(id));
    }
}
