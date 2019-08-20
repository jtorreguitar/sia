package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.State;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

public class SSState implements State {

    public static final int EMPTY_CELL = -1;

    private final int[][] board;
    private final Square[] squares;

    public SSState(int[][] board, Square[] squares) {
        this.board = board;
        this.squares = squares;
    }

    private SSState(SSState state) {
        this.board = state.board;
        this.squares = state.squares;
    }

    private SSState setSquareInBoard(Square square) {
        SSState ret = new SSState(this);
        ret.squares[square.getId()] = square;
        validateSquareSetting(ret, square);
        ret.board[square.getPosition().x][square.getPosition().y] = square.getId();
        return ret;
    }

    private static void validateSquareSetting(SSState state, Square square) {
        if(state.isOutOfBounds(square.getPosition()))
            throw new ArrayIndexOutOfBoundsException("the square lays outside the board boundries");
        if(state.isOccupied(square.getPosition()))
            throw new IllegalArgumentException("the board position you want to occupy is already taken");
    }

    private boolean isOutOfBounds(Point point) {
        return point.x >= 0 && point.x < board[0].length &&
                point.y >= 0 && point.y < board.length;
    }

    private boolean isOccupied(Point position) {
        return board[position.x][position.y] != EMPTY_CELL;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SSState))
            return false;
        SSState state = (SSState) obj;
        return Arrays.equals(this.board, state.board) &&
                Arrays.equals(this.squares, state.squares);
    }

    public Optional<State> moveSquare(Square square, Direction direction) {
        final Square newSquare = square.move();
        if(isOutOfBounds(newSquare.getPosition()))
            return Optional.empty();
        if(board[newSquare.getPosition().x][newSquare.getPosition().y] == EMPTY_CELL)
            return Optional.of(setSquareInBoard(newSquare));
        final Optional<State> maybeState = pushSquare(square, findNeighbour(square, direction));
        if(maybeState.isPresent())
            return Optional.of(((SSState)maybeState.get()).setSquareInBoard(newSquare));
        return Optional.empty();
    }

    private Optional<State> pushSquare(Square pushingSquare, Square pushedSquare) {
        final Square newSquare = pushedSquare.push(pushingSquare);
        if(isOutOfBounds(newSquare.getPosition()))
            return Optional.empty();
        if(board[newSquare.getPosition().x][newSquare.getPosition().y] == EMPTY_CELL)
            return Optional.of(setSquareInBoard(newSquare));
        final Optional<State> maybeState = pushSquare(pushingSquare, findNeighbour(pushedSquare, pushingSquare.getDirection()));
        if(maybeState.isPresent())
            return Optional.of(((SSState)maybeState.get()).setSquareInBoard(newSquare));
        return Optional.empty();
    }

    private Square findNeighbour(Square square, Direction direction) {
        return getSquare(Square.findNewPosition(square.getPosition(), direction));
    }

    private Square getSquare(Point position) {
        return getSquare(getSquareId(position));
    }

    private int getSquareId(Point position) {
        return board[position.x][position.y];
    }

    public Square getSquare(int id) {
        return squares[id];
    }

    @Override
    public String getRepresentation() {
        return null;
    }

    public Square[] getSquares() {
        return squares;
    }

    public int[][] getBoard() {
        return board;
    }
}
