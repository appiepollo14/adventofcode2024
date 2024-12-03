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
    List<List<Integer>> unsafeLists = new ArrayList<>(new ArrayList<>());

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
//        input = new ArrayList<>(new ArrayList<>());
//        load();
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
            if (isSafeList(l)) {
                safeListsQty += 1;
            }
        }
        return safeListsQty;
    }

    private boolean isSafeList(List<Integer> l) {
        if (Objects.equals(l.get(0), l.get(1))) {
            return false;
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
            return true;
        }
        unsafeLists.add(l);
        return false;
    }

    // Between 439 and 465
    // 457 not correct
    // 445 not correct
    // 203 not correct
    public int calculatePart2() {
        int extraSafeCount = 0;

        // Create a copy to avoid ConcurrentModificationException
        List<List<Integer>> copyOfUnsafeLists = new ArrayList<>(unsafeLists);

        for (List<Integer> originalList : copyOfUnsafeLists) {
            boolean isSafe = false;

            for (int index = 0; index < originalList.size(); index++) {
                List<Integer> copiedList = new ArrayList<>(originalList);
                System.out.println(copiedList);
                copiedList.remove(index);
                System.out.println(copiedList);

                if (isSafeList(copiedList)) {
                    isSafe = true;
                    break;
                }
            }

            if (isSafe) {
                extraSafeCount++;
            }
        }

        return extraSafeCount;
    }
}
