package ethicalengine;

import java.util.ArrayList;

/**
 * Character is an abstract class providing foundation for other classes to form pedestrians or
 * passengers.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
abstract public class Character {

    /**
     * age category categorises persons in these categories based on their age
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 19-Jun-2020
     */
    public enum AgeCategory {
        /**
         * age category - baby
         */
        BABY,
        /**
         * age category - child
         */
        CHILD,
        /**
         * age category - adult
         */
        ADULT,
        /**
         * age category - senior
         */
        SENIOR;

        /**
         * Returns enum name in lower case.
         *
         * @return enum value in readable format
         */
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Body types that can be specified
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
     */
    public enum BodyType {
        /**
         * Average body-type
         */
        AVERAGE,
        /**
         * Athletic body-type
         */
        ATHLETIC,
        /**
         * Over-weight body-type
         */
        OVERWEIGHT,
        /**
         * Handicap body-type
         */
        HANDICAP,
        /**
         * Unspecified body-type is default body-type if not specified
         */
        UNSPECIFIED;

        /**
         * Returns enum value at the ordinal (index).
         *
         * @param ord is the ordinal or index of the enum value requested
         * @return enum value at the ordinal
         */
        public static BodyType byOrdinal(int ord) {
            return BodyType.values()[ord - 1];
        }

        /**
         * Returns enum values in string array format.
         *
         * @return list of enum values in string array format
         */
        public static ArrayList<String> getAllValues() {
            ArrayList<String> values = new ArrayList<>();
            for (BodyType value : BodyType.values()) {
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

    /**
     * Genders that can be specified
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
     */
    public enum Gender {
        /**
         * Male
         */
        MALE,
        /**
         * Female
         */
        FEMALE,
        /**
         * Unknown is the default gender of a person if not specified
         */
        UNKNOWN;

        /**
         * Returns enum value at the ordinal (index).
         *
         * @param ord is the ordinal or index of the enum value requested
         * @return enum value at the ordinal
         */
        public static Gender byOrdinal(int ord) {
            return Gender.values()[ord - 1];
        }

        /**
         * Returns enum values in string array format.
         *
         * @return list of enum values in string array format
         */
        public static ArrayList<String> getAllValues() {
            ArrayList<String> values = new ArrayList<>();
            for (Gender value : Gender.values()) {
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

    private int age;
    private Gender gender;
    private BodyType bodyType;

    /**
     * Creates an instance of child classes of character
     */
    Character() {
        this.age = Constants.DEFAULT_AGE;
        this.gender = Constants.DEFAULT_GENDER;
        this.bodyType = Constants.DEFAULT_BODYTYPE;
    }

    /**
     * Creates an instance of child classes of character considering several parameters.
     *
     * @param age of the character
     * @param gender of the character
     * @param bodyType of the character
     */
    Character(int age, Gender gender, BodyType bodyType) {
        this.age = age;
        this.gender = gender;
        this.bodyType = bodyType;
    }

    /**
     * Creates an instance of child classes of character considering another character.
     *
     * @param c is the other character.
     */
    Character(Character c) {
        this.age = c.age;
        this.gender = c.gender;
        this.bodyType = c.bodyType;
    }

    /**
     * Returns age of the character
     *
     * @return age of the character
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age of the character.
     *
     * @param age is the value to which class variable is set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns gender of the character
     *
     * @return gender of the character.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets gender of the character
     *
     * @param gender is the value to which class variable is set.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Returns body type of the character.
     *
     * @return body type of the character.
     */
    public BodyType getBodyType() {
        return bodyType;
    }

    /**
     * converts array of characters to arraylist of characters.
     *
     * @param characters is the array of characters
     * @return arraylist of characters
     */
    public static ArrayList<Character> getCharacterArrayList(Character[] characters) {
        ArrayList<Character> characterArrayList = new ArrayList<>();

        for (Character character : characters) {
            if (character instanceof Person) {
                Person person = (Person) character;
                characterArrayList.add(person);
            } else if (character instanceof Animal) {
                Animal animal = (Animal) character;
                characterArrayList.add(animal);
            }
        }

        return characterArrayList;
    }

    /**
     * Sets body type of the character
     *
     * @param bodyType is the value to which class variable is set.
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
