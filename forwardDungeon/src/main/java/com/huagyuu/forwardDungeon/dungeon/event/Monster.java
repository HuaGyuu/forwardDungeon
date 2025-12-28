package com.huagyuu.forwardDungeon.dungeon.event;

import lombok.Data;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-26
 * @Version: 1.0
 */
@Data
public class Monster extends DungeonEvent{
    private int hp;
    private int damage;
    private int defence;

    public Monster(int hp, int damage, int defence) {
        this.hp = hp;
        this.damage = damage;
        this.defence = defence;
        this.type = 1;
    }
}
