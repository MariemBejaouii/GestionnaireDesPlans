/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Connection;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class notification {

    public void addNotifications(String title, String content) {

        if (null != content) {
            if (content.length() > 50) {
                content = content.substring(0, 49) + "......";
            }
        }
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(content)
                .hideAfter(Duration.seconds(120))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.showInformation();
    
} }
