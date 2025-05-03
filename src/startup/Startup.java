package startup;

import controller.Controller;
import integration.DatabaseCreator;
import view.View;

/**
 * This class represents the startup procedure for the program.
 * I call a function in the creator object to fill the inventory system, this is a temporary solution so that I can run the program and add items.
 */

public class Startup {

	public static void main(String[] args) {
		DatabaseCreator creator = new DatabaseCreator();
		creator.fillInventorySystem();
		Controller contr = new Controller(creator);
		View view = new View(contr);
	}

}
