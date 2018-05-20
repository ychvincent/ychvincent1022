package com.ychvincent.itproject2;

import java.io.Serializable;

/**
 * Created by ychvi on 13/5/2018.
 */

public class LanguageHolder implements Serializable {
    String selectedLanguage, languageModel, languageIdentifier;
    public LanguageHolder()
    {
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getLanguageModel() {
        return languageModel;
    }

    public void setLanguageModel(String languageModel) {
        this.languageModel = languageModel;
    }

    public String getLanguageIdentifier() {
        return languageIdentifier;
    }

    public void setLanguageIdentifier(String languageIdentifier) {
        this.languageIdentifier = languageIdentifier;
    }
}
