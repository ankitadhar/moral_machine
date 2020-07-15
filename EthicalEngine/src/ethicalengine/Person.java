package ethicalengine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Person class inherits properties from abstract character class. Instances of this class
 * contribute in forming pedestrian/passenger lists.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class Person extends Character {

    /**
     * Range divides persons in different age groups
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
     */
    private enum Range {

        BABY(Constants.AGE_MIN, Constants.AGE_BABY_MAX),
        CHILD(Constants.AGE_CHILD_MIN, Constants.AGE_CHILD_MAX),
        ADULT(Constants.AGE_ADULT_MIN, Constants.AGE_ADULT_MAX),
        SENIOR(Constants.AGE_SENIOR_MIN, Constants.AGE_MAX),
        OTHER(0, -1); // This range can never exist, but it is necessary
        // in order to prevent a NullPointerException from
        // being thrown while we switch

        private final int MIN_VALUE;
        private final int MAX_VALUE;

        private Range(int minValue, int maxValue) {
            this.MIN_VALUE = minValue;
            this.MAX_VALUE = maxValue;
        }

        public static Range from(int age) {
            return Arrays.stream(Range.values())
                    .filter(range -> age >= range.MIN_VALUE && age <= range.MAX_VALUE)
                    .findAny()
                    .orElse(OTHER);
        }
    }

    /**
     * Professions that can be specified
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
     */
    public enum Profession {
        /**
         * Doctor as profession
         */
        DOCTOR,
        /**
         * CEO as profession
         */
        CEO,
        /**
         * Criminal as profession
         */
        CRIMINAL,
        /**
         * homeless as profession
         */
        HOMELESS,
        /**
         * unemployed as profession
         */
        UNEMPLOYED,
        /**
         * politician as profession
         */
        POLITICIAN,
        /**
         * ngo worker as profession
         */
        NGOWORKER,
        /**
         * educationist as profession
         */
        EDUCATIONIST,
        /**
         * journalist as profession
         */
        JOURNALIST,
        /**
         * actor as profession
         */
        ACTOR,
        /**
         * Unknown as the default profession if not specified for an adult
         */
        UNKNOWN,
        /**
         * none as profession for non-adults
         */
        NONE;

        /**
         * Returns enum value at the ordinal (index).
         *
         * @param ord is the ordinal or index of the enum value requested
         * @return enum value at the ordinal
         */
        public static Profession byOrdinal(int ord) {
            return Profession.values()[ord - 1];
        }

        /**
         * Returns enum values in string array format.
         *
         * @return list of enum values in string array format
         */
        public static ArrayList<String> getAllValues() {
            ArrayList<String> values = new ArrayList<>();
            for (int i = 0; i < Profession.values().length - 1; i++) {
                Profession value = Profession.values()[i];
                values.add(value.name());
            }
            return values;
        }

        /**
         * Returns enum name in lower case.
         *
         * @return enum value as string
         */
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private Profession profession;
    private boolean pregnant;
    private boolean you;

    public Person() {
        super();
        this.profession = Constants.DEFAULT_PROFESSION;

        // profession for anyone not adult is none.
        if (AgeCategory.ADULT != this.getAgeCategory()) {
            this.profession = Profession.NONE;
        }

        this.pregnant = false;
    }

    public Person(int age, Gender gender, BodyType bodytype) {
        super(age, gender, bodytype);
        this.profession = Constants.DEFAULT_PROFESSION;

        // profession for anyone not adult is none.
        if (AgeCategory.ADULT != this.getAgeCategory()) {
            this.profession = Profession.NONE;
        }

        this.pregnant = false;
    }

    /**
     * Creates an instance of person considering several parameters.
     *
     * @param age is the age of the person
     * @param profession is the profession of the person
     * @param gender is the gender of the person
     * @param bodytype is the body type of the person
     * @param pregnant indicates if the person is pregnant
     */
    public Person(int age, Profession profession, Gender gender, BodyType bodytype,
            boolean pregnant) {
        super(age, gender, bodytype);
        this.profession = profession;
        this.pregnant = false;

        // profession for anyone not adult is none.
        if (AgeCategory.ADULT != this.getAgeCategory()) {
            this.profession = Profession.NONE;
        }
        if (Gender.FEMALE == gender && AgeCategory.ADULT == this.getAgeCategory()) {
            this.pregnant = pregnant;
        }
    }

    /**
     * Creates an instance of person from another instance of person.
     *
     * @param otherPerson is the other person to be cloned
     */
    public Person(Person otherPerson) {
        super(otherPerson);
        this.profession = otherPerson.profession;
        this.pregnant = otherPerson.pregnant;
    }

    /**
     * Returns profession of the person instance
     *
     * @return profession of the person instance
     */
    public Profession getProfession() {
        if (this.getAgeCategory() != AgeCategory.ADULT) {
            return Profession.NONE;
        }
        return profession;
    }

    /**
     * Sets profession of the person instance
     *
     * @param profession is the value of the profession to be set
     */
    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    /**
     * Returns true if the person is pregnant
     *
     * @return if the person is pregnant
     */
    public boolean isPregnant() {
        return pregnant;
    }

    /**
     * Sets if the person is pregnant
     *
     * @param pregnant is the value to be set
     */
    public void setPregnant(boolean pregnant) {
        if (this.getGender() == Gender.FEMALE && AgeCategory.ADULT == this.getAgeCategory()) {
            this.pregnant = pregnant;
        } else {
            this.pregnant = false;
        }
    }

    /**
     * Returns true if the person instance is the user.
     *
     * @return if the person instance is the user.
     */
    public boolean isYou() {
        return you;
    }

    /**
     * Sets the person instance as user with a true valued argument or not user with a false
     * valued argument.
     *
     * @param you is the value to be set
     */
    public void setAsYou(boolean you) {
        this.you = you;
    }

    /**
     * AgeCategory sets category of age for the person based on person's age
     *
     * @return age category [baby,child,adult,senior]
     */
    public AgeCategory getAgeCategory() {
        AgeCategory ageCategory;

        switch (Range.from(this.getAge())) {

            case BABY:
                ageCategory = AgeCategory.BABY;
                break;

            case CHILD:
                ageCategory = AgeCategory.CHILD;
                break;

            case ADULT:
                ageCategory = AgeCategory.ADULT;
                break;

            case SENIOR:
                ageCategory = AgeCategory.SENIOR;
                break;

            default:
                ageCategory = AgeCategory.SENIOR;
        }
        return ageCategory;
    }

    /**
     * Returns readable format of person instance
     *
     * @return readable format of person instance
     */
    @Override
    public String toString() {
        String string = "";

        if (this.you) {
            string = "you ";
        }

        string = string + this.getBodyType() + " " + this.getAgeCategory() + " ";

        if (this.getProfession() != Profession.NONE) {
            string = string + this.getProfession() + " ";
        }

        string = string + this.getGender();

        if (this.isPregnant()) {
            string = string + " pregnant";
        }
        return string;
    }
}
