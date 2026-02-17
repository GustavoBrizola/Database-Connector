package database.source;

// Important libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
* @author GustavoBrizola 
*/
public class ConnectionSettings
{ 
    // Singleton Pattern
    private static ConnectionSettings settings;
    private ConnectionSettings(){}
    protected static ConnectionSettings Get_Instance()
    {
        if(settings == null)
        {
            synchronized(ConnectionSettings.class) {settings = new ConnectionSettings();}
        }
        return settings;
    }

    // Variables
    private int driver;
    
    private String protocol;
    private String host;
    private String port;
    private String database;

    private String user;
    private String password;
    private Connection connection;

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
    public ConnectionSettings Connect()
    {
        BuildProtocol();
        try
        {
            SetConnection(DriverManager.getConnection(GetProtocol(), GetUser(), GetPassword()));
        } 
        catch (SQLException e) 
        {   
            System.err.println("("+e.getErrorCode()+") "+"Connection Failed: "+e.getMessage());
            Connect();
        }
        return this;
    }

    // Exclude any existing connection
    public ConnectionSettings Disconnect()
    {
        try
        {
            // Personaly cannot understand why won't end
            GetConnection().close();
            SetConnection(null);
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
        if(GetConnection() != null) return true;
        else return false;
    }

    // Changes connection characteristcs
    public ConnectionSettings ChangeCredentials(String user, String password)
    {
        Disconnect();
        SetUser(user);
        SetPassword(password);
        Connect();
        return this;
    }
    
    // Changes database
    public ConnectionSettings ChangeDatabase(String database)
    {
        Disconnect();
        SetDatabase(database);
        Connect();
        return this;
    }

    // Change Host
    public ConnectionSettings ChangeHost(String host)
    {
        Disconnect();
        SetHost(host);
        Connect();
        return this;
    }
    
    // Change port number
    public ConnectionSettings ChangePort(String port)
    {
        Disconnect();
        SetPort(port);
        Connect();
        return this;
    }
    
    // Change Database System Driver
    public ConnectionSettings ChangeDriver(int driver)
    {
        Disconnect();
        SetDriver(driver);
        Connect();
        return this;
    }

    // Builds the URL
    private String BuildProtocol()
    {
        switch(driver) 
        {
            case 1:
                //MySQL - jdbc:mysql://<host>:<port>/<database>
                SetProtocol
                (
                    "jdbc:mysql://"
                    + GetHost() + ":"
                    + GetPort() + "/"
                    + GetDatabase()
                );
            break;
            case 2:
                //PostgreeSQL - jdbc:postgresql://<host>:<port>/<database>
                SetProtocol
                (
                    "jdbc:postgresql://"
                    + GetHost() + ":"
                    + GetPort() + "/"
                    + GetDatabase()
                );
            default:
                // null if the driver value does not meet the cases
                SetProtocol(null);
            break;
        }
        return GetProtocol();
    }
}
