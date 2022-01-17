package competition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author Falusi Gergő Gábor
 * @since 2020. október 02.
 * @modified 2020. október 17.
 */
public class Contest {

    private ArrayList<Being> beings = new ArrayList<>();
    private ArrayList<Weather> weathers = new ArrayList<>();
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Contest ct = new Contest();
        ct.create();
        ct.run();
    }

    private void create() {

        System.out.println("Kérem a fájl nevét:");
        String fileName = in.next();

        try {
            File myObj = new File(fileName);
            try ( Scanner myReader = new Scanner(myObj)) {
                int beingNum = myReader.nextInt();
                myReader.nextLine();

                for (int i = 0; i < beingNum; i++) {
                    String name = myReader.next();
                    String type = myReader.next();
                    int water = myReader.nextInt();
                    myReader.nextLine();

                    switch (type) {
                        case "h":
                            SandWalker sw = new SandWalker(name, water, 8, true, 0);
                            beings.add(sw);
                            break;
                        case "s":
                            Sponge sp = new Sponge(name, water, 20, true, 0);
                            beings.add(sp);
                            break;
                        case "l":
                            Walker wk = new Walker(name, water, 12, true, 0);
                            beings.add(wk);
                            break;
                    }

                }

                String days = myReader.next();

                for (int i = 0; i < days.length(); i++) {
                    char day = days.charAt(i);
                    switch (day) {
                        case 'n':
                            Sunny su = new Sunny();
                            weathers.add(su);
                            break;
                        case 'f':
                            Cloudy cl = new Cloudy();
                            weathers.add(cl);
                            break;
                        case 'e':
                            Rainy ra = new Rainy();
                            weathers.add(ra);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Rossz fájlnév.");
        }
    }

    private void run() {

        race();
        int champ = champion();

        if (champ != -1) {
            System.out.println("A győztes neve:");
            System.out.println(beings.get(champ).name);
            System.out.println("Megtett távolsága:");
            System.out.println(beings.get(champ).distance);
        } else {
            if (!beings.isEmpty()) {
                System.out.println("Senki nem ért célba.");
            }
        }
    }

    private void race() {
        for (Weather weather : weathers) {
            for (Being being : beings) {
                if (being.isLive) {
                    being.day(weather);
                }
            }
        }
    }

    private int champion() {
        boolean l = false;
        int index = 0;
        int maxIndex = -1;
        int maxDistance = 0;
        for (Being being : beings) {
            if (l && being.isLive) {
                if (being.distance > maxDistance) {
                    maxDistance = being.distance;
                    maxIndex = index;
                }
            }
            if (!l && being.isLive) {
                l = true;
                maxDistance = being.distance;
                maxIndex = index;
            }
            index++;
        }
        return maxIndex;
    }
}
