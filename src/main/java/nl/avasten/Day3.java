package nl.avasten;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/day3")
public class Day3 {

    List<String> input = new ArrayList<>();
    List<String> multiplications = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day3() {
        input = new ArrayList<>();
        load();
        filterMultiplications();
        return calculatePart1();
//        return calculatePart1();
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day3part2() {
        input = new ArrayList<>();
        load();
        return 0;
//        return calculatePart2();
    }

    void load() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/day3/puzzle-input.txt"));

            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void filterMultiplications() {
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";

        Pattern pattern = Pattern.compile(regex);
        for (String i : input) {
            Matcher matcher = pattern.matcher(i);
            while (matcher.find()) {
                multiplications.add(matcher.group());
            }
        }
//        System.out.println(multiplications);
    }

    public int calculatePart1() {
        int sum = 0;
        for (String l : multiplications) {
            l = l.replace("mul(", "").replace(")", "");
            var numbers = l.split(",");
            sum += Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
        }
        return sum;
    }
//            if (Objects.equals(l.get(0), l.get(1))) {
//                continue;
//            }
//            boolean safe = true;
//            if (l.get(1) < l.get(0)) {
//                l = l.reversed();
//            }
//
//            for (int i = 1; i < l.size(); i++) {
//
//                System.out.println(l.get(i));
//                var safeStep = (l.get(i) - l.get(i - 1) >= 1 && l.get(i) - l.get(i - 1) <= 3);
//                if (!safeStep) {
//                    safe = false;
//                    break;
//                }
//            }
//            if (safe) {
//                safeListsQty += 1;
//            }
//            System.out.println("List: " + l + " is: " + safe);
//
//        }
//        return safeListsQty;
//    }
//
//    // Between 439 and 465
//    // 457 not correct
//    // 445 not correct
//    public int calculatePart2() {
//        int safeListsQty = 0;
//        for (List<Integer> l : input) {
//            boolean safe = true;
//            boolean skippedOne = false;
//            if (l.get(1) < l.get(0)) {
//                l = l.reversed();
//            }
//
//            for (int i = 1; i < l.size(); i++) {
//                var safeStep = (l.get(i) - l.get(i - 1) >= 1 && l.get(i) - l.get(i - 1) <= 3);
//                if (!safeStep) {
//                    if (!skippedOne) {
//                        skippedOne = true;
//                        if (i + 1 < l.size()) {
//                            safe = (l.get(i + 1) - l.get(i - 1) >= 1 && l.get(i + 1) - l.get(i - 1) <= 3);
//                            i += i;
//                            System.out.println("Skipped one");
//                        }
//                        continue;
//                    }
//                    safe = false;
//                    break;
//                }
//            }
//            if (safe) {
//                safeListsQty += 1;
//            }
//            System.out.println("List: " + l + " is: " + safe);
//
//        }
//        return safeListsQty;
//    }

}
