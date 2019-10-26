package ar.edu.itba.sia.model;

import ar.edu.itba.sia.interfaces.Chromosome;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Character implements Chromosome {

    private static final Double MAX_HEIGHT = 2.0;
    private static final Double MIN_HEIGHT = 1.3;
    private static final Integer EQUIPMENT_COUNT = 5;
    private static final Integer HEIGHT_INDEX = 5;

    private final Map<Integer, String> indexes = Stream.of(new Object[][] {{0, EquipmentStorage.WEAPON_NAME},
                                                                                {1, EquipmentStorage.BOOTS_NAME},
                                                                                {2, EquipmentStorage.HELMET_NAME},
                                                                                {3, EquipmentStorage.GAUNTLETS_NAME},
                                                                                {4, EquipmentStorage.BREAST_PLATE_NAME}})
                                                            .collect(Collectors.toMap(data -> (Integer) data[0],
                                                                                    data -> (String) data[1]));

    private Map<String, Equipment> equipment = Stream.of(new Object[][] {{EquipmentStorage.WEAPON_NAME, null},
                                                                        {EquipmentStorage.BOOTS_NAME, null},
                                                                        {EquipmentStorage.HELMET_NAME, null},
                                                                        {EquipmentStorage.GAUNTLETS_NAME, null},
                                                                        {EquipmentStorage.BREAST_PLATE_NAME, null}})
                                                    .collect(Collectors.toMap(data -> (String) data[0],
                                                                            data -> (Equipment) data[1]));
    private Double height;
    private Random random;
    private Double characterAttackModifier;
    private Double characterDefenseModifier;
    private Double characterStrengthModifier;
    private Double characterAgilityModifier;
    private Double characterWisdomModifier;
    private Double characterEnduranceModifier;
    private Double characterHealthModifier;

    public Character(Equipment weapon,
                    Equipment boots,
                    Equipment gauntlets,
                    Equipment helmet,
                    Equipment breastPlate,
                    Double characterAttackModifier,
                    Double characterDefenseModifier,
                    Double characterStrengthModifier,
                    Double characterAgilityModifier,
                    Double characterWisdomModifier,
                    Double characterEnduranceModifier,
                    Double characterHealthModifier,
                    Double height,
                    Random random) {
        equipment.put(EquipmentStorage.WEAPON_NAME, weapon);
        equipment.put(EquipmentStorage.BOOTS_NAME, boots);
        equipment.put(EquipmentStorage.HELMET_NAME, gauntlets);
        equipment.put(EquipmentStorage.GAUNTLETS_NAME, helmet);
        equipment.put(EquipmentStorage.BREAST_PLATE_NAME, breastPlate);
        this.random = random;
        this.characterAttackModifier = characterAttackModifier;
        this.characterDefenseModifier = characterDefenseModifier;
        this.characterStrengthModifier = characterStrengthModifier;
        this.characterAgilityModifier = characterAgilityModifier;
        this.characterWisdomModifier = characterWisdomModifier;
        this.characterEnduranceModifier = characterEnduranceModifier;
        this.characterHealthModifier = characterHealthModifier;
        this.height = height;
    }

    private Character(Map<String, Equipment> equipment,
                      Double characterAttackModifier,
                      Double characterDefenseModifier,
                      Double characterStrengthModifier,
                      Double characterAgilityModifier,
                      Double characterWisdomModifier,
                      Double characterEnduranceModifier,
                      Double characterHealthModifier,
                      Double height,
                      Random random) {
        this.equipment = equipment;
        this.random = random;
        this.characterAttackModifier = characterAttackModifier;
        this.characterDefenseModifier = characterDefenseModifier;
        this.characterStrengthModifier = characterStrengthModifier;
        this.characterAgilityModifier = characterAgilityModifier;
        this.characterWisdomModifier = characterWisdomModifier;
        this.characterEnduranceModifier = characterEnduranceModifier;
        this.characterHealthModifier = characterHealthModifier;
        this.height = height;
    }

    @Override
    public double getAptitude() {
        return attack()*characterAttackModifier + defense()*characterDefenseModifier;
    }

    @Override
    public int getAlleleCount() {
        return EQUIPMENT_COUNT + 1; // the alleles are the pieces of equipment and the height
    }

    @Override
    public List<Chromosome> cross(int startIndex, int endIndex, Chromosome mate) {
        if(!(mate instanceof Character)) throw new IllegalArgumentException();
        if(startIndex < endIndex)
            return cross(IntStream.range(startIndex, endIndex).boxed().collect(Collectors.toList()), mate);
        return cross(Stream.concat(IntStream.range(startIndex, getAlleleCount()).boxed(),
                                    IntStream.range(0, startIndex).boxed())
                            .collect(Collectors.toList()),
                    mate);
    }

    @Override
    public List<Chromosome> cross(List<Integer> indexes, Chromosome mate) {
        if(!(mate instanceof Character)) throw new IllegalArgumentException();
        Character character = (Character) mate;
        Map<String, Equipment> retMap1 = new HashMap<>(equipment);
        Map<String, Equipment> retMap2 = new HashMap<>(character.equipment);
        indexes.stream().filter(i -> i < EQUIPMENT_COUNT)
                .map(this.indexes::get)
                .forEach(e -> retMap1.put(e, character.equipment.get(e)));
        indexes.stream().filter(i -> i < EQUIPMENT_COUNT)
                .map(this.indexes::get)
                .forEach(e -> retMap2.put(e, equipment.get(e)));
        Double retHeight1 = height;
        Double retHeight2 = character.height;
        if(indexes.contains(HEIGHT_INDEX)) {
            retHeight1 = character.height;
            retHeight2 = height;
        }
        return Arrays.asList(new Character(retMap1,
                                            characterAttackModifier,
                                            characterDefenseModifier,
                                            characterStrengthModifier,
                                            characterAgilityModifier,
                                            characterWisdomModifier,
                                            characterEnduranceModifier,
                                            characterHealthModifier,
                                            retHeight1,
                                            random),
                                new Character(retMap2,
                                            characterAttackModifier,
                                            characterDefenseModifier,
                                            characterStrengthModifier,
                                            characterAgilityModifier,
                                            characterWisdomModifier,
                                            characterEnduranceModifier,
                                            characterHealthModifier,
                                            retHeight2,
                                            random));
    }

    @Override
    public Chromosome mutate(List<Integer> indexes) {
        Map<String, Equipment> retMap = new HashMap<>(equipment);
        indexes.stream().filter(i -> i < EQUIPMENT_COUNT)
                        .map(this.indexes::get)
                        .forEach(e -> retMap.put(e, EquipmentStorage.get(random.nextInt(EquipmentStorage.size(e)), e)));
        Double retHeight = height;
        if(indexes.contains(HEIGHT_INDEX))
            retHeight = randomInRange(MIN_HEIGHT, MAX_HEIGHT);
        return new Character(retMap,
                characterAttackModifier,
                characterDefenseModifier,
                characterStrengthModifier,
                characterAgilityModifier,
                characterWisdomModifier,
                characterEnduranceModifier,
                characterHealthModifier,
                retHeight,
                random);
    }

    private Double attack() {
        return (agility() + wisdom())*strength()* attackModifier();
    }

    private Double defense() {
        return (endurance() + wisdom())*health()*defenseModifier();
    }

    private Double strength() {
        return 100*Math.tanh(0.01*equipment.values().stream().mapToDouble(e -> e.getStrength()*characterStrengthModifier).sum());
    }

    private Double agility() {
        return Math.tanh(0.01*equipment.values().stream().mapToDouble(e -> e.getAgility()*characterAgilityModifier).sum());
    }

    private Double wisdom() {
        return 0.6*Math.tanh(0.01*equipment.values().stream().mapToDouble(e -> e.getWisdom()*characterWisdomModifier).sum());
    }

    private Double endurance() {
        return Math.tanh(0.01*equipment.values().stream().mapToDouble(e -> e.getEndurance()*characterEnduranceModifier).sum());
    }

    private Double health() {
        return 100*Math.tanh(0.01*equipment.values().stream().mapToDouble(e -> e.getHealth()*characterHealthModifier).sum());
    }

    private Double attackModifier() {
        return 0.5 - Math.pow(3*height -5, 4) + Math.pow(3*height-5, 2) + 0.5*height;
    }

    private Double defenseModifier() {
        return 2 + Math.pow(3*height -5, 4) - Math.pow(3*height-5, 2) - 0.5*height;
    }

    private Double randomInRange(Double left, Double right) {
        return random.nextDouble()*(right-left) + left;
    }
}
