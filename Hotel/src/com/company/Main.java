package com.company;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Made in association with DWM Inc.
 */

public class Main extends Application {
    static private Scanner in = new Scanner(System.in);
    static private ArrayList<Human> guestsall = new ArrayList<>();// список всех гостей
    static private ArrayList<Room> list = new ArrayList<>();// лист для изменения параметров комнат
    static private ArrayList<Human> queue = new ArrayList<>();// очередь

    static private int findFreeRoom() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).freeBeds != 0) {
                return i;
            }
        }
        return -1;
    }

    static private void settle(int theRoomNum) {
        while (queue.size() > 0) {
            if (theRoomNum == -1) {
                break;
            }
            list.get(theRoomNum).guests.add(queue.get(0));
            queue.remove(queue.get(0));
            list.get(theRoomNum).freeBeds--;
            System.out.println("Guest settled in " + ++theRoomNum);
        }
    }

    static private String findBestForGroup(int Number) {
        String bestNum = null;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).square / Number < list.get(i + 1).square / Number && list.get(i + 1).freeBeds >= Number) {
                bestNum = String.valueOf(i + 1);
            }
        }
        return bestNum;
    }

    static private void Check() {
        for (int i = 0; i < guestsall.size(); i++) {
            if (guestsall.get(i).WantLive == guestsall.get(i).days) {
                unSettle(FindId(guestsall.get(i).WantLive));
            }
        }
    }

    static private String FindId(int WantLive) {
        for (Human human : guestsall) {
            if (human.WantLive == WantLive) {
                return human.id;
            }
        }
        return null;
    }

    static private void unSettle(String Id) {
        for (int i = 0; i < guestsall.size(); i++) {
            if (guestsall.get(i).id.equals(Id)) {
                list.get(i).freeBeds++;
                guestsall.get(i).Price = 1.3 * ((list.get(i).square * 2.5 * guestsall.get(i).days * list.get(i).Ver) / (list.get(i).Maximum + 1));
                list.get(i).guests.remove(guestsall.get(i));
                System.out.println("Guest " + guestsall.get(i).name + "(" + guestsall.get(i).id + ")" + " has settled out");
                System.out.println("Guest have to pay " + guestsall.get(i).Price + " $");
                guestsall.remove(guestsall.get(i));
            }
        }
    }

    public static void main(String[] args) {
        Application.launch();
        Room econom_1 = new Room(1, 1, 1, 20, 1.0);
        Room econom_2 = new Room(2, 2, 2, 40, 1.0);
        Room ordinary_1 = new Room(3, 2, 2, 60, 1.4);
        Room ordinary_2 = new Room(4, 2, 2, 80, 1.4);
        Room ordinary_3 = new Room(5, 3, 3, 90, 1.4);
        Room expensive_1 = new Room(6, 4, 4, 160, 1.8);
        Room expensive_2 = new Room(7, 4, 4, 200, 1.8);
        list.add(econom_1);
        list.add(econom_2);
        list.add(ordinary_1);
        list.add(ordinary_2);
        list.add(ordinary_3);
        list.add(expensive_1);
        list.add(expensive_2);
        System.out.println("Good day! Felco is greeting you! Good work day.");
        System.out.println("-to registrate person type 'settle' and then give name and personal id of person.");
        System.out.println("-to registrate group of people type 'settle_group' and then enter number of the group and their names and ids consistently.");
        System.out.println("-to settle person out type 'settle_out'.");
        System.out.println("-to watch info about rooms type 'give_info'.");
        System.out.println("-to watch all guests and their id type 'guests'.");
        System.out.println("-to skip day type 'next_day'.");
        System.out.println("-to end functionality type 'exit'.");
        boolean end = false;
        int days = 0;
        System.out.println("Day " + days);
        while (!end) {
            String cmd = in.nextLine();// непосредственно команда
            String[] input = cmd.split(" ");
            switch (input[0]) {
                case "settle":
                    System.out.print("enter name and id, how many days person wants to live\n>");
                    String nameIdDate = in.nextLine();
                    try {
                        String[] info = nameIdDate.split(" ");
                        queue.add(new Human(info[0], info[1], days, Integer.valueOf(info[2])));
                        guestsall.add(queue.get(0));
                        int Where = findFreeRoom();
                        settle(Where);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Ops :(");
                        System.out.println("an error occurred during realization...");
                    }
                    break;
                case "settle_out":
                    try {
                        System.out.print("enter Id\n>");
                        String Idshnik = in.nextLine();
                        unSettle(Idshnik);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Ops :(");
                        System.out.println("an error occurred during realization...");
                    }
                    break;
                case "give_info":
                    list.forEach(System.out::println);
                    break;
                case "settle_group":
                    try {
                        System.out.println("How many people");
                        int number = Integer.parseInt(in.nextLine());
                        int BestGroup = Integer.valueOf(findBestForGroup(number));
                        int counter = 0;
                        while (counter < number) {
                            String NameIdDate = in.nextLine();
                            String[] infos = NameIdDate.split(" ");
                            guestsall.add(new Human(infos[0], infos[1], days, Integer.valueOf(infos[2])));
                            queue.add(guestsall.get(counter));
                            settle(BestGroup);
                            counter++;
                        }
                        System.out.println("Group settled succesfully!");
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Ops :(");
                        System.out.println("an error occurred during realization...");
                        System.out.println("It failed with ArrayIndexOutOfBoundsException...");
                    }
                    break;
                case "guests":
                    guestsall.forEach(System.out::println);
                    break;
                case "next_day":
                    days++;
                    for (Human human : guestsall) {
                        human.days++;
                    }
                    System.out.println("Day " + days);
                    Check();
                    break;
                case "exit":
                    System.out.println("Good bye!");
                    end = true;
                    break;
                default:
                    System.out.println("unknown command");
                    System.out.println("maybe it is a typo in your command");
                    break;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene menu = new Scene(root, 1280, 1024);
        
    }
}