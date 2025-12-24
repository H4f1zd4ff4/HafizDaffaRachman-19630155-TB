/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ACER
 */
public class FormAset extends javax.swing.JFrame {

    /**
     * Creates new form FormAset
     */
    public FormAset() {
        initComponents();
        tampilkan_combo();
        tampilkan_tabel();
        tampilkan_transaksi();
    }
    
    private void tampilkan_combo() {
        try {
            // Bersihkan combo box dari data bawaan (Item 1, Item 2)
            cbKategori.removeAllItems(); 
            
            String sql = "SELECT * FROM kategori";
            java.sql.Connection conn = (java.sql.Connection)Koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            while(res.next()){
                // Mengambil kolom 'nama_kategori' dari database
                cbKategori.addItem(res.getString("nama_kategori"));
            }
        } catch (Exception e) {
            System.out.println("Gagal isi Combo: " + e.getMessage());
        }
    }

    private void tampilkan_tabel() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID_ASLI"); // Kolom 0 (Akan kita sembunyikan)
    model.addColumn("No.");    // Kolom 1 (Nomor urut 1, 2, 3)
    model.addColumn("Nama Aset");
    model.addColumn("Kategori");
    model.addColumn("Jumlah");
    
    try {
        int nomorUrut = 1;
        // Ambil id_aset juga di SQL
        String sql = "SELECT aset.id_aset, aset.nama_aset, kategori.nama_kategori, aset.jumlah "
                   + "FROM aset JOIN kategori ON aset.id_kategori = kategori.id_kategori";
        Connection conn = (Connection)Koneksi.configDB();
        Statement stm = conn.createStatement();
        ResultSet res = stm.executeQuery(sql);
        
        while(res.next()){
            model.addRow(new Object[]{
                res.getString("id_aset"), // Masuk ke kolom 0
                nomorUrut++,              // Masuk ke kolom 1
                res.getString("nama_aset"),
                res.getString("nama_kategori"),
                res.getString("jumlah")
            });
        }
        tabelData.setModel(model);

        // --- KODE SEMBUNYIKAN KOLOM ID (Index 0) ---
        tabelData.getColumnModel().getColumn(0).setMinWidth(0);
        tabelData.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelData.getColumnModel().getColumn(0).setWidth(0);
        
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    private void cetak_excel() {
    try {
        // 1. Tentukan lokasi dan nama file (Format .xls agar langsung dikenali Excel)
        String namaFile = "Laporan_Aset_Inventaris.xls";
        java.io.FileWriter fw = new java.io.FileWriter(namaFile);
        
        // 2. Ambil data dari Database
        java.sql.Connection conn = (java.sql.Connection)Koneksi.configDB();
        java.sql.Statement stm = conn.createStatement();
        String sql = "SELECT aset.id_aset, aset.nama_aset, kategori.nama_kategori, aset.jumlah " +
                     "FROM aset JOIN kategori ON aset.id_kategori = kategori.id_kategori";
        java.sql.ResultSet res = stm.executeQuery(sql);
        
        // 3. Menulis Header/Judul Kolom
        // Gunakan \t (Tab) agar Excel otomatis membagi menjadi kolom
        fw.write("ID Aset \t Nama Aset \t Kategori \t Jumlah \n");
        
        // 4. Melakukan perulangan untuk memasukkan semua data database ke baris Excel
        while (res.next()) {
            fw.write(res.getString(1) + " \t "); // ID
            fw.write(res.getString(2) + " \t "); // Nama
            fw.write(res.getString(3) + " \t "); // Kategori
            fw.write(res.getString(4) + " \n "); // Jumlah (pindah baris)
        }
        
        fw.close(); // Menutup file setelah selesai menulis
        
        // 5. Pesan Berhasil
        JOptionPane.showMessageDialog(null, "Laporan Excel berhasil dibuat!");
        
        // 6. Otomatis membuka file Excel tersebut jika di komputer ada Excel
        java.awt.Desktop.getDesktop().open(new java.io.File(namaFile));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal Membuat Excel: " + e.getMessage());
    }
}
    
    private void tampilkan_transaksi() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No");
    model.addColumn("ID Barang");
    model.addColumn("Tipe");
    model.addColumn("Jumlah");
    model.addColumn("Waktu");

    try {
        int no = 1;
        // Mengambil data dari tabel transaksi yang baru saja Anda buat
        String sql = "SELECT * FROM transaksi"; 
        java.sql.Connection conn = (java.sql.Connection)Koneksi.configDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet res = stm.executeQuery(sql);
        
        while(res.next()){
            model.addRow(new Object[]{
                no++, 
                res.getString("id_aset"), 
                res.getString("jenis_transaksi"), 
                res.getString("jumlah_transaksi"),
                res.getString("tanggal_transaksi")
            });
        }
        tabelTransaksi.setModel(model);
        
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        cbKategori = new javax.swing.JComboBox<>();
        txtJumlah = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aplikasi Inventaris Aset");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Nama : ");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Kategori : ");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Jumlah : ");

        cbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        btnSimpan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelData);

        btnHapus.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnLaporan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLaporan.setText("Cetak");
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelTransaksi);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Riwayat Transaksi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNama)
                                    .addComponent(cbKategori, 0, 185, Short.MAX_VALUE)
                                    .addComponent(txtJumlah)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(535, 535, 535)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(385, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(71, 71, 71)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        
        try {
    // 1. Ambil data dari inputan
    String nama = txtNama.getText();
    String kategori = (String) cbKategori.getSelectedItem();
    String jumlah = txtJumlah.getText();

    Connection conn = (Connection)Koneksi.configDB();
    
    // 2. Simpan ke tabel 'aset'
    String sqlAset = "INSERT INTO aset (nama_aset, id_kategori, jumlah) VALUES (?, ?, ?)";
    PreparedStatement pstAset = conn.prepareStatement(sqlAset, Statement.RETURN_GENERATED_KEYS);
    pstAset.setString(1, nama);
    pstAset.setString(2, "1"); // Contoh ID kategori, sesuaikan dengan logika Anda
    pstAset.setString(3, jumlah);
    pstAset.executeUpdate();

    // 3. Ambil ID aset yang baru saja dibuat untuk riwayat
    ResultSet rs = pstAset.getGeneratedKeys();
    int idBaru = 0;
    if (rs.next()) {
        idBaru = rs.getInt(1);
    }

    // 4. OTOMATIS simpan ke tabel 'transaksi' sebagai riwayat
    String sqlTrans = "INSERT INTO transaksi (id_aset, jenis_transaksi, jumlah_transaksi) VALUES (?, 'Masuk', ?)";
    PreparedStatement pstTrans = conn.prepareStatement(sqlTrans);
    pstTrans.setInt(1, idBaru);
    pstTrans.setString(2, jumlah);
    pstTrans.executeUpdate();

    // 5. Refresh kedua tabel agar muncul datanya
    tampilkan_tabel();     // Refresh tabel atas
    tampilkan_transaksi(); // Refresh tabel bawah (Riwayat)

    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan dan Tercatat di Riwayat!");

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, e.getMessage());
}
        
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        try {
            int baris = tabelData.getSelectedRow();
            String id = tabelData.getValueAt(baris, 0).toString();
            String sql = "DELETE FROM aset WHERE id_aset = '" + id + "'";
            Connection conn = Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            tampilkan_tabel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        // TODO add your handling code here:
        cetak_excel();
    }//GEN-LAST:event_btnLaporanActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(FormAset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAset().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelData;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
