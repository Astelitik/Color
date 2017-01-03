package com.color;

import java.io.*;
import java.util.*;

/**
 * Created by Volodymyr Paladiuk on 27.12.2016.
 */

public class JavaColor {

    public static void main(String[] args) {

        //RGB, exp. color red (255, 0, 0), blue (0, 0, 255)

        //for creating color and writing to file
        PrintWriter output = createFile("D:\\colors.txt");
        System.out.println("Make a list of RGB, please enter color values");
        List<MyColor> colorsToFile = createListColor();
        for (MyColor myColor : colorsToFile) {
            writeColorToFile(myColor, output);
        }
        output.close();

        // for getting all colors from file
        List<MyColor> colors = getAllColorFromFile("D:\\colors.txt");
        System.out.println("All colors " + colors);

        // for getting RGB list (first 9 elements) where each color is a multiple of 51.
        List<MyColor> checkedColors = checkColor(colors);
        if (!(checkedColors == null)) {
            for (int i = 0; i < checkedColors.size(); ) {
                if (checkedColors.size() > 9) {
                while (i <= 9) {
                    System.out.println("Checked Colors " + checkedColors.get(i));
                    i++;
                }
                } else {
                    System.out.println("Checked Colors " + checkedColors.get(i));
                    i++;
                }
            }
        } else {
            System.out.println("No RGB list in which each color value is a multiple of 51");
        }
    }

    private static List<MyColor> createListColor() {
        List<MyColor> myColorList = new ArrayList<>();
        Scanner scn = new Scanner(System.in);
        MyColor color;

        boolean ask;
        do {
            ask = false;

            color = buildColorFromConsole();

            System.out.println("Add one more color? (y / n)");
            String askYN = scn.nextLine();
            if (askYN.equalsIgnoreCase("y")) {
                ask = true;
                myColorList.add(color);
            }

        } while (ask);

        myColorList.add(color);

        return myColorList;
    }

    private static MyColor buildColorFromConsole() {
        MyColor myColor = new MyColor();
        Scanner scn = new Scanner(System.in);

        int red;
        int green;
        int blue;

        System.out.println("Input value of color Red (from 0 to 255)");
        red = scn.nextInt();

        if (red > 0 & red <= 255) {
            myColor.red = red;

        } else {
            System.err.println("Next time input number from 0 to 255");
            System.exit(0);
        }

        System.out.println("Input value of color Green (from 0 to 255)");
        green = scn.nextInt();

        if (green > 0 & green <= 255) {
            myColor.green = green;
        } else {
            System.err.println("Next time input number from 0 to 255");
            System.exit(0);
        }

        System.out.println("Input value of color Blue (from 0 to 255)");
        blue = scn.nextInt();
        if (blue > 0 & blue <= 255) {
            myColor.blue = blue;
        } else {
            System.err.println("Next time input number from 0 to 255");
            System.exit(0);
        }
        return myColor;
    }

    private static void writeColorToFile(MyColor myColor, PrintWriter output) {
        String colorInfo = Integer.toString(myColor.red) + ", " + Integer.toString(myColor.green) + ", " + Integer.toString(myColor.blue);
        output.println(colorInfo);
    }

    private static List<MyColor> getAllColorFromFile(String fileName) {
        List<MyColor> colors = new ArrayList<>();

        File listOfColors = new File(fileName);

        try {
            BufferedReader getInfo = new BufferedReader(new FileReader(listOfColors));

            String colorInfo = getInfo.readLine();
            while (colorInfo != null) {

                String[] integerList = colorInfo.split(", ");
                int colorRed = Integer.parseInt(integerList[0]);
                int colorGreen = Integer.parseInt(integerList[1]);
                int colorBlue = Integer.parseInt(integerList[2]);

                MyColor myColor = new MyColor(colorRed, colorGreen, colorBlue);
                colors.add(myColor);

                colorInfo = getInfo.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file");
            System.exit(0);
        }catch (IOException exc) {
            exc.printStackTrace();
            System.exit(0);
        }

        return colors;
    }

    public static List<MyColor> checkColor(List<MyColor> colors) {

        List<MyColor> myColors = new ArrayList<>();

        for (MyColor myColor : colors) {

            if((myColor.red % 51 == 0) & (myColor.green % 51 == 0) & (myColor.blue % 51) == 0) {
                myColors.add(myColor);
            }
        }

       Collections.sort(myColors, new Comparator<MyColor>() {
            @Override
            public int compare(MyColor o1, MyColor o2) {
                return o1.red - o2.red;
            }
        });

        Comparator<MyColor> colorComparator = ((o1, o2) -> o1.blue - o2.blue);
        myColors.sort(colorComparator.reversed());

        return myColors;
    }

    private static class MyColor {
        int red;
        int green;
        int blue;

        public MyColor() {
        }

        public MyColor(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        public String toString() {
            return "MyColor{" +
                    "red=" + red +
                    ", green=" + green +
                    ", blue=" + blue +
                    '}';
        }
    }

    private static PrintWriter createFile(String fileName){

        try {
            File file = new File(fileName);
            PrintWriter colorToWrite = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            return colorToWrite;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}