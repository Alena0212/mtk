import Exceptions.InputFileException;
import Exceptions.NotValidSequenceException;
import Exceptions.StateMachineFileException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: <MachineInfoFile> <InputFile>");
        }
        try {
            StateMachine stateMachine = new StateMachine(args[0]);
            stateMachine.validateSequence(args[1]);
            System.out.println("Input sequence is valid!");
        } catch (StateMachineFileException | InputFileException e) {
            System.err.println(e.getMessage());
        } catch (NotValidSequenceException e) {
            System.out.println("Input sequence is not valid for this machine: " + e.getMessage());
        }
    }
}
