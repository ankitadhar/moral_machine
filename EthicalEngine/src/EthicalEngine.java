
import ethicalengine.Character.AgeCategory;
import ethicalengine.Animal;
import ethicalengine.Character.BodyType;
import ethicalengine.Character;
import ethicalengine.Constants;
import ethicalengine.Character.Gender;
import ethicalengine.Person;
import ethicalengine.Person.Profession;
import ethicalengine.Scenario;
import ethicalengine.Animal.Specie;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Possible decision outputs
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 19-Jun-2020
 *
 * enum Decision { /** Pedestrians crossing the road.
 *
 * PEDESTRIANS, /** Passengers of the car.
 *
 * PASSENGERS; }
 */
/**
 * Implementation of the moral machine with the main method to start the program.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class EthicalEngine {

    /**
     * Possible decision outputs
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 19-Jun-2020
     */
    enum Decision {
        /**
         * Pedestrians crossing the road.
         */
        PEDESTRIANS,
        /**
         * Passengers of the car.
         */
        PASSENGERS;
    }

    static final Scanner KEYIN = new Scanner(System.in);
    private static String writeFilename = Constants.LOG_FILE;
    private static String filename = null;

    public EthicalEngine() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Audit audit;
        boolean readfileFlag = false;
        boolean writefileFlag = false;
        boolean interactFlag = false;
        boolean helpFlag = false;
        ArrayList<Scenario> scenarioList;
        EthicalEngine engine = new EthicalEngine();

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-c":
                    case "--config": {
                        try {
                            filename = args[++i];
                            readfileFlag = true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            helpFlag = true;
                        }
                        break;
                    }
                    case "-h":
                    case "--help": {
                        helpFlag = true;
                        break;
                    }
                    case "-r":
                    case "--results": {
                        try {
                            writeFilename = args[++i];
                            writefileFlag = true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            helpFlag = true;
                        }
                        break;
                    }
                    case "-i":
                    case "--interactive": {
                        interactFlag = true;
                        break;
                    }
                    default:
                        helpFlag = true;
                }
            }

            if (helpFlag) {
                engine.showHelpContent();
            } else if (interactFlag) {
                if (!writefileFlag) {
                    writeFilename = Constants.USER_LOG_FILE;
                }
                try {
                    engine.welcomeUser(readfileFlag);
                } catch (InvalidInputException | IOException e) {
                    System.out.println(e.getMessage());
                }
            } else if (readfileFlag) {
                try {
                    scenarioList = engine.readScenarioFromFile();
                    audit = new Audit(scenarioList);
                    audit.run();
                    audit.printToFile(writeFilename);
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException | InvalidDataFormatException | NumberFormatException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (writefileFlag) {
                engine.auditRandomScenarios();
            }
        } else {
            engine.auditRandomScenarios();
        }
    }

    /**
     * Displays content of --help command line argument
     */
    public void showHelpContent() {
        System.out.println(Constants.HELP_CONTENT);
    }

    /**
     * Starts the program in interactive mode.
     *
     * @param readfileFlag indicates if scenarios are to be read from external file during
     * interactive mode.
     * @throws InvalidInputException for invalid input from user
     * @throws IOException for IO operations welcomeUser sets up the stage for interactive mode
     * for user.
     */
    public void welcomeUser(boolean readfileFlag) throws InvalidInputException, IOException {
        String logConsent;

        ArrayList<Scenario> scenarioList;
        Audit userAudit = new Audit();

        if (readfileFlag) {
            try {
                scenarioList = readScenarioFromFile();
                userAudit = new Audit(scenarioList);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException | InvalidDataFormatException | NumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        userAudit.setAuditType(Constants.AUDIT_BY_USER);

        try {
            readWelcomefile();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(Constants.CONSENT_TO_LOG_MSG
                + "(" + Constants.USER_AGREED + "/" + Constants.USER_DISAGREED + ")");

        do {
            logConsent = KEYIN.nextLine();
            try {
                if (!(logConsent.equals(Constants.USER_DISAGREED)
                        || logConsent.equals(Constants.USER_AGREED))) {
                    throw new InvalidInputException(Constants.CONSENT_TO_LOG_MSG
                            + "(" + Constants.USER_AGREED + "/" + Constants.USER_DISAGREED + ")");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!(logConsent.equals(Constants.USER_DISAGREED)
                || logConsent.equals(Constants.USER_AGREED)));

        interactiveDecision(userAudit, readfileFlag, logConsent.equals(Constants.USER_AGREED));
    }

    /**
     * Calls functions from Audit class to present scenarios for user to decide
     *
     * @param userAudit is the instance of Audit class to be used for interactive mode
     * @param readfileFlag indicates if scenarios are to be read from external file during
     * interactive mode.
     * @param logConsent indicates user's consent to log audit report based on user's decision.
     * @throws InvalidInputException for invalid input from user
     * @throws IOException for IO operations
     */
    public void interactiveDecision(Audit userAudit, boolean readfileFlag, boolean logConsent)
            throws InvalidInputException, IOException {

        boolean exhaustedScenarios;

        exhaustedScenarios = userAudit.runByUser(readfileFlag);

        if (logConsent) {
            userAudit.printToFile(writeFilename);
        } else {
            userAudit.printStatistic();
        }
        if (exhaustedScenarios) {
            System.out.println("That's all. Press Enter to quit.");
            KEYIN.nextLine();
            System.exit(0);
        }

        System.out.println(Constants.CONSENT_TO_CONTINUE
                + "(" + Constants.USER_AGREED + "/" + Constants.USER_DISAGREED + ")");
        String toContinue;

        do {
            toContinue = EthicalEngine.KEYIN.nextLine();
            try {
                switch (toContinue) {
                    case Constants.USER_AGREED:
                        interactiveDecision(userAudit, readfileFlag, logConsent);
                        break;
                    case Constants.USER_DISAGREED:
                        System.exit(0);
                    default:
                        throw new InvalidInputException(Constants.CONSENT_TO_CONTINUE
                                + "(" + Constants.USER_AGREED + "/"
                                + Constants.USER_DISAGREED + ")");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!(toContinue.equals(Constants.USER_DISAGREED)
                || toContinue.equals(Constants.USER_AGREED)));
    }

    /**
     * Reads the welcome content from file for the user in interactive mode.
     *
     * @throws FileNotFoundException if file to read welcome content is not present
     * @throws IOException for IO operations
     */
    public void readWelcomefile() throws FileNotFoundException, IOException {
        File file = new File(Constants.WELCOME_FILE);
        if (file.exists()) {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String streamCurLine;

            while ((streamCurLine = bufferReader.readLine()) != null) {
                System.out.println(streamCurLine);
            }
            bufferReader.close();
        } else {
            throw new FileNotFoundException(Constants.FILE_TO_READ_NOT_FOUND);
        }
    }

    /**
     * Auto audits randomly generated scenarios
     */
    public void auditRandomScenarios() {
        Audit audit = new Audit();
        audit.run(Constants.AUDIT_STREAK);
        audit.printToFile(writeFilename);
    }

    /**
     * Reads an external .csv file to create scenarios.
     *
     * @return array of scenarios generated by reading external file
     * @throws InvalidDataFormatException when number of attributes are not correct to generate
     * scenarios
     * @throws FileNotFoundException when file to read is not present in the specified path
     * @throws IOException for IO operations
     * @throws NumberFormatException when number not found when expected
     */
    public ArrayList<Scenario> readScenarioFromFile()
            throws InvalidDataFormatException, FileNotFoundException, IOException,
            NumberFormatException {

        ArrayList<Scenario> scenarioList = new ArrayList<>();
        /**
         * initialising indices of headers of .csv file with -1.
         */
        int classIndex = -1;
        int genderIndex = -1;
        int ageIndex = -1;
        int bodyTypeIndex = -1;
        int professionIndex = -1;
        int pregnantIndex = -1;
        int isYouIndex = -1;
        int speciesIndex = -1;
        int isPetIndex = -1;
        int roleIndex = -1;

        File file = new File(filename);

        /**
         * checking if the file exists at the specified path.
         */
        if (file.exists()) {

            BufferedReader bufferReader = new BufferedReader(new FileReader(file));

            /**
             * initialising parameters required for generating scenario.
             */
            ArrayList<Character> passengers = new ArrayList<>();
            ArrayList<Character> pedestrians = new ArrayList<>();
            boolean isLegalScenario = false;

            /**
             * initialising counters. line counter tracks line number of .csv file that is being
             * read. head counter is used for finding index number of each attribute as per .csv
             * headers.
             */
            int lineCounter = 0;
            int headCounter = 0;

            // reading file line by line to fetch all scenarios
            String streamCurLine;
            String[] scenarioAttr;

            if ((streamCurLine = bufferReader.readLine()) != null) {
                /**
                 * assuming first row of the file has the header indicating column position of
                 * attributes. reading and setting indices for attributes.
                 */
                scenarioAttr = streamCurLine.split(Constants.ATTR_DELIMITER);
                lineCounter++;
                for (String attr : scenarioAttr) {
                    switch (attr) {
                        case "class":
                            classIndex = headCounter++;
                            break;
                        case "gender":
                            genderIndex = headCounter++;
                            break;
                        case "age":
                            ageIndex = headCounter++;
                            break;
                        case "bodyType":
                            bodyTypeIndex = headCounter++;
                            break;
                        case "profession":
                            professionIndex = headCounter++;
                            break;
                        case "pregnant":
                            pregnantIndex = headCounter++;
                            break;
                        case "isYou":
                            isYouIndex = headCounter++;
                            break;
                        case "species":
                            speciesIndex = headCounter++;
                            break;
                        case "isPet":
                            isPetIndex = headCounter++;
                            break;
                        case "role":
                            roleIndex = headCounter++;
                            break;
                    }
                }
            }

            while ((streamCurLine = bufferReader.readLine()) != null) {
                try {
                    /**
                     * assigning default values to person and animal characteristic variables.
                     */
                    int age = Constants.DEFAULT_AGE;
                    Gender gender = Constants.DEFAULT_GENDER;
                    Profession profession = Constants.DEFAULT_PROFESSION;
                    BodyType bodyType = Constants.DEFAULT_BODYTYPE;
                    String species = Constants.DEFAULT_SPECIES;
                    boolean isYou = false;
                    boolean isPregnant = false;
                    boolean isPet = false;

                    lineCounter++;

                    /**
                     * fetching scenario attributes; i.e, character's characteristics by splitting
                     * at the delimiter, since we are reading a .csv file delimiter is ','.
                     */
                    scenarioAttr = streamCurLine.split(Constants.ATTR_DELIMITER);

                    /**
                     * if the class column begins with "scenario:", it indicates beginning of a
                     * new scenario details
                     */
                    if (scenarioAttr[classIndex].startsWith("scenario:")) {
                        if (lineCounter > 2) {
                            /**
                             * generating a scenario before reading details of new scenario; with
                             * an exception of the first scenario.
                             */
                            Character[] passengerChar = passengers.toArray(new Character[0]);
                            Character[] pedestrianChar = pedestrians.toArray(new Character[0]);
                            Scenario scenario = new Scenario(passengerChar, pedestrianChar,
                                    isLegalScenario);
                            scenarioList.add(scenario);

                            // re-initialising parameters required for generating scenario.
                            passengers = new ArrayList<>();
                            pedestrians = new ArrayList<>();
                        }

                        // setting scenario as legal scenario if class column has "scenario:green"
                        isLegalScenario = scenarioAttr[classIndex].split(":")[1].equals("green");
                    } else if (scenarioAttr[classIndex].startsWith("person")) {
                        /**
                         * if the class column is person, scenario attributes are person's
                         * characteristics.
                         */

                        // throwing exception when attributes are lesser than the requirement
                        if (scenarioAttr.length != Constants.ATTR_LIST_LENGTH) {
                            throw new InvalidDataFormatException(lineCounter);
                        }

                        /**
                         * validating characteristics and assigning them to corresponding
                         * variables.
                         */
                        for (int i = 0; i < scenarioAttr.length; i++) {
                            try {
                                if (!scenarioAttr[i].equals("") && i == ageIndex) {
                                    // invalid age if age is lesser than minimum required age.
                                    if (Integer.parseInt(scenarioAttr[ageIndex])
                                            < Constants.AGE_MIN) {
                                        throw new InvalidCharacteristicException(lineCounter);
                                    }
                                    age = Integer.parseInt(scenarioAttr[ageIndex]);
                                } else if (!scenarioAttr[i].equals("") && i == professionIndex) {
                                    if (Integer.parseInt(scenarioAttr[ageIndex]) >= Constants.AGE_ADULT_MIN
                                            && Integer.parseInt(scenarioAttr[ageIndex]) <= Constants.AGE_ADULT_MAX) {
                                        profession = Profession.valueOf(
                                                getValue(scenarioAttr[professionIndex],
                                                        "profession",
                                                        lineCounter));
                                    }
                                } else if (!scenarioAttr[i].equals("") && i == genderIndex) {
                                    gender = Gender.valueOf(getValue(
                                            scenarioAttr[genderIndex],
                                            "gender",
                                            lineCounter));
                                } else if (!scenarioAttr[i].equals("") && i == bodyTypeIndex) {
                                    bodyType = BodyType.valueOf(getValue(
                                            scenarioAttr[bodyTypeIndex],
                                            "bodyType",
                                            lineCounter));
                                } else if (!scenarioAttr[i].equals("") && i == isYouIndex) {
                                    isYou = scenarioAttr[isYouIndex].equalsIgnoreCase("true");
                                } else if (!scenarioAttr[i].equals("") && i == pregnantIndex) {
                                    isPregnant = scenarioAttr[pregnantIndex]
                                            .equalsIgnoreCase("true");
                                }
                            } catch (InvalidCharacteristicException e) {
                                /**
                                 * catches exception when invalid characteristics are found in
                                 * .csv.
                                 */
                                System.out.println(e.getMessage());
                            } catch (NumberFormatException e) {
                                // catches exception when age in .csv is not a number.
                                System.out.println("WARNING: invalid number format in config file"
                                        + " in line " + lineCounter);
                            }
                        }

                        // creating instance of person class with characteristics found in .csv.
                        Person person = new Person(age, profession, gender, bodyType, isPregnant);
                        person.setAsYou(isYou); // setting as you if given in .csv.

                        // adding to appropriate list based on their role specified in .csv.
                        if (scenarioAttr[roleIndex].equals("pedestrian")) {
                            pedestrians.add(person);
                        } else if (scenarioAttr[roleIndex].equals("passenger")) {
                            passengers.add(person);
                        }
                    } else if (scenarioAttr[classIndex].startsWith("animal")) {
                        /**
                         * if the class column is animal, scenario attributes are animal's
                         * characteristics.
                         */

                        // throwing exception when attributes are lesser than the requirement
                        if (scenarioAttr.length != Constants.ATTR_LIST_LENGTH) {
                            throw new InvalidDataFormatException(lineCounter);
                        }

                        isPet = scenarioAttr[isPetIndex].equalsIgnoreCase("true");

                        try {
                            // invalid species if species of animal is not specified.
                            if (scenarioAttr[speciesIndex] == null) {
                                throw new InvalidCharacteristicException(lineCounter);
                            } else if (!scenarioAttr[speciesIndex].equals("")) {
                                species = scenarioAttr[speciesIndex];
                            }

                        } catch (InvalidCharacteristicException e) {
                            System.out.println(e.getMessage());
                        }

                        // creating instance of animal class with characteristics found in .csv.
                        Animal animal = new Animal(age, gender, bodyType, species);
                        animal.setPet(isPet); // setting as pet if given in .csv.

                        // adding to appropriate list based on their role specified in .csv.
                        if (scenarioAttr[roleIndex].equalsIgnoreCase("pedestrian")) {
                            pedestrians.add(animal);
                        } else if (scenarioAttr[roleIndex].equalsIgnoreCase("passenger")) {
                            passengers.add(animal);
                        }
                    }
                } catch (InvalidDataFormatException e) {
                    // catching invalid data format exception
                    System.out.println(e.getMessage());
                }
            }

            /**
             * generating the last scenario that was read but not generated.
             */
            Character[] passengerChar = passengers.toArray(new Character[0]);
            Character[] pedestrianChar = pedestrians.toArray(new Character[0]);
            Scenario scenario = new Scenario(passengerChar, pedestrianChar, isLegalScenario);
            scenarioList.add(scenario);
            bufferReader.close();
        } else {
            // throwing exception when file not found.
            throw new FileNotFoundException(Constants.FILE_TO_READ_NOT_FOUND);
        }
        return scenarioList;
    }

    /**
     * Returns attribute (characteristic of character) in uppercase if present in maintained list
     * of characteristics else throws invalid characteristic exception.
     *
     * @param attr is an attribute read from file for a character
     * @param attrType is the category of the attribute read
     * @param lineNumber required to indicate line number of file being read, if any error occurs
     * @return attribute (attr) in uppercase if matched with any of the predefined ones in enums
     * @throws InvalidCharacteristicException if attribute finds no match with the predefined ones
     * in their respective enums
     */
    public String getValue(String attr, String attrType, int lineNumber)
            throws InvalidCharacteristicException {
        attr = attr.toUpperCase();
        ArrayList<String> attrList = new ArrayList<>();
        switch (attrType) {
            case "gender":
                attrList = Gender.getAllValues();
                break;
            case "profession":
                attrList = Profession.getAllValues();
                break;
            case "bodyType":
                attrList = BodyType.getAllValues();
                break;
        }

        if (!attrList.contains(attr)) {
            throw new InvalidCharacteristicException(lineNumber);
        }
        return attr;
    }

    /**
     * Presents user with an interactive mode to decide whom to save in a particular scenario.
     *
     * @param scenario is the scenario being audited by the user
     * @return decision if passenger(s) or pedestrian(s) are to be saved
     * @throws InvalidInputException for invalid input from user
     */
    public static Decision userDecide(Scenario scenario) throws InvalidInputException {
        Decision decision = null;
        System.out.print(scenario.toString());
        System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
        do {
            try {
                String userDecision = KEYIN.nextLine();
                if (userDecision.equals("passenger") || userDecision.equals("passengers")
                        || userDecision.equals("1")) {
                    decision = Decision.PASSENGERS;
                } else if (userDecision.equals("pedestrian") || userDecision.equals("pedestrians")
                        || userDecision.equals("2")) {
                    decision = Decision.PEDESTRIANS;
                } else {
                    throw new InvalidInputException("Who should be saved? (passenger(s) [1] "
                            + "or pedestrian(s) [2])");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        } while (null == decision);

        return decision;
    }

    /**
     * Logic of ethical engine to decide whom to save in a particular scenario.
     *
     * @param scenario is the scenario being audited by the engine
     * @return decision if passenger(s) or pedestrian(s) are to be saved
     */
    public static Decision decide(Scenario scenario) {
        int passengerPts = 0;

        int pedestrianPts = 0;

        if (scenario.isLegalCrossing()) {
            pedestrianPts += Constants.ONE_POINT;
        } else {
            passengerPts += Constants.THREE_POINT;
        }

        if (scenario.getPassengerCount() > scenario.getPedestrianCount()) {
            passengerPts += Math.ceil(scenario.getPassengerCount()
                    / scenario.getPedestrianCount());

        } else {
            pedestrianPts += Math.ceil(scenario.getPedestrianCount()
                    / scenario.getPassengerCount());
        }

        passengerPts = calculatePoints(Character.getCharacterArrayList(scenario.getPassengers()),
                passengerPts,
                scenario.getPassengerCount());

        pedestrianPts = calculatePoints(Character.getCharacterArrayList(scenario.getPedestrians()),
                pedestrianPts,
                scenario.getPedestrianCount());

        if (passengerPts > pedestrianPts) {
            return Decision.PASSENGERS;
        }
        return Decision.PEDESTRIANS;
    }

    /**
     * Extended logic of ethical engine to decide whom to save in a particular scenario.
     *
     * @param characterList is the list of characters of a group (passenger(s)/pedestrian(s)) who
     * are being evaluated based on their characteristics for engine to decide who to save
     * @param points initial points of the group (passenger(s)/pedestrian(s))
     * @param characterCount is the number of group members in the group
     * @return the calculated aggregated points of all the group members
     */
    public static int calculatePoints(ArrayList<Character> characterList,
            int points, int characterCount) {
        /**
         * A point system is employed to determine who is more valuable for society to be saved.
         */
        boolean hasCriminal = false;
        boolean hasYou = false;
        int deduct = 0;
        /**
         * Each character is analysed for it's characteristics and based on those either points
         * are added or reduced.
         */
        for (Character character : characterList) {
            if (character instanceof Person) {
                Person person = (Person) character;
                if (person.getBodyType() == BodyType.HANDICAP) {
                    points += Constants.ONE_POINT;
                }

                if (person.getAgeCategory() == AgeCategory.BABY
                        || person.getAgeCategory() == AgeCategory.CHILD) {
                    points += Constants.TWO_POINT;
                } else if (person.getAgeCategory() == AgeCategory.ADULT) {
                    points += Constants.ONE_POINT;
                }

                if (person.getProfession() == Profession.DOCTOR
                        || person.getProfession() == Profession.NGOWORKER
                        || person.getProfession() == Profession.EDUCATIONIST
                        || person.getProfession() == Profession.POLITICIAN) {
                    points += Constants.FIVE_POINT;
                } else if (person.getProfession() == Profession.CEO
                        || person.getProfession() == Profession.JOURNALIST) {
                    points += Constants.TWO_POINT;
                } else if (person.getProfession() == Profession.CRIMINAL) {
                    deduct += Constants.FIVE_POINT;
                    hasCriminal = true;
                }

                if (person.isPregnant()) {
                    points += Constants.FIVE_POINT;
                } else if (person.isYou() && 1 == characterCount) {
                    deduct += Constants.THREE_POINT;
                    hasYou = true;
                }
            } else if (character instanceof Animal) {
                Animal animal = (Animal) character;

                if (animal.getSpecies().equals(Specie.ENDANGERED.name())) {
                    points += Constants.TWO_POINT;
                }
                if (animal.isPet()) {
                    points += Constants.ONE_POINT;
                }
            }
        }

        if ((hasCriminal && 1 == characterCount) || (points < deduct)) {
            deduct = points;
        }

        points -= deduct;

        return points;
    }
}
