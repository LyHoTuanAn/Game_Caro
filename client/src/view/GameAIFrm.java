package view;

import controller.Client;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Point;
import model.XOButton;

/**
 * @author Admin
 */
public class GameAIFrm extends javax.swing.JFrame {

    private static final int ROW_COUNT = 20;
    private static final int COL_COUNT = 20;
    public XOButton[][] Buttons = new XOButton[COL_COUNT][ROW_COUNT];
    private static final int winScore = 100000000;
    private int gameNumber = 0;
    private int userWin = 0;
    private int aIWin = 0;
    XOButton preButton;

    public GameAIFrm() {
        initComponents();
        this.setTitle("Caro Game Nhóm 8");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/image/caroicon.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);
        playerNicknameValue.setText(Client.user.getNickname());
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        playerNumberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        playerButtonImage.setIcon(new ImageIcon("assets/game/" + Client.user.getAvatar() + ".jpg"));
        vsIcon.setIcon(new ImageIcon("assets/game/swords-1.png"));
        playerCurrentPosition.setIcon(new ImageIcon("assets/image/x3.jpg"));
        competitorCurrentPosition.setIcon(new ImageIcon("assets/image/o3.jpg"));
        competitorButtonImage.setIcon(new ImageIcon("assets/image/ai.png"));

        //Set layout dạng lưới cho panel chứa button
        gameBoardPanel.setLayout(new GridLayout(20, 20));

