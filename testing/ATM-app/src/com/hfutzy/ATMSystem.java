package com.hfutzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public class Account {
        private String cardId;    //卡号
        private String userName;  //用户名
        private String passWord;  //密码
        private double money;     //余额
        private double quotaMoney;//档次取现额度

        public Account() {
        }

        public Account(String cardId, String userName, String passWord, double quotaMoney) {
            this.cardId = cardId;
            this.userName = userName;
            this.passWord = passWord;
            this.quotaMoney = quotaMoney;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getQuotaMoney() {
            return quotaMoney;
        }

        public void setQuotaMoney(double quotaMoney) {
            this.quotaMoney = quotaMoney;
        }
    }

    public static void main(String[] args) {
        //定义集合，用于存储账户对象
        ArrayList<Account> accounts = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        while (true) {
            showMenu();
            String command = sc.next();
            switch (command) {
                case "0":
                    return;
                case "1":
                    //登录
                    login(accounts, sc);
                    break;
                case "2":
                    //注册（开户）
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("The command is error!");
                    break;
            }
        }
    }

    /**
     * 准备系统首页：登录和注册
     *
     */
    public static void showMenu() {
        System.out.println("=====欢迎进入首页=====");
        System.out.println("请按照提示操作：");
        System.out.println("-------------------");
        System.out.println("|1.登陆账户         |");
        System.out.println("|2.注册账户         |");
        System.out.println("-------------------");
        System.out.println("输入0结束操作并退出系统。");
        System.out.println("请输入您的指令：");
    }

    /**
     * 实现登录功能
     *
     * @param accounts
     * @param sc
     */
    public static void login(ArrayList<Account> accounts, Scanner sc) {
        //录入卡号
        while (true) {
            System.out.println("请您输入登陆的卡号：");
            String cardId = sc.next();
            //根据卡号查询账户对象
            Account acc = getAccountCardId(cardId, accounts);
            if (acc != null) {
                System.out.println("请您输入登陆密码：");
                while (true) {
                    //说明卡号存在，输入密码
                    String password = sc.next();
                    //判断密码是否正确
                    if (password.equals(acc.getPassWord())) {
                        //密码正确，登陆成功
                        System.out.println("登陆成功！");
                        System.out.println("欢迎" + acc.getUserName() + "先生/女士进入银行ATM系统！");
                        System.out.println("您的卡号是：" + acc.getCardId());
                        showUserCommand(sc, acc, accounts);
                        return;
                    } else {
                        System.out.println("密码错误，请再次输入：");
                    }
                }
            } else {
                System.out.println("您输入的卡号错误！（不存在该账户）");
            }
        }
    }

    /**
     * 用户注册（开户）功能
     *
     * @param accounts
     */
    public static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("======用户注册功能======");
        //键盘录入信息，姓名，密码，确认密码，
        System.out.println("请输入开户名称：");
        String name = sc.next();

        String password = "";
        while (true) {
            System.out.println("请输入开户密码：");
            password = sc.next();
            System.out.println("请您再次输入确认密码：");
            String okPassword = sc.next();
            //判断两次输入的密码是否一致
            if (password.equals(okPassword)) {
                break;
            } else {
                System.out.println("两次输入的密码不一致！");
            }
        }
        System.out.println("请您输入当次限额：");
        double quotaMoney = sc.nextDouble();

        //生成账户的卡号卡号8位且不与其他账户卡号重复
        String cardId = createCardId(accounts);

        //创建账户对象封装账户的信息
        Account acc = new Account(cardId, name, password, quotaMoney);

        //账户对象添加到集合
        accounts.add(acc);
        System.out.println("恭喜您！您已开户成功。");
        System.out.println("您的卡号是：" + acc.getCardId() + "。请您妥善保管！");
    }

    /**
     * 随机生成卡号-方法
     *
     * @param accounts
     * @return
     */
    public static String createCardId(ArrayList<Account> accounts) {
        //生成8位随机数字代表卡号
        while (true) {
            Random r = new Random();
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }

            //判断是否重复
            Account acc = getAccountCardId(cardId, accounts);
            if (acc == null) {
                //说明当前卡号没有重复
                return cardId;
            }
        }
    }

    /**
     * 判断卡号是否重复-方法
     *
     * @param cardId
     * @param accounts
     * @return
     */
    public static Account getAccountCardId(String cardId, ArrayList<Account> accounts) {
        //根据卡号查询账户对象
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;
    }

    /**
     * 用户操作页面
     */
    public static void showUserCommand(Scanner sc, Account acc, ArrayList<Account> accounts) {
        while (true) {
            System.out.println("==========用户操作界面==========");
            System.out.println("     1.查询账户    2. 存款");
            System.out.println("     3.修改密码    4. 取款");
            System.out.println("     5.退出系统    6. 转账");
            System.out.println("     7.注销账户");
            System.out.println("请输入您的指令：");
            String command = sc.next();
            switch (command) {
                case "1":
                    //查询账户
                    showAccount(acc);
                    break;
                case "2":
                    //存款
                    depositMoney(acc, sc);
                    break;
                case "3":
                    //修改密码
                    updatePassword(acc, sc);
                    return;
                case "4":
                    //取款
                    drawMoney(acc,sc);
                    break;
                case "5":
                    //退出系统
                    System.out.println("已退出！感谢使用。");
                    return;
                case "6":
                    //转账
                    transferMoney(accounts, acc, sc);
                    break;
                case "7":
                    //注销账户
                    //当前集合删除当前对象
                    accounts.remove(acc);
                    System.out.println("注销成功！");
                   return;
                default:
                    System.out.println("The command is error!");
                    break;
            }
        }
    }

    /**
     * 查询账户信息
     * @param acc
     */
    public static void showAccount(Account acc) {
        System.out.println("欢迎：" + acc.getUserName() + "使用银行ATM系统！");
        System.out.println("======当前账户信息======");
        System.out.println("卡号：" + acc.getCardId());
        System.out.println("姓名：" + acc.getUserName());
        System.out.println("余额：" + acc.getMoney());
        System.out.println("当次限额：" + acc.getQuotaMoney());
    }

    /**
     * 存款功能
     * @param acc
     * @param sc
     */
    public static void depositMoney(Account acc, Scanner sc) {
        System.out.println("======存款操作======");
        System.out.println("请您输入存款金额：");
        double money = sc.nextDouble();
        //将账户对象中的money属性修改
        acc.setMoney(acc.getMoney() + money);
        System.out.println("存款成功！您的信息如下：");
        showAccount(acc);
    }

    /**
     * 取款功能
     * @param acc
     * @param
     */
    public static void drawMoney(Account acc, Scanner sc) {
        System.out.println("======取款操作======");
        if(acc.getMoney() > 100){
            while (true) {
                System.out.println("请输入取款金额：");
                double money = sc.nextDouble();

                if(money > acc.getQuotaMoney()){
                    System.out.println("您所取款金额超过当次限额，您最多可取款金额为：" + acc.getQuotaMoney());
                }else{
                    //判断余额
                    if(acc.getMoney() >=money){
                        acc.setMoney(acc.getMoney() - money);
                        System.out.println("恭喜您取款成功！");
                        System.out.println("您取款金额为：" + money);
                        System.out.println("您所剩余额为：" + acc.getMoney());
                        return;
                    }else{
                        System.out.println("余额不足！");
                    }
                }
            }
        }else{
            System.out.println("您的余额不足100元。无法取款！");
        }
    }

    /**
     *转账功能
     * @param accounts
     * @param acc
     * @param sc
     */
    public static void transferMoney(ArrayList<Account> accounts, Account acc, Scanner sc) {
        //判断系统中是否有两个及以上的账户
        if(accounts.size() < 2){
            System.out.println("系统中无其他账户，无法转账。");
            return;
        }
        //判断自己的账户中是否有余额
        if(acc.getMoney() == 0){
            System.out.println("您的帐户余额为：" + acc.getMoney() + "。无法转账。");
            return;
        }
        //开始转账
        while (true) {
            System.out.println("请您输入对方的卡号：");
            String cardId = sc.next();
            //判断对方账户是否存在，存在说明正确
            Account account = getAccountCardId(cardId, accounts);
            if(account != null){
                //判断账号是否和当前相同
                if(account.getCardId().equals(acc.getCardId())){
                    System.out.println("您不可以对自己进行转账");
                }else{
                    //进行姓氏确认
                    String name = "*" + account.getUserName().substring(1);
                    System.out.println("请您确认【" + name + "】的姓氏");
                    String preName = sc.next();
                    //判断
                    if(account.getUserName().startsWith(preName)){
                        //进行转账
                        System.out.println("请您输入所要转账的金额：");
                        double money = sc.nextDouble();
                        if(money > acc.getMoney()){
                            System.out.println("余额不足！");
                        }else{
                            acc.setMoney(acc.getMoney() - money);
                            account.setMoney(account.getMoney() + money);
                            System.out.println("转账成功！");
                            System.out.println("您已为卡号为：" + account.getCardId()
                                    + ",用户名为：*" + account.getUserName().substring(1)
                                    + " 转账" + money + "元。");
                            System.out.println("您的账户信息如下：");
                            showAccount(acc);
                            return;
                        }
                    }else{
                        System.out.println("抱歉，你输入的信息有误！");
                    }
                }

            }else{
                System.out.println("您输入的卡号有错误！");
            }
        }
    }

    /**
     * 修改密码
     * @param acc
     */
    public static void updatePassword(Account acc, Scanner sc) {
        System.out.println("======修改密码======");
        while (true) {
            System.out.println("请输入正确的密码：");
            String okPassword = sc.next();
            if(okPassword.equals(acc.getPassWord())){
                while (true) {
                    //输入新密码
                    System.out.println("请您输入新密码：");
                    String newPassWord = sc.next();
                    System.out.println("请您再次输入密码确认：");
                    String okNewPassWord = sc.next();
                    if(okNewPassWord.equals(newPassWord)){
                        acc.setPassWord(newPassWord);
                        return;
                    }else{
                        System.out.println("两次输入的密码不一致！");
                    }
                }
            }else{
                System.out.println("密码错误");
            }
        }

    }


}

