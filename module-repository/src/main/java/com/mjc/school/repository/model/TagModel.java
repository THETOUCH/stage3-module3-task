package com.mjc.school.repository.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tags") //
public class TagModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<NewsModel> news;

    public TagModel(
            Long id, String name
    ) {
        this.id = id;
        this.name = name;
    }

    public TagModel() {

    }

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<NewsModel> getNews() {
        return news;
    }

    public void setNews(List<NewsModel> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagModel tagModel)) return false;
        return Objects.equals(id, tagModel.id) && Objects.equals(name, tagModel.name) && Objects.equals(news, tagModel.news);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, news);
    }
}
