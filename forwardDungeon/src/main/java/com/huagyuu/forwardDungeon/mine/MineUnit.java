package com.huagyuu.forwardDungeon.mine;

public enum MineUnit {
    GRAM("g", 0),KILOGRAM("kg", 1),TON("t", 2),KILOTON("kt", 3);

    String unitName;
    int thousandPower;

    MineUnit(String name, int thousandPower) {
        this.unitName = name;
        this.thousandPower = thousandPower;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getThousandPower() {
        return thousandPower;
    }

    public boolean equals(MineUnit unit) {
        return this.unitName.equals(unit.getUnitName());
    }

    public static MineUnit getMineUnitByName(String name) {
        switch (name) {
            case "g":
                return GRAM;
            case "kg":
                return KILOGRAM;
            case "t":
                return TON;
            case "kt":
                return KILOTON;
        }
        return null;
    }

    public static long unitsToGrams(MineUnit mineUnit) {
        long i = 1;
        for (int i1 = 0; i1 < mineUnit.getThousandPower(); i1++) {
            i *= 1000;
        }

        return i;
    }
}
