package myshelfie_view.cli.printers;

import myshelfie_model.Tile;

import java.util.HashMap;
import java.util.List;

/***
 * This class decides wich color palette to use for the CLI
 * Usages: // System.out.println(Color.RED.getCode() + "Testo rosso" + Color.RESET.getCode());
 * */
public enum Color {
    //How to set color code:
    //R=x G=y B=z -> "\033[0;38;2;x;y;z;m"
    //Example:
    //R=140, G=51, B=0 -> BROWN = "\033[0;38;2;140;51;0m"
    //To choose the color: https://www.w3schools.com/colors/colors_picker.asp
    RESET("\u001B[0m"),
    BROWN("\033[0;38;2;140;051;000m"),
    PARQUET("\033[0;38;2;255;217;180m"),//153, 102, 51
    //Background colors:
    //R=x G=y B=z -> "\u001B[48;2;x;y;z;m"
    BACK_CYAN ("\u001B[48;2;153;204;255m"),
    BACK_GREEN  ("\u001B[48;2;47;120;47m"),
    BACK_YELLOW  ("\u001B[48;2;255;204;0m"),
    BACK_BLUE  ("\u001B[48;2;0;51;153m"),
    BACK_MAGENTA  ("\u001B[48;2;255;128;170m"),
    BACK_BEIGE  ("\u001B[48;2;255;255;204m");



    //#################################### COLOR ####################################
    private final String code;

    Color(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    //#################################### UTILS ####################################
    public static String Tile2String(Tile tile) {
        String tileString = "   ";

        if (tile == null) {
            return tileString;
        }

        switch (tile.getType())
        {
            case CATS -> tileString = (BACK_GREEN.getCode() + "  \s" + RESET.getCode());
            case BOOKS -> tileString = (BACK_BEIGE.getCode() + "  \s" + RESET.getCode());
            case FRAMES -> tileString = (BACK_BLUE.getCode() + "  \s" + RESET.getCode());
            case TROPHIES -> tileString = (BACK_CYAN.getCode() + "  \s" + RESET.getCode());
            case PLANTS -> tileString = (BACK_MAGENTA.getCode() + "  \s" + RESET.getCode());
            case GAMES -> tileString = (BACK_YELLOW.getCode() + "  \s" + RESET.getCode());
        }
        return tileString;
    }

    public static String getColoredString(String str, Color color) {
        StringBuilder coloredString = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            coloredString.append(color.getCode()).append(c).append(Color.RESET.getCode());
        }

        return coloredString.toString();
    }

    public static String aggregateStrings(String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static String colorChar(String string, List <Character> specials, Color color){

        StringBuilder output = new StringBuilder(); // stringa di output

        // ciclo sulla stringa di input
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            if (specials.contains(c)) {
                // se il carattere è quello da colorare, aggiungi il codice colore
                output.append(color.getCode()); // aggiungi il codice colore
                while (i < string.length()-1 && specials.contains(string.charAt(i+1))){
                    output.append(c); // aggiungi il carattere
                    i++;
                };
                output.append(string.charAt(i)).append(Color.RESET.getCode()) ;
            } else {
                // altrimenti aggiungi il carattere normale
                output.append(c);
            }
        }
        return output.toString();

    };



}
