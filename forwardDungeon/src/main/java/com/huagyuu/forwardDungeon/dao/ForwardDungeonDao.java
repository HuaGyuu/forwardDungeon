package com.huagyuu.forwardDungeon.dao;

import com.huagyuu.forwardDungeon.armor.*;
import com.huagyuu.forwardDungeon.blacksmith.BuildEquipment;
import com.huagyuu.forwardDungeon.dungeon.Dungeon;
import com.huagyuu.forwardDungeon.dungeon.event.DungeonEvent;
import com.huagyuu.forwardDungeon.dungeon.event.EquipmentBox;
import com.huagyuu.forwardDungeon.dungeon.event.Monster;
import com.huagyuu.forwardDungeon.hero.Hero;
import com.huagyuu.forwardDungeon.hero.Warehouse;
import com.huagyuu.forwardDungeon.machine.MineMachine;
import com.huagyuu.forwardDungeon.mine.HeroMine;
import com.huagyuu.forwardDungeon.mine.Mine;
import com.huagyuu.forwardDungeon.mine.MineUnit;
import com.huagyuu.forwardDungeon.weapon.Equipment;
import com.huagyuu.forwardDungeon.weapon.NumWeapon;
import com.huagyuu.forwardDungeon.weapon.Weapon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class ForwardDungeonDao {
    public static void saveHeroMines(List<HeroMine> heroMineList) {
        String path = "data/heroMines.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            for (HeroMine heroMine : heroMineList) {
                String statement = heroMine.getMine().getName() + " "
                        + heroMine.getTotal() + " "
                        + heroMine.getHeavyUnit().getUnitName() + " "
                        + heroMine.getRate() + " "
                        + heroMine.getRateUnit().getUnitName();

                bufferedWriter.write(statement);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveHeroInfo(Hero hero) {
        String path = "data/heroInfo.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            Weapon weapon = hero.getWeapon();
            Helmet helmet = hero.getHelmet();
            Cuirass cuirass = hero.getCuirass();
            Glove glove = hero.getGlove();
            LegGuard legGuard = hero.getLegGuard();
            Shoe shoe = hero.getShoe();

            String statement = hero.getMoney() + " " +
                    hero.getLevel() + " " +
                    hero.getHp() + " " +
                    hero.getDefense() + " " +
                    hero.getAttack() + " " +
                    weapon.getName() + " " +
                    weapon.getLevel() + " " +
                    weapon.getStar() + " " +
                    weapon.getDamage() + " " +
                    weapon.getValue() + " " +
                    weapon.getUpValue() + " " +
                    weapon.getEquipmentType() + " " +
                    helmet.getName() + " " +
                    helmet.getLevel() + " " +
                    helmet.getStar() + " " +
                    helmet.getDefense() + " " +
                    helmet.getValue() + " " +
                    helmet.getUpValue() + " " +
                    helmet.getEquipmentType() + " " +
                    helmet.getType() + " " +
                    cuirass.getName() + " " +
                    cuirass.getLevel() + " " +
                    cuirass.getStar() + " " +
                    cuirass.getDefense() + " " +
                    cuirass.getValue() + " " +
                    cuirass.getUpValue() + " " +
                    cuirass.getEquipmentType() + " " +
                    cuirass.getType() + " " +
                    glove.getName() + " " +
                    glove.getLevel() + " " +
                    glove.getStar() + " " +
                    glove.getDefense() + " " +
                    glove.getValue() + " " +
                    glove.getUpValue() + " " +
                    glove.getEquipmentType() + " " +
                    glove.getType() + " " +
                    legGuard.getName() + " " +
                    legGuard.getLevel() + " " +
                    legGuard.getStar() + " " +
                    legGuard.getDefense() + " " +
                    legGuard.getValue() + " " +
                    legGuard.getUpValue() + " " +
                    legGuard.getEquipmentType() + " " +
                    legGuard.getType() + " " +
                    shoe.getName() + " " +
                    shoe.getLevel() + " " +
                    shoe.getStar() + " " +
                    shoe.getDefense() + " " +
                    shoe.getValue() + " " +
                    shoe.getUpValue() + " " +
                    shoe.getEquipmentType() + " " +
                    shoe.getType() + " " +
                    hero.getUpMoney() + " " +
                    hero.getUpValue();

            bufferedWriter.write(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveNumWeapons(List<NumWeapon> numWeapons) {
        String path = "data/numWeapons.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            for (NumWeapon numWeapon : numWeapons) {
                Weapon weapon = numWeapon.getWeapon();
                String statement = weapon.getName() + " " +
                        weapon.getLevel() + " " +
                        weapon.getStar() + " " +
                        weapon.getDamage() + " " +
                        weapon.getValue() + " " +
                        weapon.getUpValue() + " " +
                        weapon.getEquipmentType() + " " +
                        numWeapon.getNum();

                bufferedWriter.write(statement);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveNumArmors(List<NumArmor> numArmors) {
        String path = "data/numArmors.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            for (NumArmor numArmor : numArmors) {
                Armor armor = numArmor.getArmor();
                String statement = armor.getName() + " " +
                        armor.getLevel() + " " +
                        armor.getStar() + " " +
                        armor.getDefense() + " " +
                        armor.getValue() + " " +
                        armor.getUpValue() + " " +
                        armor.getEquipmentType() + " " +
                        armor.getType() + " " +
                        numArmor.getNum();

                bufferedWriter.write(statement);
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Hero loadHero() {
        Hero hero = null;
        String path = "data/heroInfo.txt";
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            line = bufferedReader.readLine();
            String[] strings = line.split(" ");
            double money = Double.parseDouble(strings[0]);
            int level = Integer.parseInt(strings[1]);
            int hp = Integer.parseInt(strings[2]);
            int defense = Integer.parseInt(strings[3]);
            int attack = Integer.parseInt(strings[4]);

            Weapon weapon = new Weapon();
            weapon.setName(strings[5]);
            weapon.setLevel(Integer.parseInt(strings[6]));
            weapon.setStar(Integer.parseInt(strings[7]));
            weapon.setDamage(Integer.parseInt(strings[8]));
            weapon.setValue(Integer.parseInt(strings[9]));
            weapon.setUpValue(Integer.parseInt(strings[10]));
            weapon.setEquipmentType(Integer.parseInt(strings[11]));

            Helmet helmet = new Helmet();
            helmet.setName(strings[12]);
            helmet.setLevel(Integer.parseInt(strings[13]));
            helmet.setStar(Integer.parseInt(strings[14]));
            helmet.setDefense(Integer.parseInt(strings[15]));
            helmet.setValue(Integer.parseInt(strings[16]));
            helmet.setUpValue(Integer.parseInt(strings[17]));
            helmet.setEquipmentType(Integer.parseInt(strings[18]));
            helmet.setType(Integer.parseInt(strings[19]));

            Cuirass cuirass = new Cuirass();
            cuirass.setName(strings[20]);
            cuirass.setLevel(Integer.parseInt(strings[21]));
            cuirass.setStar(Integer.parseInt(strings[22]));
            cuirass.setDefense(Integer.parseInt(strings[23]));
            cuirass.setValue(Integer.parseInt(strings[24]));
            cuirass.setUpValue(Integer.parseInt(strings[25]));
            cuirass.setEquipmentType(Integer.parseInt(strings[26]));
            cuirass.setType(Integer.parseInt(strings[27]));

            Glove glove = new Glove();
            glove.setName(strings[28]);
            glove.setLevel(Integer.parseInt(strings[29]));
            glove.setStar(Integer.parseInt(strings[30]));
            glove.setDefense(Integer.parseInt(strings[31]));
            glove.setValue(Integer.parseInt(strings[32]));
            glove.setUpValue(Integer.parseInt(strings[33]));
            glove.setEquipmentType(Integer.parseInt(strings[34]));
            glove.setType(Integer.parseInt(strings[35]));

            LegGuard legGuard = new LegGuard();
            legGuard.setName(strings[36]);
            legGuard.setLevel(Integer.parseInt(strings[37]));
            legGuard.setStar(Integer.parseInt(strings[38]));
            legGuard.setDefense(Integer.parseInt(strings[39]));
            legGuard.setValue(Integer.parseInt(strings[40]));
            legGuard.setUpValue(Integer.parseInt(strings[41]));
            legGuard.setEquipmentType(Integer.parseInt(strings[42]));
            legGuard.setType(Integer.parseInt(strings[43]));

            Shoe shoe = new Shoe();
            shoe.setName(strings[44]);
            shoe.setLevel(Integer.parseInt(strings[45]));
            shoe.setStar(Integer.parseInt(strings[46]));
            shoe.setDefense(Integer.parseInt(strings[47]));
            shoe.setValue(Integer.parseInt(strings[48]));
            shoe.setUpValue(Integer.parseInt(strings[49]));
            shoe.setEquipmentType(Integer.parseInt(strings[50]));
            shoe.setType(Integer.parseInt(strings[51]));

            int upMoney = Integer.parseInt(strings[52]);
            int upValue = Integer.parseInt(strings[53]);

            hero = new Hero(attack, weapon, cuirass, upValue, upMoney, shoe, level, legGuard, helmet, defense, glove, hp, money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hero;
    }

    public static List<HeroMine> loadHeroMines() {
        ArrayList<HeroMine> heroMines = new ArrayList<>();
        String path = "data/heroMines.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                String name = strings[0];
                double total = Double.parseDouble(strings[1]);
                String heavyUnitName = strings[2];
                double rate = Double.parseDouble(strings[3]);
                String rateUnitName = strings[4];

                Mine mine = Mine.getInstanceByName(name);
                MineUnit heavyUnit = MineUnit.getMineUnitByName(heavyUnitName);
                MineUnit rateUnit = MineUnit.getMineUnitByName(rateUnitName);
                heroMines.add(new HeroMine(mine, total, heavyUnit, rate, rateUnit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return heroMines;
    }

    public static List<NumWeapon> loadNumWeapons() {
        String path = "data/numWeapons.txt";
        ArrayList<NumWeapon> numWeapons = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                int num =  Integer.parseInt(strings[7]);

                Weapon weapon = loadWeaponByStrings(strings);
                numWeapons.add(new NumWeapon(num, weapon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numWeapons;
    }

    public static List<NumArmor> loadNumArmors() {
        String path = "data/numArmors.txt";
        ArrayList<NumArmor> numArmors = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                int num =  Integer.parseInt(strings[8]);

                Armor armor = loadArmorByStrings(strings);
                numArmors.add(new NumArmor(armor, num));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numArmors;
    }

    public static Warehouse loadWarehouse() {
        return new Warehouse(loadNumArmors(), loadNumWeapons());
    }

    public static void saveMineMachine(MineMachine machine) {
        String path = "data/mineMachine.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            String statement = machine.getLevel() + " " +
                    machine.getRate() + " " +
                    machine.getMineLevel() + " " +
                    machine.getUpMoney() + " " +
                    machine.getProportion();

            bufferedWriter.write(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MineMachine loadMineMachine() {
        String path = "data/mineMachine.txt";
        MineMachine machine = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line = bufferedReader.readLine();
            String[] strings = line.split(" ");

            int level = Integer.parseInt(strings[0]);
            double rate = Double.parseDouble(strings[1]);
            int mineLevel = Integer.parseInt(strings[2]);
            double upMoney = Double.parseDouble(strings[3]);
            int proportion = Integer.parseInt(strings[4]);

            machine = new MineMachine(level, rate, mineLevel, upMoney, proportion);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return machine;
    }

    public static void saveDungeon(Dungeon dungeon) {
        String path = "data/dungeon.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            DungeonEvent event = dungeon.getDungeonEvent();
            String statement = Dungeon.layer + " " +
                    Dungeon.prize + " " +
                    event.getType();

            bufferedWriter.write(statement);
            bufferedWriter.newLine();

            if (event.getType() == 1) {
                Monster monster = (Monster) event;
                statement = monster.getHp() + " " +
                        monster.getDamage() + " " +
                        monster.getDefence();

                bufferedWriter.write(statement);
            } else {
                EquipmentBox equipmentBox = (EquipmentBox) event;
                Equipment equipment = equipmentBox.getEquipment();

                if (equipment.getEquipmentType() == 1) {
                    Weapon weapon = (Weapon)  equipment;
                    saveWeapon(weapon, bufferedWriter);
                } else {
                    Armor armor = (Armor) equipment;
                    saveArmor(armor, bufferedWriter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveWeapon(Weapon weapon, BufferedWriter bufferedWriter) throws IOException {

        String statement = weapon.getName() + " " +
                weapon.getLevel() + " " +
                weapon.getStar() + " " +
                weapon.getDamage() + " " +
                weapon.getValue() + " " +
                weapon.getUpValue() + " " +
                weapon.getEquipmentType();

        bufferedWriter.write(statement);
    }

    public static void saveArmor(Armor armor, BufferedWriter bufferedWriter) throws IOException {

        String statement = armor.getName() + " " +
                armor.getLevel() + " " +
                armor.getStar() + " " +
                armor.getDefense() + " " +
                armor.getValue() + " " +
                armor.getUpValue() + " " +
                armor.getEquipmentType() + " " +
                armor.getType();

        bufferedWriter.write(statement);
    }

    public static Dungeon loadDungeon() {
        String path = "data/dungeon.txt";
        Dungeon dungeon = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line = bufferedReader.readLine();
            String[] strings = line.split(" ");
            int layer = Integer.parseInt(strings[0]);
            int prize = Integer.parseInt(strings[1]);
            int dungeonEventType = Integer.parseInt(strings[2]);

            line = bufferedReader.readLine();
            DungeonEvent dungeonEvent = null;
            String[] split = line.split(" ");

            if (dungeonEventType == 1) {
                int hp = Integer.parseInt(split[0]);
                int damage = Integer.parseInt(split[1]);
                int defence = Integer.parseInt(split[2]);

                dungeonEvent = new Monster(hp, damage, defence);
            } else {
                String name = split[0];
                int level = Integer.parseInt(split[1]);
                int star = Integer.parseInt(split[2]);
                int value = Integer.parseInt(split[4]);
                int upValue = Integer.parseInt(split[5]);
                int equipmentType = Integer.parseInt(split[6]);

                if (equipmentType == 1) {
                    int damage = Integer.parseInt(split[3]);
                    Weapon weapon = new Weapon(name, level, star, damage, value, upValue, equipmentType);
                    dungeonEvent = new EquipmentBox(weapon);
                } else {
                    int defense = Integer.parseInt(split[3]);
                    int type = Integer.parseInt(split[7]);

                    Armor armor = Armor.getInstanceByType(type);
                    armor.setName(name);
                    armor.setLevel(level);
                    armor.setStar(star);
                    armor.setDefense(defense);
                    armor.setUpValue(upValue);
                    armor.setEquipmentType(equipmentType);
                    armor.setValue(value);

                    dungeonEvent = new EquipmentBox(armor);
                }
            }

            dungeon = new Dungeon(dungeonEvent);
            Dungeon.layer =  layer;
            Dungeon.prize = prize;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dungeon;
    }

    public static EquipmentBox randomEquipmentBox() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            String path = "data/weaponOfEquipmentBox.txt";
            Weapon weapon = randomGetWeaponByDungeonLayer(path, Dungeon.layer, random);
            return new EquipmentBox(weapon);
        } else {
            String path = "data/armorOfEquipmentBox.txt";
            Armor armor = randomGetArmorByDungeonLayer(path, Dungeon.layer, random);
            return new EquipmentBox(armor);
        }
    }

    private static Armor randomGetArmorByDungeonLayer(String path, int layer, Random random) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            int index = random.nextInt((layer == 0 ? 1 : layer) * 2) + 1;
            for (int i = 1; i < index; i++) {
                bufferedReader.readLine();
            }

            String line = bufferedReader.readLine();
            String[] strings = line.split(" ");

            return loadArmorByStrings(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weapon randomGetWeaponByDungeonLayer(String path, int layer,  Random random) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            int index = random.nextInt((layer == 0 ? 1 : layer)) + 1;
            for (int i = 1; i < index; i++) {
                bufferedReader.readLine();
            }

            String line = bufferedReader.readLine();
            String[] strings = line.split(" ");

            return loadWeaponByStrings(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Armor loadArmorByStrings(String[] strings) {
        String name = strings[0];
        int level = Integer.parseInt(strings[1]);
        int star  = Integer.parseInt(strings[2]);
        int defense = Integer.parseInt(strings[3]);
        int value  = Integer.parseInt(strings[4]);
        int upValue = Integer.parseInt(strings[5]);
        int equipmentType = Integer.parseInt(strings[6]);
        int type = Integer.parseInt(strings[7]);

        Armor armor = Armor.getInstanceByType(type);
        armor.setName(name);
        armor.setLevel(level);
        armor.setStar(star);
        armor.setDefense(defense);
        armor.setValue(value);
        armor.setUpValue(upValue);
        armor.setEquipmentType(equipmentType);

        return armor;
    }

    public static Weapon loadWeaponByStrings(String[] strings) {
        String name = strings[0];
        int level = Integer.parseInt(strings[1]);
        int star  = Integer.parseInt(strings[2]);
        int damage = Integer.parseInt(strings[3]);
        long value  = Long.parseLong(strings[4]);
        int upValue = Integer.parseInt(strings[5]);
        int equipmentType = Integer.parseInt(strings[6]);

        return new Weapon(name, level, star, damage, value, upValue, equipmentType);
    }

    public static List<Weapon> loadShopWeapons() {
        String path = "data/weaponOfShop.txt";
        ArrayList<Weapon> weapons = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                weapons.add(loadWeaponByStrings(strings));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return weapons;
    }

    public static List<Armor> loadShopArmors() {
        String path = "data/armorOfShop.txt";
        ArrayList<Armor> armors = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                armors.add(loadArmorByStrings(strings));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return armors;
    }

    public static List<BuildEquipment> loadWeaponOfBuildEquipments() {
        String path = "data/weaponOfBuildEquipment.txt";
        ArrayList<BuildEquipment> buildEquipments = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                Weapon weapon = loadWeaponByStrings(strings);
                List<HeroMine> heroMineList = loadHeroMineOfBuildEquipmentsByStrings(strings, 7);
                buildEquipments.add(new BuildEquipment(weapon, heroMineList, weapon.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buildEquipments;
    }

    public static List<BuildEquipment> loadArmorOfBuildEquipments() {
        String path = "data/armorOfBuildEquipment.txt";
        ArrayList<BuildEquipment> buildEquipments = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" ");
                Armor armor = loadArmorByStrings(strings);
                List<HeroMine> heroMineList = loadHeroMineOfBuildEquipmentsByStrings(strings, 8);
                buildEquipments.add(new BuildEquipment(armor, heroMineList, armor.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buildEquipments;
    }

    public static List<HeroMine> loadHeroMineOfBuildEquipmentsByStrings(String[] strings, int index) {
        ArrayList<HeroMine> heroMineList = new ArrayList<>();
        try {
            while (true) {
                String name = strings[index++];
                double total = Double.parseDouble(strings[index++]);

                Mine mine = Mine.getInstanceByName(name);
                heroMineList.add(new HeroMine(total, mine));
            }
        } catch (Exception e) {

        } finally {
            return heroMineList;
        }
    }

    public static List<BuildEquipment> loadAllBuildEquipments() {
        List<BuildEquipment> buildEquipments = loadWeaponOfBuildEquipments();
        buildEquipments.addAll(loadArmorOfBuildEquipments());
        return buildEquipments;
    }

    public static int loadTimeLength() {
        String path = "data/timeLength.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String line = bufferedReader.readLine();

            return Integer.parseInt(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void saveTimeLength(int timeLength) {
        String path = "data/timeLength.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            String s = timeLength + "";
            bufferedWriter.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
