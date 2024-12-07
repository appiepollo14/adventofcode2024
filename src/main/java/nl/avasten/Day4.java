package nl.avasten;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Path("/day4")
public class Day4 {

    Map<Integer, char[]> input = new HashMap<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day4() {
        return findXMAX();
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day4part2() {
        return 0;
    }

    @PostConstruct
    void load() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/day4/puzzle-input.txt"));

            int row = 0;
            while (scanner.hasNextLine()) {
                input.put(row, scanner.nextLine().toCharArray());
                row++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int findXMAX() {
        int xmasQty = 0;
        for (Map.Entry<Integer, char[]> entry : input.entrySet()) {
            int rowNumber = entry.getKey();
            char[] arr = entry.getValue();
            for (int col = 0; col < arr.length; col++) {
                if (arr[col] == 'X') {
                    System.out.println("'X' found at row: " + rowNumber + ", col: " + col);
                    if (findWest(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findEast(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findNorth(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findSouth(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findNorthEast(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findNorthWest(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findSouthEast(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                    if (findSouthWest(new coordinate(rowNumber, col))) {
                        xmasQty += 1;
                    }
                }
            }
        }
        return xmasQty;
    }

    public boolean findWest(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row, xCoordinate.col - 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row, xCoordinate.col - 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row, xCoordinate.col - 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findEast(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row, xCoordinate.col + 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row, xCoordinate.col + 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row, xCoordinate.col + 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findNorth(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row - 1, xCoordinate.col)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row - 2, xCoordinate.col)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row - 3, xCoordinate.col)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findSouth(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row + 1, xCoordinate.col)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row + 2, xCoordinate.col)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row + 3, xCoordinate.col)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findSouthWest(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row + 1, xCoordinate.col - 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row + 2, xCoordinate.col - 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row + 3, xCoordinate.col - 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findSouthEast(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row + 1, xCoordinate.col + 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row + 2, xCoordinate.col + 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row + 3, xCoordinate.col + 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findNorthWest(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row - 1, xCoordinate.col - 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row - 2, xCoordinate.col - 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row - 3, xCoordinate.col - 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public boolean findNorthEast(coordinate xCoordinate) {
        boolean found;
        try {
            found = getCharAt(new coordinate(xCoordinate.row - 1, xCoordinate.col + 1)) == 'M'
                    && getCharAt(new coordinate(xCoordinate.row - 2, xCoordinate.col + 2)) == 'A' &&
                    getCharAt(new coordinate(xCoordinate.row - 3, xCoordinate.col + 3)) == 'S';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return found;
    }

    public Character getCharAt(coordinate coordinate) throws ArrayIndexOutOfBoundsException {
        if (input.get(coordinate.row) != null) {
            return input.get(coordinate.row)[coordinate.col];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public record coordinate(int row, int col) {
    }
}
