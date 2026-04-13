package com.board.menu;

import com.board.exception.BusinessException;
import com.board.model.Board;
import com.board.service.BoardService;
import com.board.util.InputHelper;

public class MainMenu {
    private final BoardService boardService = new BoardService();

    public void start() {
        int option;
        do {
            System.out.println("\n=== BOARD MASTER ===");
            System.out.println("1 - Criar novo board");
            System.out.println("2 - Selecionar board existente");
            System.out.println("3 - Listar todos os boards");
            System.out.println("4 - Excluir board");
            System.out.println("0 - Sair");
            option = InputHelper.readInt("Opção: ");

            switch (option) {
                case 1:
                    createBoard();
                    break;
                case 2:
                    selectBoard();
                    break;
                case 3:
                    listBoards();
                    break;
                case 4:
                    deleteBoard();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void createBoard() {
        String name = InputHelper.readString("Nome do board: ");
        String description = InputHelper.readString("Descrição: ");
        try {
            Board board = boardService.createBoard(name, description);
            System.out.println("Board criado com sucesso! ID: " + board.getId());
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listBoards() {
        try {
            var boards = boardService.getAllBoards();
            if (boards.isEmpty()) {
                System.out.println("Nenhum board cadastrado.");
            } else {
                System.out.println("=== BOARDS ===");
                for (Board b : boards) {
                    System.out.println(b.getId() + " - " + b.getName());
                }
            }
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void selectBoard() {
        listBoards();
        Long id = (long) InputHelper.readInt("Digite o ID do board: ");
        try {
            Board board = boardService.loadBoardWithDetails(id);
            if (board != null) {
                BoardMenu boardMenu = new BoardMenu(board);
                boardMenu.show();
            } else {
                System.out.println("Board não encontrado.");
            }
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void deleteBoard() {
        listBoards();
        Long id = (long) InputHelper.readInt("Digite o ID do board a excluir: ");
        try {
            boardService.deleteBoard(id);
            System.out.println("Board excluído com sucesso.");
        } catch (BusinessException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}