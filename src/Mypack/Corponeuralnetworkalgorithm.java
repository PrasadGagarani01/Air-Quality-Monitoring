package Mypack;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.DBconnect;

/**
 * Servlet implementation class CorpoPrediction
 */
@WebServlet("/Corponeuralnetworkalgorithm")
public class Corponeuralnetworkalgorithm extends HttpServlet {
	
       
	/**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Statement st1 = null;
        ResultSet rs1 = null;
        
        Statement st = null;
       
        int kit = Integer.parseInt(request.getParameter("kitTxt"));
        CorpokitGetSet.setKit(kit);
        String noofdays = request.getParameter("daysTxt");
        
        
        try {
        	Connection conn = DBconnect.getConnect();
        	
            String sql = "select * from airquuality where kid = '" + kit + "' order by date desc limit 3";
            st1=conn.prepareStatement(sql);
            
            long[] datevalues = new long[3];
            
            double[] tempvalues = new double[3];
            double[] humvalues = new double[3];
            double[] mq2values = new double[3];
            double[] mq5values = new double[3];
            double[] mq135values = new double[3];
           

            int count = 0;
       
            ResultSet rs = st1.executeQuery(sql);
            while (rs.next()) {
               
            	datevalues[count] = new SimpleDateFormat("yyyy.MM.dd").parse(rs.getString("date")).getTime();
                System.out.println("1 "+datevalues[count]);
                
                tempvalues[count] = Double.parseDouble(rs.getString("temp"));
                humvalues[count] = Double.parseDouble(rs.getString("hum"));
                mq2values[count] = Double.parseDouble(rs.getString("mq2"));
                mq5values[count] = Double.parseDouble(rs.getString("mq5"));
                mq135values[count] = Double.parseDouble(rs.getString("mq135"));
                
                count++;
            }

            double slopetemp = PredictionAnalysis.slope(tempvalues[2], tempvalues[0], datevalues[2], datevalues[0]);
            double slopehum = PredictionAnalysis.slope(humvalues[2], humvalues[0], datevalues[2], datevalues[0]);
            double slopemq2 = PredictionAnalysis.slope(mq2values[2], mq2values[0], datevalues[2], datevalues[0]);
            double slopemq5 = PredictionAnalysis.slope(mq5values[2], mq5values[0], datevalues[2], datevalues[0]);
            double slopemq135 = PredictionAnalysis.slope(mq135values[2], mq135values[0], datevalues[2], datevalues[0]);
       
            Calendar c = new GregorianCalendar();
            String sql1 = "SELECT MAX(`date`) as last_date FROM `airquuality` "
                    + "WHERE `kid`='" + kit + "';";
            System.out.println("sql1 "+sql1);
            
            st=conn.prepareStatement(sql1);
            
            ResultSet last_dates = st.executeQuery(sql1);
            String date = null;
            
            
            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            if (last_dates.next()) {
                date = last_dates.getString(1);
                System.out.println("last_dates "+date);
                c.setTime(dbDateFormat.parse(date));//DateFormat.getInstance().parse("2015-04-01"));
                System.out.println("c "+c.getTime());
                c.add(Calendar.DATE, Integer.parseInt(noofdays));
                System.out.println("c add "+c.getTime());
            }


            double predicttemp = PredictionAnalysis.predict(tempvalues[0], slopetemp, c.getTimeInMillis(), datevalues[0]);

            double predicthum = PredictionAnalysis.predict(humvalues[0], slopehum, c.getTimeInMillis(), datevalues[0]);
            double predictmq2 = PredictionAnalysis.predict(mq2values[0], slopemq2, c.getTimeInMillis(), datevalues[0]);
            double predictmq5  = PredictionAnalysis.predict(mq5values[0], slopemq5, c.getTimeInMillis(), datevalues[0]);
            double predictmq135 = PredictionAnalysis.predict(mq135values[0], slopemq135, c.getTimeInMillis(), datevalues[0]);
           String predict_date = dbDateFormat.format(c.getTime());
            System.out.println(" c**** " + predict_date);

//            sql = "DELETE `predict_stock` "
//                        + "WHERE stock_date='" + predict_date + "' and stock_name='" + stockname + "';";
//                System.out.println("line 103 " + sql);
//                st1 = dbconn.connect();
//                st1.executeUpdate(sql);
            
            //  SELECT `id`, `kid`, `date`, `time`, `temp`, `hum`, `mq2`, `mq5`, `mq135` FROM `airquuality` WHERE 1
            
            System.out.println();
            sql = "INSERT INTO `predict_airquuality` (`id`, `kid`, `date`, `temp`, `hum`, `mq2`, `mq5`, mq135) "
                    + "VALUES (NULL, '" + kit + "', '" + predict_date + "', '" + predicttemp + "', '" + predicthum + "', '" + predictmq2 + "', '" + predictmq5 + "',"
                    + "'" + predictmq135 + "');";
            System.out.println("line 103 " + sql);
            
            
            
            st1=conn.prepareStatement(sql);
            st1.executeUpdate(sql);

//            sql1 = "SELECT * FROM `actual_predict_stock` "
//                    + "WHERE `stock_date`='" + predict_date + "';";
//            System.out.println("sql1 "+sql1);
//            ResultSet actualPredictStock = dbconn.connect().executeQuery(sql1);
//
//            if (actualPredictStock.next()) {
//                predictopen = actualPredictStock.getDouble("open");
//                predictclose = actualPredictStock.getDouble("close");
//                predictvolume = actualPredictStock.getDouble("volume");
//                predictadjust_close = actualPredictStock.getDouble("adjust_close");
//                predictindex_ratio = actualPredictStock.getDouble("index_ratio");
//                predictpe_ratio = actualPredictStock.getDouble("pe_ratio");
//                predictsma = actualPredictStock.getDouble("sma");
//                predictobv = actualPredictStock.getDouble("obv");
//                predictproc = actualPredictStock.getDouble("proc");

//                double predictedClose = TrainingTesting.PredictParameters(predictopen, predictclose, predictvolume, predictadjust_close, predictindex_ratio, predictpe_ratio,
//                        predictsma, predictobv, predictproc, stockname, getServletContext().getRealPath("/") + "/",closevalues[0]);

//                if(!Double.isNaN(predictedClose))
//                {predictclose = predictedClose;
//                sql = "UPDATE `predict_stock` SET `close`='" + predictclose + "' "
//                        + "WHERE stock_date='" + predict_date + "' and stock_name='" + stockname + "';";
//                System.out.println("line 103 " + sql);
//                st1 = dbconn.connect();
//                st1.executeUpdate(sql);}
                HttpSession session = request.getSession(true);
//            if (Testtrend) {'
                
                
//                session.setAttribute("flash_message", "Stock " + stockname + " has predicted close value of " + predictclose + " for "+predict_date);//increasing trend");
//                session.setAttribute("flash_type", "success");
                
                
//            } else {
//                session.setAttribute("flash_message", "Stock " + stockname + " has decreasing trend");
//                session.setAttribute("flash_type", "success");
//            }
//            }
            response.sendRedirect("viewCorpoPredictTle.jsp");

        } catch (Exception ex) {
            Logger.getLogger(Corponeuralnetworkalgorithm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
//    public static void main(String[] args) {
//        
//        
//        try {
//            Statement st1 = dbconn.connect();
//            String stockname="SBIN.NS";
//            String noofdays="5";
//            String sql = "select * from stock where stock_name = '" + stockname + "' order by stock_date desc limit 3";
//            int[] openvalues = new int[3];
//            int[] closevalues = new int[3];
//            int[] volumevalues = new int[3];
//            long[] timevalues = new long[3];
//            double[] adjust_closevalues = new double[3];
//
//            double[] index_ratiovalues = new double[3];
//            double[] pe_ratiovalues = new double[3];
//            double[] smavalues = new double[3];
//            double[] obvvalues = new double[3];
//            double[] procvalues = new double[3];
//
//            int count = 0;
//            ResultSet rs = st1.executeQuery(sql);
//            while (rs.next()) {
//                openvalues[count] = rs.getInt("open");
//                closevalues[count] = rs.getInt("close");
//                volumevalues[count] = rs.getInt("volume");
//                timevalues[count] = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("stock_date")).getTime();
//                adjust_closevalues[count] = rs.getDouble("adjust_close");
//                index_ratiovalues[count] = rs.getDouble("index_ratio");
//                pe_ratiovalues[count] = rs.getDouble("pe_ratio");
//                smavalues[count] = rs.getDouble("sma");
//                obvvalues[count] = rs.getDouble("obv");
//                procvalues[count] = rs.getDouble("proc");
//
//                count++;
//            }
//
//            double slopeopen = PredictionAnalysis.slope(openvalues[2], openvalues[0], timevalues[2], timevalues[0]);
//            double slopeclose = PredictionAnalysis.slope(closevalues[2], closevalues[0], timevalues[2], timevalues[0]);
//            double slopevolume = PredictionAnalysis.slope(volumevalues[2], volumevalues[0], timevalues[2], timevalues[0]);
//            double slopeadjust_close = PredictionAnalysis.slope(adjust_closevalues[2], adjust_closevalues[0], timevalues[2], timevalues[0]);
//            double slopeindex_ratio = PredictionAnalysis.slope(index_ratiovalues[2], index_ratiovalues[0], timevalues[2], timevalues[0]);
//            double slopepe_ratio = PredictionAnalysis.slope(pe_ratiovalues[2], pe_ratiovalues[0], timevalues[2], timevalues[0]);
//            double slopesma = PredictionAnalysis.slope(smavalues[2], smavalues[0], timevalues[2], timevalues[0]);
//            double slopeobv = PredictionAnalysis.slope(obvvalues[2], obvvalues[0], timevalues[2], timevalues[0]);
//            double slopeproc = PredictionAnalysis.slope(procvalues[2], procvalues[0], timevalues[2], timevalues[0]);
//
//            Calendar c = new GregorianCalendar();
//            String sql1 = "SELECT MAX(`stock_date`) as last_date FROM `stock` "
//                    + "WHERE `stock_name`='" + stockname + "';";
//            System.out.println("sql1 "+sql1);
//            ResultSet last_dates = dbconn.connect().executeQuery(sql1);
//            String date = null;
//            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            if (last_dates.next()) {
//                date = last_dates.getString(1);
//                System.out.println("last_dates "+date);
//                c.setTime(dbDateFormat.parse(date));//DateFormat.getInstance().parse("2015-04-01"));
//                System.out.println("c "+c.getTime());
//                
//                c.add(Calendar.DATE, Integer.parseInt(noofdays));
//                System.out.println("c add "+c.getTime());
//            }
//            
//            
//            double predictopen = PredictionAnalysis.predict(openvalues[0], slopeopen, c.getTimeInMillis(), timevalues[0]);
//            double predictclose = PredictionAnalysis.predict(closevalues[0], slopeclose, c.getTimeInMillis(), timevalues[0]);
//            double predictvolume = PredictionAnalysis.predict(volumevalues[0], slopevolume, c.getTimeInMillis(), timevalues[0]);
//            double predictadjust_close = PredictionAnalysis.predict(adjust_closevalues[0], slopeadjust_close, c.getTimeInMillis(), timevalues[0]);
//            double predictindex_ratio = PredictionAnalysis.predict(index_ratiovalues[0], slopeindex_ratio, c.getTimeInMillis(), timevalues[0]);
//            double predictpe_ratio = PredictionAnalysis.predict(pe_ratiovalues[0], slopepe_ratio, c.getTimeInMillis(), timevalues[0]);
//            double predictsma = PredictionAnalysis.predict(smavalues[0], slopesma, c.getTimeInMillis(), timevalues[0]);
//            double predictobv = PredictionAnalysis.predict(obvvalues[0], slopeobv, c.getTimeInMillis(), timevalues[0]);
//            double predictproc = PredictionAnalysis.predict(procvalues[0], slopeproc, c.getTimeInMillis(), timevalues[0]);
//            String predict_date = dbDateFormat.format(c.getTime());
//            System.out.println(" c**** " + predict_date);
//
//            sql = "INSERT INTO `predict_stock` (`id`, `stock_name`, `stock_date`, `open`, `close`, `volume`, `adjust_close`, index_ratio, pe_ratio, sma, obv, proc) "
//                    + "VALUES (NULL, '" + stockname + "', '" + predict_date + "', '" + predictopen + "', '" + predictclose + "', '" + predictvolume + "', '" + predictadjust_close + "',"
//                    + "'" + predictindex_ratio + "','" + predictpe_ratio + "','" + predictsma + "','" + predictobv + "','" + predictproc + "');";
//            System.out.println("line 103 " + sql);
//            st1 = dbconn.connect();
//            st1.executeUpdate(sql);            
//            
//            double predictedClose = TrainingTesting.PredictParameters(predictopen, predictclose, predictvolume, predictadjust_close, predictindex_ratio, predictpe_ratio,
//                    predictsma, predictobv, predictproc, stockname, "",closevalues[0]);
//            
//            predictclose = predictedClose;
//            sql = "UPDATE `predict_stock` SET `close`='" + predictclose + "' "
//                    + "WHERE stock_date='" + predict_date + "' and stock_name='" + stockname + "';";
//            System.out.println("line 103 " + sql);
//        } catch (Exception ex) {
//            Logger.getLogger(Prediction.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
