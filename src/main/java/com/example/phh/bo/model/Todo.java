package com.example.phh.bo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class Todo implements Serializable {
    private Long id;
    private String memo;
    private LocalDate dueDate;
    private LocalDateTime modifiedDateTime;

    public Todo() {

    }

    public Todo(Long id, String memo, LocalDate dueDate, LocalDateTime modifiDateTime) {
        this.id = id;
        this.memo = memo;
        this.dueDate = dueDate;
        this.modifiedDateTime = modifiDateTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getModifiedDateTime() {
        return this.modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }
}