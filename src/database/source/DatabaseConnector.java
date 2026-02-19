/*
* @author GustavoBrizola
*/
package database.source; 

public class DatabaseConnector
{
    // Use Singleton Pattern to access all once this class is instantiated
    public ConnectorSettings settings = ConnectorSettings.Get_Instance();   // Setting object     
    public QueryOperations operations = QueryOperations.Get_Instance();     // Operation object
    // public Exports export = Exports.Get_Instance();          // Exports object
    // public Import import = Import.Get_Instance();            // Import object

    // Constructor
    /***
     * Iniciates link to MySQL Database.
     * 
     * <p>
     * {@code settings} gives change configurations parateters used
     * <P>
     * {@code operations} Allow create, execute and prints queries.
     * <P>
     * <strong>NOTE:</strong> To prevent memory leaks, be sure use 
     * {@code settings.Disconnect()} when connection is not necessary.
     * 
     * @param driver - Determines witch database engine system the URL protocol will use, 
     * along with the host, port and database parameter.
     * @param host - Server IP address domain.
     * @param port - Network port.
     * @param database - Database name for access.
     * @param user - User name credentials.
     * @param password - Authentication password.
     * 
     */
    public DatabaseConnector(int driver, String host, String port, String database, String user, String password)
    {
        // Initizlize all neeeded variables
        settings.SetDriver(driver);
        settings.SetHost(host);
        settings.SetPort(port);
        settings.SetDatabase(database);
        settings.SetUser(user);
        settings.SetPassword(password);
        settings.Connect();
    }
}
