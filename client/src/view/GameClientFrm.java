package view;


import controller.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.swing.*;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import model.User;



/**
 * @author Admin
 */
public class GameClientFrm extends javax.swing.JFrame {
    private int currentPlayer;
    private final User competitor;
    private final JButton[][] button;
    private final int[][] competitorMatrix;
    private final int[][] matrix;
    private final int[][] userMatrix;

    //if changing it you will need to redesign icon
    private final int size = 15;
    private final Timer timer;
    private Integer second;
    private Integer minute;
    private int numberOfMatch;
    private final String[] normalItem;
    private final String[] winItem;
    private final String[] iconItem;
    private final String[] preItem;

    private JButton preButton;
    private int userWin;
    private int competitorWin;
    private boolean isSending;
    private boolean isListening;
    private final String competitorIP;

    public GameClientFrm(User competitor, int room_ID, int isStart, String competitorIP) {
        initComponents();
        numberOfMatch = isStart;
        this.competitor = competitor;
        this.competitorIP = competitorIP;

        isSending = false;
        isListening = false;
        microphoneStatusButton.setIcon(new ImageIcon("assets/game/mute.png"));
        speakerStatusButton.setIcon(new ImageIcon("assets/game/mutespeaker.png"));

        //init score
        userWin = 0;
        competitorWin = 0;

        this.setTitle("Caro Game Nhóm 8");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);

        //Set layout dạng lưới cho panel chứa button
        gamePanel.setLayout(new GridLayout(size, size));

