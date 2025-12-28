package com.huagyuu.forwardDungeon.armor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class Glove extends Armor {
    public Glove() {
        super();
        this.type = 3;
    }

    public Glove(int defense, String name, int value) {
        super(defense, name, value);
        this.type = 3;
    }
}
