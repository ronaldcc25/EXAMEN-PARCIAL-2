// Clase para representar un partido
public class Match {

    public String player1;
    public String player2;
    public int[] sets; // Resultado de los sets (1 si gana player1, 2 si gana player2)
    public String winner;

    public Match(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.sets = new int[3]; // Máximo 3 sets
    }
}