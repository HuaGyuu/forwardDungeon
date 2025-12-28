package com.huagyuu.forwardDungeon;

import com.huagyuu.forwardDungeon.armor.Armor;
import com.huagyuu.forwardDungeon.armor.Helmet;
import com.huagyuu.forwardDungeon.armor.NumArmor;
import com.huagyuu.forwardDungeon.blacksmith.Blacksmith;
import com.huagyuu.forwardDungeon.blacksmith.BuildEquipment;
import com.huagyuu.forwardDungeon.dao.ForwardDungeonDao;
import com.huagyuu.forwardDungeon.dungeon.Dungeon;
import com.huagyuu.forwardDungeon.dungeon.event.DungeonEvent;
import com.huagyuu.forwardDungeon.dungeon.event.EquipmentBox;
import com.huagyuu.forwardDungeon.dungeon.event.Monster;
import com.huagyuu.forwardDungeon.hero.Hero;
import com.huagyuu.forwardDungeon.hero.Warehouse;
import com.huagyuu.forwardDungeon.machine.MineMachine;
import com.huagyuu.forwardDungeon.mine.*;
import com.huagyuu.forwardDungeon.shop.Shop;
import com.huagyuu.forwardDungeon.weapon.Equipment;
import com.huagyuu.forwardDungeon.weapon.NumWeapon;
import com.huagyuu.forwardDungeon.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @Author: HuaGyuu
 * @CreateTime: 2025-12-25
 * @Version: 1.0
 */
public class ForwardDungeon {
    private static Scanner scanner = new Scanner(System.in);
    private static List<HeroMine> heroMines;
    private static MineMachine machine;
    private static Hero hero;
    public static Dungeon dungeon;

    public static void main(String[] args) {
        loadHero();
        loadMineMachine();
        dungeon = ForwardDungeonDao.loadDungeon();
        showMainMenu();
    }

    private static void loadHero() {
        hero = ForwardDungeonDao.loadHero();
        heroMines = ForwardDungeonDao.loadHeroMines();
        Warehouse warehouse = ForwardDungeonDao.loadWarehouse();
        warehouse.setHeroMines(heroMines);
        hero.setWarehouse(warehouse);
    }

    private static void loadMineMachine() {
        machine = ForwardDungeonDao.loadMineMachine();
        machine.setHeroMines(heroMines);
        Thread thread = new Thread(machine, "machine");
        thread.setDaemon(true);
        thread.start();
    }

    private static void showMainMenu() {
        while (true) {
            showGameName();
            System.out.println("1. 资源获取");
            System.out.println("2. 角色属性");
            System.out.println("3. 商店");
            System.out.println("4. 铁匠铺");
            System.out.println("5. 探索地牢");
            System.out.println("6. 说明");
            System.out.println("-1 保存并退出");

            switch (inputInt()) {
                case 0: break;
                case 1:
                    showResourceMainMenu();
                    break;
                case 2:
                    showHeroInfoMenu();
                    break;
                case 3:
                    showShopMenu();
                    break;
                case 4:
                    showBlacksmithMenu();
                    break;
                case 5:
                    showExploreDungeonMenu();
                    break;
                case 6:
                    showGameGuide();
                    break;
                case -1:
                    saveGame();
                    return;
                default:
                    errorPrint("数字错误");
            }
        }

    }

    private static void saveGame() {
        Warehouse warehouse = hero.getWarehouse();
        ForwardDungeonDao.saveHeroMines(heroMines);
        ForwardDungeonDao.saveHeroInfo(hero);
        ForwardDungeonDao.saveNumWeapons(warehouse.getNumWeapons());
        ForwardDungeonDao.saveNumArmors(warehouse.getNumArmors());
        ForwardDungeonDao.saveMineMachine(machine);
        ForwardDungeonDao.saveDungeon(dungeon);
    }

