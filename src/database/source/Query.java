package database.source;

public class Query
{    
    // Singleton Pattern
    private static Query query;
    private Query(){sqlquery = null;}
    protected static Query GetInstance()
    {
        // TODO: Find out a way to multiple instances when database calls it
        // another code pattern need
        if(query == null) synchronized(Query.class) {query = new Query();}
        return query;
    }

    public QueryCommand commands = QueryCommand.Get_Instance();     

    // Variables
    private String sqlquery;

    // Getters and Setters
    protected void SetQuery(String query) {this.sqlquery = query;}
    public String GetQuery() {return this.sqlquery;}
}
