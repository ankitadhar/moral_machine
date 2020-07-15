package ethicalengine;

import java.util.ArrayList;

/**
 * Scenario class generates scenarios based on inputs given
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class Scenario {

    private ArrayList<Character> passengers;
    private ArrayList<Character> pedestrians;
    private boolean legalCrossing;
    private boolean addedYou;

    /**
     * Creates scenarios with passengers, pedestrians and legalCrossing information
     *
     * @param passengers are the passengers of the car
     * @param pedestrians are the pedestrians crossing street
     * @param legalCrossing indicates if the pedestrians were crossing the road when the light is
     * green for pedestrians
     */
    public Scenario(Character[] passengers, Character[] pedestrians,
            boolean legalCrossing) {
        this.passengers = Character.getCharacterArrayList(passengers);
        if (this.hasYouInCar()) {
            this.checkForYou(this.passengers);
        }
        this.pedestrians = Character.getCharacterArrayList(pedestrians);
        if (this.hasYouInLane()) {
            this.checkForYou(this.pedestrians);
        }
        this.legalCrossing = legalCrossing;
    }

    /**
     * Returns count of passengers in the scenario.
     *
     * @return count of passengers in the scenario.
     */
    public int getPassengerCount() {
        return this.passengers.size();
    }

    /**
     * Returns count of pedestrians in the scenario.
     *
     * @return count of pedestrians in the scenario.
     */
    public int getPedestrianCount() {
        return this.pedestrians.size();
    }

    /**
     * Returns passenger list of the scenario.
     *
     * @return passenger list of the scenario.
     */
    public Character[] getPassengers() {
        Character[] passengerChar = passengers.toArray(new Character[0]);
        return passengerChar;
    }

    /**
     * Returns pedestrian list of the scenario.
     *
     * @return pedestrian list of the scenario.
     */
    public Character[] getPedestrians() {
        Character[] pedestrianChar = pedestrians.toArray(new Character[0]);
        return pedestrianChar;
    }

    /**
     * Returns whether the pedestrians were crossing the road legally
     *
     * @return whether the pedestrians were crossing the road legally
     */
    public boolean isLegalCrossing() {
        return legalCrossing;
    }

    /**
     * Sets legal crossing
     *
     * @param legalCrossing is the value to which class variable is to be set
     */
    public void setLegalCrossing(boolean legalCrossing) {
        this.legalCrossing = legalCrossing;
    }

    /**
     * Checks if user exists among any of the participating groups
     *
     * @param characterList is one of the participating groups. (pedestrian(s)/passenger(s))
     */
    private void checkForYou(ArrayList<Character> characterList) {
        for (Character character : characterList) {
            if (character instanceof Person) {
                Person person = (Person) character;
                if (person.isYou() && !this.addedYou) {
                    this.addedYou = true;
                } else if (person.isYou()) {
                    person.setAsYou(false);
                }
            }
        }
    }

    /**
     * Provides support to hasYouInCar and hasYouInLane
     *
     * @param characterList is list of characters in Lane (pedestrians) or Car (passengers)
     * @return true if user is present in the character list
     */
    private boolean hasYouIn(ArrayList<Character> characterList) {
        boolean foundFlag = false;
        for (Character character : characterList) {
            if (character instanceof Person) {
                Person person = (Person) character;
                if (person.isYou()) {
                    foundFlag = true;
                    break;
                }
            }
        }
        return foundFlag;
    }

    /**
     * Checks if user is present in car.
     *
     * @return true if user present in car
     */
    public boolean hasYouInCar() {
        return hasYouIn(this.passengers);
    }

    /**
     * Checks if user is present in Lane.
     *
     * @return true if user present in lane
     */
    public boolean hasYouInLane() {
        return hasYouIn(this.pedestrians);
    }

    /**
     * Returns details of characters in character list provided in string format.
     *
     * @param characterList is one of the participating groups. (pedestrian(s)/passenger(s))
     * @return a string with multiple lines, where each line has readable data of a person/animal
     * instance.
     */
    private String getCharStringList(ArrayList<Character> characterList) {
        String string = "\n";
        for (Character character : characterList) {
            if (!string.equals("\n")) {
                string += "\n";
            }
            string = string + "- " + character.toString();
        }
        return string;
    }

    /**
     * Returns readable format of scenario instance
     *
     * @return readable format of scenario instance
     */
    @Override
    public String toString() {
        String string = "";
        string = "======================================\n"
                + "# Scenario\n"
                + "======================================\n"
                + "Legal Crossing: " + (this.isLegalCrossing() ? "yes" : "no") + "\n"
                + "Passengers (" + this.getPassengerCount() + ")\n"
                + this.getCharStringList(this.passengers) + "\n"
                + "Pedestrians (" + this.getPedestrianCount() + ")\n"
                + this.getCharStringList(this.pedestrians) + "\n";
        return string;
    }
}
