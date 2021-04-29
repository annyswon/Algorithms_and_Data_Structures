package main.java.exercise;

import main.java.exercise.graph.Graph;
import main.java.exercise.helper.Heuristic;
import main.java.exercise.helper.Point;
import main.java.exercise.helper.PriorityQueue;
import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

import java.util.*;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "Anna", // Vorname
                "Surkova", // Nachname
                "12042425" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für die euklidische Distanz
    public double heuristicManhattanDistance(Point point1, Point point2) {
        if (point1 == null || point2 == null) {
            return 0;
        }
        var diffX = point1.getX() - point2.getX();
        var diffY = point1.getY() - point2.getY();
        return Math.abs(diffX) + Math.abs(diffY);
    }

    // Implementieren Sie hier Ihre Lösung für die euklidische Distanz
    public double heuristicEuclideanDistance(Point point1, Point point2) {
        if (point1 == null || point2 == null) {
            return 0;
        }
        int diffX = point1.getX() - point2.getX();
        int diffY = point1.getY() - point2.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    // Implementieren Sie hier Ihre Lösung für A*
    public void aStar(Graph g, PriorityQueue q, Heuristic h, int source, int target, int[] path) {
        Map<Integer, Double> gscore = new HashMap<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        gscore.put(source, 0.);
        q.add(source, h.evaluate(source));
        while (!q.isEmpty()) {
            int current = q.removeFirst();
            if (current == target) {
                int index = 0;
                while (cameFrom.containsKey(current)) {
                    path[path.length - index++ - 1] = current;
                    current = cameFrom.get(current);
                }
                path[path.length - index - 1] = current;
                break;
            }

            for (int neighbor: g.getNeighbors(current)) {
                double tentative_gscore = gscore.get(current) + g.getEdgeWeight(current, neighbor);
                if (tentative_gscore < gscore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gscore.put(neighbor, tentative_gscore);
                    if (q.contains(neighbor)) {
                        q.decreaseWeight(neighbor, gscore.get(neighbor) + h.evaluate(neighbor));
                    } else {
                        q.add(neighbor, gscore.get(neighbor) + h.evaluate(neighbor));
                    }
                }
            }

        }
    }


}
