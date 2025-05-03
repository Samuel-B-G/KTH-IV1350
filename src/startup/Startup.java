package startup;

import controller.Controller;
import integration.DatabaseCreator;
import view.View;

/**
 * This class represents the startup procedure for the program.
 */

public class Startup {

	public static void main(String[] args) {
		DatabaseCreator creator = new DatabaseCreator();
		creator.fillInventorySystem(); // Temporary function to fill database
		Controller contr = new Controller(creator);
		View view = new View(contr);
	}

}
