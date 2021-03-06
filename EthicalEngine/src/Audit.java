
import ethicalengine.Animal;
import ethicalengine.ScenarioGenerator;
import ethicalengine.Scenario;
import ethicalengine.Character;
import ethicalengine.Constants;
import ethicalengine.Person;
import ethicalengine.Person.Profession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Structure to maintain statistics of characteristics (attributes) of characters involved in
 * scenarios for audit report.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 19-Jun-2020
 */


class AttributeStat {

    private String attribute;
    private int numSurvivors;
    private int numTotal;

    /**
     * Creates instance of AttributeStats with a new attribute.
     *
     * @param attribute sets the attribute (characteristic) to display in audit report
     */
    public AttributeStat(String attribute) {
        this.attribute = attribute;
        this.numSurvivors = 0;
        this.numTotal = 0;
    }

    /**
     * Returns attribute (characteristic) to display in audit report
     *
     * @return attribute (characteristic) to display in audit report
     */
    public String getAttribute() {
        return this.attribute;
    }

    /**
     * Returns the survivors ratio of characters with the characteristics (attribute) of the
     * instance.
     *
     * @return the survivors ratio of characters with the characteristics (attribute) of the
     * instance.
     */
    public float getRatio() {
        return (float) ((float) numSurvivors / numTotal);
    }

    /**
     * Updates the counts for number of survivors and total number of characters with
     * characteristics (attribute) of the instance.
     */
    public void updateAttrStats() {
        ++this.numSurvivors;
        ++this.numTotal;
    }

    /**
     * Updates the count for only the total number of characters with characteristics (attribute)
     * of the instance.
     */
    public void updateAttrTotal() {
        ++this.numTotal;
    }

    /**
     * Returns readable format for (characteristics) attribute and corresponding survival ratio.
     *
     * @return readable format for (characteristics) attribute and corresponding survival ratio.
     */
    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(1);
        formatter.setMinimumFractionDigits(1);
        return attribute + ": " + formatter.format(getRatio());
    }

}

