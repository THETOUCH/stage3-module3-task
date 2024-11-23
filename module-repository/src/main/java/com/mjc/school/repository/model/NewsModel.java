package com.mjc.school.repository.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="news") //
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorModel author;

    @ManyToMany
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagModel> tags;

    public NewsModel(
            Long id,
            String title,
            String content,
            LocalDateTime createDate,
            LocalDateTime lastUpdatedDate,
            AuthorModel author,
            List<TagModel> tags
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.author = author;
        this.tags = tags;
    }
    public NewsModel() {

    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public AuthorModel getAuthor() {
        return author;
    }

    public void setAuthor(AuthorModel author) {
        this.author = author;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsModel newsModel)) return false;
        return Objects.equals(id, newsModel.id) && Objects.equals(title, newsModel.title) && Objects.equals(content, newsModel.content) && Objects.equals(createDate, newsModel.createDate) && Objects.equals(lastUpdatedDate, newsModel.lastUpdatedDate) && Objects.equals(author, newsModel.author)
                && Objects.equals(tags, newsModel.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createDate, lastUpdatedDate, author, tags);
    }
}
