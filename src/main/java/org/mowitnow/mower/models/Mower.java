package org.mowitnow.mower.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "mowers")
public class Mower {
    @Id
    private final String id = UUID.randomUUID().toString();
    @Column
    private int xPosition;
    @Column
    private int yPosition;
    @Column
    private Orientation orientation;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Ground ground;

    public void rotateRight() {
        switch (orientation) {
            case N:
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
                if (yPosition < ground.getTopRightCorner()) yPosition++;
                break;
            case E:
                if (xPosition < ground.getLowerLeftCorner()) xPosition++;
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
                if (yPosition < ground.getTopRightCorner()) yPosition++;
                break;
            case W:
                if (xPosition < ground.getLowerLeftCorner()) xPosition++;
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    private void checkPositionmoveForward() {
        switch (orientation) {
            case N:
                if (yPosition >= ground.getTopRightCorner())
                    throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the Y axis \{ground.getTopRightCorner()}");
                break;
            case E:
                if (xPosition >= ground.getLowerLeftCorner())
                    throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the X axis \{ground.getLowerLeftCorner()}");
                break;
            case S:
                if (yPosition <= 0)
                    throw new RuntimeException("the dimensions of the lawn have been exceeded on the Y axis");
                break;
            case W:
                if (xPosition <= 0)
                    throw new RuntimeException("the dimensions of the lawn have been exceeded on the X axis");
                break;
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }

    private void checkPositionmoveBackward() {
        switch (orientation) {
            case N:
                if (yPosition <= 0)
                    throw new RuntimeException("the dimensions of the lawn have been exceeded on the Y axis");
                break;
            case E:
                if (xPosition <= 0)
                    throw new RuntimeException("the dimensions of the lawn have been exceeded on the X axis");
                break;
            case S:
                if (yPosition >= ground.getTopRightCorner())
                    throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the Y axis \{ground.getTopRightCorner()}");
                break;
            case W:
                if (xPosition >= ground.getLowerLeftCorner())
                    throw new RuntimeException(STR."the dimensions of the lawn have been exceeded on the X axis \{ground.getLowerLeftCorner()}");
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
