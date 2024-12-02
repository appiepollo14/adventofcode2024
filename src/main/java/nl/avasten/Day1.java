package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Path("/day1")
public class Day1 {

    Map<String, ArrayList<Integer>> lists = new HashMap<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day1() {
        load();
        return calculatePart1();
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day1part2() {
        load();
        return calculatePart2();
    }

    void load() {
        lists.put("left", new ArrayList<>());
        lists.put("right", new ArrayList<>());
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/day1/puzzle-input.txt"));

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String[] splitted = currentLine.split("   ");
                var currLeftList = lists.get("left");
                currLeftList.add(Integer.valueOf(splitted[0]));
                lists.put("left", currLeftList);
                var currRightList = lists.get("right");
                currRightList.add(Integer.valueOf(splitted[1]));
                lists.put("right", currRightList);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int calculatePart1() {
        int sum = 0;
        var leftList = lists.get("left");
        Collections.sort(leftList);
        var rightList = lists.get("right");
        Collections.sort(rightList);
        for (int i = 0; i < leftList.size(); i++) {
            if (rightList.get(i) > leftList.get(i)) {
                sum += (rightList.get(i) - leftList.get(i));
            } else {
                sum += (leftList.get(i) - rightList.get(i));
            }
        }
        return sum;
    }

    public int calculatePart2() {
        int sum = 0;
        var leftList = lists.get("left");
        var rightList = lists.get("right");
        Collections.sort(leftList);
        for (Integer left : leftList) {
            var right = rightList.stream().filter(c -> c.equals(left)).count();
            sum += (int) (left * right);
        }
        return sum;
    }

}
