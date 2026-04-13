package com.board.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {
    private Long id;
    private String title;
    private String description;
    private Long columnId;
    private Long assignedUserId;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private List<Label> labels = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();

    public Card() {}

    public Card(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getColumnId() { return columnId; }
    public void setColumnId(Long columnId) { this.columnId = columnId; }
    public Long getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<Label> getLabels() { return labels; }
    public void setLabels(List<Label> labels) { this.labels = labels; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", columnId=" + columnId +
                '}';
    }
}