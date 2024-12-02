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

    public int calculatePart2() {
        int sum = 0;
        return sum;
    }

}
