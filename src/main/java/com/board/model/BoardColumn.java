package com.board.model;

import java.util.Objects;

public class BoardColumn {
    private Long id;
    private Long boardId;
    private String name;
    private int order;
    private String kind; // INITIAL, PENDING, FINAL, CANCEL

    public BoardColumn() {}

    public BoardColumn(String name, int order, String kind) {
        this.name = name;
        this.order = order;
        this.kind = kind;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBoardId() { return boardId; }
    public void setBoardId(Long boardId) { this.boardId = boardId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardColumn that = (BoardColumn) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BoardColumn{id=" + id + ", name='" + name + "', order=" + order + ", kind='" + kind + "'}";
    }
}