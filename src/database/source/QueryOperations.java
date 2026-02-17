package database.source;

// Important libraries
import java.sql.SQLException;
import java.sql.Statement;

/*
* @author GustavoBrizola 
*/
public class QueryOperations
{  
    // Singleton Pattern structure
    private static QueryOperations operations;
    private QueryOperations(){}
    protected static QueryOperations Get_Instance()
    {
        if(operations == null)
        {
            synchronized(QueryOperations.class) {operations = new QueryOperations();}
        }
        return operations;
    }
        
    // Variables
    private String query;
    private Statement query_statement;
        
    // Setters and Getters
    protected void SetQuery(String query) {this.query = query;}
    public String GetQuery() {return this.query;}

    protected void SetQueryStatement(Statement query_statement) {this.query_statement = query_statement;}
    public Statement GetQueryStatement() {return this.query_statement;}
    
    /* Operation Methods */
    // Executes any SQL statement
    public QueryOperations Prompt(String query)
    {
        SetQuery(query);
        return this;
    }

    // Primary Syntaxes - Initialize the query
    // SELECT
     public QueryOperations SELECT(String data)
    {        
        if(data == null) return this;
        String method = BuildQuery("SELECT ? ", data);
        SetQuery(method);
        return this;
    }

    //INSERT
    public QueryOperations INSERT(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("INSERT ? ", data);
        SetQuery(method);
        return this;
    }

    //CREATE
    public QueryOperations CREATE(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("CREATE ? ", data);
        SetQuery(method);
        return this;
    }

    //DELETE
    public QueryOperations DELETE(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("DELETE ? ", data);
        SetQuery(method);
        return this;
    }

    //DROP
    public QueryOperations DROP(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("DROP ? ", data);
        SetQuery(method);
        return this;   
    }

    //SHOW
    public QueryOperations SHOW(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("SHOW ? ", data);
        if(GetQuery() != null)
        {
            SetQuery(GetQuery()+method);
            return this;
        }
        SetQuery(method);
        return this;   
    }

    // Seconday Syntaxes - Determines Parameters
    // FROM
    public QueryOperations FROM(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("FROM ? ", data);
        if(GetQuery() != null)
        {
            SetQuery(GetQuery()+method);
            return this;
        }
        SetQuery(method);
        return this;
    }

    // WHERE
    public QueryOperations WHERE(String data)
    {   
        if(data == null) return this;
        String method = BuildQuery("WHERE ? ", data);
        if(GetQuery() != null)
        {
            SetQuery(GetQuery()+method);
            return this;
        }
        SetQuery(method);
        return this;
    }

    // AND
    public QueryOperations AND(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("AND ? ", data);
        if(GetQuery() != null)
        {
            SetQuery(GetQuery()+method);
            return this;
        }
        SetQuery(method);
        return this;
    }

    // VALUES
    public QueryOperations VALUES(String data)
    {
        if(data == null) return this;
        String method = BuildQuery("VALUES ? ", data);
        if(GetQuery() != null)
        {
            SetQuery(GetQuery()+method);
            return this;
        }
        SetQuery(method);
        return this;     
    }

    private String BuildQuery(String statement, String data)
    {
        return statement.replaceFirst("\\?", data);
    }

    // Run the query
    public QueryOperations RunQuery()
    {  
        try
        {
            SetQueryStatement(ConnectionSettings.Get_Instance().GetConnection().createStatement());
            GetQueryStatement().execute(GetQuery()); 
        }
        catch(SQLException e)
        {
            System.err.println("Query failed: " + e.getMessage());
        }
        // Clear the query for future operations
        SetQuery(null);
        return this;
    }

    // Prints on Terminal
    public void Terminal()
    {
        try
        {
            if(GetQueryStatement().getResultSet() != null)
            {
                while(GetQueryStatement().getResultSet().next())
                {
                    for(int i = 1; i <= GetQueryStatement().getResultSet().getMetaData().getColumnCount(); i++)
                    {
                        if(i>1) System.out.print(", ");
                        System.out.print(GetQueryStatement().getResultSet().getMetaData().getCatalogName(i) + ": " + GetQueryStatement().getResultSet().getString(i));
                    }   
                    System.out.println(""); 
                }
            }
        } 
        catch(SQLException e)
        {
            System.err.println("Terminal Output Failed: " + e.getMessage());
        }
    }
}