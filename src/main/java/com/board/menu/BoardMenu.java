package com.board.menu;

import com.board.exception.BusinessException;
import com.board.model.Board;
import com.board.model.BoardColumn;
import com.board.model.Card;
import com.board.service.CardService;
import com.board.util.InputHelper;

public class BoardMenu {
    private final Board board;
    private final CardService cardService = new CardService();

    public BoardMenu(Board board) {
        this.board = board;
    }

    public void show() {
        int option;
        do {
            System.out.println("\n=== BOARD: " + board.getName() + " ===");
            listColumnsWithCards();
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Adicionar card");
            System.out.println("2 - Mover card");
            System.out.println("3 - Remover card");
            System.out.println("0 - Voltar");
            option = InputHelper.readInt("Opção: ");

            switch (option) {
                case 1:
                    addCard();
                    break;
                case 2:
                    moveCard();
                    break;
                case 3:
                    removeCard();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void listColumnsWithCards() {
        for (BoardColumn column : board.getColumns()) {
            System.out.println("\n--- " + column.getName() + " ---");
            try {
                var cards = cardService.findCardsByColumn(column.getId());
                if (cards.isEmpty()) {
                    System.out.println("  (vazio)");
                } else {
                    for (Card c : cards) {
                        System.out.printf("  %d - %s (prazo: %s)%n", c.getId(), c.getTitle(),
                                c.getDueDate() != null ? c.getDueDate() : "sem prazo");
                    }
                }
            } catch (BusinessException e) {
                System.out.println("Erro ao carregar cards: " + e.getMessage());
            }
        }
    }

    private void addCard() {
        String title = InputHelper.readString("Título: ");
        String description = InputHelper.readString("Descrição: ");
        // Selecionar coluna inicial (primeira coluna do board)
        BoardColumn initialColumn = board.getColumns().stream().findFirst().orElse(null);
        if (initialColumn == null) {
            System.out.println("Board sem colunas.");
            return;
        }
        try {
            cardService.createCard(title, description, initialColumn.getId(), null, null);
            System.out.println("Card adicionado com sucesso!");
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void moveCard() {
        long cardId = InputHelper.readInt("ID do card a mover: ");
        System.out.println("Colunas disponíveis:");
        for (int i = 0; i < board.getColumns().size(); i++) {
            System.out.println(i + " - " + board.getColumns().get(i).getName());
        }
        int colIndex = InputHelper.readInt("Nova coluna (índice): ");
        if (colIndex < 0 || colIndex >= board.getColumns().size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Long newColumnId = board.getColumns().get(colIndex).getId();
        try {
            cardService.moveCard(cardId, newColumnId);
            System.out.println("Card movido com sucesso.");
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removeCard() {
        long cardId = InputHelper.readInt("ID do card a remover: ");
        try {
            cardService.deleteCard(cardId);
            System.out.println("Card removido.");
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}