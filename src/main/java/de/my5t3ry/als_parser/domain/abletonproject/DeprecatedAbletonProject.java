package de.my5t3ry.als_parser.domain.abletonproject;

import java.util.ArrayList;

/**
 * created by: sascha.bast
 * since: 08.09.17
 */
public class DeprecatedAbletonProject extends AbletonProject {
    public DeprecatedAbletonProject() {
        this.internalDevices = new ArrayList<>();
        this.externalDevices = new ArrayList<>();
        this.audioTracksCount = 0;
        this.groupTracksCount = 0;
        this.midiTracksCount = 0;
    }
}
