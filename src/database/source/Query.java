package database.source;

public class Query
{     
    // Singleton pattern
    private Query(){}
    private static Query query;
    protected static Query GetInstance()
    {
        if(query == null) synchronized(Query.class) {query = new Query();}
        return query;
    }

    // Variables
    private String sqlquery;

    // Getters and Setters
    protected void SetQuery(String query) {this.sqlquery = query;}
    public String GetQuery() {return this.sqlquery;}
    public Query NewQuery() {SetQuery(null); return this;}

    // Links of classes
    public QueryRun run = QueryRun.GetInstance();
    public QueryCommand commmand = QueryCommand.GetInstance();
}