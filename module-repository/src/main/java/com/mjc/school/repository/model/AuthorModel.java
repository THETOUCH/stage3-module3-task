package com.mjc.school.repository.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="author") //
public class AuthorModel implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<NewsModel> news;



    public AuthorModel(Long id, String name, LocalDateTime createDate, LocalDateTime lastUpdateDate,
                       List<NewsModel> news) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdatedDate = lastUpdateDate;
        this.news = news;
    }
    public AuthorModel() {}
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
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getLastUpdateDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdatedDate = lastUpdateDate;
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
        if (!(o instanceof AuthorModel that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdatedDate, that.lastUpdatedDate)
                && Objects.equals(news, that.news);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, lastUpdatedDate, news);
    }
}
