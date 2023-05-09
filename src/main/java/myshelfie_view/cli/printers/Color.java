package myshelfie_view.cli.printers;

/***
 * This class decides wich color palette to use for the CLI
 * Usages: // System.out.println(Color.RED.getCode() + "Testo rosso" + Color.RESET.getCode());
 * */
public enum Color {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");


    private final String code;

    Color(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static void  main(String[] args) {
        System.out.println(Color.MAGENTA.getCode() + "Testo ABCD0123456789" + Color.RESET.getCode());
    }
}
