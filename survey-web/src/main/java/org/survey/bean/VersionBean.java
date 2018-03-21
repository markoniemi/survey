package org.survey.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Bean for displaying version information on page.
 */
@Component
@Scope("request")
@Slf4j
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
public class VersionBean {
    private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
    private static final String CREATED_BY = "Created-By";
    private static final String BUILT_BY = "Built-By";
    private static final String UNKNOWN = "unknown";
    @Getter
    private String version = UNKNOWN;
    @Getter
    private String createdBy = UNKNOWN;
    @Getter
    private String builtBy = UNKNOWN;

    /**
     * Read manifest attributes to bean.
     */
    @PostConstruct
    public void initialize() {
        try {
            Manifest manifest = getManifest();
            Attributes attributes = manifest.getMainAttributes();
            version = attributes.getValue(IMPLEMENTATION_VERSION);
            createdBy = attributes.getValue(CREATED_BY);
            builtBy = attributes.getValue(BUILT_BY);
        } catch (NullPointerException | IOException e ) {
            log.error("Unable to read the Manifest file from classpath.", e);
        }
    }

    /**
     * getManifest is package private to enable overriding in a test case.
     * 
     * @throws IOException
     *             if manifest file does not exist.
     */
    Manifest getManifest() throws IOException {
        InputStream inputStream = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getResourceAsStream("/META-INF/MANIFEST.MF");
        return new Manifest(inputStream);
    }
}
