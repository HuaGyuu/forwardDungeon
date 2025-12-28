package com.huagyuu.forwardDungeon.weapon;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class Weapon extends Equipment{
    private int damage;
    private long value;
    private int level;
    private int star;
    private int upValue;

    public Weapon(int damage, String name, int value) {
        this.damage = damage;
        this.name = name;
        this.value = value;
        this.level = 1;
        this.star = 0;
        this.upValue = 1;
        this.equipmentType = 1;
    }

    public Weapon(String name, int level, int star, int damage, long value, int upValue, int equipmentType) {
        super(equipmentType, name);
        this.value = value;
        this.upValue = upValue;
        this.star = star;
        this.level = level;
        this.damage = damage;
    }

    public Weapon() {
    }

    public boolean equals(Weapon weapon) {
        return this.name.equals(weapon.getName()) &&
                this.level == weapon.level &&
                this.star == weapon.star;
    }

    public void levelUp() {
        this.level++;
        int i = upValue++;
        damage += i * (int) Math.pow(2, star);
        value += i * (int) Math.pow(2, star);
    }

    public Weapon copy() {
        Weapon newWeapon = new Weapon();
        newWeapon.setLevel(level);
        newWeapon.setName(name);
        newWeapon.setDamage(damage);
        newWeapon.setStar(star);
        newWeapon.setValue(value);

        return newWeapon;
    }

    public void starUp() {
        this.star++;
        int i = upValue++;
        damage += i * (int) Math.pow(2, star);
        value += i * (int) Math.pow(2, star);
    }

    public long getLevelUpValue() {
        return value + upValue + 1;
    }
}