        //Setup play button
        button = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                gamePanel.add(button[i][j]);
            }
        }

        //SetUp play Matrix
        competitorMatrix = new int[size][size];
        matrix = new int[size][size];
        userMatrix = new int[size][size];

        //Setup UI
        playerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        competitorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setAlignmentX(JLabel.CENTER);
        sendButton.setBackground(Color.white);
        sendButton.setIcon(new ImageIcon("assets/image/send2.png"));
        playerNicknameValue.setText(Client.user.getNickname());
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        playerNumberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        playerButtonImage.setIcon(new ImageIcon("assets/game/" + Client.user.getAvatar() + ".jpg"));
        roomNameLabel.setText("Phòng: " + room_ID);
        vsIcon.setIcon(new ImageIcon("assets/game/swords-1.png"));
        competitorNicknameValue.setText(competitor.getNickname());
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        competitorNumberOfWinValue.setText(Integer.toString(competitor.getNumberOfWin()));
        competotorButtonImage.setIcon(new ImageIcon("assets/game/" + competitor.getAvatar() + ".jpg"));
        competotorButtonImage.setToolTipText("Xem thông tin đối thủ");
        playerCurrentPositionLabel.setVisible(false);
        competitorPositionLabel.setVisible(false);
        drawRequestButton.setVisible(false);
        playerTurnLabel.setVisible(false);
        competitorTurnLabel.setVisible(false);
        countDownLabel.setVisible(false);
        messageTextArea.setEditable(false);
        scoreLabel.setText("Tỉ số: 0-0");

        //Setup timer
        second = 60;
        minute = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = minute.toString();
                String temp1 = second.toString();
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                if (temp1.length() == 1) {
                    temp1 = "0" + temp1;
                }
                if (second == 0) {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second = 60;
                    minute = 0;
                    try {
                        Client.openView(Client.View.GAME_CLIENT, "Bạn đã thua do quá thời gian", "Đang thiết lập ván chơi mới");
                        increaseWinMatchToCompetitor();
                        Client.socketHandle.write("lose,");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                    }

                } else {
                    countDownLabel.setText("Thời Gian:" + temp + ":" + temp1);
                    second--;
                }

            }

        });

        //Setup icon
        normalItem = new String[2];
        normalItem[1] = "assets/image/o2.jpg";
        normalItem[0] = "assets/image/x2.jpg";
        winItem = new String[2];
        winItem[1] = "assets/image/owin.jpg";
        winItem[0] = "assets/image/xwin.jpg";
        iconItem = new String[2];
        iconItem[1] = "assets/image/o3.jpg";
        iconItem[0] = "assets/image/x3.jpg";
        preItem = new String[2];
        preItem[1] = "assets/image/o2_pre.jpg";
        preItem[0] = "assets/image/x2_pre.jpg";
        setupButton();

        setEnableButton(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitGame();
            }
        });

    }

    public void exitGame() {
        try {
            timer.stop();
            voiceCloseMic();
            voiceStopListening();
            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
            Client.openView(Client.View.HOMEPAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }

    public void stopAllThread() {
        timer.stop();
        voiceCloseMic();
        voiceStopListening();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        playerNumberOfWinLabel = new javax.swing.JLabel();
        playerTurnLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        playerNicknameLabel = new javax.swing.JLabel();
        playerNumberOfGameLabel = new javax.swing.JLabel();
        competitorNumberOfWinLabel = new javax.swing.JLabel();
        competitorNicknameLabel = new javax.swing.JLabel();
        competotorNumberOfGameLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        messageTextField = new javax.swing.JTextField();
        playerNicknameValue = new javax.swing.JLabel();
        playerNumberOfGameValue = new javax.swing.JLabel();
        playerNumberOfWinValue = new javax.swing.JLabel();
        competitorNicknameValue = new javax.swing.JLabel();
        competotorNumberOfGameValue = new javax.swing.JLabel();
        competitorNumberOfWinValue = new javax.swing.JLabel();
        countDownLabel = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        microphoneStatusButton = new javax.swing.JButton();
        speakerStatusButton = new javax.swing.JButton();
        scoreLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        drawRequestButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();
        competitorTurnLabel = new javax.swing.JLabel();
        playerCurrentPositionLabel = new javax.swing.JLabel();
        competitorPositionLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        playerButtonImage = new javax.swing.JLabel();
        vsIcon = new javax.swing.JLabel();
        competotorButtonImage = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        newGameMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
                jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
                jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
                jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
                jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
                jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
                jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
                jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);

        playerNumberOfWinLabel.setText("Số ván thắng");

        playerTurnLabel.setForeground(new java.awt.Color(255, 0, 0));
        playerTurnLabel.setText("Đến lượt bạn");

        playerNicknameLabel.setText("Nickname");

        playerNumberOfGameLabel.setText("Số ván chơi");

        competitorNumberOfWinLabel.setText("Số ván thắng");

        competitorNicknameLabel.setText("Nickname");

        competotorNumberOfGameLabel.setText("Số ván chơi");

        messageTextArea.setColumns(20);
        messageTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageTextArea.setRows(5);
        jScrollPane1.setViewportView(messageTextArea);

        messageTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyPressed(evt);
            }
        });

        playerNicknameValue.setText("{nickname}");

        playerNumberOfGameValue.setText("{sovanchoi}");

        playerNumberOfWinValue.setText("{sovanthang}");

        competitorNicknameValue.setText("{nickname}");

        competotorNumberOfGameValue.setText("{sovanchoi}");

        competitorNumberOfWinValue.setText("{sovanthang}");

        countDownLabel.setForeground(new java.awt.Color(255, 0, 0));
        countDownLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countDownLabel.setText("Thời gian:00:20");

        gamePanel.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 568, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
                gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 666, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        playerLabel.setForeground(new java.awt.Color(255, 255, 255));
        playerLabel.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        competitorLabel.setForeground(new java.awt.Color(255, 255, 255));
        competitorLabel.setText("Đối thủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(competitorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(competitorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        roomNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNameLabel.setText("{Tên Phòng}");

        microphoneStatusButton.setToolTipText("Bật mic để nói chuyện cùng nhau");
        microphoneStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                microphoneStatusButtonActionPerformed(evt);
            }
        });

        speakerStatusButton.setToolTipText("Âm thanh trò chuyện đang tắt");
        speakerStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speakerStatusButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(roomNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(microphoneStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(speakerStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(roomNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(microphoneStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(speakerStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("Tỉ số:  0-0");

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        drawRequestButton.setBackground(new java.awt.Color(102, 102, 102));
        drawRequestButton.setForeground(new java.awt.Color(255, 255, 255));
        drawRequestButton.setText("Cầu hòa");
        drawRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawRequestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(131, 131, 131)
                                .addComponent(drawRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(drawRequestButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        competitorTurnLabel.setForeground(new java.awt.Color(0, 0, 204));
        competitorTurnLabel.setText("Đến lượt đối thủ");

        playerCurrentPositionLabel.setText("x/o");

        competitorPositionLabel.setText("x/o");

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        playerButtonImage.setBackground(new java.awt.Color(102, 102, 102));

        competotorButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competotorButtonImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(vsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                                .addComponent(playerButtonImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(playerButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 12, Short.MAX_VALUE))
        );

        mainMenu.setText("Menu");
        mainMenu.setToolTipText("");

        newGameMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newGameMenuItem.setText("Game mới");
        newGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(newGameMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        exitMenuItem.setText("Thoát");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(exitMenuItem);

        jMenuBar1.add(mainMenu);


        JMenu suggestionMenu = new javax.swing.JMenu("Gợi ý");
        JMenuItem suggestionMenuItem = new javax.swing.JMenuItem("Gợi ý");
        suggestionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        suggestionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintMenuActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestionMenuItem);
        jMenuBar1.add(mainMenu);
        jMenuBar1.add(suggestionMenu);
        jMenuBar1.add(helpMenu);


        helpMenu.setText("Help");

        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        helpMenuItem.setText("Trợ giúp");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addContainerGap()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(playerNumberOfWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(26, 26, 26)
                                                                                                .addComponent(playerNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(competitorNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(39, 39, 39)
                                                                                                .addComponent(competitorNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(competotorNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(competitorNumberOfWinLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(27, 27, 27)
                                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(competotorNumberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                        .addComponent(competitorNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(29, 29, 29)
                                                                                .addComponent(playerCurrentPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(39, 39, 39)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(41, 41, 41)
                                                                                                .addComponent(competitorPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addContainerGap()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(playerNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(playerNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(26, 26, 26)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(playerNumberOfGameValue)))
                                                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(playerTurnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(28, 28, 28)
                                                                                .addComponent(countDownLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addGap(28, 28, 28)
                                                                                .addComponent(competitorTurnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(29, 29, 29)))
                                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(playerNicknameLabel)
                                                        .addComponent(playerNicknameValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(playerNumberOfGameLabel)
                                                        .addComponent(playerNumberOfGameValue))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(playerNumberOfWinLabel)
                                                        .addComponent(playerNumberOfWinValue))
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(competitorNicknameLabel)
                                                        .addComponent(competitorNicknameValue))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(competotorNumberOfGameLabel)
                                                        .addComponent(competotorNumberOfGameValue))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(competitorNumberOfWinLabel)
                                                        .addComponent(competitorNumberOfWinValue))))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(competitorPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scoreLabel)
                                                        .addComponent(playerCurrentPositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(countDownLabel)
                                        .addComponent(competitorTurnLabel)
                                        .addComponent(playerTurnLabel))
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(messageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //for(int i=0; i<5; i++){
        //    for(int j=0;j<5;j++){
        //        gamePanel.add(button[i][j]);
        //    }
        //}

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Thông báo", "Tính năng đang được phát triển", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_newGameMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        exitGame();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            if (messageTextField.getText().isEmpty()) {
                throw new Exception("Vui lòng nhập nội dung tin nhắn");
            }
            String temp = messageTextArea.getText();
            temp += "Tôi: " + messageTextField.getText() + "\n";
            messageTextArea.setText(temp);
            Client.socketHandle.write("chat," + messageTextField.getText());
            messageTextField.setText("");
            messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void drawRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawRequestButtonActionPerformed

        try {
            int res = JOptionPane.showConfirmDialog(rootPane, "Bạn có thực sự muốn cầu hòa ván chơi này", "Yêu cầu cầu hòa", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                Client.socketHandle.write("draw-request,");
                timer.stop();
                setEnableButton(false);
                Client.openView(Client.View.GAME_NOTICE, "Yêu cầu hòa", "Đang chờ phản hồi từ đối thủ");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_drawRequestButtonActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "Luật chơi: luật quốc tế 5 nước chặn 2 đầu\n"
                + "Hai người chơi luân phiên nhau chơi trước\n"
                + "Người chơi trước đánh X, người chơi sau đánh O\n"
                + "Bạn có 20 giây cho mỗi lượt đánh, quá 20 giây bạn sẽ thua\n"
                + "Khi cầu hòa, nếu đối thủ đồng ý thì ván hiện tại được hủy kết quả\n"
                + "Với mỗi ván chơi bạn có thêm 1 điểm, nếu hòa bạn được thêm 5 điểm,\n"
                + "nếu thắng bạn được thêm 10 điểm\n"
                + "Chúc bạn chơi game vui vẻ");
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void competotorButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competotorButtonImageActionPerformed

        Client.openView(Client.View.COMPETITOR_INFO, competitor);

    }//GEN-LAST:event_competotorButtonImageActionPerformed

    private void microphoneStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_microphoneStatusButtonActionPerformed
        if (isSending) {
            try {
                Client.socketHandle.write("voice-message,close-mic");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
            }
            microphoneStatusButton.setIcon(new ImageIcon("assets/game/mute.png"));
            voiceCloseMic();
            microphoneStatusButton.setToolTipText("Mic đang tắt");

        } else {
            try {
                Client.socketHandle.write("voice-message,open-mic");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
            }
            microphoneStatusButton.setIcon(new ImageIcon("assets/game/88634.png"));
            voiceOpenMic();
            microphoneStatusButton.setToolTipText("Mic đang bật");
        }
    }//GEN-LAST:event_microphoneStatusButtonActionPerformed

    private void speakerStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakerStatusButtonActionPerformed
        if (isListening) {
            try {
                Client.socketHandle.write("voice-message,close-speaker");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
            }
            speakerStatusButton.setIcon(new ImageIcon("assets/game/mutespeaker.png"));
            voiceStopListening();
            speakerStatusButton.setToolTipText("Âm thanh trò chuyện đang tắt");
        } else {
            try {
                Client.socketHandle.write("voice-message,open-speaker");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
            }
            voiceListening();
            speakerStatusButton.setIcon(new ImageIcon("assets/game/speaker.png"));
            speakerStatusButton.setToolTipText("Âm thanh trò chuyện đang bật");
        }
    }//GEN-LAST:event_speakerStatusButtonActionPerformed

    private void messageTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            try {
                if (messageTextField.getText().isEmpty()) {
                    return;
                }
                String temp = messageTextArea.getText();
                temp += "Tôi: " + messageTextField.getText() + "\n";
                messageTextArea.setText(temp);
                Client.socketHandle.write("chat," + messageTextField.getText());
                messageTextField.setText("");
                messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_messageTextFieldKeyPressed

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playSound1() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/1click.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void playSound2() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sound/win.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public void stopTimer() {
        timer.stop();
    }

    int not(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 0) {
            return 1;
        }
        return 0;
    }

    void setupButton() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int a = i, b = j;

                button[a][b].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            button[a][b].setDisabledIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                            button[a][b].setEnabled(false);
                            playSound();
                            second = 60;
                            minute = 0;
                            matrix[a][b] = 1;
                            userMatrix[a][b] = 1;
                            button[a][b].setEnabled(false);
                            try {
                                if (checkRowWin() == 1 || checkColumnWin() == 1 || checkRightCrossWin() == 1 || checkLeftCrossWin() == 1) {
                                    //Xử lý khi người chơi này thắng
                                    setEnableButton(false);
                                    increaseWinMatchToUser();
                                    Client.openView(Client.View.GAME_NOTICE, "Bạn đã thắng", "Đang thiết lập ván chơi mới");
                                    Client.socketHandle.write("win," + a + "," + b);
                                } else {
                                    Client.socketHandle.write("caro," + a + "," + b);
                                    displayCompetitorTurn();

                                }
                                setEnableButton(false);
                                timer.stop();
                            } catch (Exception ie) {
                                ie.printStackTrace();
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                        }
                    }
                });
                button[a][b].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (button[a][b].isEnabled()) {
                            button[a][b].setBackground(Color.GREEN);
                            button[a][b].setIcon(new ImageIcon(normalItem[not(numberOfMatch % 2)]));
                        }
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (button[a][b].isEnabled()) {
                            button[a][b].setBackground(null);
                            button[a][b].setIcon(new ImageIcon("assets/image/blank.jpg"));
                        }
                    }
                });
            }
        }
    }

    public void displayDrawRefuse() {
        JOptionPane.showMessageDialog(rootPane, "Đối thủ không chấp nhận hòa, mời bạn chơi tiếp");
        timer.start();
        setEnableButton(true);
    }

    public void displayCompetitorTurn() {
        countDownLabel.setVisible(false);
        competitorTurnLabel.setVisible(true);
        competitorPositionLabel.setVisible(true);
        playerTurnLabel.setVisible(false);
        drawRequestButton.setVisible(false);
        playerCurrentPositionLabel.setVisible(false);
    }

    public void displayUserTurn() {
        countDownLabel.setVisible(false);
        competitorTurnLabel.setVisible(false);
        competitorPositionLabel.setVisible(false);
        playerTurnLabel.setVisible(true);
        drawRequestButton.setVisible(true);
        playerCurrentPositionLabel.setVisible(true);
    }

    public void startTimer() {
        countDownLabel.setVisible(true);
        second = 60;
        minute = 0;
        timer.start();
    }

    public void addMessage(String message) {
        String temp = messageTextArea.getText();
        temp += competitor.getNickname() + ": " + message + "\n";
        messageTextArea.setText(temp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void addCompetitorMove(String x, String y) {
        displayUserTurn();
        startTimer();
        setEnableButton(true);
        caro(x, y);
    }

    public void setLose(String xx, String yy) {
        caro(xx, yy);
    }

    public void increaseWinMatchToUser() {
        Client.user.setNumberOfWin(Client.user.getNumberOfWin() + 1);
        playerNumberOfWinValue.setText("" + Client.user.getNumberOfWin());
        userWin++;
        scoreLabel.setText("Tỉ số: " + userWin + "-" + competitorWin);
        String tmp = messageTextArea.getText();
        tmp += "--Bạn đã thắng, tỉ số hiện tại là " + userWin + "-" + competitorWin + "--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void increaseWinMatchToCompetitor() {
        competitor.setNumberOfWin(competitor.getNumberOfWin() + 1);
        competitorNumberOfWinValue.setText("" + competitor.getNumberOfWin());
        competitorWin++;
        scoreLabel.setText("Tỉ số: " + userWin + "-" + competitorWin);
        String tmp = messageTextArea.getText();
        tmp += "--Bạn đã thua, tỉ số hiện tại là " + userWin + "-" + competitorWin + "--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void displayDrawGame() {
        String tmp = messageTextArea.getText();
        tmp += "--Ván chơi hòa--\n";
        messageTextArea.setText(tmp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void showDrawRequest() {
        int res = JOptionPane.showConfirmDialog(rootPane, "Đối thử muốn cầu hóa ván này, bạn đồng ý chứ", "Yêu cầu cầu hòa", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            try {
                timer.stop();
                setEnableButton(false);
                Client.socketHandle.write("draw-confirm,");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        } else {
            try {
                Client.socketHandle.write("draw-refuse,");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }

    public void voiceOpenMic() {

        Thread sendThread = new Thread() {

            @Override
            public void run() {
                AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
                TargetDataLine microphone;
                try {
                    microphone = AudioSystem.getTargetDataLine(format);

                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                    microphone = (TargetDataLine) AudioSystem.getLine(info);
                    microphone.open(format);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int numBytesRead;
                    int CHUNK_SIZE = 1024;
                    byte[] data = new byte[microphone.getBufferSize() / 5];
                    microphone.start();

                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);

                    int port = 5555;

                    InetAddress address = InetAddress.getByName(competitorIP);
                    DatagramSocket socket = new DatagramSocket();
                    byte[] buffer = new byte[1024];
                    isSending = true;
                    while (isSending) {
                        numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                        out.write(data, 0, numBytesRead);
                        DatagramPacket request = new DatagramPacket(data, numBytesRead, address, port);
                        socket.send(request);

                    }
                    out.close();
                    socket.close();
                    microphone.close();
                } catch (LineUnavailableException | IOException e) {
                    e.printStackTrace();
                }
            }

        };
        sendThread.start();

    }

    public void voiceCloseMic() {
        isSending = false;
    }


    public void voiceListening() {
        //                    microphone = AudioSystem.getTargetDataLine(format);
        //                    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        //                    microphone = (TargetDataLine) AudioSystem.getLine(info);
        //                    microphone.open(format);
        //                    microphone.start();
        Thread listenThread = new Thread() {
            @Override
            public void run() {
                try {
                    AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
                    SourceDataLine speakers;
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
                    speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    speakers.open(format);
                    speakers.start();
                    try {
                        DatagramSocket serverSocket = new DatagramSocket(5555);
                        isListening = true;
                        while (isListening) {
                            byte[] buffer = new byte[1024];
                            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                            serverSocket.receive(response);
                            speakers.write(response.getData(), 0, response.getData().length);
                            jProgressBar1.setValue((int) volumeRMS(response.getData()));
                        }
                        speakers.close();
                        serverSocket.close();
                    } catch (SocketTimeoutException ex) {
                        System.out.println("Timeout error: " + ex.getMessage());
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        System.out.println("Client error: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }

        };
        listenThread.start();
    }

    private int getMax(byte[] bytes) {
        int max = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            if (bytes[i] > max) max = bytes[i];
        }
        return max;
    }

    public double volumeRMS(byte[] raw) {
        double sum = 0d;
        if (raw.length == 0) {
            return sum;
        } else {
            for (byte b : raw) {
                sum += b;
            }
        }
        double average = sum / raw.length;

        double sumMeanSquare = 0d;
        for (byte b : raw) {
            sumMeanSquare += Math.pow(b - average, 2d);
        }
        double averageMeanSquare = sumMeanSquare / raw.length;
        return Math.sqrt(averageMeanSquare);
    }

    public void voiceStopListening() {
        isListening = false;
    }

    public void addVoiceMessage(String message) {
        String temp = messageTextArea.getText();
        temp += competitor.getNickname() + " " + message + "\n";
        messageTextArea.setText(temp);
        messageTextArea.setCaretPosition(messageTextArea.getDocument().getLength());
    }

    public void newgame() {

        if (numberOfMatch % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Đến lượt bạn đi trước");
            startTimer();
            displayUserTurn();
            countDownLabel.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Đối thủ đi trước");
            displayCompetitorTurn();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setIcon(new ImageIcon("assets/image/blank.jpg"));
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
                userMatrix[i][j] = 0;
            }
        }
        setEnableButton(true);
        if (numberOfMatch % 2 != 0) {
            blockGame();
        }

        playerCurrentPositionLabel.setIcon(new ImageIcon(iconItem[numberOfMatch % 2]));
        competitorPositionLabel.setIcon(new ImageIcon(iconItem[not(numberOfMatch % 2)]));
        preButton = null;
        numberOfMatch++;
    }

    public void updateNumberOfGame() {
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.user.setNumberOfGame(Client.user.getNumberOfGame() + 1);
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
    }

    public void blockGame() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setBackground(Color.white);
                button[i][j].setDisabledIcon(new ImageIcon("assets/image/border.jpg"));
                button[i][j].setText("");
                competitorMatrix[i][j] = 0;
                matrix[i][j] = 0;
                drawRequestButton.setVisible(false);
            }
        }
        timer.stop();
        setEnableButton(false);
    }

    public void setEnableButton(boolean b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    button[i][j].setEnabled(b);
                }
            }
        }
    }

    public int checkRow() {
        int win = 0, hang = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (competitorMatrix[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                button[i][j].setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkColumn() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (check) {
                    if (competitorMatrix[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkRightCross() {
        int win = 0, cheop = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (n - j >= 0 && competitorMatrix[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkLeftCross() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (check) {
                    if (n - j - 2 * cheot >= 0 && competitorMatrix[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[numberOfMatch % 2]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (competitorMatrix[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public int checkRowWin() {
        int win = 0, hang = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        hang++;
                        list.add(button[i][j]);
                        if (hang > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        hang = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    hang++;
                } else {
                    list = new ArrayList<>();
                    check = false;
                }
            }
            list = new ArrayList<>();
            hang = 0;
        }
        return win;
    }

    public int checkColumnWin() {
        int win = 0, cot = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (check) {
                    if (userMatrix[i][j] == 1) {
                        cot++;
                        list.add(button[i][j]);
                        if (cot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        check = false;
                        cot = 0;
                        list = new ArrayList<>();
                    }
                }
                if (userMatrix[i][j] == 1) {
                    check = true;
                    list.add(button[i][j]);
                    cot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            cot = 0;
        }
        return win;
    }

    public int checkRightCrossWin() {
        int win = 0, cheop = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (check) {
                    if (n >= j && userMatrix[n - j][j] == 1) {
                        cheop++;
                        list.add(button[n - j][j]);
                        if (cheop > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheop = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    n = i + j;
                    check = true;
                    list.add(button[i][j]);
                    cheop++;
                } else {
                    check = false;
                    list = new ArrayList<>();
                }
            }
            cheop = 0;
            check = false;
            list = new ArrayList<>();
        }
        return win;
    }

    public int checkLeftCrossWin() {
        int win = 0, cheot = 0, n = 0;
        boolean check = false;
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (check) {
                    if (n - j - 2 * cheot >= 0 && userMatrix[n - j - 2 * cheot][j] == 1) {
                        list.add(button[n - j - 2 * cheot][j]);
                        cheot++;
                        System.out.print("+" + j);
                        if (cheot > 4) {
                            for (JButton jButton : list) {
                                jButton.setDisabledIcon(new ImageIcon(winItem[not(numberOfMatch % 2)]));
                            }
                            win = 1;
                            break;
                        }
                        continue;
                    } else {
                        list = new ArrayList<>();
                        check = false;
                        cheot = 0;
                    }
                }
                if (userMatrix[i][j] == 1) {
                    list.add(button[i][j]);
                    n = i + j;
                    check = true;
                    cheot++;
                } else {
                    check = false;
                }
            }
            list = new ArrayList<>();
            n = 0;
            cheot = 0;
            check = false;
        }
        return win;
    }

    public void caro(String x, String y) {
        int xx, yy;
        xx = Integer.parseInt(x);
        yy = Integer.parseInt(y);
        // danh dau vi tri danh
        competitorMatrix[xx][yy] = 1;
        matrix[xx][yy] = 1;
        button[xx][yy].setEnabled(false);
        playSound1();
        if (preButton != null) {
            preButton.setDisabledIcon(new ImageIcon(normalItem[numberOfMatch % 2]));
        }
        preButton = button[xx][yy];
        button[xx][yy].setDisabledIcon(new ImageIcon(preItem[numberOfMatch % 2]));
        if (checkRow() == 1 || checkColumn() == 1 || checkLeftCross() == 1 || checkRightCross() == 1) {
            timer.stop();
            setEnableButton(false);
            increaseWinMatchToCompetitor();
            Client.openView(Client.View.GAME_NOTICE, "Bạn đã thua", "Đang thiết lập ván chơi mới");
        }
    }

//---------------------------------------AI suggests moves for players ---------------------------------------

    private int evaluateBoard(int[][] board, int player) {
        int score = 0;

        // Duyệt hàng, cột và đường chéo
        score += evaluateLines(board, player, true);  // Hàng
        score += evaluateLines(board, player, false); // Cột
        score += evaluateDiagonals(board, player);    // Đường chéo

        // Tăng trọng số tổng cho bàn cờ với nhiều chuỗi mở
        if (score > 10000) {
            score *= 1.2; // Tăng 20% điểm nếu bàn cờ đang có lợi thế lớn
        }

        return score;
    }


    private int evaluateLines(int[][] board, int player, boolean isRow) {
        int score = 0;

        for (int i = 0; i < size; i++) {
            int count = 0, block = 0;
            for (int j = 0; j < size; j++) {
                int cell = isRow ? board[i][j] : board[j][i];

                if (cell == player) {
                    count++;
                } else if (cell != 0) {
                    block++;
                    if (count > 0) {
                        score += calculateScore(count, block, player);
                        count = 0;
                        block = 1;
                    }
                } else {
                    if (count > 0) {
                        score += calculateScore(count, block, player);
                    }
                    count = 0;
                    block = 0;
                }
            }

            if (count > 0) {
                score += calculateScore(count, block, player);
            }
        }
        return score;
    }


    private int evaluateDiagonals(int[][] board, int player) {
        int score = 0;

        // Duyệt đường chéo chính
        for (int d = -(size - 1); d < size; d++) {
            int count = 0, block = 0;
            for (int i = Math.max(0, d), j = Math.max(0, -d); i < size && j < size; i++, j++) {
                if (board[i][j] == player) {
                    count++;
                } else if (board[i][j] != 0) {
                    block++;
                    if (count > 0) {
                        score += calculateScore(count, block, player) * 1.5; // Tăng điểm 50% cho đường chéo
                        count = 0;
                        block = 1;
                    }
                } else {
                    if (count > 0) {
                        score += calculateScore(count, block, player) * 1.5;
                    }
                    count = 0;
                    block = 0;
                }
            }
            if (count > 0) {
                score += calculateScore(count, block, player) * 1.5;
            }
        }

        // Duyệt đường chéo phụ (tương tự như trên)
        for (int d = 0; d < 2 * size - 1; d++) {
            int count = 0, block = 0;
            for (int i = Math.max(0, d - size + 1), j = Math.min(size - 1, d); i < size && j >= 0; i++, j--) {
                if (board[i][j] == player) {
                    count++;
                } else if (board[i][j] != 0) {
                    block++;
                    if (count > 0) {
                        score += calculateScore(count, block, player) * 1.5;
                        count = 0;
                        block = 1;
                    }
                } else {
                    if (count > 0) {
                        score += calculateScore(count, block, player) * 1.5;
                    }
                    count = 0;
                    block = 0;
                }
            }
            if (count > 0) {
                score += calculateScore(count, block, player) * 1.5;
            }
        }

        return score;
    }


    private int calculateScore(int count, int block, int player) {
        if (count >= 5) {
            return 100000; // Winning move
        } else if (block == 0) {
            if (count == 4) return 15000; // Open four
            if (count == 3) return 5000;  // Open three
            if (count == 2) return 500;   // Open two
        } else if (block == 1) {
            if (count == 4) return 8000;  // Blocked four
            if (count == 3) return 1000;  // Blocked three
            if (count == 2) return 100;   // Blocked two
        }

        // Encourage formation of open sequences with potential to grow
        if (count >= 2 && block == 0) {
            return 300 * count;
        }

        return 0;
    }



    private int[] alphaBetaSearch(int depth, int alpha, int beta, int player, boolean isMaximizing) {
        if (depth == 0 || isGameOver()) {
            return new int[]{evaluateBoard(matrix, player), -1, -1};
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) { // Only consider empty cells
                    matrix[i][j] = isMaximizing ? player : 3 - player;

                    // Check for immediate win or block
                    if (checkWin(matrix, i, j, player)) {
                        matrix[i][j] = 0; // Undo move
                        return new int[]{isMaximizing ? 100000 : -100000, i, j};
                    }

                    int[] currentMove = alphaBetaSearch(depth - 1, alpha, beta, player, !isMaximizing);
                    matrix[i][j] = 0; // Undo move

                    if (isMaximizing) {
                        if (currentMove[0] > bestScore) {
                            bestScore = currentMove[0];
                            bestMove = new int[]{i, j};
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (currentMove[0] < bestScore) {
                            bestScore = currentMove[0];
                            bestMove = new int[]{i, j};
                        }
                        beta = Math.min(beta, bestScore);
                    }

                    if (beta <= alpha) {
                        break; // Prune
                    }
                }
            }
        }

        return new int[]{bestScore, bestMove[0], bestMove[1]};
    }

    // Helper function to check for an immediate winning move
    private boolean checkWin(int[][] board, int row, int col, int player) {
        // Check horizontal, vertical, and diagonal directions for five in a row
        return checkDirection(board, row, col, player, 1, 0) // Horizontal
                || checkDirection(board, row, col, player, 0, 1) // Vertical
                || checkDirection(board, row, col, player, 1, 1) // Diagonal /
                || checkDirection(board, row, col, player, 1, -1); // Diagonal \
    }

    // Helper function to check a specific direction
    private boolean checkDirection(int[][] board, int row, int col, int player, int deltaRow, int deltaCol) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && board[newRow][newCol] == player) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }



    private boolean isGameOver() {
        return checkRowWin() == 1 || checkColumnWin() == 1 || checkRightCrossWin() == 1 || checkLeftCrossWin() == 1;
    }


    private int[] checkForImmediateWin(int player) {
        // Kiểm tra các ô trên bàn cờ
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) { // Chỉ xem xét các ô trống
                    matrix[i][j] = 1; // Đặt quân của người chơi

                    // Kiểm tra xem có phải nước đi thắng ngay lập tức không
                    if (checkWin(matrix, i, j, 1)) {
                        matrix[i][j] = 0; // Hoàn tác nước đi
                        return new int[]{i, j}; // Trả về nước đi thắng
                    }

                    matrix[i][j] = 0; // Hoàn tác nước đi
                }
            }
        }

        return null; // Không tìm thấy nước đi thắng ngay lập tức
    }

    private int[] blockImmediateWin(int player) {
        // Kiểm tra các ô trên bàn cờ
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) { // Chỉ xem xét các ô trống
                    matrix[i][j] = 2; // Đặt quân của đối thủ

                    // Kiểm tra xem có phải nước đi chặn thắng ngay lập tức không
                    if (checkWin(matrix, i, j, 2)) {
                        matrix[i][j] = 0; // Hoàn tác nước đi
                        return new int[]{i, j}; // Trả về nước đi chặn
                    }

                    matrix[i][j] = 0; // Hoàn tác nước đi
                }
            }
        }

        return null; // Không tìm thấy nước đi chặn thắng ngay lập tức
    }

    private int[] getSuggestedMove(int player) {
        if (player == 1) { // X
            return findBestMoveForX();
        } else { // O
            return findBestMoveForO();
        }
    }

    private int[] findBestMoveForX() {
        int[] winningMove = checkForImmediateWin(1); // 1 là X
        if (winningMove != null) {
            return winningMove;
        }

        int[] blockingMove = blockImmediateWin(2); // 2 là O
        if (blockingMove != null) {
            return blockingMove;
        }

        // Ưu tiên tạo chuỗi 4 nước liên tiếp cho người chơi "O"
        int[] createFourMove = findCreateFourMove(1); // 1 là người chơi "O"
        if (createFourMove != null) {
            return createFourMove;
        }

        // Ưu tiên chặn 4 nước liên tiếp của đối thủ
        int[] blockFourMove = findBlockFourMove(2); // 2 là người chơi đối thủ
        if (blockFourMove != null) {
            return blockFourMove;
        }

        // Nếu không có 4 nước liên tiếp, kiểm tra 3 nước liên tiếp chưa bị chặn
        int[] blockThreeMove = findBlockThreeMove(2); // 2 là người chơi đối thủ
        if (blockThreeMove != null) {
            return blockThreeMove;
        }

        // Nếu không có nước chặn, sử dụng thuật toán alpha-beta
        int[] result = alphaBetaSearch(4, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, true);
        if (result[1] != -1 && result[2] != -1) {
            return new int[]{result[1], result[2]};
        }

        // Nếu không có nước đi nào được tìm thấy, trả về một nước đi mặc định
        return findDefaultMove();
    }

    private int[] findBestMoveForO() {
        int[] winningMove = checkForImmediateWin(2); // 2 là O
        if (winningMove != null) {
            return winningMove;
        }

        int[] blockingMove = blockImmediateWin(1); // 1 là X
        if (blockingMove != null) {
            return blockingMove;
        }

        // Ưu tiên tạo chuỗi 4 nước liên tiếp cho người chơi "O"
        int[] createFourMove = findCreateFourMove(1); // 1 là người chơi "O"
        if (createFourMove != null) {
            return createFourMove;
        }

        // Ưu tiên chặn 4 nước liên tiếp của đối thủ
        int[] blockFourMove = findBlockFourMove(2); // 2 là người chơi đối thủ
        if (blockFourMove != null) {
            return blockFourMove;
        }

        // Nếu không có 4 nước liên tiếp, kiểm tra 3 nước liên tiếp chưa bị chặn
        int[] blockThreeMove = findBlockThreeMove(2); // 2 là người chơi đối thủ
        if (blockThreeMove != null) {
            return blockThreeMove;
        }

        // Nếu không có nước chặn, sử dụng thuật toán alpha-beta
        int[] result = alphaBetaSearch(4, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, true);
        if (result[1] != -1 && result[2] != -1) {
            return new int[]{result[1], result[2]};
        }


        return findDefaultMove();
    }
    private int[] findDefaultMove() {
        // Tìm nước đi đầu tiên trống trên bảng
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) { // Giả sử ô trống được đánh dấu bằng 0
                    return new int[]{i, j};
                }
            }
        }

        // Nếu không tìm thấy nước đi nào, trả về null
        return null;
    }

    private int[] findCreateFourMove(int myPlayer) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = myPlayer; // Giả sử đánh quân của người chơi hiện tại
                    if (isThreeInARow(matrix, i, j, myPlayer) && !isThreeInARowWithTwoBlocks(matrix, i, j, myPlayer)) {
                        if (isFourInARow(matrix, i, j, myPlayer) && !isFourInARowWithTwoBlocks(matrix, i, j, myPlayer)) {
                            matrix[i][j] = 0;
                            return new int[]{i, j};
                        }
                    }
                    matrix[i][j] = 0;
                }
            }
        }
        return null;
    }

    private int[] findBlockFourMove(int opponentPlayer) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = 1; // Giả sử đánh quân của người chơi hiện tại
                    if (isFourInARow(matrix, i, j, opponentPlayer) && !isFourInARowWithTwoBlocks(matrix, i, j, opponentPlayer)) {
                        matrix[i][j] = 0;
                        return new int[]{i, j};
                    }
                    matrix[i][j] = 0;
                }
            }
        }
        return null;
    }

    private int[] findBlockThreeMove(int opponentPlayer) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][j] = 1; // Giả sử đánh quân của người chơi hiện tại
                    if (isThreeInARow(matrix, i, j, opponentPlayer) && !isThreeInARowWithTwoBlocks(matrix, i, j, opponentPlayer)) {
                        matrix[i][j] = 0;
                        return new int[]{i, j};
                    }
                    matrix[i][j] = 0;
                }
            }
        }
        return null;
    }

    private boolean isFourInARow(int[][] board, int row, int col, int player) {
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] dir : directions) {
            if (checkDirectionWithBlock(board, row, col, player, dir[0], dir[1], 4)) {
                return true;
            }
        }
        return false;
    }

    private boolean isThreeInARow(int[][] board, int row, int col, int player) {
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] dir : directions) {
            if (checkDirectionWithBlock(board, row, col, player, dir[0], dir[1], 3)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFourInARowWithTwoBlocks(int[][] board, int row, int col, int player) {
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] dir : directions) {
            if (checkDirectionWithBlock(board, row, col, player, dir[0], dir[1], 4) &&
                    checkDirectionWithBlock(board, row, col, player, -dir[0], -dir[1], 4)) {
                return true;
            }
        }
        return false;
    }

    private boolean isThreeInARowWithTwoBlocks(int[][] board, int row, int col, int player) {
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] dir : directions) {
            if (checkDirectionWithBlock(board, row, col, player, dir[0], dir[1], 3) &&
                    checkDirectionWithBlock(board, row, col, player, -dir[0], -dir[1], 3)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDirectionWithBlock(int[][] board, int row, int col, int player, int deltaRow, int deltaCol, int length) {
        int count = 0;
        int blockedEnds = 0;

        // Kiểm tra chiều thuận
        for (int i = 1; i <= length; i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;

            if (newRow < 0 || newRow >= size || newCol < 0 || newCol >= size) {
                blockedEnds++;
                break;
            }

            if (board[newRow][newCol] == player) {
                count++;
            } else if (board[newRow][newCol] != 0) {
                blockedEnds++;
                break;
            }
        }

        // Kiểm tra chiều ngược
        for (int i = 1; i <= length; i++) {
            int newRow = row - i * deltaRow;
            int newCol = col - i * deltaCol;

            if (newRow < 0 || newRow >= size || newCol < 0 || newCol >= size) {
                blockedEnds++;
                break;
            }

            if (board[newRow][newCol] == player) {
                count++;
            } else if (board[newRow][newCol] != 0) {
                blockedEnds++;
                break;
            }
        }

        // Kiểm tra số quân và số đầu bị chặn
        return count == length && blockedEnds < 2;
    }

    private void hintMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Reset các ô được gợi ý trước đó
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (button[i][j].getIcon() != null && button[i][j].getIcon().toString().contains("ai.png")) {
                    button[i][j].setIcon(null);
                }
            }
        }

        // Tìm nước đi gợi ý
        int[] suggestedMove = getSuggestedMove(currentPlayer);

        if (suggestedMove != null) {
            int row = suggestedMove[0];
            int col = suggestedMove[1];

            // Đặt biểu tượng gợi ý
            button[row][col].setIcon(new ImageIcon("assets/image/ai.png"));

            // Hiển thị thông báo gợi ý
            JOptionPane.showMessageDialog(this,
                    "Nước đi gợi ý: Hàng " + (row + 1) + ", Cột " + (col + 1),
                    "Gợi ý",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nước đi khả thi!", "Gợi ý", JOptionPane.WARNING_MESSAGE);
        }
    }



    //-------------------------------------------------------


    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorNicknameLabel;
    private javax.swing.JLabel competitorNicknameValue;
    private javax.swing.JLabel competitorNumberOfWinLabel;
    private javax.swing.JLabel competitorNumberOfWinValue;
    private javax.swing.JLabel competitorPositionLabel;
    private javax.swing.JLabel competitorTurnLabel;
    private javax.swing.JButton competotorButtonImage;
    private javax.swing.JLabel competotorNumberOfGameLabel;
    private javax.swing.JLabel competotorNumberOfGameValue;
    private javax.swing.JLabel countDownLabel;
    private javax.swing.JButton drawRequestButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JTextArea messageTextArea;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JButton microphoneStatusButton;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JLabel playerButtonImage;
    private javax.swing.JLabel playerCurrentPositionLabel;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerNicknameLabel;
    private javax.swing.JLabel playerNicknameValue;
    private javax.swing.JLabel playerNumberOfGameLabel;
    private javax.swing.JLabel playerNumberOfGameValue;
    private javax.swing.JLabel playerNumberOfWinLabel;
    private javax.swing.JLabel playerNumberOfWinValue;
    private javax.swing.JLabel playerTurnLabel;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton speakerStatusButton;
    private javax.swing.JLabel vsIcon;
}