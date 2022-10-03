package kr.goldenmine.inuminecraftlauncher.ui;

import kr.goldenmine.inuminecraftlauncher.request.MicrosoftServiceImpl;
import kr.goldenmine.inuminecraftlauncher.request.models.MicrosoftTokenResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.minecraft.MinecraftProfileResponse;
import kr.goldenmine.inuminecraftlauncher.request.models.xbox.XBoxXstsResponse;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
public class MainFrame extends JFrame {
    private JTextField microsoftId = new JTextField();
    private JPasswordField microsoftPassword = new JPasswordField();

    private JButton loginMicrosoft = new JButton("로그인");
    private JButton loginINUToken = new JButton("게스트");

    private JTextArea logArea = new JTextArea();

    private JPanel idPasswordPanel = new JPanel();

    public MainFrame() {
        super("INU Minecraft Launcher");

        setLayout(null);
        setSizeMiddle(360,  300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        idPasswordPanel.setLayout(null);
        microsoftPassword.setEchoChar('*');
        logArea.setEditable(false);

        JScrollPane logAreaPane = new JScrollPane(logArea);

        idPasswordPanel.setBounds(10, 20, 200, 70);
        microsoftId.setBounds(0, 0, 200, 30);
        microsoftPassword.setBounds(0, 40, 200, 30);
        loginMicrosoft.setBounds(220, 20, 60, 70);
        loginINUToken.setBounds(290, 20, 60, 70);
        logArea.setBounds(10, 100, 340, 150);
        logAreaPane.setBounds(10, 100, 340, 150);

        idPasswordPanel.add(microsoftId);
        idPasswordPanel.add(microsoftPassword);

        add(idPasswordPanel);
        add(loginMicrosoft);
        add(loginINUToken);
        getContentPane().add(logAreaPane);

        microsoftId.setVisible(true);
        microsoftPassword.setVisible(true);
        loginMicrosoft.setVisible(true);
        loginINUToken.setVisible(true);

        registerAllEvents();
    }

    public void addLog(String text) {
        logArea.append(text + "\n");
    }

    public void registerAllEvents() {
        MoveToTheBottom.install(logArea);

        loginMicrosoft.addActionListener(e -> {
            tryLogin();
        });

        microsoftPassword.addActionListener(e -> {
            tryLogin();
        });
    }

    public void tryLogin() {
        new Thread(() -> {
            String id = microsoftId.getText();
            String password = new String(microsoftPassword.getPassword());

            addLog("id: " + id);
            addLog("password: " + password);

            try {
                MicrosoftTokenResponse tokenResponse = MicrosoftServiceImpl.firstLogin(id, password);
                addLog("Access Token: " + tokenResponse.getAccessToken());
                addLog("Refresh Token: " + tokenResponse.getRefreshToken());

                XBoxXstsResponse xstsResponse = MicrosoftServiceImpl.loginXbox(tokenResponse.getAccessToken());
                addLog("Profile Token: " + xstsResponse.getToken());

                MinecraftProfileResponse profileResponse = MicrosoftServiceImpl.getMinecraftProfile(xstsResponse.getToken(), xstsResponse.getPreviousUhs());
                addLog("UUID: " + profileResponse.getId());
                addLog("Name: " + profileResponse.getName());
            } catch (InterruptedException | ExecutionException | IOException ex) {
                ex.printStackTrace();
                addLog(ex.getMessage());
            }
        }).start();
    }

    public void setSizeMiddle(int width, int height) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int monitorWidth = gd.getDisplayMode().getWidth();
        int monitorHeight = gd.getDisplayMode().getHeight();

        int x = monitorWidth / 2 - width / 2;
        int y = monitorHeight / 2 - height / 2;

        setBounds(x, y, width, height);
    }

    public static class MoveToTheBottom implements DocumentListener {

        private static WeakHashMap<JTextComponent, DocumentListener> registry = new WeakHashMap<>(25);
        private JTextComponent parent;

        protected MoveToTheBottom(JTextComponent parent) {
            this.parent = parent;
            parent.getDocument().addDocumentListener(this);
        }

        public static void install(JTextComponent parent) {
            MoveToTheBottom bottom = new MoveToTheBottom(parent);
            registry.put(parent, bottom);
        }

        public static void uninstall(JTextComponent parent) {
            DocumentListener listener = registry.remove(parent);
            if (listener != null) {
                parent.getDocument().removeDocumentListener(listener);
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            parent.setCaretPosition(e.getDocument().getLength());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            parent.setCaretPosition(e.getDocument().getLength());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            parent.setCaretPosition(e.getDocument().getLength());
        }

    }
}
