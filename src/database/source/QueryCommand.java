package database.source;

public class QueryCommand
{
    // Singleton pattern
    private QueryCommand() {}
    private static QueryCommand command;
    protected static QueryCommand GetInstance()
    {
        if(command == null) synchronized(QueryCommand.class) {command = new QueryCommand();}
        return command;
    }    

    // Replace the ? with data
    // Also add to Query in case of not null
    // So repetitive code in multiple functions is avoided
    private void BuildQuery(String statement, String data)
    {
        if(data == null) return;
        String placement = statement.replaceFirst("\\?", data);
        // Prevents 'null' literally appearing on string
        if(Query.GetInstance().GetQuery() == null) 
        {
            Query.GetInstance().SetQuery(placement);
            return;
        }
        Query.GetInstance().SetQuery(Query.GetInstance().GetQuery()+placement);
    }

    // Query syntaxes
    // TODO: Improve functions
    public QueryCommand SELECT(String data) {BuildQuery("SELECT ? ",    data); return this;}
    public QueryCommand INSERT(String data) {BuildQuery("INSERT ? ",    data); return this;}
    public QueryCommand CREATE(String data) {BuildQuery("CREATE ? ",    data); return this;}
    public QueryCommand DELETE(String data) {BuildQuery("DELETE ? ",    data); return this;}
    public QueryCommand DROP(String data)   {BuildQuery("DROP ? ",      data); return this;}
    public QueryCommand SHOW(String data)   {BuildQuery("SHOW ? ",      data); return this;}
    public QueryCommand FROM(String data)   {BuildQuery("FROM ? ",      data); return this;}
    public QueryCommand WHERE(String data)  {BuildQuery("WHERE ? ",     data); return this;}
    public QueryCommand AND(String data)    {BuildQuery("AND ? ",       data); return this;}
    public QueryCommand VALUES(String data) {BuildQuery("VALUES ? ",    data); return this;}
}
