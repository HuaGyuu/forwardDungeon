package com.huagyuu.forwardDungeon.mine;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class HeroMine {
    protected Mine mine;
    protected double total;
    protected MineUnit heavyUnit;
    protected double rate;
    protected MineUnit rateUnit;

    public HeroMine(Mine mine, double total, MineUnit heavyUnit, double rate, MineUnit rateUnit) {
        this.mine = mine;
        this.rate = rate;
        this.total = total;
        this.heavyUnit = heavyUnit;
        this.rateUnit = rateUnit;
    }

    public HeroMine(double total, Mine mine) {
        this.total = total;
        this.mine = mine;
        this.heavyUnit = MineUnit.GRAM;
        this.rateUnit = MineUnit.GRAM;
        this.rate = 0.0;
    }
}
