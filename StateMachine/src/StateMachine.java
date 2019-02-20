import Exceptions.InputFileException;
import Exceptions.NotValidSequenceException;
import Exceptions.StateMachineFileException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StateMachine {

    private Set<Integer> finalStates;
    private Map<Integer, Map<Character, Integer>> transitions = new HashMap();
    private int stateCount = 0;
    private Integer currentState = 0;
    public StateMachine(String filePath) throws StateMachineFileException {
        try {
            parseMachineInfo(filePath);
        } catch (IOException | NumberFormatException e) {
            throw new StateMachineFileException( "Bad machine info file: "+ e.getMessage());
        }
    }

    private void parseMachineInfo(String machineInfoFilePath) throws IOException {
        try (Scanner sc = new Scanner(new File(machineInfoFilePath))) {
            finalStates = Arrays.stream(sc.nextLine().split(" ")).map((s)->Integer.parseInt(s)).collect(Collectors.toSet());
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(" ");
                int q1 = Integer.parseInt(data[0]), q2 = Integer.parseInt(data[2]);
                if (!transitions.containsValue(q1)) {
                    transitions.put(q1, new HashMap<>());
                    stateCount++;
                }
                transitions.get(q1).put(data[0].charAt(0), q2);
            }
        }
    }

    private void traceSequence(String filepath) throws IOException, NotValidSequenceException {
        currentState = 0;
        try (Reader reader = new FileReader(new File(filepath))) {
            int read;
            while ((read = reader.read()) > 0) {
                Character label = (char) read;
                currentState = transitions.get(currentState).get(label);
                if (currentState == null) throw new NotValidSequenceException("Character " + label.toString() + " doesn't belong to the alphabet");
            }
        }
        if (currentState != 0) throw new NotValidSequenceException("Sequence in the input file " + " doesn't belong to the language of the state machine");
    }

    public void validateSequence(String filePath) throws InputFileException, NotValidSequenceException {
        try {
            traceSequence(filePath);
        } catch (IOException e) {
            throw new InputFileException("Bad input file " + e.getMessage());
        }
    }

}
