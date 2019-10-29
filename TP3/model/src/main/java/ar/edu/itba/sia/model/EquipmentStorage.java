package ar.edu.itba.sia.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquipmentStorage {

    /* package */ static final String WEAPON_NAME = "weapon";
    /* package */ static final String BOOTS_NAME = "boots";
    /* package */ static final String HELMET_NAME = "helmet";
    /* package */ static final String GAUNTLETS_NAME = "gauntlets";
    /* package */ static final String BREAST_PLATE_NAME = "breast plate";

    private static Equipment[] weapons;
    private static Equipment[] boots;
    private static Equipment[] helmets;
    private static Equipment[] gauntlets;
    private static Equipment[] breastPlates;

    private static Map<String, Equipment[]> equipment = Stream.of(new Object[][] {{WEAPON_NAME, new Equipment[] { }},
                                                                                {BOOTS_NAME, new Equipment[] { }},
                                                                                {HELMET_NAME, new Equipment[] { }},
                                                                                {GAUNTLETS_NAME, new Equipment[] { }},
                                                                                {BREAST_PLATE_NAME, new Equipment[] { }}})
                                                                .collect(Collectors.toMap(data -> (String) data[0],
                                                                                        data -> (Equipment[]) data[1]));

    public static void setWeapons(Equipment[] readWeapons) {
        if(weapons != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(WEAPON_NAME, readWeapons);
    }

    public static void setBoots(Equipment[] readBoots) {
        if(boots != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(BOOTS_NAME, readBoots);
    }

    public static void setHelmets(Equipment[] readHelmets) {
        if(helmets != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(HELMET_NAME, readHelmets);
    }

    public static void setGauntlets(Equipment[] readGauntlets) {
        if(gauntlets != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(GAUNTLETS_NAME, readGauntlets);
    }

    public static void setBreastPlates(Equipment[] readBreastPlates) {
        if(breastPlates != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(BREAST_PLATE_NAME, readBreastPlates);
    }

    /* pacakage */ static Equipment get(int index, String equipmentType) {
        return equipment.get(equipmentType)[index];
    }

    /* package */ static int size(String equipmentType) {
        return equipment.get(equipmentType).length;
    }


}
