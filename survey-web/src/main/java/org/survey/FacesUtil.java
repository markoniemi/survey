package org.survey;

import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * FacesUtil combines all methods that use FacesContext or ExternalContext. Have
 * not yet figured out how to replace it with mock class during testing.
 */
public class FacesUtil {
    /**
     * FacesUtil is a utility class and hence should not have public
     * constructor.
     */
    private FacesUtil() {
    }

    public static String showMessage(String id, String messageKey) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = facesContext.getApplication()
                .getResourceBundle(facesContext, "messages");
        String message = resourceBundle.getString(messageKey);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(message));
        return null;
    }

    public static String getRequestParameter(String parameterName) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) {
            return null;
        }
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        if (map == null) {
            return null;
        }
        return (String) map.get(parameterName);
    }
}
