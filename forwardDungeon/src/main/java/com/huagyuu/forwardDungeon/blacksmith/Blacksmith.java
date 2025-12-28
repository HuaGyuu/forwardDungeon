package com.huagyuu.forwardDungeon.blacksmith;

import com.huagyuu.forwardDungeon.armor.Helmet;
import com.huagyuu.forwardDungeon.dao.ForwardDungeonDao;
import com.huagyuu.forwardDungeon.mine.Gold;
import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.mine.Mine;
import com.huagyuu.forwardDungeon.weapon.Weapon;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-27
 * @Version: 1.0
 */
@Data
public class Blacksmith {
    private List<BuildEquipment> buildEquipments;

    public Blacksmith(List<BuildEquipment> buildEquipments) {
        this.buildEquipments = buildEquipments;
    }

    public Blacksmith() {
        this.buildEquipments = new ArrayList<>();
    }

    public static Blacksmith loadBlacksmith() {
        Blacksmith blacksmith = new Blacksmith(ForwardDungeonDao.loadAllBuildEquipments());
        return blacksmith;
    }
}
