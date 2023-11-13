package org.mowItNow.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        }
    }

    public void moveForward() {
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
        }
    }

    public void moveBackward() {
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

    @Override
    public String toString() {
        return "This Mower is id:" + id + " and has position x: "+ xPosition + " y: "+ yPosition + " Orientation: " + orientation.toString();
    }
}
