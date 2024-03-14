import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PointsDatabase {
    // Assuming 'Database' is your class that integrates the SkipList and PRQuadTree
    private static Database data = new Database(); 

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java PointsDatabase <commandFile>");
            return;
        }

        String commandFile = args[0];

        try (Scanner scanner = new Scanner(new File(commandFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    processCommand(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Command file not found: " + commandFile);
        }
    }

    private static void processCommand(String line) {
        String[] parts = line.split("\\s+");
        switch (parts[0].toLowerCase()) {
            case "insert":
                handleInsert(parts);
                break;
            case "remove":
                handleRemove(parts);
                break;
            case "regionsearch":
                handleRegionSearch(parts);
                break;
            case "duplicates":
                data.handleDuplicatesPR(); // Assume this method exists in your Database class
                break;
            case "search":
                handleSearch(parts);
                break;
            case "dump":
                data.dump(); // Assume this method exists in your Database class
                break;
            default:
                System.out.println("Unrecognized command: " + line);
                break;
        }
    }

    private static void handleInsert(String[] parts) {
        if (parts.length == 4) {
            String name = parts[1];
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            data.insertPR(name, x, y);
        } else {
            System.out.println("Invalid insert command format.");
        }
    }

    private static void handleRemove(String[] parts) {
        if (parts.length == 2) {
            String name = parts[1];
            data.remove(name);
        } else if (parts.length == 3) {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            data.removePR(x, y);
        } else {
            System.out.println("Invalid remove command format.");
        }
    }

    private static void handleRegionSearch(String[] parts) {
        if (parts.length == 5) {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int w = Integer.parseInt(parts[3]);
            int h = Integer.parseInt(parts[4]);
            data.regionSearchPR(x, y, w, h);
        } else {
            System.out.println("Invalid regionsearch command format.");
        }
    }

    private static void handleSearch(String[] parts) {
        if (parts.length == 2) {
            String name = parts[1];
            data.searchPR(name);
        } else {
            System.out.println("Invalid search command format.");
        }
    }
    
    
    
}