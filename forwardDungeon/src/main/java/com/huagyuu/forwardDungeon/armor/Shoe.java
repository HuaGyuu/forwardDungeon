package com.huagyuu.forwardDungeon.armor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class Shoe extends Armor {
    public Shoe() {
        super();
        this.type = 5;
    }

    public Shoe(int defense, String name, int value) {
        super(defense, name, value);
        this.type = 5;
    }
}
