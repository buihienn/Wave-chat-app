package com.wavechat.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private ActionListener actionListener;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();  // Dừng chế độ chỉnh sửa khi nhấn nút
                if (actionListener != null) {
                    actionListener.actionPerformed(e);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);

        // Tùy chỉnh màu sắc
        button.setBackground(isSelected ? Color.LIGHT_GRAY : new Color(26, 41, 128));
        button.setForeground(Color.WHITE);
        return button;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }
}