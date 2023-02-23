package com.example.potroad.core;


import android.graphics.Color;

import com.example.potroad.graphics.Sprite;

import java.util.ArrayList;

public class PotholeSpawner {

    private double spawnsPerMinute = 60.0;
    private double spawnsPerSecond = spawnsPerMinute / 60.0;
    private double updatesPerSpawn = GameLoop.MAX_UPS / spawnsPerSecond;
    private double counter = updatesPerSpawn;


    /* To avoid impossible situations, a suggested path is generated which shows a possible path to
    avoid all potholes. This path can either go left, right or straight. Turning consists of two
    "cells", one on the road previously occupied and one in the turn direction. On this path, no
    pothole may be generated. Hence we are left with either 4 or 3 free roads. Combinations can be
    hardcoded and used as a look-up table. A random choice will be made and put on the corresponding
    lanes. ex: C43 means that there are 4 possible empty roads and we wish to put 3 potholes on them
     */
    private final int[][] C43 = {
            {1, 1, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 1},
            {0, 1, 1, 1}
    };

    private final int[][] C42 = {
            {1, 1, 0, 0},
            {1, 0, 1, 0},
            {1, 0, 0, 1},
            {0, 1, 1, 0},
            {0, 1, 0, 1},
            {0, 0, 1, 1}
    };

    private final int[][] C41 = {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    };

    private final int[][] C32 = {
            {1, 1, 0},
            {1, 0, 1},
            {0, 1, 1}
    };

    private final int[][] C31 = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
    };

    private int[] getRandomCombination(int n, int k) {
        assert (n == 3 || n == 4);
        assert (k <= n);

        if (n == 4) {
            switch (k) {
                case 4 : return new int[]{1, 1, 1, 1};
                case 3 : return C43[getRand(0, 3)];
                case 2 : return C42[getRand(0, 5)];
                case 1 : return C41[getRand(0, 3)];
                default: return new int[]{0, 0, 0, 0};  // 0 and error case
            }
        }
        else {  // n == 3
            switch (k) {
                case 3 : return new int[]{1, 1, 1};
                case 2 : return C32[getRand(0, 2)];
                case 1 : return C31[getRand(0, 2)];
                default: return new int[]{0, 0, 0};     // 0 and error case
            }
        }
    }


    // 0 - accessible
    // 1 - pothole / not accessible
    private final double POTHOLE_SIZE;
    private final float[] ROAD_MIDDLE;
    private final int defaultPotholeColor;
    private final Sprite[] potholeSprites;
    private final int noOfPotholeSprites;

    public PotholeSpawner(double POTHOLE_SIZE, float[] ROAD_MIDDLE, int defaultPotholeColor, Sprite[] potholeSprites){
        this.POTHOLE_SIZE = POTHOLE_SIZE;
        this.ROAD_MIDDLE = ROAD_MIDDLE;
        this.defaultPotholeColor = defaultPotholeColor;
        this.potholeSprites = potholeSprites;
        noOfPotholeSprites = potholeSprites.length;
    }



    private int getRand(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }


    private boolean[] roadTaken;   // player always spawns in the middle

    private int freeRoads() {
        int i = 0;
        for (boolean rt : roadTaken) {
            if (!rt) i++;
        }
        return i;
    }

    public int[] generatePattern(int numberOfPotholes) {
        assert numberOfPotholes >= 0 && numberOfPotholes <= 5;


        int free = freeRoads();
        assert (free == 4 || free == 3);

        int[] miniPattern = getRandomCombination(free, numberOfPotholes);
        int[] finalPattern = new int[]{0,0,0,0,0};

        /*
        // "spread out" the pattern to 5 roads
        int k = 0;
        for (int i = 0; i < 5; i ++) {
            if (roadTaken[i]) {
                finalPattern[i] = 0;
            }
            else {
                finalPattern[i] = miniPattern[k];
                k++;
            }
        }*/

        int k = 0;
        for (int i = 0; i < 5; i++) {
            if (!roadTaken[i]) {
                finalPattern[i] = miniPattern[k++];
            }
        }

        //int l = 0;
        //for (int mp : miniPattern) {
        //    finalPattern[l++] = mp;
        //}

        // update roadTaken
        for (int i = 0; i < 5; i++){
            roadTaken[i] = finalPattern[i] == 1;
        }

        return finalPattern;
    }


    Pothole generatePothole(int roadNo) {
        return new Pothole(
                ROAD_MIDDLE[roadNo] - POTHOLE_SIZE / 2,
                -50,
                POTHOLE_SIZE * 2,
                POTHOLE_SIZE * 2,
                defaultPotholeColor,
                3,  //todo re-implement speedup
                potholeSprites[(int) (Math.random() * noOfPotholeSprites)]);
    }

    private DebugRectangle generatePath(int roadNo) {
        roadTaken[roadNo] = true;

        return new DebugRectangle(
                (float) (ROAD_MIDDLE[roadNo] - POTHOLE_SIZE / 2),
                -50,
                0,
                3,//todo re-implement speedup
                (float) POTHOLE_SIZE,
                (float) POTHOLE_SIZE,
                Color.RED);
    }

    int last = 2;
    public ArrayList<DebugRectangle> generatePathRow() {
        // reset roadTaken
        roadTaken = new boolean[]{false, false, false, false, false};

        ArrayList<DebugRectangle> pathToAdd = new ArrayList<>();

        int option;
        if (last == 0) {                // leftmost road
            option = getRand(1, 2);     // can only go straight or right
        }
        else if (last == 4) {           // rightmost road
            option = getRand(0, 1);     // can only go left or straight
        }
        else {
            option = getRand(0, 2);     // every option is viable
        }

        int next = last + option - 1;   // works due to the encoding chosen
        pathToAdd.add(generatePath(last));  // also updates roadTaken

        if (next != last) {             // if turned left or right
            pathToAdd.add(generatePath(next));
            last = next;
        }


        // todo: 4 may be too strict- retry pattern with less holes?

        return pathToAdd;
    }

    public ArrayList<Pothole> generatePotholeRow(int[] pattern) {
        ArrayList<Pothole> potholesToAdd = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (pattern[i] == 1) {
                potholesToAdd.add(generatePothole(i));
            }
        }

        return potholesToAdd;
    }

    public boolean readyToSpawn(){
        if (counter <= 0) {
            counter = updatesPerSpawn;    // reset counter
            return true;
        }
        return false;
    }

    public void update() {
        counter--;
    }
}
