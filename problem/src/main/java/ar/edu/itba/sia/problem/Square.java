package ar.edu.itba.sia.problem;

import java.awt.Point;

public class Square {

    private final int id;
    private final Point goal;
    private final Point position;
    private final Direction direction;

    /* package */ Square(final int id, final Point goal, final Point position, final Direction direction) {
        this.id = id;
        this.goal = goal;
        this.position = position;
        this.direction = direction;
    }

    /* package */ Square(Square square) {
        this.id = square.id;
        this.goal = new Point(square.goal);
        this.position = new Point(square.position);
        this.direction = square.direction;
    }

    private Square(final Square square, Direction direction) {
        this.id = square.id;
        this.goal = new Point(square.goal);
        this.position = new Point(square.position);
        this.direction = direction;
    }

    private Square(final Square square, Point point) {
        this.id = square.id;
        this.goal = new Point(square.goal);
        this.direction = square.direction;
        this.position = new Point(point);
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Square))
            return false;
        final Square square = (Square) obj;
        return this.id == square.id && this.position.equals(square.position);
    }

    public Square changeDirection(final Square oldSquare, final Direction newDirection) {
        return new Square(oldSquare, newDirection);
    }

    /* package */ Square move() {
        return new Square(this, findNewPosition(position, direction));
    }

    /* package */ Square push(final Square pushingSquare) {
        return new Square(this, findNewPosition(position, pushingSquare.direction));
    }

    /* package */ static Point findNewPosition(final Point oldPosition, final Direction direction) {
        switch (direction) {
            case UP: return new Point(oldPosition.x, oldPosition.y + 1);
            case DOWN: return new Point(oldPosition.x, oldPosition.y - 1);
            case LEFT: return new Point(oldPosition.x - 1, oldPosition.y);
            case RIGHT: return new Point(oldPosition.x + 1, oldPosition.y);
        }
        return oldPosition;
    }

    /* package */ int getId() {
        return id;
    }

    /* package */ Point getGoal() {
        return goal;
    }

    /* package */ Point getPosition() {
        return position;
    }

    /* package */ Direction getDirection() {
        return direction;
    }
}
