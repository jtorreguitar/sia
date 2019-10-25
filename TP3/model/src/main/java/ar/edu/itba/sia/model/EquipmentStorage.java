package ar.edu.itba.sia.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* package */ class EquipmentStorage {

    /* package */ static final String WEAPON_NAME = "weapon";
    /* package */ static final String BOOTS_NAME = "boots";
    /* package */ static final String HELMET_NAME = "helmet";
    /* package */ static final String GAUNTLETS_NAME = "gauntlets";
    /* package */ static final String BREAST_PLATE_NAME = "breast plate";

    /* package */ static Equipment[] weapons;
    /* package */ static Equipment[] boots;
    /* package */ static Equipment[] helmets;
    /* package */ static Equipment[] gauntlets;
    /* package */ static Equipment[] breastPlates;

    private static Map<String, Equipment[]> equipment = Stream.of(new Object[][] {{WEAPON_NAME, null},
                                                                                {BOOTS_NAME, null},
                                                                                {HELMET_NAME, null},
                                                                                {GAUNTLETS_NAME, null},
                                                                                {BREAST_PLATE_NAME, null}})
                                                                .collect(Collectors.toMap(data -> (String) data[0],
                                                                                        data -> (Equipment[]) data[1]));

    /* package */ static void setWeapons(Equipment[] readWeapons) {
        if(weapons != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(WEAPON_NAME, readWeapons);
    }

    /* package */ static void setBoots(Equipment[] readBoots) {
        if(boots != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(BOOTS_NAME, readBoots);
    }

    /* package */ static void setHelmets(Equipment[] readHelmets) {
        if(helmets != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(HELMET_NAME, readHelmets);
    }

    /* package */ static void setGauntlets(Equipment[] readGauntlets) {
        if(gauntlets != null)
            throw new IllegalStateException("equipment can only be set once");
        equipment.put(GAUNTLETS_NAME, readGauntlets);
    }

    /* package */ static void setBreastPlates(Equipment[] readBreastPlates) {
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
