package com.huagyuu.forwardDungeon.hero;


import com.huagyuu.forwardDungeon.armor.*;
import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.weapon.NumWeapon;
import com.huagyuu.forwardDungeon.weapon.Weapon;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class Hero {
    private double money;
    private int level;
    private int hp;
    private int defense;
    private int attack;
    private Weapon weapon;
    private Helmet helmet;
    private Cuirass cuirass;
    private Glove glove;
    private LegGuard legGuard;
    private Shoe shoe;
    private Warehouse warehouse;
    private int upMoney;
    private int upValue;

    public Hero() {
        this.money = 20.0;
        this.level = 1;
        this.hp = 100;
        this.defense = 100;
        this.attack = 100;
        this.upMoney = 50;
    }

    public Hero(int attack, Weapon weapon, Cuirass cuirass, int upValue, int upMoney, Shoe shoe, int level, LegGuard legGuard, Helmet helmet, int defense, Glove glove, int hp, double money) {
        this.attack = attack;
        this.weapon = weapon;
        this.cuirass = cuirass;
        this.upValue = upValue;
        this.upMoney = upMoney;
        this.shoe = shoe;
        this.level = level;
        this.legGuard = legGuard;
        this.helmet = helmet;
        this.defense = defense;
        this.glove = glove;
        this.hp = hp;
        this.money = money;
    }

    public Hero(Warehouse warehouse) {
        this.money = 1000.0;
        this.level = 1;
        this.hp = 5;
        this.defense = 5;
        this.attack = 1;
        this.warehouse = warehouse;
        this.upMoney = 50;
        this.weapon = new Weapon(1, "咸鱼之刃", 1);
        this.helmet = new Helmet(1, "咸鱼之盔", 1);
        this.cuirass = new Cuirass(1, "咸鱼之甲", 1);
        this.glove = new Glove(1, "咸鱼之掌", 1);
        this.legGuard = new LegGuard(1, "咸鱼之腿", 1);
        this.shoe = new Shoe(1, "咸鱼之靴", 1);
        this.upValue = 1;
    }

    public void flushWeaponInfo() {
        this.attack = weapon.getDamage();
    }

    public void flushArmorInfo() {
        this.defense = helmet.getDefense() + cuirass.getDefense() + glove.getDefense()
                + legGuard.getDefense() + shoe.getDefense();
    }

    public Armor getArmor(int type) {
        switch (type) {
            case 1:
                return helmet;
            case 2:
                return cuirass;
            case 3:
                return glove;
            case 4:
                return legGuard;
            case 5:
                return shoe;
        }
        return null;
    }

    public void setArmor(Armor a) {
        switch (a.getType()) {
            case 1:
                this.helmet = (Helmet) a;
                break;
            case 2:
                this.cuirass = (Cuirass) a;
                break;
            case 3:
                this.glove = (Glove) a;
                break;
            case 4:
                this.legGuard = (LegGuard) a;
                break;
            case 5:
                this.shoe = (Shoe) a;
                break;
        }
    }

    public List<Armor> getArmors() {
        List<Armor> armors = new ArrayList<>();
        armors.add(helmet);
        armors.add(cuirass);
        armors.add(glove);
        armors.add(legGuard);
        armors.add(shoe);
        return armors;
    }

    public Armor getArmorByTypeOrIndex(int type) {
        switch (type) {
            case 1:
                return helmet;
            case 2:
                return cuirass;
            case 3:
                return glove;
            case 4:
                return legGuard;
            case 5:
                return shoe;
        }
        return null;
    }

    public int getSameWeaponNum() {
        for (NumWeapon numWeapon : warehouse.getNumWeapons()) {
            if (weapon.equals(numWeapon.getWeapon()))
                return numWeapon.getNum() + 1;
        }
        return 1;
    }

    public int getSameArmorNum(Armor armor) {
        Armor heroArmor = getArmorByTypeOrIndex(armor.getType());

        for (NumArmor numArmor : warehouse.getNumArmors()) {
            if (heroArmor.equals(numArmor.getArmor()))
                return numArmor.getNum() + 1;
        }
        return 1;
    }

    public void levelUp() {
        level++;
        upMoney *= 1.1;
        hp += upValue++;
    }

}