        //Setup play button
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons.length; j++) {
                Point point = new Point(i, j);
                Buttons[i][j] = new XOButton(i, j);
                Buttons[i][j].addMouseListener(new MouseListener() {

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        handleClickButton(point);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }
                });
                gameBoardPanel.add(Buttons[i][j]);
            }
        }
        gameNumber++;
        preButton = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        playerNumberOfWinLabel = new javax.swing.JLabel();
        yourTurnJLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        playerNickNameLabel = new javax.swing.JLabel();
        playerNumberOfGameLabel = new javax.swing.JLabel();
        competitorNumberOfWinLabel = new javax.swing.JLabel();
        competitorNicknameLabel = new javax.swing.JLabel();
        competitorNumberOfGameLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomMessageTextArea = new javax.swing.JTextArea();
        playerNicknameValue = new javax.swing.JLabel();
        playerNumberOfGameValue = new javax.swing.JLabel();
        playerNumberOfWinValue = new javax.swing.JLabel();
        competitorNicknameValue = new javax.swing.JLabel();
        competitorNumberOfGameValue = new javax.swing.JLabel();
        competitorNumberOfWinValue = new javax.swing.JLabel();
        gameBoardPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        botLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        compretitorTurnJLabel = new javax.swing.JLabel();
        playerCurrentPosition = new javax.swing.JLabel();
        competitorCurrentPosition = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        playerButtonImage = new javax.swing.JLabel();
        vsIcon = new javax.swing.JLabel();
        competitorButtonImage = new javax.swing.JButton();
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

        yourTurnJLabel.setForeground(new java.awt.Color(255, 0, 0));
        yourTurnJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yourTurnJLabel.setText("Bạn");

        playerNickNameLabel.setText("Nickname");

        playerNumberOfGameLabel.setText("Số ván chơi");

        competitorNumberOfWinLabel.setText("Số ván thắng");

        competitorNicknameLabel.setText("Nickname");

        competitorNumberOfGameLabel.setText("Số ván chơi");

        roomMessageTextArea.setColumns(20);
        roomMessageTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        roomMessageTextArea.setRows(5);
        jScrollPane1.setViewportView(roomMessageTextArea);

        playerNicknameValue.setText("{nickname}");

        playerNumberOfGameValue.setText("{sovanchoi}");

        playerNumberOfWinValue.setText("{sovanthang}");

        competitorNicknameValue.setText("Máy");

        competitorNumberOfGameValue.setText("9999");

        competitorNumberOfWinValue.setText("9999");

        gameBoardPanel.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout gameBoardPanelLayout = new javax.swing.GroupLayout(gameBoardPanel);
        gameBoardPanel.setLayout(gameBoardPanelLayout);
        gameBoardPanelLayout.setHorizontalGroup(
            gameBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );
        gameBoardPanelLayout.setVerticalGroup(
            gameBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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

        botLabel.setForeground(new java.awt.Color(255, 255, 255));
        botLabel.setText("Chơi với máy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(botLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("Tỉ số:  0-0");

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        compretitorTurnJLabel.setForeground(new java.awt.Color(0, 0, 204));
        compretitorTurnJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        compretitorTurnJLabel.setText("Máy");

        playerCurrentPosition.setText("x/o");

        competitorCurrentPosition.setText("x/o");

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        playerButtonImage.setBackground(new java.awt.Color(102, 102, 102));

        competitorButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competitorButtonImageActionPerformed(evt);
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
                    .addComponent(competitorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(competitorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                            .addComponent(competitorNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(competitorNumberOfWinLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(competitorNumberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(competitorNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(playerCurrentPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(competitorCurrentPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(playerNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(playerNickNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(playerNumberOfGameValue)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(yourTurnJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(159, 159, 159)
                                        .addComponent(compretitorTurnJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(playerNickNameLabel)
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
                            .addComponent(competitorNumberOfGameLabel)
                            .addComponent(competitorNumberOfGameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNumberOfWinLabel)
                            .addComponent(competitorNumberOfWinValue))))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorCurrentPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scoreLabel)
                            .addComponent(playerCurrentPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compretitorTurnJLabel)
                    .addComponent(yourTurnJLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(gameBoardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //for(int i=0; i<5; i++){
            //    for(int j=0;j<5;j++){
                //        gameBoardPanel.add(button[i][j]);
                //    }
            //}

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameMenuItemActionPerformed
    }//GEN-LAST:event_newGameMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Luật chơi: luật quốc tế 5 nước chặn 2 đầu\n"
                + "Hai người chơi luân phiên nhau chơi trước\n"
                + "Người chơi trước đánh X, người chơi sau đánh O\n"
                + "Bạn có 20 giây cho mỗi lượt đánh, quá 20 giây bạn sẽ thua\n"
                + "Khi cầu hòa, nếu đối thủ đồng ý thì ván hiện tại được hủy kết quả\n"
                + "Với mỗi ván chơi bạn có thêm 1 điểm, nếu hòa bạn được thêm 5 điểm,\n"
                + "nếu thắng bạn được thêm 10 điểm\n"
                + "Chúc bạn chơi game vui vẻ");
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void competitorButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competitorButtonImageActionPerformed

    }//GEN-LAST:event_competitorButtonImageActionPerformed

    private void updateScore() {
        scoreLabel.setText("Tỉ số: " + userWin + "-" + aIWin);
    }

    private void handleClickButton(Point point) {

        // TODO: CALC LOGIC HERE
        point.log();
        Buttons[point.x][point.y].setState(true);
        Buttons[point.x][point.y].setEnabled(false);
        if (getScore(getMatrixBoard(), true, false) >= winScore) {
            JOptionPane.showMessageDialog(null, "Bạn đã thắng");
            userWin++;
            updateScore();
            displayUserWin();
            newGame();
            return;
        }

        int nextMoveX = 0, nextMoveY = 0;
        int[] bestMove = calcNextMove(3);

        if (bestMove != null) {
            nextMoveX = bestMove[0];
            nextMoveY = bestMove[1];
        }
        Buttons[nextMoveX][nextMoveY].setState(false);
        Buttons[nextMoveX][nextMoveY].setEnabled(false);
        if (getScore(getMatrixBoard(), false, true) >= winScore) {
            JOptionPane.showMessageDialog(null, "Bạn đã thua");
            aIWin++;
            updateScore();
            displayAIWin();
            newGame();
        }
    }

    private void newGame() {
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons.length; j++) {
                Buttons[i][j].resetState();
            }
        }

        gameNumber++;
        if (gameNumber % 2 == 0) {
            JOptionPane.showMessageDialog(rootPane, "Máy đi trước", "Ván mới", JOptionPane.INFORMATION_MESSAGE);
            Buttons[9][9].setState(false);
            Buttons[9][9].setEnabled(false);

            if (getScore(getMatrixBoard(), false, true) >= winScore) {
                JOptionPane.showMessageDialog(null, "Bạn đã thua");
                newGame();
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn đi trước", "Ván mới", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayUserWin() {
        String tmp = roomMessageTextArea.getText();
        tmp += "--Bạn đã thắng, tỉ số hiện tại là " + userWin + "-" + aIWin + "--\n";
        roomMessageTextArea.setText(tmp);
        roomMessageTextArea.setCaretPosition(roomMessageTextArea.getDocument().getLength());
    }

    private void displayAIWin() {
        String tmp = roomMessageTextArea.getText();
        tmp += "--Máy thắng, tỉ số hiện tại là " + userWin + "-" + aIWin + "--\n";
        roomMessageTextArea.setText(tmp);
        roomMessageTextArea.setCaretPosition(roomMessageTextArea.getDocument().getLength());
    }


    public int[] calcNextMove(int depth) {
        int[][] board = getMatrixBoard();
        Object[] bestMove = searchWinningMove(board);
        Object[] badMove = searchLoseMove(board);

        int[] move = new int[2];

        if (badMove[1] != null && badMove[2] != null) {

            move[0] = (Integer) (badMove[1]);
            move[1] = (Integer) (badMove[2]);
            return move;
        }

        if (bestMove[1] != null && bestMove[2] != null) {

            move[0] = (Integer) (bestMove[1]);
            move[1] = (Integer) (bestMove[2]);

        } else {

            bestMove = minimaxSearchAB(depth, board, true, -1.0, winScore);
            if (bestMove[1] == null) {
                move = null;
            } else {
                move[0] = (Integer) (bestMove[1]);
                move[1] = (Integer) (bestMove[2]);
            }
        }
        return move;
    }

    public int[][] playNextMove(int[][] board, int[] move, boolean isUserTurn) {
        int i = move[0], j = move[1];
        int[][] newBoard = new int[ROW_COUNT][COL_COUNT];
        for (int h = 0; h < ROW_COUNT; h++) {
            for (int k = 0; k < COL_COUNT; k++) {
                newBoard[h][k] = board[h][k];
            }
        }
        newBoard[i][j] = isUserTurn ? 2 : 1;
        return newBoard;
    }

    private Object[] searchWinningMove(int[][] matrix) {
        ArrayList<int[]> allPossibleMoves = generateMoves(matrix);
        System.out.println(allPossibleMoves.size());

        Object[] winningMove = new Object[3];

        for (int[] move : allPossibleMoves) {
            int[][] dummyBoard = playNextMove(matrix, move, false);

            // If the white player has a winning score in that temporary board, return the move.
            if (getScore(dummyBoard, false, false) >= winScore) {
                winningMove[1] = move[0];
                winningMove[2] = move[1];
                return winningMove;
            }
        }

        return winningMove;
    }

    private Object[] searchLoseMove(int[][] matrix) {
        ArrayList<int[]> allPossibleMoves = generateMoves(matrix);
        System.out.println(allPossibleMoves.size());

        Object[] losingMove = new Object[3];

        for (int[] move : allPossibleMoves) {
            int[][] dummyBoard = playNextMove(matrix, move, true);

            // If the white player has a winning score in that temporary board, return the move.
            if (getScore(dummyBoard, true, false) >= winScore) {
                losingMove[1] = move[0];
                losingMove[2] = move[1];
                return losingMove;
            }
        }

        return losingMove;
    }

    public Object[] minimaxSearchAB(int depth, int[][] board, boolean max, double alpha, double beta) {
        if (depth == 0) {
            return new Object[]{evaluateBoardForWhite(board, !max), null, null};
        }

        ArrayList<int[]> allPossibleMoves = generateMoves(board);

        if (allPossibleMoves.isEmpty()) {
            return new Object[]{evaluateBoardForWhite(board, !max), null, null};
        }

        Object[] bestMove = new Object[3];

        if (max) {
            bestMove[0] = -1.0;

            for (int[] move : allPossibleMoves) {

                // Chơi thử với move hiện tại
                int[][] dummyBoard = playNextMove(board, move, false);
                Object[] tempMove = minimaxSearchAB(depth - 1, dummyBoard, false, alpha, beta);

                // Cập nhật alpha
                if ((Double) (tempMove[0]) > alpha) {
                    alpha = (Double) (tempMove[0]);
                }
                // Cắt tỉa beta
                if ((Double) (tempMove[0]) >= beta) {
                    return tempMove;
                }
                if ((Double) tempMove[0] > (Double) bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }

        } else {
            bestMove[0] = 100000000.0;
            bestMove[1] = allPossibleMoves.get(0)[0];
            bestMove[2] = allPossibleMoves.get(0)[1];
            for (int[] move : allPossibleMoves) {
                int[][] dummyBoard = playNextMove(board, move, true);

                Object[] tempMove = minimaxSearchAB(depth - 1, dummyBoard, true, alpha, beta);

                // Cập nhật beta
                if (((Double) tempMove[0]) < beta) {
                    beta = (Double) (tempMove[0]);
                }
                // Cắt tỉa alpha
                if ((Double) (tempMove[0]) <= alpha) {
                    return tempMove;
                }
                if ((Double) tempMove[0] < (Double) bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }
        }
        return bestMove;
    }

    public double evaluateBoardForWhite(int[][] board, boolean userTurn) {

        double blackScore = getScore(board, true, userTurn);
        double whiteScore = getScore(board, false, userTurn);

        if (blackScore == 0) {
            blackScore = 1.0;
        }

        return whiteScore / blackScore;

    }

    public ArrayList<int[]> generateMoves(int[][] boardMatrix) {
        ArrayList<int[]> moveList = new ArrayList<int[]>();

        int boardSize = boardMatrix.length;

        // Tìm những tất cả những ô trống nhưng có đánh XO liền kề
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                if (boardMatrix[i][j] > 0) {
                    continue;
                }

                if (i > 0) {
                    if (j > 0) {
                        if (boardMatrix[i - 1][j - 1] > 0
                                || boardMatrix[i][j - 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (j < boardSize - 1) {
                        if (boardMatrix[i - 1][j + 1] > 0
                                || boardMatrix[i][j + 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (boardMatrix[i - 1][j] > 0) {
                        int[] move = {i, j};
                        moveList.add(move);
                        continue;
                    }
                }
                if (i < boardSize - 1) {
                    if (j > 0) {
                        if (boardMatrix[i + 1][j - 1] > 0
                                || boardMatrix[i][j - 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (j < boardSize - 1) {
                        if (boardMatrix[i + 1][j + 1] > 0
                                || boardMatrix[i][j + 1] > 0) {
                            int[] move = {i, j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if (boardMatrix[i + 1][j] > 0) {
                        int[] move = {i, j};
                        moveList.add(move);
                        continue;
                    }
                }

            }
        }
        return moveList;

    }

    // Đánh giá bàn cờ dựa trên tổng số điểm hàng ngang, hàng dọc, và 2 đường chéo
    public int getScore(int[][] board, boolean forX, boolean blacksTurn) {

        return evaluateHorizontal(board, forX, blacksTurn)
                + evaluateVertical(board, forX, blacksTurn)
                + evaluateDiagonal(board, forX, blacksTurn);
    }

    public static int evaluateHorizontal(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;

        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[0].length; j++) {

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    //2. Đếm...
                    consecutive++;
                } // gặp ô trống
                else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        // Ra: Ô trống ở cuối sau khi đếm. Giảm block rồi bắt đầu tính điểm sau đó reset lại ban đầu
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        // 1. Vào reset lại blocks = 1 rồi bắt đầu đếm
                        blocks = 1;
                    }
                } //gặp quân địch
                else if (consecutive > 0) {
                    // 2.Ra:  Ô bị chặn sau khi đếm. Tính điểm sau đó reset lại.
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    //1. Vào: reset lại blocks = 2 rồi bắt đầu đếm
                    blocks = 2;
                }
            }

            // 3. Ra: nhưng lúc này đang ở cuối. Nếu liên tục thì vẫn tính cho đến hết dòng
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            // reset lại để tiếp tục chạy cho dòng tiếp theo
            consecutive = 0;
            blocks = 2;

        }
        return score;
    }
    // hàm tính toán đường dọc tương tự như đường ngan

    public static int evaluateVertical(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;

        for (int j = 0; j < boardMatrix[0].length; j++) {
            for (int i = 0; i < boardMatrix.length; i++) {
                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }
            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;

        }
        return score;
    }

    // Hàm tính toán 2 đường chéo tương tự như hàng ngang
    public static int evaluateDiagonal(int[][] boardMatrix, boolean forX, boolean playersTurn) {

        int consecutive = 0;
        int blocks = 2;
        int score = 0;
        // Đường chéo /
        for (int k = 0; k <= 2 * (boardMatrix.length - 1); k++) {
            int iStart = Math.max(0, k - boardMatrix.length + 1);
            int iEnd = Math.min(boardMatrix.length - 1, k);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = k - i;

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }

            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;
        }
        // Đường chéo \
        for (int k = 1 - boardMatrix.length; k < boardMatrix.length; k++) {
            int iStart = Math.max(0, k);
            int iEnd = Math.min(boardMatrix.length + k - 1, boardMatrix.length - 1);
            for (int i = iStart; i <= iEnd; ++i) {
                int j = i - k;

                if (boardMatrix[i][j] == (forX ? 2 : 1)) {
                    consecutive++;
                } else if (boardMatrix[i][j] == 0) {
                    if (consecutive > 0) {
                        blocks--;
                        score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                        consecutive = 0;
                        blocks = 1;
                    } else {
                        blocks = 1;
                    }
                } else if (consecutive > 0) {
                    score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);
                    consecutive = 0;
                    blocks = 2;
                } else {
                    blocks = 2;
                }

            }
            if (consecutive > 0) {
                score += getConsecutiveSetScore(consecutive, blocks, forX == playersTurn);

            }
            consecutive = 0;
            blocks = 2;
        }
        return score;
    }

    public static int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
        final int winGuarantee = 1000000;
        if (blocks == 2 && count <= 5) {
            return 0;
        }
        switch (count) {
            // Ăn 5 -> Cho điểm cao nhất
            case 5: {
                return winScore;
            }
            case 4: {
                // Đang 4 -> Tuỳ theo lược và bị chặn: winGuarantee, winGuarantee/4, 200
                if (currentTurn) {
                    return winGuarantee;
                } else {
                    if (blocks == 0) {
                        return winGuarantee / 4;
                    } else {
                        return 200;
                    }
                }
            }
            case 3: {
                // Đang 3: Block = 0
                if (blocks == 0) {
                    // Nếu lược của currentTurn thì ăn 3 + 1 = 4 (không bị block) -> 50000 -> Khả năng thắng cao. 
                    // Ngược lại không phải lược của currentTurn thì khả năng bị blocks cao
                    if (currentTurn) {
                        return 50000;
                    } else {
                        return 200;
                    }
                } else {
                    // Block == 1 hoặc Blocks == 2
                    if (currentTurn) {
                        return 10;
                    } else {
                        return 5;
                    }
                }
            }
            case 2: {
                // Tương tự với 2
                if (blocks == 0) {
                    if (currentTurn) {
                        return 7;
                    } else {
                        return 5;
                    }
                } else {
                    return 3;
                }
            }
            case 1: {
                return 1;
            }
        }
        return winScore * 2;
    }

    public int[][] getMatrixBoard() {
        int[][] matrix = new int[ROW_COUNT][COL_COUNT];
        for (int i = 0; i < Buttons.length; i++) {
            for (int j = 0; j < Buttons.length; j++) {
                int value = Buttons[i][j].value;
                matrix[i][j] = value;
            }
        }

        return matrix;
    }

    private javax.swing.JLabel botLabel;
    private javax.swing.JButton competitorButtonImage;
    private javax.swing.JLabel competitorCurrentPosition;
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorNicknameLabel;
    private javax.swing.JLabel competitorNicknameValue;
    private javax.swing.JLabel competitorNumberOfGameLabel;
    private javax.swing.JLabel competitorNumberOfGameValue;
    private javax.swing.JLabel competitorNumberOfWinLabel;
    private javax.swing.JLabel competitorNumberOfWinValue;
    private javax.swing.JLabel compretitorTurnJLabel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JPanel gameBoardPanel;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JMenuItem newGameMenuItem;
    private javax.swing.JLabel playerButtonImage;
    private javax.swing.JLabel playerCurrentPosition;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerNickNameLabel;
    private javax.swing.JLabel playerNicknameValue;
    private javax.swing.JLabel playerNumberOfGameLabel;
    private javax.swing.JLabel playerNumberOfGameValue;
    private javax.swing.JLabel playerNumberOfWinLabel;
    private javax.swing.JLabel playerNumberOfWinValue;
    private javax.swing.JTextArea roomMessageTextArea;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel vsIcon;
    private javax.swing.JLabel yourTurnJLabel;
}
