package me.hinnn.ccp;

import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String msg = "The quick brown fox jumps over the lazy dog.";
        final String blocks = "                                     ";
        final String allAscii = "!\"#$%&\\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

        System.out.println("Enter \"exit\" to exit. :)");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter color code here: ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }

            final String[] parts = input.split(" ");

            int r, g, b;
            if (parts.length <= 1) {
                if (!input.startsWith("#")) {
                    input = "#" + input;
                }

                try {
                    r = Integer.valueOf(input.substring( 1, 3 ), 16);
                    g = Integer.valueOf(input.substring( 3, 5 ), 16);
                    b = Integer.valueOf(input.substring( 5, 7 ), 16 );
                } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }
            } else {
                if (parts.length != 3) {
                    System.out.println("Invalid input!");
                    continue;
                }

                try {
                    r = Integer.parseInt(parts[0]);
                    g = Integer.parseInt(parts[1]);
                    b = Integer.parseInt(parts[2]);

                    if ((r > 255 || g > 255 || b > 255) || (r < 0 || g < 0 || b < 0)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }
            }

            final String textColor = genANSI_TrueColorCode(r, g, b);
            final String backColor = genANSI_BackTrueColorCode(r, g, b);
            System.out.println(
                    colorizeString(msg, textColor, "") + "\n"
                    + colorizeString(blocks, "", backColor) + "\n"
                    + colorizeString(allAscii, textColor, "")
            );
        }
    }

    private static final String PREFIX = "\u001b[";
    private static final String ANSI_RESET = "\u001B[0m";

    public static String colorizeString(String str, String textColorCode, String backColorCode) {
        return textColorCode + backColorCode + str + ANSI_RESET;
    }

    private static String genANSI_TrueColorCode(int r, int g, int b) {
        return PREFIX + "38;2;" + r + ";" + g + ";" + b + "m";
    }

    public static String genANSI_BackTrueColorCode(int r, int g, int b) {
        return PREFIX + "48;2;" + r + ";" + g + ";" + b + "m";
    }
}
