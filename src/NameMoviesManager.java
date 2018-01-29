import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import net.sf.json.JSONArray;

public class NameMoviesManager {
	final static String MOVIE_NAME = "Avataar";
	static Logger log = Logger.getLogger(NameMoviesManager.class.getName());

	public static void main(String[] args) {
		JSONArray inputFileArray = getInputFileAsJsonArray();
		if (inputFileArray != null && inputFileArray.size() > 0) {
			JSONArray selectedPersonsArr = getPersonsWatchAvataar(inputFileArray);
			if (selectedPersonsArr != null && selectedPersonsArr.size() > 0) {
				JSONArray otherMovies = getOtherMoviesWatchedByPersons(
						selectedPersonsArr, inputFileArray);
				System.out.println();
				log.info("<----------Output Results---------->");
				for (int i = 0; i < otherMovies.size(); i++) {
					System.out.println(otherMovies.getString(i));
				}
			} else {
				log.info("No Persons Found For Avataar");
			}
		} else {
			log.info("Invalid Input File");
		}

	}

	public static JSONArray getInputFileAsJsonArray() {
		BufferedReader br = null;
		JSONArray inputFileContentArr = null;
		try {
			br = new BufferedReader(
					new FileReader(
							"/home/dinesh/workspace/AffinityAnswers/InputFile/Affinity.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			inputFileContentArr = new JSONArray();
			String line;
			log.info("<----------Input File---------->");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				inputFileContentArr.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputFileContentArr;
	}

	private static JSONArray getPersonsWatchAvataar(JSONArray inputFileArray) {
		JSONArray personsWatchAvataar = null;
		try {
			if (inputFileArray != null && inputFileArray.size() > 0) {
				personsWatchAvataar = new JSONArray();
				for (int i = 0; i < inputFileArray.size(); i++) {
					if (inputFileArray.getString(i).split("\\t", -1)[1]
							.equalsIgnoreCase(MOVIE_NAME)) {
						personsWatchAvataar.add(inputFileArray.getString(i)
								.split("\\t", -1)[0]);
					}
				}
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personsWatchAvataar;
	}

	private static JSONArray getOtherMoviesWatchedByPersons(
			JSONArray selectedPersonsArr, JSONArray inputFileArray) {
		JSONArray otherMoviesArr = new JSONArray();
		try {
			for (int i = 0; i < selectedPersonsArr.size(); i++) {
				String personName = selectedPersonsArr.getString(i);
				for (int j = 0; j < inputFileArray.size(); j++) {
					if (inputFileArray.getString(j).split("\\t", -1)[0]
							.equalsIgnoreCase(personName)
							&& !inputFileArray.getString(j).split("\\t", -1)[1]
									.equalsIgnoreCase(MOVIE_NAME)) {
						String movie = inputFileArray.getString(j).split("\\t",
								-1)[1];
						// Check For Duplicate Movie Entries
						if (!otherMoviesArr.contains((Object) movie)) {
							otherMoviesArr.add(movie);
						}

					}
				}
			}
			if (otherMoviesArr != null && otherMoviesArr.size() > 0) {
				return otherMoviesArr;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
