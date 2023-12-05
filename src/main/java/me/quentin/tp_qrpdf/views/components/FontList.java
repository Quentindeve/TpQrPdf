package me.quentin.tp_qrpdf.views.components;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Font;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FontList extends JComboBox<Font> {

    private ArrayList<Font> fonts;

    public FontList() {
        var fontConstants = Font.FontFamily.values();

        this.fonts = Arrays.asList(fontConstants).stream().map(font -> {
            var instance = new Font(font, 20.f, 0);
            System.out.println(instance.toString());
            return instance;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public Font getSelectedFont() {
        return this.fonts.get(this.getSelectedIndex());
    }
}
