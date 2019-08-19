package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.State;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class SSState implements State {

    private static final int EMPTY_CELL = -1;

    private int[][] board;
    private Square[] squares;

    public SSState(int[][] board, Square[] squares) {
        this.board = board;
        this.squares = squares;
    }

    private SSState(SSState state, Square square) {
        this.board = state.board;
        this.squares = state.squares;
        this.squares[square.getId()] = square;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SSState))
            return false;
        SSState state = (SSState) obj;
        return Arrays.equals(this.board, state.board) &&
                Arrays.equals(this.squares, state.squares);
    }

    public Optional<State> move(Square square, Direction direction) {
        final Square newSquare = Square.changePosition(square, direction);
        if(isOutOfBounds(newSquare.getPosition()))
            return Optional.empty();
        final SSState newState = new SSState(this, square);
        if(board[newSquare.getPosition().x][newSquare.getPosition().y] != EMPTY_CELL)
            return newState.move(getSquare(newSquare.getPosition()) ,direction);
        return Optional.of(newState);
    }

    private boolean isOutOfBounds(Point point) {
        return point.x >= 0 && point.x < board[0].length &&
                point.y >= 0 && point.y < board.length;
    }

    private Square getSquare(Point position) {
        return squares[getSquareId(position)];
    }

    private int getSquareId(Point position) {
        return board[position.x][position.y];
    }

    @Override
    public String getRepresentation() {
        return null;
    }

    public Square[] getSquares() {
        return squares;
    }
}
