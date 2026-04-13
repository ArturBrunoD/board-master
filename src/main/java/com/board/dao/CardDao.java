package com.board.dao;

import com.board.config.DatabaseConfig;
import com.board.model.Card;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardDao implements GenericDao<Card, Long> {
    private final DatabaseConfig dbConfig = DatabaseConfig.getInstance();

    @Override
    public void create(Card card) throws SQLException {
        String sql = "INSERT INTO cards (title, description, column_id, assigned_user_id, due_date, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, card.getTitle());
            stmt.setString(2, card.getDescription());
            stmt.setLong(3, card.getColumnId());
            stmt.setObject(4, card.getAssignedUserId());
            stmt.setObject(5, card.getDueDate() != null ? Date.valueOf(card.getDueDate()) : null);
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                card.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public Card findById(Long id) throws SQLException {
        String sql = "SELECT * FROM cards WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setColumnId(rs.getLong("column_id"));
                card.setAssignedUserId((Long) rs.getObject("assigned_user_id"));
                Date dueDate = rs.getDate("due_date");
                if (dueDate != null) card.setDueDate(dueDate.toLocalDate());
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return card;
            }
        }
        return null;
    }

    @Override
    public List<Card> findAll() throws SQLException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards ORDER BY id";
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setColumnId(rs.getLong("column_id"));
                card.setAssignedUserId((Long) rs.getObject("assigned_user_id"));
                Date dueDate = rs.getDate("due_date");
                if (dueDate != null) card.setDueDate(dueDate.toLocalDate());
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                cards.add(card);
            }
        }
        return cards;
    }

    @Override
    public void update(Card card) throws SQLException {
        String sql = "UPDATE cards SET title = ?, description = ?, column_id = ?, assigned_user_id = ?, due_date = ? WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getTitle());
            stmt.setString(2, card.getDescription());
            stmt.setLong(3, card.getColumnId());
            stmt.setObject(4, card.getAssignedUserId());
            stmt.setObject(5, card.getDueDate() != null ? Date.valueOf(card.getDueDate()) : null);
            stmt.setLong(6, card.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM cards WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // Métodos específicos
    public List<Card> findByColumnId(Long columnId) throws SQLException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE column_id = ? ORDER BY created_at DESC";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, columnId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setTitle(rs.getString("title"));
                card.setDescription(rs.getString("description"));
                card.setColumnId(rs.getLong("column_id"));
                card.setAssignedUserId((Long) rs.getObject("assigned_user_id"));
                Date dueDate = rs.getDate("due_date");
                if (dueDate != null) card.setDueDate(dueDate.toLocalDate());
                card.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                cards.add(card);
            }
        }
        return cards;
    }

    public void moveCard(Long cardId, Long newColumnId) throws SQLException {
        String sql = "UPDATE cards SET column_id = ? WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, newColumnId);
            stmt.setLong(2, cardId);
            stmt.executeUpdate();
        }
    }
}