package ar.edu.itba.sia.model;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    public List<Chromosome> generateRandomPopulation(int populationSize, final CharacterData characterData, final Random random) {
        return IntStream.range(0, populationSize)
                .mapToObj(i -> randomCharacter(characterData, random))
                .collect(Collectors.toList());
    }

    private Character randomCharacter(final CharacterData characterData, final Random random) {
        return new Character(EquipmentStorage.get(random.nextInt(EquipmentStorage.size(EquipmentStorage.WEAPON_NAME)), EquipmentStorage.WEAPON_NAME),
                            EquipmentStorage.get(random.nextInt(EquipmentStorage.size(EquipmentStorage.BOOTS_NAME)), EquipmentStorage.BOOTS_NAME),
                            EquipmentStorage.get(random.nextInt(EquipmentStorage.size(EquipmentStorage.GAUNTLETS_NAME)), EquipmentStorage.GAUNTLETS_NAME),
                            EquipmentStorage.get(random.nextInt(EquipmentStorage.size(EquipmentStorage.HELMET_NAME)), EquipmentStorage.HELMET_NAME),
                            EquipmentStorage.get(random.nextInt(EquipmentStorage.size(EquipmentStorage.BREAST_PLATE_NAME)), EquipmentStorage.BREAST_PLATE_NAME),
                            characterData,
                            Character.randomHeight(random),
                            random);
    }
}
