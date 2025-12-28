package com.huagyuu.forwardDungeon.hero;

import com.huagyuu.forwardDungeon.armor.Armor;
import com.huagyuu.forwardDungeon.armor.NumArmor;
import com.huagyuu.forwardDungeon.machine.MineMachine;
import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.mine.Mine;
import com.huagyuu.forwardDungeon.mine.MineUnit;
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
public class Warehouse {
    private List<HeroMine> heroMines;
    private List<NumWeapon>  numWeapons;
    private List<NumArmor> numArmors;

    public Warehouse() {
        heroMines = new ArrayList<>();
        numWeapons = new ArrayList<>();
        numArmors = new ArrayList<>();
    }

    public Warehouse(List<HeroMine> heroMines, List<NumArmor> numArmors, List<NumWeapon> numWeapons) {
        this.heroMines = heroMines;
        this.numArmors = numArmors;
        this.numWeapons = numWeapons;
    }

    public Warehouse(List<NumArmor> numArmors, List<NumWeapon> numWeapons) {
        this.numArmors = numArmors;
        this.numWeapons = numWeapons;
    }

    public boolean decreaseNumWeapon(int id) {
        NumWeapon numWeapon = numWeapons.get(id);

        if (numWeapon == null) return false;

        int sum = numWeapon.getNum();
        if (sum == 1) numWeapons.remove(id);
        else numWeapon.setNum(sum - 1);

        return true;
    }

    public boolean decreaseNumWeapon(int id, int num) {
        NumWeapon numWeapon = numWeapons.get(id);

        if (numWeapon == null) return false;

        int sum = numWeapon.getNum();
        if (sum == num) numWeapons.remove(id);
        else numWeapon.setNum(sum - num);

        return true;
    }

    public boolean decreaseNumArmor(int id) {
        NumArmor numArmor = numArmors.get(id);

        if (numArmor == null) return false;

        int sum = numArmor.getNum();
        if (sum == 1) numArmors.remove(id);
        else numArmor.setNum(sum - 1);

        return true;
    }

    public boolean decreaseNumArmor(int id, int num) {
        NumArmor numArmor = numArmors.get(id);

        if (numArmor == null) return false;

        int sum = numArmor.getNum();
        if (sum == num) numArmors.remove(id);
        else numArmor.setNum(sum - num);

        return true;
    }

    public void decreaseHeroMine(HeroMine minusHeroMine) {
        Mine minusMine = minusHeroMine.getMine();
        double minusTotal = MineMachine.totalToGram(minusHeroMine);

        for (HeroMine heroMine : heroMines) {
            if (heroMine.getMine().equals(minusMine)) {
                heroMine.setTotal(MineMachine.totalToGram(heroMine) - minusTotal);
                heroMine.setHeavyUnit(MineUnit.GRAM);
                break;
            }
        }
    }


    public boolean increaseNumWeapon(Weapon weapon) {
        if (weapon == null) return false;

        for (NumWeapon numWeapon : numWeapons) {
            Weapon houseWeapon = numWeapon.getWeapon();
            if (weapon.equals(houseWeapon)) {
                numWeapon.setNum(numWeapon.getNum() + 1);
                weapon = null;
                break;
            }
        }

        if (weapon != null) numWeapons.add(new NumWeapon(weapon));

        return true;
    }

    public boolean increaseNumWeapon(Weapon weapon, int num) {
        if (weapon == null) return false;

        for (NumWeapon numWeapon : numWeapons) {
            Weapon houseWeapon = numWeapon.getWeapon();
            if (weapon.equals(houseWeapon)) {
                numWeapon.setNum(numWeapon.getNum() + num);
                weapon = null;
                break;
            }
        }

        if (weapon != null) numWeapons.add(new NumWeapon(num, weapon));

        return true;
    }

    public boolean increaseNumArmor(Armor armor) {
        if (armor == null) return false;

        for (NumArmor numArmor : numArmors) {
            Armor houseArmor = numArmor.getArmor();
            if (armor.equals(houseArmor)) {
                numArmor.setNum(numArmor.getNum() + 1);
                armor = null;
                break;
            }
        }

        if (armor != null) numArmors.add(new NumArmor(armor));

        return true;
    }
    public boolean increaseNumArmor(Armor armor, int num) {
        if (armor == null) return false;

        for (NumArmor numArmor : numArmors) {
            Armor houseArmor = numArmor.getArmor();
            if (armor.equals(houseArmor)) {
                numArmor.setNum(numArmor.getNum() + num);
                armor = null;
                break;
            }
        }

        if (armor != null) numArmors.add(new NumArmor(armor, num));

        return true;
    }

    public int getSameWeaponIndex(Weapon weapon) {
        int i = 0;
        if (weapon == null) return -1;

        for (; i < numWeapons.size(); i++) {
            if (weapon.equals(numWeapons.get(i).getWeapon())) {
                return i;
            }
        }

        return -1;
    }

    public int getSameArmorIndex(Armor armor) {
        int i = 0;
        if (armor == null) return -1;

        for (; i < numArmors.size(); i++) {
            if (armor.equals(numArmors.get(i).getArmor())) {
                return i;
            }
        }

        return -1;
    }

    public boolean heroMinesContainsHeroMine(HeroMine buildMine) {
        for (HeroMine heroMine : heroMines) {
            if (heroMine.getMine().equals(buildMine.getMine()) && MineMachine.totalToGram(heroMine) >= MineMachine.totalToGram(buildMine)) {
                return true;
            }
        }
        return false;
    }
}
