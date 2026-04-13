package com.board.dao;

import com.board.config.DatabaseConfig;
import com.board.model.Board;
import com.board.model.BoardColumn;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardDao implements GenericDao<Board, Long> {
    private final DatabaseConfig dbConfig = DatabaseConfig.getInstance();

    @Override
    public void create(Board board) throws SQLException {
        String sql = "INSERT INTO boards (name, description, created_at) VALUES (?, ?, ?)";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, board.getName());
            stmt.setString(2, board.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                board.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public Board findById(Long id) throws SQLException {
        String sql = "SELECT * FROM boards WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                board.setDescription(rs.getString("description"));
                board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return board;
            }
        }
        return null;
    }

    @Override
    public List<Board> findAll() throws SQLException {
        List<Board> boards = new ArrayList<>();
        String sql = "SELECT * FROM boards ORDER BY id";
        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Board board = new Board();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                board.setDescription(rs.getString("description"));
                board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                boards.add(board);
            }
        }
        return boards;
    }

    @Override
    public void update(Board board) throws SQLException {
        String sql = "UPDATE boards SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, board.getName());
            stmt.setString(2, board.getDescription());
            stmt.setLong(3, board.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM boards WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // Métodos extras específicos
    public List<BoardColumn> findColumnsByBoardId(Long boardId) throws SQLException {
        String sql = "SELECT * FROM board_columns WHERE board_id = ? ORDER BY `order`";
        List<BoardColumn> columns = new ArrayList<>();
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BoardColumn col = new BoardColumn();
                col.setId(rs.getLong("id"));
                col.setBoardId(rs.getLong("board_id"));
                col.setName(rs.getString("name"));
                col.setOrder(rs.getInt("order"));
                col.setKind(rs.getString("kind"));
                columns.add(col);
            }
        }
        return columns;
    }
}