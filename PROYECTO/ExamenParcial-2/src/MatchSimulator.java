import java.util.Random;
import java.util.concurrent.Callable;

// Clase Callable para simular un partido
public class MatchSimulator implements Callable<Match> {
    private final Match match;
    private final Random random;

    public MatchSimulator(Match match) {
        this.match = match;
        this.random = new Random();
    }

    @Override
    public Match call() {
        int winsPlayer1 = 0;
        int winsPlayer2 = 0;
        int setIndex = 0;

        // Simular hasta que alguien gane 2 sets
        while (winsPlayer1 < 2 && winsPlayer2 < 2 && setIndex < 3) {
            // Simular un set (50% de probabilidad para cada jugador)
            if (random.nextBoolean()) {
                match.sets[setIndex] = 1; // Gana player1
                winsPlayer1++;
            } else {
                match.sets[setIndex] = 2; // Gana player2
                winsPlayer2++;
            }
            setIndex++;
        }

        // Determinar ganador
        match.winner = winsPlayer1 >= 2 ? match.player1 : match.player2;
        return match;
    }
}