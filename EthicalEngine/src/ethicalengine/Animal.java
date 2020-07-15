package ethicalengine;

/**
 * Animal class inherits properties from abstract character class.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class Animal extends Character {

    /**
     * species can be specified even out of this list; This list is used for random generation of
     * animals.
     *
     * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
     */
    public enum Specie {
        /**
         * cat as specie
         */
        CAT,
        /**
         * dog as specie
         */
        DOG,
        /**
         * bird as specie
         */
        BIRD,
        /**
         * rabbit as specie
         */
        RABBIT,
        /**
         * hamster as specie
         */
        HAMSTER,
        /**
         * turtle as specie
         */
        TURTLE,
        /**
         * endangered as specie group
         */
        ENDANGERED;

        /**
         * Returns enum value at the ordinal (index).
         *
         * @param ord is the ordinal or index of the enum value requested
         * @return enum value at the ordinal
         */
        public static Specie byOrdinal(int ord) {
            return Specie.values()[ord - 1];
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

    private String species;
    private boolean pet;

    /**
     * Creates an instance of animal
     *
     * @param species set species for the animal instance being generated.
     */
    public Animal(String species) {
        this.species = species;
    }

    /**
     * Creates an instance of animal from another animal
     *
     * @param otherAnimal is the animal whose data is being copied
     */
    public Animal(Animal otherAnimal) {
        super(otherAnimal);
        this.species = otherAnimal.species;
    }

    public Animal() {
        super();
        this.species = Constants.DEFAULT_SPECIES;
    }

    /**
     * Creates an instance of animal considering several parameters.
     *
     * @param age of the animal (used by character class)
     * @param gender of the animal (used by character class)
     * @param bodyType of the animal (used by character class)
     * @param species of the animal (used by character class)
     */
    public Animal(int age, Gender gender, BodyType bodyType, String species) {
        super(age, gender, bodyType);
        this.species = species;
    }

    /**
     * Returns species of the animal instance
     *
     * @return species of the animal generated
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Sets species of the animal instance
     *
     * @param species is the value to which animal species is to be set
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Returns true if the animal is a pet
     *
     * @return if the animal is a pet
     */
    public boolean isPet() {
        return pet;
    }

    /**
     * Sets animal as pet with a true valued argument or not as a pet with a false valued argument
     *
     * @param pet is the value to be set to animal as pet.
     */
    public void setPet(boolean pet) {
        this.pet = pet;
    }

    /**
     * Returns readable format of animal instance
     *
     * @return readable format of animal instance
     */
    @Override
    public String toString() {
        String string = this.species.toLowerCase();

        if (this.pet) {
            string = string + " is pet";
        }

        return string;
    }
}
