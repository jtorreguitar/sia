package ar.edu.itba.sia.problem;

import java.awt.Point;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.LinkedList;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class BoardParser {
    
    public SSState parse(String jsonFilePath) throws FileNotFoundException {
        final FileReader reader = new FileReader(jsonFilePath);
        final JsonParser parser = new JsonParser();
        final JsonBoard jsonBoard = new Gson().fromJson(parser.parse(reader), JsonBoard.class);
        final Square[] squares = parseSquares(jsonBoard);
        final int[][] board = parseBoard(jsonBoard, squares);
        return new SSState(board, squares);
    }

    private Square[] parseSquares(final JsonBoard board) {
        LinkedList<Square> squares = new LinkedList<>();
        for(int i = 0; i < board.blocks.length; i++)
            squares.add(new Square(i,
                                toPoint(board.blocks[i].goal),
                                toPoint(board.blocks[i].initialPosition),
                                board.blocks[i].direction));
        return squares.toArray(new Square[] { });

    }

    private Point toPoint(JsonPoint point) {
        return new Point(point.x, point.y);
    }

    private int[][] parseBoard(final JsonBoard jsonBoard, final Square[] squares) {
        final int[][] board = emptyBoard(jsonBoard.size);
        fillBoard(board, squares);
        return board;
    }

    private void fillBoard(final int[][] board, final Square[] squares) {
        for(Square square : squares)
            board[square.getPosition().y][square.getPosition().x] = square.getId();
    }

    private int[][] emptyBoard(int size) {
        final int[][] board = new int[size][size];
        for(int i = 0; i < board.length; i++)
            Arrays.fill(board[i], SSState.EMPTY_CELL);
        return board;
    }

    private class JsonBoard {
        int size;
        JsonBlock[] blocks;
        JsonPoint[] directionChangePoints;
    }

    private class JsonBlock {
        JsonPoint initialPosition;
        JsonPoint goal;
        Direction direction;
    }

    private class JsonPoint {
        int x;
        int y;
    }
}
