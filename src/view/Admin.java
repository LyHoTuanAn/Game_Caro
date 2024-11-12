package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import dao.UserDAO;

import javax.swing.JOptionPane;

import model.User;
import controller.Room;
import controller.Server;
import controller.ServerThread;

/**
 * @author Admin
 */
public class Admin extends JFrame implements Runnable {
    private final UserDAO userDAO;

    /**
     * Creates new form Admin
     */
    public Admin() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        threadRoomListView.setEditable(false);
        messageView.setEditable(false);
        userDAO = new UserDAO();
    }


    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        threadRoomListView = new JTextArea();
        viewThreadButton = new JButton();
        viewRoomListButton = new JButton();
        jPanel1 = new JPanel();
        boardLabel = new JLabel();
        noticeTextField = new JTextField();
        publishMessageButton = new JButton();
        jScrollPane2 = new JScrollPane();
        messageView = new JTextArea();
        userIdTextField = new JTextField();
        banButton = new JButton();
        warnButton = new JButton();
        cancelBanButton = new JButton();
        reasonComboBox = new JComboBox<>();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();

        //======== jScrollPane1 ========
        {

            //---- threadRoomListView ----
            threadRoomListView.setColumns(20);
            threadRoomListView.setFont(new Font("Tahoma", Font.PLAIN, 14));
            threadRoomListView.setRows(5);
            jScrollPane1.setViewportView(threadRoomListView);
        }

        //---- viewThreadButton ----
        viewThreadButton.setText("Xem danh s\u00e1ch lu\u1ed3ng");
        viewThreadButton.addActionListener(e -> viewThreadButtonActionPerformed(e));

        //---- viewRoomListButton ----
        viewRoomListButton.setText("Xem danh s\u00e1ch ph\u00f2ng");
        viewRoomListButton.addActionListener(e -> viewRoomListButtonActionPerformed(e));

        //======== jPanel1 ========
        {
            jPanel1.setBackground(new Color(0x666666));
            jPanel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing.
            border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER
            , javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font
            .BOLD ,12 ), java. awt. Color. red) ,jPanel1. getBorder( )) ); jPanel1. addPropertyChangeListener (
            new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order"
            .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );

            //---- boardLabel ----
            boardLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            boardLabel.setForeground(Color.white);
            boardLabel.setHorizontalAlignment(SwingConstants.CENTER);
            boardLabel.setText("Admin");

            GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup()
                    .addComponent(boardLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup()
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(boardLabel)
                        .addContainerGap(21, Short.MAX_VALUE))
            );
        }

        //---- noticeTextField ----
        noticeTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noticeTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                noticeTextFieldKeyPressed(e);
            }
        });

        //---- publishMessageButton ----
        publishMessageButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        publishMessageButton.setText("Ph\u00e1t th\u00f4ng b\u00e1o");
        publishMessageButton.addActionListener(e -> publishMessageButtonActionPerformed(e));

        //======== jScrollPane2 ========
        {

            //---- messageView ----
            messageView.setColumns(20);
            messageView.setRows(5);
            jScrollPane2.setViewportView(messageView);
        }

        //---- banButton ----
        banButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        banButton.setText("Ban");
        banButton.addActionListener(e -> banButtonActionPerformed(e));

        //---- warnButton ----
        warnButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        warnButton.setText("C\u1ea3nh c\u00e1o");
        warnButton.addActionListener(e -> warnButtonActionPerformed(e));

        //---- cancelBanButton ----
        cancelBanButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelBanButton.setText("Hu\u1ef7 Ban");
        cancelBanButton.addActionListener(e -> cancelBanButtonActionPerformed(e));

        //---- reasonComboBox ----
        reasonComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reasonComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
            "Ch\u1ecdn l\u00fd do",
            "Ng\u00f4n ng\u1eef th\u00f4 t\u1ee5c - x\u00fac ph\u1ea1m ng\u01b0\u1eddi kh\u00e1c",
            "Spam \u0111\u0103ng nh\u1eadp",
            "S\u1eed d\u1ee5ng game v\u1edbi m\u1ee5c \u0111\u00edch x\u1ea5u",
            "Ph\u00e1t hi\u1ec7n r\u00f2 r\u1ec9 b\u1ea3o m\u1eadt - t\u00e0i kho\u1ea3n t\u1ea1m th\u1eddi b\u1ecb kho\u00e1 \u0111\u1ec3 ki\u1ec3m tra th\u00eam"
        }));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(viewThreadButton, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
                    .addGap(114, 114, 114)
                    .addComponent(viewRoomListButton, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addGap(61, 61, 61))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane2))
                            .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(noticeTextField, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(publishMessageButton))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(userIdTextField, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(reasonComboBox, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cancelBanButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(warnButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(banButton, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(viewRoomListButton)
                        .addComponent(viewThreadButton, GroupLayout.Alignment.TRAILING))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(noticeTextField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(publishMessageButton))
                    .addGap(18, 18, 18)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(userIdTextField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                        .addComponent(banButton)
                        .addComponent(warnButton)
                        .addComponent(cancelBanButton)
                        .addComponent(reasonComboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(20, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void viewThreadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewThreadButtonActionPerformed
        StringBuilder res = new StringBuilder();
        String room;
        int i = 1;
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            if (serverThread.getRoom() == null)
                room = null;
            else room = "" + serverThread.getRoom().getId();
            if (serverThread.getUser() != null) {
                res.append(i).append(". Client-number: ").append(serverThread.getClientNumber()).append(", User-ID: ").append(serverThread.getUser().getID()).append(", Room: ").append(room).append("\n");
            } else {
                res.append(i).append(". Client-number: ").append(serverThread.getClientNumber()).append(", User-ID: null, Room: ").append(room).append("\n");
            }
            i++;
        }
        threadRoomListView.setText(res.toString());
    }//GEN-LAST:event_viewThreadButtonActionPerformed

    private void viewRoomListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewRoomListButtonActionPerformed
        StringBuilder res = new StringBuilder();
        int i = 1;
        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
            Room room1 = serverThread.getRoom();
            String listUser = "List user ID: ";
            if (room1 != null) {
                if (room1.getNumberOfUser() == 1) {
                    listUser += room1.getUser1().getUser().getID();
                } else {
                    listUser += room1.getUser1().getUser().getID() + ", " + room1.getUser2().getUser().getID();
                }
                res.append(i).append(". Room_ID: ").append(room1.getId()).append(", Number of player: ").append(room1.getNumberOfUser()).append(", ").append(listUser).append("\n");
                i++;
            }

        }
        threadRoomListView.setText(res.toString());
    }//GEN-LAST:event_viewRoomListButtonActionPerformed

    private void publishMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishMessageButtonActionPerformed
        sendMessage();
    }//GEN-LAST:event_publishMessageButtonActionPerformed

    private void noticeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noticeTextFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            sendMessage();
        }
    }//GEN-LAST:event_noticeTextFieldKeyPressed

    private void banButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banButtonActionPerformed
        try {
            if (isInvalidForm()) return;
            int userId = Integer.parseInt(userIdTextField.getText());
            User user = new User();
            user.setID(userId);
            userDAO.updateBannedStatus(user, true);
            ServerThread serverThread = Server.serverThreadBus.getServerThreadByUserID(userId);
            serverThread.write("banned-notice," + reasonComboBox.getSelectedItem());
            if (serverThread.getRoom() != null) {
                Room room = serverThread.getRoom();
                ServerThread competitorThread = room.getCompetitor(serverThread.getClientNumber());
                room.setUsersToNotPlaying();
                if (competitorThread != null) {
                    room.decreaseNumberOfGame();
                    competitorThread.write("left-room,");
                    competitorThread.setRoom(null);
                }
                serverThread.setRoom(null);
            }
            Server.admin.addMessage("User có ID " + userId + " đã bị BAN");
            serverThread.setUser(null);
            Server.serverThreadBus.boardCast(-1, "chat-server," + "User có ID " + userId + " đã bị BAN");
            JOptionPane.showMessageDialog(rootPane, "Đã BAN user " + userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
        }
    }//GEN-LAST:event_banButtonActionPerformed

    private void cancelBanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBanButtonActionPerformed
        try {
            if (userIdTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập ID của User");
                return;
            }
            int userId = Integer.parseInt(userIdTextField.getText());
            User user = new User();
            user.setID(userId);
            userDAO.updateBannedStatus(user, false);
            userIdTextField.setText("");
            JOptionPane.showMessageDialog(rootPane, "Đã huỷ BAN user " + userId);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
        }
    }//GEN-LAST:event_cancelBanButtonActionPerformed

    private void warnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warnButtonActionPerformed
        try {
            if (isInvalidForm()) return;
            int userId = Integer.parseInt(userIdTextField.getText());
            Server.serverThreadBus.sendMessageToUserID(userId, "warning-notice," + reasonComboBox.getSelectedItem());
            JOptionPane.showMessageDialog(rootPane, "Đã cảnh cáo user " + userId);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
        }
    }//GEN-LAST:event_warnButtonActionPerformed

    private void sendMessage() {
        String message = noticeTextField.getText();
        if (message.isEmpty()) return;
        String temp = messageView.getText();
        temp += "Thông báo từ máy chủ : " + message + "\n";
        messageView.setText(temp);
        messageView.setCaretPosition(messageView.getDocument().getLength());
        Server.serverThreadBus.boardCast(-1, "chat-server,Thông báo từ máy chủ : " + message);
        noticeTextField.setText("");
    }

    public void addMessage(String message) {
        String tmp = messageView.getText();
        tmp = tmp + message + "\n";
        messageView.setText(tmp);
        messageView.setCaretPosition(threadRoomListView.getDocument().getLength());
    }

    private boolean isInvalidForm() {
        if (userIdTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập ID của User");
            return true;
        }
        if (reasonComboBox.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn lý do");
            return true;
        }
        return false;
    }

    private JScrollPane jScrollPane1;
    private JTextArea threadRoomListView;
    private JButton viewThreadButton;
    private JButton viewRoomListButton;
    private JPanel jPanel1;
    private JLabel boardLabel;
    private JTextField noticeTextField;
    private JButton publishMessageButton;
    private JScrollPane jScrollPane2;
    public static JTextArea messageView;
    private JTextField userIdTextField;
    private JButton banButton;
    private JButton warnButton;
    private JButton cancelBanButton;
    private JComboBox<String> reasonComboBox;

    @Override
    public void run() {
        new Admin().setVisible(true);
    }
}