    private static void showExploreDungeonMenu() {
        while (true) {
            showPartName("探索地牢");

            System.out.println("1. 闯关地牢");
            System.out.println("2. 幸运地牢");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    showBreakDungeonMenu();
                    break;
                case 2:
                    showLuckyDungeonMenu();
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void showLuckyDungeonMenu() {
        while (true) {
            showPartName("幸运地牢");
            System.out.println("1. 说明");
            System.out.println("2. 闯关");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    showLuckyDungeonInfoMenu();
                    break;
                case 2:
                    luckyDungeon();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void luckyDungeon() {
        showPartName("幸运地牢");
        double money = hero.getMoney();
        System.out.println("已拥有货币：" + money);
        System.out.println("输入要携带的货币数量：");
        showExitMenu();

        int input = inputInt();

        if (input == -1) return;

        if (input < 0 || input > money) {
            System.out.println("货币数量错误");
            return;
        }

        Random random = new Random();
        int i = random.nextInt(100);

        try {
            System.out.println("正在闯荡.....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (i <= 50) {
            System.out.println("获胜，获得货币：" + input);
            hero.setMoney(money + input);
        } else {
            System.out.println("失败，失去货币：" + input);
            hero.setMoney(money - input);
        }
    }

    private static void showLuckyDungeonInfoMenu() {
        System.out.println("选择一定数量货币进入，有 50% 概率获胜\n获胜将获得同等数量的货币\n失败将失去同等数量的货币");
    }

    private static void showBreakDungeonMenu() {
        while (true) {
            showPartName("闯关地牢");
            System.out.println("已过地牢层数：" + Dungeon.layer);
            System.out.println("下一层奖励：" + Dungeon.prize);
            System.out.println("1. 说明");
            System.out.println("2. 下一层");
            System.out.println("3. 再次挑战本层");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    showBreakDungeonInfoMenu();
                    break;
                case 2:
                    breakNextDungeon();
                    break;
                case 3:
                    breakNowDungeon();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void breakNowDungeon() {
        if (Dungeon.layer == 0) {
            errorPrint("0 层不得挑战");
            return;
        }
        DungeonEvent event = dungeon.getDungeonEvent();

        if (event.getType() == 1) {
            Monster monster = (Monster) event;
            breakMonsterDungeon(monster);
        } else {
            EquipmentBox equipmentBox = (EquipmentBox) event;
            breakEquipmentBoxDungeon(equipmentBox);
        }
    }

    private static void breakNextDungeon() {
        dungeon = new Dungeon();
        DungeonEvent event = dungeon.getDungeonEvent();

        if (event.getType() == 1) {
            Monster monster = (Monster) event;
            System.out.println("此为怪物房");
            System.out.println("血量：" + monster.getHp());
            System.out.println("伤害：" + monster.getDamage());
            System.out.println("防御：" + monster.getDefence());
            System.out.println("是否进入");
            System.out.println("1. 进入");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    breakMonsterDungeon(monster);
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        } else {
            EquipmentBox equipmentBox = (EquipmentBox) event;
            System.out.println("此为宝物房");
            System.out.println("是否进入");
            System.out.println("1. 进入");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    breakEquipmentBoxDungeon(equipmentBox);
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
            System.out.println("宝物为：" + equipmentBox.getEquipment().getName());
        }
    }

    private static void breakEquipmentBoxDungeon(EquipmentBox equipmentBox) {
        try {
            System.out.println("正在闯荡....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Equipment equipment = equipmentBox.getEquipment();
        if (equipment.getEquipmentType() == 1) {
            hero.getWarehouse().increaseNumWeapon((Weapon) equipment);
        } else {
            hero.getWarehouse().increaseNumArmor((Armor) equipment);
        }

        double money = hero.getMoney() + Dungeon.prize;
        hero.setMoney(money);
        System.out.println("已获取宝物：" + equipment.getName() + "，获得货币：" + Dungeon.prize);
    }

    private static void breakMonsterDungeon(Monster monster) {
        try {
            System.out.println("正在闯荡....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (1.0 * hero.getDefense() * hero.getHp() / monster.getDamage() <
                1.0 * monster.getDefence() * monster.getHp() / hero.getAttack()) {
            System.out.println("落败，实力不足");
            return;
        }

        double money = hero.getMoney() + Dungeon.prize;
        hero.setMoney(money);
        successPrint("获胜，获得货币：" + Dungeon.prize);
    }

    private static void showBreakDungeonInfoMenu() {
        System.out.println("下一层随机出现怪物房或宝物房，怪物越来越强\n，奖励越来越大");
    }

    private static void showBlacksmithMenu() {
        while (true) {
            showPartName("铁匠铺");

            System.out.println("1. 武器升级");
            System.out.println("2. 防具升级");
            System.out.println("3. 武器升星");
            System.out.println("4. 防具升星");
            System.out.println("5. 锻造装备");

            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    showWeaponLevelUpMenu();
                    break;
                case 2:
                    showArmorLevelUpMenu();
                    break;
                case 3:
                    showWeaponStarUpMenu();
                    break;
                case 4:
                    showArmorStarUpMenu();
                    break;
                case 5:
                    showBuildEquipmentMenu();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void showBuildEquipmentMenu() {
        Blacksmith blacksmith = Blacksmith.loadBlacksmith();
        while (true) {
            showPartName("锻造装备");
            int i = 1;
            List<BuildEquipment> buildEquipments = blacksmith.getBuildEquipments();
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s", "编号", "名称", "数值",  "等级", "星级"));
            for (BuildEquipment buildEquipment : buildEquipments) {
                i = showBuildEquipmentInfo(buildEquipment, i);
            }

            showExitMenu();
            int inputInt = inputInt();

            if (inputInt == -1) {
                return;
            }

            buildEquipment(inputInt, buildEquipments);
        }
    }

    private static void buildEquipment(int inputInt, List<BuildEquipment> buildEquipments) {
        int size = buildEquipments.size();
        if (inputInt <= 0 || inputInt > size) {
            errorPrint("数字错误");
            return;
        }

        int newIndex = inputInt - 1;

        BuildEquipment buildEquipment = buildEquipments.get(newIndex);
        Warehouse warehouse = hero.getWarehouse();
        double money = hero.getMoney();
        long price = buildEquipment.getPrice();
        List<HeroMine> buildMIneList = buildEquipment.getMineList();
        int flag = 0;

        for (HeroMine buildMine : buildMIneList) {
            if (warehouse.heroMinesContainsHeroMine(buildMine)) {
                flag++;
            }
        }

        if (flag != buildMIneList.size()) {
            errorPrint("所需矿物不足");
            return;
        }

        if (money < price) {
            errorPrint("所需货币不足");
            return;
        }

        hero.setMoney(money -  price);
        for (HeroMine heroMine : buildMIneList) {
            warehouse.decreaseHeroMine(heroMine);
        }

        Equipment equipment = buildEquipment.getEquipment();
        if (equipment.getEquipmentType() == 1) {
            Weapon weapon = (Weapon) equipment;
            warehouse.increaseNumWeapon(weapon);
        } else  {
            Armor armor = (Armor) equipment;
            warehouse.increaseNumArmor(armor);
        }

        successPrint("锻造成功");
    }

    private static int showBuildEquipmentInfo(BuildEquipment buildEquipment, int i) {
        Equipment equipment = buildEquipment.getEquipment();
        if (equipment.getEquipmentType() == 1) {
            Weapon weapon = (Weapon) equipment;
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s", i++, weapon.getName(), weapon.getDamage(),  weapon.getLevel(), weapon.getStar()));
        } else {
            Armor armor = (Armor) equipment;
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s", i++, armor.getName(), armor.getDefense(),  armor.getLevel(), armor.getStar()));
        }

        for (HeroMine heroMine : buildEquipment.getMineList()) {
            System.out.println("        需要矿物： " + heroMine.getMine().getName() + "  需要数量" + heroMine.getTotal() + heroMine.getHeavyUnit().getUnitName());
        }

        System.out.println("        需要货币：" + buildEquipment.getPrice());

        return i;
    }

    private static void showArmorStarUpMenu() {
        while (true) {
            List<NumArmor> numArmors = hero.getWarehouse().getNumArmors();
            List<Armor> armors = hero.getArmors();
            int id = showAllNumArmorsStarUp(numArmors);

            for (Armor armor : armors) {
                System.out.println(String.format("%-5s%-12s%-12d%-7d%-7d%-4d", ++id + "(装)", armor.getName(), armor.getDefense(), armor.getLevel(), armor.getStar(), hero.getSameArmorNum(armor)));
            }

            showExitMenu();

            int index = inputInt();
            if (index == -1) {
                return;
            }

            armorStarUp(index, numArmors);
        }
    }

    private static void armorStarUp(int index, List<NumArmor> numArmors) {
        int size = numArmors.size();
        if (index <= 0 || index > (size + 5)) {
            errorPrint("数字错误");
            return;
        }

        int newIndex = index - 1;

        if (newIndex >= size) {
            Armor armor = hero.getArmorByTypeOrIndex(newIndex - size + 1);
            if (hero.getSameArmorNum(armor) < 9) {
                errorPrint("数量不足（10 个同级同星可升星）");
                return;
            }

            newIndex = hero.getWarehouse().getSameArmorIndex(armor);
            armor.starUp();
        } else {
            NumArmor numArmor = numArmors.get(newIndex);
            if (numArmor.getNum() < 10) {
                errorPrint("数量不足（10 个同级同星可升星）");
                return;
            }

            numArmor.getArmor().starUp();
        }

        hero.getWarehouse().decreaseNumArmor(newIndex, 9);
        hero.flushArmorInfo();

        successPrint("升星成功");
    }

    private static void showWeaponStarUpMenu() {
        while (true) {
            List<NumWeapon> numWeapons = hero.getWarehouse().getNumWeapons();
            Weapon weapon = hero.getWeapon();
            int id = showAllNumWeaponsStarUp(numWeapons);

            System.out.println(String.format("%-5s%-12s%-12d%-7d%-7d%-4d", (id + 1) + "(装)", weapon.getName(), weapon.getDamage(), weapon.getLevel(), weapon.getStar(), hero.getSameWeaponNum()));
            showExitMenu();

            int index = inputInt();
            if (index == -1) {
                return;
            }

            weaponStarUp(index, numWeapons);
        }
    }

    private static void weaponStarUp(int index, List<NumWeapon> numWeapons) {
        int size = numWeapons.size();
        if (index <= 0 || index > (size + 1)) {
            errorPrint("数字错误");
            return;
        }

        int newIndex = index - 1;

        if (newIndex == size) {
            if (hero.getSameWeaponNum() < 9) {
                errorPrint("数量不足（10 个同级同星可升星）");
                return;
            }

            Weapon heroWeapon = hero.getWeapon();
            newIndex = hero.getWarehouse().getSameWeaponIndex(heroWeapon);
            heroWeapon.starUp();
        } else {
            NumWeapon numWeapon = numWeapons.get(newIndex);
            if (numWeapon.getNum() < 10) {
                errorPrint("数量不足（10 个同级同星可升星）");
                return;
            }

            numWeapon.getWeapon().starUp();
        }

        hero.getWarehouse().decreaseNumWeapon(newIndex, 9);
        hero.flushWeaponInfo();

        successPrint("升星成功");
    }

    private static void showArmorLevelUpMenu() {
        while (true) {
            List<NumArmor> numArmors = hero.getWarehouse().getNumArmors();
            int id = showAllNumArmorsLevelUp(numArmors);
            List<Armor> armors = hero.getArmors();

            for (Armor armor : armors) {
                System.out.println(String.format("%-5s%-12s%-12d%-7d%-7d%-7d", (++id) + "(装)", armor.getName(), armor.getDefense(), armor.getLevel(), armor.getStar(), armor.getLevelUpValue()));
            }

            showExitMenu();

            int index = inputInt();
            if (index == -1) {
                return;
            }

            armorLevelUp(index, numArmors);
        }
    }

    private static void armorLevelUp(int index, List<NumArmor> numArmors) {
        int size = numArmors.size();
        if (index <= 0 || index > (size + 5)) {
            errorPrint("数字错误");
            return;
        }

        int newIndex = index - 1;
        Armor armor = null;
        int price = 0;
        double money = hero.getMoney();

        if (newIndex >= size) {
            armor = hero.getArmorByTypeOrIndex(newIndex - size + 1);
            price = (int) (armor.getValue() * 1.2);

            if (money < price) {
                errorPrint("货币不足");
                return;
            }

            armor.levelUp();
        } else {
            armor = numArmors.get(newIndex).getArmor();
            armor = armor.copy(armor.getClass());
            price = (int) (armor.getValue() * 1.2);

            if (money < price) {
                errorPrint("货币不足");
                return;
            }

            Warehouse warehouse = hero.getWarehouse();
            warehouse.decreaseNumArmor(newIndex);

            armor.levelUp();
            warehouse.increaseNumArmor(armor);
        }

        hero.flushArmorInfo();
        hero.setMoney(money - price);
        hero.flushWeaponInfo();

        successPrint("升级成功");
    }

    private static void showWeaponLevelUpMenu() {
        while (true) {
            List<NumWeapon> numWeapons = hero.getWarehouse().getNumWeapons();
            Weapon weapon = hero.getWeapon();
            int id = showAllNumWeaponsLevelUp(numWeapons);

            System.out.println(String.format("%-5s%-12s%-12d%-7d%-7d%-7d", (id + 1) + "(装)", weapon.getName(), weapon.getDamage(), weapon.getLevel(), weapon.getStar(), weapon.getLevelUpValue()));
            showExitMenu();

            int index = inputInt();
            if (index == -1) {
                return;
            }

            weaponLevelUp(index, numWeapons);
        }

    }

    private static void weaponLevelUp(int index, List<NumWeapon> numWeapons) {
        int size = numWeapons.size();
        if (index <= 0 || index > (size + 1)) {
            errorPrint("数字错误");
            return;
        }

        int newIndex = index - 1;
        Weapon weapon = null;
        int price = 0;
        double money = hero.getMoney();

        if (newIndex == size) {
            weapon = hero.getWeapon();
            price = (int) (weapon.getValue() * 1.2);

            if (money < price) {
                errorPrint("货币不足");
                return;
            }

            weapon.levelUp();
        } else {
            weapon = numWeapons.get(newIndex).getWeapon().copy();
            price = (int) (weapon.getValue() * 1.2);

            if (money < price) {
                errorPrint("货币不足");
                return;
            }

            Warehouse warehouse = hero.getWarehouse();
            warehouse.decreaseNumWeapon(newIndex);

            weapon.levelUp();
            warehouse.increaseNumWeapon(weapon);
        }

        hero.setMoney(money - price);
        hero.flushWeaponInfo();

        successPrint("升级成功");
    }

    private static void showHeroInfoMenu() {
        while (true) {
            showPartName("角色属性");;

            System.out.println("货币：" + hero.getMoney());
            System.out.println("血量：" + hero.getHp());
            System.out.println("攻击：" + hero.getAttack());
            System.out.println("防御：" + hero.getDefense());
            System.out.println(String.format("类型：%-12s%-12s%-6s%-6s", "名称", "数值", "等级", "星级"));

            Weapon weapon = hero.getWeapon();
            if (weapon != null)
                System.out.println(String.format("武器：%-12s%-12d%-6d%-6d", weapon.getName(), weapon.getDamage(), weapon.getLevel(), weapon.getStar()));

            List<Armor> armors = hero.getArmors();
            for (Armor armor : armors) {
                System.out.println(String.format("头盔：%-12s%-12d%-6d%-6d", armor.getName(), armor.getDefense(),  armor.getLevel(), armor.getStar()));
            }

            System.out.println();
            System.out.println("1. 角色升级");
            System.out.println("2. 更改武器");
            System.out.println("3. 更改防具");
            showExitMenu();

            switch (inputInt()) {
                case  0: break;
                case 1:
                    showHeroLevelUpMenu();
                    break;
                case 2:
                    showChangeWeaponMenu();
                    break;
                case 3:
                    showChangeArmorMenu();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void showChangeArmorMenu() {
        while (true) {
            showPartName("更改防具");
            List<NumArmor> numArmors = hero.getWarehouse().getNumArmors();
            showAllNumArmors(numArmors, true, null);

            System.out.println("请输入想要改为的防具编号");
            showExitMenu();

            int id = inputInt();

            if (id == -1) {
                return;
            }

            changeArmor(id, numArmors);
        }
    }

    private static void changeArmor(int id, List<NumArmor> numArmors) {
        if (id <= 0 || id > numArmors.size()) {
            errorPrint("编号不存在");
            return;
        }

        int newId = id -1;

        NumArmor numArmor = numArmors.get(newId);
        Armor newArmor = numArmor.getArmor();
        Armor oldArmor = hero.getArmor(newArmor.getType());
        Warehouse warehouse = hero.getWarehouse();

        if (!warehouse.increaseNumArmor(oldArmor)) {
            errorPrint("原防具为空");
            return;
        }

        hero.setArmor(newArmor);

        if (!warehouse.decreaseNumArmor(newId)) {
            errorPrint("待该防具为空");
            return;
        }

        hero.flushArmorInfo();

        successPrint("防具更换成功");
    }

    private static void showChangeWeaponMenu() {
        while (true) {
            showPartName("更改武器");
            List<NumWeapon> numWeapons = hero.getWarehouse().getNumWeapons();
            showAllNumWeapons(numWeapons, true, null);

            System.out.println("请输入想要改为的武器编号");
            showExitMenu();

            int id = inputInt();

            if (id == -1) {
                return;
            }

            changeWeapon(id, numWeapons);
        }

    }

    private static void changeWeapon(int id, List<NumWeapon>  numWeapons) {
        if (id <= 0 || id > numWeapons.size()) {
            errorPrint("编号不存在");
            return;
        }

        int newId = id -1;

        NumWeapon numWeapon = numWeapons.get(newId);
        Weapon oldWeapon = hero.getWeapon();
        Warehouse warehouse = hero.getWarehouse();

        if (!warehouse.increaseNumWeapon(oldWeapon)) {
            errorPrint("原武器为空");
            return;
        }

        hero.setWeapon(numWeapon.getWeapon());

        if (!warehouse.decreaseNumWeapon(newId)) {
            errorPrint("待该武器为空");
            return;
        }

        hero.flushWeaponInfo();

        successPrint("武器更换成功");
    }

    private static void showHeroLevelUpMenu() {
        while (true) {
            showPartName("角色升级");
            System.out.println("升级需要货币：" + hero.getUpMoney());
            System.out.println("1. 升级");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1:
                    heroLevelUp();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }

    }

    private static void heroLevelUp() {
        double upMoney = hero.getUpMoney();
        double money = hero.getMoney();

        if (money < upMoney) {
            errorPrint("货币不足，无法升级");
            return;
        }

        hero.setMoney(money - upMoney);
        hero.levelUp();
        System.out.println("升级成功");
    }

    private static void showShopMenu() {
        while (true) {
            showPartName("商店");
            System.out.println("1. 购买");
            System.out.println("2. 出售");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1: showBuyMenu();
                    break;
                case 2: showSaleMenu();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void showSaleMenu() {
        while (true) {
            showPartName("商店出售");
            System.out.println("1. 出售矿石");
            System.out.println("2. 出售武器");
            System.out.println("3. 出售防具");

            showExitMenu();
            switch (inputInt()) {
                case 0: break;
                case 1: showSaleMineMenu();
                    break;
                case 2: showSaleWeaponMenu();
                    break;
                case 3: showSaleArmorMenu();
                    break;
                case -1: return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void showSaleArmorMenu() {
        while (true) {
            showPartName("出售防具");
            List<NumArmor> numArmors = hero.getWarehouse().getNumArmors();

            if (numArmors.size() == 0 || numArmors == null) {
                errorPrint("暂时没有武器");
                return;
            }

            showAllNumArmors(numArmors, true, null);

            showExitMenu();
            System.out.println("输入要出售的防具编号");
            int id = inputInt();

            if (id == -1) {
                return;
            }

            saleArmorByNum(id, numArmors);
        }
    }

    private static void showAllNumArmors(List<NumArmor> numArmors, boolean hasNum, List<Armor> armorList) {
        if (hasNum) {
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s%-4s", "编号", "名称", "防御",  "等级", "星级","价格", "数量"));

            for (int i = 0; i < numArmors.size(); i++) {
                NumArmor numArmor = numArmors.get(i);
                Armor armor = numArmor.getArmor();
                String line = String.format(
                        "%-5d%-12s%-12d%-7d%-7d%-7d%-4d",
                        (i + 1),
                        armor.getName(),
                        armor.getDefense(),
                        armor.getLevel(),
                        armor.getStar(),
                        armor.getValue(),
                        numArmor.getNum()
                );
                System.out.println(line);
            }
        } else {
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "防御",  "等级", "星级","价格"));

            for (int i = 0; i < armorList.size(); i++) {
                Armor armor = armorList.get(i);
                String line = String.format(
                        "%-5d%-12s%-12d%-7d%-7d%-7d",
                        (i + 1),
                        armor.getName(),
                        armor.getDefense(),
                        armor.getLevel(),
                        armor.getStar(),
                        (int)(armor.getValue() * 1.2)
                );
                System.out.println(line);
            }
        }

    }

    private static int showAllNumArmorsLevelUp(List<NumArmor> numArmors) {
        System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "防御",  "等级", "星级","价格"));

        int i = 0;
        for (; i < numArmors.size(); i++) {
            NumArmor numArmor = numArmors.get(i);
            Armor armor = numArmor.getArmor();
            String line = String.format(
                    "%-5d%-12s%-12d%-7d%-7d%-7d",
                    (i + 1),
                    armor.getName(),
                    armor.getDefense(),
                    armor.getLevel(),
                    armor.getStar(),
                    (int)(armor.getValue() * 1.2)
            );
            System.out.println(line);
        }

        return i;
    }

    private static void saleArmorByNum(int id, List<NumArmor> numArmors) {
        if (id <= 0 || id > numArmors.size()) {
            errorPrint("编号不存在");
            return;
        }

        int newId = id - 1;

        System.out.println("输入要出售的数量(整数)");
        int num = inputInt();

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        NumArmor numArmor = numArmors.get(newId);
        int end = numArmor.getNum() - num;

        if (end < 0) {
            errorPrint("数量不足，出售失败");
            return;
        }

        double saleMoney = num * numArmor.getArmor().getValue();

        hero.setMoney(hero.getMoney() + num * numArmor.getArmor().getValue());
        successPrint("出售成功");
        System.out.println("共获得货币：" + saleMoney);
        hero.getWarehouse().decreaseNumArmor(newId, num);
        System.out.println();
    }

    private static void showSaleWeaponMenu() {
        while (true) {
            showPartName("出售武器");
            List<NumWeapon> numWeapons = hero.getWarehouse().getNumWeapons();

            if (numWeapons.size() == 0 || numWeapons == null) {
                errorPrint("暂时没有武器");
                return;
            }

            showAllNumWeapons(numWeapons, true, null);

            showExitMenu();
            System.out.println("输入要出售的武器编号");
            int id = inputInt();

            if (id == -1) {
                return;
            }

            saleWeaponByNum(id, numWeapons);
        }
    }

    private static void showAllNumWeapons(List<NumWeapon> numWeapons, boolean isSale, List<Weapon> weaponList) {
        if (isSale) {
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s%-4s", "编号", "名称", "攻击", "等级", "星级", "价格", "数量"));

            for (int i = 0; i < numWeapons.size(); i++) {
                NumWeapon numWeapon = numWeapons.get(i);
                Weapon weapon = numWeapon.getWeapon();
                String line = String.format(
                        "%-5d%-12s%-12d%-7d%-7d%-7d%-4d",
                        (i + 1),
                        weapon.getName(),
                        weapon.getDamage(),
                        weapon.getLevel(),
                        weapon.getStar(),
                        weapon.getValue(),
                        numWeapon.getNum()
                );
                System.out.println(line);
            }
        } else {
            System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "攻击", "等级", "星级", "价格"));

            for (int i = 0; i < weaponList.size(); i++) {
                Weapon weapon = weaponList.get(i);
                String line = String.format(
                        "%-5d%-12s%-12d%-7d%-7d%-7d",
                        (i + 1),
                        weapon.getName(),
                        weapon.getDamage(),
                        weapon.getLevel(),
                        weapon.getStar(),
                        (int)(weapon.getValue() * 1.2)
                );
                System.out.println(line);
            }
        }

    }

    private static int showAllNumWeaponsLevelUp(List<NumWeapon> numWeapons) {
        System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "攻击", "等级", "星级", "价格"));

        int i = 0;
        for (; i < numWeapons.size(); i++) {
            NumWeapon numWeapon = numWeapons.get(i);
            Weapon weapon = numWeapon.getWeapon();
            String line = String.format(
                    "%-5d%-12s%-12d%-7d%-7d%-7d",
                    (i + 1),
                    weapon.getName(),
                    weapon.getDamage(),
                    weapon.getLevel(),
                    weapon.getStar(),
                    (int)(weapon.getValue() * 1.2)
            );
            System.out.println(line);
        }

        return i;
    }

    private static int showAllNumWeaponsStarUp(List<NumWeapon> numWeapons) {
        System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "攻击", "等级", "星级", "数量"));

        int i = 0;
        for (; i < numWeapons.size(); i++) {
            NumWeapon numWeapon = numWeapons.get(i);
            Weapon weapon = numWeapon.getWeapon();
            String line = String.format(
                    "%-5d%-12s%-12d%-7d%-7d%-7d",
                    (i + 1),
                    weapon.getName(),
                    weapon.getDamage(),
                    weapon.getLevel(),
                    weapon.getStar(),
                    numWeapon.getNum()
            );
            System.out.println(line);
        }

        return i;
    }

    private static int showAllNumArmorsStarUp(List<NumArmor> numArmors) {
        System.out.println(String.format("%-4s%-12s%-12s%-6s%-6s%-6s", "编号", "名称", "防御", "等级", "星级", "数量"));

        int i = 0;
        for (; i < numArmors.size(); i++) {
            NumArmor numArmor = numArmors.get(i);
            Armor armor = numArmor.getArmor();
            String line = String.format(
                    "%-5d%-12s%-12d%-7d%-7d%-7d",
                    (i + 1),
                    armor.getName(),
                    armor.getDefense(),
                    armor.getLevel(),
                    armor.getStar(),
                    numArmor.getNum()
            );
            System.out.println(line);
        }

        return i;
    }


    private static void saleWeaponByNum(int id, List<NumWeapon> numWeapons) {
        if (id <= 0 || id > numWeapons.size()) {
            errorPrint("编号不存在");
            return;
        }

        int newId = id - 1;

        System.out.println("输入要出售的数量(整数)");
        int num = inputInt();

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        NumWeapon numWeapon = numWeapons.get(newId);
        int end = numWeapon.getNum() - num;

        if (end < 0) {
            errorPrint("数量不足，出售失败");
            return;
        }


        double saleMoney = num * numWeapon.getWeapon().getValue();

        hero.setMoney(hero.getMoney() + saleMoney);
        successPrint("出售成功");
        System.out.println("共获得货币：" + saleMoney);
        hero.getWarehouse().decreaseNumWeapon(newId, num);
        System.out.println();
    }

    private static void showSaleMineMenu() {
        while (true) {
            List<HeroMine> mines = hero.getWarehouse().getHeroMines();

            showAllHeroMines(mines);

            showExitMenu();
            System.out.println("输入要出售的矿石编号");
            int id = inputInt();

            if (id == -1) {
                return;
            }

            saleMineByNum(id, mines);
        }
    }

    private static void showAllHeroMines(List<HeroMine> mines) {
        System.out.println(String.format("%-4s%-12s%-6s%-4s", "编号", "名称","价格", "数量"));

        for (int i = 0; i < mines.size(); i++) {
            HeroMine heroMine = mines.get(i);
            Mine mine = heroMine.getMine();

            String line = String.format(
                    "%-5d%-12s%-7d%-6.4f",
                    (i + 1),
                    mine.getName(),
                    mine.getValue(),
                    heroMine.getTotal()
            );

            line += heroMine.getHeavyUnit().getUnitName();
            System.out.println(line);
        }
    }

    private static void saleMineByNum(int id, List<HeroMine> heroMines) {
        if (id <= 0 || id > heroMines.size()) {
            errorPrint("编号不存在");
            return;
        }

        System.out.println("输入要出售的数量(整数)和单位，用空格分隔，如 \"10 g\"");
        String s = scanner.nextLine();
        String[] strings = s.split(" ");
        int num = Integer.parseInt(strings[0]);
        String unitName = strings[1];

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        HeroMine heroMine = heroMines.get(id - 1);
        double heroGramNum = machine.totalToGram(heroMine);
        MineUnit mineUnit = MineUnit.getMineUnitByName(unitName);
        long finalNum = MineUnit.unitsToGrams(mineUnit) * num;


        double end = heroGramNum -  finalNum;

        if (end < 0) {
            errorPrint("数量不足，出售失败");
            return;
        }

        heroMine.setTotal(end);
        heroMine.setHeavyUnit(MineUnit.GRAM);
        int saleMoney = num * heroMine.getMine().getValue();
        hero.setMoney(hero.getMoney() + saleMoney);
        successPrint("出售成功");
        System.out.println("共获得货币：" + saleMoney);
        System.out.println();
    }

    private static void showBuyMenu() {
        while (true) {
            showPartName("商店购买");
            Shop shop = Shop.loadShop();

            System.out.println("1. 购买矿石");
            System.out.println("2. 购买武器");
            System.out.println("3. 购买防具");
            showExitMenu();

            switch (inputInt()) {
                case 0:
                    break;
                case 1:
                    showBuyMineMenu(shop);
                    break;
                case 2:
                    showBuyWeaponMenu(shop);
                    break;
                case 3:
                    showBuyArmorMenu(shop);
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }

        }
    }

    private static void showBuyArmorMenu(Shop shop) {
        while (true) {
            List<Armor> armorList = shop.getArmorList();
            showAllNumArmors(null, false, armorList);

            showExitMenu();
            int choice = inputInt();

            if (choice == -1) {
                return;
            }

            buyArmorById(choice, armorList);
        }
    }

    private static void buyArmorById(int choice, List<Armor> armorList) {
        if (choice <= 0 || choice > armorList.size()) {
            errorPrint("编号错误");
            return;
        }

        int newId = choice - 1;

        System.out.println("输入要购买的数量（整数）");
        int num = inputInt();

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        Armor armor = armorList.get(newId);
        double money = hero.getMoney();
        int price = (int) (armor.getValue() * 1.2) * num;

        if (price > money) {
            errorPrint("货币不足");
            return;
        }

        hero.setMoney(money - price);
        Warehouse warehouse = hero.getWarehouse();
        warehouse.increaseNumArmor(armor, num);


        successPrint("购买成功");
    }

    private static void showBuyWeaponMenu(Shop shop) {
        while (true) {
            List<Weapon> weaponList = shop.getWeaponList();
            showAllNumWeapons(null, false, weaponList);

            showExitMenu();
            int choice = inputInt();

            if (choice == -1) {
                return;
            }

            buyWeaponById(choice, weaponList);
        }
    }

    private static void buyWeaponById(int choice, List<Weapon> weaponList) {
        if (choice <= 0 || choice > weaponList.size()) {
            errorPrint("编号错误");
            return;
        }

        int newId = choice - 1;

        System.out.println("输入要购买的数量（整数）");
        int num = inputInt();

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        Weapon weapon = weaponList.get(newId);
        double money = hero.getMoney();
        int price = (int)(weapon.getValue() * 1.2) * num;

        if (price > money) {
            errorPrint("货币不足");
            return;
        }

        hero.setMoney(money - price);
        Warehouse warehouse = hero.getWarehouse();

        warehouse.increaseNumWeapon(weapon, num);

        successPrint("购买成功");
    }

    private static void showBuyMineMenu(Shop shop) {
        while (true) {
            System.out.println(String.format("%-5s%-12s%-6s", "编号", "名称", "单价"));
            List<Mine> mineList = shop.getMineList();
            int price = 0;

            for (int i = 0; i < mineList.size(); i++) {
                Mine mine = mineList.get(i);
                price = (int) (mine.getValue() * 1.2);
                System.out.println(String.format("%-5d%-12s%-6d", i + 1, mine.getName(), price));
            }

            showExitMenu();
            int choice = inputInt();

            if (choice == -1) {
                return;
            }

            buyMineById(choice, mineList);
        }

    }

    private static void buyMineById(int choice, List<Mine> mineList) {
        if (choice <= 0 || choice > mineList.size()) {
            errorPrint("编号错误");
            return;
        }

        int newId = choice - 1;

        System.out.println("输入要购买的数量(整数)和单位，用空格分隔，如 \\\"10 g\\\"\"");
        String line = scanner.nextLine();
        String[] strings = line.split(" ");
        int num = Integer.parseInt(strings[0]);
        String unitName = strings[1];

        if (num <= 0) {
            errorPrint("数量无效");
            return;
        }

        MineUnit mineUnit = MineUnit.getMineUnitByName(unitName);
        long gramNum = (MineUnit.unitsToGrams(mineUnit) * num);

        Mine mine = mineList.get(newId);
        double money = hero.getMoney();
        long newPrice = gramNum * (int) (mine.getValue() * 1.2);

        if (newPrice > money) {
            errorPrint("货币不足");
            return;
        }

        hero.setMoney(money - newPrice);
        String name = mine.getName();
        for (HeroMine heroMine : heroMines) {
            if (name.equals(heroMine.getMine().getName())) {
                double total = machine.totalToGram(heroMine);
                heroMine.setTotal(total + gramNum);
                heroMine.setHeavyUnit(MineUnit.GRAM);
                gramNum = 0;
                break;
            }
        }

        if (gramNum != 0) {
            heroMines.add(new HeroMine(gramNum, mine));
        }

        successPrint("购买成功");
    }

    private static Shop loadShop() {
        Shop shop = new Shop();

        List<Mine> mineList = shop.getMineList();
        mineList.add(new Stone());
        mineList.add(new Iron());
        mineList.add(new Copper());
        mineList.add(new Silver());
        mineList.add(new Gold());

        List<Weapon> weaponList = shop.getWeaponList();
        weaponList.add(new Weapon(10, "草根之剑", 10));
        Weapon weapon = new Weapon(10, "草根之剑", 10);
        weapon.setLevel(2);
        weaponList.add(weapon);

        List<Armor> armorList = shop.getArmorList();
        armorList.add(new Helmet(10, "草根之帽", 10));

        Helmet helmet = new Helmet(10, "草根之帽", 10);
        helmet.setLevel(2);
        armorList.add(helmet);
        return shop;
    }

    private static void showResourceMainMenu() {
        while (true) {
            showPartName("资源获取");
            System.out.println("1. 资源产出");
            System.out.println("2. 开采机器");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1: showResourceReduceMenu();
                    break;
                case 2: showMineMachineMenu();
                case -1: return;
                default:
                    errorPrint("数字错误");
            }
        }

    }

    private static void showMineMachineMenu() {
        while (true) {
            showPartName("开采机器");
            System.out.println("1. 机器详情");
            System.out.println("2. 升级");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1: showMineMachineInfo();
                    break;
                case 2: levelUpMineMachine();
                    break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }
        }
    }

    private static void levelUpMineMachine() {
        if (machine.levelUp(hero, heroMines)) {
            successPrint("升级成功");
        } else {
            errorPrint("升级失败，货币不足");
        }
    }

    private static void showMineMachineInfo() {
        System.out.println("    等级：" + machine.getLevel());
        System.out.println("    速率：" + machine.getRate());
        System.out.println("    下一级所需货币：" + machine.getUpMoney());
    }

    private static void showResourceReduceMenu() {

        while (true) {
            showPartName("资源产出");

            for (HeroMine heroMine : heroMines) {
                System.out.println("资源:");
                System.out.println("    名称: " + heroMine.getMine().getName());
                System.out.println("    单价: " + heroMine.getMine().getValue());
                System.out.println("    总量: " + heroMine.getTotal() + heroMine.getHeavyUnit().getUnitName());
                System.out.println("    速率: " + heroMine.getRate() + heroMine.getRateUnit().getUnitName() + "/s");
            }

            System.out.println("1. 刷新");
            showExitMenu();

            switch (inputInt()) {
                case 0: break;
                case 1: break;
                case -1:
                    return;
                default:
                    errorPrint("数字错误");
            }

        }

    }

    private static void successPrint(String msg) {
        System.out.println();
        System.out.println("++++++++++++++++" + msg + "++++++++++++++++");
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void errorPrint(String msg) {
        System.out.println();
        System.out.println("----------------" + msg + "----------------");
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showGameName() {
        System.out.println("============前进地牢============");
    }

    private static void showPartName(String partName) {
        System.out.println("============" + partName + "============");
    }

    private static void showExitMenu() {
        System.out.println("-1 退出");
    }

    private static int inputInt() {
        int num = 0;
        try {
            num = scanner.nextInt();
        } catch (Exception e) {
            errorPrint("输入格式有误，请重试");
        } finally {
            scanner.skip(".*");
            scanner.nextLine();
        }
        return num;
    }

    private static void showGameGuide() {
        System.out.println("1. 矿石会在后台不断挖取，速度如详情所示");
        System.out.println("2. 升级开采机器能够加快矿物挖取速度");
        System.out.println("3. 每升30级开采机器能够解锁新的矿物挖取");
        System.out.println("4. 要挖取某一类型的矿物，必须先拥有任意数量的对应矿物（问就是特性）");
        System.out.println("5. 给角色升级能够提升血量");
        System.out.println("6. 给武器/护甲升级能够提升伤害/防御");
        System.out.println("7. 给武器/护甲升星也能够提升伤害/防御（高）");
        System.out.println("8. 十个相同武器能够升星");
        System.out.println("9. 相同武器：同名称，同等级，同星级");
    }


}
