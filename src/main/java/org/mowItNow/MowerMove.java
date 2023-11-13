package org.mowItNow;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MowerMove {

    public static void main(String[] args) {
        // Read the number of mowers
        String instructions = "GAGAGAGAA";

        // Process the mower's instructions
        Mower mower = new Mower(1, 2, 'N', 5, 5);
        mower.processInstructions(instructions);

        // Print the final position and orientation of the mower
        System.out.println(mower.getX() + " " + mower.getY() + " " + mower.getOrientation());// Read the number of mowers
        String instructions2 = "AADAADADDA";
        // Process the mower's instructions
        Mower mower2 = new Mower(3, 3, 'E', 5, 5);
        mower2.processInstructions(instructions2);

        // Print the final position and orientation of the mower
        System.out.println(mower2.getX() + " " + mower2.getY() + " " + mower2.getOrientation());

    }
}



