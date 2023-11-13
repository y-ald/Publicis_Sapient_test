package org.mowItNow;

public class Mower {
    private int x;
    private int y;
    private char orientation;
    private int maxX;
    private int maxY;

    public Mower(int x, int y, char orientation, int maxX, int maxY) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOrientation() {
        return orientation;
    }

    public void processInstructions(String instructions) {
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'D':
                    rotateRight();
                    break;
                case 'G':
                    rotateLeft();
                    break;
                case 'A':
                    moveForward();
                    break;
                case 'B':
                    moveBackward();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction: " + instruction);
            }
        }
    }

    private void rotateRight() {
        switch (orientation) {
            case 'N':
                orientation = 'E';
                break;
            case 'E':
                orientation = 'S';
                break;
            case 'S':
                orientation = 'W';
                break;
            case 'W':
                orientation = 'N';
                break;
        }
    }

    private void rotateLeft() {
        switch (orientation) {
            case 'N':
                orientation = 'W';
                break;
            case 'W':
                orientation = 'S';
                break;
            case 'S':
                orientation = 'E';
                break;
            case 'E':
                orientation = 'N';
                break;
        }
    }

    private void moveForward() {
        switch (orientation) {
            case 'N':
                if (y < maxY) y++;
                break;
            case 'E':
                if (x < maxX) x++;
                break;
            case 'S':
                if (y > 0) y--;
                break;
            case 'W':
                if (x > 0) x--;
                break;
        }
    }

    private void moveBackward() {
        switch (orientation) {
            case 'N':
                if (y > 0) y--;
                break;
            case 'E':
                if (x > 0) x--;
                break;
            case 'S':
                if (y < maxY) y++;
                break;
            case 'W':
                if (x < maxX) x++;
                break;
        }
    }

}
