package ethicalengine;

import ethicalengine.Animal.Specie;
import ethicalengine.Character.BodyType;
import ethicalengine.Character.Gender;
import ethicalengine.Person.Profession;

/**
 * Constants stores constant values across the entire project.
 *
 * @author {Name: Ankita Dhar, ID: 1154197, Username: addh} date: 18-Jun-2020
 */
public class Constants {

    private Constants() {
    }

    /**
     * default value of age of a person, used when faulty values are read while generating
     * scenarios by reading .csv file.
     */
    public static final int DEFAULT_AGE = 1;

    /**
     * minimum age to categorise persons as baby.
     */
    public static final int AGE_MIN = 1;
    /**
     * maximum age to categorise persons as baby.
     */
    public static final int AGE_BABY_MAX = 4;
    /**
     * minimum age to categorise persons as child.
     */
    public static final int AGE_CHILD_MIN = 5;
    /**
     * maximum age to categorise persons as child.
     */
    public static final int AGE_CHILD_MAX = 16;
    /**
     * minimum age to categorise persons as adult.
     */
    public static final int AGE_ADULT_MIN = 17;
    /**
     * maximum age to categorise persons as adult.
     */
    public static final int AGE_ADULT_MAX = 68;
    /**
     * minimum age to categorise persons as senior.
     */
    public static final int AGE_SENIOR_MIN = 69;
    /**
     * minimum age to categorise persons as senior. assuming max age of human to be 150 years.
     */
    public static final int AGE_MAX = 150;

    /**
     * default number of members in either of the groups (passenger(s)/pedestrian(s)) when using
     * scenario generator.
     */
    public static final int DEFAULT_GROUP_MEMBER_MIN_COUNT = 1;
    public static final int DEFAULT_GROUP_MEMBER_MAX_COUNT = 5;

    /**
     * default value of profession of a person, used when faulty values are read while generating
     * scenarios by reading .csv file.
     */
    public static final Profession DEFAULT_PROFESSION = Profession.UNKNOWN;
    /**
     * default value of gender of a person, used when faulty values are read while generating
     * scenarios by reading .csv file.
     */
    public static final Gender DEFAULT_GENDER = Gender.UNKNOWN;
    /**
     * default value of body type of a person, used when faulty values are read while generating
     * scenarios by reading .csv file.
     */
    public static final BodyType DEFAULT_BODYTYPE = BodyType.UNSPECIFIED;
    /**
     * default value of specie of an animal, used when faulty values are read while generating
     * scenarios by reading .csv file.
     */
    public static final String DEFAULT_SPECIES = Specie.DOG.toString();
    /**
     * max ordinal value of profession used for generation of random scenarios.
     */
    public static final int PROF_MAX_ORDINAL = Profession.getAllValues().size();
    /**
     * max ordinal value of gender used for generation of random scenarios.
     */
    public static final int GENDER_MAX_ORDINAL = Gender.values().length;
    /**
     * max ordinal value of body type used for generation of random scenarios.
     */
    public static final int BODYTYPE_MAX_ORDINAL = BodyType.values().length;
    /**
     * max ordinal value of specie used for generation of random scenarios.
     */
    public static final int SPECIE_MAX_ORDINAL = Specie.values().length;

    /**
     * value of number of attribute list required to generate scenarios.
     */
    public static final int ATTR_LIST_LENGTH = 10;

    /**
     * points used in the decision making.
     */
    public static final int ONE_POINT = 1;
    /**
     * points used in the decision making.
     */
    public static final int TWO_POINT = 2;
    /**
     * points used in the decision making.
     */
    public static final int THREE_POINT = 3;
    /**
     * points used in the decision making.
     */
    public static final int FOUR_POINT = 4;
    /**
     * points used in the decision making.
     */
    public static final int FIVE_POINT = 5;

    /**
     * delimiter to separate attributes of a scenario while reading .csv file.
     */
    public static final String ATTR_DELIMITER = ",";

    /**
     * default file to log audit report.
     */
    public static final String LOG_FILE = "results.log";

    /**
     * default file to read welcome message from.
     */
    public static final String WELCOME_FILE = "welcome.ascii";

    /**
     * default file to log audit report for a user.
     */
    public static final String USER_LOG_FILE = "user.log";

    /**
     * number of scenarios to be audited in a row by user
     */
    public static final int AUDIT_STREAK = 3;

    /**
     * default audit type when engine audit scenarios.
     */
    public static final String DEFAULT_AUDIT_TYPE = "Unspecified";

    /**
     * default audit type when user audit scenarios.
     */
    public static final String AUDIT_BY_USER = "User";

    /**
     * response expected from user for agreement.
     */
    public static final String USER_AGREED = "yes";

    /**
     * response expected from user for dis-agreement.
     */
    public static final String USER_DISAGREED = "no";

    /**
     * content of help when -h or --help is passed as argument
     */
    public static final String HELP_CONTENT = "EthicalEngine - COMP90041 - Final Project\n\n"
            + "Usage: java EthicalEngine [arguments]\n\n"
            + "Arguments:\n"
            + "   -c or --config\tOptional: path to config file\n"
            + "   -h or --help\t\tPrint Help (this message) and exit\n"
            + "   -r or --results\tOptional: path to results log file\n"
            + "   -i or --interactive\tOptional: launches interactive mode";

    public static final String PRINT_ERROR_MSG = "ERROR: could not print results. Target directory does not exist.";

    public static final String CONSENT_TO_LOG_MSG = "Do you consent to have your decisions saved to a file? ";

    public static final String FILE_TO_READ_NOT_FOUND = "ERROR: could not find config file.";

    public static final String CONSENT_TO_CONTINUE = "Would you like to continue? ";
}
