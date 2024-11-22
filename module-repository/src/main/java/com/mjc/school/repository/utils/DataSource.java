package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mjc.school.repository.model.data.AuthorData.getAuthorData;
import static com.mjc.school.repository.model.data.NewsData.getNewsData;

@Component
public class DataSource {
    private final List<NewsModel> news;
    private final List<AuthorModel> authors;

    private DataSource() {
        authors = getAuthorData().getAuthorList();
        news = getNewsData(authors).getNewsList();
    }

    public List<NewsModel> getNews() {
        return news;
    }
    public List<AuthorModel> getAuthors() {
        return authors;
    }

    /*public static DataSource getInstance() {
        return LazyDataSource.INSTANCE;
    }

    private static class LazyDataSource {
        static final DataSource INSTANCE = new DataSource();
    }*/
}
