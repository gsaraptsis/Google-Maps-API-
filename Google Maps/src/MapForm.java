import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//import com.kingaspx.util.BrowserUtil;
//import com.kingaspx.version.Version;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMInputElement;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MapForm {
    private JTextField textField1;
    private JButton searchButton;
    private JButton reloadButton;
    private JPanel map_panel;
    private JLabel label1;

    public MapForm() {
        open_site();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DOMDocument doc = browser.getDocument();

                DOMElement address_element = doc.findElement(By.id("address"));
                DOMElement search_element = doc.findElement(By.id("submit"));
                DOMElement button = (DOMElement) search_element;

                DOMInputElement address = (DOMInputElement) address_element;
                address.setValue(textField1.getText());

                button.click();
            }
        });
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.reload();
            }
        });
    }
    Browser browser;
    BrowserView view;

    private void open_site() {
        //BrowserUtil.setVersion(Version.V6_22);

        browser = new Browser();
        view = new BrowserView(browser);

        map_panel.add(view, BorderLayout.CENTER);

        browser.addTitleListener((TitleEvent evt) -> {
            label1.setText("   " + evt.getTitle());
        });

        browser.addConsoleListener((ConsoleEvent evt) -> {
            System.out.println("LOG: " + evt.getMessage());
        });

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent evt) {
                evt.getBrowser().setZoomLevel(-2);
            }
        });

        browser.loadURL("src/HtmlForms/simple_map.html");
    }
}
