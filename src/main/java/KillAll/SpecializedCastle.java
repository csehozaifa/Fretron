package KillAll;

import java.util.*;

public class SpecializedCastle {

    private static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private static List<List<Position>> paths = new ArrayList<>();
    private static Position startCastlePosition;
    private static Set<Position> soldiers = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numSoldiers = getIntInput(scanner, "Enter the number of soldiers: ");

        for (int i = 1; i <= numSoldiers; i++) {
            int x = getIntInput(scanner, "Enter x-coordinate for soldier " + i + ": ");
            int y = getIntInput(scanner, "Enter y-coordinate for soldier " + i + ": ");
            soldiers.add(new Position(x, y));
        }

        int castleX = getIntInput(scanner, "Enter the x-coordinate for your 'special' castle: ");
        int castleY = getIntInput(scanner, "Enter the y-coordinate for your 'special' castle: ");
        startCastlePosition = new Position(castleX, castleY);

        // Starting to find paths
        findPaths(startCastlePosition, new ArrayList<>(Arrays.asList(startCastlePosition)), soldiers, "right");

        System.out.println("Thanks. There are " + paths.size() + " unique paths for your 'special_castle'");

        for (int i = 0; i < paths.size(); i++) {
            System.out.println("Path " + (i + 1) + ":");
            System.out.println("=======");
            for (int j = 0; j < paths.get(i).size(); j++) {
                Position pos = paths.get(i).get(j);
                if (j == 0) {
                    System.out.println("Start " + pos);
                } else {
                    System.out.println("Kill " + pos + ". Turn Left");
                }
            }
            System.out.println("Arrive " + startCastlePosition);
            System.out.println();
        }
    }

    private static void findPaths(Position current, List<Position> currentPath, Set<Position> remainingSoldiers, String direction) {
        if (remainingSoldiers.isEmpty()) {
            // If no soldiers are left and we're back at the start, it's a valid path
            if (current.equals(startCastlePosition)) {
                paths.add(new ArrayList<>(currentPath));
            }
            return;
        }

        List<Position> possibleKills = getPossibleKills(current, remainingSoldiers, direction);

        for (Position kill : possibleKills) {
            List<Position> newPath = new ArrayList<>(currentPath);
            newPath.add(kill);
            Set<Position> newRemainingSoldiers = new HashSet<>(remainingSoldiers);
            newRemainingSoldiers.remove(kill);

            String newDirection = turnLeft(direction);
            findPaths(kill, newPath, newRemainingSoldiers, newDirection);
        }
    }

    private static List<Position> getPossibleKills(Position current, Set<Position> soldiers, String direction) {
        List<Position> possibleKills = new ArrayList<>();
        for (Position soldier : soldiers) {
            if (isValidMove(current, soldier, direction)) {
                possibleKills.add(soldier);
            }
        }
        return possibleKills;
    }

    private static boolean isValidMove(Position current, Position target, String direction) {
        // Ensure that the castle can move to the target position in the given direction
        switch (direction) {
            case "right":
                return current.x == target.x && current.y < target.y;
            case "left":
                return current.x == target.x && current.y > target.y;
            case "up":
                return current.y == target.y && current.x > target.x;
            case "down":
                return current.y == target.y && current.x < target.x;
        }
        return false;
    }

    private static String turnLeft(String currentDirection) {
        // Rotate left
        switch (currentDirection) {
            case "right":
                return "up";
            case "up":
                return "left";
            case "left":
                return "down";
            case "down":
                return "right";
        }
        return currentDirection;
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // clear the invalid input
            }
        }
    }
}
