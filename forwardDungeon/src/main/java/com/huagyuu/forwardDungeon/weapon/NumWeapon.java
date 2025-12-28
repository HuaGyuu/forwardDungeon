package com.huagyuu.forwardDungeon.weapon;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class NumWeapon {
    private Weapon weapon;
    private int num;

    public NumWeapon(int num, Weapon weapon) {
        this.num = num;
        this.weapon = weapon;
    }

    public NumWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.num = 1;
    }
}
