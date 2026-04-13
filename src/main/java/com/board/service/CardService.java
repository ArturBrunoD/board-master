package com.board.service;

import com.board.dao.CardDao;
import com.board.exception.BusinessException;
import com.board.model.Card;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CardService {
    private final CardDao cardDao = new CardDao();

    public Card createCard(String title, String description, Long columnId, Long assignedUserId, LocalDate dueDate) throws BusinessException {
        if (title == null || title.trim().isEmpty()) {
            throw new BusinessException("Título é obrigatório");
        }
        Card card = new Card(title, description);
        card.setColumnId(columnId);
        card.setAssignedUserId(assignedUserId);
        card.setDueDate(dueDate);
        try {
            cardDao.create(card);
            return card;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao criar card: " + e.getMessage(), e);
        }
    }

    public List<Card> findCardsByColumn(Long columnId) throws BusinessException {
        try {
            return cardDao.findByColumnId(columnId);
        } catch (SQLException e) {
            throw new BusinessException("Falha ao listar cards", e);
        }
    }

    public void moveCard(Long cardId, Long newColumnId) throws BusinessException {
        try {
            cardDao.moveCard(cardId, newColumnId);
        } catch (SQLException e) {
            throw new BusinessException("Falha ao mover card", e);
        }
    }

    public void deleteCard(Long cardId) throws BusinessException {
        try {
            cardDao.delete(cardId);
        } catch (SQLException e) {
            throw new BusinessException("Falha ao remover card", e);
        }
    }
}