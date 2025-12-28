package com.huagyuu.forwardDungeon.blacksmith;

import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.mine.Mine;
import com.huagyuu.forwardDungeon.weapon.Equipment;
import lombok.Data;

import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-27
 * @Version: 1.0
 */
@Data
public class BuildEquipment {
    private Equipment equipment;
    private List<HeroMine> mineList;
    private long price;

    public BuildEquipment(Equipment equipment, List<HeroMine> mineList, long price) {
        this.equipment = equipment;
        this.mineList = mineList;
        this.price = price;
    }
}
