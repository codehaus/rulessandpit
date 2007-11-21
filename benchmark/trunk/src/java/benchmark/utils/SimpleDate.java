package benchmark.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDate extends Date {
    public static final long serialVersionUID = 1;

    public SimpleDate(String datestr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
        this.setTime( format.parse( datestr ).getTime() );
    }
}
