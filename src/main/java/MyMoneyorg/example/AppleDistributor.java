package MyMoneyorg.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AppleDistributor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> appleWeights = new ArrayList<>();

        System.out.println("Enter apple weight in grams (-1 to stop): ");
        while (true) {
            int weight = scanner.nextInt();
            if (weight == -1) break;
            appleWeights.add(weight);
        }

        // Sort the apples in descending order
        Collections.sort(appleWeights, Collections.reverseOrder());

        // Calculate total weight
        int totalWeight = appleWeights.stream().mapToInt(Integer::intValue).sum();

        // Calculate each person's share based on the money they paid
        double ramShare = totalWeight * 0.50;
        double shamShare = totalWeight * 0.30;
        double rahimShare = totalWeight * 0.20;

        List<Integer> ramApples = new ArrayList<>();
        List<Integer> shamApples = new ArrayList<>();
        List<Integer> rahimApples = new ArrayList<>();

        // Distribute apples according to the proportion
        for (int weight : appleWeights) {
            if (ramShare >= weight) {
                ramApples.add(weight);
                ramShare -= weight;
            } else if (shamShare >= weight) {
                shamApples.add(weight);
                shamShare -= weight;
            } else if (rahimShare >= weight) {
                rahimApples.add(weight);
                rahimShare -= weight;
            } else {
                // If none of the shares can accommodate the current apple, allocate it to the one with the smallest remaining share
                if (ramShare <= shamShare && ramShare <= rahimShare) {
                    ramApples.add(weight);
                    ramShare -= weight;
                } else if (shamShare <= ramShare && shamShare <= rahimShare) {
                    shamApples.add(weight);
                    shamShare -= weight;
                } else {
                    rahimApples.add(weight);
                    rahimShare -= weight;
                }
            }
        }

        // Print the distribution result
        System.out.println("Distribution Result:");
        System.out.print("Ram: ");
        printList(ramApples);

        System.out.print("Sham: ");
        printList(shamApples);

        System.out.print("Rahim: ");
        printList(rahimApples);
    }

    private static void printList(List<Integer> list) {
        for (int weight : list) {
            System.out.print(weight + " ");
        }
        System.out.println();
    }
}


