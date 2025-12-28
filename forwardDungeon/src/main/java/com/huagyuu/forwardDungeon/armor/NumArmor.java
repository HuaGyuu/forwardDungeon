package com.huagyuu.forwardDungeon.armor;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class NumArmor {
    private Armor armor;
    private int num;

    public NumArmor(Armor armor) {
        this.armor = armor;
        num = 1;
    }

    public NumArmor(Armor armor, int num) {
        this.armor = armor;
        this.num = num;
    }
}
