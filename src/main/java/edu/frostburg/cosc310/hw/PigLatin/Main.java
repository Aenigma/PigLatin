/*
 * Copyright 2014 Kevin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.frostburg.cosc310.hw.PigLatin;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main UI class. Sets up {@link PigLatinPanel} to and displays it as a
 * {@link JFrame}. Also where {@link #main(java.lang.String[])} is.
 *
 * @author Kevin Raoofi
 */
public class Main extends JFrame {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    /* Panel which does all the real work */
    private final PigLatinPanel pigLatinPanel;

    /**
     * Creates new form View
     */
    public Main() {
        this.pigLatinPanel = new PigLatinPanel();
        this.add(pigLatinPanel);
    }

    public static void main(String args[]) {
        /* Set the Native look and feel */

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOG.log(java.util.logging.Level.SEVERE, null, ex);
        }

        final StringBuilder sb = new StringBuilder();
        try (Reader r = new InputStreamReader(Main.class.getResource("/sample.txt").openStream())) {
            CharBuffer cb = CharBuffer.allocate(0x10);
            while (r.ready()) {
                r.read(cb);
                cb.rewind();
                sb.append(cb);
                cb.rewind();
            }

        } catch (IOException ex) {
            LOG.log(Level.WARNING, null, ex);
        }

        final String initialText = sb.toString();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main m = new Main();
                m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                m.pack();
                m.setTitle("PIG LATIN");
                m.setMinimumSize(m.getPreferredSize());
                m.pigLatinPanel.getTxtInput().setText(initialText);
                m.setVisible(true);
            }
        });
    }
}
