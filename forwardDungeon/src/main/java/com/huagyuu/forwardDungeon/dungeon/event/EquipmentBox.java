package com.huagyuu.forwardDungeon.dungeon.event;

import com.huagyuu.forwardDungeon.weapon.Equipment;
import lombok.Data;

import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-26
 * @Version: 1.0
 */
@Data
public class EquipmentBox extends DungeonEvent{
    private Equipment equipment;

    public EquipmentBox(Equipment equipmentList) {
        this.equipment = equipmentList;
        this.type = 2;
    }
}
