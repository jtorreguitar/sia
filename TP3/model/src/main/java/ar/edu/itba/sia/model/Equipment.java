package ar.edu.itba.sia.model;

public class Equipment {
    private final Long id;
    private final Double strength;
    private final Double agility;
    private final Double wisdom;
    private final Double endurance;
    private final Double health;

    public Equipment(Long id, Double strength, Double agility, Double wisdom, Double endurance, Double health) {
        this.id = id;
        this.strength = strength;
        this.agility = agility;
        this.wisdom = wisdom;
        this.endurance = endurance;
        this.health = health;
    }

    public Long getId() {
        return id;
    }

    public Double getStrength() {
        return strength;
    }

    public Double getAgility() {
        return agility;
    }

    public Double getWisdom() {
        return wisdom;
    }

    public Double getEndurance() {
        return endurance;
    }

    public Double getHealth() {
        return health;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Equipment))
            return false;
        Equipment e = (Equipment) obj;
        return e.id.equals(id);
    }
}
