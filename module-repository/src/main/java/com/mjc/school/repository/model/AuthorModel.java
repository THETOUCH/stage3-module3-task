package com.mjc.school.repository.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class AuthorModel implements BaseEntity<Long> {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;
    public AuthorModel(Long id, String name, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdatedDate = lastUpdateDate;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorModel that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, lastUpdatedDate);
    }
}
