package kr.goldenmine.inumincraftlauncher;

import kr.goldenmine.inuminecraftlauncher.CoreMain;
import kr.goldenmine.inuminecraftlauncher.ui.MainFrame;

public class UIMainTest {
    public static void main(String[] args) {
        CoreMain.main(args);
        new MainFrame().setVisible(true);
    }
}
