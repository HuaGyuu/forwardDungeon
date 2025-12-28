package com.huagyuu.forwardDungeon.shop;

import com.huagyuu.forwardDungeon.armor.Armor;
import com.huagyuu.forwardDungeon.armor.NumArmor;
import com.huagyuu.forwardDungeon.dao.ForwardDungeonDao;
import com.huagyuu.forwardDungeon.mine.*;
import com.huagyuu.forwardDungeon.weapon.NumWeapon;
import com.huagyuu.forwardDungeon.weapon.Weapon;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-26
 * @Version: 1.0
 */
@Data
public class Shop {
    private List<Mine> mineList;
    private List<Weapon> weaponList;
    private List<Armor> armorList;

    public Shop(List<Mine> mineList, List<Armor> armorList, List<Weapon> weaponList) {
        this.mineList = mineList;
        this.armorList = armorList;
        this.weaponList = weaponList;
    }

    public Shop() {
        mineList = new ArrayList<>();
        mineList.add(new Stone());
        mineList.add(new Iron());
        mineList.add(new Copper());
        mineList.add(new Silver());
        mineList.add(new Gold());
    }

    public static Shop loadShop() {
        Shop shop = new Shop();
        shop.weaponList = ForwardDungeonDao.loadShopWeapons();
        shop.armorList = ForwardDungeonDao.loadShopArmors();
        return shop;
    }
}
