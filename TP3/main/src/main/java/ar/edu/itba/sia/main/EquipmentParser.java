package ar.edu.itba.sia.main;

import ar.edu.itba.sia.model.Equipment;
import ar.edu.itba.sia.model.EquipmentStorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EquipmentParser {

    private static final int idIndex = 0;
    private static final int strengthIndex = 1;
    private static final int agilityIndex = 2;
    private static final int wisdomIndex = 3;
    private static final int enduranceIndex = 4;
    private static final int healthIndex = 5;

    private static final String dataPath = "testdata/";
    private static final String weaponPath = "armas.tsv";
    private static final String bootsPath = "botas.tsv";
    private static final String helmetPath = "cascos.tsv";
    private static final String gauntletsPath = "guantes.tsv";
    private static final String breastPlatePath = "pecheras.tsv";

    /* package */ void parse(String path) {
        EquipmentStorage.setWeapons(readTsvFile(path + dataPath + weaponPath));
        EquipmentStorage.setBoots(readTsvFile(path + dataPath + bootsPath));
        EquipmentStorage.setHelmets(readTsvFile(path + dataPath + helmetPath));
        EquipmentStorage.setBreastPlates(readTsvFile(path + dataPath + breastPlatePath));
        EquipmentStorage.setGauntlets(readTsvFile(path + dataPath + gauntletsPath));
    }

    private Equipment[] readTsvFile(String filePath) {
        try (BufferedReader tsvReader = new BufferedReader(new FileReader(filePath))) {
            tsvReader.readLine(); // ignoring first line
            List<Equipment> equipment = new LinkedList<>();
            String row;
            while ((row = tsvReader.readLine()) != null) {
                String[] data = row.split("\t");
                equipment.add(new Equipment(Long.valueOf(data[idIndex]),
                                            Double.valueOf(data[strengthIndex]),
                                            Double.valueOf(data[agilityIndex]),
                                            Double.valueOf(data[wisdomIndex]),
                                            Double.valueOf(data[enduranceIndex]),
                                            Double.valueOf(data[healthIndex])));
            }
            return equipment.toArray(new Equipment[] { });
        }
        catch (IOException e) {
            throw new IllegalArgumentException("the equipment file " + filePath + " does not exist");
        }
    }
}
