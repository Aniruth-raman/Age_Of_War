package org.aniruth;

import java.util.*;

public class Main {

    // Define the advantage map
    private static final Map<String, List<String>> ADVANTAGE_MAP = new HashMap<>() {
        {
            put("Militia", Arrays.asList("Spearmen", "LightCavalry"));
            put("Spearmen", Arrays.asList("LightCavalry", "HeavyCavalry"));
            put("LightCavalry", Arrays.asList("FootArcher", "CavalryArcher"));
            put("HeavyCavalry", Arrays.asList("Militia", "FootArcher", "LightCavalry"));
            put("CavalryArcher", Arrays.asList("Spearmen", "HeavyCavalry"));
            put("FootArcher", Arrays.asList("Militia", "CavalryArcher"));
        }
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read input
        String[] ownPlatoonsInput = scanner.nextLine().split(";");
        String[] opponentPlatoonsInput = scanner.nextLine().split(";");

        List<Platoon> ownPlatoons = parsePlatoons(ownPlatoonsInput);
        List<Platoon> opponentPlatoons = parsePlatoons(opponentPlatoonsInput);

        // Find a winning arrangement
        List<Platoon> winningArrangement = findWinningArrangement(ownPlatoons, opponentPlatoons);

        if (winningArrangement == null) {
            System.out.println("There is no chance of winning");
        } else {
            for (Platoon platoon : winningArrangement) {
                System.out.print(platoon);
            }
        }
        scanner.close();
    }

    public static List<Platoon> parsePlatoons(String[] platoonsInput) {
        List<Platoon> platoons = new ArrayList<>();
        for (String platoon : platoonsInput) {
            String[] parts = platoon.split("#");
            platoons.add(new Platoon(parts[0], Integer.parseInt(parts[1])));
        }
        return platoons;
    }

    public static List<Platoon> findWinningArrangement(List<Platoon> ownPlatoons, List<Platoon> opponentPlatoons) {
        List<List<Platoon>> permutations = generatePermutations(ownPlatoons);

        for (List<Platoon> arrangement : permutations) {
            int wins = 0;
            for (int i = 0; i < opponentPlatoons.size(); i++) {
                int outcome = battleOutcome(arrangement.get(i), opponentPlatoons.get(i));
                if (outcome > 0) {
                    wins++;
                }
            }
            if (wins >= 3) {
                return arrangement;
            }
        }

        return null;
    }

    private static int battleOutcome(Platoon own, Platoon opponent) {
        // Check if own platoon has an advantage over the opponent
        if (ADVANTAGE_MAP.get(own.type).contains(opponent.type)) {
            if (own.soldiers * 2 > opponent.soldiers) return 1; // Win
            if (own.soldiers * 2 == opponent.soldiers) return 0; // Draw
            return -1; // Loss
        }
        // Check if opponent's platoon has an advantage over own platoon
        else if (ADVANTAGE_MAP.get(opponent.type).contains(own.type)) {
            if (opponent.soldiers * 2 > own.soldiers) return -1; // Loss
            if (opponent.soldiers * 2 == own.soldiers) return 0; // Draw
            return 1; // Win
        }
        // No advantage for either side
        else {
            if (own.soldiers > opponent.soldiers) return 1; // Win
            if (own.soldiers == opponent.soldiers) return 0; // Draw
            return -1; // Loss
        }
    }

    private static List<List<Platoon>> generatePermutations(List<Platoon> platoons) {
        List<List<Platoon>> permutations = new ArrayList<>();
        permute(platoons, 0, permutations);
        return permutations;
    }

    private static void permute(List<Platoon> platoons, int start, List<List<Platoon>> result) {
        if (start == platoons.size() - 1) {
            result.add(new ArrayList<>(platoons));
            return;
        }

        for (int i = start; i < platoons.size(); i++) {
            Collections.swap(platoons, i, start);
            permute(platoons, start + 1, result);
            Collections.swap(platoons, i, start);
        }
    }
}