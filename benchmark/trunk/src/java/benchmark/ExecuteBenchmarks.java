package benchmark;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ExecuteBenchmarks {
    public static void main(String[] args) throws Exception {
        System.out.println( "started" );
        System.out.println( "--------\n" );
        // main dictates the benchmark to run        
        Properties main = new Properties();
        main.load( ExecuteBenchmarks.class.getResourceAsStream( "main.conf" ) );

        // the engines we are allowed to execute for
        List<String> allowedEngines = new ArrayList<String>();
        for ( String string : main.getProperty( "allowedEngines" ).split( "," ) ) {
            allowedEngines.add( string.trim() );
        }

        Class cls = ExecuteBenchmarks.class.getClassLoader().loadClass( main.getProperty( "benchmark-class" ).trim() );
        
        // get the main benchmark conf dictated by main.conf
        Properties benchmarkProperties = new Properties();
        benchmarkProperties.load( cls.getResourceAsStream( main.getProperty( "benchmark-conf" ).trim() ) );        

        // get the main benchmark class dictated by main.conf
        Constructor constructor = cls.getConstructor( new Class[]{Map.class} );
        Benchmark benchmark = (Benchmark) constructor.newInstance( new Object[]{benchmarkProperties} );

        // each key key indicates an engine we wish to execute for this benchmark
        for ( Iterator it = benchmarkProperties.keySet().iterator(); it.hasNext(); ) {
            String key = ((String) it.next()).trim();
            
            // only execute this engine if its in the allowed list
            if ( !allowedEngines.contains( key ) ) {
                continue;
            }
            
            System.out.println( "executing : " + benchmarkProperties.getProperty( "benchmark-name".trim() ) + " : " + key);            

            // get the properties for this engine
            Properties engineProperties = new Properties();
            engineProperties.load( benchmark.getClass().getResourceAsStream( benchmarkProperties.getProperty( key ).trim() ) );                    

            // construct the SetRunner for this engine
            String setRunnerCls = engineProperties.getProperty( "SetRunner" ).trim();
            constructor = benchmark.getClass().getClassLoader().loadClass( setRunnerCls ).getConstructor( new Class[]{Map.class} );
            SetRuleRunner setRunner = (SetRuleRunner) constructor.newInstance( new Object[]{engineProperties} );

            // set the benchmark PrintStream
            PrintStream appout;
            try {
                appout = new PrintStream( (String) engineProperties.get( "appout" )  + ".txt");
            } catch ( FileNotFoundException e ) {
                // wrap as runtime exception
                throw new RuntimeException( "unable to set PrintStream",
                                            e );
            }

            // iterate
            int iterations = Integer.parseInt( engineProperties.getProperty( "iterations" ) );
            for ( int i = 0; i < iterations; i++ ) {
                benchmark.runSession( setRunner.createSession(),
                                      appout );
            }
            appout.close();

            // now output the stats
            try {
                PrintStream statsout = new PrintStream( (String) engineProperties.get( "statsout" ) + ".txt");
                statsout.print( setRunner.getStats() );
                statsout.close();
            } catch ( FileNotFoundException e ) {
                // wrap as runtime exception
                throw new RuntimeException( "unable to set PrintStream",
                                            e );
            }
        }
        System.out.println( "" );
        System.out.println( "finished" );
        System.out.println( "--------" );
    }
}
