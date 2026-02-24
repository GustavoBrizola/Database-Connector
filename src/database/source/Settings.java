package database.source;

// Important libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Settings
{ 
    // Singleton Pattern
    private static Settings settings;
    private Settings(){}
    protected static Settings GetInstance()
    {
        if(settings == null) synchronized(Settings.class) {settings = new Settings();}
        return settings;
    }

    // Variables
    private Connection connection;
    
    private int driver;
    private String protocol;
    private String host;
    private String port;
    private String database;

    private String user;
    private String password;

    // Setters and Getters
    protected void SetConnection(Connection connection) {this.connection = connection;}
    public Connection GetConnection() {return this.connection;}

    protected void SetProtocol(String protocol) {this.protocol = protocol;}
    public String GetProtocol() {return this.protocol;}

    protected void SetDriver(int driver) {this.driver = driver;}
    public int GetDriver() {return this.driver;}

    protected void SetHost(String host) {this.host = host;}
    public String GetHost() {return this.host;}

    protected void SetPort(String port) {this.port = port;}
    public String GetPort() {return this.port;}

    protected void SetDatabase(String database) {this.database = database;}
    public String GetDatabase() {return this.database;}

    protected void SetUser(String user) {this.user = user;}
    public String GetUser() {return this.user;}

    protected void SetPassword(String password) {this.password = password;}
    public String GetPassword() {return this.password;}

    /* setting Methods */
    // Starts connection session
    public Settings Connect()
    {
        // If there's connection, do nothing and returns
        if(CheckConnection()) return this;

        try
        {
            SetProtocol("jdbc:mysql://"+ GetHost()+":"+ GetPort() + "/"+ GetDatabase());
            SetConnection(DriverManager.getConnection(GetProtocol(), GetUser(), GetPassword()));
        } 
        catch (SQLException e) 
        {   
            // FIXME: Catchs exception on first attempt
            System.err.println("("+e.getErrorCode()+") "+"Connection Failed: "+e.getMessage());
            return this;
        }
        return this;
    }

    // Ends the current connection
    public Settings Disconnect()
    {
        // If there's not connection, do nothing and return
        if(!CheckConnection()) return this;
        
        try
        {
            GetConnection().close();            // Free resources
            SetConnection(null);    // Optional
        }
        catch(SQLException e)
        {
            System.err.println("Failed: "+e.getMessage());
        }
        return this;
    }

    // Checks current connection
    public boolean CheckConnection()
    {
        if(GetConnection() != null)
        return true;
        else return false;
    }

    //
    public void ChangeConnection(int driver, String host, String port, String database, String user, String password)
    {
        Disconnect();
        if(GetDriver() != driver)   SetDriver(driver);
        if(GetHost() != null)       SetHost(host);
        if(GetPort() != null)       SetPort(port);
        if(GetDatabase() != null)   SetDatabase(database);
        if(GetUser() != null)       SetUser(user);
        if(GetPassword() != null)   SetPassword(password);
        Connect();
    }
}
