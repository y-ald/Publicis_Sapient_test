package org.mowItNow.mower.models;

import java.util.UUID;

public class Mower {
    private UUID id = UUID.randomUUID();
    private int xPosition;
    private int yPosition;
    private Orientation orientation;
    private int maxX;
    private int maxY;

    public Mower(UUID id, int xPosition, int yPosition, Orientation orientation, int maxX, int maxY) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.orientation = orientation;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mower mower = (Mower) o;

        return id.equals(mower.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void rotateRight() {
        switch (orientation) {
            case N :
                orientation = Orientation.E;
                break;
            case E:
                orientation = Orientation.S;
                break;
            case S:
                orientation = Orientation.W;
                break;
            case W:
                orientation = Orientation.N;
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    public void rotateLeft() {
        switch (orientation) {
            case N:
                orientation = Orientation.W;
                break;
            case W:
                orientation = Orientation.S;
                break;
            case S:
                orientation = Orientation.E;
                break;
            case E:
                orientation = Orientation.N;
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    public void moveForward() {
        checkPositionmoveForward();
        switch (orientation) {
            case N:
                if (yPosition < maxY) yPosition++;
                break;
            case E:
                if (xPosition < maxX) xPosition++;
                break;
            case S:
                if (yPosition > 0) yPosition--;
                break;
            case W:
                if (xPosition > 0) xPosition--;
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    public void moveBackward() {
        checkPositionmoveBackward();
        switch (orientation) {
            case N:
                if (yPosition > 0) yPosition--;
                break;
            case E:
                if (xPosition > 0) xPosition--;
                break;
            case S:
                if (yPosition < maxY) yPosition++;
                break;
            case W:
                if (xPosition < maxX) xPosition++;
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    public UUID getId() {
        return id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    private void checkPositionmoveForward() {
        switch (orientation) {
            case N:
                if (yPosition >= maxY) throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the Y axis \{maxY}");
                break;
            case E:
                if (xPosition >= maxX) throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the X axis \{maxX}");
                break;
            case S:
                if (yPosition <= 0) throw new RuntimeException("the dimensions of the lawn have been exceeded on the Y axis");
                break;
            case W:
                if (xPosition <= 0) throw new RuntimeException("the dimensions of the lawn have been exceeded on the X axis");
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    private void checkPositionmoveBackward() {
        switch (orientation) {
            case N:
                if (yPosition <= 0) throw new RuntimeException("the dimensions of the lawn have been exceeded on the Y axis");
                break;
            case E:
                if (xPosition <= 0) throw new RuntimeException("the dimensions of the lawn have been exceeded on the X axis");
                break;
            case S:
                if (yPosition >= maxY) throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the Y axis \{maxY}");
                break;
            case W:
                if (xPosition >= maxX) throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the X axis \{maxX}");
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    @Override
    public String toString() {
        return xPosition + " " + yPosition + " " + orientation.toString();
    }
}
