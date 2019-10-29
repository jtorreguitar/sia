package ar.edu.itba.sia.model;

public class CharacterData {
    private final Double attackModifier;
    private final Double defenseModifier;
    private final Double strengthModifier;
    private final Double agilityModifier;
    private final Double wisdomModifier;
    private final Double enduranceModifier;
    private final Double healthModifier;

    public CharacterData(Double attackModifier,
                         Double defenseModifier,
                         Double strengthModifier,
                         Double agilityModifier,
                         Double wisdomModifier,
                         Double enduranceModifier,
                         Double healthModifier) {
        this.attackModifier = attackModifier;
        this.defenseModifier = defenseModifier;
        this.strengthModifier = strengthModifier;
        this.agilityModifier = agilityModifier;
        this.wisdomModifier = wisdomModifier;
        this.enduranceModifier = enduranceModifier;
        this.healthModifier = healthModifier;
    }

    public Double getAttackModifier() {
        return attackModifier;
    }

    public Double getDefenseModifier() {
        return defenseModifier;
    }

    public Double getStrengthModifier() {
        return strengthModifier;
    }

    public Double getAgilityModifier() {
        return agilityModifier;
    }

    public Double getWisdomModifier() {
        return wisdomModifier;
    }

    public Double getEnduranceModifier() {
        return enduranceModifier;
    }

    public Double getHealthModifier() {
        return healthModifier;
    }
}
