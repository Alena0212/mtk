import Exceptions.InputFileException;
import Exceptions.NotValidSequenceException;
import Exceptions.StateMachineFileException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StateMachine {

    private Set<Integer> finalStates;
    private Map<Integer, Map<Character, ArrayList<Integer>>> transitions = new HashMap();
    public StateMachine(String filePath) throws StateMachineFileException {
        try {
            buildMachine(filePath);
        } catch (IOException | NumberFormatException e) {
            throw new StateMachineFileException( "Bad machine info file: "+ e.getMessage());
        }
    }

    private void buildMachine(String machineInfoFilePath) throws IOException {
        try (Scanner sc = new Scanner(new File(machineInfoFilePath))) {
            finalStates = Arrays.stream(sc.nextLine().split(" ")).map((s)->Integer.parseInt(s)).collect(Collectors.toSet());
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(" ");
                int q1 = Integer.parseInt(data[0]), q2 = Integer.parseInt(data[2]);
                char alpha = data[1].charAt(0);
                if (!transitions.containsKey(q1)) {
                    transitions.put(q1, new HashMap<>());
                }
                if (!transitions.get(q1).containsKey(alpha)) {
                    transitions.get(q1).put(alpha, new ArrayList<>());
                }
                transitions.get(q1).get(alpha).add(q2);
            }
        }
    }

    private void traceSequence(String filepath) throws IOException, NotValidSequenceException {
        Stack<Integer> currentStates = new Stack<>();
        try (Reader reader = new FileReader(new File(filepath))) {
            currentStates.push(0);
            int read;
            Character label;
            while ((read  = reader.read()) > 0) {
                label = (char) read;
                while (!currentStates.isEmpty()) {
                    int currentState = currentStates.pop();
                    ArrayList<Integer> statesToGoTo = transitions.get(currentState).get(label);
                    if (statesToGoTo != null) {
                        for (Integer i : statesToGoTo) {
                            currentStates.push(i);
                        }
                    }
                }
            }
        }
        while (!currentStates.isEmpty()) {
            Integer state = currentStates.pop();
            if (finalStates.contains(state)) return;
        }
        throw new NotValidSequenceException("sequence in the input file " + " doesn't belong to the language of the state machine");
    }

    public void validateSequence(String filePath) throws InputFileException, NotValidSequenceException {
        try {
            traceSequence(filePath);
        } catch (IOException e) {
            throw new InputFileException("Bad input file " + e.getMessage());
        }
    }

}
