package nl.avasten;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/day6")
public class Day6 {

    // Map of Locations where each key is the row number, followed by a List of locations
    Map<Integer, List<Location>> map = new HashMap<>();
    Location startPoint;
    Direction currentDirection = Direction.UP;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day6() {
        Location next = getNextLocation(currentDirection, startPoint);
        if (!next.isObstacle) {

        }
        System.out.println(next);
        return determineVisited();
    }

    private Location getNextLocation(Direction d, Location currentLocation) throws ArrayIndexOutOfBoundsException {
        if (d.equals(Direction.UP)) {
            var list = map.get(currentLocation.getRow() - 1);
            if (list == null) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return list.get(currentLocation.getCol());
        } else if (d.equals(Direction.DOWN)) {
            var list = map.get(currentLocation.getRow() + 1);
            if (list == null) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return list.get(currentLocation.getCol());
        } else if (d.equals(Direction.LEFT)) {
            return map.get(currentLocation.getRow()).get(currentLocation.getCol() - 1);
        } else {
            // Direction is Right
            return map.get(currentLocation.getRow()).get(currentLocation.getCol() + 1);
        }
    }

    private int determineVisited() {
        int visitedLocations = 0;
        for (Integer i : map.keySet()) {
            for (Location l : map.get(i)) {
                if (l.isVisited) {
                    visitedLocations += 1;
                }
            }
        }
        return visitedLocations;
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day6part2() {
        int sum = 0;
        return sum;
    }

    @PostConstruct
    void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day6/test-input.txt"))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                char[] chars = line.toCharArray();
                List<Location> cols = new ArrayList<>();
                for (int i = 0; i < chars.length; i++) {
                    cols.add(new Location(row, i, chars[i] == '#'));
                    if (startPoint == null) {
                        if (chars[i] == '^') {
                            startPoint = new Location(row, i, false);
                        }
                    }
                }
                map.put(row, cols);
                row += 1;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class Location {
        private final Integer row;
        private final Integer col;
        private final boolean isObstacle;
        private boolean isVisited;

        public Location(int row, int col, boolean isObstacle) {
            this.row = row;
            this.col = col;
            this.isObstacle = isObstacle;
        }

        public boolean isVisited() {
            return this.isVisited;
        }

        public void setIsVisited() {
            this.isVisited = true;
        }

        public Integer getRow() {
            return this.row;
        }

        public Integer getCol() {
            return this.col;
        }

        @Override
        public String toString() {
            return "row: " + this.row + " , col: " + this.col + " , obstacle: " + this.isObstacle + " , visited: " + this.isVisited;
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
}
