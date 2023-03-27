package myshelfie_model.board;

import myshelfie_model.Tile;

import java.util.List;

/* La board è descritta come una matrice 9x9 in cui le caselle vengono settate così:
 * non raggiungibili: null
 * vuote : EMPTY
 * coperte : getType()
 *
 *   inizializzo così (i numeri in romano verranno posti a -1 sse verrà richiesta l'implementazione con il numero player corrispondente, altrimenti null)
 *   i\j   0     1       2     3     4      5      6      7      8
 *    0  null | null | null | III  | IV  | null | null | null | null
 *    1  null | null | null | II   | II  | IV   | null | null | null
 *    2  null | null | III  | II   | II  | II   | III  | null | null
 *    3  null | IV   | II   | II   | II  | II   | II   | II   | III
 *    4  IV   | II   | II   | II   | II  | II   | II   | II   | IV
 *    5  III  | II   | II   | II   | II  | II   | II   | IV   | null
 *    6  null | null | III  | II   | II  | II   | III  | null | null
 *    7  null | null | null | IV   | II  | II   | null | null | null
 *    8  null | null | null | null | IV  | III  | null | null | null
 * */

public interface Board {
    public void refill(List<Tile> tiles);

    public boolean refillNeeded();

    public List remove(List<Tile> chosen) throws NotPossibleMove;

}
