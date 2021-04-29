package main.java.exercise;

import main.java.framework.StudentInformation;
import main.java.framework.StudentSolution;

public class StudentSolutionImplementation implements StudentSolution {
    @Override
    public StudentInformation provideStudentInformation() {
        return new StudentInformation(
                "Anna", // Vorname
                "Surkova", // Nachname
                "12042425" // Matrikelnummer
        );
    }

    // Implementieren Sie hier Ihre Lösung für die Indexsuche
    public int findIndex(int[] numbers, int element) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == element) {
                return i;
            }
        }
        return -1;
    }

    // Implementieren Sie hier den Gale-Shapley-Algorithmus
    public void performGaleShapley(StableMatchingInstance instance, StableMatchingSolution solution) {
        int[] childFamilyRankCounter = new int[solution.getN()];
        while (solution.hasUnassignedChildren()) {
            int s = solution.getNextUnassignedChild();
            while (childFamilyRankCounter[s] < solution.getN()) {
                int f = instance.getFamilyOfChildAtRank(s, childFamilyRankCounter[s]++);
                if (solution.isFamilyFree(f)) {
                    solution.assign(s, f);
                } else {
                    if (instance.getRankOfChildForFamily(f, s) > instance.getRankOfChildForFamily(f, solution.getAssignedChild(f))) {
                        solution.setFree(solution.getAssignedChild(f));
                        solution.assign(s, f);
                    }
                }
            }
        }
    }

    // Implementieren Sie hier Ihre Methode zur Überprüfung, ob ein Matching stabil ist.
    public boolean isStableMatching(StableMatchingInstance instance, StableMatchingSolution solution) {
        for (int f = 0; f < solution.getN(); f++) {
            int currentSRankForFamily = instance.getRankOfChildForFamily(f, solution.getAssignedChild(f));
            for (int sRankForFamily = 0; sRankForFamily < currentSRankForFamily; sRankForFamily++) {
                if (instance.getRankOfFamilyForChild(sRankForFamily, solution.getAssignedFamily(sRankForFamily)) < instance.getRankOfFamilyForChild(sRankForFamily, f)) {
                    return false;
                }
            }
        }
        return true;
    }
}
