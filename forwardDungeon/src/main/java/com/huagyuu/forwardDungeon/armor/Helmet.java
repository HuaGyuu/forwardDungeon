package com.huagyuu.forwardDungeon.armor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class Helmet extends Armor {
    public Helmet(int defense, String name, int value) {
        super(defense, name, value);
        this.type = 1;
    }

    public Helmet() {
        super();
        this.type = 1;
    }
}
