# moral_machine
interactively solving puzzles of trolley dilemma

Compile the project with javac EthicalEngine.java
run the project as: EthicalEngine <options>
To check out the options, run the project as EthicalEngine -h

Reflection:
This project simulates the famous trolley dilemma problem.
Biases:
To decide who should be saved, a pointer-based system is employed.
• Number of characters for pedestrians and passengers are considered here
• Also, more points are given to passenger if pedestrians are crossing illegally than to pedestrians if they are crossing legally. Keeping in view, that car has lost its control and passengers have no fault of theirs in doing so.
After that for each character following are considered as well.
While considering a scenario:
1. No gender-based biases are considered. However, a pregnant person gets points as a new life is very much valuable.
2. No one based on their body type are discriminated; except if a person is handicapped
3. Majorly, a person is given more points if they are valuable for the society. As in, if their profession is one among the following:
a. Doctor
b. NGO worker
c. Educationist
d. Politician
They are valued the most
A lesser valuable job, like journalism is given fewer points
And criminals are not given any points.
4. Considering, myself as user, I have coded the function to reduce few points if the person is “you”. However, if “you” has other qualities, then the points will be positive
5. Also, since babies and children are future of society, hence, they are given prefer over others. Adults have lesser preference than babies and children but more than seniors. Being a senior a person doesn’t attract any point for this character.
6. Animals are given preference only if they are endangered or pets.
Few anomalies were found. Sometimes Criminals were saved, because of other characters in their group.
Note:
If -c -r -i options are provided, all of them will be taken care of while auditing; however, if -h option is provided in combination with any other option then only help text is shown to the user.
If no options are provided, then random 3 scenarios are generated and audited to display the audited result.
