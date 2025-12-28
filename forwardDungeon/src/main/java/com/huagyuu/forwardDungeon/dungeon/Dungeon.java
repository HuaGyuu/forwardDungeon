package com.huagyuu.forwardDungeon.dungeon;

import com.huagyuu.forwardDungeon.dao.ForwardDungeonDao;
import com.huagyuu.forwardDungeon.dungeon.event.DungeonEvent;
import com.huagyuu.forwardDungeon.dungeon.event.EquipmentBox;
import com.huagyuu.forwardDungeon.dungeon.event.Monster;
import com.huagyuu.forwardDungeon.weapon.Weapon;
import lombok.Data;

import java.util.Random;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-26
 * @Version: 1.0
 */
@Data
public class Dungeon {
    private DungeonEvent dungeonEvent;
    public static int layer = 0;
    public static int prize = 10;

    public Dungeon() {
        this.dungeonEvent = randomDungeonEvent();
        layer++;
        prize += layer;
    }

    public Dungeon(DungeonEvent dungeonEvent) {
        this.dungeonEvent = dungeonEvent;
    }

    private DungeonEvent randomDungeonEvent() {
        Random random = new Random();
        int i = random.nextInt(100);
        if (i <= 90) {
            int base = prize / 2;
            return new Monster(random.nextInt(base) + base, random.nextInt(base) + base, random.nextInt(base) + base);
        }

        return ForwardDungeonDao.randomEquipmentBox();
    }

    public static Dungeon loadDungeon() {
        Dungeon dungeon = new Dungeon();
        layer = 0;
        prize = 10;
        return dungeon;
    }
}
