package nl.avasten;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Path("/day5")
public class Day5 {

    List<List<Integer>> updateRules = new ArrayList<>();
    List<List<Integer>> toBeUpdatedPages = new ArrayList<>();
    List<List<Integer>> correctLists = new ArrayList<>();
    List<List<Integer>> incorrectLists = new ArrayList<>();
    List<List<Integer>> reorderedLists = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int day5() {
        int sum = 0;
        for (List<Integer> l : correctLists) {
            sum += middleOfList(l);
        }
        return sum;
    }

    @GET
    @Path("/part2")
    @Produces(MediaType.TEXT_PLAIN)
    public int day5part2() {
        int sum = 0;
        for (List<Integer> incorrectList : incorrectLists) {
            var i = orderIntegers(updateRules, incorrectList);
            sum += middleOfList(i);
        }
        return sum;
    }

    public List<Integer> orderIntegers(List<List<Integer>> rules, List<Integer> inputList) {
        // Step 1: Build the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (List<Integer> rule : rules) {
            int a = rule.get(0), b = rule.get(1);
            if (inputList.contains(a) && inputList.contains(b)) {
                graph.putIfAbsent(a, new ArrayList<>());
                graph.get(a).add(b);
                graph.putIfAbsent(b, new ArrayList<>());
            }
        }

        System.out.println("Graph: " + graph);

        // Step 2: Perform topological sorting using Kahn's algorithm
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int key : graph.keySet()) {
            inDegree.putIfAbsent(key, 0);
            for (int neighbor : graph.get(key)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        System.out.println("Indegree: " + inDegree);

        Queue<Integer> queue = new LinkedList<>();
        for (int node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                queue.add(node);
            }
        }

        System.out.println("Queue: " + queue);

        List<Integer> sortedOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedOrder.add(current);
            for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedOrder;
    }

    private void determineOrderedLists() {
        outer:
        for (List<Integer> l : toBeUpdatedPages) {
            for (List<Integer> rules : updateRules) {
                if (l.containsAll(rules)) {
                    var firstPos = l.indexOf(rules.get(0));
                    var secondPos = l.indexOf(rules.get(1));
                    if (firstPos > secondPos) {
                        incorrectLists.add(l);
                        continue outer;
                    }
                }
            }
            correctLists.add(l);
        }
    }

    private int middleOfList(List<Integer> l) {
        return l.get(l.size() / 2);
    }

    @PostConstruct
    void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day5/puzzle-input.txt"))) {
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
        determineOrderedLists();
    }
}
