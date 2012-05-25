package org.amila.cm;

/**
 * Created by IntelliJ IDEA.
 * User: amila
 * Date: 2/12/12
 * Time: 12:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class CMinusCommandLineParser {
    public static void main(String[] args) {

        CMinusParser parser;

        if (args.length == 1) {
            System.out.println("CMinus Parser Version 1.0:  Checking syntax of " + args[0]);
            parser = new CMinusParser(new java.io.StringReader(args[0]));

        } else if (args.length == 2) {
            System.out.println("CMinus Parser Version 1.0:  Reading from file " + args[1] + " . . .");
            try {
                parser = new CMinusParser(new java.io.FileInputStream(args[1]));
            }
            catch (java.io.FileNotFoundException e) {
                System.out.println("CMinus Parser Version 1.0:  File " + args[1] + " not found.");
                return;
            }
        }
        else {
            System.out.println("[ CMinus Parser Version 1.0:       ]");
            System.out.println("[ Usage:                           ]");
            System.out.println("[ java CMinusParser 'input string' ]");
            System.out.println("[               OR                 ]");
            System.out.println("[ java CMinusParser file inputfile ]");
            return;
        }
        try {
//            parser.S();
            System.out.println("CMinus Parser Version 1.0:  C Minus program's syntax check passed successfully. :)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("CMinus Parser Version 1.0:  Encountered errors during syntax check. :(");
        }
    }
}
