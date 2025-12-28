package com.huagyuu.forwardDungeon.machine;

import com.huagyuu.forwardDungeon.hero.Hero;
import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.mine.Mine;
import com.huagyuu.forwardDungeon.mine.MineUnit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
@Data
public class MineMachine implements Runnable{
    private List<HeroMine> heroMines;
    private int level;
    private double rate;
    private boolean flush;
    private int mineLevel;
    // 下一级所需的钱
    private double upMoney;
    // 机器等级与矿物等级的比例
    private int proportion;

    public MineMachine(List<HeroMine> heroMines) {
        this.heroMines = heroMines;
        this.level = 1;
        this.rate = 1;
        this.flush = false;
        this.mineLevel = 1;
        this.upMoney = 10.0;
        this.proportion = 50;
    }

    public MineMachine(int level, double rate, int mineLevel, double upMoney, int proportion) {
        this.level = level;
        this.mineLevel = mineLevel;
        this.proportion = proportion;
        this.rate = rate;
        this.upMoney = upMoney;
        this.flush = false;
    }

    @Override
    public void run() {
        try {
            updateAllMineRate(heroMines);
            List<HeroMine> affordMines = new ArrayList<>();
            while (true) {
                for (HeroMine heroMine: heroMines) {
                    if (mineLevel >= heroMine.getMine().getLevel()) {
                        affordMines.add(heroMine);
                    }
                }

                flush = false;
                while (!flush) {
                    Thread.sleep(1000);
                    for (int i = 0; i < affordMines.size(); i++) {
                        HeroMine heroMine = affordMines.get(i);
                        double total = heroMine.getTotal();
                        double rate1 = heroMine.getRate();

                        if (heroMine.getHeavyUnit().equals(heroMine.getRateUnit()))
                            heroMine.setTotal(total + rate1);
                        else {
                            heroMine.setTotal(totalToGram(heroMine) + rateToGram(heroMine));
                            heroMine.setHeavyUnit(MineUnit.GRAM);
                        }

                        changeUnit(heroMine);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMineRate(HeroMine heroMine) {
        Mine mine = heroMine.getMine();
        double mineWeight = mine.getWeight();
        if (mineLevel < heroMine.getMine().getLevel())
            heroMine.setRate(0);
        else heroMine.setRate(mineWeight * rate * 0.1);
    }

    private void updateAllMineRate(List<HeroMine> heroMine) {
        for (HeroMine mine : heroMine) {
            updateMineRate(mine);
        }
    }

    public boolean levelUp(Hero hero, List<HeroMine> heroMine) {
        if (hero.getMoney() >= upMoney) {
            hero.setMoney(hero.getMoney() - upMoney);
            level++;

            rate *= 1.1;
            upMoney *= 1.2;
            updateAllMineRate(heroMine);

            if (level / proportion >= mineLevel) {
                mineLevel++;
                flush();
            }

            return true;
        }
        return false;
    }

    public void flush() {
        flush = true;
    }

    public void changeUnit(MineUnit mineUnit) {}

    private void changeUnit(HeroMine heroMine) {
        if (heroMine.getTotal() > 1000) {
            heroMine.setTotal(heroMine.getTotal() / 1000);
            heroMine.setHeavyUnit(plusUnit(heroMine.getHeavyUnit()));
        }
/*
        if (heroMine.getRate() > 1000) {
            heroMine.setRate(heroMine.getTotal() / 1000);
            heroMine.setRateUnit(plusUnit(heroMine.getRateUnit()));
        }*/
    }

    private MineUnit plusUnit(MineUnit heavyUnit) {
        MineUnit mineUnit = null;
        switch (heavyUnit.getUnitName()) {
            case "g":
                mineUnit = MineUnit.KILOGRAM;
                break;
            case "kg":
                mineUnit = MineUnit.TON;
                break;
            case "t":
                mineUnit = MineUnit.KILOTON;
                break;
            case "kt":
                mineUnit = MineUnit.KILOGRAM;
                break;
        }
        return mineUnit;
    }

    public static double totalToGram(HeroMine heroMine) {
        int thousandPower = heroMine.getHeavyUnit().getThousandPower();
        double total = heroMine.getTotal();

        int i = 1;
        if (thousandPower >= 1) {
            for (int j = 0; j < thousandPower; j++) {
                i *= 1000;
            }
        }

        return total * i;
    }

    private double rateToGram(HeroMine heroMine) {
        int thousandPower = heroMine.getRateUnit().getThousandPower();
        double rate1 = heroMine.getRate();

        int i = 1;
        if (thousandPower >= 1) {
            for (int j = 0; j < thousandPower; j++) {
                i *= 1000;
            }
        }

        return rate1 * i;
    }
}
