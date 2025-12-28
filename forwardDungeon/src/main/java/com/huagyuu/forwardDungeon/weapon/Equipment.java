package com.huagyuu.forwardDungeon.weapon;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-26
 * @Version: 1.0
 */
@Data
public abstract class Equipment {
    protected String name;
    protected int equipmentType;

    public Equipment(int equipmentType, String name) {
        this.equipmentType = equipmentType;
        this.name = name;
    }

    public Equipment() {
    }
}
