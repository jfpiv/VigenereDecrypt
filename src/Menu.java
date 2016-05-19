import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Menu class represents a menu for a command line interface. A Menu
 * object holds a list of immutable Menu.MenuItem objects and handles querying
 * the user to choose a menu item.
 *
 * @author John Pettenger
 * @version 1.0, 02/09/16
 */
public class Menu{

  /**
   * The list of menu items.
   */
  private ArrayList<MenuItem> menuItems;

  /**
   * This menu's name.
   */
  private String menuName;

  /**
   * Variable to count the number of times this menu has been queried (via
   * queryUser() method).
   */
  private int queryCount;

  /**
   * Index of the help MenuItem. The help Menuitem is always first in the list
   * of menu items.
   */
  private static final int HELP_ITEM_INDEX = 0;

  /**
   * The help MenuItem.
   */
  private static final MenuItem HELP_ITEM = new MenuItem(
    HELP_ITEM_INDEX,
    "Display menu",
    "Displays this menu."
  );

  /**
   * Creates a menu with a specified name.
   *
   * @param name The name
   */
  public Menu(String name){
    this.menuName = name;
    this.menuItems = new ArrayList<>();
    this.queryCount = 0;

    addItem(HELP_ITEM);
  }

  /**
   * Creates a menu with no name.
   */
  public Menu(){
    this("");
  }

  /**
   * Adds an item to this menu.
   *
   * @param item The entry to add
   */
  public void addItem(MenuItem item){
    this.menuItems.add(item);
  }

  /**
   * Adds an item to this menu.
   *
   * @param id The menu item's id
   * @param name The menu item's
   * @param description The menu item's description
   */
  public void addItem(int id, String name, String description){
    addItem(new MenuItem(id, name, description));
  }

  /**
   * Gets the menu item at a specified index.
   *
   * @param index The index of the menu item
   * @return The menu item at the specified index
   */
  public MenuItem getItem(int index){
    return menuItems.get(index);
  }

  /**
   * Returns this menu's name.
   *
   * @return The name
   */
  public String getName(){
    return this.menuName;
  }

  /**
   * Sets this menu's name.
   *
   * @param name The name
   */
  public void setName(String name){
    this.menuName = name;
  }

  /**
   * Queries the user to choose a menu item. The user is queried until a valid
   * choice is made. If this is the first time calling this method,
   *
   * @param input The scanner to read input from, typically stdin
   * @return The id of the chosen menu item
   */
  public int queryUser(Scanner input){
    final int BAD_CHOICE = -1;

    boolean validChoice;
    int choiceIndex;

    // If this is the first time querying the user with this menu,
    // display this menu.
    if(queryCount++ == 0){
      System.out.println(this);
    }

    do{
      System.out.printf("%s: ", menuName);

      // Read the choice index from the user. If a NumberFormat exception
      // occurs, the user did not input an integer.
      try{
        choiceIndex = Integer.parseInt(input.nextLine());
      } catch(NumberFormatException e){
        choiceIndex = BAD_CHOICE;
      }

      // If the user requested help, print the menu, otherwise check that the
      // choice index is valid.
      if(choiceIndex == HELP_ITEM_INDEX){
        System.out.println(this);
        validChoice = false;
      } else{
        // The choice is valid if it is a legal index of menuItems.
        validChoice = choiceIndex >= 0 && choiceIndex < menuItems.size();

        // If the choice is invalid, print the menu and state that the choice
        // is invalid.
        if(!validChoice){
          System.out.printf(
            "%s%nInvalid option. Enter the menu item number only.%n", this
          );
        }
      }
    } while(!validChoice);

    // Return the id of the chosen item.
    return getItem(choiceIndex).getId();
  }


  /**
   * Builds a string representation of this menu.
   *
   * The string representation contains each menu item's index, name, and
   * description. The items' descriptions are word wrapped for a maximum of 70
   * columns. The items names' are assumed to be short enough to not require
   * word wrapping.
   *
   * Example:
   *   0. item 0 name
   *      This is item 0's description. This is item 0's description. This is
   *      item 0's description. This is item 0's description.
   *   1. item 1 name
   *      This is item 1's description. This is item 1's description. This is
   *      item 1's description. This is item 1's description.
   *   ...
   *   n. item n name
   *      This is item n's description. This is item n's description. This is
   *      item n's description. This is item n's description.
   *      |<----------------------- max 70 columns ------------------------>|
   *
   *
   * @return The string representation of this menu
   */
  public String toString(){
    final int DESCRIPTION_MAX_COLUMNS = 70;

    StringBuilder builder = new StringBuilder();

    builder.append(String.format("~~~ %s ~~~%n", menuName));

    for(int i = 0; i < menuItems.size(); ++i){
      MenuItem item = menuItems.get(i);
      String name = item.getName();
      String description = item.getDescription();

      builder.append(String.format("%2d. %s%n   ", i, name));

      // Word wrap the description based on DESCRIPTION_MAX_COLUMNS
      try(Scanner descriptionScanner = new Scanner(description)){
        int currentNumColumns = 0;

        while(descriptionScanner.hasNext()){
          String nextWord = descriptionScanner.next();
          if(currentNumColumns + nextWord.length() <= DESCRIPTION_MAX_COLUMNS){
            builder.append(" ").append(nextWord);
            // We add 1 here to account for the space
            currentNumColumns += nextWord.length() + 1;
          } else{
            builder.append(System.lineSeparator());
            builder.append("    ").append(nextWord);
            // We add 1 here to account for the space
            currentNumColumns = nextWord.length() + 1;
          }
        }
      }

      builder.append(System.lineSeparator());
    }


    return builder.toString();
  }

  /**
   * The Menu.MenuItem class represents a single entry in a Menu. A MenuItem
   * holds an entry's name and description. MenuItems are immutable.
   */
  public static final class MenuItem{

    /**
     * This menu item's name
     */
    private final String name;

    /**
     * This menu item's description.
     */
    private final String description;

    /**
     * This menu item's id.
     */
    private final int id;

    /**
     * Creates a new menu item with a specified id, name, and description.
     *
     * @param id This item's id
     * @param name This item's name
     * @param description This item's description
     */
    public MenuItem(int id, String name, String description){
      this.name = name;
      this.description = description;
      this.id = id;
    }

    /**
     * Creates a new menu item with a specified id and name and no description.
     *
     * @param id This item's id
     * @param name This item's name
     */
    public MenuItem(int id, String name){
      this(id, name, "");
    }

    /**
     * Returns this menu item's id.
     *
     * @return The id
     */
    public int getId(){
      return id;
    }

    /**
     * Returns this menu item's name.
     *
     * @return The name
     */
    public String getName(){
      return name;
    }

    /**
     * Returns this menu item's description.
     *
     * @return The description.
     */
    public String getDescription(){
      return description;
    }

    /**
     * Returns a string representation of this menu item, containing the name
     * and description on separate lines.
     *
     * @return This menu item's string representation
     */
    public String toString(){
      return String.format("%s%n%s", name, description);
    }
  }
}