/**
 * Runs scenarios through EthicalEngine decision maker and maintains scenario list and resulting
 * audit report.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class Audit {

    private String auditType;

    private ScenarioGenerator scenarioGenerator;

    private ArrayList<Scenario> scenarioList;
    private int ageSum;
    private int totalPerson;
    private ArrayList<AttributeStat> attributeStatList;
    private int auditScenarioCount;

    /**
     * Creates instance for auditing randomly generated scenarios.
     */
    Audit() {
        scenarioGenerator = new ScenarioGenerator();
        this.scenarioList = new ArrayList<>();
        this.setup();
    }

    /**
     * Creates instance for auditing given scenarios.
     *
     * @param scenarios are the scenarios provided by engine after reading an external file.
     */
    Audit(ArrayList<Scenario> scenarios) {
        this.scenarioList = scenarios;
        this.setup();
    }

    /**
     * Initialisation of variables required for auditing scenarios and printing their results.
     */
    private void setup() {
        this.auditType = Constants.DEFAULT_AUDIT_TYPE;
        attributeStatList = new ArrayList<>();
        ageSum = 0;
        totalPerson = 0;
        auditScenarioCount = 0;
    }

    /**
     * Sets the audit type to given name.
     *
     * @param name is the value for audit type
     */
    public void setAuditType(String name) {
        this.auditType = name;
    }

    /**
     * Returns audit type of the instance.
     *
     * @return audit type of the instance
     */
    public String getAuditType() {
        return auditType;
    }

    /**
     * Calls scenario generator to generate random scenarios followed by calls to ethical engine
     * functions to audit them.
     *
     * @param runs is the number of random scenarios to be generated
     */
    public void run(int runs) {
        for (int i = 0; i < runs; i++) {
            Scenario scenario = scenarioGenerator.generate();
            this.scenarioList.add(scenario);
            EthicalEngine.Decision saved = EthicalEngine.decide(scenario);
            initStat(scenario, saved);
        }
        this.auditScenarioCount = this.scenarioList.size();
    }

    /**
     * Calls ethical engine functions to audit scenarios generated by reading an external file.
     */
    public void run() {
        for (Scenario scenario : scenarioList) {
            EthicalEngine.Decision saved = EthicalEngine.decide(scenario);
            initStat(scenario, saved);
        }
        this.auditScenarioCount = this.scenarioList.size();
    }

    /**
     * Calls interactive ethical engine functions to let users audit scenarios
     *
     * @param readfileFlag indicates if the scenarios are generated by reading an external file.
     * if readfileFlag is false, then scenarios are to be generated randomly.
     * @return if scenarios are exhausted (happens only when limited scenarios are generated from
     * reading an external file).
     */
    public boolean runByUser(boolean readfileFlag) {
        int size = this.auditScenarioCount + Constants.AUDIT_STREAK;
        if (readfileFlag && this.scenarioList.size() < size) {
            size = this.scenarioList.size();
        }

        for (int i = this.auditScenarioCount; i < size; i++) {
            Scenario scenario;
            if (readfileFlag) {
                scenario = this.scenarioList.get(i);
            } else {
                scenario = scenarioGenerator.generate();
                this.scenarioList.add(scenario);
            }
            try {
                EthicalEngine.Decision saved = EthicalEngine.userDecide(scenario);
                initStat(scenario, saved);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        this.auditScenarioCount = size;
        if (readfileFlag) {
            return this.scenarioList.size() == size;
        }
        return false;
    }

    /**
     * initStat is the starting point for building audit report
     *
     * @param scenario is the scenario audited
     * @param saved is the decision (passenger(s)/pedestrian(s)) for the scenario.
     */
    private void initStat(Scenario scenario, EthicalEngine.Decision saved) {
        boolean passengerSaved = (EthicalEngine.Decision.PASSENGERS == saved);
        boolean pedestrianSaved = (EthicalEngine.Decision.PEDESTRIANS == saved);
        String signal = scenario.isLegalCrossing() ? "green" : "red";

        this.updateStat(Character.getCharacterArrayList(scenario.getPassengers()),
                passengerSaved, signal);
        this.updateStat(Character.getCharacterArrayList(scenario.getPedestrians()),
                pedestrianSaved, signal);
    }

    /**
     * Finds (attributes) characteristics of characters to be considered for audit report.
     *
     * @param characters is the list of characters of a group (passenger(s)/pedestrian(s)) whose
     * attributes (characteristics) are saved with corresponding survival ratio.
     * @param saved indicates if the group is saved or not.
     */
    private void updateStat(ArrayList<Character> characters, boolean saved, String signal) {
        for (Character character : characters) {
            if (character instanceof Person) {
                Person person = (Person) character;
                if (saved) {
                    ++this.totalPerson;
                    this.ageSum = this.ageSum + person.getAge();
                }
                this.updateAttributeStat(person.getAgeCategory().toString(), saved);
                this.updateAttributeStat(person.getBodyType().toString(), saved);
                if (person.getGender() != Character.Gender.UNKNOWN) {
                    this.updateAttributeStat(person.getGender().toString(), saved);
                }
                if (!person.getProfession().toString().equals(Profession.NONE.toString())) {
                    this.updateAttributeStat(person.getProfession().toString(), saved);
                }
                if (person.isYou()) {
                    this.updateAttributeStat("you", saved);
                }
                if (person.isPregnant()) {
                    this.updateAttributeStat("pregnant", saved);
                }
                this.updateAttributeStat("person", saved);
            } else if (character instanceof Animal) {
                Animal animal = (Animal) character;
                this.updateAttributeStat(animal.getSpecies(), saved);

                if (animal.isPet()) {
                    this.updateAttributeStat("pet", saved);
                }
                this.updateAttributeStat("animal", saved);
            }
            this.updateAttributeStat(signal, saved);
        }
    }

    /**
     * Adds or updates survival ratio of characteristics (attributes) of the characters.
     *
     * @param attribute is the characteristics of the character being considered
     * @param saved denotes if the character with the characteristics was saved.
     */
    private void updateAttributeStat(String attribute, boolean saved) {
        boolean foundFlag = false;

        for (AttributeStat attributeStat : this.attributeStatList) {
            if (attributeStat.getAttribute().equals(attribute)) {
                foundFlag = true;
                if (saved) {
                    attributeStat.updateAttrStats();
                } else {
                    attributeStat.updateAttrTotal();
                }
                break;
            }
        }

        if (!foundFlag) {
            AttributeStat attributeStat = new AttributeStat(attribute);
            if (saved) {
                attributeStat.updateAttrStats();
            } else {
                attributeStat.updateAttrTotal();
            }
            this.attributeStatList.add(attributeStat);
        }
    }

    /**
     * Sorts attributes (characteristics) in descending order of their survival ratio.
     */
    public void sortAttributes() {
        /* using insertion sort algorithm to sorting (attributes) characteristics. */
        for (int i = 1; i < this.attributeStatList.size(); i++) {
            AttributeStat key = this.attributeStatList.get(i);
            int j = i - 1;
            float leftPercentage = this.attributeStatList.get(j).getRatio();
            float rightPercentage = key.getRatio();

            /* Move attributes in the range [0..i-1], that have lesser survival ratio
               than that of key, to one position ahead of their current position */
            while (j >= 0 && leftPercentage < rightPercentage) {
                this.attributeStatList.set(j + 1, this.attributeStatList.get(j));
                j = j - 1;
                if (j >= 0) {
                    leftPercentage = this.attributeStatList.get(j).getRatio();
                }
            }
            while (j >= 0 && this.attributeStatList.get(j).getRatio() == key.getRatio()
                    && this.attributeStatList.get(j).getAttribute().compareTo(key.getAttribute()) > 0) {
                this.attributeStatList.set(j + 1, this.attributeStatList.get(j));
                j = j - 1;
            }
            attributeStatList.set(j + 1, key);
        }
    }

    /**
     * Returns readable format of audit report.
     *
     * @return readable format of audit report.
     */
    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(1);
        formatter.setMinimumFractionDigits(1);

        String string = "======================================\n"
                + "# " + this.getAuditType() + " Audit\n"
                + "======================================\n";
        string = string + "- % SAVED AFTER " + this.auditScenarioCount + " RUNS\n";
        for (AttributeStat attributeStat : this.attributeStatList) {
            string = string + attributeStat.toString() + "\n";
        }
        string = string + "--\n"
                + "average age: " + formatter.format((float) ageSum / totalPerson) + "\n";
        return string;
    }

    /**
     * Prints the audit report on console.
     */
    public void printStatistic() {
        this.sortAttributes();
        System.out.print(this.toString());
    }

    /**
     * Prints the audit report to a specified file.
     *
     * @param filepath of the file to be written in
     */
    public void printToFile(String filepath) {
        this.printStatistic();
        FileOutputStream outStream;
        File file;
        try {
            // create a file of given name in case not already present in the file path
            file = new File(filepath);
            // store audit details in file
            outStream = new FileOutputStream(file, true);
            outStream.write(this.toString().getBytes());

        } catch (IOException e) {
            System.out.println(Constants.PRINT_ERROR_MSG);
        }
    }
}
