package org.amila.cm;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Amila Manoj
 * Date: 3/7/12
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseResult {
    private boolean success;
    private ArrayList<ParseException> exceptionList;

    public ParseResult(){
        success=true;
        exceptionList = new ArrayList<ParseException>();
    }

    public void addException(ParseException e){
        success=false;
        exceptionList.add(e);
    }

    public boolean isSuccess(){
        return success;
    }

    public ArrayList<ParseException> getExceptionList(){
        return exceptionList;
    }

}
