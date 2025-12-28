package com.huagyuu.forwardDungeon.mine;

import lombok.Data;

@Data
public class Mine {
    protected String name;
    protected int value;
    protected double weight;
    protected int level;

    public boolean equals(Mine mine) {
        return name.equals(mine.name);
    }

    public static Mine getInstanceByName(String name) {
        switch (name) {
            case "石矿" :
                return new Stone();
            case "铁矿" :
                return new Iron();
            case "铜矿" :
                return new Copper();
            case "银矿" :
                return new Silver();
            case "金矿" :
                return new Gold();
        }
        return null;
    }
}
