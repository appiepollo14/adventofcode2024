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
import java.util.List;

@Path("/day5")
public class Day5 {

    List<List<Integer>> updateRules = new ArrayList<>();
    List<List<Integer>> toBeUpdatedPages = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day5() {
        return determineOrderedLists();
    }

    private int determineOrderedLists() {
        int sum = 0;
        for (List<Integer> l : toBeUpdatedPages) {
            if (isOrderedCorrectly(l)) {
                sum += middleOfList(l);
            }
        }
        return sum;
    }

    private boolean isOrderedCorrectly(List<Integer> l) {
        return true;
    }

    private int middleOfList(List<Integer> l) {
        return l.size() / 2;
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day5part2() {
        return 0;
    }

    @PostConstruct
    void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day5/test-input.txt"))) {
            String line;
            boolean isBlankLineFound = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    isBlankLineFound = true;
                    continue;
                }

                if (!isBlankLineFound) {
                    // Process "|"-delimited lines
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(Integer.parseInt(parts[0]));
                        pair.add(Integer.parseInt(parts[1]));
                        updateRules.add(pair);
                    }
                } else {
                    // Process ","-delimited lines
                    String[] parts = line.split(",");
                    List<Integer> list = new ArrayList<>();
                    for (String part : parts) {
                        list.add(Integer.parseInt(part.trim()));
                    }
                    toBeUpdatedPages.add(list);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
