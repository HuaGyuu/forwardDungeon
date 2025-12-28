package com.huagyuu.forwardDungeon.armor;

import com.huagyuu.forwardDungeon.dungeon.event.EquipmentBox;
import com.huagyuu.forwardDungeon.weapon.Equipment;
import com.huagyuu.forwardDungeon.weapon.Weapon;
import lombok.Data;

import java.lang.reflect.Constructor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class Armor extends Equipment {
    protected int type;
    protected int defense;
    protected long value;
    protected int level;
    protected int star;
    protected int upValue;

    public Armor(int defense, String name, int value) {
        this.defense = defense;
        this.name = name;
        this.value = value;
        this.level = 1;
        this.star = 0;
        this.upValue = 1;
        this.equipmentType = 2;
    }

    public Armor(String name, int level, int star, int defense, int value, int upValue, int equipmentType) {
        super(equipmentType, name);
        this.value = value;
        this.upValue = upValue;
        this.star = star;
        this.level = level;
        this.defense = defense;
    }

    public Armor() {
    }

    public boolean equals(Armor armor) {
        return this.name.equals(armor.getName()) &&
                this.level == armor.level &&
                this.star == armor.star;
    }

    public void levelUp() {
        this.level++;
        int i = upValue++;
        defense += i * (int) Math.pow(2, star);
        value += i * (int) Math.pow(2, star);
    }

    public <T extends Armor> T copy(Class<T> clazz) {
        T armor = null;
        try {
            Constructor<T> constructor = clazz.getConstructor();
            armor = constructor.newInstance();

            armor.defense = defense;
            armor.level = level;
            armor.star = star;
            armor.name = name;
            armor.value = value;
            armor.type = type;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return armor;
    }

    public void starUp() {
        this.star++;
        int i = upValue++;
        defense += i * (int) Math.pow(2, star);
        value += i * (int) Math.pow(2, star);
    }

    public long getLevelUpValue() {
        return value + upValue + 1;
    }

    public static Armor getInstanceByType(int type) {
        switch (type) {
            case 1:
                return new Helmet();
            case 2:
                return new Cuirass();
            case 3:
                return new Glove();
            case 4:
                return new LegGuard();
            case 5:
                return new Shoe();
        }
        return null;
    }
}
