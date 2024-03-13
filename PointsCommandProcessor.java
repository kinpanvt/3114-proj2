public class PointsCommandProcessor {

    private PointsDatabase database;

    public PointsCommandProcessor() {
        database = new PointsDatabase();
    }

    public void processCommand(String line) {
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "insert":
                handleInsert(parts);
                break;
            case "remove":
                handleRemove(parts);
                break;
            case "regionsearch":
                handleRegionSearch(parts);
                break;
            case "search":
                handleSearch(parts);
                break;
            case "duplicates":
                database.duplicates();
                break;
            case "dump":
                database.dump();
                break;
            default:
                System.out.println("Unrecognized command: " + line);
                break;
        }
    }

    private void handleInsert(String[] parts) {
        if (parts.length == 4) {
            try {
                String name = parts[1];
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);
                database.insert(name, x, y);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameters for insert command.");
            }
        } else {
            System.out.println("Invalid insert command.");
        }
    }

    private void handleRemove(String[] parts) {
        if (parts.length == 2) {
            database.remove(parts[1]); // Remove by name
        } else if (parts.length == 3) {
            try {
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                database.remove(x, y);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameters for remove command.");
            }
        } else {
            System.out.println("Invalid remove command.");
        }
    }

    private void handleRegionSearch(String[] parts) {
        if (parts.length == 5) {
            try {
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int width = Integer.parseInt(parts[3]);
                int height = Integer.parseInt(parts[4]);
                database.regionSearch(x, y, width, height);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameters for regionsearch command.");
            }
        } else {
            System.out.println("Invalid regionsearch command.");
        }
    }

    private void handleSearch(String[] parts) {
        if (parts.length == 2) {
            database.search(parts[1]);
        } else {
            System.out.println("Invalid search command.");
        }
    }
}
