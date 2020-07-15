package ethicalengine;

import ethicalengine.Animal.Specie;
import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.Person.Profession;
import java.util.Random;
import java.util.ArrayList;

/**
 * ScenarioGenerator is used for randomly generating scenarios.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class ScenarioGenerator {

    /**
     * default number of members in either of the groups (passenger(s)/pedestrian(s)) when using
     * scenario generator.
     */
    public static final int DEFAULT_GROUP_MEMBER_MIN_COUNT = 1;
    public static final int DEFAULT_GROUP_MEMBER_MAX_COUNT = 5;

    private long seed;
    private int passengerCountMin;
    private int passengerCountMax;
    private int pedestrianCountMin;
    private int pedestrianCountMax;
    private boolean hasYou;

    private Random random;

    /**
     * Creates an instance of ScenarioGenerator with given seed value and default minimum and
     * maximum values passenger and pedestrian counts.
     *
     * @param seed is the seed value for the generator
     */
    public ScenarioGenerator(long seed) {
        this.seed = seed;
        this.passengerCountMin = DEFAULT_GROUP_MEMBER_MIN_COUNT;
        this.passengerCountMax = DEFAULT_GROUP_MEMBER_MAX_COUNT;
        this.pedestrianCountMin = DEFAULT_GROUP_MEMBER_MIN_COUNT;
        this.pedestrianCountMax = DEFAULT_GROUP_MEMBER_MAX_COUNT;
        this.hasYou = false;
        random = new Random(seed);
    }

    /**
     * Creates an instance of ScenarioGenerator with given seed value and given minimum and
     * maximum values passenger and pedestrian counts.
     *
     * @param seed is the seed value for the generator
     * @param passengerCountMinimum is the minimum count of passengers
     * @param passengerCountMaximum is the maximum count of passengers
     * @param pedestrianCountMinimum is the minimum count of pedestrians
     * @param pedestrianCountMaximum is the maximum count of pedestrians
     */
    public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum,
            int pedestrianCountMinimum, int pedestrianCountMaximum) {
        this.seed = seed;
        this.passengerCountMin = passengerCountMinimum;
        this.passengerCountMax = passengerCountMaximum;
        this.pedestrianCountMin = pedestrianCountMinimum;
        this.pedestrianCountMax = pedestrianCountMaximum;
        this.hasYou = false;
        random = new Random(seed);
    }

    /**
     * Creates an instance of ScenarioGenerator with random seed value and default minimum and
     * maximum values passenger and pedestrian counts.
     */
    public ScenarioGenerator() {
        random = new Random();
        this.seed = random.nextLong();
        this.passengerCountMin = DEFAULT_GROUP_MEMBER_MIN_COUNT;
        this.passengerCountMax = DEFAULT_GROUP_MEMBER_MAX_COUNT;
        this.pedestrianCountMin = DEFAULT_GROUP_MEMBER_MIN_COUNT;
        this.pedestrianCountMax = DEFAULT_GROUP_MEMBER_MAX_COUNT;
        this.hasYou = false;
        random = new Random();
    }

    /**
     * Returns seed value
     *
     * @return seed value.
     */
    public long getSeed() {
        return seed;
    }

    /**
     * Sets seed value
     *
     * @param seed is the value to be set
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Returns minimum passenger count
     *
     * @return minimum passenger count
     */
    public int getPassengerCountMin() {
        return passengerCountMin;
    }

    /**
     * Sets minimum passenger count
     *
     * @param passengerCountMin is the value to set
     */
    public void setPassengerCountMin(int passengerCountMin) {
        this.passengerCountMin = passengerCountMin;
    }

    /**
     * Returns maximum passenger count
     *
     * @return maximum passenger count
     */
    public int getPassengerCountMax() {
        return passengerCountMax;
    }

    /**
     * Sets maximum passenger count
     *
     * @param passengerCountMax is the value to set
     */
    public void setPassengerCountMax(int passengerCountMax) {
        this.passengerCountMax = passengerCountMax;
    }

    /**
     * Returns minimum pedestrian count
     *
     * @return minimum pedestrian count
     */
    public int getPedestrianCountMin() {
        return pedestrianCountMin;
    }

    /**
     * Sets minimum pedestrian count
     *
     * @param pedestrianCountMin is the value to set
     */
    public void setPedestrianCountMin(int pedestrianCountMin) {
        this.pedestrianCountMin = pedestrianCountMin;
    }

    /**
     * Returns maximum pedestrian count
     *
     * @return maximum pedestrian count
     */
    public int getPedestrianCountMax() {
        return pedestrianCountMax;
    }

    /**
     * Sets maximum pedestrian count
     *
     * @param pedestrianCountMax is the value to set
     */
    public void setPedestrianCountMax(int pedestrianCountMax) {
        this.pedestrianCountMax = pedestrianCountMax;
    }

    /**
     * Generates random person
     *
     * @return random person thus created
     */
    public Person getRandomPerson() {
        int min = 1;
        int randomAge;
        int rand;
        boolean isPregnant = false;
        boolean you = false;
        Profession randomProfession;
        Gender randomGender;
        BodyType randomBodyType;
        Person person;

        randomAge = random.nextInt(Constants.AGE_MAX + 1 - min) + min;

        rand = random.nextInt(Profession.getAllValues().size() + 1 - min) + min;
        randomProfession = Profession.byOrdinal(rand);

        rand = random.nextInt(Gender.getAllValues().size() + 1 - min) + min;
        randomGender = Gender.byOrdinal(rand);

        rand = random.nextInt(BodyType.getAllValues().size() + 1 - min) + min;
        randomBodyType = BodyType.byOrdinal(rand);

        if (Gender.FEMALE == randomGender) {
            isPregnant = random.nextBoolean();
        }

        person = new Person(randomAge, randomProfession, randomGender, randomBodyType, isPregnant);

        if (!this.hasYou) {
            you = random.nextBoolean();
            person.setAsYou(you);
            this.hasYou = you;
        }

        return person;
    }

    /**
     * Generates random animal
     *
     * @return random animal thus created
     */
    public Animal getRandomAnimal() {
        int min = 1;
        int rand;
        int randomAge = Constants.DEFAULT_AGE;
        Gender randomGender = Constants.DEFAULT_GENDER;
        BodyType randomBodyType = Constants.DEFAULT_BODYTYPE;
        String randomSpecie;
        Animal animal;

        rand = random.nextInt(Specie.values().length + 1 - min) + min;
        randomSpecie = Specie.byOrdinal(rand).toString();

        animal = new Animal(randomAge, randomGender, randomBodyType, randomSpecie);
        animal.setPet(random.nextBoolean());

        return animal;
    }

    /**
     * Generates random number of characters with a logic where there will be at least one person
     * among passengers
     *
     * @param min is the minimum character count required
     * @param max is the maximum character count required
     * @param isPassenger denotes if the character list is to be generated for passenger
     * @return list of characters
     */
    private ArrayList<Character> getCharacter(int min, int max, boolean isPassenger) {
        ArrayList<Character> characters = new ArrayList<Character>();
        int count;
        int animalCount = 0;
        int personCount;

        count = random.nextInt(max + 1 - min) + min;

        personCount = random.nextInt(count + 1);
        if (isPassenger) {
            personCount = random.nextInt(count) + 1;
        }

        animalCount = count - personCount;

        for (int i = 0; i < personCount; i++) {
            Person person = getRandomPerson();
            characters.add(person);
        }

        for (int i = 0; i < animalCount; i++) {
            Animal animal = getRandomAnimal();
            characters.add(animal);
        }

        return characters;
    }

    /**
     * Generates random scenarios
     *
     * @return a random scenario that is just generated
     */
    public Scenario generate() {
        boolean leagalCrossing = random.nextBoolean();

        ArrayList<Character> passengers = new ArrayList<Character>();
        ArrayList<Character> pedestrians = new ArrayList<Character>();

        passengers = this.getCharacter(this.passengerCountMin, this.passengerCountMax, true);
        pedestrians = this.getCharacter(this.pedestrianCountMin, this.pedestrianCountMax, false);

        Character[] passengerChar = passengers.toArray(new Character[0]);
        Character[] pedestrianChar = pedestrians.toArray(new Character[0]);
        Scenario scenario = new Scenario(passengerChar, pedestrianChar, true);
        scenario.setLegalCrossing(leagalCrossing);

        return scenario;
    }
}
