package ar.edu.itba.sia.problem;

import java.awt.Point;

public class Square {

    private final int id;
    private final Point goal;
    private final Point position;
    private final Direction direction;

    public Square(final int id, final Point goal, final Point position, final Direction direction) {
        this.id = id;
        this.goal = goal;
        this.position = position;
        this.direction = direction;
    }

    private Square(final Square square, Direction direction) {
        this.id = square.id;
        this.goal = square.goal;
        this.position = square.position;
        this.direction = direction;
    }

    private Square(final Square square, Point point) {
        this.id = square.id;
        this.goal = square.goal;
        this.direction = square.direction;
        this.position = point;
    }

    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Square))
            return false;
        final Square square = (Square) obj;
        return this.position.equals(square.position) &&
                this.goal.equals(square.goal);
    }

    public static Square withNewDirection(final Square oldSquare, final Direction newDirection) {
        return new Square(oldSquare, newDirection);
    }

    public static Square changePosition(final Square oldSquare, final Direction direction) {
        if(oldSquare.direction != direction)
            return null;
        return new Square(oldSquare, findNewPosition(oldSquare.position, direction));
    }

    private static Point findNewPosition(final Point oldPosition, final Direction direction) {
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
}
