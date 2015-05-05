package Botha_Daniel_HW8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;

/**
 * PRG Information Technology Gr12: March 2015 Practical Test
 * This test paper used the "March 2014" test paper as skeleton.
 * 
 * @author [name], [student number]
 * 
 */

public class PRG_IT_2015_march_test 
{
    static 
    {
        try 
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } 
        catch (ClassNotFoundException e) 
        {
            System.err.println("Derby driver not found.");
        }
    }
    
    private Connection conn;
    private String[] poisOutput = {"Beaufort West", "Laingsburg", "Graaff Reinett", //Points of interest to be printed - notice \t at some shorter names
                        "Stellenbosch", "Tunnel\t", "Worcester", "Aberdeen",
                        "Cradock\t", "Tarkastad", "Queenstown", "Cofimvaba", 
                        "Ncobo\t", "Mthatha\t"};
    private String[] poi = {"Beaufort West", "Laingsburg", "Graaff Reinett", //List of Points of Interest
                        "Stellenbosch", "Tunnel", "Worcester", "Aberdeen",
                        "Cradock", "Tarkastad", "Queenstown", "Cofimvaba", 
                        "Ncobo", "Mthatha"};
    private String[][] arrDep = new String[13][3]; 
    /*  arrDep 2D Array of Strings:
     * 
     *       Name       ArrTime                     DepTime 
     *  0    Stb        2013-02-28 23:34:41.0       2013-03-01 00:11:49.0
     *  ...  ...        ...                         ...
     *  12   Cofimvaba	2013-03-01 07:13:08.0       2013-03-01 07:32:12.0
     */
    
    
    public PRG_IT_2015_march_test() 
    {
        try 
        {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/NBUSER", "nbuser", "nbuser");
            System.out.println("Connection to GPS_traces Database Established");
        } catch (SQLException ex) 
        {
            System.out.println("Connection to GPS_traces Database Failed: " + ex);
        } 
    }
    
    /*2.1  */
    public void distanceAll(JTextArea out)
    {
        out.setText("\t\t");
        
        for (int i = 0; i < poi.length; i++) 
            out.append("POI" + (i+1) + "\t");

        for (int i = 0; i < poi.length; i++) 
        {
            out.append("\n" + poisOutput[i]);
            for (int j = 0; j < poi.length; j++) 
            {
                out.append("\t" + distancePoints(poi[i], poi[j], "pois", "pois"));
            }
        }
    } // 2.
    
    /*2.1  */
    public void distancePointsOutput(String poi1, String poi2, JTextArea out)
    {        
        out.append("\nTotal distance between " + poi1 + " and " + poi2 + ": " 
                    + distancePoints(poi1, poi2, "pois", "pois") + "km");
    } // 2.1
    
    /*2.1  */
    public double distancePoints(String poi1, String poi2, String tbl1, String tbl2)
    {
        try 
        {
            double dist;
            double lat1, lon1, lat2, lon2;
            
            String sql1 = "SELECT * \nFROM NBUSER.\""+ tbl1 +"\"\n" +
                           "WHERE \"name\" = '"+ poi1 +"'";
            String sql2 = "SELECT * \nFROM NBUSER.\""+ tbl2 +"\"\n" +
                           "WHERE \"name\" = '"+ poi2 +"'";
            
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            
            lat1 = rs.getDouble(2);
            lon1 = rs.getDouble(3);

            ResultSet rs2 = stmt.executeQuery(sql2);
            rs2.next();
            
            lat2 = rs2.getDouble(2);
            lon2 = rs2.getDouble(3);
            
            dist = (double) Math.round( haversine(lat1, lon1, lat2, lon2) *100 ) / 100;
            return dist;
        } 
        catch (SQLException e) 
        {
            System.out.println("distancePoints query unsuccessful: " + e);
            return 0.0;
        }
    
    } // 2.1
    
    /*2.2  */
    public void arrDepartTimes(JTextArea out)
    {
        try 
        {
            double dist, latTrace, lonTrace, rad, latPOI, lonPOI;
            String arr, dep;
            int c = 0;
            
            Statement stmt = conn.createStatement();    //To be used for traces
            Statement stmt2 = conn.createStatement();   //To be used for POIs
            
            out.setText(""); //Clear output 
            
            String sql1 = "SELECT * \nFROM NBUSER.\"traces\"";  //Traces
            String sql2 = "SELECT * \nFROM NBUSER.\"pois\"";    //POIS

            ResultSet rs;     //Traces
            ResultSet rs2 = stmt2.executeQuery(sql2);   //POIS
            
            while(rs2.next()) //Loop over POI
            {
                
                latPOI = rs2.getDouble(2);          //POIs
                lonPOI = rs2.getDouble(3);          //POIs
                rad = rs2.getDouble(4);             //POIs
                String name = rs2.getString(1);     //POIs
                
                rs = stmt.executeQuery(sql1); 
                
                innerloop: //http://www.stackoverflow.com/questions/886955/breaking-out-of-nested-loops-in-java
                while(rs.next())    //Loop over Traces
                {
                    latTrace = rs.getDouble(2);         //Latitude of Trace
                    lonTrace = rs.getDouble(3);         //Longitude of Trace

                    dist = haversine(latTrace, lonTrace, latPOI, lonPOI);

                    if (dist<rad) 
                    {
                        arr = "" + rs.getTimestamp(1); //Arrival Time 

                        while(rs.next())
                        {
                            latTrace = rs.getDouble(2); //Lat of current trace
                            lonTrace = rs.getDouble(3); //Long of current trace

                            dist = haversine(latTrace, lonTrace, latPOI, lonPOI);

                            if (dist>rad) 
                            {
                                dep = "" + rs.getTimestamp(1); //Departure Time

                                arrDep[c][0] = name; //name
                                arrDep[c][1] = arr; //arrival time
                                arrDep[c][2] = dep; //departure time
                                out.append(poisOutput[c] + "\t" + arrDep[c][1] + "\t" + arrDep[c][2] +"\n");
                                c++;
                                break innerloop;
                            }
                        }
                    }
                }
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("Arrival and Departure Times query unsuccessful: " + e);
        }
        
        
        
    } // 2.2
    
    public void drivingDistances(JTextArea out)
    {
        out.setText("\t\t");
        
        for (int i = 0; i < poi.length; i++) 
            out.append("POI" + (i+1) + "\t");

        for (int i = 0; i < poi.length; i++) 
        {
            out.append("\n" + poisOutput[i]);
            for (int j = 0; j < poi.length; j++) 
            {
                out.append("\t" + distancePoints(poi[i], poi[j], "pois", "pois"));
            }
        }
    }
    
    /* 1.  */
    public void showAllTraces() 
    {
        try 
        {
            Statement stmt = conn.createStatement();

            String sql = "select * from NBUSER.\"traces\"";

            ResultSet rs = stmt.executeQuery(sql);
            String[] headings = {"Timestamp", "Latitude", "Longitude", "Speed"};
            int[] colWidth = {23, 15, 15, 15};
            displayTable(rs, headings, colWidth);
        } 
        catch (SQLException e) 
        {
            System.out.println ("showAllTraces query unsuccessful");
        }
    } // 1.

    /* 2.    */
    public void betweenTimes() 
    {
        try 
        {
            Statement stmt = conn.createStatement();

            String sql = "select * from NBUSER.\"traces\"\n" +
                "where \"traces\".\"trace_time\" between '2013-02-28 18:07:36' and '2013-02-28 18:39:22'";

            ResultSet rs = stmt.executeQuery(sql);
            String[] headings = {"Timestamp", "Latitude", "Longitude", "Speed"};
            int[] colWidth = {23, 15, 15, 15};
            displayTable(rs, headings, colWidth);
        } 
        catch (SQLException e) 
        {
            System.out.println("betweenTimes query unsuccessful\n" + e);
        }
   } // 2.

    /* 3.    */
    public void aroundLocation() 
    {
        try 
        {
            Statement stmt = conn.createStatement();

	    String sql = "select \"speed\"*1.6 AS \"Speed in km/h\"\n" +
                "from \"traces\" \n" +
                "where \"latitude\" between -33.82 and -33.745 and \"longitude\" between 18.79 and 19.01";

	    ResultSet rs = stmt.executeQuery(sql);
            String[] headings = {"Speed"};
            int[] colWidth = {15};
            displayTable(rs, headings, colWidth);
	} 
        catch (SQLException e) 
        {
            System.out.println("aroundLocation query unsuccessful\n" + e);
        }
   } // 3.

    /*  4.  */
    public void countSpeedLimitExceedings() 
    {
        try 
        {
	    Statement stmt = conn.createStatement();

	    String sql = "select count(*) AS \"Number of traces indicating overspeeding\"\n" +
                "from NBUSER.\"traces\" \n" +
                "where \"speed\"*1.6 > 120";

	    ResultSet rs = stmt.executeQuery(sql);
	    String[] headings = {"Amount of traces where speedlimit is breached"};
	    int[] colWidth = {8};
	    displayTable(rs, headings, colWidth);
        } 
        catch (SQLException e) 
        {
	    System.out.println("countSpeedLimitExceedings query unsuccessful\n" + e);
        }
    } // 4.

     /* 5.   */
     public void top5North() 
     {
        try 
        {
	    Statement stmt = conn.createStatement();

	    String sql = "select \"trace_time\"\n" +
                "from NBUSER.\"traces\" \n" +
                "ORDER BY \"latitude\" DESC\n" +
                "fetch first 5 rows only";

	    ResultSet rs = stmt.executeQuery(sql);
            String[] headings = {"Timestamp"};
            int[] colWidth = {23};
            displayTable(rs, headings, colWidth);
        } 
        catch (SQLException e) 
        {
            System.out.println("top5North query unsuccessful\n" + e);
        }
    } // 5.

    /*  6.   */
    public void averageSpeed() 
    {
	 try {
            Statement stmt = conn.createStatement();
            String sql = "select \"trace_time\", \"speed\" from \"traces\"";
            ResultSet rs = stmt.executeQuery(sql);
            displayAverageSpeed(rs);
        } 
        catch (SQLException e) 
        {
            System.out.println("averageSpeed query unsuccessful\n" + e);
        }
    } // 6.

    
    
    ///////////////////////////////////////////////
    
    void displayTable(ResultSet rs, String[] headings, int[] colWidth) 
    {
        for (int i = 0; i < headings.length; i++) 
        {
            System.out.print(addSpaces(headings[i], colWidth[i]));
        }
        System.out.println("");
        for (int i = 0; i < headings.length; i++) 
        {
            for (int j = 0; j < colWidth[i]; j++) 
            {
                System.out.print("=");
            }
        }
        System.out.println("");

        try 
        {
            while (rs.next()) 
            {
                for (int i = 0; i < headings.length; i++) 
                {
                    System.out.print(addSpaces(rs.getString(i+1), colWidth[i]));
                }
                System.out.println();
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    void displayAverageSpeed(ResultSet rs)
    {
        double speed1, speed2;
        double distance = 0.0;
        String time1str, time2str;
        int timediff = 0;
        
        try 
        {
            rs.next();
            time1str = rs.getString(1);
            speed1 = Double.parseDouble(rs.getString(2));
            while (rs.next()) 
            {
                time2str = rs.getString(1);
                speed2 = Double.parseDouble(rs.getString(2));
                timediff = getTimeDifference(time1str, time2str);
                distance += (speed1+speed2)*1.6/2 * timediff/60/60;
                time1str = time2str;
                speed1 = speed2;
            }
            System.out.println("\nDistance = " + String.valueOf(distance) );
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    public int getTimeDifference(String startTime, String endTime)
    {
        int y,M,d,h,m,s;
        int timeDifferenceInHoura = 0;
        
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try
        {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            timeDifferenceInHoura = (int) ((date2.getTime()-date1.getTime())/1000.0);
        }
        catch (Exception ep)
        {
            System.out.println(ep);
        }
        
        return timeDifferenceInHoura;
    }

    private String addSpaces(String str, int colWidth) 
    {
        String temp = str;
        for (int i = colWidth; i > str.length(); i--) 
        {
            temp = temp + " ";
        }
        return temp;
    }
    
    
    public double haversine(double lat1, double lon1, double lat2, double lon2)
    {
        /*
        Inputs:     coordinates of two points in degrees
        Output:     distance in km between the two coordinates (on a round Earth)
        */
        double dLat,dLon,a,c;
        lat1 *= 0.0174532925;   // Convert from degrees to radians
        lon1 *= 0.0174532925;
        lat2 *= 0.0174532925;
        lon2 *= 0.0174532925;
        dLat = lat2 - lat1;
        dLon = lon2 - lon1;
        a = Math.pow(Math.sin(dLat/2),2) + Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin(dLon/2),2);
        c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        return 6371.135*c;
    }
    
    public static void main(String[] args) {
        new PRG_IT_2015_march_test();
    }
}
