package ar.edu.itba.sia.problem;

import ar.edu.itba.sia.interfaces.State;

import java.awt.*;
import java.util.*;

public class SSState implements State {

    public static final int EMPTY_CELL = -1;

    private final int[][] board;
    private final Square[] squares;

    public SSState(int[][] board, Square[] squares) {
        this.board = board;
        this.squares = squares;
    }

    private SSState(SSState state) {
        this.board = Arrays.stream(state.board).map(i -> Arrays.copyOf(i, i.length)).toArray(int[][]::new);
        this.squares = newSquareArray(state.squares);
    }

    private Square[] newSquareArray(Square[] squares) {
        return Arrays.stream(squares)
                    .map(Square::new)
                    .toArray(Square[]::new);
    }

    private SSState setSquareInBoard(Square square) {
        SSState ret = new SSState(this);
        ret.squares[square.getId()] = square;
        ret.validateSquareSetting(square);
        ret.board[square.getPosition().y][square.getPosition().x] = square.getId();
        return ret;
    }

    private SSState setEmpty(Point position) {
        SSState ret = new SSState(this);
        ret.board[position.y][position.x] = EMPTY_CELL;
        return ret;
    }

    private void validateSquareSetting(Square square) {
        if(isOutOfBounds(square.getPosition()))
            throw new ArrayIndexOutOfBoundsException("the square lays outside the board boundaries");
        if(isOccupied(square.getPosition()))
            throw new IllegalArgumentException("the board position you want to occupy is already taken");
    }

    private boolean isOutOfBounds(Point point) {
        return point.y < 0 || point.y >= board.length ||
                point.x < 0 || point.x >= board[point.y].length;
    }

    private boolean isOccupied(Point position) {
        return board[position.y][position.x] != EMPTY_CELL;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SSState))
            return false;
        SSState state = (SSState) obj;
        return Arrays.deepEquals(this.board, state.board) &&
                Arrays.equals(this.squares, state.squares);
    }

    /* package */ Optional<State> moveSquare(int squareId) {
        final Square square = getSquare(squareId);
        final SSState squareRemovedState = setEmpty(square.getPosition());
        final Square newSquare = square.move();
        return moveOrPush(square, square, newSquare, squareRemovedState);
    }

    private Optional<State> pushSquare(Square pushingSquare, Square pushedSquare) {
        final Square newSquare = pushedSquare.push(pushingSquare);
        final SSState squareRemovedState = setEmpty(pushedSquare.getPosition());
        return moveOrPush(pushingSquare, pushedSquare, newSquare, squareRemovedState);
    }

    private Optional<State> moveOrPush(Square pushingSquare, Square pushedSquare, Square newSquare, SSState squareRemovedState) {
        if(isOutOfBounds(newSquare.getPosition()))
            return Optional.empty();
        if(board[newSquare.getPosition().y][newSquare.getPosition().x] == EMPTY_CELL)
            return Optional.of(squareRemovedState.setSquareInBoard(newSquare));
        final Optional<State> maybeState = squareRemovedState.pushSquare(pushingSquare, findNeighbour(pushedSquare, pushingSquare.getDirection()));
        return maybeState.map(state -> ((SSState) state).setSquareInBoard(newSquare));
    }

    private Square findNeighbour(Square square, Direction direction) {
        return getSquare(Square.findNewPosition(square.getPosition(), direction));
    }

    private Square getSquare(Point position) {
        return getSquare(getSquareId(position));
    }

    private int getSquareId(Point position) {
        return board[position.y][position.x];
    }

    private Square getSquare(int id) {
        return squares[id];
    }

    @Override
    public String getRepresentation() {
        StringBuilder sb = new StringBuilder();
        for(int[] i : board) {
            sb.append(Arrays.toString(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    public Square[] getSquares() {
        return squares;
    }
}
