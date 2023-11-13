package org.mowItNow.presentation;

import org.mowItNow.Utils;
import org.mowItNow.service.MowerService;
import org.mowItNow.service.MowerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class MowerScreen {
    private static Logger logger = LoggerFactory.getLogger(MowerScreen.class);
    private MowerService mowerService;

    public MowerScreen(MowerService mowerService) {
        this.mowerService = mowerService;
    }

    public void screen() throws IOException, URISyntaxException {

    }
    public static void menu() throws IOException, URISyntaxException {
        List<Path> configFiles = Utils.getFilesFromResources("mowerTasksConfigurations");

        int selection = 0;
        while (true) {
            Scanner input = new Scanner(System.in);
            int filte_index = 1;

            /***************************************************/

            System.out.println("Choose from these choices, the config to run");
            System.out.println("-------------------------\n");
            for(Path path: configFiles) {
                System.out.println(filte_index + " - " + path.getFileName());
                filte_index ++;
            }
            System.out.println(filte_index + " - Run all");

            selection = input.nextInt();
            switch (selection) {
                case 1:
                    // Perform "original number" case.
                    break;
                case 2:
                    // Perform "encrypt number" case.
                    break;
                case 3:
                    // Perform "decrypt number" case.
                    break;
                case 4:
                    // Perform "quit" case.
                    System.exit(0);
                    break;
                default:
                    // The user input an unexpected choice.
            }
        }
    }
}

//public class DynamicMenu {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        // Create a map to associate menu options with actions
//        Map<Integer, Runnable> menuOptions = new HashMap<>();
//        menuOptions.put(1, () -> {
//            System.out.println("You selected Option 1");
//            // Add code for Option 1
//        });
//        menuOptions.put(2, () -> {
//            System.out.println("You selected Option 2");
//            // Add code for Option 2
//        });
//        menuOptions.put(3, () -> {
//            System.out.println("You selected Option 3");
//            // Add code for Option 3
//        });
//        menuOptions.put(4, () -> {
//            System.out.println("Exiting the program. Goodbye!");
//            System.exit(0); // Exit the program
//        });
//
//        while (true) {
//            // Display menu options
//            System.out.println("Menu:");
//            System.out.println("1. Option 1");
//            System.out.println("2. Option 2");
//            System.out.println("3. Option 3");
//            System.out.println("4. Exit");
//
//            // Prompt user for input
//            System.out.print("Enter your choice: ");
//
//            // Read user input
//            int choice = scanner.nextInt();
//
//            // Process user choice using the map
//            if (menuOptions.containsKey(choice)) {
//                menuOptions.get(choice).run();
//            } else {
//                System.out.println("Invalid choice. Please enter a valid option.");
//            }
//        }
//    }
//}
//
//    If you want to achieve dynamic behavior similar to a switch statement in Java, you can use a Map to associate keys with corresponding actions. Here's an example:

        java

        import java.util.HashMap;
        import java.util.Map;
        import java.util.Scanner;
