
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scanner;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        System.out.println("Введите размер судоку");
        //new testedMatrix(scanner.nextInt(),scanner.nextInt(),scanner);
        boolean flag;
       //  flag = scanner.nextBoolean();
        flag = false;
        createSudocu(scanner.nextInt(), flag);

    }


    private static void createSudocu(int size, boolean cords) {
        int[][] sudocu = new int[size][size];
        String str;
        for (int i = 0; i < size; i++) {
            Arrays.fill(sudocu[i], 0);
        }
        if (cords) {
            while (!(str = scanner.nextLine()).equals("start")) {
                String[] cell = str.split(" ");
                if (cell.length == 3) {
                    sudocu[Integer.parseInt(cell[0]) - 1][Integer.parseInt(cell[1]) - 1] = Integer.parseInt(cell[2]);
                }
            }
        } else {
            System.out.println("введите судоку, разделителем является пробел");
            scanner.nextLine();
            for (int i = 0; i < size; i++) {
                String[] c = (scanner.nextLine()).split(" ");
                for (int j = 0; j < size; j++) {
                  boolean flag = true;
                    try {
                        Integer.parseInt(c[j]);

                    } catch (NumberFormatException e) {
                        flag = false;}

                    if (flag) {
                        sudocu[j][i] = Integer.parseInt(c[j]);
                    }
                }
            }
        }
        for (int i = 0; i < sudocu[0].length; i++) {
            if (i % ((int) Math.sqrt(size)) == 0) {
                System.out.println();
            }
            for (int j = 0; j < sudocu.length; j++) {
                if (j % ((int) Math.sqrt(size)) == 0) {
                    System.out.print(" ");
                }
                System.out.print(sudocu[j][i] + " ");
                for (int k = 0; k < ((size + "").length() - (sudocu[j][i] + "").length()); k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        Long time;






        time = System.nanoTime();
        System.out.println("решение с помощью танцующих ссылок");
        time = System.nanoTime();
        ConditionMatrix conditionMatrix = new ConditionMatrix(sudocu, size);
        System.out.println(System.nanoTime() - time);


        System.out.println("решение грубой силой");
        BruteForce bruteForce = new BruteForce();
        bruteForce.bruteforce(sudocu);
        System.out.println(System.nanoTime() - time);

    }

}
