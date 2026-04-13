package com.board.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<BoardColumn> columns = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();

    public Board() {}

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters e setters (todos)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<BoardColumn> getColumns() { return columns; }
    public void setColumns(List<BoardColumn> columns) { this.columns = columns; }
    public List<Label> getLabels() { return labels; }
    public void setLabels(List<Label> labels) { this.labels = labels; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}