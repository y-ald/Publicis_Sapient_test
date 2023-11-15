package org.mowItNow.mower.presentation;

import org.mowItNow.Utils;
import org.mowItNow.mower.service.MowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Component
public class MowerScreen {
    private static Logger logger = LoggerFactory.getLogger(MowerScreen.class);
    private MowerService mowerService;
    private static Map<Integer, Runnable> menuOptions = new HashMap<>();

    public MowerScreen(MowerService mowerService) {
        this.mowerService = mowerService;
    }
    
    public void screen() throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            initializeMenu();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            if (menuOptions.containsKey(choice)) {
                menuOptions.get(choice).run();
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void initializeMenu() {
        String folder = "mowerTasksConfigurations";
        List<String> fileNames = Utils.getFilesFromResources(folder);
        int filte_index = 1;

        System.out.println();
        System.out.println("************************************************ Menu ******************************************************");
        System.out.println("\n");
        System.out.println("Choose from these choices, the config to run: ");
        System.out.println("-------------------------\n");
        for(String fileName: fileNames) {
            menuOptions.put(filte_index, () -> mowerService.executeMowerTask(fileName));
            System.out.println(filte_index+ " - " + fileName.split("\\.")[0]);
            filte_index ++;
        }
        System.out.println(filte_index + " - Run all the configurations" );
        menuOptions.put(filte_index, () -> mowerService.executeAllMowerTask(fileNames));
        menuOptions.put( filte_index + 1, () -> {
            logger.info("Exiting the program. Goodbye!");
            System.exit(0);
        });
        System.out.println( menuOptions.size()  + " - Exit");
        System.out.println();
    }
}

