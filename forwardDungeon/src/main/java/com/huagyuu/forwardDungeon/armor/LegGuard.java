package com.huagyuu.forwardDungeon.armor;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class LegGuard extends Armor {
    public LegGuard() {
        super();
        this.type = 4;
    }

    public LegGuard(int defense, String name, int value) {
        super(defense, name, value);
        this.type = 4;
    }
}
