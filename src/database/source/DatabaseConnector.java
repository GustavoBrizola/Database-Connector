/*
* @author GustavoBrizola
*/
package database.source; 

public class DatabaseConnector
{
    /*****************
    Method Chaining
    It connects all other methods and atributes to the main class
    When called as object, it has access to all methods and atributes, as long as they are public
        * public methods are accessible by every class that has instantiated the object
        * protected methods and atributes are only accessible by THIS class
        * private methods and atributes are only accessible inside their own class

    Advantages:
        * Easier to read and write code
        * Better organization of methods and atributes
        * Easier to maintain and update code
        * Reduces the need for multiple instances of classes

    Disadvantages:
        * Can lead to longer method chains that are harder to debug
        * May introduce performance overhead if not implemented correctly

    Example:
    Database database_sample = new Database(...);
    database_sample.Settings.function();
    *****************/

    /*****************
    Singleton Pattern
    Ensures a class has only one instance and provides a global point of access to it.
    Useful when exactly one object is needed to coordinate actions across the system or a section feature.
        * For this project, it is used to manage variables and methods accessibility accross all class within, not out of it.
        * altought it can not instantiate far more than one object.
        * Since it only allow share varable data within, outer classes dont have access to components without instantiating the main class.
        * Classes that aren't specifically in this directory can't call classes other than this

    Advantages:
        * Controlled access to the sole instance
        * Reduced namespace pollution
        * Allow refinement of operations and representation

    Disadvantages:
        * Can be more difficult to unit test due to global state
        * May introduce hidden dependencies in the code
        * Can be considered an anti-pattern by some due to its global state nature

    Example:
    Operation Class:
        String data = Settings.Get_Instance().function();
        or
        Settings.Get_Instance().function();
    *****************/
       
    // Use Singleton Pattern to access all once this class is instantiated
    public ConnectionSettings setting = ConnectionSettings.Get_Instance();  // Setting object     
    public QueryOperations operation = QueryOperations.Get_Instance();      // Operation object
    // public Exports export = Exports.Get_Instance();          // Exports object
    // public Import import = Import.Get_Instance();            // Import object

    // Constructor
    public DatabaseConnector(int driver, String host, String port, String database, String user, String password)
    {
        // Initizlize all neeeded variables
        setting.SetDriver(driver);
        setting.SetHost(host);
        setting.SetPort(port);
        setting.SetDatabase(database);
        setting.SetUser(user);
        setting.SetPassword(password);
        setting.Connect();
    }
}
