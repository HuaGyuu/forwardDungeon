package com.huagyuu.forwardDungeon.armor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class Cuirass extends Armor {
    public Cuirass() {
        super();
        this.type = 2;
    }

    public Cuirass(int defense, String name, int value) {
        super(defense, name, value);
        this.type = 2;
    }
}
