package myshelfie_view.cli.printers.macro;

import myshelfie_model.goal.common_goal.*;

import static myshelfie_view.cli.printers.Printer.String2CharMatrix;


public class PlotCommonGoals{
    private CommonGoal modelCommongoal;
    private char[][] commongoal;
    private String[] commonGoalString = {
            //0
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │                                                                 │
                                                                      \s
    │   ╔═══╦═══╗           Two groups each containing 4 tiles.       │
        ║ = ║ = ║               of the same type in a 2x2 square.     \s
    │   ╠═══╬═══╣   X 2     The tiles of one square can be different  │
        ║ = ║ = ║               from those of the other square.       \s
    │   ╚═══╩═══╝                                                     │
                                                                      \s
                                                                      \s
    │                                                                 │
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //1
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │   ╔═══╗                                                         │
        ║ ≠ ║                                                         \s
    │   ╠═══╣                                                         │
        ║ ≠ ║                                                         \s
    │   ╠═══╣                                                         │
        ║ ≠ ║                                                         \s
    │   ╠═══╣ X 2     Two columns each formed by 6 different          │
        ║ ≠ ║                                  types of tiles.        \s
    │   ╠═══╣                                                         │
        ║ ≠ ║                                                         \s
        ╠═══╣                                                         \s
    │   ║ ≠ ║                                                         │
    │   ╚═══╝                                                         │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //2
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │     ┌ ─ ─ ─ ┐                                                   │
          ¦ ╔═══╗ ¦                                                   \s
    │     ¦ ║   ║ ¦       Four groups each containing at least        │
          ¦ ╠═══╣ ¦       4 tiles of the same type                    \s
    │     ¦ ║   ║ ¦       (not necessarily in the depicted shape).    │
          ¦ ╠═══╣ ¦ X 4   The tiles of one group can be different     \s
    │     ¦ ║   ║ ¦       from those of another group.                │
          ¦ ╠═══╣ ¦                                                   \s
    │     ¦ ║   ║ ¦                                                   │
          ¦ ╚═══╝ ¦                                                   \s
    │     └ ─ ─ ─ ┘                                                   │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //3
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │     ┌ ─ ─ ─ ┐                                                   │
          ¦ ╔═══╗ ¦       Six groups each containing at least         \s
    │     ¦ ║   ║ ¦       2 tiles of the same type                    │
          ¦ ╠═══╣ ¦ X 6   (not necessarily in the depicted shape).    \s
    │     ¦ ║   ║ ¦       The tiles of one group can be different     │
          ¦ ╚═══╝ ¦       from those of another group.                \s
    │     └ ─ ─ ─ ┘                                                   │
                                                                      \s
    │                                                                 │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //4
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │   ╔═══╗                                                         │
        ║   ║                                                         \s
    │   ╠═══╣                                                         │
        ║   ║             Three columns each formed by 6 tiles        \s
    │   ╠═══╣ MAX 3 ≠     of maximum three different types.           │
        ║   ║             One column can show the same or             \s
    │   ╠═══╣ X 3         a different combination of another column.  │
        ║   ║                                                         \s
    │   ╠═══╣                                                         │
        ║   ║                                                         \s
        ╠═══╣                                                         \s
    │   ║   ║                                                         │
    │   ╚═══╝                                                         │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //5
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │                                                                 │
      ╔═══╦═══╦═══╦═══╦═══╗ Two lines each formed by 5 different      \s
    │ ║ ≠ ║ ≠ ║ ≠ ║ ≠ ║ ≠ ║ types of tiles.                           │
      ╚═══╩═══╩═══╩═══╩═══╝ One line can show the same or a different \s
    │                       combination of the other line.            │
              X 2                                                     \s
    │                                                                 │
                                                                      \s
    │                                                                 │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //6
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │                                                                 │
      ╔═══╦═══╦═══╦═══╦═══╗ Four lines each formed by 5 tiles         \s
    │ ║   ║   ║   ║   ║   ║ of maximum three different types.         │
      ╚═══╩═══╩═══╩═══╩═══╝ One line can show the same or a different \s
    │     MAX 3 ≠           combination of the other line.            │
                                                                      \s
    │        X 4                                                      │
                                                                      \s
    │                                                                 │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //7
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │   ╔═══╗- -╔═══╗                                                 │
        ║ = ║   ║ = ║       Four tiles of the same type in the four   \s
    │   ╚═══╝   ╚═══╝       corners of the bookshelf.                 │
        ¦           ¦                                                 \s
    │   ╔═══╗   ╔═══╗                                                 │
        ║ = ║   ║ = ║                                                 \s
    │   ╚═══╝_ _╚═══╝                                                 │
                                                                      \s
    │                                                                 │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //8
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │    ╔═══╗ ╔═══╗                                                  │
         ║ = ║ ║ = ║         Eight tiles of the same type.            \s
    │    ╚═══╝ ╚═══╝         There’s no restriction about             │
      ╔═══╗ ╔═══╗ ╔═══╗      the position of these tiles.             \s
    │ ║ = ║ ║ = ║ ║ = ║                                               │
      ╚═══╝ ╚═══╝ ╚═══╝                                               \s
    │ ╔═══╗ ╔═══╗ ╔═══╗                                               │
      ║ = ║ ║ = ║ ║ = ║                                               \s
    │ ╚═══╝ ╚═══╝ ╚═══╝                                               │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //9
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │                                                                 │
                                                                      \s
    │  ╔═══╗   ╔═══╗                                                  │
       ║ = ║   ║ = ║        Five tiles of the same type               \s
    │  ╚═══╬═══╬═══╝        forming an X.                             │
           ║ = ║                                                      \s
    │  ╔═══╬═══╬═══╗                                                  │
       ║ = ║   ║ = ║                                                  \s
    │  ╚═══╝   ╚═══╝                                                  │
                                                                      \s
    │                                                                 │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //10
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │ ╔═══╗                                                           │
      ║ = ║                                                           \s
    │ ╚═══╬═══╗                                                       │
          ║ = ║                Five tiles of the same type            \s
    │     ╚═══╬═══╗            forming a diagonal.                    │
              ║ = ║                                                   \s
    │         ╚═══╬═══╗                                               │
                  ║ = ║                                               \s
    │             ╚═══╬═══╗                                           │
                      ║ = ║                                           \s
    │                 ╚═══╝                                           │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """,
            //11
    """
    ┌── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┐
    │  ╔═══╗                                                          │
       ║   ║                                                          \s
    │  ╠═══╬═══╗                                                      │
       ║   ║   ║               Five columns of increasing or          \s
    │  ╠═══╬═══╬═══╗           decreasing height.                     │
       ║   ║   ║   ║           Starting from the first column         \s
    │  ╠═══╬═══╬═══╬═══╗       on the left or on the right,           │
       ║   ║   ║   ║   ║       each next column must be made of       \s
    │  ╠═══╬═══╬═══╬═══╬═══╗   exactly one more tile.                 │
       ║   ║   ║   ║   ║   ║   Tiles can be of any type.              \s
    │  ╚═══╩═══╩═══╩═══╩═══╝                                          │
                                                                      \s
    │                                                                 │
    └── ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ──┘
    """
    };


    public PlotCommonGoals(CommonGoal modelCommonGoal) {
        this.modelCommongoal = modelCommongoal;
        commongoal = new char[15][67];
        buildCommongoal();
    }
    /***
     * this method is used to build the matrix for a common goal
     */

    public char[][] getCommongoalCharMatrix() {
        return commongoal;
    }

    /***
     *
     *
     * Coordinate system of the Game displayed on the CLI
     *           1         2         3         4         5         6         7         8         9
     * 0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░X                                       Y░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░X┌────────┐  ┌────────┐Y░░░░░XYour PointsY░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┬   A  │  │ ┬┬  B  │ ░░░░░Z    C      W░░░░░░░░░░
     * ░░                                         ░░░░░ │ ┴  ¯¯¯ │  │ ┴┴ ¯¯¯ │ ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░Z└────────┘  └────────┘W░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░               (Board)                   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░                                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░Z                                       W░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░X                                                                 Y░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                   (Common Goal)                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░                                                                   ░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░Z                                                                 W░░░
     * ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
     *
     * CommonGoal: X = 23,30 Y = 23,96 Z = 37,30 W = 37,96 --> Area: 15x67
     *
     */
    public void buildCommongoal(){
        switch(modelCommongoal.getClass().getSimpleName()) {
            case "TwoSquares":
                commongoal=String2CharMatrix(commonGoalString[0]);
                break;
            case "TwoColumns":
                commongoal=String2CharMatrix(commonGoalString[1]);
                break;
            case "FourGroups4Tiles":
                commongoal=String2CharMatrix(commonGoalString[2]);
                break;
            case "SixGroups2Tiles":
                commongoal=String2CharMatrix(commonGoalString[3]);
                break;
            case "ThreeColumnsMax3":
                commongoal=String2CharMatrix(commonGoalString[4]);
                break;
            case "TwoLines":
                commongoal=String2CharMatrix(commonGoalString[5]);
                break;
            case "FourLinesMax3":
                commongoal=String2CharMatrix(commonGoalString[6]);
                break;
            case "FourCorners":
                commongoal=String2CharMatrix(commonGoalString[7]);
                break;
            case "EightTiles":
                commongoal=String2CharMatrix(commonGoalString[8]);
                break;
            case "Cross":
                commongoal=String2CharMatrix(commonGoalString[9]);
                break;
            case "Diagonal5Tiles":
                commongoal=String2CharMatrix(commonGoalString[10]);
                break;
            case "Pyramid":
                commongoal=String2CharMatrix(commonGoalString[11]);
                break;
            default:
                System.out.println("Error: CommonGoal not found");
                commongoal = null;
                break;
        }
    }




}
