package coolfish.main;

import java.text.ParseException;

import coolfish.board.Board;
import coolfish.board.FenParser;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Coolfish v0.0.1");

        Testing.testBlockerMasking();

        Board board;
        try {
            board = FenParser.parseFEN("r1bqk1nr/ppp2ppp/8/1P1p1n2/1b1Pp3/4P2N/PBPNBPPP/R2QK2R b KQkq - 3 8");
            board.printBoard(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}