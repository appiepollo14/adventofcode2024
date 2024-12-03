package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day2")
public class Day2 {

    List<List<Integer>> input = new ArrayList<>(new ArrayList<>());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day2() {
        load();
        return calculatePart1();
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day2part2() {
        input = new ArrayList<>(new ArrayList<>());
        load();
        return calculatePart2();
    }

    void load() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/day2/puzzle-input.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String[] splitted = currentLine.split(" ");
                input.add(Arrays.stream(splitted).map(Integer::valueOf).toList());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int calculatePart1() {
        int safeListsQty = 0;
        for (List<Integer> l : input) {
            if (Objects.equals(l.get(0), l.get(1))) {
                continue;
            }
            boolean safe = true;
            if (l.get(1) < l.get(0)) {
                l = l.reversed();
            }

            for (int i = 1; i < l.size(); i++) {

                System.out.println(l.get(i));
                var safeStep = (l.get(i) - l.get(i - 1) >= 1 && l.get(i) - l.get(i - 1) <= 3);
                if (!safeStep) {
                    safe = false;
                    break;
                }
            }
            if (safe) {
                safeListsQty += 1;
            }
            System.out.println("List: " + l + " is: " + safe);

        }
        return safeListsQty;
    }

    // Between 439 and 465
    // 457 not correct
    // 445 not correct
    // 203 not correct
    public int calculatePart2() {
        int safeListsQty = 0;
        for (List<Integer> l : input) {
            if (isSafe(l)) {
                safeListsQty += 1;
            }
        }
        return safeListsQty;
    }


    private boolean isSafe(List<Integer> list) {
        if (list.size() < 2) return true; // Single-element or empty lists are always safe

        boolean increasing = list.get(1) > list.get(0); // Determine the direction
        boolean skipped = false; // Flag to indicate if we've skipped an element

        for (int i = 1; i < list.size(); i++) {
            int diff = list.get(i) - list.get(i - 1);

            if (!isValidStep(diff, increasing)) {
                if (skipped) {
                    return false; // If we've already skipped once, list is not safe
                }
                skipped = true;

                // Try skipping the current or the previous element
                if (i + 1 < list.size() && isValidStep(list.get(i + 1) - list.get(i - 1), increasing)) {
                    i++; // Skip the current element
                } else if (i > 1 && isValidStep(list.get(i) - list.get(i - 2), increasing)) {
                    // Skip the previous element
                    continue;
                } else {
                    return false; // Cannot make the list safe
                }
            }
        }

        return true; // List is safe if we reach here
    }

    private boolean isValidStep(int diff, boolean increasing) {
        if (diff < 1 || diff > 3) return false; // Difference must be between 1 and 3
        return increasing ? diff > 0 : diff < 0; // Check if it respects the direction
    }
}
