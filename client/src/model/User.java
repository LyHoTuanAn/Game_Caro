package model;

/**
 * @author Admin
 */
public class User {
    private int ID;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private int numberOfGame;
    private int numberOfWin;
    private int numberOfDraw;
    private boolean online;
    private boolean playing;
    private int rank;

    public User(int ID, String username, String password, String nickname, String avatar, int numberOfGame, int numberOfWin, int numberOfDraw, int rank) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.numberOfGame = numberOfGame;
        this.numberOfWin = numberOfWin;
        this.numberOfDraw = numberOfDraw;
        this.rank = rank;
    }

    public User(int ID, String username, String password, String nickname, String avatar, int numberOfGame, int numberOfWin, int numberOfDraw, boolean online, boolean playing) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.numberOfGame = numberOfGame;
        this.numberOfWin = numberOfWin;
        this.numberOfDraw = numberOfDraw;
        this.online = online;
        this.playing = playing;
    }

    public User(int ID, String username, String password, String nickname, String avatar, int numberOfGame, int numberOfWin, int numberOfDraw) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.numberOfGame = numberOfGame;
        this.numberOfWin = numberOfWin;
        this.numberOfDraw = numberOfDraw;
    }


    public User(int ID, String nickname) {
        this.ID = ID;
        this.nickname = nickname;
    }

    public User(int ID, String nickname, boolean online, boolean playing) {
        this.ID = ID;
        this.nickname = nickname;
        this.online = online;
        this.playing = playing;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public User(String username, String password, String nickname, String avatar) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public User(int ID, String nickname, int numberOfGame, int numberOfDraw) {
        this.ID = ID;
        this.nickname = nickname;
        this.numberOfGame = numberOfGame;
        this.numberOfDraw = numberOfDraw;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNumberOfGame() {
        return numberOfGame;
    }

    public void setNumberOfGame(int numberOfGame) {
        this.numberOfGame = numberOfGame;
    }

    public int getNumberOfWin() {
        return numberOfWin;
    }

    public void setNumberOfWin(int numberOfWin) {
        this.numberOfWin = numberOfWin;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean isOnline) {
        this.online = isOnline;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean isPlaying) {
        this.playing = isPlaying;
    }

    public int getNumberOfDraw() {
        return numberOfDraw;
    }

    public void setNumberOfDraw(int numberOfDraw) {
        this.numberOfDraw = numberOfDraw;
    }


}
