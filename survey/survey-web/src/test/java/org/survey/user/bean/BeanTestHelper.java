package org.survey.user.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeanTestHelper {
    String requestParameter;
    String message;

    void showMessage(String id, String messageKey) {
        try {
            ResourceBundle resourceBundle = new PropertyResourceBundle(new FileInputStream(
                    "src/main/resources/usermanagement/MessageResources.properties"));
            message = resourceBundle.getString(messageKey);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
