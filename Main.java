package search;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.exit;

public class Main {

    static int licznik = 0;
    static String string = null;
    static ArrayList<String> ans = new ArrayList<String>();
    static List<String> persons = new ArrayList<>();
    static Set<String> any = new HashSet<>();


    public static void main(String[] args) throws IOException {


        Scanner sca = null;
        try {
            sca = new Scanner(System.in);

            File file;
            file = new File(args[1]);

            try (Scanner input = new Scanner(file)) {
                while (input.hasNextLine()) {
                    persons.add(input.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found!");
            }

            BufferedReader br = null;
            try {

                printMenu();
                int choose = sca.nextInt();
                while (choose != 0) {
                    switch (choose) {
                        case 1:

                            selectStrategy();
                            printMenu();
                            break;
                        case 2:
                            secondOption(br);
                            printMenu();
                            break;
                        default:
                            mistake();
                            printMenu();
                    }
                    choose = sca.nextInt();
                }

                System.out.println("Bye!");
                exit(-1);

            } finally {
                if (br != null) {
                    br.close();
                }

            }
            br.reset();


        } finally {
            if (sca != null) {
                sca.close();
            }
        }

    }

    private static void selectStrategy() throws IOException {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        Scanner scann = new Scanner(System.in);
        String strategy = scann.next();
        if (strategy.equalsIgnoreCase("ALL")) {
            searchAll();
        } else if (strategy.equalsIgnoreCase("ANY")) {
            searchAny();
        } else if (strategy.equalsIgnoreCase("NONE")) {
            searchNone();
        }
    }

    private static void secondOption(BufferedReader br) throws IOException {

        switch (licznik) {
            case 0:

                System.out.println("\n=== List of people ===\n");
                while ((string = br.readLine()) != null) {
                    System.out.println(string);

                    ans.add(string);
                }
                licznik++;
                break;
            default:
                System.out.println("\n=== List of people ===\n");
                ans.forEach(System.out::println);

                break;
        }
    }


    public static void searchAll() {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        String search = s.nextLine().toLowerCase();
        boolean match = false;
        int sum = 0;
        for (String person : persons) {
            if (person.toLowerCase().matches("(^" + search + "\\s.*|.*\\s" + search + "\\s.*|.*\\s" + search + "$)")) {
                match = true;
                sum++;
            }
        }
        if (match) {
            System.out.println(sum + " persons found:");
            for (String person : persons) {
                if (person.toLowerCase().matches(".*" + search + ".*")) {
                    System.out.println(person);
                }
            }
        } else {
            System.out.println("No matching people found.");
        }
    }

    public static void searchAny() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        String search = s.nextLine().toLowerCase();
        String[] word = search.split(" ");

        boolean match = false;
        int sum = 0;


        for (String person : persons) {
            for (String w : word) {
                if (person.toLowerCase().matches("^" + w + "\\s.*|.*\\s" + w + "\\s.*|.*\\s" + w + "$")) {
                    match = true;
                }
            }
        }
        if (match) {
            for (String person : persons) {
                for (String w : word) {
                    if (person.toLowerCase().matches(".*" + w + ".*")) {
                        any.add(person);

                    }
                }
            }
            System.out.println(any.size() + " " + "persons found:");
            for (String a : any) {
                System.out.println(a);
            }
        } else {
            System.out.println("No matching people found.");
        }
    }


    public static void searchNone() {


        Scanner s = new Scanner(System.in);
        System.out.println("Enter a name or email to search all suitable people.");
        String search = s.nextLine().toLowerCase();
        String[] word = search.split(" ");
        boolean match = false;
        int sum = 0;
        for (String person : persons) {
            for (String w : word) {
                if ((person.toLowerCase().matches("^" + w + "\\s.*|.*\\s" + w + "\\s.*|.*\\s" + w + "$"))) {
                    match = true;
                    sum++;
                }
            }
        }
        if (match) {
            for (String person : persons) {
                for (String w : word) {

                    if (person.toLowerCase().matches(".*" + w + ".*")) {

                        any.add(person);
                    }
                }
            }
            Set<String> no = new HashSet<String>(persons);

            no.removeAll(any);
            System.out.println((no.size()) + " persons found:");
            for (String n : no) {
                System.out.println(n);
            }
        } else {
            System.out.println("No matching people found.");
        }


    }


    private static void mistake() {
        System.out.println("Incorrect option! Try again.");
    }


    public static void printMenu() {
        System.out.println("\n=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit");
    }


}