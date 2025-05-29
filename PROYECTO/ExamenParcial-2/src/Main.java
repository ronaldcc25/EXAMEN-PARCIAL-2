import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Lista inicial de 16 jugadores
        List<String> players = new ArrayList<>(Arrays.asList(
                "Jugador1", "Jugador2", "Jugador3", "Jugador4",
                "Jugador5", "Jugador6", "Jugador7", "Jugador8",
                "Jugador9", "Jugador10", "Jugador11", "Jugador12",
                "Jugador13", "Jugador14", "Jugador15", "Jugador16"
        ));

        // Nombres de las rondas
        String[] roundNames = {"Octavos de final", "Cuartos de final", "Semifinales", "Final"};
        int roundIndex = 0;

        // Crear un ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(8); // Hasta 8 partidos simultáneos en octavos

        while (players.size() > 1) {
            System.out.println("\n=== " + roundNames[roundIndex] + " ===");
            List<String> nextRoundPlayers = new ArrayList<>();
            List<Future<Match>> futures = new ArrayList<>();

            // Crear partidos para la ronda actual
            for (int i = 0; i < players.size(); i += 2) {
                Match match = new Match(players.get(i), players.get(i + 1));
                futures.add(executor.submit(new MatchSimulator(match)));
            }

            // Esperar y mostrar resultados de los partidos
            for (Future<Match> future : futures) {
                Match result = future.get();
                System.out.println("\nPartido: " + result.player1 + " vs " + result.player2);
                for (int i = 0; i < result.sets.length; i++) {
                    if (result.sets[i] != 0) {
                        String setWinner = result.sets[i] == 1 ? result.player1 : result.player2;
                        System.out.println("  Set " + (i + 1) + ": Gana " + setWinner);
                    }
                }
                System.out.println("  Ganador: " + result.winner);
                nextRoundPlayers.add(result.winner);
            }

            // Actualizar lista de jugadores para la siguiente ronda
            players = nextRoundPlayers;
            roundIndex++;
        }

        // Declarar al campeón
        System.out.println("\n¡El campeón del torneo es: " + players.get(0) + "!");
        executor.shutdown();
    }
}