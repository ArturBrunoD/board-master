package com.board.service;

import com.board.dao.BoardDao;
import com.board.dao.BoardColumnDao;
import com.board.exception.BusinessException;
import com.board.model.Board;
import com.board.model.BoardColumn;
import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private final BoardDao boardDao = new BoardDao();
    private final BoardColumnDao columnDao = new BoardColumnDao();

    public Board createBoard(String name, String description) throws BusinessException {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException("Nome do board não pode ser vazio");
        }
        Board board = new Board(name, description);
        try {
            boardDao.create(board);
            createDefaultColumns(board.getId());
            return board;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao criar board: " + e.getMessage(), e);
        }
    }

    private void createDefaultColumns(Long boardId) throws SQLException {
        String[][] cols = {
                {"A Fazer", "0", "INITIAL"},
                {"Em Progresso", "1", "PENDING"},
                {"Concluído", "2", "FINAL"},
                {"Cancelado", "3", "CANCEL"}
        };
        for (String[] col : cols) {
            BoardColumn column = new BoardColumn(col[0], Integer.parseInt(col[1]), col[2]);
            column.setBoardId(boardId);
            columnDao.create(column);
        }
    }

    public List<Board> getAllBoards() throws BusinessException {
        try {
            return boardDao.findAll();
        } catch (SQLException e) {
            throw new BusinessException("Falha ao listar boards", e);
        }
    }

    public Board loadBoardWithDetails(Long boardId) throws BusinessException {
        try {
            Board board = boardDao.findById(boardId);
            if (board == null) throw new BusinessException("Board não encontrado");
            List<BoardColumn> columns = boardDao.findColumnsByBoardId(boardId);
            board.setColumns(columns);
            return board;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao carregar detalhes do board", e);
        }
    }
}