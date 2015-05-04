package Botha_Daniel_HW8;

public class PointsOfInterest extends javax.swing.JFrame 
{

    public PointsOfInterest() 
    {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfPOI1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfPOI2 = new javax.swing.JTextField();
        btnGetDistance = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taOutput = new javax.swing.JTextArea();
        btnDrive = new javax.swing.JButton();
        btnGetAllDistances = new javax.swing.JButton();
        btnArrDep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(tfPOI1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 155, -1));

        jLabel1.setText("Point of Interest 1:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        jLabel2.setText("Point of Interest 2:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));
        getContentPane().add(tfPOI2, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 30, 150, -1));

        btnGetDistance.setText("Calculate Distance");
        btnGetDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetDistanceActionPerformed(evt);
            }
        });
        getContentPane().add(btnGetDistance, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 130, -1));

        taOutput.setColumns(20);
        taOutput.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        taOutput.setRows(5);
        jScrollPane1.setViewportView(taOutput);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, 470, 200));

        btnDrive.setText("Driving Distances");
        btnDrive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDriveActionPerformed(evt);
            }
        });
        getContentPane().add(btnDrive, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 140, -1));

        btnGetAllDistances.setText("All Distances");
        btnGetAllDistances.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAllDistancesActionPerformed(evt);
            }
        });
        getContentPane().add(btnGetAllDistances, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, -1));

        btnArrDep.setText("Arrival and Departures");
        btnArrDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArrDepActionPerformed(evt);
            }
        });
        getContentPane().add(btnArrDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 150, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGetDistanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetDistanceActionPerformed
        String poi1 = tfPOI1.getText();
        String poi2 = tfPOI2.getText();
        new PRG_IT_2015_march_test().distancePointsOutput(poi1, poi2, taOutput);
    }//GEN-LAST:event_btnGetDistanceActionPerformed

    private void btnDriveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDriveActionPerformed
        
    }//GEN-LAST:event_btnDriveActionPerformed

    private void btnGetAllDistancesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllDistancesActionPerformed
        new PRG_IT_2015_march_test().distanceAll(taOutput);
    }//GEN-LAST:event_btnGetAllDistancesActionPerformed

    private void btnArrDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArrDepActionPerformed
        new PRG_IT_2015_march_test().arrDepartTimes(taOutput);
    }//GEN-LAST:event_btnArrDepActionPerformed
    public static void main(String args[]) 
    {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PointsOfInterest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PointsOfInterest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PointsOfInterest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PointsOfInterest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PointsOfInterest().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArrDep;
    private javax.swing.JButton btnDrive;
    private javax.swing.JButton btnGetAllDistances;
    private javax.swing.JButton btnGetDistance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taOutput;
    private javax.swing.JTextField tfPOI1;
    private javax.swing.JTextField tfPOI2;
    // End of variables declaration//GEN-END:variables
}
